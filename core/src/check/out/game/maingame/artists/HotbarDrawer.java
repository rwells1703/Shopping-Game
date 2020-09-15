package check.out.game.maingame.artists;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Projectile;
import check.out.game.maingame.fermions.shoppers.Player;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import fernebon.core.base.Nebula;
import fernebon.core.base.artist.Artist;
import fernebon.core.util.LifeCycleImplementation;

public class HotbarDrawer extends LifeCycleImplementation implements Artist {
    private Camera camera;
    private SpriteBatch batch;

    private Texture digitTexture;
    private Texture hotbarTexture;
    private Texture entityTexture;

    private Group hotbarLabels;
    private TextureRegion[] hotbarTextureRegions;
    private TextureRegion[] collectibleTextureRegions;
    private Skin skin;
    private Stage stage;

    private int[] hotbar = new int[4];

    public HotbarDrawer(Camera camera) {
        this.camera = camera;
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("skin/flat-earth/flat-earth-ui.json")); //need to change so access from game object
        stage = new Stage();

        final int tileDimension = Gdx.graphics.getWidth() / ConstShop.WIDTH;

        hotbarLabels = new Group();

        for (int i = 0; i < ConstShop.HOTBAR_MAX; i++) {
            Label label = new Label("", skin);
            label.setFontScale(1.5f);
            label.setWidth(10f);
            label.setHeight(10f);
            label.setPosition((float) (i + 1.6) * tileDimension, (float) (0.9 * tileDimension));
            hotbarLabels.addActor(label);
        }
        stage.addActor(hotbarLabels);


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

        Player player = ((NebulaShop) nebula).player;

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

        for (int i = 0; i < ConstShop.HOTBAR_MAX; i++) {
            int quantity = player.cargo.quantity.get(i, 0);
            ((Label) hotbarLabels.getChild(i)).setText(quantity == 0 ? "" : Integer.toString(quantity));
        }

        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void dispose(Nebula nebula) {
//        Gdx.input.setInputProcessor(null);
        stage.dispose();
        batch.dispose();
        entityTexture.dispose();
    }
}
