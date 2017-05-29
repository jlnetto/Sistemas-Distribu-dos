package data;

import models.*;

import org.apache.thrift.TException;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GraphHandler implements Operations.Iface {

    //private ArrayList<Graph> Graphs = new ArrayList<Graph>();
    private Graph G = new Graph(new ArrayList<Vertex>(),new ArrayList<Edge>());

    @Override
    public synchronized void loadGraph(String caminho){
       Object aux = null;

        try{
            FileInputStream restFile = new FileInputStream(caminho);
            ObjectInputStream stream = new ObjectInputStream(restFile);

            aux = stream.readObject();
            if(aux != null){
                G = (Graph)aux;
            }
            stream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void saveGraph(String caminho){
            try{
                FileOutputStream saveFile = new FileOutputStream(caminho);
                ObjectOutputStream stream = new ObjectOutputStream(saveFile);

                stream.writeObject(G);
                stream.close();
            } catch (IOException exc){
                exc.printStackTrace();
            }
        }
    

    @Override
    public synchronized boolean createVertex(int nome, int cor, String descricao, double peso){
        if(G.getV() != null) {
            for (Vertex v : G.V){
                if(v.nome == nome){ 
                    return false;
                }
            }
        }
        G.getV().add(new Vertex (nome,cor,descricao,peso));
        return true;
    }

    @Override
    public synchronized boolean createEdge(int v1, int v2, double peso, int flag, String descricao){
        int criaControl = 0;
        for(Vertex v:G.V){ 
            if(v.nome == v1 || v.nome == v2){
                criaControl++;
            }
        }
        if(criaControl > 1) {
            Edge aux2 = new Edge(v1, v2, peso, flag, descricao);
            if (!ifEquals(aux2)) {
                if (flag == 2) {
                    Edge aux = new Edge(v2, v1, peso, flag, descricao);
                    if (!ifEquals(aux)) {
                        G.A.add(aux);
                    }
                }
                G.A.add(aux2);
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized boolean deleteVertex(int nome){

        for(int i=G.getA().size()-1;i>=0;i--){
            if (G.getA().get(i).v1 == nome || G.getA().get(i).v2 == nome) {
                G.A.remove(i);
                if(G.A.isEmpty()){
                    break;
                }
            }
        }
        for(Vertex v:G.V){
            if (v.nome == nome){
                G.V.remove(v);
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized boolean deleteEdge(int v1, int v2){
        for(Edge a:G.A){
            if(a.v1 == v1 && a.v2 == v2){
                G.A.remove(a);
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized boolean updateVertex(int nomeUp, Vertex V){
        if(V == null){
            return false;
        }
        if(nomeUp != V.nome){ 
            return false;
        }
        for(Vertex v:G.getV()){
            if(v.nome == nomeUp){
                v.cor = V.cor;
                v.descricao = V.descricao;
                v.peso = V.peso;
                return true;
            }
        }
        return false; 
    }

    public boolean ifEquals(Edge A){
        for(Edge a:G.getA()){
            if(a.v1 == A.v1 && a.v2 == A.v2){
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized boolean updateEdge(int nomeV1, int nomeV2, Edge A){
        if(A == null){
            return false;
        }
        if(nomeV1 != A.v1 || nomeV2 != A.v2){
            return false;
        }
        for(Edge a:G.getA()){
            if(a.v1 == nomeV1 && a.v2 == nomeV2){
                a.peso = A.peso;
                a.flag = A.flag;
                a.descricao = A.descricao;
                if(A.flag == 2 && a.flag != 2){
                    Edge aux = new Edge(A.v2,A.v1,A.peso,A.flag,A.descricao);
                    if(!ifEquals(aux)){
                        G.getA().add(aux);
                    }
                }
                return true;
            }
        }
        return false; 
    }

    @Override
    public boolean updateGraph(java.util.List<Vertex> V, java.util.List<Edge> A){
        G.V = V;
        G.A = A;
        return true;
    }

    @Override
    public Vertex getVertex(int nome){
        if(!G.getV().isEmpty()) {
            for (Vertex v : G.getV()) {
                if (v.nome == nome) {
                    return v;
                }
            }
        }
        return null;
    }

    @Override
    public Edge getEdge(int v1, int v2){
        if(!G.getA().isEmpty()) {
            for (Edge a : G.getA()) {
                if (a.v1 == v1 && a.v2 == v2) {
                    return a;
                }
            }
        }
        return null;
    }

    @Override
    public String showGraph(){
        String exibir = "Vértices: ";
        for(Vertex v:G.getV()){
            exibir = exibir+v.nome+" ,";
        }
        exibir = exibir + "\n";
        exibir = exibir+"Arestas: ";
        for(Edge a:G.getA()){
            exibir = exibir+"("+a.v1+", "+a.v2+")";
        }
        return exibir;
    }

    @Override
    public String showVertex(){
        String exibir = "";
        for (Vertex v:G.getV()){
            exibir = exibir+"Vértice: "+v.nome+" Peso: "+v.peso+" Cor: "+v.cor+" Descrição: "+v.descricao+"\n";
        }
        return exibir;
    }

    @Override
    public String showEdge(){
        String exibir = "";
        for (Edge a:G.getA()){
            exibir = exibir+"Aresta: "+"("+a.v1+", "+a.v2+") Peso: "+a.peso+" Flag: "+a.flag+" Descrição: "+a.descricao+"\n";
        }
        return exibir;
    }

    @Override
    public String showVertexOfEdges(int v1, int v2) {
        return("("+v1+", "+v2+")");
    }

    @Override
    public String showEdgesOfVertex(int nomeV) {
        String exibir = "Arestas do vértice "+nomeV+": ";
        for(Edge a:G.getA()){
            if(a.v1 == nomeV || a.v2 == nomeV){
                exibir = exibir+"("+a.v1+", "+a.v2+")";
            }
        }
        return exibir;
    }

    @Override
    public String showAdjacency(int nomeV) {
        String exibir = "Vizinhos de "+nomeV+" são: \n";

        for (Edge a : G.getA()) {
            if(a.v1 == nomeV) {
                Vertex v = getVertex(a.v2);
                exibir = exibir+ "Vértice: "+ v.nome +" "+
                        "Cor: "+v.cor +" "+
                        "Peso: "+ v.peso + " "+
                        "Descrição: "+ v.descricao+ "\n";
            } else if(a.v2 == nomeV) {
                Vertex v = getVertex(a.v1);
                exibir = exibir+ "Vértice: "+ v.nome +" "+
                        "Cor: "+v.cor +" "+
                        "Peso: "+ v.peso + " "+
                        "Descrição: "+ v.descricao+ "\n";
            }
        }
        return exibir;
    }

    @Override
    public String smallerPath(int nomeV1, int nomeV2){
        return "Dijkstra";
    }
    //TODO Adicinar algoritmo de menor caminho
}
