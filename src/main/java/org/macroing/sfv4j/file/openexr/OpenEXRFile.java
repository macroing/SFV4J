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
import java.util.Optional;

public final class OpenEXRFile {
	private List<Chunk> chunks;
	private List<Header> headers;
	private List<OffsetTable> offsetTables;
	private String fileName;
	private int magicNumber;
	private int version;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public OpenEXRFile() {
		this("");
	}
	
	public OpenEXRFile(final String fileName) {
		this.fileName = Objects.requireNonNull(fileName, "fileName == null");
		this.chunks = new ArrayList<>();
		this.headers = new ArrayList<>();
		this.offsetTables = new ArrayList<>();
		this.magicNumber = Constants.MAGIC_NUMBER;
		this.version = 2;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<Chunk> getChunks() {
		return new ArrayList<>(this.chunks);
	}
	
	public List<Header> getHeaders() {
		return new ArrayList<>(this.headers);
	}
	
	public List<OffsetTable> getOffsetTables() {
		return new ArrayList<>(this.offsetTables);
	}
	
	public Optional<Header> getHeader() {
		return getHeader(0);
	}
	
	public Optional<Header> getHeader(final int index) {
		return index >= 0 && index < this.headers.size() ? Optional.of(this.headers.get(index)) : Optional.empty();
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public boolean containsLongNames() {
		return (this.version & 0b00000000000000000000001000000000) != 0;
	}
	
	public boolean containsNonImageParts() {
		return (this.version & 0b00000000000000000000010000000000) != 0;
	}
	
	public boolean hasHeader() {
		return hasHeader(0);
	}
	
	public boolean hasHeader(final int index) {
		return index >= 0 && index < this.headers.size();
	}
	
	public boolean isMultiPartFileWithDeepData() {
		return (this.version & 0b00000000000000000000000100000000) == 0 && (this.version & 0b00000000000000000000010000000000) != 0 && (this.version & 0b00000000000000000000100000000000) != 0;
	}
	
	public boolean isMultiPartFileWithScanLines() {
		return (this.version & 0b00000000000000000000000100000000) == 0 && (this.version & 0b00000000000000000000010000000000) == 0 && (this.version & 0b00000000000000000000100000000000) != 0;
	}
	
	public boolean isMultiPartFileWithTiles() {
		return (this.version & 0b00000000000000000000000100000000) == 0 && (this.version & 0b00000000000000000000010000000000) == 0 && (this.version & 0b00000000000000000000100000000000) != 0;
	}
	
	public boolean isSinglePartFileWithDeepData() {
		return (this.version & 0b00000000000000000000000100000000) == 0 && (this.version & 0b00000000000000000000010000000000) != 0 && (this.version & 0b00000000000000000000100000000000) == 0;
	}
	
	public boolean isSinglePartFileWithScanLines() {
		return (this.version & 0b00000000000000000000000100000000) == 0 && (this.version & 0b00000000000000000000010000000000) == 0 && (this.version & 0b00000000000000000000100000000000) == 0;
	}
	
	public boolean isSinglePartFileWithTiles() {
		return (this.version & 0b00000000000000000000000100000000) != 0 && (this.version & 0b00000000000000000000010000000000) == 0 && (this.version & 0b00000000000000000000100000000000) == 0;
	}
	
	public int getHeight() {
		for(final Header header : this.headers) {
			for(final Attribute attribute : header.getAttributes()) {
				if(attribute instanceof DataWindowAttribute) {
					final DataWindowAttribute dataWindowAttribute = DataWindowAttribute.class.cast(attribute);
					
					return dataWindowAttribute.getHeight();
				}
			}
		}
		
		return 0;
	}
	
	public int getMagicNumber() {
		return this.magicNumber;
	}
	
	public int getVersion() {
		return this.version;
	}
	
	public int getVersionNumber() {
		return this.version & 0b00000000000000000000000011111111;
	}
	
	public int getWidth() {
		for(final Header header : this.headers) {
			for(final Attribute attribute : header.getAttributes()) {
				if(attribute instanceof DataWindowAttribute) {
					final DataWindowAttribute dataWindowAttribute = DataWindowAttribute.class.cast(attribute);
					
					return dataWindowAttribute.getWidth();
				}
			}
		}
		
		return 0;
	}
	
	public void addChunk(final Chunk chunk) {
		this.chunks.add(Objects.requireNonNull(chunk, "chunk == null"));
	}
	
	public void addHeader(final Header header) {
		this.headers.add(Objects.requireNonNull(header, "header == null"));
	}
	
	public void addOffsetTable(final OffsetTable offsetTable) {
		this.offsetTables.add(Objects.requireNonNull(offsetTable, "offsetTable == null"));
	}
	
	public void removeChunk(final Chunk chunk) {
		this.chunks.remove(Objects.requireNonNull(chunk, "chunk == null"));
	}
	
	public void removeHeader(final Header header) {
		this.headers.remove(Objects.requireNonNull(header, "header == null"));
	}
	
	public void removeOffsetTable(final OffsetTable offsetTable) {
		this.offsetTables.remove(Objects.requireNonNull(offsetTable, "offsetTable == null"));
	}
	
	public void setFileName(final String fileName) {
		this.fileName = Objects.requireNonNull(fileName, "fileName == null");
	}
	
	public void setMagicNumber(final int magicNumber) {
		this.magicNumber = magicNumber;
	}
	
	public void setVersion(final int version) {
		this.version = version;
	}
}