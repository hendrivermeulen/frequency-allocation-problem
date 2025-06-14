package com.hendri.methods;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.hendri.models.Cell;
import com.hendri.models.RegisteredCell;
import com.hendri.utils.Graph;

public class GreedyAllocation
{

	public static Set<RegisteredCell> greedilyAllocate(Set<Cell> cells, Set<Integer> frequencies){
		if(frequencies.isEmpty()) {
			throw new IllegalStateException("No frequencies provided");
		}

		Set<RegisteredCell> registeredCells = new HashSet<>();
		Iterator<Integer> initialAllocation = frequencies.iterator();

		for (Cell cell : cells) {
			RegisteredCell bestReg = null;
			if(initialAllocation.hasNext()){
				bestReg = RegisteredCell.builder()
						.cell(cell).frequency(initialAllocation.next()).build();
			} else {
				double bestQuality = -1;
				for (Integer frequency : frequencies) {
					RegisteredCell possibleReg = RegisteredCell.builder()
							.frequency(frequency).cell(cell).build();
					Set<RegisteredCell> possibleSolution = new HashSet<>(registeredCells);
					possibleSolution.add(possibleReg);
					double quality = Graph.getGraphQualityMeasure(possibleSolution).getQuality();
					if(quality > bestQuality){
						bestQuality = quality;
						bestReg = possibleReg;
					}
				}
			}
			registeredCells.add(bestReg);
		}

		return registeredCells;
	}

}
