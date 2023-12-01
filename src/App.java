import lista_arestas.Grafo;
import lista_arestas.Vertice;

public class App {
    public static void main(String[] args) throws Exception {

        Grafo<Integer> grafo = new Grafo<>();

        grafo.adicionarAresta(1, 4, 1);
        grafo.adicionarAresta(1, 5, 1);
        grafo.adicionarAresta(2, 5, 1);
        grafo.adicionarAresta(4, 5, 1);
        grafo.adicionarAresta(4, 7, 1);
        grafo.adicionarAresta(6, 7, 1);
        grafo.adicionarAresta(6, 8, 1);
        grafo.adicionarAresta(8, 9, 1);
        grafo.adicionarAresta(7, 9, 1);
        // grafo.adicionarAresta(9, 1, 1);
        grafo.adicionaVertice(3);

        // grafo.buscaProfundidade();

        System.out.printf("\nTem ciclo: %s\n\n", (grafo.temCiclo()) ? "Sim" : "Não");

        try {
            for (Vertice<Integer> vertice : grafo.ordenacaoTopologica()) {
                System.out.print(vertice.getValor() + " ");
            }
        } catch (NullPointerException e) {
            System.out.println("Grafo cíclico ou vazio.");
        }

    }
}
