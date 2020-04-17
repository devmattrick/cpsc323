package cx.matthew.cpsc323.lexer;

import java.util.*;

public class Lexer {

    private static final List<Character> SEPARATORS = Collections.unmodifiableList(Arrays.asList(
            '\'', '(', ')', '{', '}', '[', ']', ',', ':', ';'
    ));

    private static final List<Character> OPERATORS = Collections.unmodifiableList(Arrays.asList(
            '*', '+', '-', '=', '/', '>', '<', '%'
    ));

    private static final List<String> KEYWORDS = Collections.unmodifiableList(Arrays.asList(
            "int", "float", "bool", "true", "false", "if", "else", "then", "endif", "while", "whileend", "do", "doend",
            "for", "forend", "input", "output", "and", "or", "not"
    ));

    private static final State[][] TRANSITIONS = {
                            // Inputs:
            // State:       /* Space        Alpha          Numeric        Dollar         Dot            Exclamation     Separator        Operator        Unknown    */
            /* Initial   */ {State.INITIAL, State.STRING,  State.INTEGER, State.REJECT,  State.REJECT,  State.COMMENT,  State.SEPARATOR, State.OPERATOR, State.REJECT},
            /* String    */ {State.INITIAL, State.STRING,  State.STRING,  State.STRING,  State.REJECT,  State.COMMENT,  State.SEPARATOR, State.OPERATOR, State.REJECT},
            /* Integer   */ {State.INITIAL, State.STRING,  State.INTEGER, State.REJECT,  State.REAL,    State.COMMENT,  State.SEPARATOR, State.OPERATOR, State.REJECT},
            /* Real      */ {State.INITIAL, State.STRING,  State.REAL,    State.REJECT,  State.REJECT,  State.COMMENT,  State.SEPARATOR, State.OPERATOR, State.REJECT},
            /* Separator */ {State.INITIAL, State.STRING,  State.INTEGER, State.REJECT,  State.REJECT,  State.COMMENT,  State.SEPARATOR, State.OPERATOR, State.REJECT},
            /* Operator  */ {State.INITIAL, State.STRING,  State.INTEGER, State.REJECT,  State.REJECT,  State.COMMENT,  State.SEPARATOR, State.OPERATOR, State.REJECT},
            /* Comment   */ {State.COMMENT, State.COMMENT, State.COMMENT, State.COMMENT, State.COMMENT, State.INITIAL,  State.COMMENT,   State.COMMENT,  State.COMMENT},
            /* Reject    */ {State.REJECT,  State.REJECT,  State.REJECT,  State.REJECT,  State.REJECT,  State.REJECT,   State.REJECT,    State.REJECT,   State.REJECT}
    };

    public Queue<Token> lex(String input) {
        Queue<Token> tokens = new ArrayDeque<>();

        int line = 1;
        int col = 1;

        State prev = State.INITIAL;
        String buffer = "";
        for (char c : input.toCharArray()) {
            InputType type = getInputType(c);
            State next = TRANSITIONS[prev.ordinal()][type.ordinal()];

            if (prev == State.STRING && next != State.STRING) {
                if (KEYWORDS.contains(buffer)) {
                    tokens.add(new Token(Token.Type.KEYWORD, buffer));
                } else {
                    tokens.add(new Token(Token.Type.IDENTIFIER, buffer));
                }

                buffer = "";
            }

            if (prev == State.INTEGER && next != State.INTEGER && next != State.REAL) {
                tokens.add(new Token(Token.Type.INTEGER, buffer));
                buffer = "";
            }

            if (prev == State.REAL && next != State.REAL) {
                tokens.add(new Token(Token.Type.REAL, buffer));
                buffer = "";
            }

            if (prev == State.COMMENT && next != State.COMMENT) {
                // No longer add comments to token queue
                // tokens.add(new Token(Token.Type.COMMENT, buffer + c));
                buffer = "";
            }

            if (next == State.STRING || next == State.INTEGER || next == State.REAL || next == State.COMMENT) {
                buffer += c;
            }

            if (next == State.OPERATOR) {
                tokens.add(new Token(Token.Type.OPERATOR, String.valueOf(c)));
            }

            if (next == State.SEPARATOR) {
                tokens.add(new Token(Token.Type.SEPARATOR, String.valueOf(c)));
            }

            if (next == State.REJECT) {
                throw new IllegalStateException(String.format("Syntax error at line %d, column %d.", line, col));
            }

            prev = next;

            // Line/ column counter
            col++;
            if (c == '\n' || c == '\r') {
                line++;
                col = 1;
            }
        }

        // Fixes a bug where if there's not a newline at the end of file the final identifier wouldn't be recognized
        if (prev == State.STRING) {
            if (KEYWORDS.contains(buffer)) {
                tokens.add(new Token(Token.Type.KEYWORD, buffer));
            } else {
                tokens.add(new Token(Token.Type.IDENTIFIER, buffer));
            }

        }

        return tokens;
    }

    private InputType getInputType(char c) {
        if (c == '.') return InputType.DOT;
        if (c == '$') return InputType.DOLLAR;
        if (c == '!') return InputType.EXCLAMATION;
        if (Character.isWhitespace(c)) return InputType.SPACE;
        if (Character.isAlphabetic(c)) return InputType.ALPHABETIC;
        if (Character.isDigit(c)) return InputType.NUMERIC;
        if (SEPARATORS.contains(c)) return InputType.SEPARATOR;
        if (OPERATORS.contains(c)) return InputType.OPERATOR;

        return InputType.UNKNOWN;
    }

    private enum State {
        INITIAL,
        STRING,
        INTEGER,
        REAL,
        SEPARATOR,
        OPERATOR,
        COMMENT,
        REJECT,
    }

    private enum InputType {
        SPACE,
        ALPHABETIC,
        NUMERIC,
        DOLLAR,
        DOT,
        EXCLAMATION,
        SEPARATOR,
        OPERATOR,
        UNKNOWN,
    }

}