package cx.matthew.cpsc323.parser.tree.nodes;

public class ASTNodeFactorID implements ASTNodeFactor {
    private ASTNodeID id;

    public ASTNodeFactorID(ASTNodeID id) {
        this.id = id;
    }

    @Override
    public String getProductionRule() {
        return "<Factor> -> <ID>";
    }

    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{ id };
    }

    public ASTNodeID getId() {
        return id;
    }
}
