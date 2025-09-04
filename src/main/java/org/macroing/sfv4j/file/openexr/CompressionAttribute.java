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

public final class CompressionAttribute extends Attribute {
	private final int compressionType;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public CompressionAttribute(final int compressionType) {
		super(Constants.ATTRIBUTE_NAME_COMPRESSION, Constants.ATTRIBUTE_TYPE_COMPRESSION);
		
		this.compressionType = compressionType;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getCompressionName() {
		switch(this.compressionType) {
			case Constants.COMPRESSION_TYPE_NONE:
				return "None";
			case Constants.COMPRESSION_TYPE_PIZ:
				return "PIZ";
			case Constants.COMPRESSION_TYPE_R_L_E:
				return "RLE";
			case Constants.COMPRESSION_TYPE_ZIP:
				return "ZIP";
			case Constants.COMPRESSION_TYPE_ZIPS:
				return "ZIPS";
			default:
				return "Unknown";
		}
	}
	
	@Override
	public String toString() {
		return String.format("CompressionAttribute(compressionType = %s, compressionName = \"%s\")", Integer.toString(this.compressionType), getCompressionName());
	}
	
	public int getCompressionType() {
		return this.compressionType;
	}
}