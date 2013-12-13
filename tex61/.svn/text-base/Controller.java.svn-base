package tex61;

import java.util.ArrayList;
import java.io.PrintWriter;

import static tex61.FormatException.reportError;

/** Receives (partial) words and commands, performs commands, and
 *  accumulates and formats words into lines of text, which are sent to a
 *  designated PageAssembler.  At any given time, a Controller has a
 *  current word, which may be added to by addText, a current list of
 *  words that are being accumulated into a line of text, and a list of
 *  lines of endnotes.
 *  @author Brian Su
 */
class Controller {

    /** A new Controller that sends formatted output to OUT. */
    Controller(PrintWriter out) {
        _mainText = new LineAssembler(new PagePrinter(out));
        _endnotes = new LineAssembler(new PageCollector(_endnotesList));
        setNormalMode();
        _refNum = 1;

        _mainText.setTextWidth(Defaults.TEXT_WIDTH);
        _mainText.setTextHeight(Defaults.TEXT_HEIGHT);
        _mainText.setParSkip(Defaults.PARAGRAPH_SKIP);
        _mainText.setIndentation(Defaults.INDENTATION);
        _mainText.setParIndentation(Defaults.PARAGRAPH_INDENTATION);

        _endnotes.setTextWidth(Defaults.ENDNOTE_TEXT_WIDTH);
        _endnotes.setParSkip(Defaults.ENDNOTE_PARAGRAPH_SKIP);
        _endnotes.setIndentation(Defaults.ENDNOTE_INDENTATION);
        _endnotes.setParIndentation(Defaults.ENDNOTE_PARAGRAPH_INDENTATION);
    }

    /** Add TEXT to the end of the word of formatted text currently
     *  being accumulated. */
    void addText(String text) {
        _current.addText(text);
    }

    /** Finish any current word of text and, if present, add to the
     *  list of words for the next line.  Has no effect if no unfinished
     *  word is being accumulated. */
    void endWord() {
        _current.finishWord();
    }

    /** Finish any current word of formatted text and process an end-of-line
     *  according to the current formatting parameters. */
    void addNewline() {
        endWord();
        _current.newLine();
    }

    /** Finish any current word of formatted text, format and output any
     *  current line of text, and start a new paragraph. */
    void endParagraph() {
        _current.endParagraph();
    }

    /** If valid, process TEXT into an endnote, first appending a reference
     *  to it to the line currently being accumulated. */
    void formatEndnote(String text) {
        if (!_endnoteMode) {
            String ref = "[" + _refNum + "]";
            _current.addText(ref);
            setEndnoteMode();
            InputParser endnoteParser = new InputParser(text, this);
            _current.addText(ref + " ");
            endnoteParser.process();
            _refNum += 1;
            setNormalMode();
        } else {
            reportError("Endnotes are not allowed to be in endnotes");
        }
    }

    /** Set the current text height (number of lines per page) to VAL, if
     *  it is a valid setting.  Ignored when accumulating an endnote. */
    void setTextHeight(int val) {
        if (!_endnoteMode) {
            _current.setTextHeight(val);
        }
    }

    /** Set the current text width (width of lines including indentation)
     *  to VAL, if it is a valid setting. */
    void setTextWidth(int val) {
        _current.setTextWidth(val);
    }

    /** Set the current text indentation (number of spaces inserted before
     *  each line of formatted text) to VAL, if it is a valid setting. */
    void setIndentation(int val) {
        _current.setIndentation(val);
    }

    /** Set the current paragraph indentation (number of spaces inserted before
     *  first line of a paragraph in addition to indentation) to VAL, if it is
     *  a valid setting. */
    void setParIndentation(int val) {
        _current.setParIndentation(val);
    }

    /** Set the current paragraph skip (number of blank lines inserted before
     *  a new paragraph, if it is not the first on a page) to VAL, if it is
     *  a valid setting. */
    void setParSkip(int val) {
        _current.setParSkip(val);
    }

    /** Iff ON, begin filling lines of formatted text. */
    void setFill(boolean on) {
        _current.setFill(on);
    }

    /** Iff ON, begin justifying lines of formatted text whenever filling is
     *  also on. */
    void setJustify(boolean on) {
        _current.setJustify(on);
    }

    /** Finish the current formatted document or endnote (depending on mode).
     *  Formats and outputs all pending text. */
    void close() {
        endParagraph();
        if (!_endnoteMode) {
            writeEndnotes();
        }
    }

    /** Start directing all formatted text to the endnote assembler. */
    void setEndnoteMode() {
        _current = _endnotes;
        _endnoteMode = true;
    }

    /** Return to directing all formatted text to _mainText. */
    void setNormalMode() {
        _current = _mainText;
        _endnoteMode = false;
    }

    /** Write all accumulated endnotes to _mainText. */
    private void writeEndnotes() {
        for (int i = 0; i < _endnotesList.size(); i++) {
            _mainText.addLine(_endnotesList.get(i));
        }
    }

    /** True iff we are currently processing an endnote. */
    private boolean _endnoteMode;
    /** Number of next endnote. */
    private int _refNum;
    /** A LineAssembler that assembles endnotes. */
    private LineAssembler _endnotes;
    /** A LineAssembler that assembles non-endnote text. */
    private LineAssembler _mainText;
    /** A list of properly formated endnotes that will be passed
     * to _MAINTEXT after all non-endnote text has been output to
     * _MAINTEXT. */
    private ArrayList<String> _endnotesList = new ArrayList<String>();
    /** A pointer that points to the current Line Assembler used
     * to assemble text. If the text is an endnote, _CURRENT points
     * to _ENDNOTES. Otherwise, it points to _MAINTEXT. */
    private LineAssembler _current;

    /** Return _ENDNOTESLIST. */
    public ArrayList<String> getEL() {
        return _endnotesList;
    }

    /** Return _REFNUM. */
    public int getRef() {
        return _refNum;
    }

    /** Return _ENDNOTEMODE. */
    public boolean getEDM() {
        return _endnoteMode;
    }
}

