import java.util.*;

public class Grafo {
    private Map<Integer, Parada> paradas = new HashMap<>();
    private Map<String, Ruta> rutas = new HashMap<>();

    public void agregarParada(Parada parada) {
        paradas.put(parada.getId(), parada);
    }

    public void agregarRuta(Ruta ruta) {
        rutas.put(ruta.getNombre(), ruta);
    }

    public Parada obtenerParada(int id) {
        return paradas.get(id);
    }

    public void imprimirRutas() {
        for (Ruta ruta : rutas.values()) {
            ruta.imprimirRuta();
        }
    }
}
