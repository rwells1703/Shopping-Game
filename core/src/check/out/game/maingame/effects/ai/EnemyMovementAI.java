package check.out.game.maingame.effects.ai;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.shoppers.Enemy;
import check.out.game.maingame.fermions.shoppers.Player;
import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.math.Vector2;
import fernebon.core.base.Nebula;
import fernebon.core.base.effect.Effect;
import fernebon.core.util.LifeCycleImplementation;

import java.util.ArrayList;

/**
 * AI to try to make a given shopper try and ram into the player.
 */
public class EnemyMovementAI extends LifeCycleImplementation implements Effect {
    private Enemy enemy;

    public EnemyMovementAI(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public int getPriority() {
        return ConstShop.EP_AI_KEYBOARD;
    }

    @Override
    public void onUpdate(Nebula nebula, float deltaTime) {
        //Set the player's desired force based on the ws keys - this method doesn't actually apply said force.
        if (enemy.getCurrentLifeCycleState().isDeleted()) {//Just in case - if the enemy is no more, dispose of this effect.
            nebula.effects().delete(this);
            return;
        }

        // Draw raycasts to all the waypoints
        if (ConstShop.DEBUG_DRAW_WAYPOINT_RAYS) {
            for (int i = 0; i < ((NebulaShop) nebula).waypoints.length; i++) {
                ((NebulaShop) nebula).rayCaster.rayCollided(enemy.getBody().getPosition(), ((NebulaShop) nebula).waypoints[i]);
            }
        }

        Player player = ((NebulaShop) nebula).player;
        Vector2[] playerVertices = ((NebulaShop) nebula).rayCaster.getPolygonShapedBodyVertices(player.getBody());

        if (((NebulaShop) nebula).rayCaster.canSee(enemy.getBody().getPosition(), playerVertices)) {
            enemy.controller.desiredForce = new Vector2(player.getBody().getPosition());
            enemy.targetWaypointID = -1;
        } else {
            // If we where chasing the player, relocate to chasing a waypoint
            if (enemy.targetWaypointID == -1 || reachedWaypoint(((NebulaShop) nebula).waypoints[enemy.targetWaypointID])) {
                ArrayList<Integer> possibleWaypointTargets = new ArrayList<>();

                // Pick a random waypoint within view
                for (int i = 0; i < ((NebulaShop) nebula).waypoints.length; i++) {
                    if (!((NebulaShop) nebula).rayCaster.rayCollided(enemy.getBody().getPosition(), ((NebulaShop) nebula).waypoints[i])) {
                        possibleWaypointTargets.add(i);
                    }
                }

                if (possibleWaypointTargets.size() != 0) {
                    enemy.targetWaypointID = possibleWaypointTargets.get((int) (Math.random() * (possibleWaypointTargets.size())));
                }
            }

            // If there are no waypoints in view, the target will remain the player (even though it is behind a wall)
            if (enemy.targetWaypointID == -1) {
                enemy.controller.desiredForce = new Vector2(player.getBody().getPosition());
            } else {
                enemy.controller.desiredForce = new Vector2(((NebulaShop) nebula).waypoints[enemy.targetWaypointID]);
            }
        }

        float enemyAngle = enemy.getBody().getAngle();

        enemy.controller.desiredForce.sub(enemy.getBody().getPosition());//Vector should now be displacement from enemy to player.
        enemy.controller.desiredForce.rotateRad(-enemyAngle);//Vector should now be displacement of player from enemy, in frame in which enemy is facing forward.

        enemy.controller.desiredTorque = ConstShop.SHOPPERTORQUEFACTOR * (enemy.controller.desiredForce.x < 0 ? 1 : -1);//Apply torque to try and point at player.

        enemy.controller.desiredForce.setLength2(ConstShop.ENEMY_THRUST_FACTOR_2);//Now vector is to become desired force - set the length (if displacement is (0,0), there should be a "glitch" in which no force is applied, but then enemy and player coincide...)
        enemy.controller.desiredForce.rotateRad(enemyAngle);//Set force to be in direction of trolley.
    }

    private boolean reachedWaypoint(Vector2 waypoint) {
        return waypoint.dst(enemy.getBody().getPosition()) < 1;
    }
}