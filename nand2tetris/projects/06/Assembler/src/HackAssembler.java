import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class HackAssembler {

    String outCode = "";
    String newLine;

    public HackAssembler(FileParser fileParser) throws FileNotFoundException {

        if (fileParser.machineCode.get(0) !=  "failure to parse file") {
            for (int i=0; i<fileParser.machineCode.size(); i++) {

                String key = fileParser.machineCode.get(i);
                if (fileParser.symbolTable.keySet().contains(key)) {
//                    System.out.println(fileParser.symbolTable.get(key));
                    newLine = fileParser.symbolTable.get(key);
                    outCode += newLine;
                } else {
//                    System.out.println(fileParser.machineCode.get(i));
                    newLine = fileParser.machineCode.get(i);
                    outCode += newLine;
                }
                if (i+1 < fileParser.machineCode.size()) {
                    outCode += "\n";
                }
            }
        }
//        System.out.println(outCode);
//        System.out.println(fileParser.machineCode);
//        System.out.println(fileParser.symbolTable);
        try (PrintWriter out = new PrintWriter("C:/Users/jguy4/OneDrive/Documents/CS/Nand2Tetris/nand2tetris/projects/06/cmp/pong_test.hack")) {
            out.println(outCode);
        }
    }
}
