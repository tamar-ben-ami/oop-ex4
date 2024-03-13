package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Block;

import java.awt.*;

public class Leaf extends Block {
    private static final Color LEAF_COLOR = new Color(50, 200, 30);
    public static final RectangleRenderable LEAF_RENDERABLE = new RectangleRenderable(LEAF_COLOR);

    public static final float LEAF_SIZE = 15;
    public static final Vector2 LEAF_DIMENSION = new Vector2(LEAF_SIZE, LEAF_SIZE);

    private static final Float INITIAL_ANGLE = 10.f;
    private static final Float FINAL_ANGLE = -10.f;

    private static final Float INITIAL_DIM_DOUBLE = 0.7f;
    private static final Float FINAL_DIM_DOUBLE = 1.3f;

    private float cycleLength;
    private int leafCounter;

    public Leaf(Vector2 topLeftCorner, float cycleLength, int leafCounter) {
        super(topLeftCorner, LEAF_RENDERABLE);
        this.cycleLength = cycleLength;
        new ScheduledTask(this,
                (leafCounter) % cycleLength,
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
