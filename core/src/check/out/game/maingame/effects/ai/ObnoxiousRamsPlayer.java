package check.out.game.maingame.effects.ai;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Player;
import check.out.game.maingame.fermions.Shopper;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.math.Vector2;
import fernebon.core.base.Nebula;
import fernebon.core.base.Pointer;
import fernebon.core.base.effect.Effect;
import fernebon.core.base.fermion.Fermion;
import fernebon.core.util.LifeCycleImplementation;

/**
 * AI to try to make a given shopper try and ram into the player.
 */
public class ObnoxiousRamsPlayer extends LifeCycleImplementation implements Effect {
    private Pointer<Fermion> pointer;

    public ObnoxiousRamsPlayer(Pointer<Fermion> pointer) {
        this.pointer = pointer;
    }

    @Override
    public int getPriority() {
        return ConstShop.EP_AI_KEYBOARD;
    }

    @Override
    public void onUpdate(Nebula nebula, float deltaTime) {
        //Set the player's desired force based on the ws keys - this method doesn't actually apply said force.
        Shopper shopper = pointer.getPointeeCast();
        if (shopper == null) {//Just in case - if the shopper is no more, dispose of this effect.
            nebula.effects().remove(this);
            return;
        }

        float shopperAngle = shopper.getBody().getAngle();
        Vector2 vector = shopper.controller.desiredForce;
        vector.set(((NebulaShop) nebula).player.<Player>getPointeeCast().getBody().getPosition());
        vector.sub(shopper.getBody().getPosition());//Vector should now be displacement from shopper to player.
        vector.rotateRad(-shopperAngle);//Vector should now be displacement of player from shopper, in frame in which shopper is facing forward.

        shopper.controller.desiredTorque = ConstShop.SHOPPERTORQUEFACTOR * (vector.x < 0 ? 1 : -1);//Apply torque to try and point at player.

        vector.setLength2(ConstShop.OBNOXIOUS_THRUST_FACTOR_2);//Now vector is to become desired force - set the length (if displacement is (0,0), there should be a "glitch" in which no force is applied, but then shopper and player coincide...)
        vector.rotateRad(shopperAngle);//Set force to be in direction of trolley.
    }
}
