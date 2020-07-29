package check.out.game.maingame.stellar;

import check.out.game.maingame.ConstShop;
import com.badlogic.gdx.math.Vector2;
import fernebon.b2d.impl.NebulaB2DEasyOverride;
import fernebon.core.base.Pointer;
import fernebon.core.base.fermion.Fermion;

public class NebulaShop extends NebulaB2DEasyOverride {
    @Override
    protected Vector2 getGravity() {
        return new Vector2(0,0);
    }

    @Override
    protected int[] getOptimisedFermionSetMasks() {//These are the sets that the FermionList is (theoretically) optimised to iterate through.
        return new int[]{
                ConstShop.FB_SHOPPER,
        };
    }

    public Pointer<Fermion> player;
}
