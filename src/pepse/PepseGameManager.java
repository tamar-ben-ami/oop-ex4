package pepse;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.*;
import danogl.util.Vector2;
import pepse.avatarEnergizer.Avatar;
import pepse.avatarEnergizer.EnergyLevelDisplayer;
import pepse.world.*;
import pepse.world.daynight.*;
import danogl.collisions.Layer;
import pepse.world.trees.Flora;
import java.util.List;

/**
 * The Pepse Game manager class
 * @author tamar, yaara
 * @see GameManager
 */
public class PepseGameManager extends GameManager {
    private static final float CYCLE_LENGTH = 30;
    private Flora flora;

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


    /**
     * Creates the world of the game
     * @param windowController window controller
     */
    private void createWorld(WindowController windowController) {
        Vector2 windowDimensions = windowController.getWindowDimensions();
        gameObjects().addGameObject(Sky.create(windowDimensions), Layer.BACKGROUND);
        gameObjects().addGameObject(Night.create(windowDimensions, CYCLE_LENGTH), Layer.BACKGROUND);
        GameObject sun = Sun.create(windowDimensions, 100*CYCLE_LENGTH);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);
        gameObjects().addGameObject(SunHalo.create(sun), Layer.BACKGROUND);
        Terrain terrain = new Terrain(windowDimensions, 0);
        List<Block> listBlocks = terrain.createInRange(0, (int) windowDimensions.x());
        for (Block block : listBlocks) {
            this.gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
        flora = new Flora(0, terrain::groundHeightAt);
        for (GameObject obj: flora.createInRange(0, (int) windowDimensions.x())) {
            gameObjects().addGameObject(obj);
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
        var avatar = new Avatar(Vector2.of(0, 0), inputListener, imageReader, flora::onJumpCallback);
        gameObjects().addGameObject(new EnergyLevelDisplayer(avatar::getEnergyLevel));
        gameObjects().addGameObject(avatar);
    }

}
