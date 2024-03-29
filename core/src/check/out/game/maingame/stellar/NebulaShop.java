package check.out.game.maingame.stellar;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.shoppers.Player;
import check.out.game.maingame.nonfermions.RayCaster;
import check.out.game.maingame.nonfermions.SoundEffectHandler;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import fernebon.b2d.impl.NebulaB2DEasyOverride;

public class NebulaShop extends NebulaB2DEasyOverride {
    public Camera camera;
    public SoundEffectHandler soundEffectHandler;
    public Player player;
    public Body bodyForSensors;
    public Vector2[] waypoints;
    public RayCaster rayCaster;

    @Override
    public void create() {
        super.create();

        //Create the body to be used by sensors.
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);
        bodyForSensors = world.createBody(bodyDef);

        soundEffectHandler = new SoundEffectHandler();

        rayCaster = new RayCaster(this);
    }

    @Override
    protected Vector2 getGravity() {
        return new Vector2(0, 0);
    }

    @Override
    protected int[] getOptimisedFermionSetMasks() {//These are the sets that the FermionList is (theoretically) optimised to iterate through.
        return new int[]{
                ConstShop.FB_SHOPPER,
                ConstShop.FB_COLLECTIBLE,
                ConstShop.FB_FLOORING,
        };
    }

    @Override
    public void dispose() {
        soundEffectHandler.dispose();
        super.dispose();
    }
    //Todo shared SpriteBatch.
}
