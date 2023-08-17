package DroneSim;

/**
 * This obstacle class uses abstract methods from entities
 */

public class Object extends Entities {
    double direction; // direction uses angle
    double speed;

    /**
     * @param obsX
     * @param obsY
     * @param obsZ
     */
    public Object(double obsX, double obsY, double obsZ) {
        super(obsX, obsY, obsZ);
    } // x, y and z contain size and x and y

    /**
     * check for collision
     *
     * @param da
     */
    protected void checkDrone(Arena da) {
        direction = da.adjAngle(x, y, radius, direction, IDdrone);
    }

    /**
     * used to get next coordinates and give new direction
     */
    protected void adjustDrone() {
        double newDirection = direction * Math.PI / 180; //put angle in radians as cos and sine require things to be in radians
        double nxtX = x + speed * Math.cos(newDirection);
        x = nxtX;
        double nxtY = y + speed * Math.sin(newDirection);
        y = nxtY;
    }
}
