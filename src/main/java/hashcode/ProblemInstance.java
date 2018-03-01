package hashcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Abdel on 01/03/2018.
 */
public class ProblemInstance {

    List<Ride> rides = new ArrayList<>();
    int nbSteps;
    int nbVehicles;
    int nbRides;
    int bonus;

    public ProblemInstance(String fileName){

    }

    public Solution solve(){
        Solution solution = new Solution(this);

        return solution;
    }


    /**Simulated annealing template**/
    public Solution simulatedAnnealing(Solution solution){

        //Parameters
        int restartLimit=52000;
        //restartLimit=(int)maxIter;
        int numberOfRestarts=10;
        double t0=10;
        double t=t0;
        double coeff=0.99;
        int displayFreq=1000;
        boolean stopCriterion=false;

        //Counters
        int restartCounter=0;
        int numberOfRestartsCounter=0;
        int iteration=0;

        //Best solution
        double currentScore = solution.evaluate();
        double bestScore=currentScore;
        Solution bestSolution = new Solution(this);
        bestSolution.copy(solution);

        while(!stopCriterion){

            boolean restarted=false;
            //Copy solution and test improvement
            Solution newSolution = new Solution(this);
            newSolution.copy(solution);
            Random rn = new Random();

            if(restartCounter<restartLimit){
                //Do some neighbourhood


            }
            else{//Restart please
                System.out.println("Restart #"+numberOfRestartsCounter);
                System.out.println("----------------------------------------------------------------------------");
                System.out.println("----------------------------------------------------------------------------");
                restartCounter=0;
                numberOfRestartsCounter++;
                t=t0*0.9;
                //Do the restart here
                //TODO
                restarted=true;
            }

            double newScore = newSolution.evaluate();

            //Update current
            double delta = newScore-currentScore;
            double acceptanceProbability = Math.exp(-delta/t);
            if(restarted || delta<0 || (delta>0 && rn.nextDouble()<acceptanceProbability)){
                //Accept solution please
                currentScore=newScore;
                //solution.copy(newSolution);
                restartCounter=0;
            }
            else{
                //Not accepted.
                restartCounter++;
            }
            //Update best
            if(newScore<bestScore){
                bestScore=newScore;
                //bestSolution.copy(newSolution);
                System.out.println("Best updated: "+bestScore);
                restartCounter=0;
            }

            t=t*coeff;

            if(iteration%displayFreq==0){
                System.out.println("Current Score: "+currentScore + "(Best="+bestScore+")"+"\t (T="+t+")");
                System.out.println("Restart="+restartCounter);
             }

            //Stop criterion
            if(numberOfRestartsCounter>=numberOfRestarts)
                stopCriterion=true;

            iteration++;

        }

        return bestSolution;
    }


}
