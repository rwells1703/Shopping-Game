package check.out.game.maingame.artists;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Projectile;
import check.out.game.maingame.fermions.Shopper;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fernebon.core.base.Nebula;
import fernebon.core.base.artist.Artist;
import fernebon.core.base.fermion.FermionList;
import fernebon.core.util.LifeCycleImplementation;

public class PlayerDrawer extends LifeCycleImplementation implements Artist {
  private Camera camera;
  private SpriteBatch batch;
  private Texture playerTexture;
  private Texture projectileTexture;

  public PlayerDrawer(Camera camera){
    this.camera=camera;
    batch=new SpriteBatch();
    playerTexture=new Texture(Gdx.files.internal("trolley.png"));
    projectileTexture = new Texture(Gdx.files.internal("banana-peel.png"));
  }

  @Override
  public int getPriority() {
    return ConstShop.AP_PLAYER_DRAW;
  }

  @Override
  public void onDraw(Nebula nebula, float deltaTime) {
    batch.setProjectionMatrix(camera.combined);
    batch.enableBlending();
    batch.begin();

    for(Shopper shopper: nebula.fermions().<Shopper>particles(ConstShop.FB_SHOPPER)){
      float angle = (float) (shopper.getBody().getAngle() *180/Math.PI);
      batch.draw(new TextureRegion(playerTexture), shopper.getBody().getPosition().x-0.5f, shopper.getBody().getPosition().y-0.5f, 0.5f, 0.5f, 1f, 1.25f, 1f, 1f, angle);
    }

    for(Projectile projectile: nebula.fermions().<Projectile>particles(ConstShop.FB_PROJECTILE)){
      float angle = (float) (projectile.getBody().getAngle() *180/Math.PI);
      batch.draw(new TextureRegion(projectileTexture), projectile.getBody().getPosition().x-0.25f, projectile.getBody().getPosition().y-0.25f, 0.25f, 0.25f, 0.5f, 0.5f, 1f, 1f, angle);
    }

    batch.end();
  }

  @Override
  public void dispose(Nebula nebula) {
    batch.dispose();
    playerTexture.dispose();
    projectileTexture.dispose();
  }
}
