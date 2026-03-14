import javax.print.Doc;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

class PrintingOffice{

    /**
     * calculates the average pages in all the documents.
     *
     * @param lodocs List.
     * @return OptionalDouble.
     */
    static OptionalDouble avgPages(List<Document> lodocs) {
        int counter=0;
        if(lodocs.isEmpty()){
            return (OptionalDouble.empty()) ;
        }
        for(Document page: lodocs){
            counter += page.numberOfPages();
        }
        OptionalDouble average = OptionalDouble.of(((double) counter / lodocs.size()));
        return (average);

    }

    /**
     * prints all the doc in the list.
     *
     * @param lodocs list.
     */
    static void printDocuments(List<Document> lodocs) {
        for(Document page: lodocs){
            page.print();
        }
    }
}
