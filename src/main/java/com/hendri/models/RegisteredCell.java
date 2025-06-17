/* © Copyright Hendrik Jacobus Vermeulen 2025, all rights reserved */
package com.hendri.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Models a registered cell
 * @author Hendrik Vermeulen
 */
@Builder
@Getter
@EqualsAndHashCode
@ToString
public class RegisteredCell
{
	/**
	 * The cell that has been registered
	 */
	@NonNull
	private Cell cell;
	/**
	 * The frequency it has been registered on
	 */
	@NonNull
	private Integer frequency;
}
