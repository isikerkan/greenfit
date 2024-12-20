# Food Tracker Database Deployment

This repository contains deployment scripts for the PostgreSQL database with more complex logic.

## Table of Contents

1. [Overview](#overview)
2. [Deployment Script](#deployment-script)
3. [Integration of the Swiss Nutrient Database](#integration-of-the-swiss-nutrient-database)
4. [Usage Instructions](#usage-instructions)
5. [Mapper](#mapper)

---

## Overview

The database is designed for managing foods, recipes, consumption, and the associated nutritional information. It includes the following core functions:
- Tracking items and their nutritional values.
- Creating recipes and their ingredients.
- Managing portions and consumption times.

---

## Data Model

The data model defines the structure and relationships between the tables. The main tables include:

- **Items**: Information about foods, such as name, barcode, and manufacturer.
- **Nutritional Values**: Details on calories, macronutrients, and vitamins.
- **Manufacturers**: 

---

## Deployment Script

The deployment scripts should be deployed in the numbered order.

---

## Integration of the Swiss Nutrient Database

This project uses the **Swiss Nutrient Database** as a reference for nutritional information. This database is provided by:

Federal Departmen of Home Affairs FDHA
Federal Food Safety and Veterinary Office FSVO  
Schwarzenburgstrasse 155  
3003 Bern
Switzerland
(As of 17.08.2023)  
https://naehrwertdaten.ch/en/downloads/

### Included Information
The database contains:
- **Generic Foods**: General food categories and their nutritional values.

The relevant data from the Swiss Nutrient Database is integrated into the `Nutritional_Values` table of the Food Tracker database.

---

## Usage Instructions

1. **Prerequisites**:
   - PostgreSQL must be installed.
   - A user with the necessary permissions to create databases and tables.

2. **Deployment**:
   - Run the deployment script `Postgres DB Deployment Script v2.txt` with an SQL client or via the command line:
     ```bash
     psql -U <username> -d <database_name> -f "Postgres DB Deployment Script v2.txt"
     ```
   - Ensure that all tables are created successfully.

3. **Import Nutritional Data**:
   - The tables from the Swiss Nutrient Database can be imported into the tables of the Food Tracker database. Step 3 performs this, but a path must be specified.

4. **Testing**:
   - Verify the table structure and relationships with SQL commands such as:
     ```sql
     \dt
     ```
---

## Mapper

The repository contains a Python mapper that creates database inserts from a modified Excel of the **Swiss Nutrient Database**.
