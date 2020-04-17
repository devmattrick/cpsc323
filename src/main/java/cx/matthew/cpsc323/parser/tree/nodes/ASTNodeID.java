package cx.matthew.cpsc323.parser.tree.nodes;

import cx.matthew.cpsc323.lexer.Token;

// <ID> -> id
public class ASTNodeID implements ASTNode {
    private Token token;

    public ASTNodeID(Token token) {
        this.token = token;
    }

    @Override
    public String stringify() {
        return String.format("%s (Token: %s | %s)", getProductionRule(), token.getType(), token.getLexeme());
    }

    @Override
    public String getProductionRule() {
        return "<ID> -> id";
    }

    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }

    public Token getToken() {
        return token;
    }
}
