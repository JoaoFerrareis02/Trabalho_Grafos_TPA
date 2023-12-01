package lista_arestas;

import java.util.ArrayList;
import java.util.List;

public class Grafo<T> {

    private List<Vertice<T>> vertices = new ArrayList<>();
    private List<Aresta<T>> arestas = new ArrayList<>();

    public Grafo() {
    }

    public List<Vertice<T>> getVertices() {
        return vertices;
    }

    public List<Aresta<T>> getArestas() {
        return arestas;
    }

    public Vertice<T> adicionaVertice(T valor) {
        Vertice<T> novo = new Vertice<T>(valor);
        this.vertices.add(novo);
        return novo;
    }

    private Vertice<T> obterVertice(T valor) {
        Vertice<T> v;
        for (Vertice<T> vertice : this.getVertices()) {
            v = vertice;
            if (v.getValor().equals(valor))
                return v;
        }
        return null;
    }

    public void adicionarAresta(T origem, T destino, float peso) {
        Vertice<T> verticeOrigem, verticeDestino;
        Aresta<T> novaAresta;
        verticeOrigem = obterVertice(origem);
        if (verticeOrigem == null) {
            verticeOrigem = adicionaVertice(origem);
        }
        verticeDestino = obterVertice(destino);
        if (verticeDestino == null) {
            verticeDestino = adicionaVertice(destino);
        }
        novaAresta = new Aresta<>(verticeOrigem, verticeDestino, peso);
        this.arestas.add(novaAresta);
    }

    /*--- Função para fazer busca por profundidade ---*/

    public void buscaProfundidade() {
        boolean[] visitados = new boolean[vertices.size()];
        for (Vertice<T> vertice : vertices) {
            int index = vertices.indexOf(vertice);
            if (!visitados[index]) {
                buscaProfundidadeRecursiva(vertice, visitados);
            }
        }
    }

    private void buscaProfundidadeRecursiva(Vertice<T> vertice, boolean[] visitados) {
        visitados[vertices.indexOf(vertice)] = true;
        System.out.println(vertice.getValor());
        for (Aresta<T> aresta : arestas) {
            if (aresta.getOrigem().equals(vertice) && !visitados[vertices.indexOf(aresta.getDestino())]) {
                buscaProfundidadeRecursiva(aresta.getDestino(), visitados);
            }
        }
    }

    /*--- Função para descobrir se tem ciclo ---*/

    public boolean temCiclo() {
        boolean[] visitados = new boolean[vertices.size()];
        boolean[] noCaminho = new boolean[vertices.size()];
        for (Vertice<T> vertice : vertices) {
            int index = vertices.indexOf(vertice);
            if (!visitados[index]) {
                if (temCicloRecursivo(vertice, visitados, noCaminho))
                    return true;
            }
        }
        return false;
    }

    private boolean temCicloRecursivo(Vertice<T> vertice, boolean[] visitados, boolean[] noCaminho) {
        int index = vertices.indexOf(vertice);
        visitados[index] = true;
        noCaminho[index] = true;
        for (Aresta<T> aresta : arestas) {
            if (aresta.getOrigem().equals(vertice)) {
                Vertice<T> vizinho = aresta.getDestino();
                int vizinhoIndex = vertices.indexOf(vizinho);
                if (!visitados[vizinhoIndex]) {
                    if (temCicloRecursivo(vizinho, visitados, noCaminho)) {
                        return true;
                    }
                } else if (noCaminho[vizinhoIndex]) {
                    return true;
                }
            }
        }
        noCaminho[index] = false;
        return false;
    }

    /*--- Função para fazer a ordenação topológica ---*/

    public List<Vertice<T>> ordenacaoTopologica(){
        if (temCiclo()) {
            return null;
        }
        List<Vertice<T>> resultado = new ArrayList<>();
        boolean[] visitados = new boolean[vertices.size()];
        for (Vertice<T> vertice : vertices) {
            int index = vertices.indexOf(vertice);
            if (!visitados[index]) {
                ordenacaoTopologicaRecursiva(vertice, visitados, resultado);
            }
        }
        return resultado;
    }

    private void ordenacaoTopologicaRecursiva(Vertice<T> vertice, boolean[] visitados, List<Vertice<T>> resultado) {
        visitados[vertices.indexOf(vertice)] = true;
        for (Aresta<T> aresta : arestas) {
            if (aresta.getOrigem().equals(vertice) && !visitados[vertices.indexOf(aresta.getDestino())]) {
                ordenacaoTopologicaRecursiva(aresta.getDestino(), visitados, resultado);
            }
        }
        resultado.add(vertice);
    }

}
