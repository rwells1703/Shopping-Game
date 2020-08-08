package check.out.game.maingame.fermions;

import check.out.game.maingame.ConstShop;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fernebon.b2d.util.fermions.BodiedFermionPartial;

public class Projectile extends BodiedFermionPartial {
    public int type;
    public boolean flying = false;
    public float timeOfFlight = 0;

    public Projectile(World world, Vector2 position){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(position);
        bodyDef.linearDamping = 2;

        body=world.createBody(bodyDef);


        PolygonShape shape=new PolygonShape();//Create fixture shape.
        shape.set(new float[]{-0.25f, -0.25f, 0.25f, -0.25f, 0.25f, 0.25f, -0.25f, 0.25f});

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density=1;

        body.createFixture(fixtureDef);

        shape.dispose();
    }

    @Override
    public int getSetMask() {
        return ConstShop.FB_PROJECTILE;
    }
}
