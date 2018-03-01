package hashcode;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

    	String fileName = "a_example";
        System.out.println("Hash code -- Qualification Round 2018");
        String inputfileName = fileName+".in";
        InputStructure inputStructure = new InputStructure(inputfileName);
        System.out.println(inputStructure);
        //run optim here
        ProblemInstance pI = new ProblemInstance(inputStructure);
        String solution = pI.solve().toString();
        FileHandler.writeOutput("target/"+fileName+".txt", solution);

    }
}
