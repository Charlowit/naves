package com.cescristorey.naves.entities;

import com.cescristorey.naves.Naves;
import com.cescristorey.naves.TextureManager;

import com.badlogic.gdx.math.Vector2;

public class Missile extends Entity {

    public Missile(Vector2 pos) {
        super(TextureManager.MISSILE, pos, new Vector2(0, 5));
    }

    @Override
    public void update() {
        pos.add(direction);
    }

    public boolean checkEnd() {
        return pos.y >= Naves.HEIGHT;
    }

}
