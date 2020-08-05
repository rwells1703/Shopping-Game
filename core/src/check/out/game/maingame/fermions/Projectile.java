package check.out.game.maingame.fermions;

import check.out.game.maingame.ConstShop;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import fernebon.b2d.util.fermions.BodiedFermionPartial;

public class Projectile extends BodiedFermionPartial {
    public Projectile(World world, Vector2 position){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(position);
        bodyDef.linearDamping = 2;

        body=world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(0.2f);

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
