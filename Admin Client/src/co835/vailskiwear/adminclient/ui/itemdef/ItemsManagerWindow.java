package co835.vailskiwear.adminclient.ui.itemdef;

import co835.vailskiwear.adminclient.model.itemdef.ItemDef;
import co835.vailskiwear.adminclient.model.itemdef.ItemDefManager;
import co835.vailskiwear.adminclient.ui.util.LabelledComponent;
import res.Res;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class ItemsManagerWindow extends JFrame implements ListSelectionListener, ActionListener{

    private static ItemsManagerWindow instance;

    private final DefaultListModel<ItemDef> model;
    private final JList<ItemDef> list;

    private final JButton addButton;

    private final JTextField idBox;
    private final JTextField nameBox;
    private final JButton editNameButton;
    private final JTextField priceBox;
    private final JButton editPriceButton;
    private final JButton removeButton;

    public ItemsManagerWindow(){
        super("Vail Skiwear - Item Manager");
        setIconImage(Res.LOGO.getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultListModel<>();

        for(final ItemDef def : ItemDefManager.get())
            model.addElement(def);

        list = new JList<>(model);
        list.setBorder(new TitledBorder(String.format("%,d Items", model.size())));
        list.setCellRenderer(
                new DefaultListCellRenderer(){
                    public Component getListCellRendererComponent(final JList list, final Object o, final int i, final boolean s, final boolean f){
                        final Component comp = super.getListCellRendererComponent(list, o, i, s, f);
                        if(o == null)
                            return comp;
                        final ItemDef def = (ItemDef) o;
                        final JLabel label = (JLabel) comp;
                        label.setText(String.format("#%d - %s", def.getId(), def.getName()));
                        label.setToolTipText(DecimalFormat.getCurrencyInstance().format(def.getPrice()));
                        label.setIcon(Res.PRODUCT_16);
                        return label;
                    }
                }
        );
        list.addListSelectionListener(this);

        addButton = new JButton("Add", Res.PRODUCT_ADD_16);

        final JPanel listContainer = new JPanel(new BorderLayout());
        listContainer.add(addButton, BorderLayout.NORTH);
        listContainer.add(new JScrollPane(list), BorderLayout.CENTER);

        idBox = new JTextField();
        idBox.setEditable(false);

        nameBox = new JTextField();
        nameBox.setEditable(false);

        editNameButton = new JButton(Res.PRODUCT_EDIT_16);
        editNameButton.setEnabled(false);

        priceBox = new JTextField();
        priceBox.setEditable(false);

        editPriceButton = new JButton(Res.PRODUCT_EDIT_16);
        editPriceButton.setEnabled(false);

        removeButton = new JButton("Remove", Res.PRODUCT_REMOVE_16);

        final JPanel actions = new JPanel();
        actions.setLayout(new BoxLayout(actions, BoxLayout.X_AXIS));
        actions.add(removeButton);

        final JPanel fields = new JPanel();
        fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));
        fields.add(new LabelledComponent("ID", 60, idBox));
        fields.add(Box.createVerticalStrut(2));
        fields.add(new LabelledComponent("Name", 60, nameBox, editNameButton));
        fields.add(Box.createVerticalStrut(2));
        fields.add(new LabelledComponent("Price", 60, priceBox, editPriceButton));
        fields.add(Box.createVerticalStrut(2));
        fields.add(new LabelledComponent("Actions", 60, actions));

        final JPanel container = new JPanel(new BorderLayout());
        container.setBorder(new EmptyBorder(5, 5, 5, 5));
        container.add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listContainer, fields), BorderLayout.CENTER);

        add(container, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    public void valueChanged(final ListSelectionEvent e){
        final ItemDef selected = list.getSelectedValue();
        update(selected);
    }

    public void actionPerformed(final ActionEvent e){

    }

    public void update(final ItemDef def){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        idBox.setText(def == null ? "" : Integer.toString(def.getId()));
                        idBox.repaint();
                        nameBox.setText(def == null ? "" : def.getName());
                        nameBox.repaint();
                        editNameButton.setEnabled(def != null);
                        editNameButton.repaint();
                        priceBox.setText(def == null ? "" : DecimalFormat.getCurrencyInstance().format(def.getPrice()));
                        priceBox.repaint();
                        editPriceButton.setEnabled(def != null);
                        editPriceButton.repaint();
                        removeButton.setEnabled(def != null);
                        removeButton.repaint();
                    }
                }
        );
    }

    public static void init(){
        instance = new ItemsManagerWindow();
    }

    public static ItemsManagerWindow get(){
        return instance;
    }

    public static void open(){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        instance.setVisible(true);
                    }
                }
        );
    }

    public static void close(){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        instance.setVisible(false);
                    }
                }
        );
    }

}
