import java.util.List;

public class Ruta {
    private String nombre;
    private List<Arco> arcos;

    public Ruta(String nombre, List<Arco> arcos) {
        this.nombre = nombre;
        this.arcos = arcos;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Arco> getArcos() {
        return arcos;
    }

    public void imprimirRuta() {
        System.out.println("Ruta: " + nombre);
        for (Arco arco : arcos) {
            System.out.println(arco);
        }
    }
}
