package hashcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Abdel on 01/03/2018.
 */
public class Solution {

    Map<Integer, List<Ride>> assignment = new HashMap<>();

    ProblemInstance instance;

    public Solution(ProblemInstance instance){
        this.instance = instance;
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        return str.toString();
    }

    public void write(){

    }

    public void readInitialSolution(String fileName){

    }

    public double evaluate(){
        double value=0.0;
        for(Integer vehicle : assignment.keySet()){

        }

        return value;
    }

    public void copy(Solution other){
        for(Integer vehicle : assignment.keySet()){
            this.assignment.put(vehicle,new ArrayList<>());
            for(Ride ride : other.assignment.get(vehicle)){
                this.assignment.get(vehicle).add(ride);
            }
        }
    }

}
