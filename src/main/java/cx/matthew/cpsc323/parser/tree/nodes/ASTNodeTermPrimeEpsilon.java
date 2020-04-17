package cx.matthew.cpsc323.parser.tree.nodes;

// <Term’> -> ε
public class ASTNodeTermPrimeEpsilon implements ASTNodeTermPrime {
    @Override
    public String getProductionRule() {
        return "<Term’> -> ε";
    }

    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }
}
