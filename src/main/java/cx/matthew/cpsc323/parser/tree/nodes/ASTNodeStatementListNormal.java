package cx.matthew.cpsc323.parser.tree.nodes;

import cx.matthew.cpsc323.lexer.Token;

// <StatementList> -> <Statement> ; <StatementList>
public class ASTNodeStatementListNormal implements ASTNodeStatementList {
    private ASTNodeStatement statement;
    private Token token;
    private ASTNodeStatementList statementList;

    public ASTNodeStatementListNormal(ASTNodeStatement statement, Token token, ASTNodeStatementList statementList) {
        this.statement = statement;
        this.token = token;
        this.statementList = statementList;
    }

    @Override
    public String stringify() {
        return String.format("%s (Token: %s | %s)", getProductionRule(), token.getType(), token.getLexeme());
    }

    @Override
    public String getProductionRule() {
        return "<StatementList> -> <Statement> ; <StatementList>";
    }

    @Override
    public ASTNode[] getChildren() {
        return new ASTNode[]{ statement, statementList};
    }

    public ASTNodeStatement getStatement() {
        return statement;
    }

    public Token getToken() {
        return token;
    }

    public ASTNodeStatementList getStatementList() {
        return statementList;
    }
}
