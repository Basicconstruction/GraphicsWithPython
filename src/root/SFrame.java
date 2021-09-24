package root;

import javax.swing.*;

public class SFrame extends JFrame {
    private int defaultCloseOperation = HIDE_ON_CLOSE;

    public SFrame(){
        super();
    }
    @Override
    public void setDefaultCloseOperation(int operation) {
        if (operation != DO_NOTHING_ON_CLOSE &&
                operation != HIDE_ON_CLOSE &&
                operation != DISPOSE_ON_CLOSE &&
                operation != EXIT_ON_CLOSE) {
            throw new IllegalArgumentException("defaultCloseOperation must be"
                    + " one of: DO_NOTHING_ON_CLOSE, HIDE_ON_CLOSE,"
                    + " DISPOSE_ON_CLOSE, or EXIT_ON_CLOSE");
        }

        if (operation == EXIT_ON_CLOSE) {
            SecurityManager security = System.getSecurityManager();
            if (security != null) {
                security.checkExit(0);
            }
        }
        if (this.defaultCloseOperation != operation) {
            int oldValue = this.defaultCloseOperation;
            this.defaultCloseOperation = operation;
            firePropertyChange("defaultCloseOperation", oldValue, operation);
        }
    }
}
