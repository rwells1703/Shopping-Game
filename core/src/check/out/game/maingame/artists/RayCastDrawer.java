package check.out.game.maingame.artists;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.nonfermions.Ray;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import fernebon.core.base.Nebula;
import fernebon.core.base.artist.Artist;
import fernebon.core.util.LifeCycleImplementation;

public class RayCastDrawer extends LifeCycleImplementation implements Artist {
    private Camera camera;
    private ShapeRenderer shapeRenderer;

    public RayCastDrawer(Camera camera) {
        this.camera = camera;
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setAutoShapeType(true);
    }

    @Override
    public int getPriority() {
        return ConstShop.AP_RAY_CAST_DRAW;
    }

    @Override
    public void onDraw(Nebula nebula, float deltaTime) {
        shapeRenderer.begin();


        for (Ray ray : ((NebulaShop) nebula).rayCaster.castedRays) {
            if (ray.collided) {
                shapeRenderer.setColor(Color.RED);
            } else {
                shapeRenderer.setColor(Color.LIME);
            }

            Vector2 camPos2d = new Vector2(camera.position.x - camera.viewportWidth / 2, camera.position.y - camera.viewportHeight / 2);

            shapeRenderer.line(ray.startPoint.sub(camPos2d), ray.endPoint.sub(camPos2d));
        }

        shapeRenderer.end();


        // Clear the rays
        ((NebulaShop) nebula).rayCaster.castedRays.clear();
    }

    @Override
    public void dispose(Nebula nebula) {
        shapeRenderer.dispose();
    }
}
