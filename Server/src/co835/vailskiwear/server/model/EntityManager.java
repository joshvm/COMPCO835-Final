package co835.vailskiwear.server.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public abstract class EntityManager<T extends Entity> {

    private final File file;
    private final String tagName;
    protected final Map<Integer, T> map;

    protected EntityManager(final File file, final String tagName){
        this.file = file;
        this.tagName = tagName;

        map = new HashMap<>();
    }

    public int getNextId(){
        for(int i = 0; i < Integer.MAX_VALUE; i++)
            if(get(i) == null)
                return i;
        return -1;
    }

    public int size(){
        return map.size();
    }

    public Collection<T> get(){
        return map.values();
    }

    public T get(final int id){
        return map.get(id);
    }

    public boolean add(final T entity, final boolean save){
        map.put(entity.getId(), entity);
        if(!save || save())
            return true;
        remove(entity, false);
        return false;
    }

    public boolean remove(final T entity, final boolean save){
        map.remove(entity.getId());
        if(!save || save())
            return true;
        add(entity, false);
        return false;
    }

    public boolean load(){
        System.out.println("loading " + tagName + "s");
        if(!file.exists())
            return true;
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            final DocumentBuilder bldr = factory.newDocumentBuilder();
            final Document doc = bldr.parse(file);
            final NodeList items = doc.getElementsByTagName(tagName);
            for(int i = 0; i < items.getLength(); i++){
                final Node n = items.item(i);
                if(n.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                final Element e = (Element) n;
                final T item = parse(e);
                map.put(item.getId(), item);
            }
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean save(){
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            final DocumentBuilder bldr = factory.newDocumentBuilder();
            final Document doc = bldr.newDocument();
            final Element root = doc.createElement(tagName + "s");
            for(final T item : map.values())
                root.appendChild(item.toElement(doc));
            doc.appendChild(root);
            final Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            tr.transform(new DOMSource(doc), new StreamResult(new FileWriter(file)));
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    protected abstract T parse(final Element e);
}
