/* © Copyright Hendrik Jacobus Vermeulen 2025, all rights reserved */
package com.hendri.models;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class QualityMeasure
{

	@NonNull
	private Set<RegisteredCell> registrations;
	@NonNull
	private Double quality;

}
