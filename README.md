nemoLib ESU Visualization
=======

Description
-----------
The ESU algorithm visualization tool visualizes the ESU algorithm to find subgraphs. Starting from the root node, the tool searches the undirected graph text file inputted by the user to create a GUI for a visualized tree for the subgraphs of a specific size network. Currently, the tool supports a motif size of three. The tool uses the nemoLib library Java version as a reference to implement the ESU algorithm and to parse the input graph text file.

Prerequisites
-------------
* A macOS operating environment due to the nauty27r1 labelg program in the project, which is compatible with macOS
* JDK version 8 or higher
* [Maven](https://maven.apache.org/) version 3.0 or higher
* Netbeans IDE

Installation and Use
--------------------
* Download the GitHub repository as a zip file or clone the repository
* Unzip the zip file
* Open the `nbactions.xml` file to change the path of the `<exec.workingdir>` to the current location of the program (there are three sections of the `<exec.workingdir>`)
  * If the path of the `<exec.workingdir>` is at the incorrect location, there will be errors with Maven.
* Open the project using the Netbeans IDE
* Clean and build the program
* Run the program in the NetworkMotifDetector.java class since it contains the main driver function
* The GUI is displayed, showing the options to select a graph text file and a motif size
* Select a graph text file
* Input a motif size greater than or equal to three (the tool currently supports a motif size of three)
* Click `Submit`

Example Testing File
---------------
An example graph input text file can be found in the project, labelled graph.txt.

Documentation
-------------
JavaDocs are auto-generated when Maven creates the project.

Future Project Ideas
--------------------
* Test with more graph text files
* Test with larger graph text files
* Make the tool more efficient so that it can apply to larger graph text files
* Test with nauty27r1 labelg program that is compatible other operating systems
* Support directed graphs
* Support the display of subgraphs for a motif size greater than three 
* Deploy the tool as a web application
* Deploy the tool on the University of Washington Bothell's servers
* Allow users to zoom in and out of specific areas of the tree
* Allow users to click on a specific node, which would then display the subgraph pattern
