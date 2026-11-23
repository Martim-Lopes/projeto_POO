package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;

public abstract class WordObject extends GameObject implements Pushable{

    public WordObject(Point2D position, String imageName) {
        super(position, imageName, 10);
    }

    @Override
    public boolean isPassable() {
        return true;
    }

    @Override
    public boolean canBePushed(Room room){
        return true;
    }

    @Override
    public void onPushed(boolean direct){}
}
