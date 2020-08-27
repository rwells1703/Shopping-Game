package check.out.game.maingame.fermions.shoppers;

import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Shopper {
    public int targetWaypointID = -1;

    public Enemy(NebulaShop nebula, Vector2 position) {
        super(nebula, position);
    }
}
