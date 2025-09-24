/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoxml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Carlo
 */
public class Servidor {
    
public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Servidor iniciado en puerto 5000. Esperando clientes...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado.");

                // Leer XML recibido
                InputStream entrada = socket.getInputStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                byte[] temp = new byte[1024];
                int bytesLeidos;
                while ((bytesLeidos = entrada.read(temp)) != -1) {
                    buffer.write(temp, 0, bytesLeidos);
                }

                byte[] xmlBytes = buffer.toByteArray();

                // Parsear XML a objeto Persona
                Persona persona = parseXmlToPersona(xmlBytes);

                // Calcular IMC y resultado
                double imc = persona.getPeso() / Math.pow(persona.getAltura(), 2);
                String resultado;
                if (imc < 18.5) {
                    resultado = "Bajo peso";
                } else if (imc < 25) {
                    resultado = "Normal";
                } else if (imc < 30) {
                    resultado = "Sobrepeso";
                } else {
                    resultado = "Obesidad";
                }

                System.out.println("Procesando: " + persona.getNombre() + " -> IMC: " + imc + " (" + resultado + ")");

                // Generar XML de respuesta
                byte[] respuestaXml = generarRespuestaXml(persona, imc, resultado);

                // Enviar XML de respuesta
                OutputStream salida = socket.getOutputStream();
                salida.write(respuestaXml);
                socket.close();
                System.out.println("Respuesta enviada al cliente.\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Convierte XML recibido en un objeto Persona
    private static Persona parseXmlToPersona(byte[] xmlBytes) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xmlBytes));

        String nombre = doc.getElementsByTagName("nombre").item(0).getTextContent();
        double altura = Double.parseDouble(doc.getElementsByTagName("altura").item(0).getTextContent());
        double peso = Double.parseDouble(doc.getElementsByTagName("peso").item(0).getTextContent());

        return new Persona(nombre, altura, peso);
    }

    // Genera XML con IMC y resultado
    private static byte[] generarRespuestaXml(Persona persona, double imc, String resultado) throws Exception {
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

        Element imcElem = doc.createElement("imc");
        imcElem.appendChild(doc.createTextNode(String.format("%.2f", imc)));
        root.appendChild(imcElem);

        Element resElem = doc.createElement("resultado");
        resElem.appendChild(doc.createTextNode(resultado));
        root.appendChild(resElem);

        // Convertir a bytes
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(doc), new StreamResult(outputStream));

        return outputStream.toByteArray();
    }


}
