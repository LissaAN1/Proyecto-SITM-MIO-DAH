package model;
public class Arco {
    private final String rutaId;
    private final int orientation;
    private final Parada origen;
    private final Parada destino;
    private final double distanciaKm;

    public Arco(String rutaId, int orientation, Parada origen, Parada destino, double distanciaKm) {
        this.rutaId = rutaId;
        this.orientation = orientation;
        this.origen = origen;
        this.destino = destino;
        this.distanciaKm = distanciaKm;
    }

    @Override
    public String toString() {
        String sentido = (orientation == 0) ? "IDA" : "REGRESO";
        return "Ruta: " + rutaId + " (Sentido " + sentido + ") | " +
                origen.getShortName() + " -> " + destino.getShortName() +
                " | Distancia: " + String.format("%.3f", distanciaKm) + " km";
    }
}