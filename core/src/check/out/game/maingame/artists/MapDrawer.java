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
    private int[] layers;

    public MapDrawer(OrthographicCamera camera, TiledMap map, float unitScale, String... layers) {//Todo Add this to Fernebon with unitScale passed in.
        if (layers.length == 0)
            throw new IllegalArgumentException("This map drawer won't draw anything as no layers were specified. This is presumably a mistake.");
        this.camera = camera;
        this.map = map;
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);
        setLayers(map, layers);
    }

    public MapDrawer(OrthographicCamera camera, TiledMap map, TiledMapRenderer renderer, String... layers) {
        if (layers.length == 0)
            throw new IllegalArgumentException("This map drawer won't draw anything as no layers were specified. This is presumably a mistake.");
        this.camera = camera;
        this.map = null;
        this.renderer = renderer;
        setLayers(map, layers);
    }

    private void setLayers(TiledMap map, String[] layerStrings) {
        layers = new int[layerStrings.length];
        for (int i = 0; i < layers.length; i++) {
            layers[i] = map.getLayers().getIndex(layerStrings[i]);
        }
    }

    @Override
    public void onDraw(Nebula nebula, float deltaTime) {
        renderer.setView(camera);
        renderer.render(layers);
    }

    @Override
    public void dispose(Nebula nebula) {
        if (map != null) map.dispose();
    }
}
