package cx.matthew.cpsc323.parser;

import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

public class SymbolTable {

    private int maxMemoryAddress = 5000;
    private final Hashtable<Symbol, Integer> symbols = new Hashtable<>();

    public int insert(Symbol symbol) {
        if (symbols.containsKey(symbol)) {
            throw new RuntimeException(String.format("Variable %s has already been declared", symbol.getName()));
        }

        int address = maxMemoryAddress++;
        symbols.put(symbol, address);
        return address;
    }

    public int lookup(Symbol symbol) {
        if (symbols.containsKey(symbol)) {
            return symbols.get(symbol);
        }

        // If a variable with that name is found, but didn't get found previously, we have a mismatched type
        Optional<Symbol> found = symbols.keySet().stream().filter(test -> test.getName().equals(symbol.getName())).findAny();
        if (found.isPresent()) {
            throw new RuntimeException(
                    String.format(
                            "Mismatched variable type. Expected %s, got %s",
                            symbol.getType().getName(),
                            found.get().getType().getName()
                    )
            );
        }

        // Otherwise the variable is not defined at all
        throw new RuntimeException(String.format("Unknown variable %s", symbol.getName()));
    }

    public void print() {
        for (Map.Entry<Symbol, Integer> entry : symbols.entrySet()) {
            Symbol key = entry.getKey();
            System.out.printf("%-4d     %-6s      %s\n", entry.getValue(), key.getName(), key.getType().getName());
        }
    }

}
