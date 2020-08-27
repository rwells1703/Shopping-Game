package check.out.game.maingame.nonfermions;

import check.out.game.maingame.fermions.TerrainStatic;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public class ToggledRayCastCallback implements RayCastCallback {
    public boolean collided;

    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
        if (fixture.getUserData() instanceof TerrainStatic) {
            collided = true;
        }

        return -1;
    }
}
