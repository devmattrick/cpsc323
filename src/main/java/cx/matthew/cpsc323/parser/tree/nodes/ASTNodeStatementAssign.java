package cx.matthew.cpsc323.parser.tree.nodes;

// <Statement> -> <Assign>
public class ASTNodeStatementAssign implements ASTNodeStatement {
    private ASTNodeAssign assign;

    public ASTNodeStatementAssign(ASTNodeAssign assign) {
        this.assign = assign;
    }

    @Override
    public String getProductionRule() {
        return "<Statement> -> <Assign>";
    }

    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{ assign };
    }

    public ASTNodeAssign getAssign() {
        return assign;
    }
}
