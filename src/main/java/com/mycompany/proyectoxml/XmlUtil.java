package com.mycompany.proyectoxml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.ByteArrayOutputStream;

public class XmlUtil {

    public static byte[] personaToXml(Persona persona) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element root = doc.createElement("persona");
        doc.appendChild(root);

        Element nombre = doc.createElement("nombre");
        nombre.appendChild(doc.createTextNode(persona.getNombre()));
        root.appendChild(nombre);

        Element altura = doc.createElement("altura");
        altura.appendChild(doc.createTextNode(String.valueOf(persona.getAltura())));
        root.appendChild(altura);

        Element peso = doc.createElement("peso");
        peso.appendChild(doc.createTextNode(String.valueOf(persona.getPeso())));
        root.appendChild(peso);

        // Convertir a bytes
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(doc), new StreamResult(outputStream));

        return outputStream.toByteArray();
    }
}
