/* © Copyright Hendrik Jacobus Vermeulen 2025, all rights reserved */
package com.hendri.utils;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

import com.hendri.models.Cell;
import com.hendri.models.Edge;
import com.hendri.models.QualityMeasure;
import com.hendri.models.RegisteredCell;
import lombok.NonNull;

/**
 * Utility class for Graph functions
 * @author Hendrik Vermeulen
 */
public class Graph
{
	/**
	 * Used to get past calculations of distances between cells to avoid recalculation
	 */
	private static final HashMap<Edge, Double> pastDistanceCalculations = new HashMap<>();

	public static void clearCache(){
		pastDistanceCalculations.clear();
	}

	/**
	 * Get the distance between cells using their UTM northing and easting
	 * @param from The cell starting from
	 * @param to The cell going to
	 * @return The distance between the two
	 */
	public static double getDistanceBetween(@NonNull Cell from, @NonNull Cell to){
		Objects.requireNonNull(from);
		Objects.requireNonNull(to);

		// Using UTM instead of lat lon due to the cell being so close to each other
		// This can be adapted to use the lat lon instead if the cells are not in the same
		// UTM cell
		double distance = pastDistanceCalculations.computeIfAbsent(
				Edge.builder().from(from).to(to).build(),
				edge -> Math.sqrt(Math.pow(from.getEast() - to.getEast(), 2)
						+ Math.pow(from.getNorth() - to.getNorth(), 2))
		);

		pastDistanceCalculations.put(Edge.builder().from(to).to(from).build(), distance);

		return distance;
	}

	/**
	 * Get the cell with the same frequency that is the furthest away
	 * @param cells The cells to check
	 * @param from The cell to compare other cells with
	 * @return The cell furthest away in the provided set from the cell provided
	 */
	public static double getFurthestAwayWithSameFrequency(@NonNull Set<RegisteredCell> cells, @NonNull RegisteredCell from){
		// validate
		Objects.requireNonNull(from);
		Objects.requireNonNull(cells);

		// prepare
		double furthestDistance = -1;
		boolean hasSameFrequencyNeighbours = false;

		// go through each
		for (RegisteredCell neighbour : cells) {
			// only if same frequency
			if(!neighbour.equals(from) && neighbour.getFrequency().equals(from.getFrequency())){
				hasSameFrequencyNeighbours = true;
				// check if the furthest away
				double distance = getDistanceBetween(neighbour.getCell(), from.getCell());
				if(furthestDistance < distance){
					furthestDistance = distance;
				}
			}
		}


		if (furthestDistance == -1){
			if(hasSameFrequencyNeighbours){
				// can also just return max value but useful to check for issues
				throw new IllegalStateException("Provided a set with no neighbours with the same frequency");
			}
			return Double.MAX_VALUE;
		}

		return furthestDistance;
	}

	/**
	 * Get the quality measure of the provided set
	 * @param permutation The set of registrations
	 * @return The quality of the set
	 */
	public static QualityMeasure getGraphQualityMeasure(@NonNull Set<RegisteredCell> permutation){
		// validate
		Objects.requireNonNull(permutation);

		// prepare
		double quality = 0;
		int count = 0;

		// go through each edge
		for (RegisteredCell cell : permutation) {
			for (RegisteredCell neighbour : permutation) {
				// skip same cells
				if(!cell.equals(neighbour)){
					// if different frequencies give full quality else calculate quality using current distance and furthest away
					if(cell.getFrequency().equals(neighbour.getFrequency())) {
						double distance = getDistanceBetween(cell.getCell(), neighbour.getCell());
						double furthestSameFreqCell =
								getFurthestAwayWithSameFrequency(permutation, cell);
						quality +=  (furthestSameFreqCell == 0) ? 0 : distance/furthestSameFreqCell;
					} else {
						quality += 1;
					}
					count++;
				}
			}
		}

		// convert to percentage
		quality = (quality/count)*100;

		// check for errors
		if(Double.isNaN(quality)){
			throw new IllegalStateException("Calculation error");
		}

		return QualityMeasure.builder().quality(quality).registrations(permutation).build();
	}
}
