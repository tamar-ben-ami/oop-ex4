package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Trunk extends GameObject {

    private static final Color TRUNK_COLOR = new Color(100, 50, 20);
    public static final float TRUNK_WIDTH = 30;
    public static final RectangleRenderable TRUNK_RENDERABLE = new RectangleRenderable(TRUNK_COLOR);

    public Trunk(Vector2 groundHeight, float height) {
        super(new Vector2(groundHeight.x(), groundHeight.y() + height), new Vector2(TRUNK_WIDTH, height), TRUNK_RENDERABLE);
    }
}
