package cx.matthew.cpsc323.parser.tree.nodes;

public class ASTNodeFactorNum implements ASTNodeFactor {
    private ASTNodeNum num;

    public ASTNodeFactorNum(ASTNodeNum num) {
        this.num = num;
    }

    @Override
    public String getProductionRule() {
        return "<Factor> -> <Num>";
    }

    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{ num };
    }

    public ASTNodeNum getNum() {
        return num;
    }
}
