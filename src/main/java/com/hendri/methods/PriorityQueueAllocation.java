/* © Copyright Hendrik Jacobus Vermeulen 2025, all rights reserved */
package com.hendri.methods;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import com.hendri.models.Cell;
import com.hendri.models.Edge;
import com.hendri.models.RegisteredCell;
import com.hendri.utils.Graph;

public class PriorityQueueAllocation
{

	public static Set<RegisteredCell> priorityAllocate(Set<Cell> cells, Set<Integer> frequencies){
		Comparator<Edge> edgeComparator = Comparator.comparingDouble(
				edge -> Graph.getDistanceBetween(edge.getFrom(), edge.getTo()));

		HashMap<Cell, PriorityQueue<Edge>> allCellPriorities = new HashMap<>();
		PriorityQueue<Edge> edgePriorities = new PriorityQueue<>(edgeComparator);

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

		HashMap<Cell, Integer> allocations = new HashMap<>();
		while (!edgePriorities.isEmpty()){
			Edge edge = edgePriorities.poll();
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
		int furthestAwayFreq = -1;
		double furthestAwayFreqDistance = -1;
		int amountOfEmptyNeighboursVisited = 0;
		Set<Integer> leftoverAllocations = new HashSet<>(freqAllocations);

		if(allocations.values().size() >= freqAllocations.size()) {
			while (!cellPriorities.isEmpty() && !leftoverAllocations.isEmpty()
					&& amountOfEmptyNeighboursVisited < freqAllocations.size()) {
				Edge edge = cellPriorities.poll();
				Cell neighbour = (edge.getFrom().equals(cell)) ? edge.getTo() : edge.getFrom();
				if (allocations.containsKey(neighbour)) {
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
			for (Integer integer : allocations.values()) {
				leftoverAllocations.remove(integer);
			}
		}

		if(leftoverAllocations.isEmpty()){
			if(furthestAwayFreq == -1){
				throw new IllegalStateException("Could not allocate frequency");
			}
			return furthestAwayFreq;
		}
		return leftoverAllocations.stream().findFirst().get();
	}

}
