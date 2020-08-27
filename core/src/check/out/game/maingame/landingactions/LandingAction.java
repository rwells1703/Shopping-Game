package check.out.game.maingame.landingactions;

import fernebon.core.base.Nebula;
import fernebon.core.base.Pointer;
import fernebon.core.base.fermion.Fermion;

/**
 * Action to take place when given projectile lands.
 */
public interface LandingAction {
    /**
     * Called when the projectile is in landed state, but still exists.
     *
     * @return True if should remove this ProjectileCausesActionOnLanding Effect.
     */
    boolean invoke(Nebula nebula, Pointer<Fermion> projectilePointer);
}
