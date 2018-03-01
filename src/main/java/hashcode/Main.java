package hashcode;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Hash code -- Qualification Round 2018");
        String fileName = "a_example.in";
        InputStructure inputStructure = new InputStructure(fileName);
        System.out.println(inputStructure);
        //run optim here
        ProblemInstance pI = new ProblemInstance(inputStructure);
        String solution = pI.solve().toString();
        FileHandler.writeOutput("target/output.txt", solution);

    }
}
