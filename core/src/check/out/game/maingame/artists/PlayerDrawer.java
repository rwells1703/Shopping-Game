package check.out.game.maingame.artists;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Collectible;
import check.out.game.maingame.fermions.Projectile;
import check.out.game.maingame.fermions.Shopper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import fernebon.core.base.Nebula;
import fernebon.core.base.artist.Artist;
import fernebon.core.util.LifeCycleImplementation;

public class PlayerDrawer extends LifeCycleImplementation implements Artist {
  private Camera camera;
  private SpriteBatch batch;

  private Texture playerTexture;
  private Texture collectibleTexture;
  private Texture projectileTexture;

  public PlayerDrawer(Camera camera){
    this.camera=camera;
    batch=new SpriteBatch();
    playerTexture=new Texture(Gdx.files.internal("trolley.png"));
//    collectibleTexture = new Texture(Gdx.files.internal("bananas/banana-full.png"));
//    projectileTexture = new Texture(Gdx.files.internal("bananas/banana-peel.png"));
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
      Vector2 pos = shopper.getBody().getPosition();
      float angle = (float) (shopper.getBody().getAngle() *180/Math.PI);
      batch.draw(new TextureRegion(playerTexture), pos.x-0.5f, pos.y-0.5f, 0.5f, 0.5f, 1f, 1.25f, 1f, 1f, angle);
    }

    for(Projectile projectile: nebula.fermions().<Projectile>particles(ConstShop.FB_PROJECTILE)){
      projectileTexture = new Texture(Gdx.files.internal(ConstShop.PROJECTILE_TEXTURE_PATHS[projectile.type]));
      Vector2 pos = projectile.getBody().getPosition();
      float angle = (float) (projectile.getBody().getAngle() *180/Math.PI);
      batch.draw(new TextureRegion(projectileTexture), pos.x-0.25f, pos.y-0.25f, 0.25f, 0.25f, 0.5f, 0.5f, 1f, 1f, angle);
    }
    for(Collectible collectible: nebula.fermions().<Collectible>particles(ConstShop.FB_COLLECTIBLE)){
//      System.out.println(collectible.type);
      collectibleTexture = new Texture(Gdx.files.internal(ConstShop.COLLECTIBLE_TEXTURE_PATHS[collectible.type]));
      Vector2 pos = collectible.getPosition();
      float angle = (float) (collectible.getSensor().getBody().getAngle() *180/Math.PI);
      batch.draw(new TextureRegion(collectibleTexture), pos.x-0.25f, pos.y-0.25f, 0.25f, 0.25f, 0.5f, 0.5f, 1f, 1f, angle);
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
