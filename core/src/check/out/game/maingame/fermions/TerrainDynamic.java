package check.out.game.maingame.fermions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class TerrainStatic implements Terrain {
  Body body;

  public TerrainStatic(World world, Vector2 position, float size) {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.StaticBody;
    bodyDef.position.set(position);
    //bodyDef.linearDamping = 1;

    body = world.createBody(bodyDef);

    PolygonShape shape = new PolygonShape();

    shape.set(new float[]{
        -size/2,         -size/2,
        -size/2,         size/2,
        size/2,         -size/2,
        size/2,         size/2,
    });

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;

    body.createFixture(fixtureDef);
    body.setUserData(this);

    shape.dispose();
  }
}
