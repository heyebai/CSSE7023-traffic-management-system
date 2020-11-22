package tms.network;

import tms.intersection.Intersection;
import tms.route.Route;
import tms.sensors.Sensor;
import tms.util.DuplicateSensorException;
import tms.util.IntersectionNotFoundException;
import tms.util.InvalidOrderException;
import tms.util.RouteNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a network of intersections connected by routes.
 */
public class Network {
    /** intersections that form the network */
    private List<Intersection> intersections;
    /** default yellow time in the network */
    private int yellowTime = 1;

    /**
     *Creates a new empty network with no intersections.
     */
    public Network() {
        intersections = new ArrayList<>();
    }

    /**
     * Returns the yellow time for all traffic lights in this network.
     *
     * @return traffic light yellow time (in seconds)
     */
    public int getYellowTime() {
        return this.yellowTime;
    }

    /**
     * Sets the time that lights appear yellow between turning from green to
     * red (in seconds) for all new traffic lights added to this network.
     *
     * @param yellowTime new yellow time for all new traffic lights in network
     */
    public void setYellowTime​(int yellowTime) {
        if (yellowTime < 1) {
            throw new IllegalArgumentException("Invalid yellowTime.");
        }
        this.yellowTime = yellowTime;
    }

    /**
     * Creates a new intersection with the given ID and adds it to this network.
     *
     * @param id identifier of the intersection to be created
     * @throws IllegalArgumentException if an intersection already exists with
     * the given ID, or if the given ID contains the colon character (:), or if
     * the id contains only whitespace (space, newline, tab, etc.) characters.
     */
    public void createIntersection(String id) throws IllegalArgumentException {
        for (Intersection intersection : intersections) {
            if (id.equals(intersection.getId())) {
                throw new IllegalArgumentException("An intersection already " +
                        "exists with ID: " + id);
            }
        }
        if (id.indexOf(":") != -1 || id.isBlank()) {
            throw new IllegalArgumentException("Invalid intersection id.");
        }
        Intersection newIntersection = new Intersection(id);
        intersections.add(newIntersection);
    }

    /**
     * Creates a connecting route between the two intersections with the given
     * IDs.
     *
     * @param from ID of origin intersection
     * @param to ID of destination intersection
     * @param defaultSpeed speed limit of the route to create
     * @throws IntersectionNotFoundException if no intersection exists with an
     * ID of 'from' or 'to'
     * @throws IllegalStateException if a route already exists between the given
     * two intersections
     * @throws IllegalArgumentException if defaultSpeed is negative
     */
    public void connectIntersections(String from, String to, int defaultSpeed)
            throws IntersectionNotFoundException, IllegalStateException,
            IllegalArgumentException {
        Intersection newRouteFrom = findIntersection​(from);
        Intersection newRouteTo = findIntersection​(to);
        for (Route route : newRouteTo.getConnections()) {
            if (route.getFrom().equals(newRouteFrom)) {
                throw new IllegalStateException("Connection already exists " +
                        "from intersection " + from);
            }
        }
        if (defaultSpeed < 0) {
            throw new IllegalArgumentException("DefaultSpeed cannot be " +
                    "negative.");
        }
        newRouteTo.addConnection(newRouteFrom, defaultSpeed);
    }

    /**
     * Adds traffic lights to the intersection with the given ID.
     *
     * @param intersectionId ID of intersection to add traffic lights to
     * @param duration number of seconds between traffic light cycles
     * @param intersectionOrder list of origin intersection IDs, traffic lights
     *                          will go green in this order
     * @throws IntersectionNotFoundException if no intersection with the given
     * ID exists
     * @throws InvalidOrderException if the order specified is not a permutation
     * of the intersection's incoming routes; or if order is empty
     * @throws IllegalArgumentException if the given duration is less than the
     * network's yellow time plus one
     */
    public void addLights(String intersectionId, int duration,
                           List<String> intersectionOrder)
            throws IntersectionNotFoundException, InvalidOrderException,
            IllegalArgumentException {
        Intersection intersection = findIntersection​(intersectionId);
        if (duration < (yellowTime + 1)) {
            throw new IllegalArgumentException("The duration cannot be " +
                    "less than " + (yellowTime + 1));
        }
        List<Route> routeOrder = new ArrayList<>();
        for (String from : intersectionOrder) {
            try {
                routeOrder.add(getConnection​(from, intersectionId));
            } catch (RouteNotFoundException e) {
                throw new InvalidOrderException("no route exists between the " +
                        "two given intersections.");
            }
        }
        intersection.addTrafficLights(routeOrder, yellowTime, duration);
    }

