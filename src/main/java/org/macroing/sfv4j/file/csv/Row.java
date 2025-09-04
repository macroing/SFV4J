/**
 * Copyright 2025 J&#246;rgen Lundgren
 * 
 * This file is part of SFV4J.
 * 
 * SFV4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * SFV4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with SFV4J. If not, see <http://www.gnu.org/licenses/>.
 */
package org.macroing.sfv4j.file.csv;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class Row {
	private final List<Cell> cells;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Row() {
		this.cells = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<Cell> getCells() {
		return new ArrayList<>(this.cells);
	}
	
	public Optional<Cell> getCell(final int index) {
		return index >= 0 && index < this.cells.size() ? Optional.of(this.cells.get(index)) : Optional.empty();
	}
	
	public boolean hasCell(final int index) {
		return index >= 0 && index < this.cells.size();
	}
	
	public void addCell(final Cell cell) {
		this.cells.add(Objects.requireNonNull(cell, "cell == null"));
	}
	
	public void removeCell(final Cell cell) {
		this.cells.remove(Objects.requireNonNull(cell, "cell == null"));
	}
}