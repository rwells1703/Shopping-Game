package check.out.game.maingame.landingactions;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Projectile;
import check.out.game.maingame.fermions.shoppers.Shopper;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import fernebon.core.base.Nebula;

/**
 * Makes an explosion where the projectile lands.
 */
public class LandingExplosive implements LandingAction {
    @Override
    public boolean invoke(Nebula nebula, Projectile projectile) {
        Vector2 epicentre = new Vector2(projectile.getBody().getPosition());
        Vector2 impulse = new Vector2(0, 0);
        Vector2 shopperPosition;
        float separation2;
        Body body;
        for (Shopper shopper : nebula.fermions().<Shopper>particles(ConstShop.FB_SHOPPER)) {//For each shopper.
            body = shopper.getBody();
            shopperPosition = body.getPosition();
            separation2 = impulse.set(shopperPosition).sub(epicentre).len2();//Find out relative position to explosion.
            if (separation2 < ConstShop.EXPLOSION_MAX_RADIUS && separation2 > 0) {//If shopper is in blast radius.
                //Apply an impulse that falls off like r^-2.
                body.applyLinearImpulse(impulse.setLength2(ConstShop.EXPLOSIVE_COEFFICIENT / separation2), body.getPosition(), true);
            }
        }
        return true;
    }
}
