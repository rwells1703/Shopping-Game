package check.out.game.maingame.nonfermions;

import com.badlogic.gdx.utils.IntIntMap;

public class Cargo {
    public IntIntMap quantity=new IntIntMap();//Hmm, should this be more private?
    public void addOneOf(int type){
        quantity.put(type,quantity.get(type,0)+1);
    }
}
