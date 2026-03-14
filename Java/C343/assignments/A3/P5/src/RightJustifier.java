import java.util.*;

/** Interface for paragraph right-justification under the L1 ugliness spec. */
public interface RightJustifier {
    /**
     * Given words, line length L, and ideal blank length b,
     * return the justified lines satisfying:
     *  - blanks strictly positive (>= 1 space) whenever a line has >= 2 words,
     *  - last line charged only if average blank < b,
     *  - otherwise charge sum |blank_i - b| which equals k * |avg - b|.
     */
    List<String> justify(List<String> words, int L, double b); // TODO: implement in DPJustifier
}
