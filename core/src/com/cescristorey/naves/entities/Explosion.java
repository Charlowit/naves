package com.cescristorey.naves.entities;

import com.cescristorey.naves.SoundManager;
import com.cescristorey.naves.TextureManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Explosion extends Entity {
    // animación de la explosión

    private static final int EXPLOSION_COLS = 8;
    private static final int EXPLOSION_ROWS = 3;

    private Texture explosion = TextureManager.EXPLOSION;
    private TextureRegion[] explosionFrames;
    private Animation explosionAnimation;
    private TextureRegion currentFrame;


    private boolean finished;

    private float x, y, stateTime;

    public Explosion(Vector2 pos, Vector2 direction) {
        super(TextureManager.EXPLOSION, pos, direction);

        this.x = pos.x;
        this.y = pos.y;

        // Divido la imagen en frames
        TextureRegion[][] tmp = TextureRegion.split(explosion, explosion.getWidth()
                / EXPLOSION_COLS, explosion.getHeight() / EXPLOSION_ROWS);

        explosionFrames = new TextureRegion[EXPLOSION_COLS * EXPLOSION_ROWS];
        int index = 0;
        for (int i = 0; i < EXPLOSION_ROWS; i++) {
            for (int j = 0; j < EXPLOSION_COLS; j++) {
                explosionFrames[index++] = tmp[i][j];
            }
        }
        explosionAnimation = new Animation(0.025f, explosionFrames);
        stateTime = 0f;

        SoundManager.SFX_EXPLOSION.play();
    }

    @Override
    public void render(SpriteBatch sb) {
        stateTime += Gdx.graphics.getDeltaTime();

        currentFrame = (TextureRegion) explosionAnimation.getKeyFrame(stateTime, false);
        if (explosionAnimation.isAnimationFinished(stateTime)) {
            finished = true;
        }
        sb.draw(currentFrame, x, y);
    }

    @Override
    public void update() {

    }

    public boolean getFinished() {
        return finished;
    }
}
