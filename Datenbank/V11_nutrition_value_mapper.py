import pandas as pd # pip install pandas needs to be used for it to work
import math

# ----------------------
# Configuration Section
# ----------------------

# Path to your Excel file
EXCEL_FILE = "PATH TO EXCEL/Schweizer_Nahrwertdatenbank.xlsx"

# Name of the database table
DB_TABLE = "nutritional_values"

# Mapping: Excel column headers to DB column names. Need to have the same Names as in the Excel file, else NULL gets rerturned
excel_to_db_map = {
    # DB Field                       Excel Column
    "article_id":                    "ID",
    "values_per_100g_100ml":         "Bezugseinheit",
    "calories_kcal":                 "Energie, Kalorien (kcal)",
    "protein_g":                     "Protein (g)",
    "carbs_g":                       "Kohlenhydrate, verfügbar (g)",
    "total_fats_g":                  "Fett, total (g)",
    "saturated_fats_g":              "Fettsäuren, gesättigt (g)",
    "monounsaturated_fats_g":        "Fettsäuren, einfach ungesättigt (g)",
    "polyunsaturated_fats_g":        "Fettsäuren, mehrfach ungesättigt (g)",
    "sugars_g":                      "Zucker (g)",
    "fiber_g":                       "Nahrungsfasern (g)",
    "salt_g":                        "Salz (NaCl) (g)",
    "vitamin_c_mg":                  "Vitamin C (Ascorbinsäure) (mg)",
    "vitamin_d_ug":                  "Vitamin D (Calciferol) (µg)",
    "vitamin_a_ug":                  "Vitamin A-Aktivität, RE (µg-RE)",
    "vitamin_b1_thiamin_mg":         "Vitamin B1 (Thiamin) (mg)",
    "vitamin_b2_riboflavin_mg":      "Vitamin B2 (Riboflavin) (mg)",
    "vitamin_b3_niacin_mg":          "Niacin (mg)",
    "vitamin_b5_pantothenic_acid_mg":"Pantothensäure (mg)",
    "vitamin_b6_mg":                 "Vitamin B6 (Pyridoxin) (mg)",
    "vitamin_b7_biotin_ug":          "Biotin (µg)",
    "vitamin_b11_folic_acid_ug":     "Folat (µg)",
    "vitamin_b12_ug":                "Vitamin B12 (Cobalamin) (µg)",
    "vitamin_e_mg":                  "Vitamin E (α-Tocopherol) (mg)",
    "vitamin_k_ug":                  "Vitamin K (µg)",
    "calcium_mg":                    "Calcium (Ca) (mg)",
    "iron_mg":                       "Eisen (Fe) (mg)",
    "arsenic_ug":                    "Arsen (µg)",
    "boron_mg":                      "Bor (mg)",
    "choline_mg":                    "Cholin (mg)",
    "chloride_mg":                   "Chlorid (Cl) (mg)",
    "chromium_mg":                   "Chrom (mg)",
    "cobalt_ug":                     "Kobalt (µg)",
    "copper_mg":                     "Kupfer (mg)",
    "fluoride_mg":                   "Fluorid (mg)",
    "iodine_ug":                     "Jod (I) (µg)",
    "magnesium_mg":                  "Magnesium (Mg) (mg)",
    "manganese_mg":                  "Mangan (mg)",
    "molybdenum_ug":                 "Molybdän (µg)",
    "phosphorus_mg":                 "Phosphor (P) (mg)",
    "potassium_mg":                  "Kalium (K) (mg)",
    "rubidium_ug":                   "Rubidium (µg)",
    "selenium_ug":                   "Selen (Se) (µg)",
    "silicon_mg":                    "Silizium (mg)",
    "sulfur_mg":                     "Schwefel (mg)",
    "tin_mg":                        "Zinn (mg)",
    "vanadium_ug":                   "Vanadium (µg)",
    "zinc_mg":                       "Zink (Zn)  (mg)",
    "water_ml":                      "Wasser (g)",
    "alcohol_ml":                    "Alkohol (g)"
}

# ------------------------------------------
# Load Excel and Generate SQL Insert Statements
# ------------------------------------------

df = pd.read_excel(EXCEL_FILE)

# We'll only consider columns that are in the map and present in the DataFrame
available_columns = [col for col in excel_to_db_map.values() if col in df.columns]
if not available_columns:
    raise ValueError("No matching columns found between Excel file and DB mapping.")

# Filter dataframe to only these columns
df = df[available_columns]

# Write to Output SQL file
with open("PATH/3_INSERT nutritional_values_complex.sql", "w", encoding="utf-8") as outfile:
    outfile.write("SET search_path TO %DATABASE%;\n") #define DATABASE here the target DB or Comment it out if nod needed
    
    for _, row in df.iterrows():
        columns = []
        values = []
        
        for db_col, excel_col in excel_to_db_map.items():
            if excel_col in available_columns:
                raw_value = row[excel_col]

                # Handle NULL values: If NaN or empty cell, set as NULL
                if pd.isna(raw_value) or (isinstance(raw_value, str) and raw_value.strip() == ""):
                    val = "NULL"
                else:
                    # Determine if numeric
                    if isinstance(raw_value, (int, float)) and not math.isnan(raw_value):
                        # Numeric value
                        val = str(raw_value)
                    else:
                        # Treat as string and quote it
                        val = f"'{str(raw_value).strip()}'"
                
                columns.append(db_col)
                values.append(val)

        # Build the INSERT statement for the SQL. The File need to be edited, second line needs to be removed manually, didnt fix that
        sql = f"INSERT INTO {DB_TABLE} ({', '.join(columns)}) VALUES ({', '.join(values)});"
        outfile.write(sql + "\n")

print("Insert statements written to SQL file.")