package com.cescristorey.naves.screen;

import com.cescristorey.naves.Naves;
import com.cescristorey.naves.SoundManager;
import com.cescristorey.naves.TextureManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class MainMenuScreen implements Screen {

    private OrthographicCamera camara;

    private BitmapFont fontGameTitle;
    private BitmapFont fontBeginGame;

    private String tituloJuego = "Naves 80";
    private String empezarJugar;

    private int waitCounter;
    private Naves game;

    public MainMenuScreen(Naves _game) {
        TextureManager.resume();

        camara = new OrthographicCamera();
        camara.setToOrtho(false, Naves.WIDTH, Naves.HEIGHT);

        fontGameTitle = new BitmapFont();
        fontGameTitle.getData().scale( 3 );
        if (Naves.isMobile()) {
            empezarJugar = "¡Toca la pantalla para empezar!";
        } else {
            empezarJugar = "¡Pulsa ENTER para empezar!";
        }

        fontBeginGame = new BitmapFont();
        fontBeginGame.getData().setScale( 1 );

        // Música
        if (!SoundManager.MUS_BACKGROUND.isPlaying()) {
            SoundManager.resume();
            SoundManager.MUS_BACKGROUND.setLooping(true);
            SoundManager.MUS_BACKGROUND.play();
        }

        waitCounter = 0;
        game = _game;
    }

    public void update() {
        if (waitCounter > 50) {
            if (Naves.isMobile() && Gdx.input.isTouched()) {
                    game.setScreen(new GameScreen(game));
                
            } else if (Gdx.input.isKeyPressed(Keys.ENTER)) {
                    game.setScreen(new GameScreen(game));
            }
            
        }
        waitCounter++;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camara.combined);
        game.batch.begin();
        fontGameTitle.draw(game.batch, tituloJuego, Naves.WIDTH / 2 - getStringWidth(fontGameTitle, tituloJuego) / 2, Naves.HEIGHT / 4 * 3);
        fontBeginGame.draw(game.batch, empezarJugar, Naves.WIDTH / 2 - getStringWidth(fontBeginGame, empezarJugar) / 2, Naves.HEIGHT / 2);
        game.batch.end();
        update();
    }

    private int getStringWidth(BitmapFont bmpf, String str) {
        return (int) new GlyphLayout(bmpf, str).width;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
        fontGameTitle.dispose();
        fontBeginGame.dispose();
    }

    @Override
    public void pause() {
        TextureManager.pause();
        SoundManager.pause();
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
