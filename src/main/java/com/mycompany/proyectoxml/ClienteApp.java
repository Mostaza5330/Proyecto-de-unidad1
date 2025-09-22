package com.mycompany.proyectoxml;

import java.util.Scanner;

public class ClienteApp {
    public static void main(String[] args) {
        try {
            // Capturar datos
            Scanner sc = new Scanner(System.in);
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Altura (m): ");
            double altura = sc.nextDouble();
            System.out.print("Peso (kg): ");
            double peso = sc.nextDouble();

            Persona persona = new Persona(nombre, altura, peso);

            // Convertir a XML en bytes
            byte[] xmlBytes = XmlUtil.personaToXml(persona);

            // Enviar al servidor y recibir respuesta
            byte[] respuesta = ClienteSocket.enviarPersona(xmlBytes);

            // Procesar XML de respuesta
            XmlParser.leerRespuesta(respuesta);

            sc.close(); // Close the scanner

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
