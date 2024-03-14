package pepse;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.*;
import danogl.util.Vector2;
import pepse.world.*;
import pepse.world.daynight.*;
import danogl.collisions.Layer;
import pepse.world.trees.Flora;
import pepse.world.trees.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * The Pepse Game manager class
 * @author tamar, yaara
 * @see GameManager
 */
public class PepseGameManager extends GameManager {
    private static final float CYCLE_LENGTH = 30;
    private Terrain terrain;
    private Flora flora;
    private List<Tree> trees;
    private Vector2 windowDimensions;

    /**
     * Constructor of Pepse game.
     */
    public PepseGameManager(){
        super();
    }

    /**
     * Main method of the pepse game
     * @param args args
     */
    public static void main(String[] args){
        new PepseGameManager().run();
    }


    private void createWorld(WindowController windowController) {
        this.windowDimensions = windowController.getWindowDimensions();
        gameObjects().addGameObject(Sky.create(windowDimensions), Layer.BACKGROUND);
        gameObjects().addGameObject(Night.create(windowDimensions, CYCLE_LENGTH), Layer.BACKGROUND);
        GameObject sun = Sun.create(windowDimensions, 100*CYCLE_LENGTH);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);
        gameObjects().addGameObject(SunHalo.create(sun), Layer.BACKGROUND);
        terrain = new Terrain(windowDimensions, 0);
        List<Block> listBlocks = terrain.createInRange(0, (int) windowDimensions.x());
        for (Block block : listBlocks) {
            this.gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
    }

    private void onJumpCallback() {
        System.out.println("hi");
        for (int i = 0; i < trees.size(); i++) {
            trees.get(i).onJump();
        }
    }

    /**
     * Initializes the pepse game.
     * @param imageReader image reader
     * @param soundReader sound reader
     * @param inputListener input listener
     * @param windowController window controller
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        createWorld(windowController);

        var avatar = new Avatar(Vector2.of(0, 0), inputListener, imageReader, this::onJumpCallback);
        gameObjects().addGameObject(avatar);
        gameObjects().addGameObject(new EnergyLevelDisplayer(avatar::getEnergyLevel));

        // create random trees at random places with random number of leaves and heights
        trees = new ArrayList<>();
        Flora flora = new Flora(0, terrain::groundHeightAt, avatar::addEnergy);
        for (GameObject obj: flora.createInRange(0, (int) windowDimensions.x())) {
            gameObjects().addGameObject(obj);
        }
        trees.addAll(flora.getTreesList());

    }

}
