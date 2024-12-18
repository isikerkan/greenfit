-- Part 1: Creating tables without relationships

CREATE TABLE Article
(
    ID                 SERIAL PRIMARY KEY,
    Article_Number     VARCHAR(100) NOT NULL,
    Name               VARCHAR(255) NOT NULL,
    Barcode            VARCHAR(100),
    Manufacturer_ID    INT,
    Unit_Of_Measure_ID INT,
    Food_Category_ID   INT
);

CREATE TABLE Recipe
(
    ID   SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL
);

CREATE TABLE Ingredients
(
    ID         SERIAL PRIMARY KEY,
    Article_ID INT,
    Recipe_ID  INT
);

CREATE TABLE Nutritional_Values
(
    ID                             SERIAL PRIMARY KEY,
    Values_Per_100g_100ml          VARCHAR(50),
    Article_ID                     INT,
    Calories_kcal                  DECIMAL,
    Protein_g                      DECIMAL,
    Carbs_g                        DECIMAL,
    Total_Fats_g                   DECIMAL,
    Saturated_Fats_g               DECIMAL,
    Monounsaturated_Fats_g         DECIMAL,
    Polyunsaturated_Fats_g         DECIMAL,
    Sugars_g                       DECIMAL,
    Fiber_g                        DECIMAL,
    Salt_g                         DECIMAL,
    Vitamin_C_mg                   DECIMAL,
    Vitamin_D_ug                   DECIMAL,
    Vitamin_A_ug                   DECIMAL,
    Vitamin_B1_Thiamin_mg          DECIMAL,
    Vitamin_B2_Riboflavin_mg       DECIMAL,
    Vitamin_B3_Niacin_mg           DECIMAL,
    Vitamin_B5_Pantothenic_Acid_mg DECIMAL,
    Vitamin_B6_mg                  DECIMAL,
    Vitamin_B7_Biotin_ug           DECIMAL,
    Vitamin_B11_Folic_Acid_ug      DECIMAL,
    Vitamin_B12_ug                 DECIMAL,
    Vitamin_E_mg                   DECIMAL,
    Vitamin_K_ug                   DECIMAL,
    Calcium_mg                     DECIMAL,
    Iron_mg                        DECIMAL,
    Arsenic_ug                     DECIMAL,
    Boron_mg                       DECIMAL,
    Choline_mg                     DECIMAL,
    Chloride_mg                    DECIMAL,
    Chromium_mg                    DECIMAL,
    Cobalt_ug                      DECIMAL,
    Copper_mg                      DECIMAL,
    Fluoride_mg                    DECIMAL,
    Iodine_ug                      DECIMAL,
    Magnesium_mg                   DECIMAL,
    Manganese_mg                   DECIMAL,
    Molybdenum_ug                  DECIMAL,
    Phosphorus_mg                  DECIMAL,
    Potassium_mg                   DECIMAL,
    Rubidium_ug                    DECIMAL,
    Selenium_ug                    DECIMAL,
    Silicon_mg                     DECIMAL,
    Sulfur_mg                      DECIMAL,
    Tin_mg                         DECIMAL,
    Vanadium_ug                    DECIMAL,
    Zinc_mg                        DECIMAL,
    Water_ml                       DECIMAL,
    Alcohol_ml                     DECIMAL
);

CREATE TABLE Units_Of_Measure
(
    ID   SERIAL PRIMARY KEY,
    Unit VARCHAR(100)
);

CREATE TABLE Manufacturer
(
    ID      SERIAL PRIMARY KEY,
    Name    VARCHAR(255) NOT NULL,
    Country VARCHAR(100)
);

CREATE TABLE Portion
(
    ID                SERIAL PRIMARY KEY,
    Portion_Size_Type VARCHAR(100),
    Amount            DECIMAL
);

CREATE TABLE Portion_Sizes
(
    ID         SERIAL PRIMARY KEY,
    Portion_ID INT,
    Article_ID INT,
    Recipe_ID  INT,
    Quantity   DECIMAL
);

CREATE TABLE Food_Category
(
    ID   SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Type VARCHAR(100)
);

CREATE TABLE Slots
(
    ID   SERIAL PRIMARY KEY,
    Name VARCHAR(100) NOT NULL
);

CREATE TABLE Consumption
(
    ID         SERIAL PRIMARY KEY,
    Slots_ID   INT,
    Article_ID INT,
    Recipe_ID  INT,
    Person_ID  INT
);

CREATE TABLE Person
(
    ID        SERIAL PRIMARY KEY,
    Name      VARCHAR(255) NOT NULL,
    Age       INT,
    Gender    VARCHAR(10),
    Height    DECIMAL,
    Weight    DECIMAL,
    Allergies VARCHAR(255)
);

