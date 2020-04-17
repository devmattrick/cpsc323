package cx.matthew.cpsc323.parser;

import cx.matthew.cpsc323.lexer.Token;
import cx.matthew.cpsc323.parser.tree.AST;
import cx.matthew.cpsc323.parser.tree.nodes.*;

import java.util.Queue;

public class Parser {

    private Queue<Token> tokens;

    public Parser(Queue<Token> tokens) {
        this.tokens = tokens;
    }

    public AST parse() {
        return new AST(parseStatementList());
    }

    private ASTNodeStatementList parseStatementList() {
        Token token = tokens.peek();

        if (token == null) {
            return new ASTNodeStatementListEpsilon();
        }

        ASTNodeStatement statement = parseStatement();
        Token semi = tokens.remove();

        if (semi.getType() != Token.Type.SEPARATOR || !semi.getLexeme().equals(";")) {
            throw new IllegalStateException("Missing semicolon");
        }

        return new ASTNodeStatementListNormal(statement, semi, parseStatementList());
    }

    private ASTNodeStatement parseStatement() {
        Token peek = tokens.peek();

        if (peek == null) {
            return null;
        }

        if (peek.getType() == Token.Type.IDENTIFIER) {
            return new ASTNodeStatementAssign(parseAssign());
        }

        return new ASTNodeStatementDeclarative(parseDeclarative());
    }

    private ASTNodeAssign parseAssign() {
        ASTNodeID id = parseID();
        Token token = tokens.remove();

        if (token.getType() != Token.Type.OPERATOR || !token.getLexeme().equals("=")) {
            throw new IllegalStateException("Expected '=', got " + token.getLexeme());
        }

        return new ASTNodeAssign(id, token, parseExpression());
    }

    private ASTNodeDeclarative parseDeclarative() {
        ASTNodeType type = parseType();
        ASTNodeID id = parseID();
        Token equals = tokens.remove();

        if (equals.getType() != Token.Type.OPERATOR || !equals.getLexeme().equals("=")) {
            throw new IllegalStateException("Expected '=', got " + equals.getLexeme());
        }

        return new ASTNodeDeclarative(type, id, equals, parseExpression());
    }

    private ASTNodeExpression parseExpression() {
        return new ASTNodeExpression(parseTerm(), parseExpressionPrime());
    }

    private ASTNodeExpressionPrime parseExpressionPrime () {
        Token token = tokens.peek();

        if (token != null) {
            if (token.getType() == Token.Type.OPERATOR) {
                if (token.getLexeme().equals("+")) {
                    tokens.remove();
                    return new ASTNodeExpressionPrimeAdditive(token, parseTerm(), parseExpressionPrime());
                }

                if (token.getLexeme().equals("-")) {
                    tokens.remove();
                    return new ASTNodeExpressionPrimeSubtractive(token, parseTerm(), parseExpressionPrime());
                }
            }
        }

        return new ASTNodeExpressionPrimeEpsilon();
    }

    private ASTNodeTerm parseTerm() {
        return new ASTNodeTerm(parseFactor(), parseTermPrime());
    }

    private ASTNodeTermPrime parseTermPrime() {
        Token token = tokens.peek();

        if (token != null) {
            if (token.getType() == Token.Type.OPERATOR) {
                if (token.getLexeme().equals("*")) {
                    tokens.remove();
                    return new ASTNodeTermPrimeMultiplicative(token, parseFactor(), parseTermPrime());
                }

                if (token.getLexeme().equals("/")) {
                    tokens.remove();
                    return new ASTNodeTermPrimeDivisive(token, parseFactor(), parseTermPrime());
                }
            }
        }

        return new ASTNodeTermPrimeEpsilon();
    }

    private ASTNodeFactor parseFactor() {
        Token peek = tokens.peek();

        if (peek == null) {
            throw new IllegalStateException("Expected a factor, got EOF");
        }

        if (peek.getType() == Token.Type.SEPARATOR) {
            if (peek.getLexeme().equals("(")) {
                Token paren1 = tokens.remove();
                ASTNodeExpression exp = parseExpression();
                Token paren2 = tokens.remove();

                if (paren2.getType() == Token.Type.SEPARATOR) {
                    if (paren2.getLexeme().equals(")")) {
                        return new ASTNodeFactorParentheticalExpression(paren1, exp, paren2);
                    }
                }

                throw new IllegalStateException("Expected close paren, got " + paren2.getLexeme());
            }
        }

        if (peek.getType() == Token.Type.IDENTIFIER) {
            return new ASTNodeFactorID(parseID());
        }

        if (peek.getType() == Token.Type.INTEGER || peek.getType() == Token.Type.REAL) {
            return new ASTNodeFactorNum(parseNum());
        }

        throw new IllegalStateException("Expected open paren, identifier, or integer, got " + peek.getType().getName());
    }

    private ASTNodeID parseID() {
        Token token = tokens.remove();

        if (token.getType() != Token.Type.IDENTIFIER) {
            throw new IllegalStateException("Expected identifier, got " + token.getType().getName());
        }

        return new ASTNodeID(token);
    }

    private ASTNodeNum parseNum() {
        Token token = tokens.remove();

        if (token.getType() == Token.Type.INTEGER || token.getType() == Token.Type.REAL) {
            return new ASTNodeNum(token);
        }

        throw new IllegalStateException("Expected integer or real, got " + token.getType().getName());
    }

    private ASTNodeType parseType() {
        Token token = tokens.remove();

        if (token.getType() != Token.Type.KEYWORD) {
            throw new IllegalStateException("Expected keyword, got " + token.getType().getName());
        }

        return new ASTNodeType(token);
    }

}
