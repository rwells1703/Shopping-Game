package check.out.game.maingame.fermions;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.nonfermions.Cargo;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Shopper {

    public Player(World world, Vector2 position) {
        super(world, position);
    }

    public Cargo cargo = new Cargo(10);

    public void updateMass() {
        getBody().setLinearDamping(ConstShop.SHOPPERLINEARDAMPING + cargo.getMass() * ConstShop.MASSLINEARDAMPINGFACTOR);
        getBody().setAngularDamping(ConstShop.SHOPPERANGULARDAMPING + cargo.getMass() * ConstShop.MASSANGULARDAMPINGFACTOR);
    }

    public void addOneOf(int type) {
        cargo.addOneOf(type);
        updateMass();
    }

    public void removeOneOf(int type) {
        cargo.removeOneOf(type);
        updateMass();
    }
}
