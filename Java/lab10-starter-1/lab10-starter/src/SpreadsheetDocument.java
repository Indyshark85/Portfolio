class SpreadsheetDocument implements Document {
    private int SpreadSheetPageDefault;

    SpreadsheetDocument() {
        SpreadSheetPageDefault = 50;
    }

    @Override
    public String print() {
        return ("Printing spreadsheet document!");
    }

    @Override
    public int numberOfPages() {
        return SpreadSheetPageDefault;
    }
}
