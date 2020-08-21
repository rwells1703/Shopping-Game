package check.out.game.maingame.landingactions;

import check.out.game.maingame.effects.IceIsSlippery;
import check.out.game.maingame.fermions.Projectile;
import check.out.game.maingame.fermions.flooring.IceRing;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.math.Vector2;
import fernebon.core.base.Nebula;
import fernebon.core.base.Pointer;
import fernebon.core.base.fermion.Fermion;

/**
 * Makes an ice ring appear where the ice cream lands.
 */
public class LandingIceCream implements LandingAction {
    @Override
    public boolean invoke(Nebula nebula, Pointer<Fermion> projectilePointer) {
        Projectile projectile = projectilePointer.getPointeeCast();
        Vector2 position = new Vector2(projectile.getBody().getPosition());
        nebula.effects().add(() -> new IceIsSlippery(
                nebula.fermions().addWithPointer(() -> new IceRing((NebulaShop) nebula, position))
        ));//Hmm, can I replace this with something more reusable (like can make black hole,...)?
        nebula.fermions().remove(projectile);//Comment this out to make the splattered ice cream not disappear.
        return true;
    }
}
