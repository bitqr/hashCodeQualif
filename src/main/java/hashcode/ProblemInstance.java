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
            ride.latestEnd = rideInput.latestFinish;
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
                 if(r1!=r2) {
                     int distance = Solution.distance(r1.endRow, r1.endColumn, r2.startRow, r2.startColumn);
                    rideMetrics.get(r1).add(new RideMetric(r2, distance!=0 ? r2.getLength() / distance : r2.getLength()));
                }
            });
        });
    }

    public hashcode.Solution solve(){
        hashcode.Solution solution = new hashcode.Solution(this);

        return solution;
    }
    
   public Solution createEmptySolution() {
        Solution result = new Solution(this);
               
        // Sort rides by earliest start time for first assignation
        rides.sort(Comparator.comparingInt(o -> o.earliestStart));
       
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
        //restartLimit=(int)maxIter;
        int nbIter=100000;
        double t0=10;
        double t=t0;
        double coeff=0.99;
        int displayFreq=1000;
        boolean stopCriterion=false;
        int restartLimit=150000;

        //Counters
        int iteration=0;
        int displayIt=0;
        int restartCounter=0;

        //Best solution
        double currentScore = solution.score;
        double bestScore=currentScore;
        Solution bestSolution = new Solution(this);
        bestSolution.copy(solution);

        while(!stopCriterion){

            //Copy solution and test improvement
            Solution newSolution = new Solution(this);
            newSolution.copy(solution);
            Random rn = new Random();

            //Do some neighbourhood
            Ride rideToReallocate = rides.get(Math.abs(rn.nextInt())%nbRides);
            int vehicleToReallocate = Math.abs(rn.nextInt())%nbVehicles;
            int positionToReallocate = 0;
            if(!newSolution.assignment.get(vehicleToReallocate).isEmpty())
                positionToReallocate = Math.abs(rn.nextInt())%newSolution.assignment.get(vehicleToReallocate).size();
            int vehicleToDischarge = newSolution.ridesToVehicles.get(rideToReallocate);
            newSolution.neighbour(rideToReallocate,vehicleToReallocate,positionToReallocate);
            newSolution.evaluateVehicleOnly(vehicleToDischarge);
            newSolution.evaluateVehicleOnly(vehicleToReallocate);
            double newScore = newSolution.score;
            //newSolution.neighbour2(rideToReallocate,positionToReallocate);
            //newSolution.evaluateVehicleOnly(newSolution.ridesToVehicles.get(rideToReallocate));

            //Update current
            double delta = newScore-currentScore;
            double acceptanceProbability = Math.exp(delta/t);
            if(delta>0 || (delta<0 && rn.nextDouble()<acceptanceProbability)){
                //Accept solution please
                currentScore=newScore;
                solution.copy(newSolution);
                iteration=0;
            }
            else{
                restartCounter++;
            }

            //Update best
            if(newScore>bestScore){
                bestScore=newScore;
                bestSolution.copy(newSolution);
                System.out.println("Best updated: "+bestScore);
                iteration=0;
            }

            t=t*coeff;

            if(displayIt%displayFreq==0){
                System.out.println("Current Score: "+currentScore + "(Best="+bestScore+")"+"\t (T="+t+")");
             }

            //Stop criterion
            if(iteration>=nbIter)
                stopCriterion=true;

            iteration++;
            displayIt++;

        }

        System.out.println("Best solution value = "+bestSolution.score);
//        System.out.println("Best solution value = "+bestSolution.evaluate2());
        return bestSolution;
    }


}
