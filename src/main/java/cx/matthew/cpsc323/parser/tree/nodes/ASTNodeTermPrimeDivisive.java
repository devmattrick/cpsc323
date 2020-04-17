package cx.matthew.cpsc323.parser.tree.nodes;

import cx.matthew.cpsc323.lexer.Token;

// <Term’> -> / <Factor> <Term’>
public class ASTNodeTermPrimeDivisive implements ASTNodeTermPrime {
    private Token token;
    private ASTNodeFactor factor;
    private ASTNodeTermPrime termPrime;

    public ASTNodeTermPrimeDivisive(Token token, ASTNodeFactor factor, ASTNodeTermPrime termPrime) {
        this.token = token;
        this.factor = factor;
        this.termPrime = termPrime;
    }

    @Override
    public String stringify() {
        return String.format("%s (Token: %s | %s)", getProductionRule(), token.getType(), token.getLexeme());
    }

    @Override
    public String getProductionRule() {
        return "<Term’> -> / <Factor> <Term’>";
    }

    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{ factor, termPrime };
    }

    public Token getToken() {
        return token;
    }

    public ASTNodeFactor getFactor() {
        return factor;
    }

    public ASTNodeTermPrime getTermPrime() {
        return termPrime;
    }
}
