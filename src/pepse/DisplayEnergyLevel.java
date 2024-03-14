package pepse;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.function.Supplier;

class EnergyLevelDisplayer extends GameObject{
    private static final Vector2 NUM_COUNTER_DIM = new Vector2(30, 30);

    private Supplier<Float> energyLevelGetter;
    private TextRenderable energyImage;

    public EnergyLevelDisplayer(Supplier<Float> energyLevelGetter) {
        super(Vector2.ZERO, NUM_COUNTER_DIM, null);
        energyImage = new TextRenderable(Integer.toString(0) + "%");
        this.renderer().setRenderable(energyImage);
        this.energyLevelGetter = energyLevelGetter;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        energyImage.setString(Float.toString(energyLevelGetter.get()) + "%");
    }
}