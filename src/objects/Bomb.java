package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;

import java.util.List;

public class Bomb extends GameObject implements Pushable {

    private static final int INIT_TICKS = 10;
    private boolean lit = false;
    private int ticksToExplode = INIT_TICKS;

    public Bomb(Point2D position) {
        super(position, "bomb", 20);
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public boolean canBePushed(Room room) {
        return true;
    }


    //Bomb: é móvel e o seu rastilho acende quando o jogador a empurra diretamente. Explode ao
    //final de 10 ticks, removendo-se a si e a todos os objetos nas oito posições adjacentes (Wall
    //passa a CrackedWall). Enquanto o rastilho arde, pode continuar a ser empurrada.
    // Se for empurrada indiretamente por outros objetos, o rastilho não acende.

    public void ticks(Room r) {
        if (!lit) return;
        ticksToExplode--;
        switch (ticksToExplode) {
            case (9):
                setName("bomb1");
                break;
            case (7):
                setName("bomb2");
                break;
            case (5):
                setName("bomb3");
                break;
            case (3):
                setName("bomb4");
                break;
            case (1):
                setName("bomb5");
                break;
        }
        if (ticksToExplode <= 0) {
            explode(r);
        }
    }

    @Override
    public void onPushed(boolean direct) {
        if (direct && !lit) {
            lit = true;
            ticksToExplode = INIT_TICKS;
        }
    }

    private void explode(Room r) {
        List<Point2D> pontos = this.getPosition().getWideNeighbourhoodPoints();
        r.removeObject(this);
        for (Point2D ponto : pontos) {
            List<GameObject> objs = r.getObject(ponto);
            for (GameObject obj : r.getObject(ponto)) {
                if (obj instanceof Wall) {
                    r.removeObject((GameObject) obj);
                    r.addObject(new CrackedWall(ponto));
                } else if (obj instanceof Baba) {
                    r.killPlayer();
                } else {
                    r.removeObject((GameObject) obj);
                }
            }
        }
        r.updateRules();
    }
}
