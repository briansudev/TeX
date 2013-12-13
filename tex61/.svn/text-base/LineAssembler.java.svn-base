package tex61;

import java.util.ArrayList;

import static tex61.FormatException.error;

/** An object that receives a sequence of words of text and formats
 *  the words into filled and justified text lines that are sent to a receiver.
 *  @author Brian Su
 */
class LineAssembler {

    /** A new, empty line assembler with default settings of all
     *  parameters, sending finished lines to PAGES. */
    LineAssembler(PageAssembler pages) {
        _pages = pages;
        _currentWord = null;
        _words = new ArrayList<String>();
        _chars = 0;
        _fill = true;
        _justify = true;
        _firstLine = true;
    }

    /** Add TEXT to the word currently being built. */
    void addText(String text) {
        _currentWord = (_currentWord != null) ? _currentWord + text : text;
    }

    /** Finish the current word, if any, and add to words being accumulated. */
    void finishWord() {
        if (_currentWord != null) {
            addWord(_currentWord);
        }
    }

    /** Add WORD to the formatted text. If _FILL, then if the result of adding
     * the word to the line would make the line longer than _TEXTWIDTH, then
     * output the current line and then add the word to a new line. The number
     * of characters in the line after adding the word, LINENUMCHARS, is equal
     * to the number of indents, including _PARINDENT if _FIRSTLINE, plus the
     * number of characters in the line, _CHARS, plus the size of the word to be
     * added and 1 for the space between the previous word and WORD, and the
     * minimum number of spaces that the line could have, which is the number
     * words in the line minus 1. */
    void addWord(String word) {
        if (_fill) {
            int indents = (_firstLine) ? _parIndent + _indent : _indent;
            int lineNumChars = indents + _chars + _words.size() + word.length();
            if (lineNumChars > _textWidth) {
                outputLine(false);
            }
        }
        _words.add(_currentWord);
        _chars += word.length();
        _currentWord = null;
    }

    /** Add LINE to our output, with no preceding paragraph skip.  There must
     *  not be an unfinished line pending. */
    void addLine(String line) {
        if (_words.isEmpty()) {
            _pages.addLine(line);
        } else {
            throw error("There must not be an unfinished line pending.");
        }
    }

    /** Set the current indentation to VAL. VAL >= 0. */
    void setIndentation(int val) {
        if (val >= 0) {
            _indent = val;
        } else {
            throw error("Indentation must be greater than 0.");
        }
    }

    /** Set the current paragraph indentation to VAL. VAL >= 0. */
    void setParIndentation(int val) {
        if (val >= -_indent) {
            _parIndent = val;
        } else {
            throw error("Paragraph indentation must be greater than 0.");
        }
    }

    /** Set the text width to VAL, where VAL >= 0. */
    void setTextWidth(int val) {
        if (val >= 0) {
            _textWidth = val;
        } else {
            throw error("Text width must be greater than 0.");
        }
    }

    /** Iff ON, set fill mode. */
    void setFill(boolean on) {
        _fill = on;
    }

    /** Iff ON, set justify mode (which is active only when filling is
     *  also on). */
    void setJustify(boolean on) {
        _justify = on;
    }

    /** Set paragraph skip to VAL.  VAL >= 0. */
    void setParSkip(int val) {
        if (val >= 0) {
            _parSkip = val;
        } else {
            throw error("Paragraph skip must be greater than 0.");
        }
    }

    /** Set page height to VAL > 0. */
    void setTextHeight(int val) {
        if (val >= 0) {
            _pages.setTextHeight(val);
        } else {
            throw error("Text height must be greater than 0.");
        }
    }

    /** Process the end of the current input line.  No effect if
     *  current line accumulator is empty or in fill mode.  Otherwise,
     *  adds a new complete line to the finished line queue and clears
     *  the line accumulator. */
    void newLine() {
        if (!_fill) {
            outputLine(false);
        }
    }

