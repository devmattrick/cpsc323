package cx.matthew.cpsc323.parser.tree.nodes;

import cx.matthew.cpsc323.lexer.Token;

// <Expression’> -> + <Term> <Expression’>
public class ASTNodeExpressionPrimeAdditive implements ASTNodeExpressionPrime {
    private Token token;
    private ASTNodeTerm term;
    private ASTNodeExpressionPrime expPrime;

    public ASTNodeExpressionPrimeAdditive(Token token, ASTNodeTerm term, ASTNodeExpressionPrime expPrime) {
        this.token = token;
        this.term = term;
        this.expPrime = expPrime;
    }

    @Override
    public String stringify() {
        return String.format("%s (Token: %s | %s)", getProductionRule(), token.getType(), token.getLexeme());
    }

    @Override
    public String getProductionRule() {
        return "<Expression’> -> + <Term> <Expression’>";
    }

    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{ term, expPrime };
    }

    public Token getToken() {
        return token;
    }

    public ASTNodeTerm getTerm() {
        return term;
    }

    public ASTNodeExpressionPrime getExpPrime() {
        return expPrime;
    }
}
