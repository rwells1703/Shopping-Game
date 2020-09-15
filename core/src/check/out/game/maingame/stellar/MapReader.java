package check.out.game.maingame.stellar;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.effects.ai.EnemyMovementAI;
import check.out.game.maingame.fermions.Collectible;
import check.out.game.maingame.fermions.TerrainStatic;
import check.out.game.maingame.fermions.shoppers.Enemy;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import fernebon.core.base.effect.EffectList;
import fernebon.core.base.fermion.FermionList;

import java.util.Random;

import static check.out.game.maingame.ConstShop.SHELF_UNIT_SIZE;
import static check.out.game.maingame.ConstShop.TILE_WIDTH;

public class MapReader {
    private TiledMap map;
    private NebulaShop nebula;
    private FermionList fermionlist;

    public MapReader(NebulaShop nebula, TiledMap map) {
        this.nebula = nebula;
        this.map = map;
        this.fermionlist = nebula.fermions();
    }

    public void readInShelving() {
        boolean[][] hasShelf = getHasShelving(getShelvesLayer());

        readInVerticalShelving(hasShelf);
        readInHorizontalShelving(hasShelf);
//        checkReadIn(hasShelf);
    }

    public void readInObjects() {
        MapObjects shoppers = map.getLayers().get("shoppers").getObjects();
        addShoppers(shoppers);
        MapObjects collectibles = map.getLayers().get("collectibles").getObjects();
        addCollectibles(collectibles);
        MapObjects waypoints = map.getLayers().get("waypoints").getObjects();
        addWaypoints(waypoints);
    }

    public float getMapWidth() {
        return getShelvesLayer().getWidth() * SHELF_UNIT_SIZE;
    }

    public float getMapHeight() {
        return getShelvesLayer().getHeight() * SHELF_UNIT_SIZE;
    }

    private TiledMapTileLayer getShelvesLayer() {
        return (TiledMapTileLayer) map.getLayers().get("shelves");
    }

    private boolean[][] getHasShelving(TiledMapTileLayer shelves) {
        boolean[][] hasShelf = new boolean[shelves.getWidth()][shelves.getHeight()];
        for (int x = 0; x < shelves.getWidth(); x++) {
            for (int y = 0; y < shelves.getHeight(); y++) {
                hasShelf[x][y] = (shelves.getCell(x, y) != null);
            }
        }

        return hasShelf;
    }

    /**
     * Only reads in shelving that forms vertical blocks of length >=2 as vertical shelves.
     */
    private void readInVerticalShelving(boolean[][] hasShelf) {
        int height = 1;
        int bottom = 0;
        boolean makingBlock = false;
        final int WIDTH = hasShelf.length, HEIGHT = hasShelf[0].length;

        for (int left = 0; left < WIDTH; left++) {
            for (int y = 0; y < HEIGHT; y++) {

                if (makingBlock) {
                    if (hasShelf[left][y]) {
                        hasShelf[left][y] = false;
                        height++;
                    } else {
                        addShelf(left, bottom, 1, height);
                        makingBlock = false;
                    }
                } else {
                    if (hasShelf[left][y] && y != HEIGHT - 1 && hasShelf[left][y + 1]) {
                        bottom = y;
                        height = 2;
                        makingBlock = true;
                        hasShelf[left][y] = hasShelf[left][y + 1] = false;
                        y++;//Already checked.
                    }
                }
            }
            if (makingBlock) {
                addShelf(left, bottom, 1, height);
                makingBlock = false;
            }
        }
    }

    /**
     * Reads in remaining shelves as horizontal shelves.
     */
    private void readInHorizontalShelving(boolean[][] hasShelf) {
        int width = 1;
        int left = 0;
        boolean makingBlock = false;
        final int WIDTH = hasShelf.length, HEIGHT = hasShelf[0].length;

        for (int bottom = 0; bottom < HEIGHT; bottom++) {
            for (int x = 0; x < HEIGHT; x++) {
                if (makingBlock) {
                    if (hasShelf[x][bottom]) {
                        hasShelf[x][bottom] = false;
                        width++;
                    } else {
                        addShelf(left, bottom, width, 1);
                        makingBlock = false;
                    }
                } else {
                    if (hasShelf[x][bottom]) {
                        left = x;
                        width = 1;
                        makingBlock = true;
                        hasShelf[x][bottom] = false;
                    }
                }
            }
            if (makingBlock) {
                addShelf(left, bottom, width, 1);
                makingBlock = false;
            }
        }
    }

    /**
     * Reports on how many shelves weren't read in.
     */
    private void checkReadIn(boolean[][] hasShelf) {
        int misreads = 0;
        for (int i = 0; i < hasShelf.length; i++) {
            for (int j = 0; j < hasShelf[i].length; j++) {
                if (hasShelf[i][j]) {
                    System.out.println(i + "," + j + " wasn't read in!");
                    misreads++;
                }
            }
        }
        System.out.println("Done reading map with " + misreads + " shelf block(s) not read in.");
    }

    private void addShelf(int x, int y, int width, int height) {
        nebula.fermions().add(new TerrainStatic(), it -> it.init(nebula.world(), x, y, width, height));
    }

    public void addWaypoints(MapObjects waypointObjects) {
        nebula.waypoints = new Vector2[waypointObjects.getCount()];

        for (int i = 0; i < waypointObjects.getCount(); i++) {
            MapProperties properties = waypointObjects.get(i).getProperties();
            nebula.waypoints[i] = new Vector2((float) properties.get("x") / (int) map.getProperties().get("tilewidth"), (float) properties.get("y") / (int) map.getProperties().get("tileheight"));
        }
    }

    private void addShoppers(MapObjects shoppers) {
        EffectList effectList = nebula.effects();

        for (MapObject shopper : shoppers) {
            MapProperties properties = shopper.getProperties();
            Vector2 position = new Vector2(Float.parseFloat(properties.get("x").toString()), Float.parseFloat(properties.get("y").toString()));
            effectList.add(new EnemyMovementAI(
                    fermionlist.add(new Enemy(), it -> it.init(nebula, pixelToCoord(position)))
            ), null);
        }
    }

    private void addCollectibles(MapObjects collectibles) {
        Random rnd = new Random(System.currentTimeMillis());
        for (MapObject collectible : collectibles) {
            int type = rnd.nextInt(ConstShop.NUM_COLLECTIBLE_TYPES);
            MapProperties properties = collectible.getProperties();
            Vector2 position = new Vector2(Float.parseFloat(properties.get("x").toString()), Float.parseFloat(properties.get("y").toString()));
            fermionlist.add(new Collectible(pixelToCoord(position), type), it -> it.init(nebula));
        }
    }

    private Vector2 pixelToCoord(Vector2 pixelCoords) {
        return pixelCoords.scl(1 / (float) TILE_WIDTH);
    }
}
