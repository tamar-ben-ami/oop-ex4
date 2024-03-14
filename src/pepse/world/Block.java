package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * A block game object. This is a simple GameObject with a Renderable and a
 * @author tamar, yaara
 * @see GameObject
 */
public class Block extends GameObject {
    // constants
    public static final int SIZE = 30;

    /**
     * Construct a Block instance with final size.
     *
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

    public Block(Vector2 topLeftCorner, Renderable renderable, int size) {
        super(topLeftCorner, Vector2.ONES.mult(size), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }
}
