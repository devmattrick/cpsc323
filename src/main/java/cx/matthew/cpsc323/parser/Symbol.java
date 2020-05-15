package cx.matthew.cpsc323.parser;

import java.util.Objects;

public class Symbol {

    private final Type type;
    private final String name;

    public Symbol(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return type == symbol.type &&
                Objects.equals(name, symbol.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name);
    }

    enum Type {
        INTEGER("integer"),
        BOOLEAN("boolean");

        private final String name;
        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
