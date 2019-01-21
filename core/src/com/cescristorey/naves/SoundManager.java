package com.cescristorey.naves;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    // Música
    public static Music MUS_BACKGROUND = Gdx.audio.newMusic(Gdx.files.internal("sound/background_music.mp3"));

    // Efectos de sonido
    public static Sound SFX_EXPLOSION = Gdx.audio.newSound(Gdx.files.internal("sound/sfxExplosion.wav"));

    public static void pause() {
        MUS_BACKGROUND.dispose();
        SFX_EXPLOSION.dispose();
    }

    public static void resume() {
        MUS_BACKGROUND = Gdx.audio.newMusic(Gdx.files.internal("sound/background_music.mp3"));
        SFX_EXPLOSION = Gdx.audio.newSound(Gdx.files.internal("sound/sfxExplosion.wav"));
    }
}
