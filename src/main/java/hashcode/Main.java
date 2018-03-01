package hashcode;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Hash code -- Qualification Round 2018");
        String fileName = "a_example.in";
        //String fileName = "b_should_be_easy.in";
        //String fileName = "c_no_hurry.in";
        //String fileName = "d_metropolis.in";
        //String fileName = "e_high_bonus.in";
        InputStructure inputStructure = new InputStructure(fileName);
        System.out.println(inputStructure);
        //run optim here
        ProblemInstance pI = new ProblemInstance(inputStructure);
        Solution initialSolution = pI.createEmptySolution();
        Solution simulatedSolution = pI.simulatedAnnealing(initialSolution);
        //String solution = pI.solve().toString();
        FileHandler.writeOutput("target/"+fileName+"_output", simulatedSolution.toString());
    }
}
