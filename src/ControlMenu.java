import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlMenu implements ActionListener {

    Model model;
    View view;

    public ControlMenu(Model model, View view) {
        this.model = model;
        this.view = view;
        view.setControlButonMenu(this);
    }

    public ControlMenu(View view) {
        this.view = view;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getHint()) {
            view.hint(true);
        }
        if (e.getSource() == view.getRestart()) {
            view.resetGame();
        }
        if (e.getSource() == view.getScoreboard()) {
            view.pause();
            ViewScoreBoard scbView = new ViewScoreBoard(model, view);
        }
        if (e.getSource() == view.getPause()){
            view.pause();
            ViewPause vP = new ViewPause(model, view);
        }
    }
}
