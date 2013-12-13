package tex61;

import static tex61.FormatException.error;

/** A PageAssembler accepts complete lines of text (minus any
 *  terminating newlines) and turns them into pages, adding form
 *  feeds as needed.  It prepends a form feed (Control-L  or ASCII 12)
 *  to the first line of each page after the first.  By overriding the
 *  'write' method, subtypes can determine what is done with
 *  the finished lines.
 *  @author Brian Su
 */
abstract class PageAssembler {

    /** Create a new PageAssembler that sends its output to OUT.
     *  Initially, its text height is unlimited. It prepends a form
     *  feed character to the first line of each page except the first. */
    PageAssembler() {
        _numLines = 0;
        _textHeight = Integer.MAX_VALUE;
        _firstPage = true;
    }

    /** Add LINE to the current page, starting a new page with it if
     *  the previous page is full. A null LINE indicates a skipped line,
     *  and has no effect at the top of a page. */
    void addLine(String line) {
        if (_numLines == 0) {
            if (!_firstPage) {
                if (line != null) {
                    write("\f" + line);
                    _numLines++;
                }
            } else {
                if (line != null) {
                    write(line);
                    _numLines++;
                }
            }
        } else {
            if (_numLines < _textHeight) {
                if (line == null) {
                    line = "";
                }
                write(line);
                _numLines++;
            } else {
                _numLines = 0;
                _firstPage = false;
                if (line != null) {
                    write("\f" + line);
                    _numLines++;
                }
            }
        }
    }

    /** Set text height to VAL, where VAL > 0. */
    void setTextHeight(int val) {
        if (val >= 0) {
            _textHeight = val;
        } else {
            throw error("Text height must be positive.");
        }
    }

    /** Perform final disposition of LINE, as determined by the
     *  concrete subtype. */
    abstract void write(String line);

    /** The maximum number of lines in a page. */
    private int _textHeight;
    /** The current number of lines on a page. */
    private int _numLines;
    /** True if the current page being assembled is the first page. */
    private boolean _firstPage;

    /** Return _FIRSTPAGE. */
    public boolean getFirst() {
        return _firstPage;
    }
}
