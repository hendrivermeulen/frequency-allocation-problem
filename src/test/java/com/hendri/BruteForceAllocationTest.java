package com.hendri;

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
import static com.hendri.utils.TestData.Y;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BruteForceAllocationTest
{

	@Test
	void bruteForceNoInterference(){
		Set<Cell> testData = Set.of(A, B, C);
		Set<RegisteredCell> actual = BruteForceAllocation.bruteForceAllocate(testData, FREQUENCIES);

		Set<RegisteredCell> expected = Set.of(
				RegisteredCell.builder().cell(A).frequency(110).build(),
				RegisteredCell.builder().cell(B).frequency(111).build(),
				RegisteredCell.builder().cell(C).frequency(112).build()
		);

		assertEquals(testData.size(), actual.size());
		assertEquals(expected, actual);
	}

	@Test
	void bruteForceInterference(){
		Set<Cell> testData = Set.of(A, B, C, Y);
		Set<RegisteredCell> actual = BruteForceAllocation.bruteForceAllocate(testData, FREQUENCIES);

		assertEquals(testData.size(), actual.size());

		RegisteredCell registeredB = actual.stream().filter(
				registeredCell -> registeredCell.getCell().equals(B)).findFirst().get();
		RegisteredCell registeredY = actual.stream().filter(
				registeredCell -> registeredCell.getCell().equals(Y)).findFirst().get();

		assertNotEquals(registeredB.getFrequency(), registeredY.getFrequency());
	}

	@Test
	void getGraphQualityMeasureSameSite(){
		Cell cellA = Cell.builder().id("A").lat(0.0).lon(0.0).north(0).east(0).build();
		Cell cellB = Cell.builder().id("B").lat(0.0).lon(0.0).north(0).east(0).build();
		Cell cellC = Cell.builder().id("C").lat(0.0).lon(0.0).north(0).east(0).build();

		Set<RegisteredCell> actual = BruteForceAllocation.bruteForceAllocate(
				Set.of(cellA, cellB, cellC), FREQUENCIES);

		Iterator<RegisteredCell> iterator = actual.iterator();
		RegisteredCell first = iterator.next();
		RegisteredCell second = iterator.next();
		RegisteredCell third = iterator.next();

		assertEquals(100, Graph.getGraphQualityMeasure(actual).getQuality());
		assertNotEquals(first.getFrequency(), second.getFrequency());
		assertNotEquals(first.getFrequency(), third.getFrequency());
		assertNotEquals(second.getFrequency(), third.getFrequency());

		assertTrue(FREQUENCIES.contains(first.getFrequency()));
		assertTrue(FREQUENCIES.contains(second.getFrequency()));
		assertTrue(FREQUENCIES.contains(third.getFrequency()));
	}

	@Test
	void getGraphQualityMeasure(){
		Cell cellA = Cell.builder().id("A").lat(0.0).lon(0.0).north(0).east(0).build();
		Cell cellB = Cell.builder().id("B").lat(0.0).lon(0.0).north(0).east(0).build();
		Cell cellC = Cell.builder().id("C").lat(0.0).lon(0.0).north(0).east(0).build();
		Cell cellD = Cell.builder().id("D").lat(0.0).lon(0.0).north(100).east(100).build();

		Set<RegisteredCell> actual = BruteForceAllocation.bruteForceAllocate(
				Set.of(cellA, cellB, cellC, cellD), FREQUENCIES);

		assertEquals(100, Graph.getGraphQualityMeasure(actual).getQuality());
	}

}