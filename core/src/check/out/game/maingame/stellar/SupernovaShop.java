package check.out.game.maingame.stellar;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.artists.*;
import check.out.game.maingame.colliders.CollectCollectibles;
import check.out.game.maingame.colliders.ShopperShelfCrash;
import check.out.game.maingame.colliders.ShopperShopperCrash;
import check.out.game.maingame.effects.*;
import check.out.game.maingame.fermions.shoppers.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import fernebon.b2d.base.collider.ColliderList;
import fernebon.b2d.util.artists.DebugRenderer;
import fernebon.b2d.util.effects.StepperOfWorld;
import fernebon.b2d.util.prehensile.PrehensileCollisionManager;
import fernebon.core.base.artist.ArtistList;
import fernebon.core.base.effect.EffectList;
import fernebon.core.base.fermion.FermionList;
import fernebon.core.impl.SupernovaPartial;
import fernebon.core.util.effects.DrawCaller;
import fernebon.gdx.util.artists.ScreenClearer;

public class SupernovaShop extends SupernovaPartial<NebulaShop> {
    private float MAP_WIDTH = 20,
            MAP_HEIGHT = 15;

    @Override
    protected NebulaShop getNewNebula() {
        return new NebulaShop();
    }

    @Override
    protected void setupNebula(NebulaShop nebulaImplemented) {
        final float VIEWPORT_HEIGHT = ConstShop.VIEWPORT_HEIGHT;//The height of the viewed part of the nebula.

        TiledMap map = new TmxMapLoader().load("maps/map1.tmx");
        MapReader reader = new MapReader(nebulaImplemented, map);
        MAP_WIDTH = reader.getMapWidth();
        MAP_HEIGHT = reader.getMapHeight();

        //Setup the camera.
        nebulaImplemented.camera = new OrthographicCamera(((float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight()) * VIEWPORT_HEIGHT, VIEWPORT_HEIGHT);
        nebulaImplemented.camera.position.set(nebulaImplemented.camera.viewportWidth / 2f, nebulaImplemented.camera.viewportHeight / 2f, 0);
        nebulaImplemented.camera.update();

        addFermions(nebulaImplemented, reader);
        addEffects(nebulaImplemented, nebulaImplemented.camera);
        addArtists(nebulaImplemented, nebulaImplemented.camera, map);
        addColliders(nebulaImplemented);
    }

    protected void addEffects(NebulaShop nebulaImplemented, Camera camera) {
        EffectList list = nebulaImplemented.effects();

        list.add(new KeyboardMovesPlayer(), null);
        list.add(new ControllerForcesOnShoppers(), null);
        list.add(new LaunchProjectile(), null);
        list.add(new Gravity(), null);
        list.add(new StepperOfWorld() {
            @Override
            public int getPriority() {
                return ConstShop.EP_PHYSICS_STEP;
            }
        }, null);
        list.add(new Spotlight(camera, MAP_WIDTH, MAP_HEIGHT), null);
        list.add(new DrawCaller() {
            @Override
            public int getPriority() {
                return ConstShop.EP_DRAW;
            }
        }, null);
    }

    protected void addArtists(NebulaShop nebulaImplemented, Camera camera, TiledMap map) {
        ArtistList list = nebulaImplemented.artists();

        list.add(new ScreenClearer() {
            @Override
            public int getPriority() {
                return ConstShop.AP_SCREEN_CLEAR;
            }
        }, null);

        if (ConstShop.DEBUG_DRAW_BODIES) {
            list.add(new DebugRenderer(camera, nebulaImplemented.world()) {
                @Override
                public int getPriority() {
                    return ConstShop.AP_DEBUG_DRAW;
                }
            }, null);
        }

        if (ConstShop.DEBUG_DRAW_ACTIVE_RAYS) {
            list.add(new RayCastDrawer(camera), null);
        }

        list.add(new MapDrawer((OrthographicCamera) camera, map, ConstShop.SHELF_UNIT_SIZE / 128f, "floor") {
            @Override
            public int getPriority() {
                return ConstShop.AP_FLOOR_DRAW;
            }
        }, null);

        list.add(new MapDrawer((OrthographicCamera) camera, map, ConstShop.SHELF_UNIT_SIZE / 128f, "shelves") {
            @Override
            public int getPriority() {
                return ConstShop.AP_SHELVING_DRAW;
            }
        }, null);

        list.add(new PlayerDrawer(camera), null);
        list.add(new HotbarDrawer(camera), null);
        list.add(new FlooringHazardsDrawer(camera), null);
    }

    protected void addColliders(NebulaShop nebulaImplemented) {
        ColliderList list = nebulaImplemented.colliders();

        list.add(new CollectCollectibles(), null);
        list.add(new ShopperShopperCrash(), null);
        list.add(new ShopperShelfCrash(), null);
        list.add(new PrehensileCollisionManager() {
            @Override
            public int getPriority() {
                return ConstShop.CP_ON_FLOORING;
            }
        }, null);
    }

    protected void addFermions(NebulaShop nebulaImplemented, MapReader reader) {
        FermionList list = nebulaImplemented.fermions();

        /*
        The add method returns the added fermion particle (here the player), already of type Q (here Player).
        The fermion passed in is the player object. It must not be setup with any side-effects
            E.g. cargo is allowed to be created, as (I think) it effectively has no side-effects - I think it is effectively just data stored in the fermion.
            However, the Box2D body should not be initialised as it has side effects (a body appears in the world, which may be invisible if the fermion isn't added, and I think can cause issues if added at the wrong time (like during a physics step)).
        Any side-effect initialisation should be done by the Modifier<Q> init object/function.
            This has an invoke method which acts on (is called on) the player just before it is added to the list.
            In this case, that invoke method calls the player init method, passing in what is necessary to initialise Box2D stuff.
        */
        nebulaImplemented.player = list
                .add(new Player(), it -> it.init(nebulaImplemented, new Vector2(2, 2)));

        reader.readInShelving();
        reader.readInObjects();
    }

    @Override
    public void dispose() {
    }
}