class PresentationDocument implements Document{
    private int PresentationDefault;

    /**
     * Constructor
     */
    PresentationDocument() {
        PresentationDefault = 20;
    }

    /**
     * prints
     * @return string
     */
    @Override
    public String print() {
        return Document.super.print();
    }

    /**
     * outputs number of pages
     * @return
     */
    @Override
    public int numberOfPages() {
        return PresentationDefault;
    }
}
