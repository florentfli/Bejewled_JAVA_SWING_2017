import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

public class View extends JFrame {

    public static Random random = new Random();

    private Model model;

    private JPanel pMain, pProgressBar, pGame, pInfo;
    private JProgressBar progressBar;
    private JLabel level, essai, score, levelNb, essaiNb, scoreNb, space, raison;
    private JToggleButton[][] buttonGrid;

    private JMenuItem restart, scoreboard, hint, pause;
    private JMenu menu;
    private JMenuBar mb;

    public View(Model model) {
        this.model = model;

        this.model.setTimer(new Timer(3000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.setProgressBar(model.getProgressBar() - model.getNiveau() % 100);
                checkProgress();
            }
        }));

        initWidget();
        addWidget();
        pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setTitle("PROJET IHM : BEJEWELED   //   FLIEDNER Florent");
        this.setVisible(true);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("./img.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void initWidget() {
        mb = new JMenuBar();
        menu = new JMenu("MENU");
        restart = new JMenuItem("Restart");
        scoreboard = new JMenuItem("Scoreboard");
        hint = new JMenuItem("Hint...");
        pause = new JMenuItem("Pause");

        pMain = new JPanel(new GridLayout(3, 1, 10, 0));
        pInfo = new JPanel(new GridLayout(6, 1, 20, 20));
        pInfo.setLayout(new BoxLayout(pInfo, BoxLayout.X_AXIS));
        pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));

        pGame = new JPanel(new GridLayout(model.getLARGEUR(), model.getLONGEUR()));

        raison = new JLabel();

        pProgressBar = new JPanel();
        progressBar = new JProgressBar(0, 100);
        progressBar.setPreferredSize(new Dimension(600, 30));
        progressBar.setValue(model.getProgressBar());
        progressBar.setStringPainted(true);
        level = new JLabel("Level : ");
        levelNb = new JLabel("" + model.getNiveau() + "    ");
        score = new JLabel("Score : ");
        scoreNb = new JLabel("0    ");
        space = new JLabel(" ");
        scoreNb.setIconTextGap(1000);
        essai = new JLabel("Essaie : ");
        essaiNb = new JLabel("" + model.getEssai());
        this.displayGrid();
    }

    public void addWidget() {
        pInfo.add(level);
        pInfo.add(levelNb);
        pInfo.add(score);
        pInfo.add(scoreNb);
        pInfo.add(essai);
        pInfo.add(essaiNb);

        pProgressBar.add(progressBar);

        pMain.add(pInfo);
        pMain.add(pGame);
        pMain.add(pProgressBar);
        createMenu();
        setContentPane(pMain);
    }

    public void createMenu() {
        menu.add(restart);
        menu.add(scoreboard);
        menu.add(pause);
        menu.add(hint);
        mb.add(menu);
        this.setJMenuBar(mb);
    }

    public void setControlButonMenu(ControlMenu cm) {
        restart.addActionListener(cm);
        scoreboard.addActionListener(cm);
        hint.addActionListener(cm);
        pause.addActionListener(cm);
    }

    public void displayGrid() {
        pGame.removeAll();
        pGame.setLayout(new GridLayout(model.getLARGEUR(), model.getLONGEUR()));
        buttonGrid = new JToggleButton[model.getLARGEUR()][model.getLONGEUR()];
        for (int i = 0; i < model.getLARGEUR(); i++) {
            for (int j = 0; j < model.getLONGEUR(); j++) {
                buttonGrid[i][j] = new JToggleButton(model.getTabImg()[model.getGrid()[i][j]]);
                buttonGrid[i][j].setPreferredSize(new Dimension(75, 75));
                buttonGrid[i][j].setDisabledIcon(model.getTabImg()[model.getGrid()[i][j]]);
                buttonGrid[i][j].setBackground(Color.white);
                pGame.add(buttonGrid[i][j]);
            }
        }
        this.pack();
    }

    public void setControlButonGame(JToggleButton button, ActionListener cb) {
        button.addActionListener(cb);
    }

    public void printGrid() {
        System.out.println();
        for (int i = 0; i < model.getLARGEUR(); i++) {
            for (int j = 0; j < model.getLONGEUR(); j++) {
                System.out.print((model.getGrid()[i][j]) + " ");
            }
            System.out.println("");
        }
    }

    public void printGridView() {
        System.out.println();
        for (int i = 0; i < model.getLARGEUR(); i++) {
            for (int j = 0; j < model.getLONGEUR(); j++) {
                System.out.print(img((model.getTabImg()[model.getGrid()[i][j]])) + " ");
            }
            System.out.println("");
        }
    }

    public void checkGrid() {
        // i = row
        // j = col
        //Parcour du tableau
        for (int i = model.getLARGEUR() - 1; i >= 0; i--) {
            for (int j = model.getLONGEUR() - 1; j >= 0; j--) {
                for (int k = 4; k >= 2; k--) {
                    //parcourir chaque jewl de la suite
                    if (i >= k) {
                        int count = 0;
                        for (int l = 1; l <= k; l++) {
                            if (model.getGrid()[i][j] == model.getGrid()[i - l][j]) {
                                count++;
                                if (count == k) {
                                    System.out.println(k);
                                    this.updateScore(k);
                                    this.downButton(i, j, k, false);
                                    this.newRandButon(i, j, k, false);
                                    this.updateGrid();
                                }
                            }
                        }
                    }
                    if (j >= k) {
                        int count = 0;
                        for (int l = 1; l <= k; l++) {
                            if (model.getGrid()[i][j] == model.getGrid()[i][j - l]) {
                                count++;
                                if (count == k) {
                                    this.updateScore(k);
                                    this.downButton(i, j, k, true);
                                    this.newRandButon(i, j, k, true);
                                    this.updateGrid();
                                    try {
                                        Thread.sleep(200);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public boolean checkIsGridEmpty() {
        // i = row
        // j = col
        //Parcour du tableau
        for (int i = model.getLARGEUR() - 1; i >= 0; i--) {
            //i = 7 - i;
            for (int j = model.getLONGEUR() - 1; j >= 0; j--) {
                //j = 7 - j;
                //parcourir toute les suite de 3 a 5 jewl
                for (int k = 4; k >= 2; k--) {
                    //parcourir chaque jewl de la suite
                    if (i >= k) {
                        int count = 0;
                        for (int l = 1; l <= k; l++) {
                            if (model.getGrid()[i][j] == model.getGrid()[i - l][j]) {
                                count++;
                                if (count == k) {
                                    return false;
                                }
                            }
                        }
                    }
                    if (j >= k) {
                        int count = 0;
                        for (int l = 1; l <= k; l++) {
                            if (model.getGrid()[i][j] == model.getGrid()[i][j - l]) {
                                count++;
                                if (count == k) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public int[] checkGridHint(int[][] grid) {
        int[] tab = new int[3];
        for (int i = grid.length - 1; i >= 0; i--) {
            //i = 7 - i;
            for (int j = grid.length - 1; j >= 0; j--) {
                //j = 7 - j;
                //parcourir toute les suite de 3 a 5 jewl
                for (int k = 4; k >= 2; k--) {
                    //parcourir chaque jewl de la suite
                    if (i >= k) {
                        int count = 0;
                        for (int l = 1; l <= k; l++) {
                            if (grid[i][j] == grid[i - l][j]) {
                                count++;
                                if (count == k) {
                                    tab[0] = i;
                                    tab[1] = j;
                                    tab[2] = 2;
                                    return tab;
                                }
                            }
                        }
                    }
                    if (j >= k) {
                        int count = 0;
                        for (int l = 1; l <= k; l++) {
                            if (grid[i][j] == grid[i][j - l]) {
                                count++;
                                if (count == k) {
                                    tab[0] = i;
                                    tab[1] = j;
                                    tab[2] = 1;
                                    return tab;
                                }
                            }
                        }
                    }
                }
            }
        }
        tab[0] = 11;
        tab[1] = 11;
        tab[2] = 11;
        return tab;
    }

    public int hint(boolean printOnGrid) {
        for (int i = 0; i < model.getLONGEUR() - 2; i++) {
            for (int j = 0; j < model.getLONGEUR() - 2; j++) {
                int[][] copyModel = copyGrid(model.getGrid());

                int temp = copyModel[i][j];
                copyModel[i][j] = copyModel[i + 1][j];
                copyModel[i + 1][j] = temp;

                int[] tab = this.checkGridHint(copyModel);
                int x = tab[0];
                int y = tab[1];
                int ori = tab[2];

                if (x != 11 && y != 11) {
                    return printHint(x, y, ori, printOnGrid);
                }
            }
        }
        for (int i = 0; i < model.getLONGEUR() - 1; i++) {
            for (int j = 0; j < model.getLONGEUR() - 1; j++) {
                // copyModel = new int[model.getLONGEUR()][model.getLARGEUR()];
                int[][] copyModel = copyGrid(model.getGrid());
                int temp = copyModel[i][j];
                copyModel[i][j] = copyModel[i][j + 1];
                copyModel[i][j + 1] = temp;

                int[] tab = this.checkGridHint(copyModel);
                int x = tab[0];
                int y = tab[1];
                int ori = tab[2];

                if (x != 11 && y != 11) {
                    return printHint(x, y, ori, printOnGrid);
                }
            }
        }
        return 0;
    }

    private int printHint(int x, int y, int ori, boolean printOnGrid) {
        if (ori == 2) {
            if (printOnGrid) {
                model.setEssai(model.getEssai() - 1);
                if (model.getEssai() >= 0) this.getEssaiNb().setText(("" + model.getEssai()));
                this.buttonGrid[x][y].setBackground(Color.yellow);
                this.buttonGrid[x - 2][y].setBackground(Color.yellow);
                this.buttonGrid[x - 1][y].setBackground(Color.yellow);
            }
            return 1;
        }
        if (ori == 1) {
            if (printOnGrid) {
                model.setEssai(model.getEssai() - 1);
                if (model.getEssai() >= 0) this.getEssaiNb().setText(("" + model.getEssai()));
                this.buttonGrid[x][y].setBackground(Color.yellow);
                this.buttonGrid[x][y - 1].setBackground(Color.yellow);
                this.buttonGrid[x][y - 2].setBackground(Color.yellow);
            }
            return 2;
        }
        return 0;
    }

    private int[][] copyGrid(int[][] tabInitial) {
        int[][] copy = new int[tabInitial.length][tabInitial.length];
        for (int i = 0; i < tabInitial.length; i++) {
            for (int j = 0; j < tabInitial.length; j++) {
                copy[i][j] = tabInitial[i][j];
            }
        }
        return copy;
    }

    private void downButton(int posX, int posY, int size, boolean isHorizontal) {
        if (isHorizontal) {
            for (int i = 0; i <= size; i++) {
                for (int j = posX; j + 1 >= 0; j--) {
                    if (j > 0) {
                        model.getGrid()[j][posY - i] = model.getGrid()[j - 1][posY - i];
                        //this.buttonGrid[j][posY - i].setBackground(Color.BLUE);
                    }
                }
            }
        } else {
            //parcour sens bas vers haut
            for (int i = 0; i <= size; i++) {
                //System.out.println(posX);
                for (int j = posX - size + 1; j >= 0; j--) {
                    model.getGrid()[j + 1][posY] = model.getGrid()[j][posY];
                    //this.buttonGrid[j][posY].setBackground(Color.BLUE);
                }
            }
        }
    }

    public void updateGrid() {
        for (int i = 0; i < model.getLARGEUR(); i++) {
            for (int j = 0; j < model.getLONGEUR(); j++) {
                if (model.getGrid()[i][j] != img(this.buttonGrid[i][j].getIcon())) {
                    this.buttonGrid[i][j].setIcon(model.getTabImg()[model.getGrid()[i][j]]);
                }
                this.buttonGrid[i][j].setBackground(Color.white);
            }
        }
    }

    private void newRandButon(int posX, int posY, int size, boolean isHorizontal) {
        if (isHorizontal) {
            for (int i = 0; i <= size; i++) {
                int rand = random.nextInt((7) + 1);
                this.model.getGrid()[0][posY - i] = rand;
                //this.buttonGrid[0][posY - i].setBackground(Color.BLUE);
            }
        } else {
            for (int i = 0; i <= size; i++) {
                int rand = random.nextInt((7) + 1);
                this.model.getGrid()[i][posY] = rand;
                //this.buttonGrid[i][posY].setBackground(Color.BLUE);
            }
        }
    }

    public void updateScore(int i) {
        switch (i) {
            case 2:
                model.setScore(model.getScore() + model.getNiveau() * 100);
                model.setProgressBar(model.getProgressBar() + 2 * model.getNiveau());
                break;
            case 3:
                model.setScore(model.getScore() + model.getNiveau() * 300);
                model.setProgressBar(model.getProgressBar() + 4 * model.getNiveau());
                break;
            case 4:
                model.setScore(model.getScore() + model.getNiveau() * 1000);
                model.setProgressBar(model.getProgressBar() + 10 * model.getNiveau());
                break;
            default:
                break;
        }
        this.getScoreNb().setText("" + model.getScore() + "    ");
        checkProgress();
    }

    private void checkProgress() {
        if (model.getProgressBar() >= 100) {
            model.getTimer().stop();
            model.setFirstRound(true);
            model.setNiveau(model.getNiveau() + 1);
            this.levelNb.setText("" + model.getNiveau() + "    ");
            model.setProgressBar(50);
            this.progressBar.setValue(model.getProgressBar());
            resetGrid();
        } else if (model.getProgressBar() < 0) {
            model.getTimer().stop();
            this.lost();
        } else {
            progressBar.setValue(model.getProgressBar());
        }
    }

    private void resetGrid() {
        this.model.initGrid();
        this.updateGrid();
    }

    public void resetGame() {
        this.model.initGrid();
        this.updateGrid();
        model.setEssai(15);
        model.setScore(0);
        model.setNiveau(1);
        model.setProgressBar(50);
        model.getTimer().stop();
        model.setFirstRound(true);
        this.updateView();
    }

    private void updateView() {
        scoreNb.setText("" + model.getScore() + "    ");
        essaiNb.setText("" + model.getEssai() + "    ");
        levelNb.setText("" + model.getNiveau() + "    ");
        progressBar.setValue(model.getProgressBar());
    }

    public boolean isLost() {
        if (model.getProgressBar() <= 0 || model.getEssai() < 0 || hint(false) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void lost() {
        model.getTimer().stop();
        if (model.getProgressBar() <= 0) raison.setText("Rasion : temps ecouler...");
        if (model.getEssai() < 0) raison.setText("Rasion : Nombre d'essai depasser...");
        if (hint(false) == 0) raison.setText("Rasion : Plus de combinaison possible...");
        ViewScoreBoard viewScoreBoard = new ViewScoreBoard(model, this, model.getScore());
    }

    public void pause() {
        model.getTimer().stop();
        for (int i = 0; i < model.getLARGEUR(); i++) {
            for (int j = 0; j < model.getLONGEUR(); j++) {
                buttonGrid[i][j].setIcon(new ImageIcon(model.getPauseImg().getImage().getScaledInstance(65, 65, java.awt.Image.SCALE_SMOOTH)));
                //buttonGrid[i][j].setEnabled(false);
                //buttonGrid[i][j].setSelected(true);
            }
        }
    }

    public void unPause() {
        for (int i = 0; i < model.getLARGEUR(); i++) {
            for (int j = 0; j < model.getLONGEUR(); j++) {
                buttonGrid[i][j].setEnabled(true);
                buttonGrid[i][j].setSelected(false);
            }
        }
        model.getTimer().start();
        this.updateGrid();
    }


    public int img(Icon icon) {
        return Arrays.asList(model.getTabImg()).indexOf(icon);
    }

    public void updateGrid(int indexXB1, int indexYB1, int valueB2, int indexXB2, int indexYB2, int valueB1) {
        this.buttonGrid[indexXB1][indexYB1].setIcon(new ImageIcon(model.getTabImg()[valueB2].getImage().getScaledInstance(65, 65, java.awt.Image.SCALE_SMOOTH)));
        this.buttonGrid[indexXB2][indexYB2].setIcon(new ImageIcon(model.getTabImg()[valueB1].getImage().getScaledInstance(65, 65, java.awt.Image.SCALE_SMOOTH)));
    }


    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public JToggleButton[][] getButtonGrid() {
        return buttonGrid;
    }

    public void setButtonGrid(JToggleButton[][] buttonGrid) {
        this.buttonGrid = buttonGrid;
    }

    public JPanel getpMain() {
        return pMain;
    }

    public void setpMain(JPanel pMain) {
        this.pMain = pMain;
    }

    public JPanel getpProgressBar() {
        return pProgressBar;
    }

    public void setpProgressBar(JPanel pProgressBar) {
        this.pProgressBar = pProgressBar;
    }

    public JPanel getpGame() {
        return pGame;
    }

    public JMenuItem getHint() {
        return hint;
    }

    public void setHint(JMenuItem hint) {
        this.hint = hint;
    }

    public void setpGame(JPanel pGame) {
        this.pGame = pGame;
    }

    public JPanel getpInfo() {
        return pInfo;
    }

    public void setpInfo(JPanel pInfo) {
        this.pInfo = pInfo;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public JLabel getLevel() {
        return level;
    }

    public void setLevel(JLabel level) {
        this.level = level;
    }

    public JLabel getScore() {
        return score;
    }

    public void setScore(JLabel score) {
        this.score = score;
    }

    public JLabel getLevelNb() {
        return levelNb;
    }

    public void setLevelNb(JLabel levelNb) {
        this.levelNb = levelNb;
    }

    public JLabel getScoreNb() {
        return scoreNb;
    }

    public void setScoreNb(JLabel scoreNb) {
        this.scoreNb = scoreNb;
    }

    public JMenuItem getRestart() {
        return restart;
    }

    public void setRestart(JMenuItem restart) {
        this.restart = restart;
    }

    public JMenuItem getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(JMenuItem scoreboard) {
        this.scoreboard = scoreboard;
    }

    public JMenu getMenu() {
        return menu;
    }

    public void setMenu(JMenu menu) {
        this.menu = menu;
    }

    public JMenuBar getMb() {
        return mb;
    }

    public void setMb(JMenuBar mb) {
        this.mb = mb;
    }

    public static Random getRandom() {
        return random;
    }

    public static void setRandom(Random random) {
        View.random = random;
    }

    public JLabel getEssai() {
        return essai;
    }

    public void setEssai(JLabel essai) {
        this.essai = essai;
    }

    public JLabel getEssaiNb() {
        return essaiNb;
    }

    public JMenuItem getPause() {
        return pause;
    }

    public void setPause(JMenuItem pause) {
        this.pause = pause;
    }

    public void setEssaiNb(JLabel essaiNb) {
        this.essaiNb = essaiNb;
    }

    public JLabel getSpace() {
        return space;
    }

    public void setSpace(JLabel space) {
        this.space = space;
    }

    public JLabel getRaison() {
        return raison;
    }

    public void setRaison(JLabel raison) {
        this.raison = raison;
    }
}
