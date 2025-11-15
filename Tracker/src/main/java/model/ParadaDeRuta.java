package model;

// Modelo de datos para Linestops.csv

public class ParadaDeRuta {
    
    private final String lineStopId;
    private final int stopSequence;
    private final int orientation; // 0 = ida, 1 = regreso
    private final String lineId;
    private final String stopId;

    public ParadaDeRuta(String lineStopId, int stopSequence, int orientation, String lineId, String stopId) {
        this.lineStopId = lineStopId;
        this.stopSequence = stopSequence;
        this.orientation = orientation;
        this.lineId = lineId;
        this.stopId = stopId;
    }

    public String getLineStopId() { return lineStopId; }
    public int getStopSequence() { return stopSequence; }
    public int getOrientation() { return orientation; }
    public String getLineId() { return lineId; }
    public String getStopId() { return stopId; }

}