package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import java.util.ArrayList;
import java.util.List;
import pepse.util.NoiseGenerator;
import java.awt.*;

/**
 * create all the ground blocks using noise generator
 * @author tamar, yaara
 * @see NoiseGenerator
 */
public class Terrain {
    private static final float INITIAL_HEIGHT = 2.f/3.f;
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    public static final String GROUND_TAG = "ground";
    private final NoiseGenerator noiseGenerator;
    private final float groundHeightAtX0;
    private final Vector2 windowDimensions;


    /**
     * Constructor for terrain
     * @param windowDimensions window dimensions
     * @param seed seed number for randomization
     */
    public Terrain(Vector2 windowDimensions, int seed) {
        this.groundHeightAtX0 = INITIAL_HEIGHT * windowDimensions.y();
        this.windowDimensions = windowDimensions;
        this.noiseGenerator = new NoiseGenerator(seed, (int) groundHeightAtX0);
    }

    /**
     * gets the height of the terrain in the required x value.
     * @param x x
     * @return height of terrain
     */
    public float groundHeightAt(float x) {
        float noise = (float) noiseGenerator.noise(x, Block.SIZE * 7);
        return (int) Math.floor((noise + groundHeightAtX0) / Block.SIZE) * Block.SIZE;
    }

    /**
     * Creates blocks of the terrain
     * @param minX the minimal x val
     * @param maxX the maximal x val
     */
    public  List<Block> createInRange(int minX, int maxX) {
        List<Block> listBlocks = new ArrayList<Block>();
        for (int i = clip_min(minX, Block.SIZE); i < clip_max(maxX, Block.SIZE); i += Block.SIZE) {
            for (int j = (int) groundHeightAt(i); j < clip_max((int)windowDimensions.y(), Block.SIZE); j += Block.SIZE) {
                RectangleRenderable blockColor =
                        new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
                Block block = new Block(new Vector2(i, j), blockColor);
                block.setTag(GROUND_TAG);
                listBlocks.add(block);
            }
        }
        return listBlocks;
    }

    /**
     * take the closest value before the given value that is a multiple of the divider
     */
    public static int clip_min(int value, int divider) {
        return value - value % divider;
    }

    /**
     * take the closest value after the given value that is a multiple of the divider
     */
    public static int clip_max(int value, int divider) {
        return value + divider - value % divider;
    }
}