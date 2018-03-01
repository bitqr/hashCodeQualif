package hashcode;

public class RideMetric implements Comparable<RideMetric>{
    public Ride ride;
    public double value;

    @Override
    public int compareTo(RideMetric o) {
        return (int)value - (int)o.value;
    }
}
