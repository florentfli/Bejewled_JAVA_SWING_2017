import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPause implements ActionListener {

    Model model;
    ViewPause viewPause;
    View view;

    public ControlPause(Model model, ViewPause viewPause, View view) {
        this.model = model;
        this.viewPause = viewPause;
        this.view = view;
        this.viewPause.setControlButonGame(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewPause.getReprendre()) {
            this.viewPause.dispose();
            view.unPause();
        }
    }
}
