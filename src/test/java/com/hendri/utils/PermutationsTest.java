package com.hendri.utils;

import java.util.Set;

import com.hendri.models.RegisteredCell;
import org.junit.jupiter.api.Test;
import static com.hendri.utils.TestData.A;
import static com.hendri.utils.TestData.B;
import static com.hendri.utils.TestData.FREQUENCIES;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PermutationsTest
{

	@Test
	void testOnePermutation(){
		Set<Set<RegisteredCell>> expected = Set.of(
				Set.of(RegisteredCell.builder().cell(A).frequency(110).build()),
				Set.of(RegisteredCell.builder().cell(A).frequency(111).build()),
				Set.of(RegisteredCell.builder().cell(A).frequency(112).build())
		);

		Set<Set<RegisteredCell>> actual = Permutations.getPermutations(Set.of(A), FREQUENCIES);

		assertEquals(3, actual.size());
		assertEquals(expected, actual);
	}

	@Test
	void testTwoPermutation(){
		Set<Set<RegisteredCell>> expected = Set.of(
				Set.of(RegisteredCell.builder().cell(A).frequency(110).build(),
						RegisteredCell.builder().cell(B).frequency(110).build()),
				Set.of(RegisteredCell.builder().cell(A).frequency(111).build(),
						RegisteredCell.builder().cell(B).frequency(110).build()),
				Set.of(RegisteredCell.builder().cell(A).frequency(112).build(),
						RegisteredCell.builder().cell(B).frequency(110).build()),
				Set.of(RegisteredCell.builder().cell(A).frequency(110).build(),
						RegisteredCell.builder().cell(B).frequency(111).build()),
				Set.of(RegisteredCell.builder().cell(A).frequency(111).build(),
						RegisteredCell.builder().cell(B).frequency(111).build()),
				Set.of(RegisteredCell.builder().cell(A).frequency(112).build(),
						RegisteredCell.builder().cell(B).frequency(111).build()),
				Set.of(RegisteredCell.builder().cell(A).frequency(110).build(),
						RegisteredCell.builder().cell(B).frequency(112).build()),
				Set.of(RegisteredCell.builder().cell(A).frequency(111).build(),
						RegisteredCell.builder().cell(B).frequency(112).build()),
				Set.of(RegisteredCell.builder().cell(A).frequency(112).build(),
						RegisteredCell.builder().cell(B).frequency(112).build())
		);

		Set<Set<RegisteredCell>> actual = Permutations.getPermutations(Set.of(A, B), FREQUENCIES);

		assertEquals(9, actual.size());
		assertEquals(expected, actual);
	}

}