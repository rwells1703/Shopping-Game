package check.out.game.maingame.effects;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Player;
import check.out.game.maingame.fermions.Projectile;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntIntMap;
import fernebon.core.base.Nebula;
import fernebon.core.base.effect.Effect;
import fernebon.core.base.fermion.FermionList;
import fernebon.core.util.LifeCycleImplementation;

public class LaunchProjectile extends LifeCycleImplementation implements Effect {
    private int type = 0;
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

        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
            this.type = 0;
        }else if(Gdx.input.isKeyPressed(Input.Keys.NUM_2)){
            this.type = 1;
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {

            Vector2 projectilePos = new Vector2(playerPos.x-(float)(1*Math.sin(playerAngle)), playerPos.y+(float)(1*Math.cos(playerAngle)));
            Vector2 projectileVel = new Vector2(5*(float)Math.cos(playerAngle+Math.PI/2), 5*(float)Math.sin(playerAngle+Math.PI/2));
            Projectile projectile = new Projectile(((NebulaShop)nebula).world(), projectilePos, projectileVel, this.type);
            list.add(() -> projectile);


            player.cargo.removeOneOf(projectile.type);
        }

    }
}
