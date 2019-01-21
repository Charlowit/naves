package com.cescristorey.naves.screen;

import com.badlogic.gdx.Gdx;
import com.cescristorey.naves.Naves;
import com.cescristorey.naves.TextureManager;
import com.cescristorey.naves.entities.World;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameScreen implements Screen {
    
    private OrthographicCamera camera;
    private World world;
    private Naves game;

    public GameScreen(Naves _game) {        
        camera =  new OrthographicCamera();
	camera.setToOrtho(false, Naves.WIDTH, Naves.HEIGHT);
        game = _game;
        world = new World(game);
    }

    public void update() {
        world.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        world.render(game.batch);
        game.batch.end();
        update();
    }

    @Override
    public void resize(int width, int height) {
        
    }

    @Override
    public void dispose() {

    }

    @Override
    public void pause() {
        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }
}
