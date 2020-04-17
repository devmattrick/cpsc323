package cx.matthew.cpsc323.parser.tree.nodes;

// <StatementList> -> ε
public class ASTNodeStatementListEpsilon implements ASTNodeStatementList {
    @Override
    public String getProductionRule() {
        return "<StatementList> -> ε";
    }

    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[0];
    }
}
