package pepse.world.trees;

import danogl.GameObject;
import danogl.util.Vector2;

import java.sql.Array;
import java.util.ArrayList;

public class Tree {
    public static final int LEAF_BUFFER = 3;
    private Trunk trunk;
    private Leaf[] leaves;

    public Tree(Vector2 groundCoord, float height, int numLeaves, float cycleLength) {
        trunk = new Trunk(groundCoord, height);
        float totalMatrixLength = (numLeaves * Leaf.LEAF_SIZE) + (LEAF_BUFFER * (numLeaves - 1));
        float initX = trunk.getTopLeftCorner().x() + Trunk.TRUNK_WIDTH / 2 - (totalMatrixLength / 2f);
        float initY = trunk.getTopLeftCorner().y() - totalMatrixLength;

        leaves = new Leaf[numLeaves * numLeaves];
        for (int i = 0; i < numLeaves; i++) {
            for (int j = 0; j < numLeaves; j++) {
                leaves[(i * numLeaves) + j] = new Leaf(new Vector2(initX + (i * (Leaf.LEAF_SIZE + LEAF_BUFFER)), initY + (j *  (Leaf.LEAF_SIZE + LEAF_BUFFER))),
                        cycleLength);
            }
        }
    }

    public Leaf getLeaf(int i) {
        return leaves[i];
    }

    public Trunk getTrunk() {
        return trunk;
    }
}
