import scrapy

class CoopSpider(scrapy.Spider):
    name = "coop"
    start_urls = [
        'https://www.coop.ch/de/lebensmittel/c/supermarket'
    ]

    def parse(self, response):
        # Extract the main categories and subcategories
        categories = response.css('.navigation-wrapper li a::attr(href)').getall()

        for category in categories:
            # Follow each category link
            yield response.follow(category, callback=self.parse_category)

    def parse_category(self, response):
        # Extract subcategories or articles from each category
        subcategories = response.css('.category-item a::attr(href)').getall()

        for subcategory in subcategories:
            # Follow each subcategory link
            yield response.follow(subcategory, callback=self.parse_subcategory)

    def parse_subcategory(self, response):
        # Extract articles (products) from the subcategory page
        articles = response.css('.product-grid__item a::attr(href)').getall()

        for article in articles:
            # Follow each article link
            yield response.follow(article, callback=self.parse_product)

    def parse_product(self, response):
        # Extract the product name
        product_name = response.css('h1::text').get()

        # Extract the nutritional values section (Nährwerte)
        nutrition_section = response.xpath("//h2[contains(text(), 'Nährwerte')]/following-sibling::div").get()

        if nutrition_section:
            # Extract the individual nutritional values
            values = {
                'Energie in kJ': response.xpath("//td[contains(text(), 'Energie in kJ')]/following-sibling::td/text()").get(),
                'Energie in kcal': response.xpath("//td[contains(text(), 'Energie in kcal')]/following-sibling::td/text()").get(),
                'Fett': response.xpath("//td[contains(text(), 'Fett')]/following-sibling::td/text()").get(),
                'Davon gesättigte Fettsäuren': response.xpath("//td[contains(text(), 'gesättigte Fettsäuren')]/following-sibling::td/text()").get(),
                'Kohlenhydrate': response.xpath("//td[contains(text(), 'Kohlenhydrate')]/following-sibling::td/text()").get(),
                'davon Zucker': response.xpath("//td[contains(text(), 'davon Zucker')]/following-sibling::td/text()").get(),
                'Eiweiss': response.xpath("//td[contains(text(), 'Eiweiss')]/following-sibling::td/text()").get(),
                'Salz': response.xpath("//td[contains(text(), 'Salz')]/following-sibling::td/text()").get(),
            }

            # Return the result as a dictionary
            yield {
                'product_name': product_name,
                'nutrition': values,
            }
