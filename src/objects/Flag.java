package objects;

import pt.iscte.poo.utils.Point2D;

public class Flag extends GameObject{

    private boolean win = true;

    public Flag(Point2D position) {
        super(position, "flag", 10);
    }

    @Override
    public boolean isWin() {
        return win;
    }

    public void  setWin(boolean win){
        this.win = win;
    }
}
