package hashcode;

public class RideMetric implements Comparable<RideMetric>{
    public Ride ride;
    public double value;

    public RideMetric(Ride ride, double value) {
        this.ride = ride;
        this.value = value;
    }
    
    @Override
    public int compareTo(RideMetric o) {
        return (int)value - (int)o.value;
    }
}
