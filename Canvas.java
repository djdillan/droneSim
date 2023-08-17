package DroneSim;

/**
 * setting canvas to work with gui
 */

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Canvas {
    double width;
    double height; // setting dimensions
    GraphicsContext shapes; // used for generating drone and objects

    /**
     * set attributes
     *
     * @param g
     * @param xCanv
     * @param yCanv
     */
    public Canvas(GraphicsContext g, double xCanv, double yCanv) {
        shapes = g;
        width = xCanv;
        height = yCanv;
    }

    /**
     * used so that colours of different shapes can be changed
     *
     * @param t
     */
    public void fillCol(Color t) {
        shapes.setFill(t);
    }

    /**
     * shows the drone at position x,y with specified fill colour
     *
     * @param x
     * @param y
     * @param ra
     */
    public void droneAdd(double x, double y, double ra) {

        shapes.fillRect(x - ra, y - ra, ra * 2, ra * 2);
    }

    /**
     * clears everything within the height and width or canvas created
     */
    public void clearC() {
        shapes.clearRect(0, 0, height, width);
    }

}

