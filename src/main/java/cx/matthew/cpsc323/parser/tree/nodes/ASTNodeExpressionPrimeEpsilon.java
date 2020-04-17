package cx.matthew.cpsc323.parser.tree.nodes;

// <Expression’> -> ε
public class ASTNodeExpressionPrimeEpsilon implements ASTNodeExpressionPrime {
    @Override
    public String getProductionRule() {
        return "<Expression’> -> ε";
    }

    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }
}
