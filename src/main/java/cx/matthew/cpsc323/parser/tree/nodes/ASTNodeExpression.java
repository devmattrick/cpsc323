package cx.matthew.cpsc323.parser.tree.nodes;

// <Expression> -> <Term> <Expression’>
public class ASTNodeExpression implements ASTNode {
    private ASTNodeTerm term;
    private ASTNodeExpressionPrime expPrime;

    public ASTNodeExpression(ASTNodeTerm term, ASTNodeExpressionPrime expPrime) {
        this.term = term;
        this.expPrime = expPrime;
    }

    @Override
    public String getProductionRule() {
        return "<Expression> -> <Term> <Expression’>";
    }

    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{ term, expPrime };
    }

    public ASTNodeTerm getTerm() {
        return term;
    }

    public ASTNodeExpressionPrime getExpPrime() {
        return expPrime;
    }
}
