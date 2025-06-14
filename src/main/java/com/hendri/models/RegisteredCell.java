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
public class RegisteredCell
{
	@NonNull
	private Cell cell;
	@NonNull
	private Integer frequency;
}
