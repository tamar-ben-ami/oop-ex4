package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Avatar;

import java.awt.*;
import java.util.Objects;

import static pepse.util.ColorSupplier.approximateColor;
import static pepse.world.trees.Leaf.LEAF_DIMENSION;

/**
 * A factory for Fruit GameObject
 * @author tamar, yaara
 * @see GameObject
 */
public class Fruit extends GameObject {
    private static final float SLEEP_TIME = 30;
    private static final float ENERGY_TO_ADD = 10;
    private static final Color[] FRUIT_COLORS = new Color[] {Color.RED, Color.PINK};
    private boolean isEaten = false;
    private final Renderable renderable;
    private final int colorDelta;
    private int colorIdx = 0;

    /**
     * Constructs a Fruit
     * @param topLeftCorner The top left corner of the fruit
     * @param colorDelta The color delta
     */
    public Fruit(Vector2 topLeftCorner, int colorDelta) {
        super(topLeftCorner, LEAF_DIMENSION.mult(0.8f), null);
        this.renderable = new OvalRenderable(approximateColor(FRUIT_COLORS[colorIdx], colorDelta));
        this.colorDelta = colorDelta;
        this.renderer().setRenderable(renderable);
    }

    /**
     * Called when a collision occurs with this GameObject.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (Objects.equals(other.getTag(), Avatar.AVATAR_TAG)) {
            Avatar ava = (Avatar) other;
            ava.addEnergy(ENERGY_TO_ADD);
            this.renderer().setRenderable(null);
            isEaten = true;
            new ScheduledTask(this, SLEEP_TIME, false, () -> {
                this.renderer().setRenderable(renderable);
                isEaten = false;
            });
        }
    }

    /**
     * Updates the GameObject
     */
    public void updateColor() {
        if (isEaten) {
            return;
        }
        colorIdx = 1 - colorIdx;
        this.renderer().setRenderable(
                new OvalRenderable(approximateColor(FRUIT_COLORS[colorIdx], colorDelta)));
    }
}
