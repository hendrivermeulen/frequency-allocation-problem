/* © Copyright Hendrik Jacobus Vermeulen 2025, all rights reserved */
package com.hendri.utils;

import java.util.HashSet;
import java.util.Set;

import com.hendri.models.Cell;
import com.hendri.models.RegisteredCell;

/**
 * Utility class for generating permutations
 * @author Hendrik Vermeulen
 */
public class Permutations
{

	/**
	 * Get a set of all permutations of frequencies allocated to different cells
	 * @param cells The cells to allocate frequencies to
	 * @param frequencies The frequencies to allocate
	 * @return The set of all possible allocations
	 */
	public static Set<Set<RegisteredCell>> getPermutations(Set<Cell> cells, Set<Integer> frequencies){
		Set<Set<RegisteredCell>> permutations = new HashSet<>();
		for (Cell cell : cells) {
			permutations = addToPermutations(permutations, cell, frequencies);
		}
		return permutations;
	}

	/**
	 * Generate a permutation
	 * @param cell the cell to generate permutations of
	 * @param frequencies The frequencies to use to generate permutations
	 * @return The permutations
	 */
	private static Set<RegisteredCell> getCellPermutations(Cell cell, Set<Integer> frequencies){
		Set<RegisteredCell> cellPermutations = new HashSet<>();
		for (Integer frequency : frequencies) {
			cellPermutations.add(RegisteredCell.builder()
					.cell(cell)
					.frequency(frequency)
					.build());
		}
		return cellPermutations;
	}

	/**
	 * Add permutation to current permutations
	 * @param currentPermutations Set of all current permutations
	 * @param cell The cell to add permutations to
	 * @param frequencies The frequencies to use to generate permutations
	 * @return The set of new permutations
	 */
	private static Set<Set<RegisteredCell>> addToPermutations(Set<Set<RegisteredCell>> currentPermutations,
			Cell cell, Set<Integer> frequencies){
		Set<RegisteredCell> cellPermutations = getCellPermutations(cell, frequencies);
		Set<Set<RegisteredCell>> permutations = new HashSet<>();

		if(currentPermutations.isEmpty()){
			for (RegisteredCell cellPermutation : cellPermutations) {
				HashSet<RegisteredCell> permutation = new HashSet<>();
				permutation.add(cellPermutation);
				permutations.add(permutation);
			}
		} else {
			for (Set<RegisteredCell> permutation : currentPermutations) {
				for (RegisteredCell cellPermutation : cellPermutations) {
					Set<RegisteredCell> newPermutation = new HashSet<>(permutation);
					newPermutation.add(cellPermutation);
					permutations.add(newPermutation);
				}
			}
		}

		return permutations;
	}

}
