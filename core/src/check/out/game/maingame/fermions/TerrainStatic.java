package check.out.game.maingame.fermions;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static check.out.game.maingame.ConstShop.SHELF_UNIT_SIZE;

public class TerrainStatic extends Terrain {
    public void init(World world, int left, int bottom, int width, int height) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(SHELF_UNIT_SIZE * width / 2f, SHELF_UNIT_SIZE * height / 2f);
        setupBody(world, (left + width / 2f) * SHELF_UNIT_SIZE, (bottom + height / 2f) * SHELF_UNIT_SIZE, shape, BodyType.StaticBody, 0);
    }
}
