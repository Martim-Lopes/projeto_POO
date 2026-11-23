package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import objects.*;
import pt.iscte.poo.utils.Point2D;

public class Room {

    private List<GameObject> objects;
    private String roomName;
    private Baba player;
    private int cols;
    private int rows;

    public Room() {
        objects = new ArrayList<>();
    }

    private void setName(String name) {
        roomName = name;
    }

    public String getName() {
        return roomName;
    }

    public void addObject(GameObject obj) {
        objects.add(obj);
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public static Room readRoom(File f) throws FileNotFoundException {
        Room r = new Room();
        r.setName(f.getName());
        Scanner sc = new Scanner(f);

        String[] s = sc.nextLine().split(",");
        r.cols = Integer.parseInt(s[0]);
        r.rows = Integer.parseInt(s[1]);

        for (int i = 0; i < r.cols; i++) {
            for (int j = 0; j < r.rows; j++) {
                GameObject dirt = new Dirt(new Point2D(i, j));
                r.addObject(dirt);
            }
        }
        int j = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                break;
            }
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                MapElements elements = MapElements.fromChar(c);
                if (elements != null) {
                    r.addObject(elements.create(new Point2D(i, j)));
                }
            }
            j++;
        }
        while (sc.hasNextLine()) {
            String[] line = sc.nextLine().split(",");
            String type = line[0].trim();
            Point2D pos = new Point2D(Integer.parseInt(line[1].trim()), Integer.parseInt(line[2].trim()));
            // Caso a parte para o baba. Existe a opção de usarmos o instanceof, mas acho que e demasiado avançado
            if (type.equals("baba")) {
                Baba baba = new Baba(pos, line[3].trim());
                r.player = baba;
                r.addObject(baba);
            } else {
                RoomObjects objs = RoomObjects.fromString(type);
                if (objs != null) {
                    r.addObject(objs.create(pos));
                }
            }
        }
        r.updateRules();
        sc.close();
        return r;
    }

    public Baba getPlayer() {
        return player;
    }

    public List<GameObject> getObject(Point2D position) {
        List<GameObject> get = new ArrayList<>();
        for (GameObject obj : objects) {
            if (obj.getPosition().equals(position)) {
                get.add(obj);
            }
        }
        return get;
    }

    public boolean canMove(Point2D position) {
        if (position.getX() < 0 || position.getX() >= cols || position.getY() >= rows || position.getY() < 0) {
            return false;
        }
        for (GameObject obj : getObject(position)) {
            if (!obj.isPassable()) {
                return false;
            }
        }
        return true;
    }

    public Pushable getPushableObject(Point2D position) {
        for (GameObject obj : getObject(position)) {
            if (obj instanceof Pushable) {
                if (((Pushable) obj).canBePushed(this)) {
                    return (Pushable) obj;
                }
            }
        }
        return null;
    }

    public boolean canPush(Point2D position) {
        return getPushableObject(position) != null;
    }

    public GameObject getLethalObjectAt(Point2D position) {
        for (GameObject obj : getObject(position)) {
            if (obj.isLethal()) {
                return obj;
            }
        }
        return null;
    }

    public boolean isLethal(Point2D position) {
        return getLethalObjectAt(position) != null;
    }

    public void killPlayer() {
        if (player != null) {
            player.kill();
            objects.remove(player);
        }
    }

    public boolean isWin() {
        if (player == null || !player.isAlive()) {
            return false;
        }
        Point2D playerPos = player.getPosition();
        for (GameObject obj : objects) {
            if (obj.getPosition().equals(playerPos) && obj.isWin()) {
                return true;
            }
        }
        return false;
    }

    public void tryPutBox(List<Pushable> pushedObjs) {
        for (Pushable obj : pushedObjs) {
            if (obj instanceof Box) {
                Point2D pos = obj.getPosition();
                GameObject hole = getLethalObjectAt(pos);
                if (hole != null) {
                    objects.remove(hole);
                    objects.remove(obj);
                    addObject(new BoxInHole(pos));
                }
            }
        }
    }


    // Parte das palavras

    public WordObject getWordAt(Point2D position) {
        for (GameObject obj : getObject(position)) {
            if (obj instanceof WordObject) {
                return (WordObject) obj;
            }
        }
        return null;
    }

    public void updateRules() {
        for (GameObject obj : objects) {
            if (obj instanceof Box) {
                Box box = (Box) obj;
                box.setPassable(true);
                box.setPushable(false);
                box.setWin(false);
            }
        }

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                checkRuleAt(new Point2D(x, y), 1, 0);
                checkRuleAt(new Point2D(x, y), 0, 1);
            }
        }
    }

    private void checkRuleAt(Point2D start, int dx, int dy) {
        WordObject w1 = getWordAt(start);
        WordObject w2 = getWordAt(new Point2D(start.getX() + dx, start.getY() + dy));
        WordObject w3 = getWordAt(new Point2D(start.getX() + 2 * dx, start.getY() + 2 * dy));

        if (w1 != null && w2 != null && w3 != null && w1.getWordType() == WordType.BOX && w2.getWordType() == WordType.IS && w3.getWordType().isProperty()) {
            applyRuleToBoxes(w3.getWordType());
        }
    }

    private void applyRuleToBoxes(WordType property) {
        for (GameObject obj : objects) {
            if (obj instanceof Box) {
                Box box = (Box) obj;
                switch (property) {
                    case MOVE:
                        box.setPushable(true);
                        box.setPassable(false);
                        break;
                    case STOP:
                        box.setPassable(false);
                        break;
                    case WIN:
                        box.setWin(true);

                        break;
                    default:
                        break;
                }
            }
        }
    }
}