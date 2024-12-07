CREATE TABLE Artikel
(
    ID             SERIAL PRIMARY KEY,
    Artikel_Nr     VARCHAR(100) NOT NULL,
    Barcode        VARCHAR(100),
    Hersteller_ID  INT,
    Messeinheit_ID INT
);

CREATE TABLE Rezept
(
    ID   SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL
);

CREATE TABLE Zutaten
(
    ID         SERIAL PRIMARY KEY,
    Artikel_ID INT,
    Rezept_ID  INT
);

CREATE TABLE Nährwerte
(
    ID                                 SERIAL PRIMARY KEY,
    Werte_pro_100g_100ml               VARCHAR(50),
    Artikel_ID                         INT,
    Kalorien_kcal                      DECIMAL,
    Eiweiss_g                          DECIMAL,
    Kohlenhydrate_g                    DECIMAL,
    Gesamtfettigkeit_g                 DECIMAL,
    Gesättigte_Fettsäuren_g            DECIMAL,
    Einfach_ungesättigte_Fettsäuren_g  DECIMAL,
    Mehrfach_ungesättigte_Fettsäuren_g DECIMAL,
    Zucker_g                           DECIMAL,
    Balaststoffe_g                     DECIMAL,
    Salz_g                             DECIMAL,
    Vitamin_C_mg                       DECIMAL,
    Vitamin_D_ug                       DECIMAL,
    Vitamin_A_ug                       DECIMAL,
    Vitamin_B1_Thiamin_mg              DECIMAL,
    Vitamin_B2_Riboflavin_mg           DECIMAL,
    Vitamin_B3_Niacin_mg               DECIMAL,
    Vitamin_B5_Pantothensäure_mg       DECIMAL,
    Vitamin_B6_mg                      DECIMAL,
    Vitamin_B7_Biotin_ug               DECIMAL,
    Vitamin_B11_Folsäure_ug            DECIMAL,
    Vitamin_B12_ug                     DECIMAL,
    Vitamin_E_mg                       DECIMAL,
    Vitamin_K_ug                       DECIMAL,
    Kalzium_mg                         DECIMAL,
    Eisen_mg                           DECIMAL,
    Arsen_ug                           DECIMAL,
    Bor_mg                             DECIMAL,
    Cholin_mg                          DECIMAL,
    Chlorid_mg                         DECIMAL,
    Chrom_mg                           DECIMAL,
    Kobalt_ug                          DECIMAL,
    Kupfer_mg                          DECIMAL,
    Fluorid_mg                         DECIMAL,
    Jod_ug                             DECIMAL,
    Magnesium_mg                       DECIMAL,
    Mangan_mg                          DECIMAL,
    Molybdän_ug                        DECIMAL,
    Phosphor_mg                        DECIMAL,
    Kalium_mg                          DECIMAL,
    Rubidium_ug                        DECIMAL,
    Selen_ug                           DECIMAL,
    Silizium_mg                        DECIMAL,
    Schwefel_mg                        DECIMAL,
    Zinn_mg                            DECIMAL,
    Vanadium_ug                        DECIMAL,
    Zink_mg                            DECIMAL,
    Wasser_ml                          DECIMAL,
    Alkohol_ml                         DECIMAL
);

CREATE TABLE Messeinheiten_Artikel
(
    ID         SERIAL PRIMARY KEY,
    Artikel_ID INT,
    Einheit    VARCHAR(100)
);

CREATE TABLE Hersteller
(
    ID   SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Land VARCHAR(100)
);

CREATE TABLE Portion
(
    ID                 SERIAL PRIMARY KEY,
    Artikel_ID         INT,
    Portionsgrösse_Typ VARCHAR(100),
    Menge              DECIMAL
);

CREATE TABLE Portionsgrössen
(
    ID         SERIAL PRIMARY KEY,
    Portion_ID INT,
    Artikel_ID INT,
    Rezept_ID  INT,
    Menge      DECIMAL
);

CREATE TABLE Lebensmittel_Kategorie
(
    ID   SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Typ  VARCHAR(100)
);

CREATE TABLE Slots
(
    ID   SERIAL PRIMARY KEY,
    Name VARCHAR(100) NOT NULL
);

CREATE TABLE Konsumation
(
    ID         SERIAL PRIMARY KEY,
    Slots_ID   INT,
    Artikel_ID INT,
    Rezept_ID  INT
);

CREATE TABLE Person
(
    ID         SERIAL PRIMARY KEY,
    Name       VARCHAR(255) NOT NULL,
    Alter      INT,
    Geschlecht VARCHAR(10),
    Grösse     DECIMAL,
    Gewicht    DECIMAL,
    Allergien  VARCHAR(255)
);

