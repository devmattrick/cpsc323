package cx.matthew.cpsc323.lexer;

import java.util.Objects;

/**
 * A token created by the lexer from a given input string.
 */
public class Token {
    private final Type type;
    private final String lexeme;

    public Token(Type type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    public Type getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return type == token.type &&
                Objects.equals(lexeme, token.lexeme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, lexeme);
    }

    /**
     * The type of the token.
     */
    public enum Type {
        KEYWORD("Keyword"),
        IDENTIFIER("Identifier"),
        SEPARATOR("Separator"),
        OPERATOR("Operator"),
        INTEGER("Integer"),
        REAL("Real"),
        COMMENT("Comment");

        private final String name;
        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
