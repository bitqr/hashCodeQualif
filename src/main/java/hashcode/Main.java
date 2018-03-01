package hashcode;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Hash code -- Qualification Round 2018");
        System.out.println(new hashcode.InputStructure());
        //run optim here
        hashcode.ProblemInstance pI = new hashcode.ProblemInstance("src/resources/input.in");
        String solution = pI.solve().toString();
        hashcode.FileHandler.writeOutput("target/output.txt", solution);

    }
}
