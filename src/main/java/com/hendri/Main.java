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

public class Main
{

	private static final Color[] colors = new Color[]{Color.BLACK, Color.BLUE, Color.CYAN,
			Color.DARK_GRAY, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED,
			Color.WHITE, Color.YELLOW};
	public static final int FREQ_ALLOC_START = 110;

	private static Set<Integer> getAllocations(int num){
		int allocation = FREQ_ALLOC_START;
		Set<Integer> allocations = new HashSet<>();
		for (int i = 0; i < num; i++) {
			allocations.add(allocation++);
		}
		return allocations;
	}

	private static final mxGraph graph = new mxGraph();
	private static final mxGraphComponent graphComponent = new mxGraphComponent(graph);

	public static void main(String[] args) {
		int numAllocations = 6;
		if (args.length == 1 || args.length == 2) {
			try(Scanner scanner = new Scanner((args[0].equals("buildin")) ?
					Objects.requireNonNull(Main.class.getResourceAsStream("testData.csv")) :
					new FileInputStream(args[0]))) {
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

				Set<Integer> freqAllocations = getAllocations(numAllocations);
				Set<Cell> cells = new HashSet<>();
				int smallestX = Integer.MAX_VALUE;
				int smallestY = Integer.MAX_VALUE;

				int lineNumber = 0;
				while (scanner.hasNextLine()) {
					lineNumber++;
					String line = scanner.nextLine();

					if (!line.contains("Cell")) {
						String[] columns = line.split(",");
						if (columns.length != 5) {
							System.out.println("Invalid CSV file, please check column count");
							System.exit(0);
						}

						try {
							String id = columns[0].strip();
							int easting = Integer.parseInt(columns[1].strip());
							int northing = Integer.parseInt(columns[2].strip());
							double lat = Double.parseDouble(columns[3].strip());
							double lon = Double.parseDouble(columns[4].strip());

							if(easting < smallestX){
								smallestX = easting;
							}
							if (northing < smallestY){
								smallestY = northing;
							}

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

				long startTime;
				long endTime;
				Set<RegisteredCell> registeredCells;

				/*System.out.println("Running BruteForce...");
				startTime = System.nanoTime();
				registeredCells = BruteForceAllocation.bruteForceAllocate(cells,
						freqAllocations);
				endTime = System.nanoTime();
				System.out.println("BruteForce quality: " + Graph.getGraphQualityMeasure(registeredCells).getQuality());
				System.out.println("BruteForce time: " + (endTime-startTime) + " ns");*/

				System.out.println("Running Greedy...");
				startTime = System.nanoTime();
				registeredCells = GreedyAllocation.greedilyAllocate(cells,
						freqAllocations);
				endTime = System.nanoTime();
				System.out.println("Greedy quality: " + Graph.getGraphQualityMeasure(registeredCells).getQuality());
				System.out.println("Greedy time: " + (endTime-startTime) + " ns");

				System.out.println("Running Priority...");
				startTime = System.nanoTime();
				registeredCells = PriorityQueueAllocation.priorityAllocate(cells,
						freqAllocations);
				endTime = System.nanoTime();
				System.out.println("Priority quality: " + Graph.getGraphQualityMeasure(registeredCells).getQuality());
				System.out.println("Priority time: " + (endTime-startTime) + " ns");

				for (RegisteredCell registeredCell : registeredCells) {
					Cell cell = registeredCell.getCell();
					graph.insertVertex(null, null,
							cell.getId() + ":" + registeredCell.getFrequency(),
							cell.getEast() - smallestX, cell.getNorth() - smallestY,
							50, 50,
							"rounded=true;strokeColor=none;editable=false;fontColor=#ffffff;"
									+ "movable=false;resizable=false;edgeStyle=none;"
									+ "fillColor=#" + Integer.toHexString(
											colors[registeredCell.getFrequency() - FREQ_ALLOC_START]
													.getRGB()).substring(2));
				}

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