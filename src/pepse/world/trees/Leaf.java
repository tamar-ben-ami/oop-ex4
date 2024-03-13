package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Block;

import java.awt.*;
import java.util.Random;

public class Leaf extends Block {
    private static final Color LEAF_COLOR = new Color(50, 200, 30);
    private static final RectangleRenderable LEAF_RENDERABLE = new RectangleRenderable(LEAF_COLOR);
    private final static Random random = new Random();

    public static final int LEAF_SIZE = 15;
    public static final Vector2 LEAF_DIMENSION = new Vector2(LEAF_SIZE, LEAF_SIZE);

    private static final Float INITIAL_ANGLE = 20f;
    private static final Float FINAL_ANGLE = -20.f;

    private static final Float INITIAL_DIM_DOUBLE = 0.85f;
    private static final Float FINAL_DIM_DOUBLE = 1.15f;

    private float cycleLength;

    public Leaf(Vector2 topLeftCorner, float cycleLength) {
        super(topLeftCorner, LEAF_RENDERABLE, LEAF_SIZE);
        this.cycleLength = cycleLength;
        new ScheduledTask(this,
                (random.nextInt()) % (cycleLength/2),
                true,
                this::rotateLeaf);
    };

    public void rotateLeaf() {
        new Transition<Float>(this,
                (Float angle) -> this.renderer().setRenderableAngle(angle),
                INITIAL_ANGLE, FINAL_ANGLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength / 15,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
        new Transition<Vector2>(this,
                this::setDimensions,
                LEAF_DIMENSION.mult(INITIAL_DIM_DOUBLE), LEAF_DIMENSION.mult(FINAL_DIM_DOUBLE),
                Transition.LINEAR_INTERPOLATOR_VECTOR,
                cycleLength / 15,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
    }
}
