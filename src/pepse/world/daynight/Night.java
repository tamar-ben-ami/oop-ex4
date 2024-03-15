package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * A factory for night GameObject
 * @author tamar, yaara
 * @see GameObject
 */
public class Night {
    private static final String NIGHT_TAG = "night";
    private static final Float MIDNIGHT_OPACITY = 0.5f;
    private static final Float NOON_OPACITY = 0f;

    /**
     * Creator of Night object
     * @param windowDimensions window's dimensions
     * @param cycleLength the duration of a day-night cycle
     * @return Night GameObject
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions,
                new RectangleRenderable(Color.BLACK));
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag(NIGHT_TAG);
        new Transition<Float>(night, night.renderer()::setOpaqueness, NOON_OPACITY,
                MIDNIGHT_OPACITY, Transition.CUBIC_INTERPOLATOR_FLOAT, cycleLength / 2,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
        return night;
    }
}
