import java.io.*;
import java.util.HashMap;
import java.util.TreeMap;

public class FileParser {

    private File asmFile;
    public TreeMap<Integer, String> machineCode;
    private BinaryGenerator binGen;
    public HashMap<String,String> symbolTable;

    public FileParser(String filePath, BinaryGenerator binaryGenerator) throws FileNotFoundException {

            this.asmFile = new File(filePath);
            this.asmFile = new File("C:/Users/jguy4/OneDrive/Documents/CS/Nand2Tetris/nand2tetris/projects/06/pong/Pong.asm");

            this.binGen = binaryGenerator;

            boolean status = parseHack();

            if (status) {
                this.machineCode = binGen.machineCode;
            }
            else {
                this.machineCode.put(0, "failure to parse file");
            }

            this.symbolTable = binGen.symbolTable;


    }

    public boolean parseHack() throws FileNotFoundException {

        FileInputStream fstream = new FileInputStream(this.asmFile);
        BufferedReader br = new BufferedReader(new InputStreamReader((fstream)));

        String line = null;

        while (true) {
            try {
                if (!((line = br.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }

            String command = line.replaceAll("\\s+", "");
//            System.out.println(command);

            int offset = command.indexOf("//");
            if (offset != -1) {
                command = command.substring(0, offset);
            }

            if (command.length() > 0) {

                binGen.commandToCode = command;

                if (command.startsWith("@")) {
                    binGen.aCommand();
                }
                else if ((command.startsWith("(")) & (command.endsWith(")"))) {
                    binGen.lCommand();
                }
                else {
                    binGen.cCommand();
                }


//                System.out.println(command);
            }

        }

        return true;

    }


}
