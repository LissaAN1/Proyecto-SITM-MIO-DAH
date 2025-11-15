package service;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import model.Ruta;
import model.Arco;

public class ArcoPrinter {

    public static void printArcsByRoute(
            Map<String, List<Arco>> arcsByRoute,
            Map<String, Ruta> routes
    ) {
        for (Map.Entry<String, List<Arco>> entry : arcsByRoute.entrySet()) {

            String routeId = entry.getKey();
            List<Arco> arcs = entry.getValue();

            Ruta ruta = routes.get(routeId);

            System.out.println("=======================================");
            if (ruta != null) {
                System.out.println("Ruta " + ruta.getShortName() + " (" + ruta.getLineId() + ")");
                System.out.println("Descripción: " + ruta.getDescription());
            } else {
                System.out.println("Ruta con LINEID=" + routeId);
            }

            if (arcs == null || arcs.isEmpty()) {
                System.out.println("  (Sin arcos construidos para esta ruta)");
                continue;
            }

            Map<Integer, List<Arco>> byOrientation = new HashMap<>();
            for (Arco a : arcs) {
                byOrientation
                        .computeIfAbsent(a.getOrientation(), k -> new ArrayList<>())
                        .add(a);
            }

            // Imprimir primero IDA (0), luego REGRESO (1)
            for (int orientation : List.of(0, 1)) {
                List<Arco> list = byOrientation.get(orientation);
                if (list == null || list.isEmpty()) {
                    continue;
                }

                String sentido = (orientation == 0) ? "IDA" : "REGRESO";
                System.out.println("  Sentido: " + sentido);

                for (Arco a : list) {
                    System.out.println("    " + a);
                }
                System.out.println();
            }
        }
    }
}