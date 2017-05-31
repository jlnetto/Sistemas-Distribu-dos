namespace java models

exception KeyNotFound
{
}

struct Vertex {
	1:i32 nome,
	2:i32 cor,
	3:string descricao,
	4:double peso
}

struct Edge {
	1:i32 v1,
	2:i32 v2,
	3:double peso,
	4:i32 flag,
	5:string descricao
}

struct Graph {
    1:list<Vertice> V,
    2:list<Aresta> A
}

service Operations {
    void loadGraph(1:string caminho),
    void saveGraph(1:string caminho),
    bool createVertex(1:i32 nome,2:i32 cor,3:string descricao,4:double peso),
    bool createEdge(1:i32 v1,2:i32 v2,3:double peso,4:i32 flag,5:string descricao),
    bool deleteVertex(1:i32 nome),
    bool deleteEdge(1:i32 v1,2:i32 v2),
    bool updateVertex(1:i32 nomeUp,2:Vertice V),
    bool updateEdge(1:i32 nomeV1, 2:i32 nomeV2, 3:Aresta A),
    bool updateGraph(1:list<Vertice> V,2:list<Aresta> A),
    Vertex getVertex(1:i32 nome),
    Edge getEdge(1:i32 v1,2:i32 v2),
    string showGraph(),
    string showVertex(),
    string showEdge(),
    string showVertexOfEdges(1:i32 v1,2:i32 v2),
    string showEdgesOfVertex(1:i32 nomeV),
    string showAdjacency(1:i32 nomeV),
    string smallerPath(1:i32 v1,2:i32 v2)
}
