package check.out.game.maingame.effects;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.flooring.BeanRing;
import check.out.game.maingame.fermions.shoppers.Shopper;
import fernebon.core.base.Nebula;
import fernebon.core.base.effect.Effect;
import fernebon.core.util.LifeCycleImplementation;

/**
 * Effect to make a ring of beans slow shoppers down (or rather damp their desired force and torque).
 */
public class BeansAreGloopy extends LifeCycleImplementation implements Effect {
    private BeanRing beanRing;//Points to the bean ring.

    public BeansAreGloopy(BeanRing beanRing) {
        this.beanRing = beanRing;
    }

    @Override
    public int getPriority() {
        return ConstShop.EP_GLOOPY_BEANS;
    }

    @Override
    public void onUpdate(Nebula nebula, float deltaTime) {
        if (beanRing.getCurrentLifeCycleState().isDeleted()) {//Remove this if the bean ring is gone.
            nebula.effects().delete(this);
            return;
        }
        for (Shopper shopper : beanRing.standingOn) {//Scales down the desired force and torque of each shopper in the bean ring.
            shopper.controller.desiredForce.scl(ConstShop.BEAN_FORCE_DAMPING);
            shopper.controller.desiredTorque *= ConstShop.BEAN_TORQUE_DAMPING;
        }
    }
}
