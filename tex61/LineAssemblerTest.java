package tex61;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.StringWriter;

/** Junits for LineAssembler.
 * @author Brian Su
 */
public class LineAssemblerTest {

    public LineAssemblerTest() {
        initialize();
    }

    @Test
    public void test1() {
        mainText.addLine("hello");
        mainText.setIndentation(5);
        mainText.setParIndentation(10);
        mainText.setParIndentation(-5);
        mainText.setTextWidth(80);
        mainText.setTextHeight(50);
    }

    @Test(expected = FormatException.class)
    public void test2() {
        mainText.addLine("hello");
        mainText.setIndentation(-1);
        mainText.setParIndentation(-100);
        mainText.setTextWidth(-3);
        mainText.setTextHeight(-50);
    }


    @Test
    public void test3() {
        initialize();
        String text = "HILFINGER ";
        mainText.addText(text);
        assertEquals("HILFINGER ", text, mainText.getWord());
        assertTrue(mainText != null);
        assertTrue(mainText.getFill());
        assertTrue(mainText.getJustify());
        assertTrue(mainText.getFirstLine());
        assertEquals(mainText.getWord(), "HILFINGER ");
    }

    @Test
    public void test4() {
        initialize();
        mainText.addText("hilfinger.");
        mainText.finishWord();
        assertNull(mainText.getWord());
    }

    @Test(expected = FormatException.class)
    public void test5() {
        initialize();
        mainText.addText("hallejuh!");
        mainText.finishWord();
        mainText.addLine("hilfinger!!");
    }

    @Test
    public void test6() {
        initialize();
        mainText.addText("hilfinger!! is awesomeeee");
        mainText.finishWord();
        mainText.endParagraph();
        assertTrue(mainText.getWords().isEmpty());
        assertEquals(mainText.getChars(), 0);
    }

    @Test
    public void test7() {
        initialize();
        mainText.addText("may the odds be in my favor.");
        mainText.finishWord();
        mainText.setFill(false);
        mainText.newLine();
        assertTrue(mainText.getWords().isEmpty());
        mainText.endParagraph();
        mainText.addWord("it is difficult to say what is"
                         + "impossible");
        mainText.endParagraph();
        assertTrue(mainText.getWords().isEmpty());
    }

    /** Test arrayList. */
    private ArrayList<String> words;
    /** Test StringWriter. */
    private StringWriter output;
    /** Test PrintWriter. */
    private PrintWriter writer;
    /** Test PagePrinter. */
    private PagePrinter printer;
    /**Test Line Assemblers. */
    private LineAssembler mainText, endnote;
    /** Test Page Collector. */
    private PageCollector collector;

    /** Initialize all necessary stuff. */
    private void initialize() {
        words = new ArrayList<String>();
        output = new StringWriter();
        writer = new PrintWriter(output);
        printer = new PagePrinter(writer);
        mainText = new LineAssembler(new PagePrinter(writer));
        endnote = new LineAssembler(new PageCollector(words));
        collector = new PageCollector(words);
    }

}
