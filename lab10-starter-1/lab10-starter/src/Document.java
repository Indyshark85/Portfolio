interface Document {

    /**
     * Returns the number of pages in this document.
     */
    int numberOfPages();

    /**
     * Returns a string representing that the Document
     * is being printed.
     */
    default String print() {
        return "Printing the document!";
    }
}
