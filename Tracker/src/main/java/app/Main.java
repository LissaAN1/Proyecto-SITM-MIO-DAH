package app;

import java.util.Map;
import java.util.List;

import model.Ruta;
import model.Parada;
import model.ParadaDeRuta;
import model.Arco;

import io.DataLoader;

import service.ArcBuilder;
import service.ArcoPrinter;
import service.RouteGrouping;

public class Main {

    public static void main(String[] args) throws Exception {

        String linesPath = "data/lines-241.csv";
        String stopsPath = "data/stops-241.csv";
        String lineStopsPath = "data/linestops-241.csv";

        DataLoader loader = new DataLoader();

        Map<String, Ruta> routes = loader.loadLines(linesPath);
        Map<String, Parada> stops = loader.loadStops(stopsPath);
        List<ParadaDeRuta> lineStops = loader.loadLineStops(lineStopsPath);

        Map<String, Map<Integer, List<ParadaDeRuta>>> grouped =
                RouteGrouping.groupByRouteAndOrientation(lineStops);
        
        Map<String, List<Arco>> arcsByRoute =
                ArcBuilder.buildArcs(grouped, stops);

        ArcoPrinter.printArcsByRoute(arcsByRoute, routes);
    }
}