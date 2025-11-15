package model;

// Modelo de datos para Stops.csv

public class Parada {

    private final String stopId;
    private final String shortName;
    private final double decimalLatit;
    private final double decimalLong;

    public Parada(String stopId, String shortName, double decimalLatit, double decimalLong) {
        this.stopId = stopId;
        this.shortName = shortName;
        this.decimalLatit = decimalLatit;
        this.decimalLong = decimalLong;
    }

    public String getStopId() { return stopId; }
    public String getShortName() { return shortName; }
    public double getDecimalLatit() { return decimalLatit; }
    public double getDecimalLong() { return decimalLong; }

    public static double calculateDistanceKm(Parada p1, Parada p2) {
        double earthRadius = 6371.0; //Radio de la Tierra (km)

        double lat1Rad = Math.toRadians(p1.getDecimalLatit());
        double lat2Rad = Math.toRadians(p2.getDecimalLatit());

        double dLat = Math.toRadians(p2.getDecimalLatit() - p1.getDecimalLatit());
        double dLon = Math.toRadians(p2.getDecimalLong() - p1.getDecimalLong());

        // Fórmula de Haversine:
        double hav = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
               Math.cos(lat1Rad) * Math.cos(lat2Rad) *
               Math.sin(dLon / 2) * Math.sin(dLon / 2);

        // Ángulo central entre los dos puntos en superficie terrestre:
        double ang = 2 * Math.atan2(Math.sqrt(hav), Math.sqrt(1 - hav));

        return earthRadius * ang;
    }

    @Override
    public String toString() {
        return shortName + " (" + stopId + ")";
    }

}