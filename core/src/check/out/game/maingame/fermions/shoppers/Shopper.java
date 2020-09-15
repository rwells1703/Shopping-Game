package check.out.game.maingame.fermions.shoppers;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.nonfermions.Controller;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import fernebon.b2d.util.fermions.BodiedFermionPartial;

public class Shopper extends BodiedFermionPartial {//BodiedFermionPartial has a Body (called body) that is automatically disposed of, but needs to be set.
    public Controller controller = new Controller();//The AI specify what they WOULD LIKE to do by editing this. Whether the effects listen is not guaranteed (e.g. for a trolley with a dodgy wheel).
    public Person person;

    public void init(NebulaShop nebula, Vector2 position) {
        BodyDef bodyDef = new BodyDef();//Define body properties;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        bodyDef.linearDamping = ConstShop.SHOPPERLINEARDAMPING; //This is to slow the body, but probably isn't realistic friction (I think it is exponential decay of velocity).
        bodyDef.angularDamping = ConstShop.SHOPPERANGULARDAMPING;
        body = nebula.world().createBody(bodyDef);//Create body.
        body.setUserData(this);

        PolygonShape shape = new PolygonShape();//Create fixture shape.
        shape.set(new float[]{-0.4f, -0.475f, 0.4f, -0.475f, 0.25f, 0.710f, 0f, 0.775f, -0.25f, 0.710f});

        FixtureDef fixtureDef = new FixtureDef();//Define fixture.
        fixtureDef.shape = shape;
        fixtureDef.density = ConstShop.SHOPPEREMPTYDENISTY;

        body.createFixture(fixtureDef).setUserData(this);//Create the fixture.

        shape.dispose();

        person = new Person();
        nebula.fermions().add(person, it -> {
            it.init(nebula, position);
            //The following is also part of the Modifier<Person>'s invoke method, as it must only execute once the Box2D bodies of the person have been made, which must only be made when the person fermion is actually added to the fermion list.
            DistanceJointDef arm = new DistanceJointDef();
            arm.initialize(body, it.getBody(), new Vector2(body.getPosition()).add(new Vector2(0f, -0.475f)), new Vector2(it.getBody().getPosition()).add(new Vector2(0f, it.radius)));
            arm.length = 0.07f;
            arm.collideConnected = true;
            nebula.world().createJoint(arm);
        });
    }

    @Override
    public int getSetMask() {
        return ConstShop.FB_SHOPPER;
    }
}
