package cx.matthew.cpsc323.parser.tree.nodes;

// <Statement> -> <Assign>
public class ASTNodeStatementDeclarative implements ASTNodeStatement {
    private ASTNodeDeclarative declarative;

    public ASTNodeStatementDeclarative(ASTNodeDeclarative declarative) {
        this.declarative = declarative;
    }

    @Override
    public String getProductionRule() {
        return "<Statement> -> <Assign>";
    }

    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{ declarative };
    }

    public ASTNodeDeclarative getAssign() {
        return declarative;
    }
}
