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


    //Bomb: é móvel e o seu rastilho acende quando o jogador a empurra diretamente. Explode ao
    //final de 10 ticks, removendo-se a si e a todos os objetos nas oito posições adjacentes (Wall
    //passa a CrackedWall). Enquanto o rastilho arde, pode continuar a ser empurrada.
    // Se for empurrada indiretamente por outros objetos, o rastilho não acende.

    public void ticks(Room r) {
        if(!lit) return;
        ticksToExplode--;
        if(ticksToExplode <= 0) explode(r);
    }

    @Override
    public boolean canBePushed(Room room) {
        return true;
    }

    @Override
    public void onPushed(boolean direct) {
        if(direct){
            lit = true;
            ticksToExplode = INIT_TICKS;
        }
    }

    private void explode(Room r){
        List<Point2D> pontos = this.getPosition().getWideNeighbourhoodPoints();
        for(Point2D ponto : pontos){
            List<GameObject> objs = r.getObject(ponto);
            for(GameObject obj : r.getObject(ponto)){
                if(obj instanceof Wall){
                    r.removeObject((GameObject) obj);
                    r.addObject(new CrackedWall(ponto));
                }
                else if(obj instanceof Baba){
                    r.killPlayer();
                }else{
                    r.removeObject((GameObject) obj);
                }
            }
        }
        this.setName("bomb10");
    }
}
