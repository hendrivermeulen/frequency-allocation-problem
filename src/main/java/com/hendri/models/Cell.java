/* © Copyright Hendrik Jacobus Vermeulen 2025, all rights reserved */
package com.hendri.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class Cell
{
	@NonNull
	private String id;
	@NonNull
	private Double lat;
	@NonNull
	private Double lon;
	@NonNull
	private Integer east;
	@NonNull
	private Integer north;
}
