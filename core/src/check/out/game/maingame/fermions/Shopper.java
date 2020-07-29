package check.out.game.maingame.fermions;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.nonfermions.Controller;
import check.out.game.maingame.nonfermions.ControllerSimple;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import fernebon.b2d.util.fermions.BodiedFermionPartial;

public class Shopper extends BodiedFermionPartial {//BodiedFermionPartial has a Body (called body) that is automatically disposed of, but needs to be set.
    public Shopper(World world, Vector2 position){
        BodyDef bodyDef=new BodyDef();//Define body properties;
        bodyDef.type= BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        bodyDef.linearDamping=2;    //This is to slow the body, but probably isn't realistic friction (I think it is exponential decay of velocity).

        body=world.createBody(bodyDef);//Create body.

        CircleShape shape=new CircleShape();//Create fixture shape.
        shape.setRadius(0.25f);

        FixtureDef fixtureDef=new FixtureDef();//Define fixture.
        fixtureDef.shape=shape;

        body.createFixture(fixtureDef);//Create the fixture.

        shape.dispose();//This MUST be called when the shape is no longer needed.
    }

    @Override
    public int getSetMask() {
        return ConstShop.FB_SHOPPER;
    }

    public Controller controller=new ControllerSimple();//The AI specify what they WOULD LIKE to do by editing this. Whether the effects listen is not guaranteed (e.g. for a trolley with a dodgy wheel).
}
