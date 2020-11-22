package tms.congestion;

import tms.sensors.Sensor;

import java.util.List;

/**
 * An implementation of a congestion calculator that calculates the average
 * congestion value from all of its sensors.
 */
public class AveragingCongestionCalculator implements CongestionCalculator {
    /** list of sensors to use in congestion calculation */
    private List<Sensor> sensors;

    /**
     * Creates a new averaging congestion calculator for a given list of sensors
     * on a route.
     *
     * @param sensors list of sensors to use in congestion calculation
     */
    public AveragingCongestionCalculator(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    /**
     * Calculates the average congestion level, as returned
     * by Sensor.getCongestion(), of all the sensors stored by this calculator.
     *
     * @return the average congestion
     */
    @Override
    public int calculateCongestion() {
        int averageCongestion;
        int congestion = 0;
        if (sensors.isEmpty()) {
            averageCongestion = 0;
        } else {
            for (Sensor sensor : sensors) {
                congestion += sensor.getCongestion();
            }
            float floatCongestion = (float) congestion / sensors.size();
            averageCongestion = Math.round(floatCongestion);
        }
        return averageCongestion;
    }
}
