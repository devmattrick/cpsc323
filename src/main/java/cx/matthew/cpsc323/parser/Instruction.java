package cx.matthew.cpsc323.parser;

public class Instruction {

    private final int address;
    private final String op;
    private String argument;

    public Instruction(int address, String op) {
        this(address, op, null);
    }

    public Instruction(int address, String op, String argument) {
        this.address = address;
        this.op = op;
        this.argument = argument;
    }

    public int getAddress() {
        return address;
    }

    public String getOp() {
        return op;
    }

    public String getArgument() {
        return argument;
    }

    public void print() {
        System.out.printf("%4d : %-5s %s\n", address, op, argument == null ? "" : argument);
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }
}
