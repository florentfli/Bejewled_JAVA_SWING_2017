import javax.swing.*;
import java.awt.*;

public class ViewPause extends JFrame {
    private Model model;
    private View view;

    private JLabel pauseText;
    private JButton reprendre;

    public ViewPause(Model model, View view) {
        this.view = view;
        this.model = model;

        setTitle("PAUSE...");
        setSize(400, 200);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setVisible(true);

        initWidget();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("./img.png")));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        ControlPause controlPause = new ControlPause(model, this, view);
    }

    private void initWidget() {
        pauseText = new JLabel("Votre partie est en pause !\n\n\n\n");
        reprendre = new JButton("Continue..");

        reprendre.setAlignmentX(Component.CENTER_ALIGNMENT);
        pauseText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel pMain = new JPanel();
        pMain.setLayout(new BoxLayout(pMain,BoxLayout.Y_AXIS));

        pMain.add(new JLabel(" "));
        pMain.add(new JLabel(" "));
        pMain.add(new JLabel(" "));
        pMain.add(pauseText);
        pMain.add(new JLabel(" "));
        pMain.add(new JLabel(" "));
        pMain.add(new JLabel(" "));
        pMain.add(reprendre);
        setContentPane(pMain);
    }

    public void setControlButonGame(ControlPause controlPause) {
        reprendre.addActionListener(controlPause);
    }

    public JButton getReprendre() {
        return reprendre;
    }

    public void setReprendre(JButton reprendre) {
        this.reprendre = reprendre;
    }
}
