package pt.iscte.poo.game;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;

import objects.Baba;
import objects.GameObject;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;

public class GameEngine implements Observer {

    private static GameEngine INSTANCE = new GameEngine();
    private static final String ROOMS_DIRECTORY = "rooms";

    private Room currentRoom;
    private int lastTickProcessed = 0;
    private int level = 0;
    private int totalMoves = 0;
    private boolean gameWon = false;

    private GameEngine() {
        try {
            loadLevel(this.level);


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameEngine getInstance() {
        return INSTANCE;
    }

    public void loadLevel(int level) throws FileNotFoundException {
        String directory = ROOMS_DIRECTORY + "/room" + level + ".txt";
        currentRoom = Room.readRoom(new File(directory));
        totalMoves = 0;
        updateStatusMessage();
        updateGUI();
    }

    @Override
    public void update(Observed source) {

        if (ImageGUI.getInstance().wasKeyPressed()) {
            int k = ImageGUI.getInstance().keyPressed();

            if (k == KeyEvent.VK_R) {
                try {
                    if (gameWon) {
                        level = 0;
                        gameWon = false;
                    }
                    loadLevel(this.level);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_UP || k == KeyEvent.VK_DOWN) {
                Direction d = Direction.directionFor(k);
                if (getPlayer().move(d, currentRoom)) {
                    totalMoves++;
                    if (!getPlayer().isAlive()) {
                        updateGUI();
                        ImageGUI.getInstance().setStatusMessage("Morres-te! Prima R para reiniciar");
                    } else {
                        updateStatusMessage();
                        handleVictory();
                    }
                }
            }
        }
        int t = ImageGUI.getInstance().getTicks();
        while (lastTickProcessed < t) {
            processTick();
        }
        ImageGUI.getInstance().update();
    }

    private void processTick() {
        lastTickProcessed++;
    }

    public void updateGUI() {
        if (currentRoom != null) {
            ImageGUI.getInstance().clearImages();
            ImageGUI.getInstance().addImages(currentRoom.getObjects());
        }
    }

    private void updateStatusMessage() {
        ImageGUI.getInstance().setStatusMessage(
                "Level " + level + " | Total moves: " + totalMoves + " | R: reiniciar");
    }

    public Baba getPlayer() {
        return currentRoom.getPlayer();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    private void handleVictory() {
        if (currentRoom.isWin()) {
            if (level < 2) {
                level++;
                try {
                    loadLevel(level);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                gameWon = true;
                ImageGUI.getInstance().setStatusMessage("Parabens chegaste ao final do jogo | Prima R para reiniciar");
            }
        }
    }

}
