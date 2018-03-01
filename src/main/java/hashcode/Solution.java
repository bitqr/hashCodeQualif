package hashcode;

import java.io.IOException;
import java.util.*;

/**
 * Created by Abdel on 01/03/2018.
 */
public class Solution {

    public Map<Integer, List<Ride>> assignment = new TreeMap<>();
    public Map<Ride, Integer> ridesToVehicles = new HashMap<>();

    ProblemInstance instance;

    public Solution(ProblemInstance instance){
        this.instance = instance;
    }

    public Solution(String fileName, List<Ride> rides) throws IOException {
        FileHandler.parseInput(fileName);
        int nbVeh = FileHandler.inputFileLines.size();
        for (int v = 0; v < nbVeh; v++) {
            int nbRides = FileHandler.getIntAt(v, 0);
            for (int r = 0; r < nbRides; r++) {
                int rideId = FileHandler.getIntAt(v, r + 1);
                Ride ride = null;
                for (int i = 0; i < rides.size(); i++)
                    if (rides.get(i).id == rideId) {
                        ride = rides.get(i);
                        break;
                    }
                List<Ride> vrides = assignment.get(v);
                if (vrides == null) assignment.put(v, vrides = new ArrayList<Ride>());
                vrides.add(ride);
                ridesToVehicles.put(ride, v);
            }
        }
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for (int vehicle: assignment.keySet()) {
            List<Ride> rides = assignment.get(vehicle);
            str.append(rides.size());
            for (Ride ride: rides)
                str.append(' ').append(ride.id);
            str.append('\n');
        }
        return str.toString();
    }

    public void write(){

    }

    public void readInitialSolution(String fileName){

    }

    static int distance(int startRow, int startColumn, int endRow, int endColumn){
        return Math.abs(startRow-endRow)+Math.abs(startColumn-endColumn);
    }

    public double evaluate(){
        double value=0.0;
        for(Integer vehicle : assignment.keySet()){
            int currentStep=0;
            int currentRow=0;
            int currentColumn=0;
            if(currentStep<=instance.nbSteps){
                for(Ride ride : assignment.get(vehicle)){
                    int startOfTrip=currentStep;
                    currentStep+=Math.max(ride.earliestStart,
                            distance(currentRow,currentColumn,ride.startRow,ride.startColumn)) +
                    ride.getLength();
                    currentRow = ride.endRow;
                    currentColumn = ride.endColumn;
                    if(currentStep<ride.latestEnd){
                        value+=ride.getLength();
                        if(startOfTrip==ride.earliestStart)
                            value+=instance.bonus;
                    }
                }
            }
        }
        return value;
    }

    public void copy(Solution other){
        for(Integer vehicle : other.assignment.keySet()){
            this.assignment.put(vehicle,new ArrayList<>());
            for(Ride ride : other.assignment.get(vehicle)){
                this.assignment.get(vehicle).add(ride);
                this.ridesToVehicles.put(ride,vehicle);
            }
        }
    }

    public void neighbour(Ride ride, int vehicle, int position){
        int currentVehicle = ridesToVehicles.get(ride);
        //Remove ride from current vehicle
        assignment.get(currentVehicle).remove(ride);
        //Assign it to the new one
        ridesToVehicles.put(ride,vehicle);
        //At the right position
        assignment.get(vehicle).add(position,ride);
    }

}
