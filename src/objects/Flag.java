package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;

public class Flag extends GameObject implements Interactive, Pushable {

    private boolean win = true;

    public Flag(Point2D position) {
        super(position, "flag", 10);
        setWin(true);
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    @Override
    public boolean canBePushed(Room room){
        return true;
    }

    @Override
    public void onPushed(boolean direct) {
    }

    @Override
    public void onPlayerEnter(Room room) {
        if (win) {
            room.setWin(win);
        }
    }

    @Override
    public void onFallInHole(Hole hole, Room room) {
        room.removeObject(this);
    }
}
