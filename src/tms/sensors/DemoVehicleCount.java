package tms.sensors;

/**
 * An implementation of a vehicle count sensor.
 */
public class DemoVehicleCount extends DemoSensor implements VehicleCount {

    /**
     * Creates a new vehicle count sensor with the given threshold and data.
     *
     * @param data a non-empty array of data values
     * @param threshold a threshold value that indicates which values represent
     *                  high congestion
     */
    public DemoVehicleCount(int[] data, int threshold) {
        super(data, threshold);
    }

    /**
     * Returns the observed rate of vehicles travelling past this sensor
     * in vehicles per minute.
     *
     * @return the current rate of traffic flow in vehicles/min reported
     * by the vehicle count
     */
    @Override
    public int countTraffic() {
        return this.getCurrentValue();
    }

    /**
     *Calculates the congestion rate as the complement of the percentage given
     * by countTraffic() divided by getThreshold().
     *
     * @return the calculated congestion rate as an integer between 0 and 100
     * inclusive
     */
    @Override
    public int getCongestion() {
        float congestion = (float) this.countTraffic() / this.getThreshold();
        int congestionPct = Math.round(100 - 100 * congestion);
        return Math.min(Math.max(congestionPct, 0), 100);
    }

    /**
     * Returns the string representation of this sensor.
     *
     * @return "VC:threshold:list,of,data,values" where 'threshold' is this
     * sensor's threshold and 'list,of,data,values' is this sensor's data array
     */
    @Override
    public String toString() {
        return "VC" + ":" + super.toString();
    }
}
