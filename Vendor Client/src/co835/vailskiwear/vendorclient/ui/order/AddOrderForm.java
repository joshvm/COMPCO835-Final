package co835.vailskiwear.vendorclient.ui.order;

import co835.vailskiwear.vendorclient.model.itemdef.ItemDef;
import co835.vailskiwear.vendorclient.model.itemdef.ItemDefManager;
import co835.vailskiwear.vendorclient.model.order.SalesOrder;
import co835.vailskiwear.vendorclient.net.Connection;
import co835.vailskiwear.vendorclient.ui.util.LabelledComponent;
import res.Res;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.NumberFormatter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class AddOrderForm extends JFrame implements ActionListener{

    private static final int INVALID_ITEM_COUNT = 0;
    private static final int INVALID_ITEM = 1;
    private static final int INVALID_QUANTITY = 2;
    private static final int SUCCESS = 3;
    private static final int ERROR = 4;

    private static AddOrderForm instance;

    private final JComboBox<ItemDef> itemsBox;
    private final JSpinner quantityBox;
    private final JButton addItemButton;
    private final JButton removeItemButton;
    private final JButton clearItemsButton;

    private final ItemsTableModel finalItemsModel;
    private final JTable finalItemsTable;

    private final JLabel infoLabel;
    private final JButton addOrderButton;

    public AddOrderForm(){
        super("Vail Skiwear - Add Order");
        setIconImage(Res.LOGO.getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        itemsBox = new JComboBox<>(ItemDefManager.getArray());
        itemsBox.setRenderer(
                new DefaultListCellRenderer(){
                    public Component getListCellRendererComponent(final JList list, final Object o, final int i, final boolean s, final boolean f){
                        final Component comp = super.getListCellRendererComponent(list, o, i, s, f);
                        if(o == null)
                            return comp;
                        final ItemDef def = (ItemDef) o;
                        final JLabel label = (JLabel) comp;
                        label.setText(String.format("%s (%s)", def.getName(), DecimalFormat.getCurrencyInstance().format(def.getPrice())));
                        return label;
                    }
                }
        );

        quantityBox = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        final JFormattedTextField txt = ((JSpinner.NumberEditor) quantityBox.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);

        addItemButton = new JButton("Add Item", Res.ADD_16);
        addItemButton.addActionListener(this);

        removeItemButton = new JButton("Remove Item", Res.REMOVE_16);
        removeItemButton.addActionListener(this);

        clearItemsButton = new JButton("Clear", Res.CLEAR_16);
        clearItemsButton.addActionListener(this);

        final JPanel actions = new JPanel();
        actions.setLayout(new BoxLayout(actions, BoxLayout.X_AXIS));
        actions.add(addItemButton);
        actions.add(Box.createHorizontalStrut(2));
        actions.add(removeItemButton);
        actions.add(Box.createHorizontalStrut(2));
        actions.add(clearItemsButton);

        final JPanel addItemPanel = new JPanel();
        addItemPanel.setBorder(new TitledBorder("Add Item"));
        addItemPanel.setLayout(new BoxLayout(addItemPanel, BoxLayout.Y_AXIS));
        addItemPanel.add(new LabelledComponent("Item", 75, itemsBox));
        addItemPanel.add(Box.createVerticalStrut(2));
        addItemPanel.add(new LabelledComponent("Quantity", 75, quantityBox));
        addItemPanel.add(Box.createVerticalStrut(2));
        addItemPanel.add(new LabelledComponent("Actions", 75, actions));

        finalItemsModel = new ItemsTableModel(ItemsTableModel.Column.QUANTITY);
        finalItemsModel.setListener(
                new ItemsTableModel.UpdateListener(){
                    public void onTableModelUpdate(){
                        refreshAddOrderButton();
                    }
                }
        );

        finalItemsTable = new JTable(finalItemsModel);
        final DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)finalItemsTable.getDefaultRenderer(String.class);
        renderer.setHorizontalAlignment(JLabel.CENTER);
        finalItemsTable.setDefaultRenderer(String.class, renderer);

        infoLabel = new JLabel("", JLabel.CENTER);

        addOrderButton = new JButton("Place Order", Res.ORDER_ADD_16);
        addOrderButton.addActionListener(this);

        refreshAddOrderButton();

        final JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.add(Box.createHorizontalGlue());
        buttons.add(infoLabel);
        buttons.add(Box.createHorizontalGlue());
        buttons.add(addOrderButton);

        final JPanel center = new JPanel(new BorderLayout());
        center.add(addItemPanel, BorderLayout.NORTH);
        center.add(new JScrollPane(finalItemsTable), BorderLayout.CENTER);
        center.add(buttons, BorderLayout.SOUTH);

        final JLabel heading = new JLabel("Create Sales Order", JLabel.CENTER);
        heading.setFont(heading.getFont().deriveFont(30f));

        final JPanel container = new JPanel(new BorderLayout());
        container.setBorder(new EmptyBorder(5, 5, 5, 5));
        container.add(heading, BorderLayout.NORTH);
        container.add(center, BorderLayout.CENTER);

        add(container, BorderLayout.CENTER);

        setSize(getPreferredSize().width, 370);
        setLocationRelativeTo(null);
    }

    public void handleResponse(final int response){
        switch(response){
            case INVALID_ITEM:
                info("Invalid items", Color.RED);
                break;
            case INVALID_ITEM_COUNT:
                info("Invalid item count (> 0)", Color.RED);
                break;
            case INVALID_QUANTITY:
                info("Invalid quantity", Color.RED);
                break;
            case SUCCESS:
                info("Success", Color.GREEN);
                clear();
                break;
            case ERROR:
                info("Error adding order", Color.RED);
                break;
        }
    }

    public void info(final String text, final Color color){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        infoLabel.setText(text);
                        infoLabel.setForeground(color);
                        infoLabel.repaint();
                    }
                }
        );
    }

    private void refreshAddOrderButton(){
        double sum = 0;
        for(final SalesOrder.Item item : finalItemsModel.getItems())
            sum += item.getTotalPrice();
        final double total = sum;
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        addOrderButton.setText(String.format("Place Order (%s)", DecimalFormat.getCurrencyInstance().format(total)));
                        addOrderButton.repaint();
                    }
                }
        );
    }

    private void clear(){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        finalItemsModel.clear();
                        finalItemsTable.repaint();
                        refreshAddOrderButton();
                    }
                }
        );
    }

    public void actionPerformed(final ActionEvent e){
        final Object source = e.getSource();
        info("", null);
        if(source.equals(addItemButton)){
            final ItemDef def = (ItemDef)itemsBox.getSelectedItem();
            final int quantity = (Integer) quantityBox.getValue();
            final SalesOrder.Item existing = finalItemsModel.byItemId(def.getId());
            if(existing != null){
                existing.setQuantity(existing.getQuantity() + quantity);
                refreshAddOrderButton();
                SwingUtilities.invokeLater(
                        new Runnable() {
                            public void run(){
                                finalItemsTable.repaint();
                            }
                        }
                );
                return;
            }
            final SalesOrder.Item item = new SalesOrder.Item(def.getId(), def.getPrice(), quantity);
            SwingUtilities.invokeLater(
                    new Runnable(){
                        public void run(){
                            finalItemsModel.add(item);
                            finalItemsTable.repaint();
                            refreshAddOrderButton();
                        }
                    }
            );
        }else if(source.equals(removeItemButton)){
            final int selected = finalItemsTable.getSelectedRow();
            if(selected < 0)
                return;
            SwingUtilities.invokeLater(
                    new Runnable(){
                        public void run(){
                            finalItemsModel.remove(selected);
                            finalItemsTable.repaint();
                            refreshAddOrderButton();
                        }
                    }
            );
        }else if(source.equals(clearItemsButton)){
            clear();
        }else if(source.equals(addOrderButton)){
            info("Processing...", null);
            final List<SalesOrder.Item> items = finalItemsModel.getItems();
            if(items.isEmpty()){
                info("Order must have items", Color.RED);
                return;
            }
            if(!Connection.addOrder(items)){
                info("Error sending add order request", Color.RED);
            }
        }
    }

    public static void init(){
        instance = new AddOrderForm();
    }

    public static AddOrderForm get(){
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
