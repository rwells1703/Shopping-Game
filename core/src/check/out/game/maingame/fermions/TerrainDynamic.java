package check.out.game.maingame.fermions;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class TerrainDynamic extends Terrain {
  public TerrainDynamic(World world, float x, float y, Shape shape) {
    setupBody(world,x,y,shape,BodyType.DynamicBody,4);
  }
}
