package com.mycompany.proyectoxml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class XmlParser {

    public static void leerRespuesta(byte[] xmlRespuesta) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(new java.io.ByteArrayInputStream(xmlRespuesta));

        String nombre = doc.getElementsByTagName("nombre").item(0).getTextContent();
        String altura = doc.getElementsByTagName("altura").item(0).getTextContent();
        String peso = doc.getElementsByTagName("peso").item(0).getTextContent();
        String imc = doc.getElementsByTagName("imc").item(0).getTextContent();
        String resultado = doc.getElementsByTagName("resultado").item(0).getTextContent();

        System.out.println("Resultado del servidor:");
        System.out.println("Nombre: " + nombre);
        System.out.println("Altura: " + altura);
        System.out.println("Peso: " + peso);
        System.out.println("IMC: " + imc);
        System.out.println("Estado: " + resultado);
    }
}
