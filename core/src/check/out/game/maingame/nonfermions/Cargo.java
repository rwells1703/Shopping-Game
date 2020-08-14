package check.out.game.maingame.nonfermions;

import com.badlogic.gdx.utils.IntIntMap;

public class Cargo {
    public IntIntMap quantity=new IntIntMap();//Hmm, should this be more private?
    public void addOneOf(int type){
        System.out.println(quantity);
        quantity.put(type,quantity.get(type,0)+1);
    }
    public void removeOneOf(int type){
        System.out.println(quantity);
        quantity.put(type, quantity.get(type, 1)-1);
    }
}
