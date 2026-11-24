package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;

public class Flag extends GameObject implements Interactive{

    private boolean win = true;

    public Flag(Point2D position) {
        super(position, "flag", 10);
    }

    public void  setWin(boolean win){
        this.win = win;
    }

    @Override
    public void onPlayerEnter(Room room) {
        room.setWin(win);
    }
}
