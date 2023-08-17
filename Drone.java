package DroneSim;

/**
 * used to build and move drone
 */

public class Drone extends Entities {
    double direction; // direction that drone moves. used as double as drone moves using angles.
    double speed; // speed that drone moves at

    /**
     * size = droneZ, x = droneX, y = droneY, droneD = direction, speed = droneS
     *
     * @param droneX
     * @param droneY
     * @param droneZ
     * @param droneD
     * @param droneS
     */
    public Drone(double droneX, double droneY, double droneZ, double droneD, double droneS) {
        super(droneX, droneY, droneZ); // super is used to refer to parent class objects
        speed = droneS;
        direction = droneD;
    }

    /**
     * displays drone info in right pane
     *
     * @return
     */
    public String toString() { //math.round is used so that numerical data types do not display decimals and will instead display as long data type
        return "Drone " + IDdrone + ", travelling at speed " + Math.round(speed) + " and\ntravelling at " +
                "direction " + Math.round(direction) + " degrees,\nposition: " + Math.round(x) + ", " + Math.round(y); // Build string with details of drone which is displayed in right pane
    }

    /**
     * checks for collision
     *
     * @param coll
     */
    protected void checkDrone(Arena coll) {
        direction = coll.adjAngle(x, y, radius, direction, IDdrone);
    }

    /**
     * Gives drone new direction to move in
     */
    protected void adjustDrone() {
        double newDirection;
        double nxtX;
        double nxtY;
        newDirection = direction * Math.PI / 180; // Convert to radians as cos and sine require things to be in radians
        nxtX = x + speed * Math.cos(newDirection); // Give new x position
        x = nxtX;
        nxtY = y + speed * Math.sin(newDirection); //gives new y position
        y = nxtY;
    }
}