package com.hendri.methods;

import java.util.Iterator;
import java.util.Set;

import com.hendri.models.Cell;
import com.hendri.models.RegisteredCell;
import com.hendri.utils.Graph;
import org.junit.jupiter.api.Test;
import static com.hendri.utils.TestData.A;
import static com.hendri.utils.TestData.B;
import static com.hendri.utils.TestData.C;
import static com.hendri.utils.TestData.FREQUENCIES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GreedyAllocationTest
{

	@Test
	void greedyNoInterference(){
		Set<Cell> testData = Set.of(A, B, C);
		Set<RegisteredCell> actual = GreedyAllocation.greedilyAllocate(testData, FREQUENCIES);

		Iterator<RegisteredCell> iterator = actual.iterator();
		RegisteredCell first = iterator.next();
		RegisteredCell second = iterator.next();
		RegisteredCell third = iterator.next();

		assertEquals(100, Graph.getGraphQualityMeasure(actual).getQuality());
		assertNotEquals(first.getFrequency(), second.getFrequency());
		assertNotEquals(first.getFrequency(), third.getFrequency());
		assertNotEquals(second.getFrequency(), third.getFrequency());
	}

	@Test
	void greedyPredictiveInterference(){
		Cell cellA = Cell.builder().id("A").lat(0.0).lon(0.0).north(0).east(0).build();
		Cell cellB = Cell.builder().id("B").lat(0.0).lon(0.0).north(0).east(0).build();
		Cell cellC = Cell.builder().id("C").lat(0.0).lon(0.0).north(0).east(0).build();
		Cell cellD = Cell.builder().id("D").lat(0.0).lon(0.0).north(100).east(100).build();

		Set<RegisteredCell> actual = GreedyAllocation.greedilyAllocate(
				Set.of(cellA, cellB, cellC, cellD), FREQUENCIES);

		assertEquals(100, Graph.getGraphQualityMeasure(actual).getQuality());
	}

	@Test
	void greedyDualInterference(){
		Cell cellA = Cell.builder().id("A").lat(0.0).lon(0.0).north(0).east(0).build();
		Cell cellB = Cell.builder().id("B").lat(0.0).lon(0.0).north(0).east(0).build();
		Cell cellC = Cell.builder().id("C").lat(0.0).lon(0.0).north(0).east(0).build();
		Cell cellD = Cell.builder().id("D").lat(0.0).lon(0.0).north(100).east(100).build();
		Cell cellE = Cell.builder().id("E").lat(0.0).lon(0.0).north(100).east(100).build();

		Set<RegisteredCell> actual = GreedyAllocation.greedilyAllocate(
				Set.of(cellA, cellB, cellC, cellD, cellE), FREQUENCIES);

		assertEquals(100, Graph.getGraphQualityMeasure(actual).getQuality());
	}

}