package co835.vailskiwear.vendorclient.ui.util;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class LabelledComponent extends JPanel{

    private static final int SPACING = 10;

    public LabelledComponent(final String labelText, final int labelWidth, final JComponent component, final int spacing){
        super(new BorderLayout(spacing, 0));

        final JLabel label = new JLabel(labelText, JLabel.RIGHT);
        label.setPreferredSize(new Dimension(labelWidth, label.getPreferredSize().height));
        label.setMinimumSize(label.getPreferredSize());
        label.setSize(label.getPreferredSize());

        add(label, BorderLayout.WEST);
        add(component, BorderLayout.CENTER);
    }

    public LabelledComponent(final String labelText, final int labelWidth, final JComponent component){
        this(labelText, labelWidth, component, SPACING);
    }
}
