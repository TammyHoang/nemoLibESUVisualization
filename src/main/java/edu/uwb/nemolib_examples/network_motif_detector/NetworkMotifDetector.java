package edu.uwb.nemolib_examples.network_motif_detector;

import edu.uwb.nemolib.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;

public class NetworkMotifDetector {
    
    private static final double P_THRESH = 0.1;
    private static final int RAND_GRAPH_COUNT = 1000;

    public static void main(String[] args) {
        
        ESUVisualization view = new ESUVisualization("ESU Algorithm Visualization");
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setSize(1000, 500);
        view.setLocation(100, 100);
        view.setVisible(true);
        
//        // The commented section below is the original NemoLib code, uncomment
//        // to access the functionalities as needed.
//        String filename = args[0];
//        System.out.println("filename = " + args[0]);
//        int motifSize = Integer.parseInt(args[1]);
//        int randGraphCount = Integer.parseInt(args[2]);
//        boolean directed = false;
//
//        if (args.length > 3) {
//            if (Integer.parseInt(args[3]) > 0) {
//                directed = true;
//            }
//        }
//
//        if (motifSize < 3) {
//            System.err.println("Motif size must be 3 or larger");
//            System.exit(-1);
//        }
//
//        // parse input graph
//        System.out.println("Parsing target graph...");
//        Graph targetGraph = null;
//
//        try {
//            targetGraph = GraphParser.parse(filename, directed);
//        } catch (IOException e) {
//            System.err.println("Could not process " + filename);
//            System.err.println(e);
//            System.exit(-1);
//        }
//
//        System.out.println("Print vertex to index map =" + targetGraph.getNameToIndexMap());
//
//        // If want to save the name to index map to the file
//        targetGraph.write_nametoIndex("Name_Index.txt");
//
//        /* Step1: analyze target graph*/
//        System.out.println("Analyzing target graph...");
//
//        /* // Provide three output options from here. 
//                 Subgraph results can be provided into three options. 
//                1. SubgraphCount: provide frequency for each pattern
//                2. SubgraphProfile: Provide frequency as well as the pattern's concentration on each vertex
//                3. SubgraphCollection: Provide frequency as well as the instances of each pattern written in the filename given
//                
//               
//              
//                * The first option is "SubgraphCount", which provides the frequencies of each pattern.
//                ** If the graph or motif size is big, this method is recommended.
//                * To go different option, just comment out all the method from this line until encounter 1111111 
//         */
// /* //111111111111111111111111111111111111111111111111111111111111111111111////             
//                SubgraphCount subgraphCount = new SubgraphCount();
//                                        
//                // Create a class that will enuerate all subgraphs.
//                // If not want do full enumeration, provide probabilities for each tree level
//		SubgraphEnumerator targetGraphESU = new ESU();
//                
//                // Will enumerate all subgraphs and results will be written in subgraphCount
//		TargetGraphAnalyzer targetGraphAnalyzer =
//				new TargetGraphAnalyzer(targetGraphESU, subgraphCount);
//                
//                
//		// The frequency will be represented as percentage (relative frequency)
//                Map<String, Double> targetLabelToRelativeFrequency =
//				targetGraphAnalyzer.analyze(targetGraph, motifSize);
//                
//                System.out.println("targetLabelToRelativeFrequency="+targetLabelToRelativeFrequency);
//                
//                           
//                
//                // Step 2: generate random graphs 
//                System.out.println("Generating "+randGraphCount+" random graph...");
//                // Hard-code probs for now for random subgraph sampling. This vector will take about ~50% sample 
//		List<Double> probs = new LinkedList<>();
//		for (int i = 0; i < motifSize - 2; i++)
//		{
//			probs.add(1.0);
//		}
//		probs.add(.5);
//		probs.add(.5);
//                
//                
//                // Create enumeration class, and start sampling
//		SubgraphEnumerator randESU = new RandESU(probs);
//		
//                RandomGraphAnalyzer randomGraphAnalyzer =
//				new RandomGraphAnalyzer(randESU, randGraphCount);
//		
//                // The results are saved to randomLabelToRelativeFrequencies
//                Map<String, List<Double>> randomLabelToRelativeFrequencies =
//				randomGraphAnalyzer.analyze(targetGraph, motifSize);
//
//		System.out.println("randomLabelToRelativeFrequencies="+randomLabelToRelativeFrequencies);
//
//                //STEP 3: Determine network motifs through statistical analysis 
//                RelativeFrequencyAnalyzer relativeFrequencyAnalyzer =
//				new RelativeFrequencyAnalyzer(randomLabelToRelativeFrequencies,
//						                      targetLabelToRelativeFrequency);
//		System.out.println(relativeFrequencyAnalyzer);
//                
//                              
//               
//		System.out.println("SubraphCount Compete");	
//                
//                
//                 //111111111111111111111111111111111111111111111111111111111111111111111////
//                
//		 
//         */
// /* The second option is "SubgraphProfile", which maps each vertex concentration to each pattern.
//                // The frequency of each pattern is also provided
//                ** If the graph or motif size is big, this method is recommended.
//                * To go different option, just comment out all the method from this line until encounter 22222222222222 
//         */
// /*//222222222222222222222222222222222222222222222222222222222222222222////         
//                
//                
//                
//                //If want to provide with Profile 
//                SubgraphProfile subgraphCount = new SubgraphProfile();
//                
//               
//                // Create a class that will enuerate all subgraphs.
//                // If not want do full enumeration, provide probabilities for each tree level
//		SubgraphEnumerator targetGraphESU = new ESU();
//                
//                // Will enumerate all subgraphs and results will be written in subgraphCount
//		TargetGraphAnalyzer targetGraphAnalyzer =
//				new TargetGraphAnalyzer(targetGraphESU, subgraphCount);
//                
//                
//		// The frequency will be represented as percentage (relative frequency)
//                Map<String, Double> targetLabelToRelativeFrequency =
//				targetGraphAnalyzer.analyze(targetGraph, motifSize);
//                
//                System.out.println("targetLabelToRelativeFrequency="+targetLabelToRelativeFrequency);
//                
//                           
//                
//                // Step 2: generate random graphs 
//                System.out.println("Generating "+randGraphCount+" random graph...");
//                // Hard-code probs for now for random subgraph sampling. This vector will take about ~50% sample 
//		List<Double> probs = new LinkedList<>();
//		for (int i = 0; i < motifSize - 2; i++)
//		{
//			probs.add(1.0);
//		}
//		probs.add(.5);
//		probs.add(.5);
//                
//                
//                // Create enumeration class, and start sampling
//		SubgraphEnumerator randESU = new RandESU(probs);
//		
//                RandomGraphAnalyzer randomGraphAnalyzer =
//				new RandomGraphAnalyzer(randESU, randGraphCount);
//		
//                // The results are saved to randomLabelToRelativeFrequencies
//                Map<String, List<Double>> randomLabelToRelativeFrequencies =
//				randomGraphAnalyzer.analyze(targetGraph, motifSize);
//
//		System.out.println("randomLabelToRelativeFrequencies="+randomLabelToRelativeFrequencies);
//
//                // STEP 3: Determine network motifs through statistical analysis 
//                RelativeFrequencyAnalyzer relativeFrequencyAnalyzer =
//				new RelativeFrequencyAnalyzer(randomLabelToRelativeFrequencies,
//						                      targetLabelToRelativeFrequency);
//		System.out.println(relativeFrequencyAnalyzer);
//                
//               //  Display the nemoprofile result based on pvalue<0.05. If the file name is null, "NemoProfile.txt" is default,
//               // If the nametoindex map is not given (given as null), then the nemoprofile provide as index instead of original vertex name
//                SubgraphProfile built = NemoProfileBuilder.buildwithPvalue(subgraphCount, 
//                        relativeFrequencyAnalyzer, 0.05, "NemoProfile.txt",targetGraph.getNameToIndexMap() );
//                
//                // Print the result in screen
//                System.out.println("NemoProfile=\n" + built+"\n");
//                
//                     
//
//		System.out.println("SubgraphProfile Compete");	
//                
//                //222222222222222222222222222222222222222222222222222222222222222222////  
//                
//         */
// /* The last option is "SubgraphCollection", which write all instances of each pattern, and frequency of each pattern
//                ** It is recormended to use for moderate graph size or motif size.
//                * To go different option, just comment out all the method from this line until encounter 33333333333333333333333333333333333333333 
//         */
//        //33333333333333333333333333333333333333333////         
//        //If want to provide collections with instances written "Results.txt" file. 
//        //SubgraphCollection subgraphCount = new SubgraphCollection("Results.txt");
//        // Default file name is "SubgraphCollectionResult.txt"
//        // Create subgraphCount instance which will collect results in SubgraphCollectionG6.txt
//        SubgraphCollection subgraphCount = new SubgraphCollection();
//
//        // Create a class that will enuerate all subgraphs.
//        // If not want do full enumeration, provide probabilities for each tree level
//        SubgraphEnumerator targetGraphESU = new ESU();
//
//        // Will enumerate all subgraphs and results will be written in subgraphCount
//        TargetGraphAnalyzer targetGraphAnalyzer
//                = new TargetGraphAnalyzer(targetGraphESU, subgraphCount);
//
//        // The frequency will be represented as percentage (relative frequency)
//        Map<String, Double> targetLabelToRelativeFrequency
//                = targetGraphAnalyzer.analyze(targetGraph, motifSize);
//
//        System.out.println("targetLabelToRelativeFrequency=" + targetLabelToRelativeFrequency);
//
//        // Step 2: generate random graphs 
//        System.out.println("Generating " + randGraphCount + " random graph...");
//        // Hard-code probs for now for random subgraph sampling. This vector will take about ~50% sample 
//        List<Double> probs = new LinkedList<>();
//        for (int i = 0; i < motifSize - 2; i++) {
//            probs.add(1.0);
//        }
//        probs.add(.5);
//        probs.add(.5);
//
//        // Create enumeration class, and start sampling
//        SubgraphEnumerator randESU = new RandESU(probs);
//
//        RandomGraphAnalyzer randomGraphAnalyzer
//                = new RandomGraphAnalyzer(randESU, randGraphCount);
//
//        // The results are saved to randomLabelToRelativeFrequencies
//        Map<String, List<Double>> randomLabelToRelativeFrequencies
//                = randomGraphAnalyzer.analyze(targetGraph, motifSize);
//
//        System.out.println("randomLabelToRelativeFrequencies=" + randomLabelToRelativeFrequencies);
//
//        // STEP 3: Determine network motifs through statistical analysis 
//        RelativeFrequencyAnalyzer relativeFrequencyAnalyzer
//                = new RelativeFrequencyAnalyzer(randomLabelToRelativeFrequencies,
//                        targetLabelToRelativeFrequency);
//        System.out.println(relativeFrequencyAnalyzer);
//
//        // This is optional, if the user want to collect all subgraphs with canonical label in a file
//        //  Write the nemocollection result based on zscore thresh (anything with >=2 is collected) .                
//        System.out.println("Writing network motif instances to NemoCollection file");
//        NemoCollectionBuilder.buildwithZScore(subgraphCount, relativeFrequencyAnalyzer,
//                2, "NemoCollectionZscore.txt", targetGraph.getNameToIndexMap());
//
//        //  Write the nemocollection result based on pvalue thresh (anything with <0.05 is collected) .  
//        NemoCollectionBuilder.buildwithPvalue(subgraphCount, relativeFrequencyAnalyzer,
//                0.05, "NemoCollectionPValue.txt", targetGraph.getNameToIndexMap());
//
//        //  Write the subgraph collection 
//        NemoCollectionBuilder.buildwithPvalue(subgraphCount, relativeFrequencyAnalyzer,
//                1, "SubgraphCollection.txt", targetGraph.getNameToIndexMap());
//
//        System.out.println("NemoCollection Compete");
//        //33333333333333333333333333333333333333333////
    }
}
