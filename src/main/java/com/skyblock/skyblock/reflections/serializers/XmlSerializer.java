package com.skyblock.skyblock.reflections.serializers;

import org.dom4j.Element;
import org.dom4j.DocumentFactory;
import java.io.OutputStream;
import org.dom4j.io.XMLWriter;
import org.dom4j.io.OutputFormat;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Set;
import org.dom4j.Document;
import com.skyblock.skyblock.reflections.ReflectionsException;
import com.skyblock.skyblock.reflections.Store;
import java.util.stream.Collectors;
import org.dom4j.Node;
import java.util.Map;
import org.dom4j.io.SAXReader;
import com.skyblock.skyblock.reflections.Reflections;
import java.io.InputStream;

public class XmlSerializer implements Serializer
{
    @Override
    public Reflections read(final InputStream inputStream) {
        try {
            final Document document = new SAXReader().read(inputStream);
            final Map<String, Map<String, Set<String>>> storeMap = (Map<String, Map<String, Set<String>>>)document.getRootElement().elements().stream().collect(Collectors.toMap(Node::getName, index -> (Map)index.elements().stream().collect(Collectors.toMap(entry -> entry.element("key").getText(), entry -> (Set)entry.element("values").elements().stream().map(Element::getText).collect(Collectors.toSet())))));
            return new Reflections(new Store(storeMap));
        }
        catch (final Exception e) {
            throw new ReflectionsException("could not read.", (Throwable)e);
        }
    }
    
    @Override
    public File save(final Reflections reflections, final String filename) {
        final File file = Serializer.prepareFile(filename);
        try (final FileOutputStream out = new FileOutputStream(file)) {
            new XMLWriter((OutputStream)out, OutputFormat.createPrettyPrint()).write(this.createDocument(reflections.getStore()));
        }
        catch (final Exception e) {
            throw new ReflectionsException("could not save to file " + filename, (Throwable)e);
        }
        return file;
    }
    
    private Document createDocument(final Store store) {
        final Document document = DocumentFactory.getInstance().createDocument();
        final Element root = document.addElement("Reflections");
        store.forEach((index, map) -> {
            final Element indexElement = root.addElement(index);
            map.forEach((key, values) -> {
                final Element entryElement = indexElement.addElement("entry");
                entryElement.addElement("key").setText(key);
                final Element valuesElement = entryElement.addElement("values");
                values.forEach(value -> valuesElement.addElement("value").setText(value));
            });
        });
        return document;
    }
}
