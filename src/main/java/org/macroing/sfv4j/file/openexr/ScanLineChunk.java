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
package org.macroing.sfv4j.file.openexr;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ScanLineChunk implements Chunk {
	private List<ScanLine> scanLines;
	private long partNumber;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ScanLineChunk() {
		this(0L);
	}
	
	public ScanLineChunk(final long partNumber) {
		this.partNumber = partNumber;
		this.scanLines = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<ScanLine> getScanLines() {
		return new ArrayList<>(this.scanLines);
	}
	
	@Override
	public long getPartNumber() {
		return this.partNumber;
	}
	
	public void addScanLine(final ScanLine scanLine) {
		this.scanLines.add(Objects.requireNonNull(scanLine, "scanLine == null"));
	}
	
	public void removeScanLine(final ScanLine scanLine) {
		this.scanLines.remove(Objects.requireNonNull(scanLine, "scanLine == null"));
	}
	
	public void setPartNumber(final long partNumber) {
		this.partNumber = partNumber;
	}
}