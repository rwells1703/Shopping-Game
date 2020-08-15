package check.out.game.maingame.fermions.flooring;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.effects.IceIsSlippery;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import fernebon.core.base.Nebula;

public class IceRing extends Flooring {
    private int effectIndex;

    public IceRing(NebulaShop nebula, Vector2 position) {
        super(nebula, position);
        effectIndex = nebula.effects().addWithID(//Add the effect for this iceRing with a "handle" so you can remove the effect when the ice is removed.
                () -> new IceIsSlippery(
                        nebula.fermions().newPointerFor(this)//The slippery effect is given a pointer to this ice ring..
                )
        );
    }

    @Override
    protected Shape getShape(Vector2 position) {
        CircleShape shape = new CircleShape();
        shape.setRadius(ConstShop.ICE_RING_RADIUS);
        shape.setPosition(position);
        return shape;
    }

    @Override
    public void dispose(Nebula nebula) {
        super.dispose(nebula);
        //If the effect to make slipperiness happen still exists, dispose of it. Hmm, should the effect instead automatically dispose of itself if the pointer to IceRing is null?
        if (nebula.effects().contains(effectIndex)) nebula.effects().remove(nebula.effects().get(effectIndex));
    }
}
