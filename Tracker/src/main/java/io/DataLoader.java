package io;

import model.Ruta;
import model.Parada;
import model.ParadaDeRuta;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DataLoader {

    // Lines.csv
    public Map<String, Ruta> loadLines(String filePath) throws IOException {
        Map<String, Ruta> routes = new HashMap<>();

        try (BufferedReader br = newBufferedReader(filePath)) {
            String line = br.readLine(); // encabezado

            while ((line = br.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] c = line.split(",");

                String lineId      = clean(c[0]); // 0: LINEID
                String shortName   = clean(c[2]); // 2: SHORTNAME
                String description = clean(c[3]); // 3: DESCRIPTION

                Ruta ruta = new Ruta(lineId, shortName, description);
                routes.put(lineId, ruta);
            }
        }

        return routes;
    }

    // Stops.csv
    public Map<String, Parada> loadStops(String filePath) throws IOException {
        Map<String, Parada> stops = new HashMap<>();

        try (BufferedReader br = newBufferedReader(filePath)) {
            String line = br.readLine(); // encabezado

            while ((line = br.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] c = line.split(",");

                String stopId    = clean(c[0]); // 0: STOPID
                String shortName = clean(c[2]); // 2: SHORTNAME

                double decimalLongitude = Double.parseDouble(clean(c[6])); // 6: DECIMALLONGITUDE
                double decimalLatitude  = Double.parseDouble(clean(c[7])); // 7: DECIMALLATITUDE

                Parada parada = new Parada(
                        stopId,
                        shortName,
                        decimalLatitude,
                        decimalLongitude
                );

                stops.put(stopId, parada);
            }
        }

        return stops;
    }

    // Linestops.csv
    public List<ParadaDeRuta> loadLineStops(String filePath) throws IOException {
        List<ParadaDeRuta> list = new ArrayList<>();

        try (BufferedReader br = newBufferedReader(filePath)) {
            String line = br.readLine(); // encabezado

            while ((line = br.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] c = line.split(",");

                String lineStopId = clean(c[0]); // 0: LINESTOPID
                int stopSequence  = Integer.parseInt(clean(c[1])); // 1: STOPSEQUENCE
                int orientation   = Integer.parseInt(clean(c[2])); // 2: ORIENTATION
                String lineId     = clean(c[3]); // 3: LINEID
                String stopId     = clean(c[4]); // 4: STOPID
                int lineVariant   = Integer.parseInt(clean(c[6])); // 6: LINEVARIANT

                ParadaDeRuta pdr = new ParadaDeRuta(
                        lineStopId,
                        stopSequence,
                        orientation,
                        lineId,
                        stopId,
                        lineVariant
                );
                list.add(pdr);
            }
        }

        return list;
    }

    // Helpers comunes

    private BufferedReader newBufferedReader(String filePath) throws IOException {
        return new BufferedReader(
            new InputStreamReader(
                new FileInputStream(filePath), StandardCharsets.UTF_8
            )
        );
    }

    private String clean(String raw) {
        if (raw == null) {
            return "";
        }
        // quita espacios y comillas dobles de los CSV
        return raw.trim().replace("\"", "");
    }
}