package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Sun {
    private static final String SUN_TAG = "sun";
    private static final Vector2 SUN_COORD = new Vector2(50, 50);
    private static final float SIZE_FACTOR = 5.f;


    /**
     * Creates Sun object
     * @param windowDimensions dimensions of the window
     * @param cycleLength the length of a cycle
     * @return The Sun object
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        Vector2 sunDimensions = new Vector2(windowDimensions.y() / SIZE_FACTOR,
                windowDimensions.y() / SIZE_FACTOR);
        // Creates and adds sun to game and sets attributes
        GameObject sun = new GameObject(SUN_COORD, sunDimensions,
                new OvalRenderable(Color.YELLOW));
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN_TAG);
        float halfX = windowDimensions.x() / 2;
        float halfY = windowDimensions.y() * 2 / 3;
        Vector2 cycleCenter = new Vector2(halfX, halfY);
        new Transition<Float>(sun,
            (Float angle) -> sun.setCenter(sun.getCenter().subtract(cycleCenter).rotated(angle).add(cycleCenter)),
            0f, 360f, Transition.LINEAR_INTERPOLATOR_FLOAT, cycleLength,
                Transition.TransitionType.TRANSITION_LOOP, null);
        return sun;
    }

}
