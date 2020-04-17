package cx.matthew.cpsc323.parser.tree.nodes;

public interface ASTNode {

    default String stringify() {
        return getProductionRule();
    }

    String getProductionRule();

    ASTNode[] getChildren();

}
