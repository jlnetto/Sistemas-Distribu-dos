package data;

import models.*;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TBinaryProtocol;

import java.util.Scanner;

public class Client {

    public static void main(String [] args) {
        try{
            TTransport transport = new TSocket("localhost",9080);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            Operations.Client client = new Operations.Client(protocol);

            int menu = 0;
            int v1, v2, cor, v3, v4;
            String descricao, file;
            double peso;
            Scanner scan = new Scanner(System.in);

            //Menu
            while(menu!=16) {
                System.out.println("1 - Inserir Vértice");
                System.out.println("2 - Inserir Aresta");
                System.out.println("3 - Carregar Grafo");
                System.out.println("4 - Salvar Grafo");
                System.out.println("5 - Exibir Grafo");
                System.out.println("6 - Remover Vértice");
                System.out.println("7 - Remover Arestas");
                System.out.println("8 - Alterar Vértice");
                System.out.println("9 - Alterar Arestas");
                System.out.println("10 - Listar Vértices de uma aresta");
                System.out.println("11 - Listar Arestas de um vertice");
                System.out.println("12 -Listar Vértices adjacentes a outros");
                System.out.println("13 - Sair");
                menu = scan.nextInt();
                switch (menu) {
                    case 1:

                        System.out.println("<- Digite os devidos atributos do vertice ->");
                        System.out.println("ID do vértice: ");
                        v1 = scan.nextInt();
                        System.out.println("Cor: ");
                        cor = scan.nextInt();
                        System.out.println("Descrição: ");
                        scan.nextLine();
                        descricao = scan.nextLine();
                        System.out.println("Peso: ");
                        peso = scan.nextDouble();
                        if(client.createVertex(v1, cor, descricao, peso)){
                            System.out.println("<- Vértice inserido! ->");
                            System.out.println(client.showVertex());
                        }else{
                            System.out.println("<- Erro! ->");
                        }
                        
                        break;
                        
                    case 2:

                        System.out.println("<- Digite os devidos atributos da aresta ->");
                        System.out.println("Digite o vertice 'X' da aresta: ");
                        v1 = scan.nextInt();
                        System.out.println("Digite o vertice 'Y' da aresta: ");
                        v2 = scan.nextInt();
                        System.out.println("Digite '1' <- direcionado -> '2' <- Bi-Direcionado ->: ");
                        cor = scan.nextInt();
                        System.out.println("Descrição: ");
                        scan.nextLine();
                        descricao = scan.nextLine();
                        System.out.println("Digite o peso: ");
                        peso = scan.nextDouble();
                        if(client.createEdge(v1, v2, peso, cor, descricao)){
                            System.out.println("<- Aresta Inserida! ->");
                            System.out.println(client.showEdge());
                        }else{
                            System.out.println("<- Erro! ->");
                        }
                        
                        break;
                    
                    case 3:
                        
                        System.out.println("<- Informe o arquivo a ser carregado -> ");
                        System.out.println("Arquivo:");
                        scan.nextLine();
                        file = scan.nextLine();
                        client.loadGraph(file);
                        
                        break;
                        
                    case 4:
                        
                        System.out.println("<- Informe o arquivo para salvar ->");
                        System.out.println("Arquivo: ");
                        scan.nextLine();
                        file = scan.nextLine();
                        client.saveGraph(file);
                        
                        break;
                        
                    case 5:
                    	System.out.println(client.showGraph());

                    case 6:
                        
                        System.out.println("<- Digite o vertice que deseja remover ->");
                        System.out.println("Vertice: ");
                        v1 = scan.nextInt();
                        if(client.deleteVertex(v1)){
                            System.out.println("<- Vértice Removido! ->");
                            System.out.println(client.showGraph());
                        }else{
                            System.out.println("<- Erro! ->");
                        }
                        break;

                    case 7:
                        
                        System.out.println("<- Entre com o vertice 'X' da aresta que será removida ->");
                        v1 = scan.nextInt();
                        System.out.println("<- Entre com o vertice 'Y' ->");
                        v2 = scan.nextInt();
                        if(client.deleteEdge(v1, v2)){
                            System.out.println("<- Aresta removida! ->");
                            System.out.println(client.showEdge());
                        }else{
                            System.out.println("<- Erro! ->");
                        }
                        break;

                    case 8:
                        
                        System.out.println("<- Digite os atributos do vertice a ser alterado ->");
                        System.out.println("Número do vértice a ser alterado: ");
                        v1 = scan.nextInt();
                        System.out.println("Novo número: ");
                        v2 = scan.nextInt();
                        System.out.println("Nova cor: ");
                        cor = scan.nextInt();
                        System.out.println("Descrição do vértice novo: ");
                        scan.nextLine();
                        descricao = scan.nextLine();
                        System.out.println("Digite o peso: ");
                        peso = scan.nextDouble();
                        if(client.updateVertex(v1, new Vertex(v2, cor, descricao, peso))){
                            System.out.println("<- Vertice Alterado! ->");
                            System.out.println(client.showVertex());
                        }else{
                            System.out.println("<- Erro! ->");
                        }
                        break;

                    case 9:
                        
                        System.out.println("<- Digite os atributos da aresta a ser alterada ->");
                        System.out.println("Vertice X: ");
                        v1 = scan.nextInt();
                        System.out.println("Vertice Y: ");
                        v2 = scan.nextInt();
                        System.out.println("Novo vertice 'X': ");
                        v3 = scan.nextInt();
                        System.out.println("Novo vertice 'Y': ");
                        v4 = scan.nextInt();
                        System.out.println("Nova descrição: ");
                        scan.nextLine();
                        descricao = scan.nextLine();
                        System.out.println("Digite o peso: ");
                        peso = scan.nextDouble();
                        System.out.println("Digite o novo flag: ");
                        cor = scan.nextInt();
                        if(client.updateEdge(v1, v2, new Edge(v3, v4, peso, cor, descricao))){
                            System.out.println("<- Aresta Alterada! ->");
                            System.out.println(client.showEdge());
                        }else{
                            System.out.println("<- Erro! ->");
                        }
                        break;
            
                    case 10:
                        
                        System.out.println("<- Digite uma aresta para listar seus vértices ->");
                        System.out.println("Vertice X: ");
                        v1 = scan.nextInt();
                        System.out.println("Vertice Y: ");
                        v2 = scan.nextInt();
                        System.out.println(client.showVertexOfEdges(v1,v2));
                        break;

                    case 11:
                        
                        System.out.println("<- Digite um vertice para listar suas arestas -> ");
                        System.out.println("Vertice X: ");
                        v1 = scan.nextInt();
                        System.out.println(client.showEdgesOfVertex(v1));
                        break;

                    case 12:
                        
                        System.out.println("<- Digite o vertice para exibir suas adjacências -> ");
                        v1 = scan.nextInt();
                        System.out.println(client.showAdjacency(v1));
                        break;

                    case 13:
                        
                        System.out.println("<- Disconnecting... ->");
                        break;

                    default:
                        System.out.println("<- Opção Inválida! ->");
                }
                try{
                    Thread.sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            transport.close();
        }catch (TException x){
            x.printStackTrace();
        }
    }
}
