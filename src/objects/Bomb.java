package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;

public class Bomb extends GameObject implements Pushable {

    private final int INIT_TICKS = 10;
    private boolean lit = false;
    private int ticksToExplode = INIT_TICKS;



    public Bomb(Point2D position) {
        super(position, "bomb", 10);
    }

    @Override
    public boolean isPassable() {
        return false;
    }


    //Bomb: é móvel e o seu rastilho acende quando o jogador a empurra diretamente. Explode ao
    //final de 10 ticks, removendo-se a si e a todos os objetos nas oito posições adjacentes (Wall
    //passa a CrackedWall). Enquanto o rastilho arde, pode continuar a ser empurrada.
    // Se for empurrada indiretamente por outros objetos, o rastilho não acende.


    //ticks para explodir
    public int toExplode() {
        return INIT_TICKS;
    }

    public void ticks(Room r) {
        if(!lit) return;

        ticksToExplode--;
        if(ticksToExplode <= 0) explode(r);
    }

    //explodir as 8posicoes adjacentes
    private void explode(Room r) {

    }


    public boolean isLit() {
        return lit;
    }

    //verificar se foi puxado e alterar lit(lit = true)
    public void wasPushed(GameObject pusher){


    }

    @Override
    public boolean canBePushed(Room room) {
        return true;
    }

    @Override
    public void onPushed(boolean direct) {
        if(!direct){
            lit = true;
            ticksToExplode = INIT_TICKS;
        }
    }
}
