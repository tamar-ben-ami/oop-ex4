package pepse.avatarEnergizer;

import danogl.*;
import danogl.gui.rendering.*;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;

/**
 * The avatar of the game. The avatar is controlled by the user and can move
 * @author tamar, yaara
 * @see GameObject
 */
public class Avatar extends GameObject {
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -400;
    private static final float GRAVITY = 250;
    private static final float TIME_BETWEEN_ANIMATION = 0.2f;
    private static final float MAX_ENERGY = 100;
    private static final float JUMP_ENERGY_LOSS = 10;
    private static final float MOVE_ENERGY_LOSS = 0.5F;
    private static final float STANDING_ENERGY_GAIN = 1;

    private static final String[] IDLE_IMAGE_PATHS = {"src/assets/idle_0.png",
            "src/assets/idle_1.png", "src/assets/idle_2.png", "src/assets/idle_3.png"};
    private static final String[] JUMP_IMAGE_PATHS = {"src/assets/jump_0.png",
            "src/assets/jump_1.png", "src/assets/jump_2.png", "src/assets/jump_3.png"};
    private static final String[] RUN_IMAGE_PATHS = {"src/assets/run_0.png",
            "src/assets/run_1.png", "src/assets/run_2.png", "src/assets/run_3.png",
            "src/assets/run_4.png", "src/assets/run_5.png"};
    /**
     * The tag that will be used to identify the avatar
     */
    public static final String AVATAR_TAG = "avatar";

    private final UserInputListener inputListener;
    private float energyLevel;
    private final Renderable idleRenderer;
    private final Renderable jumpRenderer;
    private final Renderable runRenderer;
    private final Runnable onJumpCallback;

    /**
     * Gets the energy level of the avatar
     * @return The energy level of the avatar
     */
    public float getEnergyLevel() {
        return energyLevel;
    }

    /**
     * Adds energy to the avatar
     * @param energyToAdd The amount of energy to add
     */
    public void addEnergy(float energyToAdd) {
        energyLevel += energyToAdd;
        if (energyLevel > MAX_ENERGY) {
            energyLevel = MAX_ENERGY;
        }
    }

    /**
     * Constructor for Avatar
     * @param pos The position of the avatar when it is created
     * @param inputListener The input listener that will be used to control the avatar
     * @param imageReader The image reader that will be used to read the images
     * @param onJumpCallback The callback that will be called when the avatar jumps
     */
    public Avatar(Vector2 pos, UserInputListener inputListener,
                  ImageReader imageReader, Runnable onJumpCallback) {
        super(pos, Vector2.ONES.mult(50), null);
        idleRenderer = new AnimationRenderable(IDLE_IMAGE_PATHS,
                imageReader, true, TIME_BETWEEN_ANIMATION);
        jumpRenderer = new AnimationRenderable(JUMP_IMAGE_PATHS,
                imageReader, true, TIME_BETWEEN_ANIMATION);
        runRenderer = new AnimationRenderable(RUN_IMAGE_PATHS,
                imageReader, true, TIME_BETWEEN_ANIMATION);
        this.renderer().setRenderable(idleRenderer);
        renderer().setIsFlippedHorizontally(true);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.setTag(AVATAR_TAG);
        this.onJumpCallback = onJumpCallback;
        this.inputListener = inputListener;
        energyLevel = MAX_ENERGY;
    }

    /**
     * Updates the avatar's position and velocity based on user input and
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
        boolean didSomething = false;
        float xVel = 0;

        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)
                && energyLevel >= MOVE_ENERGY_LOSS) {
            xVel -= VELOCITY_X;
            energyLevel -= MOVE_ENERGY_LOSS;
            didSomething = true;
            this.renderer().setRenderable(runRenderer);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)
                && energyLevel >= MOVE_ENERGY_LOSS) {
            xVel += VELOCITY_X;
            energyLevel -= MOVE_ENERGY_LOSS;
            didSomething = true;
            this.renderer().setRenderable(runRenderer);
        }
        transform().setVelocityX(xVel);

        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE)
                && getVelocity().y() == 0 &&
                energyLevel >= JUMP_ENERGY_LOSS) {
            transform().setVelocityY(VELOCITY_Y);
            energyLevel -= JUMP_ENERGY_LOSS;
            didSomething = true;
            onJumpCallback.run();
            this.renderer().setRenderable(jumpRenderer);
        }
        if (!didSomething && getVelocity().y() == 0 ) {
            addEnergy(STANDING_ENERGY_GAIN);
            this.renderer().setRenderable(idleRenderer);
        }
    }
}
