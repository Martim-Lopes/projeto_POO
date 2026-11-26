package pt.iscte.poo.game;

import objects.GameObject;
import objects.WordObject;
import objects.WordType;
import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;
import java.util.List;

// Classe para leitura das palavras. Evita estar a encher o room de código

public class Rules {

    private List<WordType> list;

    public Rules() {
        this.list = new ArrayList<>();
    }

    public void update(Room room) {
        list.clear();
        for (GameObject obj : room.getObjects()) {
            //procuramos o IS e vemos em cima e baixo e á esquerda e á direita
            if (obj instanceof WordObject && ((WordObject) obj).getWordType() == WordType.IS) {
                Point2D p = obj.getPosition();
                // vertificar na horinzontal. Atrás tem que ter box e á frente uma propriedade
                checkSentence(room, new Point2D(p.getX() - 1, p.getY()), new Point2D(p.getX() + 1, p.getY()));
                // verificar na vertical.
                checkSentence(room, new Point2D(p.getX(), p.getY() - 1), new Point2D(p.getX(), p.getY() + 1));
            }
        }
    }

    private void checkSentence(Room room, Point2D before, Point2D after) {
        WordObject first = room.getWordAt(before);
        WordObject last = room.getWordAt(after);
        if (first != null && last != null && first.getWordType() == WordType.BOX && last.getWordType().isProperty() && !list.contains(last.getWordType())) {
            list.add(last.getWordType());
        }
    }

    // verificar se uma X propriedade está ativa
    public boolean isActive(WordType type) {
        return list.contains(type);
    }

    // ver se tem alguma contradição. Por exemplo a caixa ser move e stop
    // se houver mais opções de contradição é só adicionar aqui
    public boolean hasContradiction() {
        return (isActive(WordType.STOP) && isActive(WordType.MOVE))
                || (isActive(WordType.MOVE) && isActive(WordType.WIN));
    }
}
