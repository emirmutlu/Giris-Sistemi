package tr.com.uludem.b4b;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public final class RememberMe {
    private static final File XML_FILE = new File("C:\\Uludem\\Uludem Bilgi Teknolojileri.xml");

    public static final class Data {
        public String bayiKodu = "";
        public String kullanici = "";
        public String parola = "";
        public String varsayilanTarayici = "";
        public boolean beniHatirla = false;
    }

    public static Data load() {
        Data d = new Data();
        try {
            if (!XML_FILE.exists()) return d;
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(XML_FILE);
            Element root = doc.getDocumentElement();

            d.bayiKodu = getText(root, "BayiKodu");
            d.kullanici = getText(root, "Kullanici");
            d.parola = getText(root, "Parola");
            d.varsayilanTarayici = getText(root, "VarsayilanTarayici");
            d.beniHatirla = "true".equalsIgnoreCase(getText(root, "BeniHatirla"));
        } catch (Exception ex) { MessageWriteToFile.Write("RememberMe.load", ex); }
        return d;
    }

    public static void save (Data d) throws Exception {
        if (!XML_FILE.getParentFile().exists()) {
            XML_FILE.getParentFile().mkdirs();
        }
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = doc.createElement("UludemBilgiTeknolojileri");
        doc.appendChild(root);

        add(doc, root, "BayiKodu", d.bayiKodu);
        add(doc, root, "Kullanici", d.kullanici);
        add(doc, root, "Parola", d.parola);
        add(doc, root, "VarsayilanTarayici", d.varsayilanTarayici);
        add(doc, root, "BeniHatirla", String.valueOf(d.beniHatirla));

        var tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
        tf.transform(new DOMSource(doc), new StreamResult(XML_FILE));
    }

    public static void deleteIfExists () {
        if (XML_FILE.exists()) {
            XML_FILE.delete();
        }
    }

    private static void add (Document doc, Element root, String name, String value){
        Element el = doc.createElement(name);
        el.setTextContent(value != null ? value : "");
        root.appendChild(el);
    }

    private static String getText (Element root, String name){
        var nl = root.getElementsByTagName(name);
        if (nl.getLength()==0) return "";
        return nl.item(0).getTextContent().trim();
    }
}
