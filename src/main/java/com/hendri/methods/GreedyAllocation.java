/* © Copyright Hendrik Jacobus Vermeulen 2025, all rights reserved */
package com.hendri.methods;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import com.hendri.models.Cell;
import com.hendri.models.RegisteredCell;
import com.hendri.utils.Graph;
import lombok.NonNull;

/**
 * Holds the Greedy Allocation method
 * @author Hendrik Vermeulen
 */
public class GreedyAllocation
{

	/**
	 * Greedy method for allocating frequencies to cells
	 * @param cells The cells to allocate frequencies to
	 * @param frequencies The frequencies available to allocate
	 * @return The cells with allocated frequencies
	 */
	public static Set<RegisteredCell> greedilyAllocate(@NonNull Set<Cell> cells, @NonNull Set<Integer> frequencies){
		// validate
		Objects.requireNonNull(cells);
		Objects.requireNonNull(frequencies);
		if(cells.isEmpty() || frequencies.isEmpty()) {
			return Set.of();
		}

		// prepare
		Set<RegisteredCell> registeredCells = new HashSet<>();
		Iterator<Integer> initialAllocation = frequencies.iterator();

		// loop through each cell
		for (Cell cell : cells) {
			RegisteredCell bestReg = null;
			// first allocate frequencies to initial cells
			if(initialAllocation.hasNext()){
				bestReg = RegisteredCell.builder()
						.cell(cell).frequency(initialAllocation.next()).build();
			} else {
				double bestQuality = -1;
				// go through each frequency and allocate frequency with best result
				for (Integer frequency : frequencies) {
					// get a possible registration
					RegisteredCell possibleReg = RegisteredCell.builder()
							.frequency(frequency).cell(cell).build();
					// create solution
					Set<RegisteredCell> possibleSolution = new HashSet<>(registeredCells);
					possibleSolution.add(possibleReg);
					// get quality
					double quality = Graph.getGraphQualityMeasure(possibleSolution).getQuality();
					// take as solution if better
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
