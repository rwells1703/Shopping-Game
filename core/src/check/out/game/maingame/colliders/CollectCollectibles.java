package check.out.game.maingame.colliders;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Collectible;
import check.out.game.maingame.fermions.Player;
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

        if(A instanceof Player && B instanceof Collectible)
            collect(nebula,(Player) A,(Collectible) B);
        else if(B instanceof Player && A instanceof Collectible)
            collect(nebula,(Player) B,(Collectible) A);
    }

    /**
     * shopper collects collectible.
     */
    private void collect(Nebula nebula, Player player, Collectible collectible){
        player.cargo.addOneOf(collectible.type);
        nebula.fermions().remove(collectible);
    }
}
