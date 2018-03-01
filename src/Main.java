import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Hash code -- Qualification Round 2018");
        System.out.println(new InputStructure());
        //run optim here
        ProblemInstance pI = new ProblemInstance("src/resources/input.in");
        String solution = pI.solve().toString();
        FileHandler.writeOutput("target/output.txt", solution);

    }
}
