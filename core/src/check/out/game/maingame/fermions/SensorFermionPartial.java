package check.out.game.maingame.fermions;

import check.out.game.maingame.stellar.NebulaShop;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import fernebon.core.base.Nebula;
import fernebon.core.base.fermion.Fermion;
import fernebon.core.util.LifeCycleImplementation;

public abstract class SensorFermionPartial extends LifeCycleImplementation implements Fermion {
    protected Fixture sensor;
    private Vector2 position;

    public Fixture getSensor(){return sensor;}

    /**
     * Override to change shape, it will be disposed of.
     */
    protected Shape getShape(Vector2 position){
        CircleShape shape=new CircleShape();
        shape.setRadius(0.25f);
        shape.setPosition(position);
        return shape;
    }

    public SensorFermionPartial(NebulaShop nebula, Vector2 position){
        this.position = position;

        Shape shape=getShape(position);

        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.shape=shape;
        fixtureDef.isSensor=true;//Fixture is sensor - no physics is applied.

        sensor=nebula.bodyForSensors.createFixture(fixtureDef);
        sensor.setUserData(this);

        shape.dispose();
    }
    @Override
    public void dispose(Nebula nebula) {
        ((NebulaShop)nebula).bodyForSensors.destroyFixture(sensor);
    }

    public Vector2 getPosition(){
        return this.position;
    }
}
