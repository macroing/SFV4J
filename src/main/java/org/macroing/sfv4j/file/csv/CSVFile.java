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

public final class CSVFile {
	private final List<Row> rows;
	private final boolean hasHeader;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public CSVFile() {
		this(false);
	}
	
	public CSVFile(final boolean hasHeader) {
		this.hasHeader = hasHeader;
		this.rows = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<Row> getRows() {
		return new ArrayList<>(this.rows);
	}
	
	public Optional<Row> getRow(final int index) {
		return index >= 0 && index < this.rows.size() ? Optional.of(this.rows.get(index)) : Optional.empty();
	}
	
	public boolean hasHeader() {
		return this.hasHeader;
	}
	
	public void addRow(final Row row) {
		this.rows.add(Objects.requireNonNull(row, "row == null"));
	}
	
	public void removeRow(final Row row) {
		this.rows.remove(Objects.requireNonNull(row, "row == null"));
	}
}