package tms.intersection;

import tms.route.Route;
import tms.route.TrafficSignal;
import tms.util.TimedItem;
import tms.util.TimedItemManager;

import java.util.Arrays;
import java.util.List;

public class IntersectionLights implements TimedItem {
    /** a list of incoming routes, the list cannot be empty */
    private List<Route> connections;
    /** time in seconds for which lights will appear yellow */
    private int yellowTime;
    /** time in seconds for which lights will appear yellow and green */
    private int duration;
    /** Simulates seconds passing */
    private int secondsPassed;
    /** the index in connections */
    private int connectionsIndex;

    /**
     * Creates a new set of traffic lights at an intersection.
     *
     * @param connections a list of incoming routes, the list cannot be empty
     * @param yellowTime time in seconds for which lights will appear yellow
     * @param duration time in seconds for which lights will appear yellow and
     *                 green
     */
    public IntersectionLights(List<Route> connections, int yellowTime,
                              int duration) {
        this.connections = connections;
        this.yellowTime = yellowTime;
        this.duration = duration;
        this.secondsPassed = 0;
        this.connectionsIndex = 0;
        this.connections.get(connectionsIndex).setSignal(TrafficSignal.GREEN);

        TimedItemManager.getTimedItemManager().registerTimedItem(this);
    }

    /**
     * Returns the time in seconds for which a traffic light will appear yellow
     * when transitioning from green to red.
     *
     * @return yellow time in seconds for this set of traffic lights
     */
    public int getYellowTime() {
        return yellowTime;
    }

    /**
     * Sets a new duration of each green-yellow cycle.
     *
     * @param duration the new light signal duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
        secondsPassed = 0;
        connections.get(connectionsIndex).setSignal(TrafficSignal.GREEN);
    }

    /**
     * Simulates one second passing and updates the state of this set of
     * traffic lights.
     */
    @Override
    public void oneSecond() {
        secondsPassed++;
        if (secondsPassed % duration == 0) {
            connections.get(connectionsIndex).setSignal(TrafficSignal.RED);
            connectionsIndex++;
            connectionsIndex = connectionsIndex % connections.size();
            connections.get(connectionsIndex).setSignal(TrafficSignal.GREEN);
        } else if (secondsPassed % (duration - yellowTime) == 0) {
            connections.get(connectionsIndex).setSignal(TrafficSignal.YELLOW);
        }
    }

    /**
     * Returns the string representation of this set of IntersectionLights.
     *
     * @return formatted string representation
     */
    @Override
    public String toString() {
        String[] intersectionIds = new String[connections.size()];
        for (int i = 0; i < connections.size(); i++) {
            intersectionIds[i] = connections.get(i).getFrom().getId();
        }
        return duration + ":" + Arrays.toString(intersectionIds)
                .replace("[", "").replace("]", "").replace(", ", ",");
    }
}
