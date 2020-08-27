package check.out.game.maingame.nonfermions;

import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.ArrayList;

public class RayCaster {
    public ArrayList<Ray> castedRays = new ArrayList<>();
    private NebulaShop nebula;

    public RayCaster(NebulaShop nebula) {
        this.nebula = nebula;
    }

    public boolean canSee(Vector2 observerPosition, Vector2[] targetPositions) {
        for (Vector2 targetPos : targetPositions) {
            if (!rayCollided(observerPosition, targetPos)) {
                return true;
            }
        }

        return false;
    }

    public boolean rayCollided(Vector2 positionA, Vector2 positionB) {
        ToggledRayCastCallback callback = new ToggledRayCastCallback();

        nebula.world().rayCast(callback, positionA, positionB);

        // Add the ray to the list of rays to be drawn
        castedRays.add(new Ray(positionA, positionB, callback.collided));

        return callback.collided;
    }

    public Vector2[] getPolygonShapedBodyVertices(Body body) {
        PolygonShape shape = (PolygonShape) body.getFixtureList().get(0).getShape();
        Vector2[] playerVertices = new Vector2[shape.getVertexCount()];

        for (int i = 0; i < shape.getVertexCount(); i++) {
            playerVertices[i] = new Vector2();
            shape.getVertex(i, playerVertices[i]);
            playerVertices[i].add(body.getPosition());
            playerVertices[i].rotateAround(body.getPosition(), (float) Math.toDegrees(body.getAngle()));
        }

        return playerVertices;
    }
}