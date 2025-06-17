/* © Copyright Hendrik Jacobus Vermeulen 2025, all rights reserved */
package com.hendri.models;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * Models the quality of a registration set
 * @author Hendrik Vermeulen
 */
@Builder
@Getter
public class QualityMeasure
{

	@NonNull
	private Set<RegisteredCell> registrations;
	@NonNull
	private Double quality;

}
