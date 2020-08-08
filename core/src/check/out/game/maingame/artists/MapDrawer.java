package check.out.game.maingame.artists;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import fernebon.core.base.Nebula;
import fernebon.core.base.artist.Artist;
import fernebon.core.util.LifeCycleImplementation;

public abstract class MapDrawer extends LifeCycleImplementation implements Artist {
    private OrthographicCamera camera;
    private TiledMap map;
    private TiledMapRenderer renderer;
    public MapDrawer(OrthographicCamera camera, TiledMap map, float unitScale){//Todo Add this to Fernebon with unitScale passed in.
        this.camera=camera;
        this.map=map;
        renderer=new OrthogonalTiledMapRenderer(map,unitScale);
    }

    @Override
    public void onDraw(Nebula nebula, float deltaTime) {
        renderer.setView(camera);
        renderer.render();
    }

    @Override
    public void dispose(Nebula nebula) {
        map.dispose();
    }
}
