package model;

public class Parada {
    private int id;
    private String nombre;
    private double latitud;
    private double longitud;

    public Parada(int id, String nombre, double latitud, double longitud) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    @Override
    public String toString() {
        return nombre + " (" + latitud + ", " + longitud + ")";
    }
}
