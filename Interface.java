package DroneSim;

/**
 * Used for creating the GUI for the user
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Interface extends Application {
    private AnimationTimer animTime;
    private Canvas graphicsCon;
    private Arena drArena;
    private VBox rightBoard;

    /**
     * creates space and loads everything
     */

    public static void main(String[] args) {
        Application.launch(args); // starts the GUI

    }

    /**
     * used to update after different uses like pressing buttons
     */
    public void updateC() { // reloads/updates area after using features
        graphicsCon.clearC();
        drArena.createArena(graphicsCon);
    }

    /**
     * Used so that mouse can work as intended and press buttons and menus
     *
     * @param canvas
     */
    void setMouse(javafx.scene.canvas.Canvas canvas) {
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, // create event handler for mouse pressed. The event is pressing the mouse on button
                e -> {
                    updateC();
                    resetInfo();
                });
    }

    /**
     * create titles for about
     */

    private void title1() {
        Alert tAbout = new Alert(AlertType.INFORMATION); // creates title for drop down menu
        tAbout.setContentText("This is a Drone Simulation, created by Dillan Sunil. Student ID: 29007892 "); // used to  display message for about section
        tAbout.setHeaderText("About");
        tAbout.setTitle(null);
        tAbout.showAndWait();
    }

    /**
     * creates title for instructions
     */
    private void title2() {
        Alert tInstructions = new Alert(AlertType.INFORMATION); // creates title for drop down menu
        tInstructions.setContentText("Press start to begin simulation, press pause to pause simulation, press clear to " +
                "clear the simulation press Add Drone to Add a drone, Press add obstacle to add an obstacle, press " +
                "increase speed to increase the speed of the simulation and press decrease speed to decrease the " +
                "speed of the simulation. ");
        tInstructions.setHeaderText("Instructions"); // creating instruction menu title
        tInstructions.setTitle(null);
        tInstructions.showAndWait();
    }

    /**
     * makes buttons for the simulation that will have different abilities
     *
     * @return
     */
    private HBox createButton() { //uses javafx hbox library
        Button dronePlus = new Button("Add Drone");
        dronePlus.setOnAction(event -> { // will set the values of drones
            drArena.addDO('d'); // will add by calling from arena
            updateC(); // redraws arena to add in drone
        });

        Button obsPlus = new Button("Add Object"); // button for adding objects
        obsPlus.setOnAction(event -> {
            drArena.addDO('o');
            updateC(); // redraws the arena to add in object
        });

        Button strt = new Button("Start"); // begin sim
        strt.setOnAction(event -> {
            animTime.start(); // at every timing animation instance, thread is created which calls the handle and makes the anim do something
        });

        Button stp = new Button("Stop"); //stops simulation
        stp.setOnAction(event -> {
            animTime.stop(); // stops animation timer
        });

        Button clear = new Button("Clear");
        clear.setOnAction(event -> {
            animTime.start();
            drArena.clearA(); // clears all shapes within given arena dimensions
        });

        Button spedPlus = new Button("Add Speed"); // arena class called to increase speed by adding 1 to speed
        spedPlus.setOnAction(event -> drArena.plusSpeed());

        Button spedMinus = new Button("Decrease Speed"); // arena class called to decrease speed by minus 1 to speed
        spedMinus.setOnAction(event -> {
            drArena.minusSpeed();
        });

        return new HBox(new Label("      "), strt, stp, clear, new Label("                           " +
                "                      "), dronePlus, obsPlus, new Label("                            " +
                "               "), spedPlus, spedMinus); //used to set the order and positions of buttons
    }

    /**
     * used to control drop down menu and implement save and load features
     *
     * @return
     */
    MenuBar Menu() {

        MenuBar topMenu = new MenuBar(); // create drop down menu on the top

        Menu File = new Menu("File"); // used to create and name file tab

        Menu More = new Menu("More"); // used to create and name more tab
        MenuItem Instruction = new MenuItem("Instructions"); // creates instructions button under more tab
        Instruction.setOnAction(actionEvent -> title2()); // retrieves info from title2

        More.getItems().addAll(Instruction);

        MenuItem Load = new MenuItem("Load");
        Load.setOnAction(t -> {
            try { // Exception Handling
                drArena.load(); // calls loadfile from arena class
            } catch (IOException e) {
                e.printStackTrace(); //prints the stack trace of the instance
            }
        });

        MenuItem Save = new MenuItem("Save");
        Save.setOnAction(t -> {
            try {
                drArena.save(); // calls savefile from arena class
            } catch (IOException e) {
                e.printStackTrace(); // else print stack error
            }
        });

        MenuItem About = new MenuItem("About"); //creates about button under file tab
        About.setOnAction(actionEvent -> title1()); // retrieves info from title1

        MenuItem Exit = new MenuItem("Exit");
        Exit.setOnAction(t -> {
            animTime.stop(); //stops the anim timer
            System.exit(0); //ends program
        });

        File.getItems().addAll(Load, Save, About, Exit); // add elements to drop down menu of file tab

        topMenu.getMenus().addAll(File, More); //adds File and More tab
        return topMenu;
    }

    /**
     * @param primaryStage
     */
    public void start(Stage primaryStage) {

        BorderPane bPane = new BorderPane(); // create borderpane object
        bPane.setPadding(new Insets(0, 0, 5, 0)); // sets out the positions for the whole pane

        bPane.setTop(Menu()); // creates menu which is put into relevant part of GUI (Top)

        Scene sc = new Scene(bPane, 810, 620); // set dimensions for the whole program box that opens
        bPane.prefHeightProperty().bind(sc.heightProperty());
        bPane.prefWidthProperty().bind(sc.widthProperty());

        Group boardPos = new Group(); // create group with canvas
        bPane.setLeft(boardPos); // sets sim canvas on left
        javafx.scene.canvas.Canvas canvas = new javafx.scene.canvas.Canvas(605, 500); // Sets position of canvas where simulation takes place
        boardPos.getChildren().add(canvas);

        graphicsCon = new Canvas(canvas.getGraphicsContext2D(), 900, 520); // defines area for graphic canvas

        drArena = new Arena(420, 420); // defines size of the arena
        updateC();

        rightBoard = new VBox();
        rightBoard.setPadding(new Insets(0, 70, 80, 2)); // sets gap between graphics canvas, menu and right pane
        rightBoard.setAlignment(Pos.TOP_LEFT); // Sets the alignment of the text on right pane

        bPane.setBottom(createButton()); // set botton panel up with buttons
        bPane.setRight(rightBoard); // put in right pane

        animTime = new AnimationTimer() { // anim timer handles main stage loop
            public void handle(long currentNanoTime) {
                drArena.droneCol(); // runs check drones from arena class and updates info
                drArena.droneAdj(); // runs adjust drones from arena class

                updateC(); // update anim timer
                resetInfo(); // display drone and obstacle info
            }

        };
        setMouse(canvas); // sets mouse handler

        primaryStage.setTitle("Drone Simulation"); // title that appears at top of the window
        primaryStage.setScene(sc); // sets scene that will be used eg window
        primaryStage.show(); // shows the window
    }

    /**
     * info from arena is presented on the right pane
     */
    public void resetInfo() {
        rightBoard.getChildren().clear(); // clears whats in the right pane
        ArrayList < String > droneTotal = drArena.droneInf();
        for (String k: droneTotal) {
            Label lab = new Label(k); // send info to label
            rightBoard.getChildren().add(lab); // add label to right pane
        }
    }

}