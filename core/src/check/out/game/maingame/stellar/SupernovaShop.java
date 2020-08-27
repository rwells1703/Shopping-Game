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

        if (ConstShop.DEBUG_DRAW_BODIES) {
            list.add(() -> new DebugRenderer(camera, nebulaImplemented.world()) {
                @Override
                public int getPriority() {
                    return ConstShop.AP_DEBUG_DRAW;
                }
            });
        }

        if (ConstShop.DEBUG_DRAW_ACTIVE_RAYS) {
            list.add(() -> new RayCastDrawer(camera));
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
                .addWithPointer(() -> new Player(nebulaImplemented, new Vector2(2, 2)));

        reader.readInShelving();
        reader.readInObjects();
    }

    @Override
    public void dispose() {
    }
}