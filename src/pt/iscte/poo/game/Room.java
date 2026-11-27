package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import objects.*;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Room {

    private List<GameObject> objects;
    private String roomName;
    private Baba player;
    private int cols;
    private int rows;
    private boolean win = false;
    private boolean guiNeedsUpdate = false;
    private final Rules rules = new Rules();

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
        guiNeedsUpdate = true;
    }

    public void removeObject(GameObject obj) {
        objects.remove(obj);
        guiNeedsUpdate = true;
    }

    // Função para se ver é preciso atualizar o mapa. Assim não precisamos de estar sempre a atualizar a mapa
    public boolean needsGuiUpdate() {
        return guiNeedsUpdate;
    }

    public void clearGuiUpdate() {
        guiNeedsUpdate = false;
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public static Room readRoom(File f) {
        Room r = new Room();
        r.setName(f.getName());
        try {

            Scanner sc = new Scanner(f);

            String[] s = sc.nextLine().split(",");
            r.cols = Integer.parseInt(s[0].trim());
            r.rows = Integer.parseInt(s[1].trim());

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
                String line = sc.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                GameObject obj = RoomObjects.createFromLine(line);
                r.addObject(obj);
                if (obj instanceof Baba) {
                    r.player = (Baba) obj;
                }
            }
            r.updateRules();
            sc.close();
        } catch (FileNotFoundException e) {
            ImageGUI.getInstance().showMessage("Erro", "Erro ao ler o quarto: " + f.getName());
        }
        return r;
    }

    public Baba getPlayer() {
        return player;
    }

    public List<GameObject> getObject(Point2D position) {
        List<GameObject> get = new ArrayList<>();
        for (GameObject obj : objects) {
            if (obj.getPosition().equals(position) && !(obj instanceof Dirt)) {
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

    public Interactive getInteractiveObject(Point2D position) {
        for (GameObject obj : getObject(position)) {
            if (obj instanceof Interactive) {
                return (Interactive) obj;
            }
        }
        return null;
    }

    public boolean isInteractive(Point2D position) {
        return getInteractiveObject(position) != null;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean isWin() {
        if (player == null || !player.isAlive()) {
            return false;
        }
        return win;
    }

    public void killPlayer() {
        if (player != null) {
            player.kill();
            removeObject(player);
        }
    }

    public void triggerInteractions(Point2D position) {
        for (GameObject obj : getObject(position)) {
            if (obj instanceof Interactive) {
                ((Interactive) obj).onPlayerEnter(this);
            }
        }
    }

    public void handleHoleInteractions(List<Pushable> pushedObjs) {
        for (Pushable pushed : pushedObjs) {
            for (GameObject atPos : getObject(pushed.getPosition())) {
                if (atPos instanceof Hole) {
                    (pushed).onFallInHole((Hole) atPos, this);
                    break;
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

    // dá update usando a class rules e depois percorre a lista das rules
    public void updateRules() {
        rules.update(this);
        applyRuleToBoxes();
    }

    // A função apply fica no room para ser mais fácil atualizar as propriedades dos objetos
    private void applyRuleToBoxes() {
        for (GameObject obj : objects) {
            if (obj instanceof Box) {
                Box box = (Box) obj;
                box.setPassable(true);
                box.setPushable(false);
                box.setWin(false);
                if (rules.isActive(WordType.STOP)) {
                    box.setPassable(false);
                }
                if (rules.isActive(WordType.MOVE)) {
                    box.setPassable(false);
                    box.setPushable(true);
                }
                if (rules.isActive(WordType.WIN)) {
                    box.setWin(true);
                }
            }
        }
    }

    public boolean hasContradiction() {
        return rules.hasContradiction();
    }


    //funções para a bomba

    public void onTick() {
        for (GameObject obj : new ArrayList<>(objects)) {
            if (obj instanceof Bomb) {
                ((Bomb) obj).ticks(this);
            }
        }
    }
}