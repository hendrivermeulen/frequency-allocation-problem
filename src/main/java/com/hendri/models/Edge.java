package com.hendri.models;

import java.util.Objects;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class Edge
{

	@NonNull
	private Cell from;
	@NonNull
	private Cell to;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Edge edge)) {
			return false;
		}
		return (Objects.equals(from, edge.from) && Objects.equals(to, edge.to));
	}

	@Override
	public int hashCode() {
		return Objects.hash(from, to);
	}
}
