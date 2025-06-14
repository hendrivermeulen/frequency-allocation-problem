package com.hendri.utils;

import java.util.Set;

import com.hendri.models.Cell;
import com.hendri.models.RegisteredCell;
import org.junit.jupiter.api.Test;
import static com.hendri.utils.TestData.A;
import static com.hendri.utils.TestData.B;
import static com.hendri.utils.TestData.C;
import static com.hendri.utils.TestData.DISTANCE_BETWEEN_A_AND_B;
import static com.hendri.utils.TestData.DISTANCE_BETWEEN_A_AND_C;
import static com.hendri.utils.TestData.Z;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphTest
{

	public static final RegisteredCell REGISTERED_A = RegisteredCell.builder().frequency(110).cell(
			A).build();
	public static final RegisteredCell REGISTERED_B = RegisteredCell.builder().frequency(110).cell(
			B).build();
	public static final RegisteredCell REGISTERED_C = RegisteredCell.builder().frequency(110).cell(
			C).build();
	public static final RegisteredCell REGISTERED_Z_DIFF_FREQ = RegisteredCell.builder().frequency(111).cell(
			Z).build();
	public static final RegisteredCell REGISTERED_Z_SAME_FREQ = RegisteredCell.builder().frequency(
			110).cell(Z).build();

	@Test
	void getDistanceBetween() {
		assertEquals(DISTANCE_BETWEEN_A_AND_B, Math.round(Graph.getDistanceBetween(A, B)));
		assertEquals(DISTANCE_BETWEEN_A_AND_C, Math.round(Graph.getDistanceBetween(A, C)));
	}

	@Test
	void getFurthestAwayWithSameFrequency() {
		assertEquals(DISTANCE_BETWEEN_A_AND_B,
				Math.round(Graph.getFurthestAwayWithSameFrequency(
						Set.of(REGISTERED_A, REGISTERED_B, REGISTERED_C, REGISTERED_Z_DIFF_FREQ),
						REGISTERED_A)));
	}

	@Test
	void getGraphQualityMeasure(){
		Cell cellA = Cell.builder().id("A").lat(0.0).lon(0.0).north(0).east(0).build();
		Cell cellB = Cell.builder().id("B").lat(0.0).lon(0.0).north(0).east(0).build();
		Cell cellC = Cell.builder().id("C").lat(0.0).lon(0.0).north(0).east(0).build();

		assertEquals(100, Graph.getGraphQualityMeasure(Set.of(
				RegisteredCell.builder().cell(cellA).frequency(110).build(),
				RegisteredCell.builder().cell(cellB).frequency(111).build(),
				RegisteredCell.builder().cell(cellC).frequency(112).build())).getQuality());

		assertEquals(0, Graph.getGraphQualityMeasure(Set.of(
				RegisteredCell.builder().cell(cellA).frequency(110).build(),
				RegisteredCell.builder().cell(cellB).frequency(110).build(),
				RegisteredCell.builder().cell(cellC).frequency(110).build())).getQuality());

		assertEquals(66, Math.floor(Graph.getGraphQualityMeasure(Set.of(
				RegisteredCell.builder().cell(cellA).frequency(110).build(),
				RegisteredCell.builder().cell(cellB).frequency(111).build(),
				RegisteredCell.builder().cell(cellC).frequency(110).build())).getQuality()));

		assertEquals(66, Math.floor(Graph.getGraphQualityMeasure(Set.of(
				RegisteredCell.builder().cell(cellA).frequency(110).build(),
				RegisteredCell.builder().cell(cellB).frequency(111).build(),
				RegisteredCell.builder().cell(cellC).frequency(111).build())).getQuality()));

		assertEquals(66, Math.floor(Graph.getGraphQualityMeasure(Set.of(
				RegisteredCell.builder().cell(cellA).frequency(111).build(),
				RegisteredCell.builder().cell(cellB).frequency(110).build(),
				RegisteredCell.builder().cell(cellC).frequency(110).build())).getQuality()));

		assertEquals(66, Math.floor(Graph.getGraphQualityMeasure(Set.of(
				RegisteredCell.builder().cell(cellA).frequency(111).build(),
				RegisteredCell.builder().cell(cellB).frequency(111).build(),
				RegisteredCell.builder().cell(cellC).frequency(110).build())).getQuality()));

		assertEquals(66, Math.floor(Graph.getGraphQualityMeasure(Set.of(
				RegisteredCell.builder().cell(cellA).frequency(110).build(),
				RegisteredCell.builder().cell(cellB).frequency(110).build(),
				RegisteredCell.builder().cell(cellC).frequency(111).build())).getQuality()));

		assertEquals(66, Math.floor(Graph.getGraphQualityMeasure(Set.of(
				RegisteredCell.builder().cell(cellA).frequency(110).build(),
				RegisteredCell.builder().cell(cellB).frequency(110).build(),
				RegisteredCell.builder().cell(cellC).frequency(111).build())).getQuality()));
	}
}