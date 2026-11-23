package objects;

import pt.iscte.poo.game.Room;

public interface Pushable {

    boolean canBePushed(Room room);
    void onPushed(boolean direct);

}
