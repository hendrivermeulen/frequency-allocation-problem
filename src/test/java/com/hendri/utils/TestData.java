package com.hendri.utils;

import java.util.Set;

import com.hendri.models.Cell;
import com.hendri.models.RegisteredCell;

public class TestData
{

	public static final Set<Integer> FREQUENCIES = Set.of(110, 111, 112);

	public static final Cell A = Cell.builder().id("A").lat(-0.03098).lon(51.53657).east(536660).north(183800).build();
	public static final Cell B = Cell.builder().id("B").lat(-0.02554).lon(51.53833).east(4537032).north(184006).build();
	public static final Cell C = Cell.builder().id("C").lat(-0.02448).lon(51.53721).east(537109).north(183884).build();
	public static final Cell Y = Cell.builder().id("Y").lat(-0.02554).lon(51.53833).east(4537032).north(184006).build();
	public static final Cell Z = Cell.builder().id("Z").lat(-0.02554).lon(51.53900).east(4537032).north(189006).build();

	public static final double DISTANCE_BETWEEN_A_AND_B = 4000372;
	public static final double DISTANCE_BETWEEN_A_AND_C = 457;
	public static final double DISTANCE_BETWEEN_A_AND_Z = 4000375;

}