    /**
     * A helper checks whether the a route exists between the two given
     * intersections.
     *
     * @param from ID of origin intersection
     * @param to ID of destination intersection
     * @return Route that origin intersection is "from" and destination
     * intersection is "to".
     * @throws IntersectionNotFoundException if no intersection exists with an
     * ID given by 'to' or 'from'
     * @throws RouteNotFoundException if no route exists between the two given
     * intersections
     */
    private Route routeChecker(String from, String to)
            throws IntersectionNotFoundException, RouteNotFoundException {
        findIntersection​(from);
        findIntersection​(to);
        return getConnection​(from, to);
    }

    /**
     * Adds an electronic speed sign on the route between the two given
     * intersections.
     *
     * @param from ID of origin intersection
     * @param to ID of destination intersection
     * @param initialSpeed initial speed to be displayed on speed sign
     * @throws IntersectionNotFoundException if no intersection exists with an
     * ID given by 'from' or 'to'
     * @throws RouteNotFoundException if no route exists between the two given
     * intersections
     */
    public void addSpeedSign(String from, String to, int initialSpeed)
            throws IntersectionNotFoundException, RouteNotFoundException {
        Route route = routeChecker(from, to);
        route.addSpeedSign(initialSpeed);
    }

    /**
     * Sets the speed limit on the route between the two given intersections.
     *
     * @param from ID of origin intersection
     * @param to ID of destination intersection
     * @param newLimit new speed limit
     * @throws IntersectionNotFoundException if no intersection exists with an
     * ID given by 'from' or 'to'
     * @throws RouteNotFoundException if no route exists between the two given
     * intersections
     */
    public void setSpeedLimit(String from, String to, int newLimit)
            throws IntersectionNotFoundException, RouteNotFoundException {
        Route route = routeChecker(from, to);
        route.setSpeedLimit(newLimit);
    }

    /**
     * Sets the duration of each green-yellow cycle for the given intersection's
     * traffic lights.
     *
     * @param intersectionId ID of target intersection
     * @param duration new duration of traffic lights
     * @throws IntersectionNotFoundException if no intersection exists with an
     * ID given by 'intersectionId'
     */
    public void changeLightDuration(String intersectionId, int duration)
            throws IntersectionNotFoundException {
        Intersection intersection = findIntersection​(intersectionId);
        intersection.setLightDuration​(duration);
    }

    /**
     * Returns the route that connects the two given intersections.
     *
     * @param from ID of origin intersection
     * @param to ID of destination intersection
     * @return Route that connects these intersections
     * @throws IntersectionNotFoundException if no intersection exists with an
     * ID given by 'to' or 'from'
     * @throws RouteNotFoundException if no route exists between the two given
     * intersections
     */
    public Route getConnection​(String from, String to)
            throws IntersectionNotFoundException, RouteNotFoundException {
        Intersection intersectionTo = findIntersection​(to);
        Intersection intersectionFrom = findIntersection​(from);
        Route routeFound = null;
        for (Route route : intersectionTo.getConnections()) {
            if (route.getFrom().equals(intersectionFrom)) {
                routeFound = route;
            }
        }
        if (routeFound == null) {
            throw new RouteNotFoundException("No route exists between the " +
                    "two given intersections.");
        }
        return routeFound;
    }

    /**
     * Adds a sensor to the route between the two intersections with the given
     * IDs.
     *
     * @param from ID of intersection at which the route originates
     * @param to ID of intersection at which the route ends
     * @param sensor sensor instance to add to the route
     * @throws DuplicateSensorException if a sensor already exists on the route
     * with the same type
     * @throws IntersectionNotFoundException if no intersection exists with an
     * ID given by 'from' or 'to'
     * @throws RouteNotFoundException if no route exists between the given
     * to/from intersections
     */
    public void addSensor(String from, String to, Sensor sensor)
            throws DuplicateSensorException, IntersectionNotFoundException,
            RouteNotFoundException {
        Route route = routeChecker(from, to);
        route.addSensor(sensor);
    }

