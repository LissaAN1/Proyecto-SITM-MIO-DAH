package app;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Scanner;

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

        Map<String, Map<Integer, Map<Integer, List<ParadaDeRuta>>>> grouped =
                RouteGrouping.groupByRouteOrientationVariant(lineStops);
        
        Map<String, List<Arco>> arcsByRoute =
                ArcBuilder.buildArcs(grouped, stops);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=================================================");
            System.out.println("   Analizador de arcos SITM-MIO");
            System.out.println("=================================================");
            System.out.println("1) Mostrar TODOS los arcos");
            System.out.println("2) Mostrar arcos de UNA ruta específica");
            System.out.println("3) Salir");
            System.out.print("Seleccione una opción (1-3): ");

            String option = scanner.nextLine().trim();

            switch (option) {

                case "1" -> {
                    System.out.println("\nMostrando TODOS los arcos de todas las rutas...\n");
                    ArcoPrinter.printArcsByRoute(arcsByRoute, routes);
                }

                case "2" -> {
                    System.out.print("\nIngrese el nombre de la ruta (ej: T31, A47): ");
                    String shortNameInput = scanner.nextLine().trim();

                    String selectedRouteId = null;

                    for (Ruta ruta : routes.values()) {
                        if (ruta.getShortName().equalsIgnoreCase(shortNameInput)) {
                            selectedRouteId = ruta.getLineId();
                            break;
                        }
                    }

                    if (selectedRouteId == null) {
                        System.out.println("No se encontró ninguna ruta con nombre: " + shortNameInput);
                        break;
                    }

                    List<Arco> arcsForRoute = arcsByRoute.get(selectedRouteId);
                    if (arcsForRoute == null || arcsForRoute.isEmpty()) {
                        System.out.println("No hay arcos construidos para la ruta " +
                                shortNameInput + " (LINEID=" + selectedRouteId + ").");
                        break;
                    }

                    Map<String, List<Arco>> singleRouteMap = new HashMap<>();
                    singleRouteMap.put(selectedRouteId, arcsForRoute);

                    System.out.println("\nMostrando arcos de la ruta . . .");

                    ArcoPrinter.printArcsByRoute(singleRouteMap, routes);
                }

                case "3" -> {
                    System.out.println("Saliendo del programa...");
                    scanner.close();
                    return;
                }

                default -> {
                    System.out.println("Opción inválida. Intente nuevamente.\n");
                }
            }
        }
    }
}