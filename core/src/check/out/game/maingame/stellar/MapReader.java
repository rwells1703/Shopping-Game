package check.out.game.maingame.stellar;

import check.out.game.maingame.fermions.TerrainStatic;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import static check.out.game.maingame.ConstShop.SHELF_UNIT_SIZE;

public class MapReader {
    private TiledMap map;
    private NebulaShop nebula;
    public MapReader(NebulaShop nebula, TiledMap map){
        this.nebula = nebula;
        this.map = map;
    }

    public void readInShelving(){
        TiledMapTileLayer shelves = getShelvesLayer();

        for (int x = 0; x < shelves.getWidth(); x++) {
            for (int y = 0; y < shelves.getHeight(); y++) {
                Cell cell = shelves.getCell(x,y);
                if (cell != null) {
                    TerrainStatic terrain = new TerrainStatic(nebula.world(), x, y, 1, 1);
                    nebula.fermions().add(() -> terrain);
                }
            }
        }

        //ArrayList<int[]> previouslyJoined = new ArrayList<>();

        //readInVerticalShelving(shelves, previouslyJoined);
        //readInVerticalShelving(shelves);
    }

    public float getMapWidth(){
        return getShelvesLayer().getWidth()*SHELF_UNIT_SIZE;
    }
    public float getMapHeight(){
        return getShelvesLayer().getHeight()*SHELF_UNIT_SIZE;
    }

    private TiledMapTileLayer getShelvesLayer(){
        return (TiledMapTileLayer) map.getLayers().get("shelves");
    }

    /*
    public Cell getCell(TiledMapTileLayer shelves, int x, int y) {
        int horizontal = shelves.getWidth()-1 - x;
        int vertical = shelves.getHeight()-1 - y;

        return getShelvesLayer().getCell(horizontal, vertical);
    }

    private void readInVerticalShelving(TiledMapTileLayer shelves, ArrayList<int[]> previouslyJoined){
        //System.out.println(getCell(shelves,7,15));
        int width = 0;
        Cell previousCell = null;

        for (int x = 0; x < shelves.getWidth(); x++) {
            for(int y = 0; y < shelves.getHeight(); y++) {
                Cell cell = shelves.getCell(x, y);

                if ((cell != previousCell && previousCell != null) || (y == shelves.getHeight() && width > 0)) {
                    // If we reach the end of a continuous row, or reach the edge of the map and we have started a group
                    // draw the wall
                    addShelf(x, y, width, 1);
                    width = 0;
                } else {
                    width++;
                    // Remember these coordinates of this tile
                    // this prevents a tile from being created again in the horizontal pass
                    //previouslyJoined.add(new int[]{x,y});
                }

                previousCell = cell;
            }
        }
    }

    private void readInHorizontalShelvingOld(TiledMapTileLayer shelves){
        int previousCell=-1;
        int width=1;
        int left=0;
        boolean makingBlock=false;
        TiledMapTileLayer.Cell cell;

        System.out.println(shelves.getHeight());
        for(int bottom=0; bottom<shelves.getHeight(); bottom++){
            for (int x = 0; x < shelves.getWidth(); x++) {
                cell=shelves.getCell(x,bottom);
                if(cell==null){
                    if(makingBlock) {
                        addShelf(left,bottom,width,1);
                        makingBlock=false;
                    }
                    previousCell=-1;
                }
                else if(cell.getTile().getId()!=previousCell){
                    if(makingBlock) addShelf(left,bottom,width,1);
                    previousCell=cell.getTile().getId();
                    if(previousCell%2==0) {
                        left = x;
                        width = 1;
                        makingBlock=true;
                    }
                    else makingBlock=false;
                }
                else {
                    width++;
                }
            }
            if(makingBlock){
                addShelf(left,bottom,width,1);
                makingBlock=false;
            }
        }
    }

    private void readInVerticalShelvingOld(TiledMapTileLayer shelves){
        int previousCell=-1;
        int height=1;
        int bottom=0;
        boolean makingBlock=false;
        TiledMapTileLayer.Cell cell;

        for(int left=0; left<shelves.getWidth(); left++){
            for (int y = 0; y < shelves.getHeight(); y++) {
                cell=shelves.getCell(left,y);
                if(cell==null){
                    if(makingBlock) {
                        addShelf(left,bottom,1,height);
                        makingBlock=false;
                    }
                    previousCell=-1;
                }
                else if(cell.getTile().getId()!=previousCell){
                    if(makingBlock) addShelf(left,bottom,1,height);
                    previousCell=cell.getTile().getId();
                    if(previousCell%2==1) {
                        bottom = y;
                        height = 1;
                        makingBlock=true;
                    }
                    else makingBlock=false;
                }
                else {
                    height++;
                }
            }
            if(makingBlock){
                addShelf(left,bottom,1,height);
                makingBlock=false;
            }
        }
    }

    private void addShelf(int x, int y, int width, int height){
        nebula.fermions().add(() -> new TerrainStatic(nebula.world(), x, y, width, height));
    }*/
}
