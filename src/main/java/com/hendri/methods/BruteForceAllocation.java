package com.hendri.methods;

import java.util.Objects;
import java.util.Set;

import com.hendri.models.Cell;
import com.hendri.models.QualityMeasure;
import com.hendri.models.RegisteredCell;
import com.hendri.utils.Permutations;
import static com.hendri.utils.Graph.getGraphQualityMeasure;

public class BruteForceAllocation
{

	public static Set<RegisteredCell> bruteForceAllocate(Set<Cell> cells, Set<Integer> frequencies){
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
