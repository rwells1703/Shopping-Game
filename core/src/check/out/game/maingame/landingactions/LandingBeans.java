package check.out.game.maingame.landingactions;

import check.out.game.maingame.effects.BeansAreGloopy;
import check.out.game.maingame.fermions.Projectile;
import check.out.game.maingame.fermions.flooring.BeanRing;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.math.Vector2;
import fernebon.core.base.Nebula;
import fernebon.core.base.Pointer;
import fernebon.core.base.fermion.Fermion;

/**
 * Makes a bean ring appear where the bean can lands.
 */
public class LandingBeans implements LandingAction {
    @Override
    public boolean invoke(Nebula nebula, Pointer<Fermion> projectilePointer) {
        Projectile projectile = projectilePointer.getPointeeCast();
        Vector2 position = new Vector2(projectile.getBody().getPosition());
        nebula.effects().add(() -> new BeansAreGloopy(
                nebula.fermions().addWithPointer(() -> new BeanRing((NebulaShop) nebula, position))
        ));//Hmm, can I replace this with something more reusable (like can make black hole,...)?
        return true;
    }
}
