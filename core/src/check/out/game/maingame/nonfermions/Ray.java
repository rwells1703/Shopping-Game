package check.out.game.maingame.nonfermions;

import com.badlogic.gdx.math.Vector2;

public class Ray {
    public Vector2 startPoint;
    public Vector2 endPoint;
    public boolean collided;

    public Ray(Vector2 startPoint, Vector2 endPoint, boolean collided) {
        this.startPoint = new Vector2(startPoint);
        this.endPoint = new Vector2(endPoint);
        this.collided = collided;
    }
}
