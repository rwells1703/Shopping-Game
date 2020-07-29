package check.out.game.maingame.nonfermions;

import com.badlogic.gdx.math.Vector2;

public class ControllerSimple implements Controller {
    private Vector2 desiredForce=new Vector2();//getDesiredForce always returns this Vector2, so to specify what getDesiredForce should return, call getDesiredForce and modify the returned Vector2.

    @Override
    public Vector2 getDesiredForce() {
        return desiredForce;
    }
}
