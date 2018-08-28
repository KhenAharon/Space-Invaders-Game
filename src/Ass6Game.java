/**
 * class Ass6Game running the game.
 * @author Khen Aharon
 */
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import biuoop.DialogManager;
import biuoop.GUI;
/**
 * Ass6Game - running the game.
 */
public class Ass6Game {
    /**
     * main of the game.
     * @param args from command line.
     * @throws IOException throws exception if error occurs.
     */
    public static void main(String[] args) throws IOException {
        final int boardWidth = 800;
        final int boardHeight = 600;
        final double dt = 0.01666666666;
        final GUI gui = new GUI("Arkanoid", boardWidth, boardHeight);
        final AnimationRunner runner = new AnimationRunner(60, gui, dt);
        final Menu<Task<Void>> mainMenu = new MenuAnimation<Task<Void>>("Arkanoid",
                                                                        runner.getGui().getKeyboardSensor(),
                                                                        runner);
        String levelSetsPath = "";
        if (args.length == 0) {
            levelSetsPath = "level_sets.txt";
        } else {
            levelSetsPath = args[0];
        }
        final File file = new File("highscores.txt");
        final HighScoresTable table = new HighScoresTable(10);
        // create the file for the table
        try {
            if (file.exists()) {
                table.load(file);
            } else { // the file does not exist - save it
                table.save(file);
            }
        } catch (IOException e) {
            System.err.println("Error");
            System.exit(1);
        }
        InputStream is = null;
        Reader reader = null;
        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(levelSetsPath);
            reader = new InputStreamReader(is);
        } catch (Exception e) {
            System.err.println("error while reading.");
            System.exit(1);
        }
        LevelSets levelSet = new LevelSets();
        try {
            List<LevelSets> listLevelSet = levelSet.fromReader(reader);
            if (listLevelSet == null) {
                System.err.println("error while reading.");
                System.exit(1);
            }
            final Menu<Task<Void>> subMenu =
                    new MenuAnimation<Task<Void>>("Level Sets", runner.getGui().getKeyboardSensor(),
                                                  runner);
            for (final LevelSets l : listLevelSet) {
                subMenu.addSelection(l.getKeys(), l.getMessages(), new Task<Void>() {
                    public Void run() {
                        String filename = l.getPath();
                        List<LevelInformation> levels;
                        Reader readerLevel = null;
                        InputStream isLevel = null;
                        try {
                            isLevel = ClassLoader.getSystemClassLoader().
                                      getResourceAsStream(filename);
                            readerLevel = new InputStreamReader(isLevel);
                        } catch (Exception e) {
                            System.err.println("Couldn't read.");
                            System.exit(1);
                        }
                        LevelSpecificationReader levSpecReader = new LevelSpecificationReader();
                        levels = levSpecReader.fromReader((readerLevel));
                        GameFlow flow = new GameFlow(runner,
                                                     runner.getGui().getKeyboardSensor());
                        if (levels == null) {
                            System.err.println("no such file.");
                            System.exit(1);
                        } else {
                            try {
                                flow.runLevels(levels);
                            } catch (Exception e) {
                                System.err.println("levels exception.");
                                System.exit(1);
                            }
                        }
                        if (table.check(flow.getScore())) {
                            DialogManager dialog = gui.getDialogManager();
                            String name = dialog.
                                    showQuestionDialog("Name", "What is your name?", "");
                            table.add(new ScoreInfo(name, flow.getScore()));
                            try {
                                    table.save(file);
                            } catch (IOException e) {
                                System.err.println("Error saving table.");
                                System.exit(1);
                            }
                        }
                        return null;
                    }
                });
            }
            mainMenu.addSubMenu("s", "Start", subMenu);
            mainMenu.addSelection("h", "High Scores", new Task<Void>() {
                public Void run() {
                    // shows the table scores
                    Animation an = new KeyPressStoppableAnimation(
                                   runner.getGui().getKeyboardSensor(), "space",
                                   (new HighScoresAnimation(table)));
                    runner.run(an);
                    return null;
                }
            });
            mainMenu.addSelection("q", "Quit", new Task<Void>() {
                public Void run() {
                    System.exit(1);
                    return null;
                }
            });
            //The game loop animation
            while (true) {
                runner.run(mainMenu);
                // get selection from user.
                Task<Void> task = mainMenu.getStatus();
                Menu<Task<Void>> sub = mainMenu.getSubStatus();
                while (task == null) {
                    if (sub != null) {
                        runner.run(sub);
                        task = sub.getStatus();
                    }
                    sub.restart();
                }
                task.run();
                mainMenu.restart();
            }
        } catch (Exception e1) {
            System.err.println("last exception");
            System.exit(1);
        }
    }
}
