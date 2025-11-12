package model;

public class Arco {
    private Parada inicio;
    private Parada fin;

    public Arco(Parada inicio, Parada fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    public Parada getInicio() {
        return inicio;
    }

    public Parada getFin() {
        return fin;
    }

    @Override
    public String toString() {
        return inicio.getNombre() + " -> " + fin.getNombre();
    }
}