    /**
     * Returns the congestion level on the route between the two given
     * intersections.
     *
     * @param from ID of origin intersection
     * @param to ID of destination intersection
     * @return congestion level (integer between 0 and 100) of connecting route
     * @throws IntersectionNotFoundException if no intersection exists with an
     * ID given by 'from' or 'to'
     * @throws RouteNotFoundException if no connecting route exists between the
     * given two intersections
     */
    public int getCongestion​(String from, String to)
            throws IntersectionNotFoundException, RouteNotFoundException {
        Route route = routeChecker(from, to);
        return route.getCongestion();
    }

    /**
     * Attempts to find an Intersection instance in this network with the same
     * identifier as the given 'id' string.
     *
     * @param id intersection identifier to search for
     * @return the intersection that was found (if one was found)
     * @throws IntersectionNotFoundException if no intersection could be found
     * with the given identifier
     */
    public Intersection findIntersection​(String id)
            throws IntersectionNotFoundException {
        Intersection intersectionFound = null;
        for (Intersection intersection : intersections) {
            if (intersection.getId().equals(id)) {
                intersectionFound = intersection;
            }
        }
        if (intersectionFound == null) {
            throw new IntersectionNotFoundException("No intersection could " +
                    "be found with the given identifier");
        }
        return intersectionFound;
    }

    /**
     * Creates a new connecting route in the opposite direction to an existing
     * route.
     *
     * @param from ID of intersection that the existing route starts at
     * @param to ID of intersection that the existing route ends at
     * @throws IntersectionNotFoundException if no intersection exists with the
     * ID given by 'from' or 'to'
     * @throws RouteNotFoundException if no route currently exists between given
     * two intersections
     */
    public void makeTwoWay​(String from, String to)
            throws IntersectionNotFoundException, RouteNotFoundException {
        Route route = routeChecker(from, to);
        int defaultSpeed = route.getSpeed();
        connectIntersections(to, from, defaultSpeed);
        if (route.hasSpeedSign()) {
            int displayedSpeed = route.getSpeed();
            Route newRoute = routeChecker(to, from);
            newRoute.addSpeedSign(displayedSpeed);
        }
    }

    /**
     * Returns true if and only if this network is equal to the other given
     * network.
     *
     * @param obj other object to compare equality
     * @return true if equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Network network = (Network) obj;
        if (intersections.size() == network.intersections.size()
                && intersections.containsAll(network.intersections)
                && network.intersections.containsAll(intersections)) {
            return true;
        }
        return false;
    }

    /**
     * Returns the hash code of this network.
     *
     * @return hash code of the network
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(intersections.size());
        for (int i = 0; i < intersections.size(); i++) {
            result += 31 * Objects.hash(intersections.get(i));
        }
        return result;
    }

    /**
     * Returns the string representation of this network.
     *
     * @return string representation of this network
     */
    public String toString() {
        int numRoute = 0;
        List<String> routeStrings = new ArrayList<>();
        for (Intersection intersection : intersections) {
            numRoute += intersection.getConnections().size();
            for (Route route : intersection.getConnections()) {
                routeStrings.add(route.toString());
            }
        }
        String str = String.format("%d\n%d\n%d", intersections.size(),
                numRoute, this.yellowTime);
        List<String> intersectionStrings = new ArrayList<>();
        for (Intersection intersection : intersections) {
            intersectionStrings.add(intersection.toString());
        }
        Collections.sort(intersectionStrings);
        for (String intersectionString : intersectionStrings) {
            str += System.lineSeparator();
            str += intersectionString;
        }
        Collections.sort(routeStrings);
        for (String routeString : routeStrings) {
            str += System.lineSeparator();
            str += routeString;
        }
        return str;
    }

    /**
     * Returns a new list containing all the intersections in this network.
     *
     * @return list of all intersections in this network
     */
    public List<Intersection> getIntersections() {
        return new ArrayList<>(this.intersections);
    }
}
