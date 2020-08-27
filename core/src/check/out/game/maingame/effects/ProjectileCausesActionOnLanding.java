package check.out.game.maingame.effects;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Projectile;
import check.out.game.maingame.landingactions.LandingAction;
import fernebon.core.base.Nebula;
import fernebon.core.base.Pointer;
import fernebon.core.base.effect.Effect;
import fernebon.core.base.fermion.Fermion;
import fernebon.core.util.LifeCycleImplementation;

public class ProjectileCausesActionOnLanding extends LifeCycleImplementation implements Effect {
    private Pointer<Fermion> projectilePointer;
    private LandingAction action;
    private boolean projectileNotAppearedYet = true;//Projectile may not appear straight away, so this boolean is here so that the effect isn't removed before the projectile appears.

    /**
     * @param projectilePointer Must have that this Effect is free to remove projectilePointer from the nebula when it sees fit.
     */
    public ProjectileCausesActionOnLanding(Pointer<Fermion> projectilePointer, LandingAction action) {
        this.projectilePointer = projectilePointer;
        this.action = action;
    }

    @Override
    public int getPriority() {
        return ConstShop.EP_ACTION_ON_LANDING;
    }

    @Override
    public void onUpdate(Nebula nebula, float deltaTime) {
        Projectile projectile = projectilePointer.getPointeeCast();
        if (projectileNotAppearedYet) {//If the Projectile fermion hasn't yet existed.
            projectileNotAppearedYet = projectile == null;//Say that it has existed iff the projectile has now been set to non-null.
        } else if (projectile == null) {//Otherwise the projectile has existed. If it no longer exists, remove this effect - someone else got to the projectile first.
            nebula.effects().remove(this);
        } else if (projectile.transformed) {//Otherwise the projectile currently exists. If it has now landed, perform the action for when it lands, and then remove this effect if the action says so.
            if (action.invoke(nebula, projectilePointer))
                nebula.effects().remove(this);
        }
    }

    @Override
    public void dispose(Nebula nebula) {//Dispose removes pointer, as it should be personal to this effect.
        Effect.super.dispose(nebula);
        nebula.fermions().removePointer(projectilePointer);
    }
}
