package cx.matthew.cpsc323.parser;

import cx.matthew.cpsc323.lexer.Token;

import java.util.*;
import java.util.stream.Collectors;

public class Parser {

    private final Queue<Token> tokens;
    private final List<Instruction> instructions = new ArrayList<>();
    private final SymbolTable symbolTable = new SymbolTable();
    private int instructionLocation = 0;
    private final Stack<Integer> jumpStack = new Stack<>();

    public Parser(Queue<Token> tokens) {
        // Filter out comment tokens
        this.tokens = tokens.stream().filter((token -> token.getType() != Token.Type.COMMENT))
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void parse() {
        parseStatementList();
    }

    private void parseStatementList() {
        // If there are no tokens left, we're at the end of a statement list
        if (tokens.size() == 0) return;

        // If token is "}", we assume we're at the end of a block
        if (isToken(tokens.peek(), Token.Type.SEPARATOR, "}")) return;

        // Parse a statement
        parseStatement();

        // Parse another statement list
        parseStatementList();
    }

    private void parseStatement() {
        // If the next token is an ID token, then we have an assignment statement
        Token peek = tokens.peek();

        if (isType(peek, Token.Type.IDENTIFIER)) {
            parseAssign();

            // Expect a semicolon after a statement
            requireToken(tokens.poll(), Token.Type.SEPARATOR, ";");
        } else if (isToken(peek, Token.Type.KEYWORD, "while")) {
            parseWhileStatement();
        } else if (isToken(peek, Token.Type.KEYWORD, "if")) {
            parseIfStatement();
        } else {
            parseDeclarativeList();

            // Expect a semicolon after a statement
            requireToken(tokens.poll(), Token.Type.SEPARATOR, ";");
        }
    }

    private void parseAssign() {
        Token ident = tokens.peek();
        parseID();

        if (ident == null) {
            throw new RuntimeException("Missing ID token");
        }

        if (!isToken(tokens.poll(), Token.Type.OPERATOR, "=")) {
            throw new RuntimeException("Missing '='");
        }

        parseExpression();
        addInstruction("POPM", symbolTable.lookup(new Symbol(Symbol.Type.INTEGER, ident.getLexeme())));
    }

    private void parseWhileStatement() {
        requireToken(tokens.poll(), Token.Type.KEYWORD, "while");
        int labelLocation = instructionLocation;
        addInstruction("LABEL");

        requireToken(tokens.poll(), Token.Type.SEPARATOR, "(");
        parseComparison();
        requireToken(tokens.poll(), Token.Type.SEPARATOR, ")");

        requireToken(tokens.poll(), Token.Type.SEPARATOR, "{");
        parseStatementList();
        requireToken(tokens.poll(), Token.Type.SEPARATOR, "}");

        addInstruction("JUMP", labelLocation);
        backPatch(instructionLocation);
        addInstruction("LABEL");
    }

    private void parseIfStatement() {
        requireToken(tokens.poll(), Token.Type.KEYWORD, "if");

        requireToken(tokens.poll(), Token.Type.SEPARATOR, "(");
        parseComparison();
        requireToken(tokens.poll(), Token.Type.SEPARATOR, ")");

        requireToken(tokens.poll(), Token.Type.SEPARATOR, "{");
        parseStatementList();
        backPatch(instructionLocation);
        requireToken(tokens.poll(), Token.Type.SEPARATOR, "}");
        addInstruction("LABEL");
    }

    private void parseComparison() {
        parseExpression();
        Token token = tokens.poll();
        parseExpression();

        if (isToken(token, Token.Type.OPERATOR, "==")) {
            addInstruction("EQU");
            jumpStack.push(instructionLocation);
            addInstruction("JUMPZ");
        } else if (isToken(token, Token.Type.OPERATOR, "!=")) {
            addInstruction("NEQ");
            jumpStack.push(instructionLocation);
            addInstruction("JUMPZ");
        } else if (isToken(token, Token.Type.OPERATOR, ">")) {
            addInstruction("GRT");
            jumpStack.push(instructionLocation);
            addInstruction("JUMPZ");
        } else if (isToken(token, Token.Type.OPERATOR, "<")) {
            addInstruction("LES");
            jumpStack.push(instructionLocation);
            addInstruction("JUMPZ");
        } else if (isToken(token, Token.Type.OPERATOR, ">=")) {
            addInstruction("GEQ");
            jumpStack.push(instructionLocation);
            addInstruction("JUMPZ");
        } else if (isToken(token, Token.Type.OPERATOR, "<=")) {
            addInstruction("LEQ");
            jumpStack.push(instructionLocation);
            addInstruction("JUMPZ");
        } else if (token != null) {
            throw new RuntimeException("Expected comparison operator, got '" + token.getLexeme() + "'");
        } else {
            throw new RuntimeException("Missing comparison operator");
        }
    }

    private void parseDeclarativeList() {
        parseDeclarative();

        if (isToken(tokens.peek(), Token.Type.SEPARATOR, ",")) {
            tokens.remove();
            parseInnerDeclarativeList();
        }
    }

    private void parseInnerDeclarativeList() {
        Token ident = tokens.peek();
        parseID();

        if (ident == null) {
            throw new RuntimeException("Missing ID token");
        }

        int addr = symbolTable.insert(new Symbol(Symbol.Type.INTEGER, ident.getLexeme()));

        if (isToken(tokens.peek(), Token.Type.OPERATOR, "=")) {
            tokens.remove();
            parseExpression();
            addInstruction("POPM", addr);
        }

        if (isToken(tokens.peek(), Token.Type.SEPARATOR, ",")) {
            tokens.remove();
            parseInnerDeclarativeList();
        }
    }

    private void parseDeclarative() {
        parseType();

        Token ident = tokens.peek();
        parseID();

        if (ident == null) {
            throw new RuntimeException("Missing ID token");
        }

        int addr = symbolTable.insert(new Symbol(Symbol.Type.INTEGER, ident.getLexeme()));

        if (isToken(tokens.peek(), Token.Type.OPERATOR, "=")) {
            tokens.remove();
            parseExpression();
            addInstruction("POPM", addr);
        }
    }

    private void parseExpression() {
        parseTerm();
        parseExpressionPrime();
    }

    private void parseExpressionPrime() {
        Token op = tokens.peek();

        if (isToken(op, Token.Type.OPERATOR, "+")) {
            tokens.remove();
            parseTerm();
            addInstruction("ADD");
            parseExpressionPrime();
        } else if (isToken(op, Token.Type.OPERATOR, "-")) {
            tokens.remove();
            parseTerm();
            addInstruction("SUB");
            parseExpressionPrime();
        }
    }

    private void parseTerm() {
        parseFactor();
        parseTermPrime();
    }

    private void parseTermPrime() {
        Token op = tokens.peek();

        if (isToken(op, Token.Type.OPERATOR, "*")) {
            tokens.remove();
            parseFactor();
            addInstruction("MUL");
            parseTermPrime();
        } else if (isToken(op, Token.Type.OPERATOR, "/")) {
            tokens.remove();
            parseFactor();
            addInstruction("DIV");
            parseTermPrime();
        }
    }

    private void parseFactor() {
        Token peek = tokens.peek();

        if (isToken(peek, Token.Type.SEPARATOR, "(")) {
            tokens.remove();
            parseExpression();

            requireToken(tokens.poll(), Token.Type.SEPARATOR, ")");
        } else if (isType(peek, Token.Type.IDENTIFIER)) {
            parseID();
            addInstruction("PUSHM", symbolTable.lookup(new Symbol(Symbol.Type.INTEGER, peek.getLexeme())));
        } else if (isType(peek, Token.Type.INTEGER)) {
            parseNum();
            addInstruction("PUSHI", peek.getLexeme());
        } else {
            throw new RuntimeException("Expected expression, identifier, or number");
        }
    }

    private void parseID() {
        requireType(tokens.poll(), Token.Type.IDENTIFIER);
    }

    private void parseNum() {
        requireType(tokens.poll(), Token.Type.INTEGER);
    }

    private void parseType() {
        requireType(tokens.poll(), Token.Type.KEYWORD);
    }

    private boolean isType(Token token, Token.Type type) {
        return token != null && token.getType() == type;
    }

    private boolean isToken(Token token, Token.Type type, String lexeme) {
        return isType(token, type) && token.getLexeme().equals(lexeme);
    }

    private void requireType(Token token, Token.Type type) {
        if (!isType(token, type)) {
            throw new RuntimeException(String.format("Expected type %s, got %s", type.getName(), token.getType().getName()));
        }
    }

    private void requireToken(Token token, Token.Type type, String lexeme) {
        if (!isToken(token, type, lexeme)) {
            throw new RuntimeException(String.format("Expected %s, got %s", lexeme, token.getLexeme()));
        }
    }

    private void addInstruction(String op) {
        addInstruction(op, null);
    }

    private void addInstruction(String op, int arg) {
        addInstruction(op, String.valueOf(arg));
    }

    private void addInstruction(String op, String arg) {
        instructions.add(new Instruction(instructionLocation++, op, arg));
    }

    private void backPatch(int jumpLocation) {
        Instruction instruction = instructions.get(jumpStack.pop());
        instruction.setArgument(String.valueOf(jumpLocation));
    }

}
