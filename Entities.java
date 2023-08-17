package DroneSim;

/**
 * uses classes but no objects
 */

public abstract class Entities {
    static int numDrone = 1;
    protected double radius;
    protected double x;
    protected double y;
    protected int IDdrone;

    /**
     * @param sx
     * @param sy
     * @param sr
     */
    public Entities(double sx, double sy, double sr) {
        x = sx;
        y = sy;
        IDdrone = numDrone++; //when drone added, the drone ID increases by 1
        radius = sr; //radius of drone and object used in checking for collisions
    }

    /**
     * returns position of x
     *
     * @return
     */
    public double getterX() {
        return x;
    }

    /**
     * returns position of y
     *
     * @return
     */
    public double getterY() {
        return y;
    }

    /**
     * returns the radius
     *
     * @return
     */
    public double getterRadius() {
        return radius;
    }

    /**
     * puts drone shape on canvas
     *
     * @param mc
     */
    public void droneToCanv(Canvas mc) {
        mc.droneAdd(x, y, radius);
    }

    /**
     * returns the drones ID
     *
     * @return
     */
    public int getID() {
        return IDdrone;
    }

    /**
     * Object info for pane
     *
     * @return
     */
    public String toString() {
        return "Object at position " + Math.round(x) + "x, " + Math.round(y) + "y";
    }

    /**
     * checks drones for collisions
     *
     * @param v
     */
    protected abstract void checkDrone(Arena v);

    /**
     * adjust drone direction if collision
     */
    protected abstract void adjustDrone();

    /**
     * Checks for collisions, be it with a wall or another drone, or an object
     *
     * @param x1
     * @param y1
     * @param or
     * @return
     */
    public boolean hit(double x1, double y1, double or) {
        return (x1 - x) * (x1 - x) + (y1 - y) * (y1 - y) < (or + (2 * radius)) * (or + (2 * radius)); // collides if distance between drone and x1, y1 is less than radius
    } // drones collide if distance between drone and x1,y1 is less than ist radius + or

}


