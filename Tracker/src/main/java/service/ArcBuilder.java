package service;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import model.Arco;
import model.Parada;
import model.ParadaDeRuta;

public class ArcBuilder {
    
    public static Map<String, List<Arco>> buildArcs(
            Map<String, Map<Integer, List<ParadaDeRuta>>> grouped,
            Map<String, Parada> stops
    ) {
        Map<String, List<Arco>> arcsByRoute = new HashMap<>();

        for (Map.Entry<String, Map<Integer, List<ParadaDeRuta>>> routeEntry : grouped.entrySet()) {
            String routeId = routeEntry.getKey();
            Map<Integer, List<ParadaDeRuta>> byOrientation = routeEntry.getValue();

            List<Arco> arcsForRoute = new ArrayList<>();

            for (Map.Entry<Integer, List<ParadaDeRuta>> oriEntry : byOrientation.entrySet()) {
                int orientation = oriEntry.getKey();
                List<ParadaDeRuta> seq = oriEntry.getValue();

                for (int i = 0; i < seq.size() - 1; i++) {
                    ParadaDeRuta current = seq.get(i);
                    ParadaDeRuta next = seq.get(i + 1);

                    Parada origin = stops.get(current.getStopId());
                    Parada destination = stops.get(next.getStopId());

                    if (origin == null || destination == null) {
                        continue;
                    }

                    double distanceKm = Parada.calculateDistanceKm(origin, destination);

                    Arco arco = new Arco(
                            routeId,
                            orientation,
                            origin,
                            destination,
                            distanceKm
                    );
                    arcsForRoute.add(arco);
                }
            }

            arcsByRoute.put(routeId, arcsForRoute);
        }

        return arcsByRoute;
    }
}