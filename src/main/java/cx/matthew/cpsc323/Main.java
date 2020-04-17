package cx.matthew.cpsc323;

import cx.matthew.cpsc323.lexer.Lexer;
import cx.matthew.cpsc323.lexer.Token;
import cx.matthew.cpsc323.parser.Parser;
import cx.matthew.cpsc323.parser.tree.AST;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Queue;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Unspecified input file.");
            System.exit(1);
        }

        try {
            String input = new String(Files.readAllBytes(Paths.get(args[0])));
            Lexer lexer = new Lexer();

            Queue<Token> tokens = lexer.lex(input);

            System.out.println("LEXER RESULTS: ");
            String output = String.format("%-10s | %s\n\n", "TOKEN", "LEXEME");
            output += tokens.stream()
                    .map(token -> String.format("%-10s | %s\n", token.getType().getName(), token.getLexeme()))
                    .collect(Collectors.joining());
            System.out.println(output);

            System.out.println("PARSER RESULTS: ");
            Parser parser = new Parser(tokens);
            AST ast = parser.parse();

            ast.print();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
