class TextDocument implements Document{
    private int defaultPage;

    TextDocument() {
        this.defaultPage = 100;
    }

    @Override
    public String print() {
        return("Printing Text Document!");
    }

    @Override
    public int numberOfPages() {
        return defaultPage;
    }
}
