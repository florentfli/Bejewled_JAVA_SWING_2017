import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Model {

    private static Random random = new Random();

    private javax.swing.ImageIcon jewl1 = new javax.swing.ImageIcon(getClass().getResource("/jewl1.gif"));
    private javax.swing.ImageIcon jewl2 = new javax.swing.ImageIcon(getClass().getResource("/jewl2.gif"));
    private javax.swing.ImageIcon jewl3 = new javax.swing.ImageIcon(getClass().getResource("/jewl3.gif"));
    private javax.swing.ImageIcon jewl4 = new javax.swing.ImageIcon(getClass().getResource("/jewl4.gif"));
    private javax.swing.ImageIcon jewl5 = new javax.swing.ImageIcon(getClass().getResource("/jewl5.gif"));
    private javax.swing.ImageIcon jewl6 = new javax.swing.ImageIcon(getClass().getResource("/jewl6.gif"));
    private javax.swing.ImageIcon jewl7 = new javax.swing.ImageIcon(getClass().getResource("/jewl7.gif"));
    private javax.swing.ImageIcon jewl8 = new javax.swing.ImageIcon(getClass().getResource("/jewl8.gif"));

    private javax.swing.ImageIcon[] tabImg = new javax.swing.ImageIcon[]{jewl1, jewl2, jewl3, jewl4, jewl5, jewl6, jewl7, jewl8};

    private ImageIcon imgicon = new ImageIcon("./img.png");
    private ImageIcon pauseImg = new ImageIcon("C:\\Users\\Florent\\Desktop\\cours\\S2\\IHM\\projet_IHM2\\out\\production\\projet_IHM2\\diamond.png");

    private int valueB1, valueB2, indexXB1, indexYB1, indexXB2, indexYB2;
    private JToggleButton[] tabBut = new JToggleButton[2];

    private int LARGEUR = 10, LONGEUR = 10;
    private int[][] grid = new int[LARGEUR][LONGEUR];

    private int niveau = 1, score = 0, progressBar = 50, essai = 15;

    private int coef3=2,coef4=3,coef5=4;

    private boolean isSelected = false;
    private boolean firstRound = true;

    private Timer timer;

    private String scorePath = "C:\\Users\\Florent\\Desktop\\cours\\S2\\IHM\\projet_IHM2\\src\\scoreBoard.txt";
    private ArrayList<String> bestScores = new ArrayList<>();


    public Model() {
        initTabImg();
        initGrid();
        initScoreBoard();
    }

    public void initScoreBoard() {
        bestScores.add("0");
        bestScores.add("0");
        bestScores.add("0");
        lireScoreBoard();
    }

    private void initTabImg() {
        for (int i = 0; i < this.tabImg.length; i++) {
            this.tabImg[i] = new ImageIcon(this.tabImg[i].getImage().getScaledInstance(65, 65, java.awt.Image.SCALE_SMOOTH));
        }
    }

    public void checkFirstTurn() {
        if (firstRound) {
            firstRound = false;
            this.timer.start();
        }
    }


    public void initGrid() {
        for (int i = 0; i < LARGEUR; i++) {
            for (int j = 0; j < LONGEUR; j++) {
                int rand = random.nextInt((7) + 1);
                if (i >= 2) {
                    if (grid[i - 2][j] == grid[i - 1][j] && grid[i - 2][j] == rand) {
                        while (grid[i - 2][j] == rand) {
                            rand = random.nextInt((7) + 1);
                        }
                    }
                }
                if (j >= 2) {
                    if (grid[i][j - 2] == grid[i][j - 1] && grid[i][j - 2] == rand) {
                        while (grid[i][j - 2] == rand) {
                            rand = random.nextInt(8);
                        }
                    }
                }
                grid[i][j] = rand;
            }
        }
    }

    public void lireScoreBoard() {
        try (BufferedReader br = new BufferedReader(new FileReader(scorePath))) {
            int counter = 0;
            for (String line; (line = br.readLine()) != null && counter < 3; ) {
                bestScores.set(counter, line);
                counter++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isInScoreBoard(int score) {
        return Integer.parseInt(bestScores.get(2)) < score;
    }

    public void ecrirScoreBoard(int score) {
        if (Integer.parseInt(bestScores.get(0)) < score) {
            bestScores.set(2, bestScores.get(1));
            bestScores.set(1, bestScores.get(0));
            bestScores.set(0, "" + score);
        } else if (Integer.parseInt(bestScores.get(1)) < score) {
            bestScores.set(2, bestScores.get(1));
            bestScores.set(1, "" + score);
        } else if (Integer.parseInt(bestScores.get(2)) < score) {
            bestScores.set(2, "" + score);
        }
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(scorePath, false));
            for (int i = 0; i < bestScores.size(); i++) {
                out.write(bestScores.get(i));
                out.newLine();
            }
            out.close();
        } catch (IOException e) {
        }
    }

    public int positionScoreBoard(int score) {
        if (Integer.parseInt(bestScores.get(0)) <= score) {
            return 1;
        } else if (Integer.parseInt(bestScores.get(1)) <= score) {
            return 2;
        } else if (Integer.parseInt(bestScores.get(2)) <= score) {
            return 3;
        }
        return 0;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ImageIcon[] getTabImg() {
        return tabImg;
    }

    public void setTabImg(ImageIcon[] tabImg) {
        this.tabImg = tabImg;
    }

    public ImageIcon getImgicon() {
        return imgicon;
    }

    public void setImgicon(ImageIcon imgicon) {
        this.imgicon = imgicon;
    }

    public int getLARGEUR() {
        return LARGEUR;
    }

    public void setLARGEUR(int LARGEUR) {
        this.LARGEUR = LARGEUR;
    }

    public int getLONGEUR() {
        return LONGEUR;
    }

    public void setLONGEUR(int LONGEUR) {
        this.LONGEUR = LONGEUR;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(int progressBar) {
        this.progressBar = progressBar;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public boolean isFirstRound() {
        return firstRound;
    }

    public void setFirstRound(boolean firstRound) {
        this.firstRound = firstRound;
    }

    public int getEssai() {
        return essai;
    }

    public void setEssai(int essai) {
        this.essai = essai;
    }

    public String getScorePath() {
        return scorePath;
    }

    public void setScorePath(String scorePath) {
        this.scorePath = scorePath;
    }

    public ArrayList<String> getBestScores() {
        return bestScores;
    }

    public void setBestScores(ArrayList<String> bestScores) {
        this.bestScores = bestScores;
    }

    public int getValueB1() {
        return valueB1;
    }

    public void setValueB1(int valueB1) {
        this.valueB1 = valueB1;
    }

    public int getValueB2() {
        return valueB2;
    }

    public void setValueB2(int valueB2) {
        this.valueB2 = valueB2;
    }

    public int getIndexXB1() {
        return indexXB1;
    }

    public void setIndexXB1(int indexXB1) {
        this.indexXB1 = indexXB1;
    }

    public int getIndexYB1() {
        return indexYB1;
    }

    public void setIndexYB1(int indexYB1) {
        this.indexYB1 = indexYB1;
    }

    public int getIndexXB2() {
        return indexXB2;
    }

    public void setIndexXB2(int indexXB2) {
        this.indexXB2 = indexXB2;
    }

    public int getIndexYB2() {
        return indexYB2;
    }

    public void setIndexYB2(int indexYB2) {
        this.indexYB2 = indexYB2;
    }

    public JToggleButton[] getTabBut() {
        return tabBut;
    }

    public void setTabBut(JToggleButton[] tabBut) {
        this.tabBut = tabBut;
    }

    public int getCoef3() {
        return coef3;
    }

    public void setCoef3(int coef3) {
        this.coef3 = coef3;
    }

    public int getCoef4() {
        return coef4;
    }

    public void setCoef4(int coef4) {
        this.coef4 = coef4;
    }

    public int getCoef5() {
        return coef5;
    }

    public void setCoef5(int coef5) {
        this.coef5 = coef5;
    }

    public ImageIcon getPauseImg() {
        return pauseImg;
    }

    public void setPauseImg(ImageIcon pauseImg) {
        this.pauseImg = pauseImg;
    }
}
