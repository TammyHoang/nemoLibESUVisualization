package edu.uwb.nemolib;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

import javax.swing.*;
import java.util.Map;
import java.util.stream.Stream;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Tammy Hoang and Jessica Runandy
 */
public class ESUVisualization extends JFrame {

    private JGraphXAdapter<String, DefaultEdge> jgxAdapter;
    private static Integer motifSize;
    private static String inputFileName;
    
    // Variables for user input GUI
    private static JLabel lblFile;
    private static JLabel lblPrompt;
    private static JButton btnOpen;
    private static JButton btnSubmit;
    private static JTextField txtMotifSize;
    private static JLabel lblMotifSize;

    /**
     * The default constructor for ESUVisualization class.
     *
     * @param title the title of the JFrame.
     */
    public ESUVisualization(String title) {
        super(title);
        addComponents();
    }

    /**
     * Private method used to add components to the frame.
     */
    private void addComponents() {
        JPanel pnlFile = new JPanel();
        lblPrompt = new JLabel("Select a graph text file.");
        pnlFile.add(lblPrompt);
        btnOpen = new JButton("Open");
        pnlFile.add(btnOpen);
        lblFile = new JLabel("No file selected");
        pnlFile.add(lblFile);
        lblFile.setText("");

        // Input motif size component
        txtMotifSize = new JTextField(6);
        lblMotifSize = new JLabel("Enter an integer motif size (motif size must be 3 or larger):");

        // Submit button
        btnSubmit = new JButton("Submit");
        
        JPanel pnlMotifSize = new JPanel();
        
        pnlMotifSize.add(lblMotifSize);
        pnlMotifSize.add(txtMotifSize);
        pnlMotifSize.add(btnSubmit);

        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BoxLayout(pnlNorth, BoxLayout.Y_AXIS));
        pnlNorth.add(pnlFile);
        pnlNorth.add(pnlMotifSize);
        
        getContentPane().add(pnlNorth, BorderLayout.NORTH);
        addEventHandlers();
    }

    // Controls the event of when the "Open" and "Submit" buttons are clicked
    private void addEventHandlers() {
        // Open button
        btnOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser file = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int r = file.showOpenDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) {
                    inputFileName = file.getSelectedFile().getAbsolutePath();
                    lblFile.setText(inputFileName);
                } else {
                    lblFile.setText("User cancelled operation");
                }
            }
        });
        
        // Submit button
        // After submitting, the ESU algorithm executes to display the graph
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String motifSizeInput = txtMotifSize.getText();
                txtMotifSize.setText("");
                
                // Returns if the input file does not exist
                if (inputFileName == null) {
                    return;
                }

                // Check if the user input is an integer
                try {
                    motifSize = Integer.valueOf(motifSizeInput); 
                } catch (NumberFormatException ex) {
                    return;
                }
                
                // Ensures that the motif size is at least 3
                if (motifSize < 3) {
                    return;
                }
                
                lblMotifSize.setText("Enter a motif size (motif size must be 3 or larger):");
                String filename = inputFileName;
                System.out.println("filename = " + inputFileName);
                boolean directed = false;

                // Parse input graph
                System.out.println("Parsing target graph...");
                Graph targetGraph = null;

                try {
                    targetGraph = GraphParser.parse(filename, directed);
                } catch (IOException ex) {
                    System.err.println("Could not process " + filename);
                    System.err.println(ex);
                    System.exit(-1);
                }

                addESUTree(targetGraph, motifSize);
            }
        });
    }
    
    private void addESUTree(Graph graph, Integer motifSize){
        // create a JGraphT graph
        ListenableGraph<String, DefaultEdge> g
                = new DefaultListenableGraph<>(new SimpleGraph<>(DefaultEdge.class));

        // create a visualization using JGraph, via an adapter
        jgxAdapter = new JGraphXAdapter<>(g);

        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        getContentPane().add(component, BorderLayout.CENTER);

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

        // Creating a tree layout
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