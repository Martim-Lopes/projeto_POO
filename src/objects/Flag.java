package objects;

import pt.iscte.poo.utils.Point2D;

public class Flag extends GameObject{

    public boolean isWin;

    public Flag(Point2D position) {
        super(position, "flag", 10);
    }

    @Override
    public boolean isWin(){
        return isWin;
    }

    public void setWin(boolean isWin){
        this.isWin = isWin;
    }
}
