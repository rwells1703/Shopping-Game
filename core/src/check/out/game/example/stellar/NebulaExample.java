package check.out.game.example.stellar;

import com.badlogic.gdx.math.Vector2;
import fernebon.b2d.impl.NebulaB2DEasyOverride;

public class NebulaExample extends NebulaB2DEasyOverride {
    @Override
    protected Vector2 getGravity() {
        return new Vector2(0,-9.8f);
    }

    @Override
    protected int[] getOptimisedFermionSetMasks() {
        return new int[0];
    }
}
