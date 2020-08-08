package check.out.game.maingame.stellar;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.artists.MapDrawer;
import check.out.game.maingame.artists.PlayerDrawer;
import check.out.game.maingame.colliders.CollectCollectibles;
import check.out.game.maingame.effects.ControllerForcesOnShoppers;
import check.out.game.maingame.effects.LaunchProjectile;
import check.out.game.maingame.effects.Spotlight;
import check.out.game.maingame.effects.ai.KeyboardMovesPlayer;
import check.out.game.maingame.fermions.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import fernebon.b2d.base.collider.ColliderList;
import fernebon.b2d.util.artists.DebugRenderer;
import fernebon.b2d.util.effects.StepperOfWorld;
import fernebon.core.base.artist.ArtistList;
import fernebon.core.base.effect.EffectList;
import fernebon.core.base.fermion.FermionList;
import fernebon.core.impl.SupernovaPartial;
import fernebon.core.util.effects.DrawCaller;
import fernebon.gdx.util.artists.ScreenClearer;

public class SupernovaShop extends SupernovaPartial<NebulaShop> {
    private float   MAP_WIDTH =20,
                    MAP_HEIGHT=15;
    @Override
    protected NebulaShop getNewNebula() {
        return new NebulaShop();
    }

    @Override
    protected void setupNebula(NebulaShop nebulaImplemented) {
        final float VIEWPORT_HEIGHT=6;//The height of the viewed part of the nebula.

        TiledMap map=new TmxMapLoader().load("maps/map1.tmx");
        MapReader reader=new MapReader(nebulaImplemented,map);
        MAP_WIDTH=reader.getMapWidth();
        MAP_HEIGHT=reader.getMapHeight();

        //Setup the camera.
        Camera camera=new OrthographicCamera(((float) Gdx.graphics.getWidth()/(float) Gdx.graphics.getHeight())*VIEWPORT_HEIGHT, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth/2f,camera.viewportHeight/2f,0);
        camera.update();

        addFermions(nebulaImplemented,reader);
        addEffects(nebulaImplemented,camera);
        addArtists(nebulaImplemented,camera,map);
        addColliders(nebulaImplemented);
    }

    protected void addEffects(NebulaShop nebulaImplemented, Camera camera){
        EffectList list=nebulaImplemented.effects();

        list.add(KeyboardMovesPlayer::new);
        list.add(ControllerForcesOnShoppers::new);
        list.add(LaunchProjectile::new);
        list.add(() -> new StepperOfWorld() {
            @Override
            public int getPriority() {
                return ConstShop.EP_PHYSICS_STEP;
            }
        });
        list.add(() -> new Spotlight(camera,MAP_WIDTH,MAP_HEIGHT));
        list.add(() -> new DrawCaller() {
            @Override
            public int getPriority() {
                return ConstShop.EP_DRAW;
            }
        });
    }
    protected void addArtists(NebulaShop nebulaImplemented, Camera camera,TiledMap map){
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
        list.add(() -> new MapDrawer((OrthographicCamera) camera,map,ConstShop.SHELF_UNIT_SIZE/128f) {
            @Override
            public int getPriority() {
                return ConstShop.AP_SHELVING_DRAW;
            }
        });

        list.add(() -> new PlayerDrawer(camera));
    }
    protected void addColliders(NebulaShop nebulaImplemented){
        ColliderList list=nebulaImplemented.colliders();

        list.add(CollectCollectibles::new);
    }
    protected void addFermions(NebulaShop nebulaImplemented,MapReader reader) {
        FermionList list = nebulaImplemented.fermions();

        nebulaImplemented.player = list
            .addWithPointer(() -> new Player(nebulaImplemented.world(), new Vector2(2, 2)));

        //###Begin add collectibles.
        for (int i = 0; i < 10; i++) {
            list.add(() -> new Collectible(nebulaImplemented,new Vector2(MathUtils.random(MAP_WIDTH),MathUtils.random(MAP_HEIGHT))));
        }
        //###End add collectibles.

        list.add(() -> new Projectile(nebulaImplemented.world(), new Vector2(0,0)));

        reader.readInShelving();
    }

    @Override
    public void dispose() {
    }
}
