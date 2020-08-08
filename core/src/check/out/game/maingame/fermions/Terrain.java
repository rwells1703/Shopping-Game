package check.out.game.maingame.fermions;

import check.out.game.maingame.ConstShop;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import fernebon.b2d.util.fermions.BodiedFermionPartial;
import fernebon.core.base.fermion.Fermion;

public abstract class Terrain extends BodiedFermionPartial implements Fermion {
  /**
   * @param shape This is automatically disposed of.
   */
  protected void setupBody(World world, float x, float y, Shape shape, BodyType bodyType, int linearDamping){
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = bodyType;
    bodyDef.position.set(x,y);
    bodyDef.linearDamping = linearDamping;

    body = world.createBody(bodyDef);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;

    body.createFixture(fixtureDef);
    body.setUserData(this);

    shape.dispose();
  }

  public int getSetMask() {
    return ConstShop.FB_TERRAIN;
  }
}