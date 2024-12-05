# Food Tracker Database Deployment

Dieses Repository enthält das Deployment-Skript und das Datenmodell für die PostgreSQL-Datenbank des Food Tracker-Projekts. Es deckt alle wichtigen Tabellen und deren Beziehungen ab, die für die Nachverfolgung von Artikeln, Rezepten, Nährwerten, Portionen und Konsumationszeiten erforderlich sind.

## Inhaltsverzeichnis

1. [Überblick](#überblick)
2. [Datenmodell](#datenmodell)
3. [Deployment-Skript](#deployment-skript)
4. [Integration der Schweizer Nährwertdatenbank](#integration-der-schweizer-nährwertdatenbank)
5. [Anleitung zur Verwendung](#anleitung-zur-verwendung)
6. [Kontakt](#kontakt)

---

## Überblick

Die Datenbank ist für die Verwaltung von Lebensmitteln, Rezepten, Verbrauch und den dazugehörigen Nährwertangaben konzipiert. Sie beinhaltet folgende Kernfunktionen:
- Verfolgung von Artikeln und deren Nährwerte.
- Erstellung von Rezepten und deren Zutaten.
- Verwaltung von Portionen und Konsumationszeiten.
- Erfassung personenbezogener Daten wie Allergien, Gewicht und empfohlene Tageswerte.

---

## Datenmodell

Das Datenmodell definiert die Struktur und die Beziehungen zwischen den Tabellen. Die wichtigsten Tabellen umfassen:

- **Artikel**: Informationen über Lebensmittel, wie Name, Barcode und Hersteller.
- **Rezept**: Verwaltung von Rezepten und deren Zutaten.
- **Nährwerte**: Details zu Kalorien, Makronährstoffen und Vitaminen.
- **Portionen**: Portionstypen und -größen.
- **Konsumation**: Nachverfolgung der Essenszeiten.
- **Personen**: Nutzerinformationen, einschließlich Allergien und Gewicht.

Eine vollständige Übersicht des Modells finden Sie in der Datei `Tablestructure Food Tracker.txt`.

---

## Deployment-Skript

Das Deployment-Skript ist in der Datei `Postgres DB Deployment Script v2.txt` enthalten. Es führt die folgenden Schritte aus:

1. **Erster Schritt: Erstellen der Tabellen**:
    - Jede Tabelle wird mit ihren entsprechenden Spalten und Datentypen erstellt.
2. **Zweiter Schritt: Definieren der Beziehungen**:
    - Fremdschlüssel werden hinzugefügt, um die Beziehungen zwischen Tabellen herzustellen.
3. **Dritter Schritt: Artikel hinzufügen**:
    - Artikel werden in der Tabelle Article und Nutrition_Values importiert. Achtung den Pfad der .csv anpassen!


---

## Integration der Schweizer Nährwertdatenbank

Dieses Projekt verwendet die **Schweizer Nährwertdatenbank** als Referenz für Nährwertinformationen. Diese Datenbank wird bereitgestellt vom:

Bundesamt für Lebensmittelsicherheit und Veterinärwesen BLV  
Schwarzenburgstrasse 155  
3003 Bern  
Schweiz
(Stand 17.08.2023)
https://naehrwertdaten.ch/de/downloads/

### Enthaltene Informationen
Die Datenbank enthält:
- **Generische Lebensmittel**: Allgemeine Lebensmittelkategorien und deren Nährwerte.

Die relevanten Daten aus der Schweizer Nährwertdatenbank sind in der Tabelle `Nutritional_Values` der Food Tracker-Datenbank integriert.

---

## Anleitung zur Verwendung

1. **Voraussetzungen**:
   - PostgreSQL muss installiert sein.
   - Ein Benutzer mit den erforderlichen Berechtigungen zur Erstellung von Datenbanken und Tabellen.

2. **Deployment**:
   - Führen Sie das Deployment-Skript `Postgres DB Deployment Script v2.txt` mit einem SQL-Client oder über die Kommandozeile aus:
     ```bash
     psql -U <Benutzername> -d <Datenbankname> -f "Postgres DB Deployment Script v2.txt"
     ```
   - Stellen Sie sicher, dass alle Tabellen erfolgreich erstellt wurden.

3. **Import der Nährwertdaten**:
   - Die Tabellen aus der Schweizer Nährwertdatenbank können in die Tabellen der Food Tracker-Datenbank importiert werden. Der Schritt 3 führt das aus, dafür muss aber ein Pfad angegeben werden.

4. **Testen**:
   - Verifizieren Sie die Tabellenstruktur und Beziehungen mit SQL-Befehlen wie:
     ```sql
     \dt
     ```