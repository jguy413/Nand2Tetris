import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

//        instantiate FileParser, BinaryGenerator objects
        String testArg;
        if (args.length == 0) {
            testArg = "test";
        }
        else {
            testArg = args[0];
        }
        BinaryGenerator binaryGenerator = new BinaryGenerator();
        FileParser fileParser = new FileParser(testArg, binaryGenerator);

//        instantiate HackAssembler object
        HackAssembler assembler = new HackAssembler(fileParser);
    }

}




