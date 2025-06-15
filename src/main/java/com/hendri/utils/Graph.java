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

public class Graph
{
	private static final HashMap<Edge, Double> pastDistanceCalculations = new HashMap<>();

	public static double getDistanceBetween(@NonNull Cell from, @NonNull Cell to){
		Objects.requireNonNull(from);
		Objects.requireNonNull(to);

		double distance = pastDistanceCalculations.computeIfAbsent(
				Edge.builder().from(from).to(to).build(),
				edge -> Math.sqrt(Math.pow(from.getEast() - to.getEast(), 2)
						+ Math.pow(from.getNorth() - to.getNorth(), 2))
		);

		pastDistanceCalculations.put(Edge.builder().from(to).to(from).build(), distance);

		return distance;
	}

	public static double getFurthestAwayWithSameFrequency(@NonNull Set<RegisteredCell> cells, @NonNull RegisteredCell from){
		Objects.requireNonNull(from);
		Objects.requireNonNull(cells);

		double furthestDistance = -1;
		boolean hasSameFrequencyNeighbours = false;

		for (RegisteredCell neighbour : cells) {
			if(!neighbour.equals(from) && neighbour.getFrequency().equals(from.getFrequency())){
				hasSameFrequencyNeighbours = true;
				double distance = getDistanceBetween(neighbour.getCell(), from.getCell());
				if(furthestDistance < distance){
					furthestDistance = distance;
				}
			}
		}


		if (furthestDistance == -1){
			if(hasSameFrequencyNeighbours){
				throw new IllegalStateException("Calculation error");
			}
			return Double.MAX_VALUE;
		}

		return furthestDistance;
	}

	public static QualityMeasure getGraphQualityMeasure(Set<RegisteredCell> permutation){
		double quality = 0;
		int count = 0;

		for (RegisteredCell cell : permutation) {
			for (RegisteredCell neighbour : permutation) {
				if(!cell.equals(neighbour)){
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

		quality = (quality/count)*100;

		if(Double.isNaN(quality)){
			throw new IllegalStateException("Calculation error");
		}

		return QualityMeasure.builder().quality(quality).registrations(permutation).build();
	}
}
