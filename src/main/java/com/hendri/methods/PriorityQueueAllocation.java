/* © Copyright Hendrik Jacobus Vermeulen 2025, all rights reserved */
package com.hendri.methods;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import com.hendri.models.Cell;
import com.hendri.models.Edge;
import com.hendri.models.RegisteredCell;
import com.hendri.utils.Graph;
import lombok.NonNull;

/**
 * Holds the Priority Allocation method
 * @author Hendrik Vermeulen
 */
public class PriorityQueueAllocation
{

	/**
	 * Priority Queue method for allocating frequencies to cells
	 * @param cells The cells to allocate frequencies to
	 * @param frequencies The frequencies available to allocate
	 * @return The cells with allocated frequencies
	 */
	public static Set<RegisteredCell> priorityAllocate(@NonNull Set<Cell> cells, @NonNull Set<Integer> frequencies){
		// validate
		Objects.requireNonNull(cells);
		Objects.requireNonNull(frequencies);
		if(cells.isEmpty() || frequencies.isEmpty()) {
			return Set.of();
		}

		// use to prioritize edge in queue
		Comparator<Edge> edgeComparator = Comparator.comparingDouble(
				edge -> Graph.getDistanceBetween(edge.getFrom(), edge.getTo()));

		// prepare
		HashMap<Cell, PriorityQueue<Edge>> allCellPriorities = new HashMap<>();
		PriorityQueue<Edge> edgePriorities = new PriorityQueue<>(edgeComparator);

		// calculate each edge
		for (Cell cell : cells) {
			PriorityQueue<Edge> cellPriorities = new PriorityQueue<>(edgeComparator);
			for (Cell neighbour : cells) {
				if(!cell.equals(neighbour)){
					Edge edge = Edge.builder().from(cell).to(neighbour).build();
					cellPriorities.add(edge);
					edgePriorities.add(edge);
				}
			}
			allCellPriorities.put(cell, cellPriorities);
		}

		// perform priority allocation
		HashMap<Cell, Integer> allocations = new HashMap<>();
		while (allocations.size() != cells.size()){
			Edge edge = Objects.requireNonNull(edgePriorities.poll());
			allocations.computeIfAbsent(edge.getFrom(), cell -> allocate(cell,
					allCellPriorities.get(cell), allocations, frequencies));
			allocations.computeIfAbsent(edge.getTo(), cell -> allocate(cell,
					allCellPriorities.get(cell), allocations, frequencies));
		}

		return allocations.entrySet().stream().map(pair->
				RegisteredCell.builder().cell(pair.getKey()).frequency(pair.getValue()).build())
				.collect(Collectors.toSet());
	}

	private static int allocate(Cell cell, PriorityQueue<Edge> cellPriorities,
			HashMap<Cell, Integer> allocations, Set<Integer> freqAllocations){
		// prepare
		int furthestAwayFreq = -1;
		double furthestAwayFreqDistance = -1;
		int amountOfEmptyNeighboursVisited = 0;
		Set<Integer> leftoverAllocations = new HashSet<>(freqAllocations);

		// only if there are the initial frequencies have been allocated
		if(allocations.values().size() != freqAllocations.size()) {
			// only while there are neighbours left and we have frequencies left to allocation
			// and that we have not run out of k nearest neighbours to visit
			while (!cellPriorities.isEmpty() && !leftoverAllocations.isEmpty()
					&& amountOfEmptyNeighboursVisited < freqAllocations.size()) {
				// get next smallest edge
				Edge edge = cellPriorities.poll();
				// get number from edge
				Cell neighbour = (edge.getFrom().equals(cell)) ? edge.getTo() : edge.getFrom();
				// only if neighbour has an allocation
				if (allocations.containsKey(neighbour)) {
					// check if this neighbour has the furthest away frequency
					Integer freqAllocation = allocations.get(neighbour);
					leftoverAllocations.remove(freqAllocation);
					double distanceBetween = Graph.getDistanceBetween(cell, neighbour);
					if (furthestAwayFreqDistance < distanceBetween) {
						furthestAwayFreq = freqAllocation;
						furthestAwayFreqDistance = distanceBetween;
					}
				} else {
					amountOfEmptyNeighboursVisited++;
				}
			}
		} else {
			// get left over allocations
			for (Integer integer : allocations.values()) {
				leftoverAllocations.remove(integer);
			}
		}

		if(leftoverAllocations.isEmpty()){
			// confirm we could get an allocation
			if(furthestAwayFreq == -1){
				throw new IllegalStateException("Could not allocate frequency");
			}
			// take furthest away
			return furthestAwayFreq;
		}
		// take first leftover allocation
		return leftoverAllocations.stream().findFirst().get();
	}

}
