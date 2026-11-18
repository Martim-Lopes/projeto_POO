package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Baba extends GameObject {

    private String direction;

    public Baba(Point2D position, String direction) {
        this.direction = direction;
        super(position, "baba-" + direction, 25);
    }

    @Override
    public String getName() {
        return "baba-" + direction;
    }

    public boolean move(Direction d, Room room) {
        direction = letterFor(d);
        Point2D next = getPosition().plus(d.asVector());
        if(room.canMove(next)){
            setPosition(next);
            return true;
        }
        return false;
    }

    private static String letterFor(Direction d) {
        switch (d) {
            case UP: return "u";
            case DOWN: return "d";
            case LEFT: return "l";
            default: return "r";
        }
    }
}
