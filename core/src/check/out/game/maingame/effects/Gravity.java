package check.out.game.maingame.effects;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Projectile;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import fernebon.core.base.Nebula;
import fernebon.core.base.effect.Effect;
import fernebon.core.util.LifeCycleImplementation;

public class Gravity extends LifeCycleImplementation implements Effect {
    @Override
    public int getPriority() {
        return ConstShop.EP_GRAVITY;
    }

    @Override
    public void onUpdate(Nebula nebula, float deltaTime) {
        for(Projectile projectile: nebula.fermions().<Projectile>particles(ConstShop.FB_PROJECTILE)){
            if(projectile.getBody().getLinearVelocity().len()<0.9){
                projectile.transformed = true;
            }
        }
    }

    @Override
    public void dispose(Nebula nebula) {

    }
}
