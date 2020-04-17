package cx.matthew.cpsc323.parser.tree;

import cx.matthew.cpsc323.parser.tree.nodes.ASTNode;

public class AST {

    private ASTNode root;

    public AST(ASTNode root) {
        this.root = root;
    }

    public ASTNode getRoot() {
        return root;
    }

    public void print() {
        printChildren(root, 0);
    }

    private void printChildren(ASTNode node, int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        System.out.println(node.stringify());

        for (ASTNode child : node.getChildren()) {
            printChildren(child, depth + 1);
        }
    }

}
