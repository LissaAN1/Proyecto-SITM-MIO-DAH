package model;

// Arco entre dos paradas de una ruta

public class Arco {
    
    private final String routeId;
    private final int orientation;
    private final Parada origin;
    private final Parada destination;
    private final double distanciaKm;

    public Arco(String routeId, int orientation, Parada origin, Parada destination, double distanciaKm) {
        this.routeId = routeId;
        this.orientation = orientation;
        this.origin = origin;
        this.destination = destination;
        this.distanciaKm = distanciaKm;
    }

    public String getRouteId() { return routeId; }
    public int getOrientation() { return orientation; }
    public Parada getOrigin() { return origin; }
    public Parada getDestination() { return destination; }
    public double getDistanceKm() { return distanciaKm; }

    @Override
    public String toString() {
        String sentido = (orientation == 0) ? "IDA" : "REGRESO";
        return "Ruta: " + routeId + " (Sentido " + sentido + ") | " +
                origin.getShortName() + " -> " + destination.getShortName() +
                " | Distancia: " + String.format("%.3f", distanciaKm) + " km";
    }

}