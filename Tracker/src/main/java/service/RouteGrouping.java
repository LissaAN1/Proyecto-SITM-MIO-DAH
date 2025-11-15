package service;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;

import model.ParadaDeRuta;

public class RouteGrouping {
    
    public static Map<String, Map<Integer, List<ParadaDeRuta>>>
    groupByRouteAndOrientation(List<ParadaDeRuta> lineStops) {

        Map<String, Map<Integer, List<ParadaDeRuta>>> result = new HashMap<>();

        for (ParadaDeRuta pdr : lineStops) {
            String routeId = pdr.getLineId();
            int orientation = pdr.getOrientation();

            Map<Integer, List<ParadaDeRuta>> byOrientation =
                    result.computeIfAbsent(routeId, k -> new HashMap<>());

            List<ParadaDeRuta> list =
                    byOrientation.computeIfAbsent(orientation, k -> new ArrayList<>());

            list.add(pdr);
        }
        
        for (Map<Integer, List<ParadaDeRuta>> byOrientation : result.values()) {
            for (List<ParadaDeRuta> list : byOrientation.values()) {
                list.sort(Comparator.comparingInt(ParadaDeRuta::getStopSequence));
            }
        }

        return result;
    }
}