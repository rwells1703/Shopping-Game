package check.out.game.maingame.stellar;

import check.out.game.maingame.ConstShop;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import fernebon.b2d.impl.NebulaB2DEasyOverride;
import fernebon.core.base.Pointer;
import fernebon.core.base.fermion.Fermion;

public class NebulaShop extends NebulaB2DEasyOverride {
    @Override
    public void create() {
        super.create();

        //Create the body to be used by sensors.
        BodyDef bodyDef=new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,0);
        bodyForSensors=world.createBody(bodyDef);
    }

    @Override
    protected Vector2 getGravity() {
        return new Vector2(0,0);
    }

    @Override
    protected int[] getOptimisedFermionSetMasks() {//These are the sets that the FermionList is (theoretically) optimised to iterate through.
        return new int[]{
                ConstShop.FB_SHOPPER,
                ConstShop.FB_COLLECTIBLE,
        };
    }

    public Pointer<Fermion> player;
    public Body bodyForSensors;
}
