package lista_arestas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grafo<T> {

    private List<Vertice<T>> vertices = new ArrayList<>();
    private List<Aresta<T>> arestas = new ArrayList<>();

    public Grafo() {
    }

    public List<Vertice<T>> getVertices() {
        return this.vertices;
    }

    public List<Aresta<T>> getArestas() {
        return this.arestas;
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

    public void buscaProfundidade() {
        boolean[] visitados = new boolean[vertices.size()];
        int index;
        for (Vertice<T> vertice : this.vertices) {
            index = this.vertices.indexOf(vertice);
            buscaProfundidadeRecursiva(vertice, visitados, index);
        }
    }

    private void buscaProfundidadeRecursiva(Vertice<T> vertice, boolean[] visitados, int index) {
        if (!visitados[index]) {
            visitados[index] = true;
            int indexDestino;
            System.out.println(vertice.getValor());
            for (Vertice<T> verticeDestino : this.verticesDestino(vertice)) {
                indexDestino = this.vertices.indexOf(verticeDestino);
                buscaProfundidadeRecursiva(verticeDestino, visitados, indexDestino);
            }
        }
    }

    public boolean temCiclo() {
        boolean[] visitados = new boolean[this.vertices.size()];
        boolean[] noCaminho = new boolean[this.vertices.size()];
        int index;
        for (Vertice<T> vertice : this.vertices) {
            index = this.vertices.indexOf(vertice);
            if (!visitados[index]) {
                if (temCicloRecursivo(vertice, visitados, noCaminho, index)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean temCicloRecursivo(Vertice<T> vertice, boolean[] visitados, boolean[] noCaminho, int index) {
        visitados[index] = true;
        noCaminho[index] = true;
        for (Vertice<T> verticeDestino : this.verticesDestino(vertice)) {
            int vizinhoIndex = this.vertices.indexOf(verticeDestino);
            if (!visitados[vizinhoIndex]) {
                if (temCicloRecursivo(verticeDestino, visitados, noCaminho, vizinhoIndex)) {
                    return true;
                }
            } else if (noCaminho[vizinhoIndex]) {
                return true;
            }
        }
        noCaminho[index] = false;
        return false;
    }

    public List<Vertice<T>> ordenacaoTopologica() {
        if (this.temCiclo()) {
            return null;
        }
        List<Vertice<T>> resultado = new ArrayList<>();
        List<Vertice<T>> semArestaEntrada = this.verticesSemArestaEntrada();
        boolean[] visitados = new boolean[this.vertices.size()];
        for (Vertice<T> vertice : semArestaEntrada) {
            int index = this.vertices.indexOf(vertice);
            ordenacaoTopologicaRecursiva(vertice, visitados, resultado, index);
        }
        Collections.reverse(resultado);
        return resultado;
    }

    private void ordenacaoTopologicaRecursiva(Vertice<T> vertice, boolean[] visitados, List<Vertice<T>> resultado, int index) {
        if (!visitados[index]) {
            visitados[index] = true;
            for (Vertice<T> verticeDestino : this.verticesDestino(vertice)) {
                int destinoIndex = this.vertices.indexOf(verticeDestino);
                ordenacaoTopologicaRecursiva(verticeDestino, visitados, resultado, destinoIndex);
            }
            resultado.add(vertice);
        }
    }

    private List<Vertice<T>> verticesDestino(Vertice<T> v) {
        List<Vertice<T>> vertices = new ArrayList<>();
        for (Aresta<T> aresta : this.arestas) {
            if (aresta.getOrigem().equals(v)) {
                vertices.add(aresta.getDestino());
            }
        }
        return vertices;
    }

    private List<Vertice<T>> verticesSemArestaEntrada(){
        List<Vertice<T>> vertices = new ArrayList<>();
        boolean semAresta;
        for (Vertice<T> vertice : this.vertices) {
            semAresta = false;
            for (Aresta<T> aresta : this.arestas) {
                if (aresta.getDestino().equals(vertice)) {
                    semAresta = true;
                    break;
                }
            }
            if(!semAresta){
                vertices.add(vertice);
            }
        }
        return vertices;
    }

}
