package check.out.game.maingame.fermions;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.math.Vector2;

public class Collectible extends SensorFermionPartial {
    public int type;

    public Collectible(NebulaShop nebula, Vector2 position, int type) {
        super(nebula, position);
        this.type = type;
    }

    @Override
    public int getSetMask() {
        return ConstShop.FB_COLLECTIBLE;
    }
}
