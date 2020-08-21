package check.out.game.maingame.effects;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Player;
import check.out.game.maingame.fermions.Projectile;
import check.out.game.maingame.nonfermions.InputCore;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import fernebon.core.base.Nebula;
import fernebon.core.base.effect.Effect;
import fernebon.core.base.fermion.FermionList;
import fernebon.core.util.LifeCycleImplementation;

public class LaunchProjectile extends LifeCycleImplementation implements Effect {
    private final float INITIALPROJECTILEVELOCITY = 20;
    private final float PROJECTILEOFFSET = 0.7F;

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
        /*Gdx.input.setInputProcessor(new InputCore());
        if(Gdx.input.getInputProcessor().scrolled(1)){
            System.out.println("down");
        }else if(Gdx.input.getInputProcessor().scrolled(-1)){
            System.out.println("up");
        }*/

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            try {
                player.removeOneOf(Projectile.SELECTED_TYPE);

                Vector3 mousePos = ((NebulaShop)nebula).mainCamera.unproject(new Vector3(Gdx.input.getX(0),Gdx.input.getY(0),0));
                Vector2 projectileVel = new Vector2(mousePos.x-playerPos.x,mousePos.y-playerPos.y);
                projectileVel.setLength2(INITIALPROJECTILEVELOCITY);
                Vector2 projectilePos = playerPos.add(new Vector2(projectileVel).setLength2(PROJECTILEOFFSET));
                projectileVel.add(player.getBody().getLinearVelocity());
                Projectile projectile = new Projectile(((NebulaShop) nebula).world(), projectilePos, projectileVel, Projectile.SELECTED_TYPE);
                list.add(() -> projectile);

            } catch (IllegalArgumentException e) {
            }
        }
    }
}
