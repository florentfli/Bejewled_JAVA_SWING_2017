import com.sun.deploy.panel.ITreeNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ControlButtonGame implements ActionListener {

    Model model;
    View view;

    public ControlButtonGame(Model model, View view) {
        this.model = model;
        this.view = view;
        for (Component child : view.getpGame().getComponents()) {
            if (child instanceof JToggleButton) {
                JToggleButton bt = (JToggleButton) child;
                this.view.setControlButonGame(bt, this);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() instanceof JToggleButton)) {
            model.checkFirstTurn();
            this.swapCase(e);
        }
    }

    private void setIndex(JToggleButton b) {
        for (int i = 0; i < this.model.getLARGEUR(); i++) {
            for (int j = 0; j < this.model.getLONGEUR(); j++) {
                if (this.view.getButtonGrid()[i][j] == b) {
                    if (!this.model.isSelected()) {
                        this.model.setIndexXB1(i);
                        this.model.setIndexYB1(j);
                        this.model.setValueB1(model.getGrid()[i][j]);
                    } else {
                        this.model.setIndexXB2(i);
                        this.model.setIndexYB2(j);
                        this.model.setValueB2(model.getGrid()[i][j]);
                    }
                }
            }
        }
    }

    public void swapCase(ActionEvent e) {
        if (!this.model.isSelected()) {
            this.model.getTabBut()[1] = (JToggleButton) e.getSource();
            this.setIndex(this.model.getTabBut()[1]);
            this.model.setSelected(true);
        } else {
            this.model.getTabBut()[0] = (JToggleButton) e.getSource();
            this.setIndex(this.model.getTabBut()[0]);

            this.model.getTabBut()[0].setSelected(false);
            this.model.getTabBut()[1].setSelected(false);

            this.model.getGrid()[this.model.getIndexXB1()][this.model.getIndexYB1()] = this.model.getValueB2();
            this.model.getGrid()[this.model.getIndexXB2()][this.model.getIndexYB2()] = this.model.getValueB1();

            if (!isItself() && view.checkIsGridEmpty()) {
                model.setEssai(model.getEssai() - 1);
                if (model.getEssai() >= 0) view.getEssaiNb().setText(("" + model.getEssai()));
            }
            if (isVoisin() && !isItself() && !isSameValue() && !view.checkIsGridEmpty()) {

                this.model.getGrid()[this.model.getIndexXB1()][this.model.getIndexYB1()] = this.model.getValueB2();
                this.model.getGrid()[this.model.getIndexXB2()][this.model.getIndexYB2()] = this.model.getValueB1();

                view.updateGrid(this.model.getIndexXB1(), this.model.getIndexYB1(), this.model.getValueB2(), this.model.getIndexXB2(), this.model.getIndexYB2(), this.model.getValueB1());
                while (!view.checkIsGridEmpty()) {
                    this.view.checkGrid();
                }
            } else {
                this.model.getGrid()[this.model.getIndexXB1()][this.model.getIndexYB1()] = this.model.getValueB1();
                this.model.getGrid()[this.model.getIndexXB2()][this.model.getIndexYB2()] = this.model.getValueB2();
            }
            this.model.setSelected(false);
            if (view.isLost()) view.lost();
        }
    }

    private boolean isVoisin() {
        if (
                this.model.getIndexXB1() + 1 == this.model.getIndexXB2() && this.model.getIndexYB1() == this.model.getIndexYB2() ||
                        this.model.getIndexXB1() - 1 == this.model.getIndexXB2() && this.model.getIndexYB1() == this.model.getIndexYB2() ||
                        this.model.getIndexXB1() == this.model.getIndexXB2() && this.model.getIndexYB1() + 1 == this.model.getIndexYB2() ||
                        this.model.getIndexXB1() == this.model.getIndexXB2() && this.model.getIndexYB1() - 1 == this.model.getIndexYB2()
                ) {

            return true;
        } else {
            return false;
        }
    }

    private boolean isItself() {
        return this.model.getIndexXB1() == this.model.getIndexXB2() && this.model.getIndexYB1() == this.model.getIndexYB2();
    }

    private boolean isSameValue() {
        return this.model.getValueB1() == this.model.getValueB2();
    }
}
