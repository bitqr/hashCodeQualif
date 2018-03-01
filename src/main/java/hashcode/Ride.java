package hashcode;

/**
 * Created by Abdel on 01/03/2018.
 */
public class Ride {
    int id;
    int startRow;
    int endRow;
    int startColumn;
    int endColumn;
    int earliestStart;
    int latestEnd;

    int getLength(){
        return Math.abs(startRow-endRow)+Math.abs(startColumn-endColumn);
    }

}