CREATE TABLE Empfehlungen_Tageswerte
(
    ID                                 SERIAL PRIMARY KEY,
    Artikel_ID                         INT,
    Kalorien_kcal                      DECIMAL,
    Eiweiss_g                          DECIMAL,
    Kohlenhydrate_g                    DECIMAL,
    Gesamtfettigkeit_g                 DECIMAL,
    Gesättigte_Fettsäuren_g            DECIMAL,
    Einfach_ungesättigte_Fettsäuren_g  DECIMAL,
    Mehrfach_ungesättigte_Fettsäuren_g DECIMAL,
    Zucker_g                           DECIMAL,
    Balaststoffe_g                     DECIMAL,
    Salz_g                             DECIMAL,
    Vitamin_C_mg                       DECIMAL,
    Vitamin_D_ug                       DECIMAL,
    Vitamin_A_ug                       DECIMAL,
    Vitamin_B1_Thiamin_mg              DECIMAL,
    Vitamin_B2_Riboflavin_mg           DECIMAL,
    Vitamin_B3_Niacin_mg               DECIMAL,
    Vitamin_B5_Pantothensäure_mg       DECIMAL,
    Vitamin_B6_mg                      DECIMAL,
    Vitamin_B7_Biotin_ug               DECIMAL,
    Vitamin_B11_Folsäure_ug            DECIMAL,
    Vitamin_B12_ug                     DECIMAL,
    Vitamin_E_mg                       DECIMAL,
    Vitamin_K_ug                       DECIMAL,
    Kalzium_mg                         DECIMAL,
    Eisen_mg                           DECIMAL,
    Arsen_ug                           DECIMAL,
    Bor_mg                             DECIMAL,
    Cholin_mg                          DECIMAL,
    Chlorid_mg                         DECIMAL,
    Chrom_mg                           DECIMAL,
    Kobalt_ug                          DECIMAL,
    Kupfer_mg                          DECIMAL,
    Fluorid_mg                         DECIMAL,
    Jod_ug                             DECIMAL,
    Magnesium_mg                       DECIMAL,
    Mangan_mg                          DECIMAL,
    Molybdän_ug                        DECIMAL,
    Phosphor_mg                        DECIMAL,
    Kalium_mg                          DECIMAL,
    Rubidium_ug                        DECIMAL,
    Selen_ug                           DECIMAL,
    Silizium_mg                        DECIMAL,
    Schwefel_mg                        DECIMAL,
    Zinn_mg                            DECIMAL,
    Vanadium_ug                        DECIMAL,
    Zink_mg                            DECIMAL,
    Wasser_ml                          DECIMAL,
    Alkohol_ml                         DECIMAL
);

-- Teil 2: Relationen zwischen Tabellen erstellen

ALTER TABLE Artikel
    ADD CONSTRAINT fk_hersteller
        FOREIGN KEY (Hersteller_ID)
            REFERENCES Hersteller (ID);

ALTER TABLE Artikel
    ADD CONSTRAINT fk_messeinheit
        FOREIGN KEY (Messeinheit_ID)
            REFERENCES Messeinheiten_Artikel (ID);

ALTER TABLE Zutaten
    ADD CONSTRAINT fk_artikel
        FOREIGN KEY (Artikel_ID)
            REFERENCES Artikel (ID);

ALTER TABLE Zutaten
    ADD CONSTRAINT fk_rezept
        FOREIGN KEY (Rezept_ID)
            REFERENCES Rezept (ID);

ALTER TABLE Nährwerte
    ADD CONSTRAINT fk_nährwerte_artikel
        FOREIGN KEY (Artikel_ID)
            REFERENCES Artikel (ID);

ALTER TABLE Messeinheiten_Artikel
    ADD CONSTRAINT fk_messeinheiten_artikel
        FOREIGN KEY (Artikel_ID)
            REFERENCES Artikel (ID);

ALTER TABLE Portion
    ADD CONSTRAINT fk_portion_artikel
        FOREIGN KEY (Artikel_ID)
            REFERENCES Artikel (ID);

ALTER TABLE Portionsgrössen
    ADD CONSTRAINT fk_portionsgrössen_portion
        FOREIGN KEY (Portion_ID)
            REFERENCES Portion (ID);

ALTER TABLE Portionsgrössen
    ADD CONSTRAINT fk_portionsgrössen_artikel
        FOREIGN KEY (Artikel_ID)
            REFERENCES Artikel (ID);

ALTER TABLE Portionsgrössen
    ADD CONSTRAINT fk_portionsgrössen_rezept
        FOREIGN KEY (Rezept_ID)
            REFERENCES Rezept (ID);

ALTER TABLE Konsumation
    ADD CONSTRAINT fk_konsumation_slots
        FOREIGN KEY (Slots_ID)
            REFERENCES Slots (ID);

ALTER TABLE Konsumation
    ADD CONSTRAINT fk_konsumation_artikel
        FOREIGN KEY (Artikel_ID)
            REFERENCES Artikel (ID);

ALTER TABLE Konsumation
    ADD CONSTRAINT fk_konsumation_rezept
        FOREIGN KEY (Rezept_ID)
            REFERENCES Rezept (ID);

ALTER TABLE Empfehlungen_Tageswerte
    ADD CONSTRAINT fk_empfehlungen_artikel
        FOREIGN KEY (Artikel_ID)
            REFERENCES Artikel (ID);
