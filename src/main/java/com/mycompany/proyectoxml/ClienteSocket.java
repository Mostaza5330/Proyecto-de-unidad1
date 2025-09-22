package com.mycompany.proyectoxml;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;

public class ClienteSocket {

    public static byte[] enviarPersona(byte[] datosXml) throws Exception {
        // Conectar al servidor (IP y puerto)
        Socket socket = new Socket("localhost", 5000);

        // Enviar datos
        OutputStream salida = socket.getOutputStream();
        salida.write(datosXml);
        socket.shutdownOutput();

        // Recibir respuesta
        InputStream entrada = socket.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] temp = new byte[1024];
        int bytesLeidos;
        while ((bytesLeidos = entrada.read(temp)) != -1) {
            buffer.write(temp, 0, bytesLeidos);
        }

        socket.close();
        return buffer.toByteArray(); // XML con IMC y resultado
    }
}
