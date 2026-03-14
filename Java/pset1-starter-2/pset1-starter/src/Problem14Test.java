import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class   Problem14Test {

    @Test
    void testProblem14() {
        assertAll(
                () -> assertEquals(true, Problem14.isValidIpv4("192.168.1.244")),
                () -> assertEquals(true, Problem14.isValidIpv4("149.165.192.52")),
                () -> assertEquals(false, Problem14.isValidIpv4("192.168.1.256")),
                () -> assertEquals(false, Problem14.isValidIpv4("192.168.1201.23")),
                () -> assertEquals(false, Problem14.isValidIpv4("192.168.1201.ABC")),
                () -> assertEquals(false, Problem14.isValidIpv4("ABC.DEF.GHI")),
                () -> assertEquals(false, Problem14.isValidIpv4("192.168.1A6.201"))
        );

    }
}
