package check.out.game.maingame.effects;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Shopper;
import com.badlogic.gdx.Gdx;
import fernebon.core.base.Nebula;
import fernebon.core.base.effect.Effect;
import fernebon.core.util.LifeCycleImplementation;

public class ControllerForcesOnShoppers extends LifeCycleImplementation implements Effect {
    @Override
    public int getPriority() {
        return ConstShop.EP_MOVE_SHOPPERS;
    }

    @Override
    public void onUpdate(Nebula nebula, float deltaTime) {
        //For each shopper, apply the force requested by the shopper's controller.
        //This must be done on each tick, as forces are reset to none after each physics step.
        for(Shopper shopper:nebula.fermions().<Shopper>particles(ConstShop.FB_SHOPPER)){
            shopper.getBody().applyForceToCenter(shopper.controller.desiredForce,true);
        }
    }
}
