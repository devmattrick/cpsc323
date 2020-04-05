package cx.matthew.cpsc323;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Unspecified input file.");
            System.exit(1);
        }

        String outputFile = null;
        if (args.length >= 2) {
            outputFile = args[1];
        }

        try {
            String input = new String(Files.readAllBytes(Paths.get(args[0])));
            Lexer lexer = new Lexer();

            List<Token> tokens = lexer.lex(input);
            String output = String.format("%-10s | %s\n\n", "TOKEN", "LEXEME");
            output += tokens.stream()
                        .map(token -> String.format("%-10s | %s\n", token.getType().getName(), token.getLexeme()))
                        .collect(Collectors.joining());

            if (outputFile != null) {
                Files.write(Paths.get(outputFile), output.getBytes());
            } else {
                System.out.print(output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
