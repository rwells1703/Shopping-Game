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
    public int getPriority() {
        return ConstShop.EP_LAUNCH_PROJECTILE;
    }

    @Override
    public void onUpdate(Nebula nebula, float deltaTime) {
        FermionList list = nebula.fermions();

        Player player = ((NebulaShop) nebula).player.getPointeeCast();

        Vector2 playerPos = player.getBody().getPosition();
        float playerAngle = player.getBody().getAngle();

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            Projectile.SELECTED_TYPE = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            Projectile.SELECTED_TYPE = 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            Projectile.SELECTED_TYPE = 2;
        }


        //TODO scrolling not working properly - might be my scroll wheel is dodgy?
//        Gdx.input.setInputProcessor(new InputCore());
//        if(Gdx.input.getInputProcessor().scrolled(1)){
////            System.out.println("down");
//        }else if(Gdx.input.getInputProcessor().scrolled(-1)){
//            System.out.println("up");
//        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            if (player.cargo.quantity.get(Projectile.SELECTED_TYPE, 0) > 0) {
                Vector2 projectilePos = new Vector2(playerPos.x - (float) (1 * Math.sin(playerAngle)), playerPos.y + (float) (1 * Math.cos(playerAngle)));
                Vector2 projectileVel = new Vector2(5 * (float) Math.cos(playerAngle + Math.PI / 2), 5 * (float) Math.sin(playerAngle + Math.PI / 2));
                Projectile projectile = new Projectile(((NebulaShop) nebula).world(), projectilePos, projectileVel, Projectile.SELECTED_TYPE);
                list.add(() -> projectile);


                player.cargo.removeOneOf(projectile.type);
            }

        }

    }
}
