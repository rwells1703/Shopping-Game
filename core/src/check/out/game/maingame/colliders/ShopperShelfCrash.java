package check.out.game.maingame.colliders;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.shoppers.Shopper;
import check.out.game.maingame.fermions.TerrainStatic;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.physics.box2d.Contact;
import fernebon.b2d.base.collider.Collider;
import fernebon.core.base.Nebula;
import fernebon.core.util.LifeCycleImplementation;

/**
 * Makes shoppers collect collectibles.
 */
public class ShopperShelfCrash extends LifeCycleImplementation implements Collider {
    @Override
    public int getPriority() {
        return ConstShop.CP_SHOPPER_SHELF_CRASH;
    }

    @Override
    public void beginContact(Nebula nebula, Contact contact) {
        Object A = contact.getFixtureA().getUserData();
        Object B = contact.getFixtureB().getUserData();

        if (A instanceof Shopper && B instanceof TerrainStatic) {
            shopperShelfCrash(nebula, (Shopper) A);
        } else if (A instanceof TerrainStatic && B instanceof Shopper) {
            shopperShelfCrash(nebula, (Shopper) B);
        }
    }

    private void shopperShelfCrash(Nebula nebula, Shopper shopper) {
        int minCrashSpeed = 2;
        if (Math.abs(shopper.getBody().getLinearVelocity().x) > minCrashSpeed || Math.abs(shopper.getBody().getLinearVelocity().y) > minCrashSpeed) {
            int soundNumber = (int) (Math.random() * ((2 - 1) + 1)) + 1;
            ((NebulaShop) nebula).soundEffectHandler.playSound("shopperShelfCrash" + soundNumber);
        }
    }
}