CREATE TABLE Recommended_Daily_Values
(
    ID                             SERIAL PRIMARY KEY,
    Article_ID                     INT,
    Calories_kcal                  DECIMAL,
    Protein_g                      DECIMAL,
    Carbs_g                        DECIMAL,
    Total_Fats_g                   DECIMAL,
    Saturated_Fats_g               DECIMAL,
    Monounsaturated_Fats_g         DECIMAL,
    Polyunsaturated_Fats_g         DECIMAL,
    Sugars_g                       DECIMAL,
    Fiber_g                        DECIMAL,
    Salt_g                         DECIMAL,
    Vitamin_C_mg                   DECIMAL,
    Vitamin_D_ug                   DECIMAL,
    Vitamin_A_ug                   DECIMAL,
    Vitamin_B1_Thiamin_mg          DECIMAL,
    Vitamin_B2_Riboflavin_mg       DECIMAL,
    Vitamin_B3_Niacin_mg           DECIMAL,
    Vitamin_B5_Pantothenic_Acid_mg DECIMAL,
    Vitamin_B6_mg                  DECIMAL,
    Vitamin_B7_Biotin_ug           DECIMAL,
    Vitamin_B11_Folic_Acid_ug      DECIMAL,
    Vitamin_B12_ug                 DECIMAL,
    Vitamin_E_mg                   DECIMAL,
    Vitamin_K_ug                   DECIMAL,
    Calcium_mg                     DECIMAL,
    Iron_mg                        DECIMAL,
    Arsenic_ug                     DECIMAL,
    Boron_mg                       DECIMAL,
    Choline_mg                     DECIMAL,
    Chloride_mg                    DECIMAL,
    Chromium_mg                    DECIMAL,
    Cobalt_ug                      DECIMAL,
    Copper_mg                      DECIMAL,
    Fluoride_mg                    DECIMAL,
    Iodine_ug                      DECIMAL,
    Magnesium_mg                   DECIMAL,
    Manganese_mg                   DECIMAL,
    Molybdenum_ug                  DECIMAL,
    Phosphorus_mg                  DECIMAL,
    Potassium_mg                   DECIMAL,
    Rubidium_ug                    DECIMAL,
    Selenium_ug                    DECIMAL,
    Silicon_mg                     DECIMAL,
    Sulfur_mg                      DECIMAL,
    Tin_mg                         DECIMAL,
    Vanadium_ug                    DECIMAL,
    Zinc_mg                        DECIMAL,
    Water_ml                       DECIMAL,
    Alcohol_ml                     DECIMAL
);

-- Part 2: Establishing relationships between tables

ALTER TABLE Article
    ADD CONSTRAINT fk_manufacturer
        FOREIGN KEY (Manufacturer_ID)
            REFERENCES Manufacturer (ID);

ALTER TABLE Article
    ADD CONSTRAINT fk_unit_of_measure
        FOREIGN KEY (Unit_Of_Measure_ID)
            REFERENCES Units_Of_Measure (ID);

ALTER TABLE Ingredients
    ADD CONSTRAINT fk_article
        FOREIGN KEY (Article_ID)
            REFERENCES Article (ID);

ALTER TABLE Ingredients
    ADD CONSTRAINT fk_recipe
        FOREIGN KEY (Recipe_ID)
            REFERENCES Recipe (ID);

ALTER TABLE Nutritional_Values
    ADD CONSTRAINT fk_nutritional_values_article
        FOREIGN KEY (Article_ID)
            REFERENCES Article (ID);

ALTER TABLE Portion_Sizes
    ADD CONSTRAINT fk_portion_sizes_portion
        FOREIGN KEY (Portion_ID)
            REFERENCES Portion (ID);

ALTER TABLE Portion_Sizes
    ADD CONSTRAINT fk_portion_sizes_article
        FOREIGN KEY (Article_ID)
            REFERENCES Article (ID);

ALTER TABLE Portion_Sizes
    ADD CONSTRAINT fk_portion_sizes_recipe
        FOREIGN KEY (Recipe_ID)
            REFERENCES Recipe (ID);

ALTER TABLE Consumption
    ADD CONSTRAINT fk_consumption_slots
        FOREIGN KEY (Slots_ID)
            REFERENCES Slots (ID);

ALTER TABLE Consumption
    ADD CONSTRAINT fk_consumption_article
        FOREIGN KEY (Article_ID)
            REFERENCES Article (ID);

ALTER TABLE Consumption
    ADD CONSTRAINT fk_consumption_recipe
        FOREIGN KEY (Recipe_ID)
            REFERENCES Recipe (ID);

ALTER TABLE Consumption
    ADD CONSTRAINT fk_consumption_person
        FOREIGN KEY (Person_ID)
            REFERENCES Person (ID);

ALTER TABLE Recommended_Daily_Values
    ADD CONSTRAINT fk_recommended_values_article
        FOREIGN KEY (Article_ID)
            REFERENCES Article (ID);
