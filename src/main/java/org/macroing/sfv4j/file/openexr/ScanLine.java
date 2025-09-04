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

import java.util.List;
import java.util.Objects;

public final class ScanLine {
	private final byte[] pixelData;
	private final int yOffset;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ScanLine(final int yOffset, final byte[] pixelData) {
		this.yOffset = yOffset;
		this.pixelData = Objects.requireNonNull(pixelData, "pixelData == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public byte[] getPixelData() {
		return this.pixelData.clone();
	}
	
	public float[] toFloatArray(final ChannelsAttribute channelsAttribute, final int width) {
		final List<Channel> channels = channelsAttribute.getChannels();
		
		final int[] channelBytes = new int[channels.size()];
		
		for(int i = 0; i < channels.size(); i++) {
			final Channel channel = channels.get(i);
			
			if(channel.isPixelTypeFloat()) {
				channelBytes[i] = 4;
			} else if(channel.isPixelTypeHalf()) {
				channelBytes[i] = 2;
			} else {
				throw new IllegalArgumentException("Unsupported pixel type!");
			}
		}
		
		int pixelWidth = 0;
		
		for(int i = 0; i < channelBytes.length; i++) {
			pixelWidth += channelBytes[i];
		}
		
		if(pixelWidth * width != this.pixelData.length) {
			throw new IllegalArgumentException("Invalid data!");
		}
		
		final float[] floatArray = new float[channels.size() * width];
		
		for(int i = 0; i < this.pixelData.length;) {
			for(int j = 0; j < channelBytes.length; j++) {
				for(int k = 0; k < width; k++) {
					if(channelBytes[j] == 4) {
						final float value = doReadFloat(this.pixelData, i);
						
						floatArray[k * channels.size() + j] = value;
						
						i += 4;
					} else if(channelBytes[j] == 2) {
						final float value = doReadHalf(this.pixelData, i);
						
						floatArray[k * channels.size() + j] = value;
						
						i += 2;
					}
				}
			}
		}
		
		return floatArray;
	}
	
	public int getPixelDataSize() {
		return this.pixelData.length;
	}
	
	public int getYOffset() {
		return this.yOffset;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static float doReadFloat(final byte[] bytes, final int index) {
		return Float.intBitsToFloat(doReadInt32(bytes, index));
	}
	
	private static float doReadHalf(final byte[] bytes, final int index) {
		return doToFloat(doReadInt16(bytes, index));
	}
	
	private static float doToFloat(final int highBits) {
		int mant = highBits & 0x03FF;
		int exp = highBits & 0x7C00;
		
		if(exp == 0x7C00) {
			exp = 0x3FC00;
		} else if(exp != 0) {
			exp += 0x1C000;
			
			if(mant == 0 && exp > 0x1C400) {
				return Float.intBitsToFloat((highBits & 0x8000) << 16 | exp << 13 | 0x3FF);
			}
		} else if(mant != 0) {
			exp = 0x1C400;
			
			do {
				mant <<= 1;
				exp -= 0x400;
			} while((mant & 0x400) == 0);
			
			mant &= 0x3FF;
		}
		
		return Float.intBitsToFloat((highBits & 0x8000) << 16 | (exp | mant) << 13);
	}
	
	private static int doReadInt16(final byte[] bytes, final int index) {
		final int a = bytes[index + 0];
		final int b = bytes[index + 1];
		
		return ((a & 0xFF) << 0) | ((b & 0xFF) << 8);
	}
	
	private static int doReadInt32(final byte[] bytes, final int index) {
		final int a = bytes[index + 0];
		final int b = bytes[index + 1];
		final int c = bytes[index + 2];
		final int d = bytes[index + 3];
		
		return ((a & 0xFF) << 0) | ((b & 0xFF) << 8) | ((c & 0xFF) << 16) | ((d & 0xFF) << 24);
	}
}