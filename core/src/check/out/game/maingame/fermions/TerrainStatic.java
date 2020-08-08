package check.out.game.maingame.fermions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class TerrainStatic extends Terrain {
  public TerrainStatic(World world, Vector2 position, float size) {
    super(world, position, size, BodyType.StaticBody, 0);
  }
}
