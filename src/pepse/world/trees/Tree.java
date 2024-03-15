package pepse.world.trees;

import danogl.GameObject;
import danogl.util.Vector2;

import java.util.*;

/**
 * The Tree class
 * @author tamar, yaara
 * @see GameObject
 */
public class Tree {
    public static final int LEAF_BUFFER = 3;
    public static final int BOUND_COLOR_DELTA = 20;
    private final static Random random = new Random();

    private final Trunk trunk;
    private final Leaf[] leaves;
    private final Fruit[] fruits;
    private final int numFruits;
    private final int numLeaves;

    /**
     * Constructor of Tree object
     * @param groundCoord ground coordinate
     * @param height height of the tree
     * @param numLeavesInRow number of leaves in a row
     * @param cycleLength the duration of a day-night cycle
     * @param numFruits number of fruits
     */
    public Tree(Vector2 groundCoord, float height, int numLeavesInRow, float cycleLength, int numFruits) {
        trunk = new Trunk(groundCoord, height);
        float totalMatrixLength = (numLeavesInRow * Leaf.LEAF_SIZE) + (LEAF_BUFFER * (numLeavesInRow - 1));
        float initX = trunk.getTopLeftCorner().x() + Trunk.TRUNK_WIDTH / 2 - (totalMatrixLength / 2f);
        float initY = trunk.getTopLeftCorner().y() - totalMatrixLength;
        this.numFruits = numFruits;
        this.numLeaves = numLeavesInRow * numLeavesInRow;
        this.leaves = new Leaf[numLeaves];
        this.fruits = new Fruit[numFruits];
        for (int i = 0; i < numLeavesInRow; i++) {
            for (int j = 0; j < numLeavesInRow; j++) {
                int colorDelta = random.nextInt(BOUND_COLOR_DELTA);
                leaves[(i * numLeavesInRow) + j] = new Leaf(
                        new Vector2(initX + (i * (Leaf.LEAF_SIZE + LEAF_BUFFER)),
                                initY + (j *  (Leaf.LEAF_SIZE + LEAF_BUFFER))),
                        cycleLength, colorDelta);
            }
        }
    }

    /**
     * Get the leaf in the i-th index
     * @param i
     * @return the leaf in the i-th index
     */
    public Leaf getLeaf(int i) {
        return leaves[i];
    }

    /**
     * Get the fruit in the i-th index
     */
    public void onJump() {
        trunk.updateColor();
        for (int i = 0; i < numLeaves; i++) {
            leaves[i].rotateLeafIn90();
        }
        for (int i = 0; i < numFruits; i++) {
            fruits[i].updateColor();
        }
    }

    /**
     * Add fruit to the tree
     * @param idx index of the fruit
     * @param fruit fruit to add
     */
    private void addFruit(int idx, Fruit fruit) {
        fruits[idx] = fruit;
    }

    /**
     * Get the tree components
     * @return the tree components
     */
    public List<GameObject> getTreeComponents() {
        List<GameObject> treeComponents = new ArrayList<>();
        treeComponents.add(trunk);
        treeComponents.addAll(Arrays.asList(leaves).subList(0, numLeaves));
        treeComponents.addAll(Arrays.asList(fruits).subList(0, numFruits));
        return treeComponents;
    }

    /**
     * Get the tree components
     */
    public static Tree createTree(float groundCoordX, float groundCoordY, int numLeavesInRow, int numFruits,
                                  float height, float cycleLength) {
        Tree tree = new Tree(new Vector2(groundCoordX, groundCoordY),
                height, numLeavesInRow, cycleLength, numFruits);
        HashSet<Integer> fruitsIndexes = new HashSet<>();
        while (fruitsIndexes.size() < numFruits)  {
            Integer j = random.nextInt(numLeavesInRow * numLeavesInRow);
            if (!fruitsIndexes.contains(j)) {
                fruitsIndexes.add(j);
                tree.addFruit(fruitsIndexes.size() - 1,
                        new Fruit(tree.getLeaf(j).getTopLeftCorner(), random.nextInt(BOUND_COLOR_DELTA)));
            }
        }
        return tree;
    }
}
