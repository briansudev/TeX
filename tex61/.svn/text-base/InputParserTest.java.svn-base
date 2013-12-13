package tex61;

import java.util.Scanner;
import java.io.StringReader;
import java.io.PrintWriter;
import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests of InputParser.
 * @author Brian Su
 */
public class InputParserTest {

    @Test
    public void testBT() {
        Scanner input2 = new Scanner(" hi");
        input2.findWithinHorizon(InputParser.INPUT_PATTERN, 0);
        assertNotNull(input2.match().group(1));
    }

    @Test
    public void testESC() {
        Scanner input2 = new Scanner("\\{");
        input2.findWithinHorizon(InputParser.INPUT_PATTERN, 0);
        assertNotNull(input2.match().group(4));
    }

    @Test
    public void testCOMMANDARG() {
        Scanner input2 = new Scanner("\\parskip{5}");
        input2.findWithinHorizon(InputParser.INPUT_PATTERN, 0);
        assertNotNull(input2.match().group(5));
        assertNotNull(input2.match().group(6));
        input2.findWithinHorizon(InputParser.BALANCED_TEXT, 0);
        assertNotNull(input2.match().group(1));
    }

    @Test
    public void testTEXT() {
        Scanner input2 = new Scanner("hello");
        input2.findWithinHorizon(InputParser.INPUT_PATTERN, 0);
        assertNotNull(input2.match().group(7));
    }

    @Test
    public void testParseInt() {
        initialize();
        assertEquals(parser.parseInt("5"), 5);
        assertEquals(parser.parseInt("100"), 100);
        assertEquals(parser.parseInt("-100"), -100);
    }

    @Test(expected = FormatException.class)
    public void testParseInt2() {
        initialize();
        parser.parseInt("s");
        parser.parseInt("endnote");
    }

    @Test
    public void nullArgTest() {
        initialize();
        parser.nullArg(null);
        parser.notNullArg("10");
    }

    @Test(expected = FormatException.class)
    public void nullArgTestError() {
        initialize();
        parser.nullArg("10");
        parser.notNullArg(null);
    }

    /** Initialize all required stuff. */
    public void initialize() {
        input = new StringReader("blahblahblah");
        output = new PrintWriter(System.out);
        cntrl = new Controller(output);
        parser = new InputParser(input, cntrl);
    }

    /** Test StringReader. */
    private StringReader input;
    /** Test PrintWriter. */
    private PrintWriter output;
    /** Test Controller. */
    private Controller cntrl;
    /** Test InputParser. */
    private InputParser parser;
}
