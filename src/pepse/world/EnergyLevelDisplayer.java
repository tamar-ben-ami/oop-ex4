package pepse.world;

import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import java.util.function.Supplier;

/**
 * A GameObject that displays the energy level of the avatar.
 * @author tamar, yaara
 * @see GameObject
 */
public class EnergyLevelDisplayer extends GameObject{
    private static final Vector2 NUM_COUNTER_DIM = new Vector2(30, 30);

    private final Supplier<Float> energyLevelGetter;
    private final TextRenderable energyImage;

    /**
     * Constructs the EnergyLevelDisplayer
     * @param energyLevelGetter A supplier that returns the energy level of the avatar
     */
    public EnergyLevelDisplayer(Supplier<Float> energyLevelGetter) {
        super(Vector2.ZERO, NUM_COUNTER_DIM, null);
        energyImage = new TextRenderable(0 + "%");
        this.renderer().setRenderable(energyImage);
        this.energyLevelGetter = energyLevelGetter;
    }

    /**
     * Updates the energy level displayer
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        energyImage.setString(energyLevelGetter.get() + "%");
    }
}