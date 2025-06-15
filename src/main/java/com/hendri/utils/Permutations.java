/* © Copyright Hendrik Jacobus Vermeulen 2025, all rights reserved */
package com.hendri.utils;

import java.util.HashSet;
import java.util.Set;

import com.hendri.models.Cell;
import com.hendri.models.RegisteredCell;

public class Permutations
{

	public static Set<Set<RegisteredCell>> getPermutations(Set<Cell> cells, Set<Integer> frequencies){
		Set<Set<RegisteredCell>> permutations = new HashSet<>();
		for (Cell cell : cells) {
			permutations = addToPermutations(permutations, cell, frequencies);
		}
		return permutations;
	}

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
