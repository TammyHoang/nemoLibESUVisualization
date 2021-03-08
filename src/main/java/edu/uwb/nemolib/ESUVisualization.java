package edu.uwb.nemolib;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.*;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

import javax.swing.*;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author Tammy Hoang and Jessica Runandy
 */
public class ESUVisualization extends JFrame {

    private JGraphXAdapter<String, DefaultEdge> jgxAdapter;
    private int levels;

    /**
     * The default constructor for ESUVisualization class.
     *
     * @param title the title of the JFrame.
     * @param graph
     * @param motifSize
     */
    public ESUVisualization(String title, Graph graph, int motifSize) {
        super(title);
        levels = motifSize;
        addComponents(graph);
    }

    /**
     * Private method used to add components to the frame.
     */
    private void addComponents(Graph graph) {
        // create a JGraphT graph
        ListenableGraph<String, DefaultEdge> g
                = new DefaultListenableGraph<>(new SimpleGraph<>(DefaultEdge.class));

        // create a visualization using JGraph, via an adapter
        jgxAdapter = new JGraphXAdapter<>(g);

        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        getContentPane().add(component);

        g.addVertex("Root");

        // Adding all of the vertices to the ESU visualization graph/tree
        for (Object key : graph.getNameToIndexMap().keySet()) {
            g.addVertex(key.toString());
            g.addEdge("Root", key.toString());

            System.out.println("Lvl1Parent: " + Integer.valueOf(key.toString()));
            AdjacencyList children = graph.getAdjacencyList((Integer) graph.getNameToIndexMap().get(key));
            // Iterating through each vertices' children
            CompactHashSet.Iter i = children.iterator();
            while (i.hasNext()) {
                int curr = Math.abs(i.next());
                AdjacencyList path = new AdjacencyList();
                path.add((Integer) graph.getNameToIndexMap().get(key));
                path.add(curr);
                String vertex = key.toString();
                // Getting the key (node) from the key-value HashMap
                Stream<String> keyStream = keys(graph.getNameToIndexMap(), curr);
                Integer child = Integer.valueOf(keyStream.findFirst().get());
                // Check if the child is greater than the parent
                if (child > Integer.valueOf(key.toString())) {
                    vertex += "," + child.toString();
                    g.addVertex(vertex);
                    g.addEdge(key.toString(), vertex);
                    System.out.println("\tLvl2Child=" + child);
                    // Find the children's children
                    AdjacencyList nextLvlChildren = new AdjacencyList();
                    // Get the siblings
                    AdjacencyList siblings = graph.getAdjacencyList((Integer) graph.getNameToIndexMap().get(key));
                    CompactHashSet.Iter siblingsIter = siblings.iterator();
                    while (siblingsIter.hasNext()) {
                        int sIter = Math.abs(siblingsIter.next());
                        // Get the key (node) from the key-value HashMap
                        keyStream = keys(graph.getNameToIndexMap(), sIter);
                        Integer sibling = Integer.valueOf(keyStream.findFirst().get());
                        // Check if the sibling is greater than the current node
                        if (sibling > child) {
                            nextLvlChildren.add(sIter);
                        }
                    }
                    // Get the object from the key-value HashMap
                    Stream<Object> keyStream1 = keys(graph.getNameToIndexMap(), curr);
                    Object key1 = keyStream1.findFirst().get();
                    // Get the adjacency list's iterator of the current object
                    CompactHashSet.Iter iNextLvlChildren = graph.getAdjacencyList(
                            (Integer) graph.getNameToIndexMap().get(key1)).iterator();
                    while (iNextLvlChildren.hasNext()) {
                        int nextLevelChild = Math.abs(iNextLvlChildren.next());
                        // Get the key (node) from the key-value HashMap
                        keyStream = keys(graph.getNameToIndexMap(), nextLevelChild);
                        child = Integer.valueOf(keyStream.findFirst().get());
                        // Check if the child is greater than the parent node and the siblings don't already contain the next level's child
                        if (child > Integer.valueOf(key.toString()) && !siblings.contains(nextLevelChild)) {
                            nextLvlChildren.add(nextLevelChild);
                        }
                    }
                    // Create the vertices and edges of the 3rd level children
                    CompactHashSet.Iter childrenIter = nextLvlChildren.iterator();
                    while (childrenIter.hasNext()) {
                        int nextChild = Math.abs(childrenIter.next());
                        AdjacencyList currPath = path.copy();
                        currPath.add(nextChild);
                        // Get the key (node) from the key-value HashMap
                        keyStream = keys(graph.getNameToIndexMap(), nextChild);
                        child = Integer.valueOf(keyStream.findFirst().get());
                        String cVertex = vertex + "," + child.toString();
                        g.addVertex(cVertex);
                        g.addEdge(vertex, cVertex);
                        System.out.println("\t\tLvl3Child=" + child.toString());
                    }
                }
            }
        }

        // positioning via jgraphx layouts
        mxHierarchicalLayout layout = new mxHierarchicalLayout(jgxAdapter);
        layout.execute(jgxAdapter.getDefaultParent());
    }

    public <K, V> Stream<K> keys(Map<K, V> map, V value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey);
    }
            }