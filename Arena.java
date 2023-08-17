
package DroneSim;
/**
 * Tracks entities in arena and changes them when needed
 */

import javafx.scene.paint.Color;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Constructor for DroneArena
 */
public class Arena {
    private final ArrayList < Entities > droneTot; // array for all entities
    double dx;
    double dy;
    double sizeOfX;
    double sizeofY; // size of the arena
    Random rand = new Random(); // generate random

    /**
     * creates the arena with specified parameters
     *
     * @param areaX
     * @param areaY
     */
    Arena(double areaX, double areaY) {
        sizeOfX = areaX;
        sizeofY = areaY;
        droneTot = new ArrayList < > (); // creates an array for total drones
    }

    /**
     * creates shapes on canvas
     *
     * @param graphc
     */
    public void createArena(Canvas graphc) {
        for (Entities d: droneTot) {
            if (d instanceof Drone) {
                graphc.fillCol(Color.BLACK); // sets a colour for the drones
                d.droneToCanv(graphc); // then draws them
            }
            if (d instanceof Object) { // if statement: if there is an obstacle then fill it in colour and draw it
                graphc.fillCol(Color.DARKCYAN); //sets colour for obstacle
                d.droneToCanv(graphc); // then draws them
            }
        }
    }

    /**
     * runs droneCol to check for collision
     */
    public void droneCol() {
        for (Entities allD: droneTot) {
            allD.checkDrone(this); // for loop to check collisions
        }
    }

    /**
     * changes the direction of drones if collision
     */

    public void droneAdj() {
        for (Entities adjD: droneTot) adjD.adjustDrone();
    }

    /**
     * gives details of current drones
     *
     * @return
     */
    public ArrayList < String > droneInf() {
        ArrayList < String > infoRet = new ArrayList < > (); // array list used for drone info
        for (Entities arrD: droneTot) {
            infoRet.add(arrD.toString()); // array list to store drone info
        }
        return infoRet;
    }

    /**
     * adding in drones and objects
     *
     * @param type
     */
    public void addDO(char type) {
        dy = rand.nextInt((int)(sizeofY));
        dx = rand.nextInt((int)(sizeOfX)); //rand is used to place them in random parts of the arena
        if (type == 'd') {
            droneTot.add(new Drone(dx, dy, 10, 25, 1));
            //drone z, d, s contain different information
        }
        if (type == 'o') {
            droneTot.add(new Object(dx, dy, 15));
        }
    }

    /**
     * slow done drone
     */

    public void minusSpeed() {
        for (Entities m: droneTot) {
            if (m instanceof Drone && ((Drone) m).speed >= 1) {
                ((Drone) m).speed -= 1;
                //if button pressed then -1 to speed
            }
        }
    }

    /**
     * speed up drone
     */
    public void plusSpeed() {
        for (Entities j: droneTot) {
            if (j instanceof Drone && ((Drone) j).speed >= 0) {
                ((Drone) j).speed += 1;
                //if button is pressed then +1 to speed
            }
        }
    }

    /**
     * resets arena
     */
    public void clearA() {
        droneTot.clear(); //clear
    }

    /**
     * Used for saving file
     *
     * @throws IOException
     */
    void save() throws IOException {
        JFileChooser fileC = new JFileChooser("C:\\Users\\facef\\Documents"); //pick where to save the file
        int input = fileC.showOpenDialog(null); //user inputs where to save
        if (input == JFileChooser.APPROVE_OPTION) { //Approves
            File files = fileC.getSelectedFile(); //open the selected file
            buffWrite(files);
        }
    }

    /**
     * @param write
     * @throws IOException
     */
    void buffWrite(File write) throws IOException {
        FileWriter saver = new FileWriter(write);
        BufferedWriter buffer = new BufferedWriter(saver); //buffer used to save multiple statements
        buffer.write(Integer.toString((int) sizeOfX)); //saving height and width of arena
        buffer.write(",");
        buffer.write(Integer.toString((int) sizeofY));
        buffer.newLine();

        for (Entities d: droneTot) {
            if (d instanceof Drone) {

                buffer.write(Integer.toString((int) d.getterX()));
                buffer.write(","); //Reads X,Y,RADIUS and saves to buffer for each drone using if statement
                buffer.write(Integer.toString((int) d.getterY()));
                buffer.write(",");
                buffer.write(Integer.toString((int) d.getterRadius()));
                buffer.newLine();
            }
        }

        buffer.close();
    }

    /**
     * @throws IOException
     */
    void load() throws IOException {
        JFileChooser fileC = new JFileChooser("C:\\Users\\facef\\Documents"); //Set file directory to save the file
        int selection = fileC.showOpenDialog(null); //To receive user input on where to save
        if (selection == JFileChooser.APPROVE_OPTION) { //Approves to open file
            File uFiles = fileC.getSelectedFile();
            if (fileC.getSelectedFile().isFile()) { // if correct file then go to readfile
                read(uFiles);
            }
        }
    }

    /**
     * @param readF
     * @throws IOException
     */
    void read(File readF) throws IOException {
        String inf;
        FileReader fReader = new FileReader(readF);
        BufferedReader read = new BufferedReader(fReader); // reads file from buffer
        inf = read.readLine();

        if (!droneTot.isEmpty()) {
            droneTot.clear(); //Clears drones
        }

        while (inf != null) { //while there is no data, read the data
            inf = read.readLine();
            String[] numbers = inf.split(","); //Splits data
            int rads = Integer.parseInt(numbers[2]);
            int x = Integer.parseInt(numbers[0]);
            int y = Integer.parseInt(numbers[1]);
            droneTot.add(new Drone(x, y, rads, 25, 1)); //add drone data
        }
        read.close();
    }

    /**
     * finds drone angle
     * x = x position, y = y position, dronRad = radius, droneDir = current angle, curID = current droneID
     *
     * @param x
     * @param y
     * @param droneRad
     * @param droneDir
     * @param curID
     * @return
     */
    public double adjAngle(double x, double y, double droneRad, double droneDir, int curID) {
        double ans = droneDir;

        if (y < droneRad || y > sizeofY - droneRad) {
            ans = -ans; // if ball hit top or bottom, set mirror angle, being -angle
        }

        if (x < droneRad || x > sizeOfX - droneRad) {
            ans = 180 - ans;
        }
        // if ball hits the left or right walls, set mirror angle being 180-angle

        for (Entities ai: droneTot) {
            if (ai.getID() != curID && ai.hit(x, y, droneRad)) {
                ans = 180 * Math.atan2(y - ai.getterY(), x - ai.getterX()) / Math.PI;
            }
            // checks for collisions in all drones. if there is, works out new angle.
        }
        return ans; // returns the angle

    }

}

