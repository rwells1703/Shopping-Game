package check.out.game.maingame.landingactions;

import check.out.game.maingame.effects.IceIsSlippery;
import check.out.game.maingame.fermions.Projectile;
import check.out.game.maingame.fermions.flooring.IceRing;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.math.Vector2;
import fernebon.core.base.Nebula;

/**
 * Makes an ice ring appear where the ice cream lands.
 */
public class LandingIceCream implements LandingAction {
    @Override
    public boolean invoke(Nebula nebula, Projectile projectile) {
        Vector2 position = new Vector2(projectile.getBody().getPosition());
        nebula.effects().add(new IceIsSlippery(
                nebula.fermions().add(new IceRing(position), it -> it.init((NebulaShop) nebula))
        ), null);//Hmm, can I replace this with something more reusable (like can make black hole,...)?
//        nebula.fermions().delete(projectile);//Uncomment this to make the splattered ice cream disappear.
        return true;
    }
}
