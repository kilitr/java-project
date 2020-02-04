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

            Thread resultThread = null;
            // parsing the Arguments
            for (String arg : args) {
                if (arg.equals("-s")) {
                    singlePathFlag = true;
                    start = getVertex(graph, args[2]);
                    destination = getVertex(graph, args[3]);
                    results = new Results(graph, start, destination);
                    resultThread = new Thread(results, "resultThread");
                }
                if (arg.equals("-b")) {
                    singleBetweennessFlag = true;
                    start = getVertex(graph, args[2]);
                    results = new Results(graph, start);
                    resultThread = new Thread(results, "resultThread");
                }
                if (arg.equals("-o")) {
                    saveOutputFlag = true;
                }
                if (arg.equals("--all") || arg.equals("-a")) { // all information needed...
                    allFlag = true;
                    results = new Results(graph, true);
                    resultThread = new Thread(results, "resultThread");
                }
            }
            if (resultThread != null) {
                resultThread.start();
                while (resultThread.isAlive()) {
                    try {
                        Thread.sleep(250);
                        System.out.print(".");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (saveOutputFlag) {
                // TODO: save output
            }
            Thread consolePrinter = null;
            if (singlePathFlag) {
                consolePrinter = new Thread(new ResultConsolePrinter(results, start, destination), "ConsolePrinter");
            } else if (singleBetweennessFlag) {
                consolePrinter = new Thread(new ResultConsolePrinter(results, start), "ConsolePrinter");
            } else if (allFlag) {
                consolePrinter = new Thread(new ResultConsolePrinter(results, true), "ConsolePrinter");
            }
            if (consolePrinter != null) {
                consolePrinter.start();
            } else {
                logger.error("The thread for creating console output could not be created!");
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
