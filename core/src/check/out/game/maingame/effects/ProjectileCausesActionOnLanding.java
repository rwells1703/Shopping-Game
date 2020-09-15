package check.out.game.maingame.effects;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Projectile;
import check.out.game.maingame.landingactions.LandingAction;
import fernebon.core.base.Nebula;
import fernebon.core.base.effect.Effect;
import fernebon.core.util.LifeCycleImplementation;

public class ProjectileCausesActionOnLanding extends LifeCycleImplementation implements Effect {
    private Projectile projectile;
    private LandingAction action;

    public ProjectileCausesActionOnLanding(Projectile projectile, LandingAction action) {
        this.projectile = projectile;
        this.action = action;
    }

    @Override
    public int getPriority() {
        return ConstShop.EP_ACTION_ON_LANDING;
    }

    @Override
    public void onUpdate(Nebula nebula, float deltaTime) {
        if (projectile.getCurrentLifeCycleState().isDeleted()) {//If the projectile has existed, nut no longer exists, delete this effect - someone else got to the projectile first.
            nebula.effects().delete(this);
        } else if (projectile.transformed) {//Otherwise the projectile currently exists. If it has now landed, perform the action for when it lands, and then remove this effect if the action says so.
            if (action.invoke(nebula, projectile))
                nebula.effects().delete(this);
        }
    }
}
