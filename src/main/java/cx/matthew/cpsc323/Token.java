package cx.matthew.cpsc323;

/**
 * A token created by the lexer from a given input string.
 */
public class Token {
    private Type type;
    private String lexeme;

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

        private String name;
        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
