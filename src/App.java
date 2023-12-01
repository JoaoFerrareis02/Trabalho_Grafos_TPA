import lista_arestas.Grafo;
import lista_arestas.Vertice;

public class App {
    public static void main(String[] args) throws Exception {

        Grafo<Integer> grafo = new Grafo<>();

        grafo.adicionarAresta(7, 11, 1);
        grafo.adicionarAresta(7, 8, 1);
        grafo.adicionarAresta(5, 11, 1);
        grafo.adicionarAresta(3, 8, 1);
        grafo.adicionarAresta(11, 2, 1);
        grafo.adicionarAresta(11, 9, 1);
        grafo.adicionarAresta(11, 10, 1);
        grafo.adicionarAresta(8, 9, 1);
        grafo.adicionarAresta(3, 10, 1);
        //grafo.adicionarAresta(3, 7, 1);
        //grafo.adicionarAresta(3, 0, 1);

        //grafo.buscaProfundidade();

        //System.out.println(grafo.temCiclo());

        for (Vertice<Integer> vertice : grafo.ordenacaoTopologica()) {
            System.out.print(vertice.getValor() + " - ");
        }

    }
}
