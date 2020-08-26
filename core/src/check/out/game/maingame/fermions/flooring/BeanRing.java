package check.out.game.maingame.fermions.flooring;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class BeanRing extends Flooring {
    public BeanRing(NebulaShop nebula, Vector2 position) {
        super(nebula, position);
    }

    @Override
    protected Shape getShape(Vector2 position) {
        CircleShape shape = new CircleShape();
        shape.setRadius(ConstShop.BEAN_RING_RADIUS);
        shape.setPosition(position);
        return shape;
    }
}
