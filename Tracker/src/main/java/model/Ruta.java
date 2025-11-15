package model;

// Modelo de datos para Lines.csv

public class Ruta {
    
    private String lineId;
    private String shortName;
    private String description;
    
    public Ruta(String lineId, String shortName, String description) {
        this.lineId = lineId;
        this.shortName = shortName;
        this.description = description;
    }

    public String getLineId() { return lineId; }
    public String getShortName() { return shortName; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return "[" + shortName + "]";
    }

}