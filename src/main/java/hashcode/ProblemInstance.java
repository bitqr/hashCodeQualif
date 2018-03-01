package hashcode;

import java.util.ArrayList;
import java.util.*;
import java.util.Random;

/**
 * Created by Abdel on 01/03/2018.
 */
public class ProblemInstance {

    List<hashcode.Ride> rides = new ArrayList<>();
    public Map<Ride, TreeSet<RideMetric>> rideMetrics = new HashMap<>();

    int nbSteps;
    int nbVehicles;
    int nbRides;
    int bonus;
    int nbRows;
    int nbColumns;

    public ProblemInstance(InputStructure inputStructure){
        nbSteps = inputStructure.inputDefinitions.get("steps");
        nbVehicles = inputStructure.inputDefinitions.get("vehicles");
        nbRides = inputStructure.inputDefinitions.get("rides");
        nbColumns = inputStructure.inputDefinitions.get("columns");
        bonus = inputStructure.inputDefinitions.get("bonus");
        nbRows = inputStructure.inputDefinitions.get("rows");
        int id=0;
        for(InputStructure.RideInput rideInput : inputStructure.rides){
            Ride ride = new Ride();
            ride.earliestStart = rideInput.earliestStart;
            ride.startColumn = rideInput.startPosition.column;
            ride.endColumn = rideInput.finishPosition.column;
            ride.startRow = rideInput.startPosition.row;
            ride.endRow = rideInput.finishPosition.row;
            ride.id=id;

            rideMetrics.put(ride, new TreeSet<>());

            id++;
            rides.add(ride);
        }

        // Compute all metrics
        rides.forEach(r1 -> {
            rides.forEach( r2-> {
                rideMetrics.get(r1).add(new RideMetric(r2, r2.getLength() / Solution.distance(r1.endRow, r1.endColumn, r2.startRow, r2.startColumn)));
            });
        });
    }

    public hashcode.Solution solve(){
        hashcode.Solution solution = new hashcode.Solution(this);

        return solution;
    }
    
   public Solution createEmptySolution() {
        Solution result = new Solution(this);

        for(int i=0 ; i<rides.size(); i++) {

            Ride r = rides.get(i);
            
            if(!result.assignment.containsKey(i%nbVehicles)) {
                result.assignment.put(i%nbVehicles, new ArrayList<>());
            }

            result.assignment.get(i%nbVehicles).add(r);
            result.ridesToVehicles.put(r, i%nbVehicles);

        };

        return result;
    }

    /**Simulated annealing template**/
    public Solution simulatedAnnealing(Solution solution){

        //Parameters
        int restartLimit=1;
        //restartLimit=(int)maxIter;
        int numberOfRestarts=1;
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
                Ride rideToReallocate = rides.get(Math.abs(rn.nextInt())%nbRides);
                int vehicleToReallocate = Math.abs(rn.nextInt())%nbVehicles;
                int positionToReallocate = Math.abs(rn.nextInt())%newSolution.assignment.get(vehicleToReallocate).size();
                newSolution.neighbour(rideToReallocate,vehicleToReallocate,positionToReallocate);
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
                solution.copy(newSolution);
                restartCounter=0;
            }
            else{
                //Not accepted.
                restartCounter++;
            }
            //Update best
            if(newScore<bestScore){
                bestScore=newScore;
                bestSolution.copy(newSolution);
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
