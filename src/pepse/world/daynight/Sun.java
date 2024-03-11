package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class Sun {
    private static final String SUN_TAG = "sun";
    private static final Vector2 SUN_COORD = new Vector2(50, 50);
    private static final float SIZE_FACTOR = 5.f;
    private static final Float FINAL_ANGLE = (float) (Math.PI * 4) / 7;
    private static final Float FIRST_ANGLE = 0.f;
    private static final float ELLIPSE_FACTOR = 1.2f;
    private static float noonHeight;
    private static float windowHeight;


    /**
     * Creates Sun object
     * @param windowDimensions dimensions of the window
     * @param cycleLength the length of a cycle
     * @return The Sun object
     */
    public static GameObject create(Vector2 windowDimensions,
                                    float cycleLength){
        Vector2 sunDimensions = new Vector2(windowDimensions.y() / SIZE_FACTOR,
                windowDimensions.y() / SIZE_FACTOR);
        windowHeight = windowDimensions.y();
        noonHeight = windowDimensions.y() - SUN_COORD.y();
        // Creates and adds sun to game and sets attributes
        GameObject sun = new GameObject(SUN_COORD, sunDimensions,
                new OvalRenderable(Color.YELLOW));
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN_TAG);
        return sun;
    }

}
