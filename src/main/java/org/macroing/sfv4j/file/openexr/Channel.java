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

import java.util.Objects;

public final class Channel {
	private final String name;
	private final int pLinear;
	private final int pixelType;
	private final int xSampling;
	private final int ySampling;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Channel(final String name, final int pixelType, final int pLinear, final int xSampling, final int ySampling) {
		this.name = Objects.requireNonNull(name, "name == null");
		this.pixelType = pixelType;
		this.pLinear = pLinear;
		this.xSampling = xSampling;
		this.ySampling = ySampling;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getName() {
		return this.name;
	}
	
	public String getPixelTypeName() {
		switch(this.pixelType) {
			case Constants.PIXEL_TYPE_FLOAT:
				return Constants.PIXEL_TYPE_NAME_FLOAT;
			case Constants.PIXEL_TYPE_HALF:
				return Constants.PIXEL_TYPE_NAME_HALF;
			case Constants.PIXEL_TYPE_U_INT:
				return Constants.PIXEL_TYPE_NAME_U_INT;
			default:
				return Constants.PIXEL_TYPE_NAME_UNKNOWN;
		}
	}
	
	@Override
	public String toString() {
		return String.format("Channel(name = \"%s\", pLinear = %s, pixelType = %s, xSampling = %s, ySampling = %s)", this.name, Integer.toString(this.pLinear), Integer.toString(this.pixelType), Integer.toString(this.xSampling), Integer.toString(this.ySampling));
	}
	
	public boolean isPixelTypeFloat() {
		return this.pixelType == Constants.PIXEL_TYPE_FLOAT;
	}
	
	public boolean isPixelTypeHalf() {
		return this.pixelType == Constants.PIXEL_TYPE_HALF;
	}
	
	public boolean isPixelTypeUInt() {
		return this.pixelType == Constants.PIXEL_TYPE_U_INT;
	}
	
	public int getPLinear() {
		return this.pLinear;
	}
	
	public int getPixelType() {
		return this.pixelType;
	}
	
	public int getXSampling() {
		return this.xSampling;
	}
	
	public int getYSampling() {
		return this.ySampling;
	}
}