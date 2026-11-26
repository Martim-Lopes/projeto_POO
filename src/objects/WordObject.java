package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;

public abstract class WordObject extends GameObject implements Pushable{

    private WordType wordType;

    public WordObject(Point2D position, String imageName, WordType wordType) {
        super(position, imageName, 10);
        this.wordType = wordType;
    }

    public WordType getWordType() {
        return wordType;
    }



    @Override
    public boolean isPassable() {
        return false;
    }
    @Override
    public boolean canBePushed(Room room){
        return true;
    }


    //função sugeria por AI para facilitar a implementação da bomba
    @Override
    public void onPushed(boolean direct){}


}
