package check.out.game.maingame.fermions.shoppers;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import fernebon.b2d.util.fermions.BodiedFermionPartial;

public class Person extends BodiedFermionPartial {
    public float radius = 0.25f;

    public void init(NebulaShop nebula, Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(position).sub(0, 1));
        bodyDef.linearDamping = ConstShop.SHOPPERLINEARDAMPING;
        bodyDef.angularDamping = ConstShop.SHOPPERANGULARDAMPING;
        body = nebula.world().createBody(bodyDef);
        body.setUserData(this);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = ConstShop.SHOPPEREMPTYDENISTY;

        body.createFixture(fixtureDef).setUserData(this);

        shape.dispose();
    }

    @Override
    public int getSetMask() {
        return ConstShop.FB_PERSON;
    }
}
