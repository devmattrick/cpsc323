package cx.matthew.cpsc323.parser.tree.nodes;

import cx.matthew.cpsc323.lexer.Token;

// <Factor> -> ( <Expression> )
public class ASTNodeFactorParentheticalExpression implements ASTNodeFactor {
    private Token paren1;
    private ASTNodeExpression exp;
    private Token paren2;

    public ASTNodeFactorParentheticalExpression(Token paren1, ASTNodeExpression exp, Token paren2) {
        this.paren1 = paren1;
        this.exp = exp;
        this.paren2 = paren2;
    }

    @Override
    public String stringify() {
        return String.format("%s (Token: %s | %s) (Token: %s | %s)", getProductionRule(), paren1.getType(), paren1.getLexeme(), paren2.getType(), paren2.getLexeme());
    }

    @Override
    public String getProductionRule() {
        return "<Factor> -> ( <Expression> )";
    }

    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{ exp };
    }

    public Token getParen1() {
        return paren1;
    }

    public ASTNodeExpression getExp() {
        return exp;
    }

    public Token getParen2() {
        return paren2;
    }
}
