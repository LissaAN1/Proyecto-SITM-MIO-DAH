package graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.imageio.ImageIO;

import model.Arco;
import model.Parada;

public class GraphDrawer {

    /**
     * Dibuja el grafo de UNA ruta (por ID) y lo exporta a un JPG.
     */
    public static void drawRouteGraph(
            String routeId,
            Map<String, List<Arco>> arcsByRoute,
            String outputFilePath
    ) throws IOException {

        List<Arco> arcs = arcsByRoute.get(routeId);
        if (arcs == null || arcs.isEmpty()) {
            System.out.println("No hay arcos para la ruta " + routeId);
            return;
        }

        String title = "Grafo de la ruta " + routeId;

        drawGraph(
                arcs,
                title,
                outputFilePath,
                1600,   // width
                900,    // height
                new Color(100, 149, 237), // color de arcos
                2f,     // grosor de arcos
                4       // radio de nodo
        );

        System.out.println("Imagen del grafo de la ruta " + routeId +
                " generada en: " + new File(outputFilePath).getAbsolutePath());
    }

    /**
     * Dibuja el grafo COMPLETO (todas las rutas) en una sola imagen JPG.
     */
    public static void drawFullGraph(
            Map<String, List<Arco>> arcsByRoute,
            String outputFilePath
    ) throws IOException {

        List<Arco> allArcs = new ArrayList<>();
        for (List<Arco> list : arcsByRoute.values()) {
            allArcs.addAll(list);
        }

        if (allArcs.isEmpty()) {
            System.out.println("No hay arcos para dibujar el grafo completo.");
            return;
        }

        String title = "Grafo completo - Todas las rutas";

        drawGraph(
                allArcs,
                title,
                outputFilePath,
                2000,   // width mayor para todo el sistema
                1200,   // height
                new Color(180, 180, 180), // color de arcos más suave
                1.2f,   // grosor más fino
                3       // radio de nodo un poco más pequeño
        );

        System.out.println("Imagen del grafo COMPLETO generada en: " +
                new File(outputFilePath).getAbsolutePath());
    }

    /**
     * Dibuja un grafo a partir de una lista de arcos y parámetros de estilo.
     */
    private static void drawGraph(
            List<Arco> arcs,
            String title,
            String outputFilePath,
            int width,
            int height,
            Color arcColor,
            float arcStrokeWidth,
            int nodeRadius
    ) throws IOException {

        int margin = 50;

        // 1. Crear imagen y Graphics2D
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 2. Calcular bounding box de lat/lon
        double minLat = Double.POSITIVE_INFINITY;
        double maxLat = Double.NEGATIVE_INFINITY;
        double minLon = Double.POSITIVE_INFINITY;
        double maxLon = Double.NEGATIVE_INFINITY;

        for (Arco arco : arcs) {
            Parada o = arco.getOrigin();
            Parada d = arco.getDestination();

            double latO = o.getDecimalLatit();
            double lonO = o.getDecimalLong();
            double latD = d.getDecimalLatit();
            double lonD = d.getDecimalLong();

            minLat = Math.min(minLat, Math.min(latO, latD));
            maxLat = Math.max(maxLat, Math.max(latO, latD));
            minLon = Math.min(minLon, Math.min(lonO, lonD));
            maxLon = Math.max(maxLon, Math.max(lonO, lonD));
        }

        if (minLat == maxLat) {
            maxLat = minLat + 0.0001;
        }
        if (minLon == maxLon) {
            maxLon = minLon + 0.0001;
        }

        final double finalMinLat = minLat;
        final double finalMaxLat = maxLat;
        final double finalMinLon = minLon;
        final double finalMaxLon = maxLon;

        Function<Double, Integer> lonToX = lon ->
                (int) (margin + (lon - finalMinLon) / (finalMaxLon - finalMinLon) * (width - 2 * margin));

        Function<Double, Integer> latToY = lat -> {
            double normalized = (lat - finalMinLat) / (finalMaxLat - finalMinLat);
            double inverted = 1.0 - normalized;
            return (int) (margin + inverted * (height - 2 * margin));
        };

        // 3. Dibujar arcos
        g2.setStroke(new BasicStroke(arcStrokeWidth));
        g2.setColor(arcColor);

        for (Arco arco : arcs) {
            Parada o = arco.getOrigin();
            Parada d = arco.getDestination();

            int x1 = lonToX.apply(o.getDecimalLong());
            int y1 = latToY.apply(o.getDecimalLatit());
            int x2 = lonToX.apply(d.getDecimalLong());
            int y2 = latToY.apply(d.getDecimalLatit());

            g2.drawLine(x1, y1, x2, y2);
        }

        // 4. Dibujar nodos
        g2.setColor(new Color(220, 20, 60));
        Set<String> drawnStops = new HashSet<>();

        for (Arco arco : arcs) {
            Parada[] puntos = { arco.getOrigin(), arco.getDestination() };
            for (Parada p : puntos) {
                if (!drawnStops.add(p.getStopId())) {
                    continue;
                }
                int x = lonToX.apply(p.getDecimalLong());
                int y = latToY.apply(p.getDecimalLatit());

                g2.fillOval(x - nodeRadius, y - nodeRadius, nodeRadius * 2, nodeRadius * 2);
            }
        }

        // 5. Título
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 20));
        g2.drawString(title, margin, margin - 10);

        g2.dispose();

        // 6. Guardar en JPG
        File outFile = new File(outputFilePath);
        ImageIO.write(image, "jpg", outFile);
    }
}
