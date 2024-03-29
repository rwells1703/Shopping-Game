package check.out.game.maingame.fermions;

import check.out.game.maingame.ConstShop;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import fernebon.b2d.util.fermions.BodiedFermionPartial;

public class Projectile extends BodiedFermionPartial {
    public static int SELECTED_TYPE = 0;
    public int type;
    public boolean transformed; //determines which texture to use

    public Projectile(int type) {
        this.type = type;
    }

    public void init(World world, Vector2 position, Vector2 velocity) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        bodyDef.linearVelocity.set(velocity);
        bodyDef.linearDamping = 4;
        bodyDef.angularDamping = 4;

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();//Create fixture shape.
        shape.set(new float[]{-0.25f, -0.25f, 0.25f, -0.25f, 0.25f, 0.25f, -0.25f, 0.25f});

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;

        body.createFixture(fixtureDef);

        shape.dispose();
    }

    @Override
    public int getSetMask() {
        return ConstShop.FB_PROJECTILE;
    }
}
