package check.out.game.maingame.landingactions;

import check.out.game.maingame.effects.BeansAreGloopy;
import check.out.game.maingame.fermions.Projectile;
import check.out.game.maingame.fermions.flooring.BeanRing;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.math.Vector2;
import fernebon.core.base.Nebula;

/**
 * Makes a bean ring appear where the bean can lands.
 */
public class LandingBeans implements LandingAction {
    @Override
    public boolean invoke(Nebula nebula, Projectile projectile) {
        Vector2 position = new Vector2(projectile.getBody().getPosition());
        nebula.effects().add(new BeansAreGloopy(
                nebula.fermions().add(new BeanRing(position), it -> it.init((NebulaShop) nebula))
        ), null);//Hmm, can I replace this with something more reusable (like can make black hole,...)?
        return true;
    }
}
