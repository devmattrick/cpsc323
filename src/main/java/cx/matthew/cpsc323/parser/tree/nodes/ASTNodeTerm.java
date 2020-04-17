package cx.matthew.cpsc323.parser.tree.nodes;

// <Term> -> <Factor> <Term’>
public class ASTNodeTerm implements ASTNode {
    private ASTNodeFactor factor;
    private ASTNodeTermPrime termPrime;

    public ASTNodeTerm(ASTNodeFactor factor, ASTNodeTermPrime termPrime) {
        this.factor = factor;
        this.termPrime = termPrime;
    }

    @Override
    public String getProductionRule() {
        return "<Term> -> <Factor> <Term’>";
    }

    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{ factor, termPrime };
    }
}
