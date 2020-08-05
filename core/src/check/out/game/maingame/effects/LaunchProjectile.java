package check.out.game.maingame.effects;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Projectile;
import check.out.game.maingame.fermions.Shopper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import fernebon.core.base.Nebula;
import fernebon.core.base.effect.Effect;
import fernebon.core.util.LifeCycleImplementation;
import org.w3c.dom.ls.LSOutput;

public class LaunchProjectile extends LifeCycleImplementation implements Effect {
    private float timeOfFlight = 0;
    private boolean flying = false;
    @Override
    public int getPriority(){
        return ConstShop.EP_LAUNCH_PROJECTILE;
    }

    @Override
    public void onUpdate(Nebula nebula, float deltaTime){
        Shopper player = (Shopper)nebula.fermions().particles(ConstShop.FB_SHOPPER).iterator().next();
        player.getBody().setAngularVelocity((float)Math.PI);

        Vector2 playerPos = player.getBody().getPosition();
        float playerAngle = player.getBody().getAngle();

        for(Projectile projectile:nebula.fermions().<Projectile>particles(ConstShop.FB_PROJECTILE)) {
            if (this.flying) {
                this.timeOfFlight += deltaTime;
            }
            if (this.timeOfFlight >= 0.5) {
                this.flying = false;
                this.timeOfFlight = 0;
                projectile.getBody().setLinearVelocity(new Vector2(0, 0));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                projectile.getBody().setTransform(playerPos.x+(float)(0.2*Math.cos(playerAngle)), playerPos.y+(float)(0.2*Math.sin(playerAngle)),0);
                projectile.getBody().setLinearVelocity(5*(float)Math.cos(playerAngle), 5*(float)Math.sin(playerAngle));
                this.flying = true;
            }
        }
    }

}
