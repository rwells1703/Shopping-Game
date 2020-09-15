package check.out.game.maingame.effects;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.flooring.IceRing;
import check.out.game.maingame.fermions.shoppers.Shopper;
import com.badlogic.gdx.math.Vector2;
import fernebon.core.base.Nebula;
import fernebon.core.base.effect.Effect;
import fernebon.core.util.LifeCycleImplementation;

/**
 * Effect to make a ring of ice slippery.
 */
public class IceIsSlippery extends LifeCycleImplementation implements Effect {
    private IceRing iceRing;//Points to the ice ring.

    public IceIsSlippery(IceRing iceRing) {
        this.iceRing = iceRing;
    }

    @Override
    public int getPriority() {
        return ConstShop.EP_SLIPPERY_ICE;
    }

    @Override
    public void onUpdate(Nebula nebula, float deltaTime) {
        Vector2 addedSlipperyVector = new Vector2(0, 0);
        if (iceRing.getCurrentLifeCycleState().isDeleted()) {//Remove this if the ice ring is gone.
            nebula.effects().delete(this);
            return;
        }
        //Currently, slipperiness is applying a force in the direction of motion to counteract friction. Hmm, is there something better?
        for (Shopper shopper : iceRing.standingOn) {
            addedSlipperyVector.set(shopper.getBody().getLinearVelocity());
            addedSlipperyVector.setLength2(ConstShop.ICE_THRUST_2);
//            addedSlipperyVector.set(ConstShop.ICE_THRUST_2,0);
//            addedSlipperyVector.rotateRad(shopper.getBody().getLinearVelocity().angleRad());
            shopper.controller.desiredForce.add(addedSlipperyVector);
        }
    }
}
