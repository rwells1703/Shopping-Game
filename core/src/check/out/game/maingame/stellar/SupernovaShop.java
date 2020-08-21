package check.out.game.maingame.stellar;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.artists.FlooringHazardsDrawer;
import check.out.game.maingame.artists.HotbarDrawer;
import check.out.game.maingame.artists.MapDrawer;
import check.out.game.maingame.artists.PlayerDrawer;
import check.out.game.maingame.colliders.CollectCollectibles;
import check.out.game.maingame.colliders.ShopperShelfCrash;
import check.out.game.maingame.colliders.ShopperShopperCrash;
import check.out.game.maingame.effects.*;
import check.out.game.maingame.effects.ai.ObnoxiousRamsPlayer;
import check.out.game.maingame.fermions.Collectible;
import check.out.game.maingame.fermions.Player;
import check.out.game.maingame.fermions.Shopper;
import check.out.game.maingame.fermions.flooring.IceRing;
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
import fernebon.b2d.util.prehensile.PrehensileCollisionManager;
import fernebon.core.base.artist.ArtistList;
import fernebon.core.base.effect.EffectList;
import fernebon.core.base.fermion.FermionList;
import fernebon.core.impl.SupernovaPartial;
import fernebon.core.util.effects.DrawCaller;
import fernebon.gdx.util.artists.ScreenClearer;

import java.util.Random;

public class SupernovaShop extends SupernovaPartial<NebulaShop> {
    private float MAP_WIDTH = 20,
            MAP_HEIGHT = 15;
    private boolean debug = false;

    @Override
    protected NebulaShop getNewNebula() {
        return new NebulaShop();
    }

    @Override
    protected void setupNebula(NebulaShop nebulaImplemented) {
        final float VIEWPORT_HEIGHT = 6;//The height of the viewed part of the nebula.

        TiledMap map = new TmxMapLoader().load("maps/map1.tmx");
        MapReader reader = new MapReader(nebulaImplemented, map);
        MAP_WIDTH = reader.getMapWidth();
        MAP_HEIGHT = reader.getMapHeight();

        //Setup the camera.
        Camera camera = new OrthographicCamera(((float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight()) * VIEWPORT_HEIGHT, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        nebulaImplemented.mainCamera = camera;

        addFermions(nebulaImplemented, reader);
        addEffects(nebulaImplemented, camera);
        addArtists(nebulaImplemented, camera, map);
        addColliders(nebulaImplemented);
    }

    protected void addEffects(NebulaShop nebulaImplemented, Camera camera) {
        EffectList list = nebulaImplemented.effects();

        list.add(KeyboardMovesPlayer::new);
        list.add(ControllerForcesOnShoppers::new);
        list.add(LaunchProjectile::new);
        list.add(Gravity::new);
        list.add(() -> new StepperOfWorld() {
            @Override
            public int getPriority() {
                return ConstShop.EP_PHYSICS_STEP;
            }
        });
        list.add(() -> new Spotlight(camera, MAP_WIDTH, MAP_HEIGHT));
        list.add(() -> new DrawCaller() {
            @Override
            public int getPriority() {
                return ConstShop.EP_DRAW;
            }
        });
    }

    protected void addArtists(NebulaShop nebulaImplemented, Camera camera, TiledMap map) {
        ArtistList list = nebulaImplemented.artists();

        list.add(() -> new ScreenClearer() {
            @Override
            public int getPriority() {
                return ConstShop.AP_SCREEN_CLEAR;
            }
        });
        if (debug) {
            list.add(() -> new DebugRenderer(camera, nebulaImplemented.world()) {
                @Override
                public int getPriority() {
                    return ConstShop.AP_DEBUG_DRAW;
                }
            });
        }
        list.add(() -> new MapDrawer((OrthographicCamera) camera, map, ConstShop.SHELF_UNIT_SIZE / 128f, "floor") {
            @Override
            public int getPriority() {
                return ConstShop.AP_FLOOR_DRAW;
            }
        });
        list.add(() -> new MapDrawer((OrthographicCamera) camera, map, ConstShop.SHELF_UNIT_SIZE / 128f, "shelves") {
            @Override
            public int getPriority() {
                return ConstShop.AP_SHELVING_DRAW;
            }
        });

        list.add(() -> new PlayerDrawer(camera));
        list.add(() -> new HotbarDrawer(camera));
        list.add(() -> new FlooringHazardsDrawer(camera));
    }

    protected void addColliders(NebulaShop nebulaImplemented) {
        ColliderList list = nebulaImplemented.colliders();

        list.add(CollectCollectibles::new);
        list.add(ShopperShopperCrash::new);
        list.add(ShopperShelfCrash::new);
        list.add(() -> new PrehensileCollisionManager() {
            @Override
            public int getPriority() {
                return ConstShop.CP_ON_FLOORING;
            }
        });
    }

    protected void addFermions(NebulaShop nebulaImplemented, MapReader reader) {
        FermionList list = nebulaImplemented.fermions();

        nebulaImplemented.player = list
                .addWithPointer(() -> new Player(nebulaImplemented.world(), new Vector2(2, 2)));

        Random rnd = new Random(System.currentTimeMillis());

        //###Begin add collectibles.
        for (int i = 0; i < 30; i++) {
            int type = rnd.nextInt(ConstShop.NUM_COLLECTIBLE_TYPES);
//            System.out.println(type+1);
            list.add(() -> new Collectible(nebulaImplemented, new Vector2(MathUtils.random(MAP_WIDTH), MathUtils.random(MAP_HEIGHT)), type));
        }
        //###End add collectibles.


        //###Begin add ice.
        list.add(() -> new IceRing(nebulaImplemented, new Vector2(4, 16)));
        //###End add ice.


        reader.readInShelving();

        //###Begin add obnoxious.
        EffectList effectList = nebulaImplemented.effects();
        for (int i = 1; i <= 5; i++) {
            int finalI = i;
            effectList.add(() -> new ObnoxiousRamsPlayer(
                    list.addWithPointer(() -> new Shopper(nebulaImplemented.world(), new Vector2(finalI * 4, finalI * 4)))
            ));
        }
        //###End add obnoxious.
    }

    @Override
    public void dispose() {
    }
}
