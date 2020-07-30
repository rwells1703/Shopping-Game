package check.out.game.maingame.effects.ai;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Shopper;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import fernebon.core.base.Nebula;
import fernebon.core.base.effect.Effect;
import fernebon.core.util.LifeCycleImplementation;

public class KeyboardMovesPlayer extends LifeCycleImplementation implements Effect {
    @Override
    public int getPriority() {
        return ConstShop.EP_AI_KEYBOARD;
    }

    @Override
    public void onUpdate(Nebula nebula, float deltaTime) {
        //Set the player's desired force based on the wasd keys - this method doesn't actually apply said force.
        Shopper player=((NebulaShop)nebula).player.getPointeeCast();
        Vector2 desiredForce=player.controller.desiredForce;
        desiredForce.x=(Gdx.input.isKeyPressed(Input.Keys.D)?1:0) + (Gdx.input.isKeyPressed(Input.Keys.A)?-1:0);
        desiredForce.y=(Gdx.input.isKeyPressed(Input.Keys.W)?1:0) + (Gdx.input.isKeyPressed(Input.Keys.S)?-1:0);
        desiredForce.setLength2(100);
    }
}
