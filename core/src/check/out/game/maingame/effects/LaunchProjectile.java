package check.out.game.maingame.effects;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Player;
import check.out.game.maingame.fermions.Projectile;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import fernebon.core.base.Nebula;
import fernebon.core.base.effect.Effect;
import fernebon.core.base.fermion.FermionList;
import fernebon.core.util.LifeCycleImplementation;

public class LaunchProjectile extends LifeCycleImplementation implements Effect {
    @Override
    public int getPriority(){
        return ConstShop.EP_LAUNCH_PROJECTILE;
    }

    @Override
    public void onUpdate(Nebula nebula, float deltaTime){
        FermionList list = nebula.fermions();

        Player player = ((NebulaShop)nebula).player.getPointeeCast();

        Vector2 playerPos = player.getBody().getPosition();
        float playerAngle = player.getBody().getAngle();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            list.add(() -> new Projectile(((NebulaShop)nebula).world(), new Vector2(playerPos.x-(float)(1*Math.sin(playerAngle)), playerPos.y+(float)(1*Math.cos(playerAngle)))));
            Projectile projectile = list.<Projectile>particles(ConstShop.FB_PROJECTILE).iterator().next();
            projectile.getBody().setLinearVelocity(5*(float)Math.cos(playerAngle+Math.PI/2), 5*(float)Math.sin(playerAngle+Math.PI/2));
            projectile.flying = true;
            player.cargo.removeOneOf(projectile.type); //projectile type, not sure
        }

        for(Projectile projectile:nebula.fermions().<Projectile>particles(ConstShop.FB_PROJECTILE)) {
            if (projectile.flying) {
                projectile.timeOfFlight += deltaTime;
            }
            if (projectile.timeOfFlight >= 0.5) {
                projectile.flying = false;
                projectile.timeOfFlight = 0;
                projectile.getBody().setLinearVelocity(new Vector2(0, 0));
            }
        }
    }
}
