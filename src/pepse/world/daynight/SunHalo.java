package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.Component;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * A factory for SunHalo GameObject
 * @author tamar, yaara
 * @see GameObject
 */
public class SunHalo {
    private static final String SUN_HALO_TAG = "sun_halo";

    /**
     * Creates SunHalo object
     * @param sun The sun object to clap position
     */
    public static GameObject create(GameObject sun){
        Vector2 haloDimensions = new Vector2(sun.getDimensions().x() * 2, sun.getDimensions().y() * 2);
        GameObject sunHalo = new GameObject(Vector2.ZERO, haloDimensions,
                new OvalRenderable(new Color(255, 255, 0, 20)));
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN_HALO_TAG);
        Component component = (deltaTime) -> sunHalo.setCenter(sun.getCenter());
        sunHalo.addComponent(component);

        return sunHalo;
    }

}
