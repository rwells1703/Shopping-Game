package check.out.game.maingame.fermions.flooring;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.SensorFermionPartial;
import check.out.game.maingame.fermions.Shopper;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.ObjectSet;
import fernebon.b2d.util.prehensile.Prehensile;
import fernebon.core.base.fermion.Fermion;

/**
 * This has an ObjectSet (standingOn) that contains all the shoppers standing on this bit of floor.
 */
public class Flooring extends SensorFermionPartial implements Fermion, Prehensile {
    public Flooring(NebulaShop nebula, Vector2 position) {
        super(nebula, position);
        this.position = position;
    }

    @Override
    public int getSetMask() {
        return ConstShop.FB_FLOORING;
    }

    /**
     * The ObjectSet of shoppers that are standing on this piece of flooring.
     */
    public ObjectSet<Shopper> standingOn=new ObjectSet<>();//Hmm, or should this store Fermions so, e.g., projectiles can stand on the floor?
    public Vector2 position;//For drawing purposes.

    @Override
    public void beginContact(Fixture fixture) {
        if(fixture.getUserData() instanceof Shopper)standingOn.add((Shopper) fixture.getUserData());
    }

    @Override
    public void endContact(Fixture fixture) {
        if(fixture.getUserData() instanceof Shopper)standingOn.remove((Shopper) fixture.getUserData());
    }
}
