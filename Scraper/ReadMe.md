
# Coop Supermarket Web Scraper

This Python project uses Scrapy to scrape the [Coop Supermarket](https://www.coop.ch/de/lebensmittel/c/supermarket) website, extracting nutritional information (Nährwerte) for all products under various categories and subcategories.

## Features

- Scrapes categories, subcategories, and individual product pages.
- Extracts nutritional information including:
  - Energy in kJ
  - Energy in kcal
  - Fat
  - Saturated fats
  - Carbohydrates
  - Sugars
  - Proteins
  - Salt
- Saves the scraped data into a JSON file for further analysis.

## Installation

To use this scraper, follow the steps below:

1. **Clone the Repository**:
   \`\`\`bash
   git clone https://github.com/yourusername/coop_scraper.git
   cd coop_scraper
   \`\`\`

2. **Create a Virtual Environment (Optional but Recommended)**:
   \`\`\`bash
   python3 -m venv venv
   source venv/bin/activate  # On Windows use \`venv\Scripts\activate\`
   \`\`\`

3. **Install Dependencies**:
   Use the \`requirements.txt\` to install the necessary Python libraries.
   \`\`\`bash
   pip install -r requirements.txt
   \`\`\`

   If you don't have a \`requirements.txt\` file yet, you can generate it by running:
   \`\`\`bash
   pip freeze > requirements.txt
   \`\`\`

4. **Install Scrapy** (if not included in \`requirements.txt\`):
   \`\`\`bash
   pip install scrapy
   \`\`\`

## Usage

1. **Navigate to the Scrapy Project Directory**:
   Ensure you are in the \`coop_scraper\` directory where the Scrapy project is set up.

2. **Run the Scraper**:
   To scrape the Coop supermarket website and save the results in a JSON file, use the following command:
   \`\`\`bash
   scrapy crawl coop -o products.json
   \`\`\`

   This will run the \`coop\` spider and save the output into a file called \`products.json\`.

3. **Check the Output**:
   The scraper will create a \`products.json\` file that contains the following structure:
   \`\`\`json
   [
       {
           "product_name": "Product Name",
           "nutrition": {
               "Energie in kJ": "1234",
               "Energie in kcal": "567",
               "Fett": "12g",
               "Davon gesättigte Fettsäuren": "4g",
               "Kohlenhydrate": "20g",
               "davon Zucker": "5g",
               "Eiweiss": "3g",
               "Salz": "1g"
           }
       },
       ...
   ]
   \`\`\`

## Scraper Flow

1. **Main URL**: The scraper starts at the main URL: \`https://www.coop.ch/de/lebensmittel/c/supermarket\`.
2. **Category and Subcategory Navigation**: It extracts the categories and subcategories, visiting each one.
3. **Product Page**: For each product, the scraper visits the product's detail page and extracts the relevant nutritional information.
4. **Data Extraction**: The nutritional data is scraped from the "Nährwerte" section for each product.

## Customization

- **Pagination Handling**: If you want to handle paginated categories or subcategories, you can modify the spider to follow pagination links.
- **Output Formats**: You can change the output format (e.g., CSV, XML) by adjusting the \`-o\` flag in the Scrapy command:
   \`\`\`bash
   scrapy crawl coop -o products.csv
   \`\`\`

## Dependencies

This project depends on the following Python packages:
- \`scrapy\`

Ensure all dependencies are installed using the \`requirements.txt\` file or by manually installing them using \`pip\`.

## Contributing

If you'd like to contribute to this project, feel free to fork the repository and submit a pull request with your improvements or bug fixes.

## License

This project is open-source and licensed under the MIT License.
