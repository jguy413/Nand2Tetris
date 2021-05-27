import java.util.HashMap;
import java.util.TreeMap;

public class BinaryGenerator {

    public String commandToCode;
    public TreeMap<Integer, String> machineCode = new TreeMap<>();
    private HashMap <String,String> compMap = new HashMap();
    private HashMap <String,String> jumpMap = new HashMap();
    private HashMap <String,String> destMap = new HashMap();
    public HashMap <String,String> symbolTable = new HashMap<>();
    private String comp;
    private String compCode;
    private String jump;
    private String jumpCode;
    private String dest;
    private String destCode;
    public int lineNum = 0;
    private int varAddress = 16;
    private int sinceLastLabel = 0;

    public BinaryGenerator() {

        this.symbolTable.put("R0", "0000000000000000");
        this.symbolTable.put("R1", "0000000000000001");
        this.symbolTable.put("R2", "0000000000000010");
        this.symbolTable.put("R3", "0000000000000011");
        this.symbolTable.put("R4", "0000000000000100");
        this.symbolTable.put("R5", "0000000000000101");
        this.symbolTable.put("R6", "0000000000000110");
        this.symbolTable.put("R7", "0000000000000111");
        this.symbolTable.put("R8", "0000000000001000");
        this.symbolTable.put("R9", "0000000000001001");
        this.symbolTable.put("R10", "0000000000001010");
        this.symbolTable.put("R11", "0000000000001011");
        this.symbolTable.put("R12", "0000000000001100");
        this.symbolTable.put("R13", "0000000000001101");
        this.symbolTable.put("R14", "0000000000001110");
        this.symbolTable.put("R15", "0000000000001111");

        this.symbolTable.put("SP", "0000000000000000");
        this.symbolTable.put("LCL", "0000000000000001");
        this.symbolTable.put("ARG", "0000000000000010");
        this.symbolTable.put("THIS", "0000000000000011");
        this.symbolTable.put("THAT", "0000000000000100");
        this.symbolTable.put("SCREEN", "0100000000000000");
        this.symbolTable.put("KBD", "0110000000000000");



        this.destMap.put("null", "000");
        this.destMap.put("M", "001");
        this.destMap.put("D", "010");
        this.destMap.put("MD", "011");
        this.destMap.put("A", "100");
        this.destMap.put("AM", "101");
        this.destMap.put("AD", "110");
        this.destMap.put("AMD", "111");

        this.jumpMap.put("null", "000");
        this.jumpMap.put("JGT", "001");
        this.jumpMap.put("JEQ", "010");
        this.jumpMap.put("JGE", "011");
        this.jumpMap.put("JLT", "100");
        this.jumpMap.put("JNE", "101");
        this.jumpMap.put("JLE", "110");
        this.jumpMap.put("JMP", "111");

        this.compMap.put("0", "0101010");
        this.compMap.put("1", "0111111");
        this.compMap.put("-1", "0111010");
        this.compMap.put("D", "0001100");
        this.compMap.put("A", "0110000");
        this.compMap.put("!D", "0001101");
        this.compMap.put("!A", "0110001");
        this.compMap.put("-D", "0001111");
        this.compMap.put("-A", "0110011");
        this.compMap.put("D+1", "0011111");
        this.compMap.put("A+1", "0110111");
        this.compMap.put("D-1", "0001110");
        this.compMap.put("A-1", "0110010");
        this.compMap.put("D+A", "0000010");
        this.compMap.put("D-A", "0010011");
        this.compMap.put("A-D", "0000111");
        this.compMap.put("D&A", "0000000");
        this.compMap.put("D|A", "0010101");
        this.compMap.put("M", "1110000");
        this.compMap.put("!M", "1110001");
        this.compMap.put("-M", "1110011");
        this.compMap.put("M+1", "1110111");
        this.compMap.put("M-1", "1110010");
        this.compMap.put("D+M", "1000010");
        this.compMap.put("D-M", "1010011");
        this.compMap.put("M-D", "1000111");
        this.compMap.put("D&M", "1000000");
        this.compMap.put("D|M", "1010101");
    }

    public void setMachineCode(String binary) {
        this.machineCode.put(this.lineNum, binary);
        this.lineNum += 1;
    }

    public void aCommand() {

        this.commandToCode = this.commandToCode.replace("@", "");
//        this.commandToCode = this.commandToCode.replaceAll("[^A-Za-z0-9]", "");

        if (isNumeric()) {
            setMachineCode(numericBinary());
        }
        else {
            if (!isLabel(this.commandToCode)) {
                addVarSymbol(this.commandToCode);
            }
            setMachineCode(this.commandToCode);
        }
    }

    public void lCommand() {
        this.commandToCode = this.commandToCode.replace("(", "");
        this.commandToCode = this.commandToCode.replace(")", "");
        addLabelSymbol(this.commandToCode);
    }

    public void cCommand() {
        int destOffset;
        int jumpOffset;
        if (this.commandToCode.contains("=")) {
            destOffset = this.commandToCode.indexOf("=");
            this.dest = this.commandToCode.substring(0, destOffset);
            this.destCode = this.destMap.get(this.dest);

            this.jumpCode = this.jumpMap.get("null");

            this.comp = this.commandToCode.substring(destOffset+1);
            this.compCode = this.compMap.get(this.comp);
        }
        else if (this.commandToCode.contains(";")) {

            this.destCode = this.destMap.get("null");

            jumpOffset = this.commandToCode.indexOf(";");
            this.jump = this.commandToCode.substring(jumpOffset+1);
            this.jumpCode = this.jumpMap.get(this.jump);

            this.comp = this.commandToCode.substring(0, jumpOffset);
            this.compCode = this.compMap.get(this.comp);
        }

        setMachineCode("111" + this.compCode + this.destCode + this.jumpCode);
    }

    private static boolean isLabel(String s) {

        if (s.matches("^.*\\.\\d$")) {
            return false;
        }

        if (s.contains(".")) {
            return true;
        }

        s = s.replaceAll("[^A-Za-z]", "");
        for (int i=0; i<s.length(); i++)
        {
            if (!Character.isUpperCase(s.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    private boolean isNumeric() {
        try {
            Double.parseDouble(this.commandToCode);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String numericBinary() {

        int decimalValue = Integer.parseInt(this.commandToCode);

        String binCode = Integer.toBinaryString(decimalValue);

        while (binCode.length() < 16) {
            binCode = "0" + binCode;
        }

        return binCode;
    }

    private static String numericBinary(int decimalValue) {

        String binCode = Integer.toBinaryString(decimalValue);

        while (binCode.length() < 16) {
            binCode = "0" + binCode;
        }

        return binCode;
    }

    private void addVarSymbol(String symbol) {
        if (!symbolTable.containsKey(symbol)) {
            symbolTable.put(symbol, numericBinary(this.varAddress));
            this.varAddress += 1;
        }
    }

    private void addLabelSymbol(String symbol) {
        if (symbolTable.containsKey(symbol)) {
            symbolTable.put(symbol, numericBinary(this.lineNum));
        }
        symbolTable.put(symbol, numericBinary(this.lineNum));
    }


}
