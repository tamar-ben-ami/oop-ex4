package pepse;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.*;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.world.*;
import pepse.world.daynight.*;
import danogl.collisions.Layer;
import pepse.world.trees.Trunk;

import java.util.List;

/**
 * The Pepse Game manager class
 * @author tamar, yaara
 * @see GameManager
 */
public class PepseGameManager extends GameManager {
    private static final float CYCLE_LENGTH = 30;

    private Terrain terrian;
    private List<Block> listBlocks;
    private EnergyLevelDisplayer energyLevelDisplayer;

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
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
        GameObject night = Night.create(windowController.getWindowDimensions(), CYCLE_LENGTH);
        gameObjects().addGameObject(night, Layer.BACKGROUND);
        // TODO fix the sun speed
        GameObject sun = Sun.create(windowController.getWindowDimensions(), 100*CYCLE_LENGTH);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND);
        terrian = new Terrain(windowController.getWindowDimensions(), 0);
        listBlocks = terrian.createInRange(0, (int) windowController.getWindowDimensions().x());
        for (Block block : listBlocks) {
            this.gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
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

        var avatar = new Avatar(Vector2.of(0, 0), inputListener, imageReader);
        gameObjects().addGameObject(avatar);
        energyLevelDisplayer = new EnergyLevelDisplayer(avatar::getEnergyLevel);
        gameObjects().addGameObject(energyLevelDisplayer);

//        System.out.println("heights: " );
//        for (int i = 0; i < 1000; i += 20) {
//            System.out.println("x: " + i +", h: " + terrian.groundHeightAt(i) +". ");
//        }
//        Trunk t = new Trunk(Vector2.of(500, 570 + 80), 80);
//        gameObjects().addGameObject(t);
//        Trunk t1 = new Trunk(Vector2.of(880, terrian.groundHeightAt(880) + 80), 80);
//        gameObjects().addGameObject(t1);

    }
}
