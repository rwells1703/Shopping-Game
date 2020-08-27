package check.out.game.maingame.effects;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Player;
import check.out.game.maingame.fermions.Projectile;
import check.out.game.maingame.landingactions.LandingAction;
import check.out.game.maingame.landingactions.LandingBeans;
import check.out.game.maingame.landingactions.LandingExplosive;
import check.out.game.maingame.landingactions.LandingIceCream;
import check.out.game.maingame.nonfermions.InputCore;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import fernebon.core.base.Nebula;
import fernebon.core.base.Pointer;
import fernebon.core.base.effect.Effect;
import fernebon.core.base.fermion.Fermion;
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
        Player player = ((NebulaShop) nebula).player.getPointeeCast();

        if (player.cargo.mass == 0)
            Projectile.SELECTED_TYPE = 0; //if no items in inventory, then scroll wheel doesn't have any effect

        Vector2 playerPos = player.getBody().getPosition();
        float playerAngle = player.getBody().getAngle();

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            Projectile.SELECTED_TYPE = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            Projectile.SELECTED_TYPE = 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            Projectile.SELECTED_TYPE = 2;
        }

        Gdx.input.setInputProcessor(new InputCore()); //for scrolling

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            try {
                int type = Projectile.SELECTED_TYPE;
                player.removeOneOf(type);

                Vector3 mousePos = ((NebulaShop) nebula).camera.unproject(new Vector3(Gdx.input.getX(0), Gdx.input.getY(0), 0));
                Vector2 projectileVel = new Vector2(mousePos.x - playerPos.x, mousePos.y - playerPos.y);
                projectileVel.setLength2(INITIALPROJECTILEVELOCITY);
                Vector2 projectilePos = playerPos.add(new Vector2(projectileVel).setLength2(PROJECTILEOFFSET));
                projectileVel.add(player.getBody().getLinearVelocity());
                launchProjectile((NebulaShop) nebula, projectilePos, projectileVel, type);
            } catch (IllegalArgumentException e) {

            }
        }
    }

    private void launchProjectile(NebulaShop nebulaShop, Vector2 projectilePos, Vector2 projectileVel, int type) {
        if (hasAction(type)) {//This is for if the projectile has a landing action.
            Pointer<Fermion> pointer = nebulaShop.fermions().addWithPointer(() -> new Projectile(nebulaShop.world(), projectilePos, projectileVel, type));
            nebulaShop.effects().add(
                    () -> new ProjectileCausesActionOnLanding(pointer, getAction(type))
            );
        } else {//This is for if the projectile has no landing action.
            nebulaShop.fermions().add(() -> new Projectile(nebulaShop.world(), projectilePos, projectileVel, type));
        }
    }

    private boolean hasAction(int type) {
        return (0 <= type && type <= 2);
    }

    private LandingAction getAction(int type) {
        switch (type) {
            case 0://Beans.
                return new LandingBeans();
            case 1://Banana
                return new LandingExplosive();
            case 2://Ice cream.
                return new LandingIceCream();
            default:
                throw new IllegalArgumentException("There is no action for projectiles of type#" + type + ".");
        }
    }
}
