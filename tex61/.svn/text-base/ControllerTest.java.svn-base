package tex61;

import java.io.PrintWriter;
import org.junit.Test;
import static org.junit.Assert.*;

/** Controller Junit.
 * @author Brian Su
 */

public class ControllerTest {


    @Test
    public void test1() {
        initialize();
        cntrl.addText("hello hilfinger.");
        cntrl.setEndnoteMode();
        cntrl.formatEndnote("srry Knuth.");
        assertTrue(cntrl.getEL().isEmpty());
    }

    @Test
    public void test2() {
        initialize();
        cntrl.formatEndnote("hello there.");
        assertTrue(!cntrl.getEL().isEmpty());
        assertEquals(cntrl.getRef(), 2);
    }

    @Test
    public void test3() {
        initialize();
        cntrl.setEndnoteMode();
        assertTrue(cntrl.getEDM());
        cntrl.setNormalMode();
        assertTrue(!cntrl.getEDM());
    }

    /** Initialize stuff. */
    public void initialize() {
        output = new PrintWriter(System.out);
        cntrl = new Controller(output);
    }

    /** Test Controller. */
    private Controller cntrl;
    /** Test PrintWriter. */
    private PrintWriter output;
}
