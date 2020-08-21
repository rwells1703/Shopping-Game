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

    private Texture entityTexture;
    private TextureRegion playerTextureRegion;
    private TextureRegion[] collectibleTextureRegions;
    private TextureRegion[] projectileTextureRegions;

    public PlayerDrawer(Camera camera) {
        this.camera = camera;
        batch = new SpriteBatch();

        entityTexture = new Texture(Gdx.files.internal("entityTexture.png"));
        playerTextureRegion = new TextureRegion(entityTexture, 0, 0, 92, 128);

        collectibleTextureRegions = new TextureRegion[]{
                new TextureRegion(entityTexture, 92, 0, 64, 64),
                new TextureRegion(entityTexture, 156, 0, 64, 64),
                new TextureRegion(entityTexture, 220, 0, 64, 64)
        };

        projectileTextureRegions = new TextureRegion[]{
                new TextureRegion(entityTexture, 92, 64, 64, 64),
                new TextureRegion(entityTexture, 156, 64, 64, 64),
                new TextureRegion(entityTexture, 220, 64, 64, 64)

        };
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

        for (Shopper shopper : nebula.fermions().<Shopper>particles(ConstShop.FB_SHOPPER)) {
            Vector2 pos = shopper.getBody().getPosition();
            float angle = (float) (shopper.getBody().getAngle() * 180 / Math.PI);
            batch.draw(playerTextureRegion, pos.x - 0.5f, pos.y - 0.5f, 0.5f, 0.5f, 1f, 1.25f, 1f, 1f, angle);

        }

        for (Projectile projectile : nebula.fermions().<Projectile>particles(ConstShop.FB_PROJECTILE)) {
            Vector2 pos = projectile.getBody().getPosition();
            float angle = (float) (projectile.getBody().getAngle() * 180 / Math.PI);
            TextureRegion region;
            if (projectile.transformed) {
                region = projectileTextureRegions[projectile.type];
            } else {
                region = collectibleTextureRegions[projectile.type];
            }
            batch.draw(region, pos.x - 0.25f, pos.y - 0.25f, 0.25f, 0.25f, 0.5f, 0.5f, 1f, 1f, angle);


        }

        for (Collectible collectible : nebula.fermions().<Collectible>particles(ConstShop.FB_COLLECTIBLE)) {
            Vector2 pos = collectible.getPosition();
            float angle = (float) (collectible.getSensor().getBody().getAngle() * 180 / Math.PI);
            batch.draw(collectibleTextureRegions[collectible.type], pos.x - 0.25f, pos.y - 0.25f, 0.25f, 0.25f, 0.5f, 0.5f, 1f, 1f, angle);
        }

        batch.end();
    }

    @Override
    public void dispose(Nebula nebula) {
        batch.dispose();
        entityTexture.dispose();
    }
}
