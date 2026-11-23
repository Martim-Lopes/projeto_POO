package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;

public class Box extends GameObject implements Pushable, Interactive {

    private boolean pushed = false;
    private boolean passable = false;
    private boolean win = false;

    public Box(Point2D position) {
        super(position, "box", 10);
    }

    @Override
    public boolean isPassable() {
        return passable;
    }

    @Override
    public boolean canBePushed(Room room) {
        return pushed;
    }

    public void setPushable(boolean pushed) {
        this.pushed = pushed;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean isWin() {
        return win;
    }

    @Override
    public void onPushed(boolean direct) {

    }

    @Override
    public void onPlayerEnter(Room room) {}
}
