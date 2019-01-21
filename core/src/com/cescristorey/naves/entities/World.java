package com.cescristorey.naves.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.cescristorey.naves.Naves;
import com.cescristorey.naves.TextureManager;
import com.cescristorey.naves.screen.GameOverScreen;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class World {

    private final Array<Entity> entities = new Array<Entity>();
    private final Player player;

    private boolean wonGame;
    public static boolean gameOver;
    private long gameOverWaitCounter;

    private BitmapFont gameOverMsg;
    private final String gameOverMsgStr = "Fin";
    private Naves game;
    private int countEnemies;
    private long timeLastEnemy;

    public World(Naves _game) {
        gameOver = false;
        addEntity(player = new Player(new Vector2(220, 15), new Vector2(0, 0), this));
        gameOverMsg = new BitmapFont();
        gameOverMsg.getData().setScale( 4 );
        game = _game;
        countEnemies = 0;
        gameOverWaitCounter = 0;
        createEnemy();
    }

    public void update() {
        if (!gameOver) {
            player.update();
        }

        // Render entities
        for (Entity e : entities) {
            e.update();
        }
        for (Missile m : getMissiles()) {
            if (m.checkEnd()) {
                entities.removeValue(m, false);
            }
        }
        for (Explosion e : getExplosions()) {
            if (e.getFinished()) {
                entities.removeValue(e, false);
            }
        }
        checkCollisions();


        if (gameOver) {
            gameOverWaitCounter++;
            System.out.println(gameOverWaitCounter);
            tryEndGame();
        }
    }
    
    public void createEnemy()
    {
        if (countEnemies < Naves.ENEMIES_NUMBER) {
            float x = MathUtils.random(0, Naves.WIDTH - TextureManager.ENEMY.getWidth());
            float y = MathUtils.random(Naves.HEIGHT, Naves.HEIGHT * 2);
            float speed = MathUtils.random(2, 5);
            addEntity(new Enemy(new Vector2(x, y), new Vector2(0, -speed)));
            countEnemies++;
            timeLastEnemy = TimeUtils.nanoTime();
        }
    }

    public void render(SpriteBatch sb) {
        
        if (TimeUtils.nanoTime() - timeLastEnemy > 1000000000)
            createEnemy();
        
        for (Entity e : entities) {
            e.render(sb);
        }
        if (gameOver) {
            int i = (int) new GlyphLayout(gameOverMsg, gameOverMsgStr).width;
            gameOverMsg.draw(sb, gameOverMsgStr, Naves.WIDTH / 2 - i / 2, Naves.HEIGHT / 4 * 3);
        }
        update();
    }

    private void checkCollisions() {
        for (Enemy e : getEnemies()) {
            for (Missile m : getMissiles()) {
                if (e.getBounds().overlaps(m.getBounds())) {
                    addEntity(new Explosion(new Vector2(e.pos.x, e.pos.y), new Vector2(0, 0)));
                    entities.removeValue(e, false);
                    entities.removeValue(m, false);
                    if (gameOver()) {
                        endGame(true);
                    }
                }
            }
            if (e.getBounds().overlaps(player.getBounds()) && !gameOver) {
                addEntity(new Explosion(new Vector2(player.pos.x, player.pos.y), new Vector2(0, 0)));
                entities.removeValue(player, true);

                endGame(false);
            }
        }
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    private Array<Enemy> getEnemies() {
        Array<Enemy> ret = new Array<Enemy>();
        for (Entity e : entities) {
            if (e instanceof Enemy) {
                ret.add((Enemy) e);
            }
        }
        return ret;
    }

    private Array<Missile> getMissiles() {
        Array<Missile> ret = new Array<Missile>();
        for (Entity e : entities) {
            if (e instanceof Missile) {
                ret.add((Missile) e);
            }
        }
        return ret;
    }

    private Array<Explosion> getExplosions() {
        Array<Explosion> ret = new Array<Explosion>();
        for (Entity e : entities) {
            if (e instanceof Explosion) {
                ret.add((Explosion) e);
            }
        }
        return ret;
    }

    public boolean gameOver() {
        return getEnemies().size <= 0;
    }

    private void endGame(boolean win) {
        wonGame = win;
        gameOver = true;
        gameOverWaitCounter = 0;
    }

    private void tryEndGame() {
        if (gameOverWaitCounter > 200) {
            game.setScreen( new GameOverScreen(game, wonGame));
        }
    }
}
