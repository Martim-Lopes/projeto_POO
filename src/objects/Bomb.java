package objects;

import pt.iscte.poo.utils.Point2D;

public class Bomb extends GameObject {
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
        return 0;
    }

    public boolean isLit() {
        return true;
    }
}
