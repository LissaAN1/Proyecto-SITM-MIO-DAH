package service;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;

import model.ParadaDeRuta;

public class RouteGrouping {
    
    public static Map<String, Map<Integer, Map<Integer, List<ParadaDeRuta>>>>
    groupByRouteOrientationVariant(List<ParadaDeRuta> lineStops) {

        Map<String, Map<Integer, Map<Integer, List<ParadaDeRuta>>>> result = new HashMap<>();

        for (ParadaDeRuta pdr : lineStops) {
            String routeId = pdr.getLineId();
            int orientation = pdr.getOrientation();
            int variant = pdr.getLineVariant();

            Map<Integer, Map<Integer, List<ParadaDeRuta>>> byOrientation =
                    result.computeIfAbsent(routeId, k -> new HashMap<>());

            Map<Integer, List<ParadaDeRuta>> byVariant =
                    byOrientation.computeIfAbsent(orientation, k -> new HashMap<>());

            List<ParadaDeRuta> list =
                    byVariant.computeIfAbsent(variant, k -> new ArrayList<>());

            list.add(pdr);
        }
        
        for (Map<Integer, Map<Integer, List<ParadaDeRuta>>> byOrientation : result.values()) {
            for (Map<Integer, List<ParadaDeRuta>> byVariant : byOrientation.values()) {
                for (List<ParadaDeRuta> list : byVariant.values()) {
                    list.sort(Comparator.comparingInt(ParadaDeRuta::getStopSequence));
                }
            }
        }

        return result;
    }
}