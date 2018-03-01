package hashcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Hash code -- Qualification Round 2018");
        List<String> fileNames = new ArrayList<String>();
        //fileNames.add("a_example.in");
        //fileNames.add("b_should_be_easy.in");
        //fileNames.add("c_no_hurry.in");
        //fileNames.add("d_metropolis.in");
        fileNames.add("e_high_bonus.in");
        for(String fileName : fileNames) {
            InputStructure inputStructure = new InputStructure(fileName);
            System.out.println(inputStructure);
            //run optim here
            ProblemInstance pI = new ProblemInstance(inputStructure);
            //Solution initialSolution = pI.createEmptySolution();
            Solution initialSolution = new Solution("target/"+fileName+"_output",pI);
            Solution simulatedSolution = pI.simulatedAnnealing(initialSolution);
            //String solution = pI.solve().toString();
            FileHandler.writeOutput("target/"+fileName+"_output", simulatedSolution.toString());
            // if (simulatedSolution.toString().equals(new Solution("target/"+fileName+"_output", pI).toString()))
            //    throw new IllegalStateException(simulatedSolution.toString() + '\n' + new Solution("target/"+fileName+"_output", pI).toString());

        }
        FileHandler.writeZip();
    }
}
