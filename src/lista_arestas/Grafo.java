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

    /**
     * Adiciona o vértice a lista de vértices.
     * 
     * @param valor - Valor dado para inserção.
     * @return O vértice inserido.
     */
    public Vertice<T> adicionaVertice(T valor) {
        Vertice<T> novo = new Vertice<T>(valor);
        this.vertices.add(novo);
        return novo;
    }

    /**
     * Verifica se existe o vértice na lista de vértices.
     * 
     * @param valor - Valor do vértice procurado.
     * @return O vértice <b>v</b>, caso encontrado. Caso contrário, <b>null</b>.
     */
    private Vertice<T> obterVertice(T valor) {
        Vertice<T> v;
        for (Vertice<T> vertice : this.getVertices()) {
            v = vertice;
            if (v.getValor().equals(valor))
                return v;
        }
        return null;
    }

    /**
     * Adiciona uma aresta com vértice de <b>origem</b> a um vértice de
     * <b>destino</b>, com <b>peso</b> dado.
     * 
     * @param origem  - Valor do vértice de origem.
     * @param destino - Valor do vértice de destino.
     * @param peso    - Valor do peso da aresta.
     */
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

    /**
     * Função que verificar se há ciclos no grafo.
     * 
     * @return <b>true</b>, caso haja ciclo e <b>false</b> caso contrário.
     */
    public boolean temCiclo() {
        boolean[] visitados = new boolean[this.vertices.size()];
        boolean[] noCaminho = new boolean[this.vertices.size()];
        int index;
        // Para cada vértice na lista de vértices. Se ele não for visitado e a função
        // recursiva de ciclos retornar verdadeira, então tem ciclo e retorna true.
        for (Vertice<T> vertice : this.vertices) {
            index = this.vertices.indexOf(vertice);
            if (!visitados[index]) {
                if (temCicloRecursivo(vertice, visitados, noCaminho, index)) {
                    return true;
                }
            }
        }
        // Caso contrário, não tem ciclo e retorna false.
        return false;
    }

    private boolean temCicloRecursivo(Vertice<T> vertice, boolean[] visitados, boolean[] noCaminho, int index) {
        // Marca o vértice como visitado e pertencente a um caminho
        visitados[index] = true;
        noCaminho[index] = true;
        // Para cada aresta cujo vértice de origem seja o vértice chamado, se o vértice
        // de destino não for visitado e a função recursiva de ciclos retornar
        // verdadeira, então tem ciclo e retorna true. Além disso, se o vértice de
        // destino estiver marcado no caminho, tem ciclo e retorna true.
        for (Aresta<T> aresta : this.arestas) {
            if (aresta.getOrigem().equals(vertice)) {
                Vertice<T> verticeDestino = aresta.getDestino();
                int vizinhoIndex = this.vertices.indexOf(verticeDestino);
                if (!visitados[vizinhoIndex]) {
                    if (temCicloRecursivo(verticeDestino, visitados, noCaminho, vizinhoIndex)) {
                        return true;
                    }
                } else if (noCaminho[vizinhoIndex]) {
                    return true;
                }
            }
        }
        // Caso contrário, apaga o caminho e rotorna false.
        noCaminho[index] = false;
        return false;
    }

    /**
     * Função que realiza ordenação topológica do grafo.
     * 
     * @return Lista ordenada dos vértices do grafo.
     */
    public List<Vertice<T>> ordenacaoTopologica() {
        // Caso haja ciclo, retorne null.
        if (this.temCiclo()) {
            return null;
        }
        // Inicia uma lista V vazia e uma lista S com vertices sem aresta de entrada.
        List<Vertice<T>> resultado = new ArrayList<>();
        List<Vertice<T>> semArestaEntrada = this.verticesSemArestaEntrada();
        boolean[] visitados = new boolean[this.vertices.size()];
        // Para cada vértice da lista S que ainda não foi vizitado, aciona a função
        // recursiva da ordenação topológica.
        for (Vertice<T> vertice : semArestaEntrada) {
            int index = this.vertices.indexOf(vertice);
            if (!visitados[index]) {
                ordenacaoTopologicaRecursiva(vertice, visitados, resultado, index);
            }
        }
        // Depois de obter os vértices ordenados, inverte a lista.
        Collections.reverse(resultado);
        // Retorna o resultado ordenado invertido.
        return resultado;
    }

    private void ordenacaoTopologicaRecursiva(Vertice<T> vertice, boolean[] visitados, List<Vertice<T>> resultado,
            int index) {
        // Marca o vertice chamado como visitado.
        visitados[index] = true;
        // Para cada aresta cujo vértice de origem seja o vértice chamado, seja chamado
        // a função recursiva de ordenação topológica onde o vertice enviado seja o
        // vértice de destino que não foi visitado.
        for (Aresta<T> aresta : this.arestas) {
            if (aresta.getOrigem().equals(vertice)) {
                Vertice<T> verticeDestino = aresta.getDestino();
                int destinoIndex = this.vertices.indexOf(verticeDestino);
                if (!visitados[destinoIndex]) {
                    ordenacaoTopologicaRecursiva(verticeDestino, visitados, resultado, destinoIndex);
                }
            }
        }
        // Por fim, adiciona vértice chamado a lista de ordenação topológica.
        resultado.add(vertice);
    }

    /**
     * Essa função retorna uma lista dos vértices que não possuem aresta de entrada,
     * ou seja, não estão como destino.
     * 
     * @return Lista dos vértices.
     */
    private List<Vertice<T>> verticesSemArestaEntrada() {
        // Inicia uma lista de vértices vazia.
        List<Vertice<T>> vertices = new ArrayList<>();
        // Para cada vértice do grafo, se esse vértice não está presente como destino de
        // uma aresta ele é adicionado. Caso contrário, não será.
        boolean semAresta;
        for (Vertice<T> vertice : this.vertices) {
            semAresta = false;
            for (Aresta<T> aresta : this.arestas) {
                if (aresta.getDestino().equals(vertice)) {
                    semAresta = true;
                    break;
                }
            }
            if (!semAresta) {
                vertices.add(vertice);
            }
        }
        // Retorna lista de vértices com os vértices que atendem aos requisitos
        // aplicados.
        return vertices;
    }

}
