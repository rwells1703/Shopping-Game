package check.out.game.maingame.fermions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Enemy extends Shopper {//BodiedFermionPartial has a Body (called body) that is automatically disposed of, but needs to be set.
    public int targetWaypointID = -1;

    public Enemy(World world, Vector2 position) {
        super(world, position);
    }
}
