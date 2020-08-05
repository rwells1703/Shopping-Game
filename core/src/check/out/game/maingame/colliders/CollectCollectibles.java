package check.out.game.maingame.colliders;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Collectible;
import check.out.game.maingame.fermions.Shopper;
import com.badlogic.gdx.physics.box2d.Contact;
import fernebon.b2d.base.collider.Collider;
import fernebon.core.base.Nebula;
import fernebon.core.util.LifeCycleImplementation;

/**
 * Makes shoppers collect collectibles.
 */
public class CollectCollectibles extends LifeCycleImplementation implements Collider {
    @Override
    public int getPriority() {
        return ConstShop.CP_COLLECT_ITEMS;
    }

    @Override
    public void beginContact(Nebula nebula, Contact contact) {
        Object A=contact.getFixtureA().getUserData();
        Object B=contact.getFixtureB().getUserData();

        if(A instanceof Shopper && B instanceof Collectible)
            collect(nebula,(Shopper) A,(Collectible) B);
        else if(B instanceof Shopper && A instanceof Collectible)
            collect(nebula,(Shopper) B,(Collectible) A);
    }

    /**
     * shopper collects collectible.
     */
    private void collect(Nebula nebula, Shopper shopper, Collectible collectible){
        //Todo make it so that the shopper gets the item. Maybe also check if the player should.
        nebula.fermions().remove(collectible);
    }
}
