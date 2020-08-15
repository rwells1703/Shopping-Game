package check.out.game.maingame.artists;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.flooring.Flooring;
import check.out.game.maingame.fermions.flooring.IceRing;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import fernebon.core.base.Nebula;
import fernebon.core.base.artist.Artist;
import fernebon.core.util.LifeCycleImplementation;

import static check.out.game.maingame.ConstShop.ICE_RING_RADIUS;

public class FlooringHazardsDrawer extends LifeCycleImplementation implements Artist {
    private Texture texture;
    private SpriteBatch batch;
    private Camera camera;
    public FlooringHazardsDrawer(Camera camera){
        this.camera = camera;
        batch=new SpriteBatch();
        texture = new Texture(Gdx.files.internal("hazardTexture.png"));
    }
    @Override
    public int getPriority() {
        return ConstShop.AP_FLOOR_HAZARDS;
    }

    @Override
    public void onDraw(Nebula nebula, float deltaTime) {
        batch.setProjectionMatrix(camera.combined);
        batch.enableBlending();
        batch.begin();

        for(Flooring flooring:nebula.fermions().<Flooring>particles(ConstShop.FB_FLOORING)){
            Vector2 position= flooring.position;
            if(flooring instanceof IceRing){
                batch.draw(texture,position.x-ICE_RING_RADIUS,position.y-ICE_RING_RADIUS,ICE_RING_RADIUS*2,ICE_RING_RADIUS*2,0,0,128,128,false,false);
            }
        }

        batch.end();
    }

    @Override
    public void dispose(Nebula nebula) {
        batch.dispose();
        texture.dispose();
    }
}
