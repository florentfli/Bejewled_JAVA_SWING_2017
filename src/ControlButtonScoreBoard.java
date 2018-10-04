import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlButtonScoreBoard implements ActionListener {

    Model model;
    View view;
    ViewScoreBoard sBView;

    public ControlButtonScoreBoard(Model model, ViewScoreBoard sBView, View view) {
        this.view = view;
        this.model = model;
        this.sBView = sBView;
        this.sBView.setControlButonGame(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sBView.getbQuit()) {
            model.getTimer().stop();
            view.dispose();
            sBView.dispose();
        }
        if (e.getSource() == sBView.getbRestart()){
            model.getTimer().stop();
            view.resetGame();
            sBView.dispose();
        }
        if (e.getSource() == sBView.getbClose()){
            model.getTimer().stop();
            sBView.dispose();
            view.unPause();
        }
    }
}
