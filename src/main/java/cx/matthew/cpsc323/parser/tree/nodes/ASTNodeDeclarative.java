package cx.matthew.cpsc323.parser.tree.nodes;

import cx.matthew.cpsc323.lexer.Token;

// <Declarative> -> <Type> <ID> = <Expression>
public class ASTNodeDeclarative implements ASTNode {
    private ASTNodeType type;
    private ASTNodeID id;
    private Token token;
    private ASTNodeExpression expression;

    public ASTNodeDeclarative(ASTNodeType type, ASTNodeID id, Token token, ASTNodeExpression expression) {
        this.type = type;
        this.id = id;
        this.token = token;
        this.expression = expression;
    }

    @Override
    public String stringify() {
        return String.format("%s (Token: %s | %s)", getProductionRule(), token.getType(), token.getLexeme());
    }

    @Override
    public String getProductionRule() {
        return "<Declarative> -> <Type> <ID> = <Expression>";
    }

    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{ type, id, expression };
    }

    public ASTNodeType getType() {
        return type;
    }

    public ASTNodeID getId() {
        return id;
    }

    public Token getToken() {
        return token;
    }

    public ASTNodeExpression getExpression() {
        return expression;
    }
}
