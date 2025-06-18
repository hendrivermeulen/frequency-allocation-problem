/* © Copyright Hendrik Jacobus Vermeulen 2025, all rights reserved */
package com.hendri;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.hendri.methods.GreedyAllocation;
import com.hendri.methods.PriorityQueueAllocation;
import com.hendri.models.Cell;
import com.hendri.models.RegisteredCell;
import com.hendri.utils.Graph;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

/**
 * A program to allocate frequencies to cells
 * @author Hendrik Vermeulen
 */
public class Main
{

	/**
	 * Colors to use for coloring vertexes
	 */
	private static final Color[] colors = new Color[]{Color.BLACK, Color.BLUE, Color.CYAN,
			Color.DARK_GRAY, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED,
			Color.WHITE, Color.YELLOW};
	/**
	 * Frequency allocation start
	 */
	public static final int FREQ_ALLOC_START = 110;
	public static final int NUM_TESTS = 10;

	/**
	 * Get list of Frequency allocations
	 * @param num The number of frequency allocations required
	 * @return A list of frequency allocations
	 */
	private static Set<Integer> getAllocations(int num){
		// start from the allocation start
		int allocation = FREQ_ALLOC_START;
		Set<Integer> allocations = new HashSet<>();
		// add in an allocation num amount of times each time increasing the allocation number
		for (int i = 0; i < num; i++) {
			allocations.add(allocation++);
		}
		return allocations;
	}

	/**
	 * The JGraphT instance to visualize the solution
	 */
	private static final mxGraph graph = new mxGraph();
	/**
	 * The component to hold the graph visualization of the solution that can be added into a JComponent
	 */
	private static final mxGraphComponent graphComponent = new mxGraphComponent(graph);

	/**
	 * Program start
	 * @param args Arguments of the program <br>
	 * First argument being the file name or location to load the csv file that contains the cell location data <br>
	 * Second argument being the number of frequency allocations allowed, this is optional <br>
	 * Use buildin as first argument to load build-in
	 */
	public static void main(String[] args) {
		int numAllocations = 6;
		// confirm we have the right amount of arguments
		if (args.length == 1 || args.length == 2) {
			// load the file, if buildin load the built-in data set
			try(Scanner scanner = new Scanner((args[0].equals("buildin")) ?
					Objects.requireNonNull(Main.class.getResourceAsStream("testData.csv")) :
					new FileInputStream(args[0]))) {
				// change allocation number if specified
				if (args.length == 2) {
					try {
						numAllocations = Integer.parseInt(args[1]);
					}
					catch (NumberFormatException e) {
						System.out.println(
								"Please provide valid integer for number of allocation: " + args[1]
										+ " is invalid");
						return;
					}
				}

				// get the frequency allocations
				Set<Integer> freqAllocations = getAllocations(numAllocations);
				// prepare
				Set<Cell> cells = new HashSet<>();
				int smallestX = Integer.MAX_VALUE;
				int smallestY = Integer.MAX_VALUE;
				int lineNumber = 0;

				// loop through each line
				while (scanner.hasNextLine()) {
					// keep track of line number
					lineNumber++;
					// get line
					String line = scanner.nextLine();

					// skip header
					if (!line.contains("Cell")) {
						// load columns
						String[] columns = line.split(",");
						if (columns.length != 5) {
							System.out.println("Invalid CSV file, please check column count");
							System.exit(0);
						}

						// parse columns
						try {
							String id = columns[0].strip();
							int easting = Integer.parseInt(columns[1].strip());
							int northing = Integer.parseInt(columns[2].strip());
							double lat = Double.parseDouble(columns[3].strip());
							double lon = Double.parseDouble(columns[4].strip());

							// keep track of smallest locations
							if(easting < smallestX){
								smallestX = easting;
							}
							if (northing < smallestY){
								smallestY = northing;
							}

							// add row
							cells.add(Cell.builder()
											.id(id)
											.east(easting)
											.north(northing)
											.lat(lat)
											.lon(lon)
									.build());
						} catch (NumberFormatException e){
							System.out.println("Invalid CSV file, please check "
									+ "for invalid numbers at line: " + lineNumber);
							System.exit(0);
						}
					}
				}

				// prepare
				long startTime = -1;
				long endTime = -1;
				Set<RegisteredCell> registeredCells = null;

				/*System.out.println("Running BruteForce...");
				startTime = System.nanoTime();
				registeredCells = BruteForceAllocation.bruteForceAllocate(cells,
						freqAllocations);
				endTime = System.nanoTime();
				System.out.println("BruteForce quality: " + Graph.getGraphQualityMeasure(registeredCells).getQuality());
				System.out.println("BruteForce time: " + (endTime-startTime) + " ns");*/

				// warm up cache
				for (int i = 0; i < NUM_TESTS; i++) {
					PriorityQueueAllocation.priorityAllocate(cells,
							freqAllocations);
				}

				// Run Greedy
				System.out.println("Running Greedy...");
				long average = 0;
				for (int i = 0; i < NUM_TESTS; i++) {
					Graph.clearCache();
					startTime = System.currentTimeMillis();
					registeredCells = GreedyAllocation.greedilyAllocate(cells, freqAllocations);
					endTime = System.currentTimeMillis();
					average += endTime-startTime;
				}
				System.out.println("Greedy quality: " + Graph.getGraphQualityMeasure(registeredCells).getQuality());
				System.out.println("Greedy time: " + (average/ NUM_TESTS) + " ms");

				// Run priority
				System.out.println("Running Priority...");
				for (int i = 0; i < NUM_TESTS; i++) {
					startTime = System.currentTimeMillis();
					registeredCells = PriorityQueueAllocation.priorityAllocate(cells,
							freqAllocations);
					endTime = System.currentTimeMillis();
						average += endTime-startTime;
				}
				System.out.println("Priority quality: " + Graph.getGraphQualityMeasure(registeredCells).getQuality());
				System.out.println("Priority time: " + (average/ NUM_TESTS) + " ms");

				// Add solution to graph
				for (RegisteredCell registeredCell : registeredCells) {
					// only use color if we have enough colors
					String color = "";
					if(numAllocations <= colors.length){
						color = "fillColor=#" + Integer.toHexString(
								colors[registeredCell.getFrequency() - FREQ_ALLOC_START]
										.getRGB()).substring(2);
					}

					// add cell to graph
					Cell cell = registeredCell.getCell();
					graph.insertVertex(null, null,
							cell.getId() + ":" + registeredCell.getFrequency(),
							cell.getEast() - smallestX, cell.getNorth() - smallestY,
							50, 50,
							"rounded=true;strokeColor=none;editable=false;fontColor=#ffffff;"
									+ "movable=false;resizable=false;edgeStyle=none;" + color);
				}

				// show JFrame with solution
				JFrame jFrame = new JFrame();
				jFrame.setTitle("Frequency Allocation Graph");
				jFrame.setContentPane(graphComponent);
				jFrame.setSize(800, 600);
				jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				jFrame.setVisible(true);

			} catch (IOException e){
				System.out.println("Could not open file, please check if file exists and file permissions");
			}
		} else {
			System.out.println("Please run with <csv-file-name> and optionally <number-of-allocations>");
		}
	}
}