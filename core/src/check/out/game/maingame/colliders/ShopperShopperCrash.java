package check.out.game.maingame.colliders;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Shopper;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.physics.box2d.Contact;
import fernebon.b2d.base.collider.Collider;
import fernebon.core.base.Nebula;
import fernebon.core.util.LifeCycleImplementation;

/**
 * Makes shoppers collect collectibles.
 */
public class ShopperShopperCrash extends LifeCycleImplementation implements Collider {
    @Override
    public int getPriority() {
        return ConstShop.CP_SHOPPER_SHOPPER_CRASH;
    }

    @Override
    public void beginContact(Nebula nebula, Contact contact) {
        Object A=contact.getFixtureA().getUserData();
        Object B=contact.getFixtureB().getUserData();

        if(A instanceof Shopper && B instanceof Shopper)
            shopperShopperCrash(nebula, (Shopper) A, (Shopper) B);
    }

    // One shopper crashes into another shopper
    private void shopperShopperCrash(Nebula nebula, Shopper shopperA, Shopper shopperB){
        int minCrashSpeed = 2;
        if (Math.abs(shopperA.getBody().getLinearVelocity().x - shopperB.getBody().getLinearVelocity().x) >= minCrashSpeed || Math.abs(shopperA.getBody().getLinearVelocity().y - shopperB.getBody().getLinearVelocity().y) >= minCrashSpeed) {
            int soundNumber = (int) (Math.random() * ((5 - 1) + 1)) + 1;
            ((NebulaShop) nebula).soundEffectHandler.playSound("shopperShopperCrash" + soundNumber);
        }
    }
}
