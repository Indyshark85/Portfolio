import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {
    @Test
    void Test(){
        Document SpreadS = new SpreadsheetDocument();
        Document TextDoc = new TextDocument();
        Document Presentation = new PresentationDocument();
        List<Document> documents = Arrays.asList(
                new TextDocument(),
                new SpreadsheetDocument(),
                new PresentationDocument()
        );

        assertAll(
                () -> assertEquals(50,SpreadS.numberOfPages()),
                () -> assertEquals("Printing spreadsheet document!",SpreadS.print()),
                () -> assertEquals(20,Presentation.numberOfPages()),
                () -> assertEquals("Printing the document!",Presentation.print()),
                () -> assertEquals(100,TextDoc.numberOfPages()),
                () -> assertEquals("Printing Text Document!",TextDoc.print()),
                () -> assertEquals(OptionalDouble.of(56),PrintingOffice.avgPages(documents))
        );
    }

}
