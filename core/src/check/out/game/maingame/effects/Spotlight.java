package check.out.game.maingame.effects;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Shopper;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import fernebon.core.base.Nebula;
import fernebon.core.base.effect.Effect;
import fernebon.core.util.LifeCycleImplementation;

/**
 * Tries to point the camera at the player.
 * If possible it will make the player the centre of the viewable screen, but will never point outside of X_MIN,X_MAX,Y_MIN,Y_MAX.
 */
public class Spotlight extends LifeCycleImplementation implements Effect {
    private Camera camera;
    private final float X_MIN,X_MAX,Y_MIN,Y_MAX;
    public Spotlight(Camera camera, float mapRightmost, float mapTopmost){
        this.camera=camera;

        X_MIN=camera.viewportWidth/2f;
        Y_MIN=camera.viewportHeight/2f;
        X_MAX= mapRightmost -X_MIN;
        Y_MAX=mapTopmost-Y_MIN;
    }
    @Override
    public int getPriority() {
        return ConstShop.EP_SPOTLIGHT;
    }

    @Override
    public void onUpdate(Nebula nebula, float deltaTime) {
        Vector2 star = ((NebulaShop) nebula).player.<Shopper>getPointeeCast().getBody().getPosition();
        float x=star.x, y= star.y;
        if(x<X_MIN)         x=X_MIN;
        else if(x>X_MAX)    x=X_MAX;
        if(y<Y_MIN)         y=Y_MIN;
        else if(y>Y_MAX)    y=Y_MAX;
        camera.position.set(x,y,0);
        camera.update();
    }
}
