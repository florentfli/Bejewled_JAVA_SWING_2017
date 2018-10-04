import javax.swing.*;
import java.awt.*;

public class ViewScoreBoard extends JFrame {
    private  Model model;
    private View view;

    private JLabel score1, score2, score3, finGame, msg;
    private int score;
    private JButton bQuit, bRestart, bClose;
    private JPanel pScore;


    public ViewScoreBoard(Model model, View view, int score) {
        this.view = view;
        this.model = model;
        this.score = score;
        model.initScoreBoard();
        model.ecrirScoreBoard(score);
        setTitle("SCORE BOARD");
        setSize(400,230);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);
        initWidget();
        addWidgetFinGame();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("./img.png")));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        ControlButtonScoreBoard controlScore = new ControlButtonScoreBoard(model, this, view);
        this.requestFocus();
    }

    public ViewScoreBoard(Model model, View view) {
        this.model = model;
        this.view = view;
        model.initScoreBoard();
        setSize(new Dimension(400, 200));
        setTitle("SCORE BOARD");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);
        initWidget();
        addWidgetInGame();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("./img.png")));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        ControlButtonScoreBoard controlScore = new ControlButtonScoreBoard(model, this, view);
    }

    private void initWidget() {
        score1 = new JLabel();
        score2 = new JLabel();
        score3 = new JLabel();
        finGame = new JLabel("Fin de la partie : " + model.getScore() + " points");
        bQuit = new JButton("Exit");
        bClose = new JButton("Close");
        bRestart = new JButton("Try Again");
        msg = new JLabel();

        setScoreBoard();
        setMsg(score);
    }

    private void setScoreBoard() {
        pScore = new JPanel(new GridLayout(3, 1,20,20));

        score1.setText("1- " + model.getBestScores().get(0));
        score2.setText("2- " + model.getBestScores().get(1));
        score3.setText("3- " + model.getBestScores().get(2));

        score1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        score2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        score3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        pScore.add(score1);
        pScore.add(score2);
        pScore.add(score3);
    }

    private void setMsg(int score) {
        int position = model.positionScoreBoard(score);
        switch (position) {
            case 1:
                msg.setText("Félicitation vous avec le meilleur score local !");
                break;
            case 2:
                msg.setText("Félicitation vous avec le 2eme meilleur score local !");
                break;
            case 3:
                msg.setText("Félicitation vous avec le 3eme meilleur score local !");
                break;
            case 0:
                msg.setText("Votre score n'est pas sur le podium...");
                break;
        }
    }

    private void addWidgetFinGame() {
        JPanel pMain = new JPanel();
        JPanel pButton = new JPanel();
        pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));

        pButton.add(bQuit);
        pButton.add(bRestart);

        pMain.add(view.getRaison());
        pMain.add(finGame);
        pMain.add(msg);
        pMain.add(pScore);
        pMain.add(pButton);
        setContentPane(pMain);
    }

    private void addWidgetInGame() {
        JPanel pMain = new JPanel();
        JPanel pButton = new JPanel();
        pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));

        pButton.add(bClose);
        pMain.add(pScore);
        pMain.add(pButton);
        setContentPane(pMain);
    }

    public void setControlButonGame(ControlButtonScoreBoard cBSB) {
        bRestart.addActionListener(cBSB);
        bQuit.addActionListener(cBSB);
        bClose.addActionListener(cBSB);
    }

    public Model getModel() {
        return model;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public JLabel getFinGame() {
        return finGame;
    }

    public void setFinGame(JLabel finGame) {
        this.finGame = finGame;
    }

    public JLabel getScore1() {
        return score1;
    }

    public void setScore1(JLabel score1) {
        this.score1 = score1;
    }

    public JLabel getScore2() {
        return score2;
    }

    public void setScore2(JLabel score2) {
        this.score2 = score2;
    }

    public JLabel getScore3() {
        return score3;
    }

    public void setScore3(JLabel score3) {
        this.score3 = score3;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public JButton getbClose() {
        return bClose;
    }

    public void setbClose(JButton bClose) {
        this.bClose = bClose;
    }

    public JPanel getpScore() {
        return pScore;
    }

    public void setpScore(JPanel pScore) {
        this.pScore = pScore;
    }

    public JLabel getMsg() {
        return msg;
    }

    public void setMsg(JLabel msg) {
        this.msg = msg;
    }

    public JButton getbQuit() {
        return bQuit;
    }

    public void setbQuit(JButton bQuit) {
        this.bQuit = bQuit;
    }

    public JButton getbRestart() {
        return bRestart;
    }

    public void setbRestart(JButton bRestart) {
        this.bRestart = bRestart;
    }
}
