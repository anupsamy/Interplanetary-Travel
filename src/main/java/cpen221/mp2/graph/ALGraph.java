package cpen221.mp2.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ALGraph<V extends Vertex, E extends Edge<V>> implements MGraph<V, E> {
    private Map<V, ArrayList<E>> adjList;

    public ALGraph() {
        Map<Vertex, Edge> adjList = new HashMap<>();
    }
    @Override
    public boolean addVertex(V v){
        if (adjList.containsKey(v)){
            return false;
        }
        else{
            adjList.put(v, new ArrayList<>());
            return true;
        }
    }
    @Override
    public boolean vertex(V v){
        return adjList.containsKey(v);
    }

    //add specs to say edge can only be added if both v1 and v2 are in adjList
    @Override
    public boolean addEdge(E e){
        if (adjList.containsKey(e.v1()) && adjList.containsKey(e.v2())){
            adjList.get(e.v1()).add(e);
            adjList.get(e.v2()).add(e);
            return true;
        }
        else{
            return false;
        }
    }

    //add spec that edge has to exist in both v1 and v2 keys
    @Override
    public boolean edge(E e) {
        if (adjList.containsKey(e.v1()) && adjList.containsKey(e.v2())) {
            if (adjList.get(e.v1()).contains(e) && adjList.get(e.v2()).contains(e)){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //add spec that edge has to exist in both v1 and v2 keys?
    @Override
    public boolean edge(V v1, V v2){
        if (adjList.containsKey(v1) && adjList.containsKey(v2)){
            for (E e : adjList.get(v1)){
                if (e.v1().equals(v2) || e.v2().equals(v2)){
                    return true;
                }
            }
            return false;
        } else{
            return false;
        }
    }
    //spec that says return 0 if edge does not exist
    @Override
    public int edgeLength(V v1, V v2){
        if (adjList.containsKey(v1) && adjList.containsKey(v2)){
            for (E e : adjList.get(v1)){
                if (e.v1().equals(v2) || e.v2().equals(v2)){
                    return e.length();
                }
            }
        }
        return 0;
    }
}
