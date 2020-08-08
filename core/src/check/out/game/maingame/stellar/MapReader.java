package check.out.game.maingame.stellar;

import check.out.game.maingame.fermions.TerrainStatic;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import static check.out.game.maingame.ConstShop.SHELF_UNIT_SIZE;

public class MapReader {
    private TiledMap map;
    private NebulaShop nebula;
    public MapReader(NebulaShop nebula, TiledMap map){
        this.nebula = nebula;
        this.map = map;
    }

    public void readInShelving(){
        TiledMapTileLayer shelves= getShelvesLayer();
        readInHorizontalShelving(shelves);
        readInVerticalShelving(shelves);
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

    private void readInHorizontalShelving(TiledMapTileLayer shelves){
        int previousCell=-1;
        int width=1;
        int left=0;
        boolean makingBlock=false;
        TiledMapTileLayer.Cell cell;

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

    private void readInVerticalShelving(TiledMapTileLayer shelves){
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

    private void addShelf(int left, int bottom, int width, int height){
        nebula.fermions().add(() -> new TerrainStatic(nebula.world(),left,bottom,width,height));
    }
}
