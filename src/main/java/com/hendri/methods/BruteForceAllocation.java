/* © Copyright Hendrik Jacobus Vermeulen 2025, all rights reserved */
package com.hendri.methods;

import java.util.Objects;
import java.util.Set;

import com.hendri.models.Cell;
import com.hendri.models.QualityMeasure;
import com.hendri.models.RegisteredCell;
import com.hendri.utils.Permutations;
import lombok.NonNull;
import static com.hendri.utils.Graph.getGraphQualityMeasure;

/**
 * Holds the Brute Force Allocation method
 * @author Hendrik Vermeulen
 */
public class BruteForceAllocation
{

	/**
	 * Brute Force method for allocating frequencies to cells
	 * @param cells The cells to allocate frequencies to
	 * @param frequencies The frequencies available to allocate
	 * @return The cells with allocated frequencies
	 */
	public static Set<RegisteredCell> bruteForceAllocate(@NonNull Set<Cell> cells, @NonNull Set<Integer> frequencies){
		// validate
		Objects.requireNonNull(cells);
		Objects.requireNonNull(frequencies);
		if(cells.isEmpty() || frequencies.isEmpty()) {
			return Set.of();
		}

		// get all permutations
		Set<Set<RegisteredCell>> permutations = Permutations.getPermutations(cells, frequencies);

		// find best
		QualityMeasure best = null;
		for (Set<RegisteredCell> permutation : permutations) {
			QualityMeasure qualityMeasure = getGraphQualityMeasure(permutation);
			if(best == null || qualityMeasure.getQuality() > best.getQuality()){
				best = qualityMeasure;
			}
		}

		return Objects.requireNonNull(best).getRegistrations();
	}
	
}
