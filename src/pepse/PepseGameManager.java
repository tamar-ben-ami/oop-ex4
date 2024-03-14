package pepse;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.*;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.world.*;
import pepse.world.daynight.*;
import danogl.collisions.Layer;
import pepse.world.trees.Flora;
import pepse.world.trees.Leaf;
import pepse.world.trees.Tree;
import pepse.world.trees.Trunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The Pepse Game manager class
 * @author tamar, yaara
 * @see GameManager
 */
public class PepseGameManager extends GameManager {
    private static final float CYCLE_LENGTH = 30;
    private final static Random random = new Random();

    private Terrain terrian;
    private Vector2 windowDimensions;
    private List<Block> listBlocks;
    private EnergyLevelDisplayer energyLevelDisplayer;
    private List<Tree> trees;

    /**
     * Constructor of pepse game.
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
        GameObject sky = Sky.create(windowDimensions);
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
        GameObject night = Night.create(windowDimensions, CYCLE_LENGTH);
        gameObjects().addGameObject(night, Layer.BACKGROUND);
        GameObject sun = Sun.create(windowDimensions, 100*CYCLE_LENGTH);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND);
        terrian = new Terrain(windowDimensions, 0);
        listBlocks = terrian.createInRange(0, (int) windowDimensions.x());
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
     * @param imageReader
     * @param soundReader
     * @param inputListener
     * @param windowController
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
        energyLevelDisplayer = new EnergyLevelDisplayer(avatar::getEnergyLevel);
        gameObjects().addGameObject(energyLevelDisplayer);

        // create random trees at random places with random number of leaves and heights
        trees = new ArrayList<>();
        Flora flora = new Flora(this, 0, terrian::groundHeightAt, avatar::addEnergy);
        for (GameObject obj: flora.createInRange(0, (int) windowDimensions.x())) {
            gameObjects().addGameObject(obj);
        }
        trees.addAll(flora.getTreesList());

    }

    public Vector2 windowDimensions() {
        return windowDimensions;
    }

}
