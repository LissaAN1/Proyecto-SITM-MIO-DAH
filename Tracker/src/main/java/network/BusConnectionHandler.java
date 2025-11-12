package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BusConnectionHandler implements Runnable {
    private final Socket socket;

    // El constructor recibe el Socket que representa la conexión con el BUS
    public BusConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String datagramaJson;
            // Lee datagramas línea por línea hasta que el BUS cierre la conexión
            while ((datagramaJson = in.readLine()) != null) {

                System.out.println("TRACKER RECIBIDO: " + datagramaJson);

                // Aquí se llamaría a una clase que procese los datos operativos

                // Envía el ACK al BUS (Acuse de Recibo)
                out.println("ACK: Datagrama procesado.");
            }

        } catch (IOException e) {
            // Maneja errores de I/O específicos de esta conexión
            System.err.println("Error en la conexión con el BUS " + socket.getInetAddress().getHostAddress() + ": " + e.getMessage());
        } finally {
            try {
                // Cierra el socket al finalizar el manejo
                socket.close();
                System.out.println("SITM-TRACKER: Conexión con BUS cerrada.");
            } catch (IOException e) {
                // Ignorar si hay error al cerrar
            }
        }
    }
}