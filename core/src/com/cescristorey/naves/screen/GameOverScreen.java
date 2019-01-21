package com.cescristorey.naves.screen;

import com.cescristorey.naves.Naves;
import com.cescristorey.naves.TextureManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.TimeUtils;

public class GameOverScreen implements Screen {

    private OrthographicCamera camera;
    private Texture texture;
    private BitmapFont font;

    private String prompt;

    private long waitCounter;
    private Naves game;


    public GameOverScreen(Naves _game, boolean won) {
        if (won) {
            texture = TextureManager.GAME_WON;
        } else {
            texture = TextureManager.GAME_OVER;
        }
        font = new BitmapFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Naves.WIDTH, Naves.HEIGHT);
        if (Naves.isMobile()) {
            prompt = "Toca la pantalla para volver a jugar";
        } else {
            prompt = "Pulsa ENTER para volver a jugar";
        }
        game = _game;
        waitCounter = TimeUtils.nanoTime();
    }

    public void update() {
        camera.update();
        if (TimeUtils.nanoTime() - waitCounter > 2 * 1000000000) {
            System.out.println("Entra");
            if ((Naves.isMobile() && Gdx.input.isTouched()) ||  (!Naves.isMobile() && Gdx.input.isKeyPressed(Keys.ENTER))) {
                game.setScreen(new MainMenuScreen(game));
            } 
        }
        waitCounter++;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(texture, Naves.WIDTH / 2 - texture.getWidth() / 2, Naves.HEIGHT / 2
                - texture.getHeight() / 2);
        font.draw(game.batch, prompt, Naves.WIDTH / 2 - (int) new GlyphLayout(font, prompt).width / 2,
                Naves.HEIGHT / 4);
        game.batch.end();
        update();
    }

    @Override
    public void resize(int width, int height) {
        
    }

    @Override
    public void dispose() {
        texture.dispose();
        font.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        game.setScreen(new MainMenuScreen(game));

    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }
}
