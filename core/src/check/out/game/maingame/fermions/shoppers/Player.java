package check.out.game.maingame.fermions.shoppers;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.nonfermions.Cargo;

public class Player extends Shopper {

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
