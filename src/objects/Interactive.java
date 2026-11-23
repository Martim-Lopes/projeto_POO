package objects;

import pt.iscte.poo.game.Room;

public interface Interactive {
    void onPlayerEnter(Room room);
    boolean isLethal();
}