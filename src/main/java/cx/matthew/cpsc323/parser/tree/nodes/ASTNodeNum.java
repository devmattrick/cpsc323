package cx.matthew.cpsc323.parser.tree.nodes;

import cx.matthew.cpsc323.lexer.Token;

public class ASTNodeNum implements ASTNode {
    private Token token;

    public ASTNodeNum(Token token) {
        this.token = token;
    }

    @Override
    public String stringify() {
        return String.format("%s (Token: %s | %s)", getProductionRule(), token.getType(), token.getLexeme());
    }

    @Override
    public String getProductionRule() {
        return "<Num> -> num";
    }

    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }

    public Token getToken() {
        return token;
    }
}
