package network;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TrackerServer {
    private final int port;

    public TrackerServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        System.out.println("SITM-TRACKER: Servidor iniciando en el puerto " + port + "...");

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {
                System.out.println("SITM-TRACKER: Esperando nueva conexión del BUS...");

                Socket busSocket = serverSocket.accept();
                System.out.println("SITM-TRACKER: Conexión aceptada desde " + busSocket.getInetAddress().getHostAddress());

                new Thread(new BusConnectionHandler(busSocket)).start();
            }
        }
    }

    // Clase interna para manejar cada conexión de BUS
    private class BusConnectionHandler implements Runnable {
        private final Socket socket;

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
                // Procesa múltiples datagramas de una misma conexión
                while ((datagramaJson = in.readLine()) != null) {
                    // **Punto de recepción del datagrama (E11)**
                    System.out.println("TRACKER RECIBIDO: " + datagramaJson);

                    // Aquí se integraría la lógica de procesamiento (E15, E18)
                    // DataProcessor.procesarDatosOperativos(datagramaJson);

                    // Responde al BUS con un ACK
                    out.println("ACK: Datagrama procesado.");
                }

            } catch (IOException e) {
                System.err.println("Error en la conexión con el BUS: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                    System.out.println("SITM-TRACKER: Conexión con BUS cerrada.");
                } catch (IOException e) {
                    // Ignorar
                }
            }
        }
    }
}