    /** If there is a current unfinished paragraph pending, close it
     *  out and start a new one. */
    void endParagraph() {
        finishWord();
        outputLine(true);
    }

    /** Transfer contents of _words to _pages, adding INDENT characters of
     *  indentation, and a total of SPACES spaces between words, evenly
     *  distributed.  Assumes _words is not empty.  Clears _words and _chars. */
    private void emitLine(int indent, int spaces) {
        if (_firstLine) {
            for (int i = 0; i < _parSkip; i++) {
                _pages.addLine(null);
            }
        }
        _firstLine = false;
        StringBuilder output = new StringBuilder();
        addSpaces(indent, output);
        if (spaces >= 3 * (_words.size() - 1)) {
            for (int i = 0; i < _words.size(); i++) {
                output.append(_words.get(i));
                if (i < _words.size() - 1) {
                    addSpaces(3, output);
                }
            }
        } else {
            int spacesSoFar = 0;
            double constant = (double) spaces / (_words.size() - 1);
            for (int i = 0; i < _words.size(); i++) {
                output.append(_words.get(i));
                if (i < _words.size() - 1) {
                    int spacesAllowed = (int) (0.5 + (i + 1) * constant);
                    int spacesToAdd = spacesAllowed - spacesSoFar;
                    spacesSoFar += spacesToAdd;
                    addSpaces(spacesToAdd, output);
                }
            }
        }
        _pages.addLine(output.toString());
        _words.clear();
        _chars = 0;
    }

    /** Adds NUMSPACES to a StringBuilder OUTPUT. */
    private void addSpaces(int numSpaces, StringBuilder output) {
        for (int i = 0; i < numSpaces; i++) {
            output.append(" ");
        }
    }

    /** If the line accumulator is non-empty, justify its current
     *  contents, if needed, add a new complete line to _pages,
     *  and clear the line accumulator. LASTLINE indicates the last line
     *  of a paragraph. */
    private void outputLine(boolean lastLine) {
        if (!_words.isEmpty()) {
            int indents = _firstLine ? _indent + _parIndent : _indent;
            int spaces;
            if (_fill && _justify && !lastLine) {
                spaces = _textWidth - _chars - indents;
            } else {
                spaces = _words.size() - 1;
            }
            emitLine(indents, spaces);
            _firstLine = lastLine;
        }
    }

    /** Destination given in constructor for formatted lines. */
    private final PageAssembler _pages;
    /** The current word being assembled. */
    private String _currentWord;
    /** A list of text that represents the current line being assembled. */
    private ArrayList<String> _words;
    /** Total number of characters in _CURRENTLINE. */
    private int _chars;
    /** The line's text width. */
    private int _textWidth;
    /** The size of the margin. */
    private int _indent;
    /** The size of a paragraph indent. */
    private int _parIndent;
    /** The number of empty lines between each paragraph. */
    private int _parSkip;
    /** True if the current line being assembled is the first line of a
     * paragraph. */
    private boolean _firstLine;
    /** True if fill mode is on. */
    private boolean _fill;
    /** True if justify mode is on. */
    private boolean _justify;

    /** Return _CURRENTWORD. */
    public String getWord() {
        return _currentWord;
    }

    /** Return _WORDS. */
    public ArrayList<String> getWords() {
        return _words;
    }

    /** Return _CHARS. */
    public int getChars() {
        return _chars;
    }

    /** Return _TEXTWIDTH. */
    public int getTextWidth() {
        return _textWidth;
    }

    /** Return _INDENT. */
    public int getIndent() {
        return _indent;
    }

    /** Return _PARINDENT. */
    public int getParIndent() {
        return _parIndent;
    }

    /** Return _PARSKIP. */
    public int getParSkip() {
        return _parSkip;
    }

    /** Return _FIRSTLINE. */
    public boolean getFirstLine() {
        return _firstLine;
    }

    /** Return _FILL. */
    public boolean getFill() {
        return _fill;
    }

    /** Return _JUSTIFY. */
    public boolean getJustify() {
        return _justify;
    }
}
