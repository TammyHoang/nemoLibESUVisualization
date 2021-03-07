package edu.uwb.nemolib;

import com.mxgraph.layout.*;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.*;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author Tammy Hoang and Jessica Runandy
 */
public class ESUVisualization extends JFrame {

    private JGraphXAdapter<String, DefaultEdge> jgxAdapter;
    private Graph targetGraph;
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
        targetGraph = graph;
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

        g.addVertex("root");

        // Adding all of the vertices to the ESU visualization graph/tree
        for (Object key : graph.getNameToIndexMap().keySet()) {
            g.addVertex(key.toString());
            g.addEdge("root", key.toString());

            // Gotta figure out the number of levels to print
            System.out.println("Lvl1Child: " + Integer.valueOf(key.toString()));
            AdjacencyList children = graph.getAdjacencyList((Integer) graph.getNameToIndexMap().get(key));
            CompactHashSet.Iter i = children.iterator();
            while (i.hasNext()) {
                Stream<String> keyStream = keys(graph.getNameToIndexMap(), Math.abs(i.next()));
                Integer child = Integer.valueOf(keyStream.findFirst().get());
                if (child > Integer.valueOf(key.toString())) {
                    g.addVertex(key.toString() + "," + child.toString());
                    g.addEdge(key.toString(), key.toString() + "," + child.toString());
                    System.out.println("\tChild=" + child);
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
