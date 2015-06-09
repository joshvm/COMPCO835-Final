package co835.vailskiwear.adminclient.ui.vendor;

import co835.vailskiwear.adminclient.model.profile.Vendor;
import res.Res;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionListener;
import java.awt.BorderLayout;
import java.awt.Component;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class VendorsList extends JPanel {

    private final DefaultListModel<Vendor> model;
    private final JList<Vendor> list;

    public VendorsList(){
        super(new BorderLayout());
        setBorder(new TitledBorder("0 Vendors"));

        model = new DefaultListModel<>();

        list = new JList<>(model);
        list.setCellRenderer(
                new DefaultListCellRenderer(){
                    public Component getListCellRendererComponent(final JList list, final Object o, final int i, final boolean s, final boolean f){
                        final Component comp = super.getListCellRendererComponent(list, o, i, s, f);
                        if(o == null)
                            return comp;
                        final Vendor vendor = (Vendor) o;
                        final JLabel label = (JLabel) comp;
                        label.setIcon(Res.PROFILE_16);
                        label.setText(String.format("%s (ID: %d)", vendor.getUser(), vendor.getId()));
                        return label;
                    }
                }
        );

        add(new JScrollPane(list), BorderLayout.CENTER);
    }

    public void addListSelectionListener(final ListSelectionListener listener){
        list.addListSelectionListener(listener);
    }

    public Vendor getSelected(){
        return list.getSelectedValue();
    }

    private void refreshBorder(){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        setBorder(new TitledBorder(String.format("%,d Vendor%s", model.size(), model.size() != 1 ? "s" : "")));
                        revalidate();
                        repaint();
                    }
                }
        );
    }

    public void add(final Vendor vendor){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        model.addElement(vendor);
                        list.repaint();
                        refreshBorder();
                    }
                }
        );
    }

    public void remove(final Vendor vendor){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        model.removeElement(vendor);
                        list.repaint();
                        refreshBorder();
                    }
                }
        );
    }

    public void clear(){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        model.clear();
                        list.repaint();
                        refreshBorder();
                    }
                }
        );
    }
}
