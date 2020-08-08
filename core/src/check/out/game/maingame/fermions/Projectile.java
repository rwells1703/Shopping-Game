package check.out.game.maingame.fermions;

import check.out.game.maingame.ConstShop;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fernebon.b2d.util.fermions.BodiedFermionPartial;

public class Projectile extends BodiedFermionPartial {
    public Projectile(World world, Vector2 position){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(position);
        bodyDef.linearDamping = 2;

        body=world.createBody(bodyDef);

//        CircleShape shape = new CircleShape();
//        shape.setRadius(0.2f);
        PolygonShape shape=new PolygonShape();//Create fixture shape.
        shape.set(new float[]{-0.4f, -0.475f, 0.4f, -0.475f, 0.25f, 0.710f, -0.25f, 0.710f});

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);

        shape.dispose();
    }

    @Override
    public int getSetMask() {
        return ConstShop.FB_PROJECTILE;
    }
}
