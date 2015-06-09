package co835.vailskiwear.adminclient.ui.order;

import co835.vailskiwear.adminclient.model.order.SalesOrder;

import javax.swing.table.AbstractTableModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class ItemsTableModel extends AbstractTableModel{

    public static interface UpdateListener{
        public void onTableModelUpdate();
    }

    public enum Column{
        NAME, QUANTITY, PRICE, TOTAL
    }

    private Column editableColumn;
    private final List<SalesOrder.Item> model;

    private UpdateListener listener;

    public ItemsTableModel(final Column editableColumn){
        this.editableColumn = editableColumn;

        model = new ArrayList<>();
    }

    public void setListener(final UpdateListener listener){
        this.listener = listener;
    }

    public ItemsTableModel(){
        this(null);
    }

    public List<SalesOrder.Item> getItems(){
        return model;
    }

    public void add(final SalesOrder.Item item){
        model.add(item);
        fireTableRowsInserted(model.size()-1, model.size()-1);
    }

    public int indexOf(final SalesOrder.Item item){
        return model.indexOf(item);
    }

    public SalesOrder.Item byItemId(final int itemId){
        for(final SalesOrder.Item i : model)
            if(i.getId() == itemId)
                return i;
        return null;
    }

    public void remove(final SalesOrder.Item item){
        final int i = indexOf(item);
        if(i != -1)
            remove(i);
    }

    public void remove(final int i){
        model.remove(i);
        fireTableRowsDeleted(i, i);
    }

    public void clear(){
        final int size = model.size()-1;
        model.clear();
        if(size > -1)
            fireTableRowsDeleted(0, size);
    }

    public int getRowCount(){
        return model.size();
    }

    public int size(){
        return model.size();
    }

    public Object getValueAt(final int r, final int c){
        if(r < 0 || r > model.size() - 1)
            return null;
        final SalesOrder.Item item = model.get(r);
        final Column col = Column.values()[c];
        switch(col){
            case NAME:
                return item.getDef().getName();
            case QUANTITY:
                return String.format("%,d", item.getQuantity());
            case PRICE:
                return DecimalFormat.getCurrencyInstance().format(item.getPrice());
            case TOTAL:
                return DecimalFormat.getCurrencyInstance().format(item.getTotalPrice());
            default:
                return null;
        }
    }

    public void setValueAt(final Object o, final int r, final int c){
        if(r < 0 || r > model.size() - 1)
            return;
        final SalesOrder.Item item = model.get(r);
        final Column col = Column.values()[c];
        switch(col){
            case QUANTITY:
                try{
                    final int quantity = Integer.parseInt(o.toString());
                    item.setQuantity(quantity);
                    if(item.getQuantity() < 1)
                        remove(r);
                    if(listener != null)
                        listener.onTableModelUpdate();
                }catch(Exception ex){}
                break;
        }
    }

    public int getColumnCount(){
        return Column.values().length;
    }

    public String getColumnName(final int c){
        return Column.values()[c].toString();
    }

    public boolean isCellEditable(final int r, final int c){
        return editableColumn != null && editableColumn.ordinal() == c;
    }
}
