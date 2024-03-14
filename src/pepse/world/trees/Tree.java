package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.function.Consumer;

public class Tree {
    public static final int LEAF_BUFFER = 3;
    public static final int NUM_OF_FRUITS = 4;
    public static final int BOUND_COLOR_DELTA = 20;
    private final static Random random = new Random();

    private Trunk trunk;
    private Leaf[] leaves;
    private Fruit[] fruits;
    private int numFruits;
    private int numLeaves;


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

    public Leaf getLeaf(int i) {
        return leaves[i];
    }

    public Trunk getTrunk() {
        return trunk;
    }

    public void onJump() {
        trunk.updateColor();
        for (int i = 0; i < numLeaves; i++) {
            leaves[i].rotateLeafIn90();
        }
        for (int i = 0; i < numFruits; i++) {
            fruits[i].updateColor();
        }
    }

    private void addFruit(int idx, Fruit fruit) {
        fruits[idx] = fruit;
    }

    private Fruit getFruit(int idx) {
        return fruits[idx];
    }

    public static Tree createTree(float groundCoordX, float groundCoordY, int numLeavesInRow, int numFruits, float height,
                                  GameObjectCollection gameObjects, float cycleLength, Consumer<Float> eatFruitCallback) {
        Tree tree = new Tree(new Vector2(groundCoordX, groundCoordY),
                height, numLeavesInRow, cycleLength, numFruits);
        gameObjects.addGameObject(tree.getTrunk());
        for (int i = 0; i < numLeavesInRow*numLeavesInRow; i ++) {
            gameObjects.addGameObject(tree.getLeaf(i), -5);
        }
        HashSet<Integer> fruitsIndexes = new HashSet<>();
        while (fruitsIndexes.size() < numFruits)  {
            Integer j = random.nextInt(numLeavesInRow * numLeavesInRow);
            if (!fruitsIndexes.contains(j)) {
                fruitsIndexes.add(j);
                tree.addFruit(fruitsIndexes.size() - 1,
                        new Fruit(tree.getLeaf(j).getTopLeftCorner(), eatFruitCallback, random.nextInt(BOUND_COLOR_DELTA)));
                gameObjects.addGameObject(tree.getFruit(fruitsIndexes.size() - 1));
            }
        }
        return tree;
    }
}
