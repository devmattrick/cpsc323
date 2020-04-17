package cx.matthew.cpsc323.parser.tree.nodes;

import cx.matthew.cpsc323.lexer.Token;

// <Assign> -> <ID> = <Expression>
public class ASTNodeAssign implements ASTNode {
    private ASTNodeID id;
    private Token token;
    private ASTNodeExpression exp;

    public ASTNodeAssign(ASTNodeID id, Token token, ASTNodeExpression exp) {
        this.id = id;
        this.token = token;
        this.exp = exp;
    }

    @Override
    public String stringify() {
        return String.format("%s (Token: %s | %s)", getProductionRule(), token.getType(), token.getLexeme());
    }

    @Override
    public String getProductionRule() {
        return "<Assign> -> <ID> = <Expression>";
    }

    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{ id, exp };
    }

    public ASTNodeID getId() {
        return id;
    }

    public Token getToken() {
        return token;
    }

    public ASTNodeExpression getExp() {
        return exp;
    }
}
