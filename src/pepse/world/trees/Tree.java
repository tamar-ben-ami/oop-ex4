package pepse.world.trees;

import danogl.util.Vector2;

public class Tree {
    public static final int LEAVES_MATRIX_DIMENSION = 6;
    private Trunk trunk;
    private Leaf[] leaves;

    public Tree(Vector2 groundHeight, float height) {
        trunk = new Trunk(groundHeight, height);
        for (int i = 0; i < LEAVES_MATRIX_DIMENSION; i++) {
            for (int j = 0; j < LEAVES_MATRIX_DIMENSION; j++;)
        }
    }
}
