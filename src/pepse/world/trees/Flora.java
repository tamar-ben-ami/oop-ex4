package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.PepseGameManager;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;
import pepse.util.mathTools;
import pepse.world.Block;

import java.awt.*;
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
    public static final int SPACE = 20;
    // Colors for trees and fruits
    private static final Color TREE_COLOR = new Color(100, 50, SPACE);
    private static final Color FRUIT_COLOR = new Color(255, 192, 203);
    private static final String TREE = "tree";
    private static final String LEAF = "leaf";
    private static final String FRUIT = "fruit";
    private static final int TREE_WIDTH = 30;
    public static final double PROB_TREE = 0.1;
    public static final double PROB_FRUIT = 0.5;

    // Fruit size
    private final Vector2 FRUIT_SIZE = new Vector2(SPACE, SPACE);

    private static final int TREE_HEIGHT = 100;
    // Default edge length for flora
    private static final int FLORA_EDGE = 120;
    private final Random random;
    private final Function<Float, Float> getGroundAt;
    private final PepseGameManager pepseGameManager;
    private final Consumer<Float> eatFruitCallback;
    private List<Tree> treesList;


    public Flora(PepseGameManager pepseGameManager, int seed, Function<Float, Float> getGroundAt,
                 Consumer<Float> eatFruitCallback) {
        this.random = new Random(seed);
        this.getGroundAt = getGroundAt;
        this.pepseGameManager = pepseGameManager;
        this.eatFruitCallback = eatFruitCallback;
    }

    public List<GameObject> createInRange(int minX, int maxX) {
        List<GameObject> treeComponenetsList = new ArrayList<GameObject>();
        treesList = new ArrayList<Tree>();
        for (int i = mathTools.clip_min(minX, Block.SIZE);
             i < mathTools.clip_max(maxX, Block.SIZE);
             i += Block.SIZE) {
            if (random.nextDouble() < 0.1) {
                int numLeavesInRow = random.nextInt(20);
                int numFruits = random.nextInt(numLeavesInRow);
                float height = 150;
                float cycleLength = 30;
                Tree tree = createTree(i, getGroundAt.apply((float) i), numLeavesInRow, numFruits,
                        height, cycleLength, eatFruitCallback);
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
