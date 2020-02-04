package de.kilitr;

import de.kilitr.exceptions.GraphNotValidException;
import de.kilitr.exceptions.NodeNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Entry Point of the Executable and Parses the commandline arguments.
 */
public class ArgumentParser {
    private static final Logger logger = LogManager.getLogger(ArgumentParser.class);

    private static Results results;
    private static boolean saveOutputFlag = false;
    private static boolean allFlag = false;
    private static boolean singlePathFlag = false;
    private static boolean singleBetweennessFlag = false;

    /**
     * Entry point of the program. Providing no arguments results in displaying a help message,
     * for further information look at the help message.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        if (args.length == 0 || args[0].equals("-h") || args[0].equals("--help")) {
            helpMessage();
            System.exit(0);
        } else {
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
            Node start = null;
            Node destination = null;

            Thread resultThread = null;
            // parsing the Arguments
            for (String arg : args) {
                if (arg.equals("-s")) {
                    singlePathFlag = true;
                    start = getNode(graph, args[2]);
                    destination = getNode(graph, args[3]);
                    results = new Results(graph, start, destination);
                    resultThread = new Thread(results, "resultThread");
                }
                if (arg.equals("-b")) {
                    singleBetweennessFlag = true;
                    start = getNode(graph, args[2]);
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
                        System.out.print(". "); // let the user know the program is not crashed
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("\n");
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
        }
    }

    private static Node getNode(Graph graph, String label) {
        Node node = null;
        try {
            node = graph.getNode(label);
        } catch (NodeNotFoundException e) {
            logger.error("Error: " + e.getMessage());
            System.exit(-1);
        }
        return node;
    }

    private static void helpMessage() {
        logger.warn("Proper Usage is: java-project <inputfile> [option] [-o <outputfile>]");

        String allHelp = "Provide all the information about the graph, that this program can collect.";
        String dijkstraHelp = "Calculate the shortest paths from start to destination.";
        String betweennessHelp = "Calculate the betweenness centrality for node.";
        String helpHelp = "displays this message again.";

        logger.info("\t\t-h | --help\t\t\t\t\t - \t" + helpHelp);
        logger.info("\tYou may also use (only) ONE of the following options:");
        logger.info("\t\t-a | --all\t\t\t\t\t - \t" + allHelp);
        logger.info("\t\t-s <start> <destination>\t - \t" + dijkstraHelp);
        logger.info("\t\t-b <node>\t\t\t\t\t - \t" + betweennessHelp);
        logger.info("\tPlease note, that the diameter is only calculated, when the option '-a or --all' is selected.");
    }
}
