package check.out.game.maingame.stellar;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.effects.ControllerForcesOnShoppers;
import check.out.game.maingame.effects.ai.KeyboardMovesPlayer;
import check.out.game.maingame.fermions.Shopper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import fernebon.b2d.util.artists.DebugRenderer;
import fernebon.b2d.util.effects.StepperOfWorld;
import fernebon.core.base.artist.ArtistList;
import fernebon.core.base.effect.EffectList;
import fernebon.core.base.fermion.FermionList;
import fernebon.core.impl.SupernovaPartial;
import fernebon.core.util.effects.DrawCaller;
import fernebon.gdx.util.artists.ScreenClearer;

public class SupernovaShop extends SupernovaPartial<NebulaShop> {
    private int MAP_WIDTH =20,
                MAP_HEIGHT=15;
    @Override
    protected NebulaShop getNewNebula() {
        return new NebulaShop();
    }

    @Override
    protected void setupNebula(NebulaShop nebulaImplemented) {
        final float VIEWPORT_HEIGHT=6;//The height of the viewed part of the nebula.
        //Setup the camera.
        Camera camera=new OrthographicCamera(((float) Gdx.graphics.getWidth()/(float) Gdx.graphics.getHeight())*VIEWPORT_HEIGHT, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth/2f,camera.viewportHeight/2f,0);
        camera.update();

        addFermions(nebulaImplemented);
        addEffects(nebulaImplemented,camera);
        addArtists(nebulaImplemented,camera);
        addColliders(nebulaImplemented);
    }

    protected void addEffects(NebulaShop nebulaImplemented, Camera camera){
        EffectList list=nebulaImplemented.effects();

        list.add(KeyboardMovesPlayer::new);
        list.add(ControllerForcesOnShoppers::new);
        list.add(() -> new StepperOfWorld() {
            @Override
            public int getPriority() {
                return ConstShop.EP_PHYSICS_STEP;
            }
        });
//        list.add(() -> new Spotlight(camera,MAP_WIDTH,MAP_HEIGHT));
        list.add(() -> new DrawCaller() {
            @Override
            public int getPriority() {
                return ConstShop.EP_DRAW;
            }
        });
    }
    protected void addArtists(NebulaShop nebulaImplemented, Camera camera){
        ArtistList list=nebulaImplemented.artists();

        list.add(() -> new ScreenClearer() {
            @Override
            public int getPriority() {
                return ConstShop.AP_SCREEN_CLEAR;
            }
        });
        list.add(() -> new DebugRenderer(camera,nebulaImplemented.world()) {
            @Override
            public int getPriority() {
                return ConstShop.AP_DEBUG_DRAW;
            }
        });
    }
    protected void addColliders(NebulaShop nebulaImplemented){
    }
    protected void addFermions(NebulaShop nebulaImplemented){
        FermionList list=nebulaImplemented.fermions();

        nebulaImplemented.player=list.addWithPointer(() -> new Shopper(nebulaImplemented.world(),new Vector2(0,0)));
    }

    @Override
    public void dispose() {

    }
}
