package check.out.game.maingame.landingactions;

import check.out.game.maingame.fermions.Projectile;
import fernebon.core.base.Nebula;

/**
 * Action to take place when given projectile lands.
 */
public interface LandingAction {
    /**
     * Called when the projectile is in landed state, but still exists.
     *
     * @return True if should remove this ProjectileCausesActionOnLanding Effect.
     */
    boolean invoke(Nebula nebula, Projectile projectilePointer);
}
