package model;

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

    public static double calcularDistanciaKm(Parada p1, Parada p2) {
        double result=2;
        return result;
    }


    @Override
    public String toString() {
        return shortName + " (" + stopId + ")";
    }
}