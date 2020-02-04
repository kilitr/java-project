package de.kilitr;

import de.kilitr.exceptions.GraphNotValidException;
import de.kilitr.exceptions.VertexNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArgumentParser {
    private static final Logger logger = LogManager.getLogger(ArgumentParser.class);

    private static Results results;
    private static boolean saveOutputFlag = false;
    private static boolean allFlag = false;
    private static boolean singlePathFlag = false;
    private static boolean singleBetweennessFlag = false;

    public static void main(String[] args) {
        if (args.length > 0) {
            // always read the Graph first.
            String inputFile = args[0];
            logger.info("Loading graph from file: \"" + inputFile + "\".");
            GraphLoader graphLoader = new GraphLoader(inputFile);
            Graph graph = null; // will not stay null
            try {
                graph = graphLoader.getUndirectedGraph();
            } catch (GraphNotValidException e) {
                logger.error("Error: " + e.getMessage());
                System.exit(-1);
            }

            // needed here for correct output later
            Vertex start = null;
            Vertex destination = null;

            // parsing the Arguments
            for (String arg : args) {
                if (arg.equals("-s")) {
                    singlePathFlag = true;
                    start = getVertex(graph, args[2]);
                    destination = getVertex(graph, args[3]);
                    results = new Results(graph, start, destination);
                }
                if (arg.equals("-b")) {
                    singleBetweennessFlag = true;
                    start = getVertex(graph, args[2]);
                    results = new Results(graph, start);
                }
                if (arg.equals("-o")) {
                    saveOutputFlag = true;
                }
                if (arg.equals("--all") || arg.equals("-a")) { // all information needed...
                    allFlag = true;
                    results = new Results(graph, true);
                }
            }
            if (saveOutputFlag) {
                // TODO: save output
            }
            if (singlePathFlag) {
                new ResultConsolePrinter(results, start, destination);
            } else if (singleBetweennessFlag) {
                new ResultConsolePrinter(results, start);
            } else if (allFlag) {
                new ResultConsolePrinter(results, true);
            }
        } else {
            helpMessage();
        }
    }

    private static Vertex getVertex(Graph graph, String label) {
        Vertex v = null;
        try {
            v = graph.getVertex(label);
        } catch (VertexNotFoundException e) {
            logger.error("Error: " + e.getMessage());
            System.exit(-1);
        }
        return v;
    }

    private static void helpMessage() {
        logger.warn("Proper Usage is: java-project <inputfile> [option] [-o <outputfile>]");
        logger.info("\tYou may also use (only) ONE of the following options:");

        String allHelp = "Provide all the information about the graph, that this program can collect.";
        String dijkstraHelp = "Calculate the shortest paths from start to destination.";
        String betweennessHelp = "Calculate the betweenness centrality for node.";

        logger.info("\t\t-a | --all\t\t\t\t\t - \t" + allHelp);
        logger.info("\t\t-s <start> <destination>\t - \t" + dijkstraHelp);
        logger.info("\t\t-b <node>\t\t\t\t\t - \t" + betweennessHelp);
    }
}
