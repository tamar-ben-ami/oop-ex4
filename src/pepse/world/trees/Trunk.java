package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import java.awt.*;
import static pepse.util.ColorSupplier.approximateColor;

/**
 * A factory for Trunk GameObject
 */
public class Trunk extends GameObject {
    static final float TRUNK_WIDTH = 30;
    private static final Color[] TRUNK_COLORS = new Color[] {new Color(100, 40, 20),
                                                            new Color(110, 70, 20)};
    private static final RectangleRenderable TRUNK_RENDERER =
            new RectangleRenderable(approximateColor(TRUNK_COLORS[0]));
    private int colorIdx = 0;

    /**
     * Constructs a Trunk
     * @param groundHeight The height of the ground
     * @param height The height of the trunk
     */
    public Trunk(Vector2 groundHeight, float height) {
        super(new Vector2(groundHeight.x(), groundHeight.y() - height), new Vector2(TRUNK_WIDTH, height), TRUNK_RENDERER);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

    /**
     * Updates the color of the trunk
     */
    public void updateColor() {
        colorIdx = 1 - colorIdx;
        this.renderer().setRenderable(new RectangleRenderable(approximateColor(TRUNK_COLORS[colorIdx])));
    }
}
