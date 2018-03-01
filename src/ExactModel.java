import ilog.concert.*;
import ilog.cplex.IloCplex;

import java.util.ArrayList;
import java.util.List;
import ilog.concert.IloConstraint;
import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearIntExpr;

/**
 * Created by Abdel on 01/03/2018.
 */
public class ExactModel {
    //Just to get a set of planes that can be taken together in a gates cluster
    protected String id;
    protected ProblemInstance instance;
    Solution solution;

    List<Integer> subProblem = new ArrayList<>();
    int size;
    int threshold;
    IloIntVar[] xVars;

    protected IloCplex cplex;

    public ExactModel(ProblemInstance instance, List<Integer> subProblem, int size, int threshold){
        this.instance = instance;
        this.size = size;
        this.subProblem = subProblem;
        this.threshold=threshold;
    }

    /**
     * Build the model
     */
    public void buildAndSolve() throws IloException {
        //Initialize
        cplex = new IloCplex();
        xVars = new IloIntVar[size];

        //Build model
        makeVariables();
        makeConstraints(); //Constraints are created after objectives because some constraints involve variables that are created inside objective generation
        IloLinearIntExpr expr = makeObjectives();
        cplex.addMaximize(expr);
        cplex.exportModel("model.lp");
        cplex.setParam(IloCplex.DoubleParam.EpGap, 0.0000001);
        cplex.setParam(IloCplex.IntParam.Threads, 4);

        //Solve model
        boolean solved = cplex.solve();
        if(solved){
            System.out.println("SubProblem is solved. I write it...");
            writeSolution();
        }
        cplex.end();
    }

    //X_i variables are binary
    public void makeVariables() throws IloException {
        for(int i=0;i<size;i++){
            IloIntVar x = cplex.intVar(0,1);
            x.setName("X("+ subProblem.get(i)+")");
            xVars[i]=x;
            cplex.add(x);
        }
    }

    public void makeConstraints() throws IloException {
        //Need a good threshold here for the constraints
        //We do not want to put together planes for which transitMatrix coefficient is lower than the threshold
/**        for(int i=0;i<size;i++){
 for(int j=i+1;j<size;j++){
 if(gatesMode){
 if(instance.distanceMatrix[subProblem.get(i)][subProblem.get(j)]>threshold){
 IloLinearIntExpr expr = cplex.linearIntExpr();
 expr.addTerm(xVars[i],1);
 expr.addTerm(xVars[j],1);
 IloConstraint constraint = cplex.addLe(expr,1);
 constraint.setName("stableConstraint("+ subProblem.get(i)+","+ subProblem.get(j)+")");
 cplex.add(constraint);
 }
 }
 else{
 if(instance.transitMatrix[subProblem.get(i)][subProblem.get(j)]<=threshold){
 IloLinearIntExpr expr = cplex.linearIntExpr();
 expr.addTerm(xVars[i],1);
 expr.addTerm(xVars[j],1);
 IloConstraint constraint = cplex.addLe(expr,1);
 constraint.setName("stableConstraint("+ subProblem.get(i)+","+ subProblem.get(j)+")");
 cplex.add(constraint);
 }
 }
 }
 }
 **/
    }

    //Sum of X_i
    public IloLinearIntExpr makeObjectives() throws IloException {
        IloLinearIntExpr expr = cplex.linearIntExpr();
        for(int i=0;i<size;i++){
            expr.addTerm(xVars[i],1);
        }
        return expr;
    }

    public void writeSolution() throws IloException{
        solution = new Solution(instance);
        for(int i=0;i<size;i++){
            if(cplex.getValue(xVars[i])>0.1){
                //solution.add(subProblem.get(i));
            }
        }
    }

}

