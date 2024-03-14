package pepse.world.trees;

import danogl.GameObject;

import pepse.util.mathTools;
import pepse.world.Block;

import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static pepse.world.trees.Tree.createTree;

/**
 * The Flora class represents the plant life in the game world.
 * It generates trees, leaves, and fruits to populate the environment.
 */
public class Flora {
    private static final int MAX_LEAVES_IN_ROW = 20;
    public static final int LEAVES_FRUIT_RATIO = 3;
    public static final int LEAVES_CYCLE_LENGTH = 5;
    public static final int HEIGHT_LEAVES_RATIO = 10;
    private final Random random;
    private final Function<Float, Float> getGroundAt;
    private final Consumer<Float> eatFruitCallback;
    private List<Tree> treesList;


    public Flora(int seed, Function<Float, Float> getGroundAt,
                 Consumer<Float> eatFruitCallback) {
        this.random = new Random(seed);
        this.getGroundAt = getGroundAt;
        this.eatFruitCallback = eatFruitCallback;
    }

    public List<GameObject> createInRange(int minX, int maxX) {
        List<GameObject> treeComponenetsList = new ArrayList<GameObject>();
        treesList = new ArrayList<Tree>();
        for (int i = mathTools.clip_min(minX, Block.SIZE);
             i < mathTools.clip_max(maxX, Block.SIZE);
             i += Block.SIZE) {
            if (random.nextDouble() < 0.1) {
                int numLeavesInRow = random.nextInt(MAX_LEAVES_IN_ROW);
                int numFruits = random.nextInt(LEAVES_FRUIT_RATIO * numLeavesInRow);
                float height = HEIGHT_LEAVES_RATIO * numLeavesInRow;
                Tree tree = createTree(i, getGroundAt.apply((float) i), numLeavesInRow, numFruits,
                        height, LEAVES_CYCLE_LENGTH, eatFruitCallback);
                treesList.add(tree);
                treeComponenetsList.addAll(tree.getTreeComponents());
            }
        }
        return treeComponenetsList;
        }

    public List<Tree> getTreesList() {
        return treesList;
    }
}
