package cx.matthew.cpsc323;

import cx.matthew.cpsc323.lexer.Lexer;
import cx.matthew.cpsc323.lexer.Token;
import cx.matthew.cpsc323.parser.Instruction;
import cx.matthew.cpsc323.parser.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Queue;

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
            Parser parser = new Parser(tokens);

            parser.parse();

            System.out.println("INSTRUCTIONS:");
            parser.getInstructions().forEach((Instruction::print));

            System.out.println("\nSYMBOL TABLE:");
            parser.getSymbolTable().print();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
