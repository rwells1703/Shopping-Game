package check.out.game.maingame.artists;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Player;
import check.out.game.maingame.fermions.Projectile;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fernebon.core.base.Nebula;
import fernebon.core.base.artist.Artist;
import fernebon.core.util.LifeCycleImplementation;

public class HotbarDrawer extends LifeCycleImplementation implements Artist {
    private Camera camera;
    private SpriteBatch batch;

    private Texture hotbarTexture;
    private Texture entityTexture;

    private TextureRegion[] hotbarTextureRegions;
    private TextureRegion[] collectibleTextureRegions;

    private int[] hotbar = new int[4];

    public HotbarDrawer(Camera camera) {
        this.camera = camera;
        batch = new SpriteBatch();

        hotbarTexture = new Texture(Gdx.files.internal("hotbar/hotbarTiles.png"));
        hotbarTextureRegions = new TextureRegion[]{
                new TextureRegion(hotbarTexture, 0, 0, 64, 64), //not selected
                new TextureRegion(hotbarTexture, 64, 0, 64, 64) //selected
        };
        entityTexture = new Texture(Gdx.files.internal("entityTexture.png"));
        collectibleTextureRegions = new TextureRegion[]{
                new TextureRegion(entityTexture, 92, 0, 64, 64),
                new TextureRegion(entityTexture, 156, 0, 64, 64),
                new TextureRegion(entityTexture, 220, 0, 64, 64)
        };
    }

    @Override
    public int getPriority() {
        return ConstShop.AP_HOTBAR_DRAW;
    }

    @Override
    public void onDraw(Nebula nebula, float deltaTime) {
        batch.setProjectionMatrix(camera.projection);
        batch.begin();


        Player player = ((NebulaShop) nebula).player.getPointeeCast();

        int index = 0;
        for (int i = 0; i < ConstShop.HOTBAR_MAX; i++) {
            if (player.cargo.quantity.get(i, 0) > 0 && Projectile.SELECTED_TYPE == i) index = 1;
            batch.draw(hotbarTextureRegions[index], i - (float) ConstShop.HOTBAR_MAX / 2, -2.9f, 0, 0, 1f, 1f, 1f, 1f, 0);
            index = 0;
        }

        for (int i = 0; i < ConstShop.NUM_COLLECTIBLE_TYPES; i++) {
            hotbar[i] = player.cargo.quantity.get(i, 0);
        }

        for (int i = 0; i < hotbar.length; i++) {
            if (hotbar[i] > 0)
                batch.draw(collectibleTextureRegions[i], i - (float) ConstShop.HOTBAR_MAX / 2, -2.9f, 0, 0, 1f, 1f, 1f, 1f, 0);
        }


        batch.end();
    }

    @Override
    public void dispose(Nebula nebula) {
        batch.dispose();
        entityTexture.dispose();
    }
}
