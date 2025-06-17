/* © Copyright Hendrik Jacobus Vermeulen 2025, all rights reserved */
package com.hendri.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Models a unregistered cell
 * @author Hendrik Vermeulen
 */
@Builder
@Getter
@EqualsAndHashCode
@ToString
public class Cell
{
	/**
	 * The id of the cell
	 */
	@NonNull
	private String id;
	/**
	 * The latitude of the cell
	 */
	@NonNull
	private Double lat;
	/**
	 * The longitude of the cell
	 */
	@NonNull
	private Double lon;
	/**
	 * The UTM easting of the cell
	 */
	@NonNull
	private Integer east;
	/**
	 * The UTM nothing of the cell
	 */
	@NonNull
	private Integer north;
}
