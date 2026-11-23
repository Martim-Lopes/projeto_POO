package objects;

import pt.iscte.poo.utils.Point2D;

import javax.swing.text.Position;

public class WordStop extends WordObject {

    public WordStop(Point2D position) {
        super(position, "word-stop");
    }

    @Override
    public boolean isPassable() {
        return false;
    }
}
