package pepse;

import danogl.*;
import danogl.collisions.Layer;
import danogl.components.*;
import danogl.gui.*;
import danogl.gui.rendering.*;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Avatar  extends GameObject {
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -400;
    private static final float GRAVITY = 250;
    private static final float TIME_BETWEEN_ANIMATION_CLIPS = 0.125f;
    private static final float JUMP_ENERGY_LOSS = 10;
    private static final float MOVE_ENERGY_LOSS = 0.5F;
    private static final float STANDING_ENERGY_GAIN = 1;
    public static final String[] IDLE_IMAGE_PATHS = {"src/assets/idle_0.png", "src/assets/idle_1.png",
            "src/assets/idle_2.png", "src/assets/idle_3.png"};
    public static final String[] JUMP_IMAGE_PATHS = {"src/assets/jump_0.png", "src/assets/jump_1.png",
            "src/assets/jump_2.png", "src/assets/jump_3.png"};
    public static final String[] RUN_IMAGE_PATHS = {"src/assets/run_0.png", "src/assets/run_1.png",
            "src/assets/run_2.png", "src/assets/run_3.png", "src/assets/run_4.png", "src/assets/run_5.png"};

    private UserInputListener inputListener;
    private float energyLevel;
    private Renderable idleRenderer;
    private Renderable jumpRenderer;
    private Renderable runRenderer;

    public float getEnergyLevel() {
        return energyLevel;
    }

    public void addEnergy(float energyToAdd) {
        energyLevel += energyToAdd;
    }

    public Avatar(Vector2 pos,
                  UserInputListener inputListener,
                  ImageReader imageReader) {
        super(pos, Vector2.ONES.mult(50), null);
        idleRenderer = new AnimationRenderable(IDLE_IMAGE_PATHS, imageReader, true, TIME_BETWEEN_ANIMATION_CLIPS);
        jumpRenderer = new AnimationRenderable(JUMP_IMAGE_PATHS, imageReader, true, TIME_BETWEEN_ANIMATION_CLIPS);
        runRenderer = new AnimationRenderable(RUN_IMAGE_PATHS, imageReader, true, TIME_BETWEEN_ANIMATION_CLIPS);
        this.renderer().setRenderable(idleRenderer);
        renderer().setIsFlippedHorizontally(true);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);

        this.inputListener = inputListener;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        boolean didSomething = false;
        float xVel = 0;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT) && energyLevel >= MOVE_ENERGY_LOSS) {
            xVel -= VELOCITY_X;
            energyLevel -= MOVE_ENERGY_LOSS;
            didSomething = true;
            this.renderer().setRenderable(runRenderer);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && energyLevel >= MOVE_ENERGY_LOSS) {
            xVel += VELOCITY_X;
            energyLevel -= MOVE_ENERGY_LOSS;
            didSomething = true;
            this.renderer().setRenderable(runRenderer);
        }
        transform().setVelocityX(xVel);
        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 && energyLevel >= JUMP_ENERGY_LOSS) {
            transform().setVelocityY(VELOCITY_Y);
            energyLevel -= JUMP_ENERGY_LOSS;
            didSomething = true;
            this.renderer().setRenderable(jumpRenderer);
        }
        if (!didSomething && getVelocity().y() == 0 ) {
            energyLevel += STANDING_ENERGY_GAIN;
            this.renderer().setRenderable(idleRenderer);
        }
    }
}