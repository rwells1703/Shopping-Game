package check.out.game.maingame.nonfermions;

import com.badlogic.gdx.math.Vector2;

/**
 * The shopper AI specifies what they WOULD LIKE the shopper to do by editing the shopper's controller.
 * Whether the effects listen is not guaranteed (e.g. for a trolley with a dodgy wheel).
 */
public class Controller {
    public Vector2 desiredForce = new Vector2();
    public float desiredTorque = 0f;
}