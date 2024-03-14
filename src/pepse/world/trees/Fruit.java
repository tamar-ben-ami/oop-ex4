package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.Avatar;

import java.awt.*;
import java.util.Objects;
import java.util.function.Consumer;

import static pepse.util.ColorSupplier.approximateColor;
import static pepse.world.trees.Leaf.LEAF_DIMENSION;

public class Fruit extends GameObject {
    private static final float SLEEP_TIME = 30;
    private static final float ENERGY_TO_ADD = 10;
    private static final Color[] FRUIT_COLORS = new Color[] {Color.RED, Color.PINK};

    private final Consumer<Float> eatFruitCallback;
    private boolean isEaten = false;
    private final Renderable renderable;
    private final int colorDelta;
    private int colorIdx = 0;

    public Fruit(Vector2 topLeftCorner, Consumer<Float> eatFruitCallback, int colorDelta) {
        super(topLeftCorner, LEAF_DIMENSION.mult(0.8f), null);
        this.eatFruitCallback = eatFruitCallback;
        this.renderable = new OvalRenderable(approximateColor(FRUIT_COLORS[colorIdx], colorDelta));
        this.colorDelta = colorDelta;
        this.renderer().setRenderable(renderable);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (Objects.equals(other.getTag(), Avatar.AVATAR_TAG)) {
            eatFruitCallback.accept(ENERGY_TO_ADD);
            this.renderer().setRenderable(null);
            isEaten = true;
            new ScheduledTask(this, SLEEP_TIME, false, () -> {
                this.renderer().setRenderable(renderable);
                isEaten = false;
            });
        }
    }

    public void updateColor() {
        if (isEaten) {
            return;
        }
        colorIdx = 1 - colorIdx;
        this.renderer().setRenderable(new OvalRenderable(approximateColor(FRUIT_COLORS[colorIdx], colorDelta)));
    }
}
