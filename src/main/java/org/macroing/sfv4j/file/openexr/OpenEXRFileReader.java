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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class OpenEXRFileReader {
	public OpenEXRFileReader() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings("static-method")
	public Optional<OpenEXRFile> readOpenEXRFile(final File file) {
		try(final BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(Objects.requireNonNull(file, "file == null")))) {
			final OpenEXRFile openEXRFile = new OpenEXRFile(file.getName());
			
			final int magicNumber = doReadInt32(bufferedInputStream);
			
			if(magicNumber != Constants.MAGIC_NUMBER) {
				return Optional.empty();
			}
			
			final int version = doReadInt32(bufferedInputStream);
			
			openEXRFile.setMagicNumber(magicNumber);
			openEXRFile.setVersion(version);
			
			if(openEXRFile.isMultiPartFileWithDeepData() || openEXRFile.isMultiPartFileWithScanLines() || openEXRFile.isMultiPartFileWithTiles()) {
				String previouslyReadAttributeName = null;
				
				do {
					final Header header = new Header();
					
					final List<Attribute> attributes = doReadAttributes(bufferedInputStream, previouslyReadAttributeName);
					
					for(final Attribute attribute : attributes) {
						header.addAttribute(attribute);
					}
					
					openEXRFile.addHeader(header);
					
					previouslyReadAttributeName = doReadString(bufferedInputStream);
				} while(!previouslyReadAttributeName.equals(""));
			} else {
				final Header header = new Header();
				
				final List<Attribute> attributes = doReadAttributes(bufferedInputStream, null);
				
				for(final Attribute attribute : attributes) {
					header.addAttribute(attribute);
				}
				
				openEXRFile.addHeader(header);
			}
			
			if(openEXRFile.isSinglePartFileWithScanLines()) {
				final Optional<Header> optionalHeader = openEXRFile.getHeader();
				
				if(optionalHeader.isPresent()) {
					final Header header = optionalHeader.get();
					
					if(header.hasCompressionTypeNone()) {
						final int height = openEXRFile.getHeight();
						
						final long[] offsets = new long[height];
						
						for(int y = 0; y < height; y++) {
							offsets[y] = doReadLong64(bufferedInputStream);
						}
						
						openEXRFile.addOffsetTable(new ScanLineOffsetTable(offsets));
						
						final ScanLineChunk scanLineChunk = new ScanLineChunk();
						
						for(int y = 0; y < height; y++) {
							final int yOffset = doReadInt32(bufferedInputStream);
							final int pixelDataSize = doReadInt32(bufferedInputStream);
							
							final byte[] pixelData = bufferedInputStream.readNBytes(pixelDataSize);
							
							scanLineChunk.addScanLine(new ScanLine(yOffset, pixelData));
						}
						
						openEXRFile.addChunk(scanLineChunk);
					}
				}
			}
			
			return Optional.of(openEXRFile);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static List<Attribute> doReadAttributes(final BufferedInputStream bufferedInputStream, final String previouslyReadAttributeName) throws IOException {
		final List<Attribute> attributes = new ArrayList<>();
		
		String previouslyReadAttributeNameToReset = previouslyReadAttributeName;
		
		while(true) {
			final String attributeName = previouslyReadAttributeNameToReset != null ? previouslyReadAttributeNameToReset : doReadString(bufferedInputStream);
			
			previouslyReadAttributeNameToReset = null;
			
			if(attributeName.equals("")) {
				break;
			}
			
			final String attributeType = doReadString(bufferedInputStream);
			
			final int size = doReadInt32(bufferedInputStream);
			
			if(attributeName.equals(Constants.ATTRIBUTE_NAME_CAP_DATE) && attributeType.equals(Constants.ATTRIBUTE_TYPE_STRING)) {
				final String value = doReadString(bufferedInputStream, size);
				
				attributes.add(new CapDateAttribute(value));
			} else if(attributeName.equals(Constants.ATTRIBUTE_NAME_CHANNELS) && attributeType.equals(Constants.ATTRIBUTE_TYPE_CHLIST)) {
				final List<Channel> channels = new ArrayList<>();
				
				while(true) {
					final String name = doReadString(bufferedInputStream);
					
					if(name.equals("")) {
						break;
					}
					
					final int pixelType = doReadInt32(bufferedInputStream);
					
					final int pLinear = bufferedInputStream.read();
					
					bufferedInputStream.read();
					bufferedInputStream.read();
					bufferedInputStream.read();
					
					final int xSampling = doReadInt32(bufferedInputStream);
					final int ySampling = doReadInt32(bufferedInputStream);
					
					channels.add(new Channel(name, pixelType, pLinear, xSampling, ySampling));
				}
				
				attributes.add(new ChannelsAttribute(channels));
			} else if(attributeName.equals(Constants.ATTRIBUTE_NAME_CHROMATICITIES) && attributeType.equals(Constants.ATTRIBUTE_TYPE_CHROMATICITIES)) {
				final float redX = doReadFloat(bufferedInputStream);
				final float redY = doReadFloat(bufferedInputStream);
				final float greenX = doReadFloat(bufferedInputStream);
				final float greenY = doReadFloat(bufferedInputStream);
				final float blueX = doReadFloat(bufferedInputStream);
				final float blueY = doReadFloat(bufferedInputStream);
				final float whiteX = doReadFloat(bufferedInputStream);
				final float whiteY = doReadFloat(bufferedInputStream);
				
				attributes.add(new ChromaticitiesAttribute(redX, redY, greenX, greenY, blueX, blueY, whiteX, whiteY));
			} else if(attributeName.equals(Constants.ATTRIBUTE_NAME_COMPRESSION) && attributeType.equals(Constants.ATTRIBUTE_TYPE_COMPRESSION)) {
				final int compressionType = bufferedInputStream.read();
				
				attributes.add(new CompressionAttribute(compressionType));
			} else if(attributeName.equals(Constants.ATTRIBUTE_NAME_DATA_WINDOW) && attributeType.equals(Constants.ATTRIBUTE_TYPE_BOX2I)) {
				final int xMin = doReadInt32(bufferedInputStream);
				final int yMin = doReadInt32(bufferedInputStream);
				final int xMax = doReadInt32(bufferedInputStream);
				final int yMax = doReadInt32(bufferedInputStream);
				
				attributes.add(new DataWindowAttribute(xMin, yMin, xMax, yMax));
			} else if(attributeName.equals(Constants.ATTRIBUTE_NAME_DISPLAY_WINDOW) && attributeType.equals(Constants.ATTRIBUTE_TYPE_BOX2I)) {
				final int xMin = doReadInt32(bufferedInputStream);
				final int yMin = doReadInt32(bufferedInputStream);
				final int xMax = doReadInt32(bufferedInputStream);
				final int yMax = doReadInt32(bufferedInputStream);
				
				attributes.add(new DisplayWindowAttribute(xMin, yMin, xMax, yMax));
			} else if(attributeName.equals(Constants.ATTRIBUTE_NAME_EXIF_IMAGE_HISTORY) && attributeType.equals(Constants.ATTRIBUTE_TYPE_STRING)) {
				final String value = doReadString(bufferedInputStream, size);
				
				attributes.add(new ExifImageHistoryAttribute(value));
			} else if(attributeName.equals(Constants.ATTRIBUTE_NAME_GENERATED_BY) && attributeType.equals(Constants.ATTRIBUTE_TYPE_STRING)) {
				final String generatedBy = doReadString(bufferedInputStream, size);
				
				attributes.add(new GeneratedByAttribute(generatedBy));
			} else if(attributeName.equals(Constants.ATTRIBUTE_NAME_LINE_ORDER) && attributeType.equals(Constants.ATTRIBUTE_TYPE_LINE_ORDER)) {
				final int lineOrder = bufferedInputStream.read();
				
				attributes.add(new LineOrderAttribute(lineOrder));
			} else if(attributeName.equals(Constants.ATTRIBUTE_NAME_NAME) && attributeType.equals(Constants.ATTRIBUTE_TYPE_STRING)) {
				final String value = doReadString(bufferedInputStream, size);
				
				attributes.add(new NameAttribute(value));
			} else if(attributeName.equals(Constants.ATTRIBUTE_NAME_ORIENTATION) && attributeType.equals(Constants.ATTRIBUTE_TYPE_INT)) {
				final int orientation = doReadInt32(bufferedInputStream);
				
				attributes.add(new OrientationAttribute(orientation));
			} else if(attributeName.equals(Constants.ATTRIBUTE_NAME_ORIGINAL_DATA_WINDOW) && attributeType.equals(Constants.ATTRIBUTE_TYPE_BOX2I)) {
				final int xMin = doReadInt32(bufferedInputStream);
				final int yMin = doReadInt32(bufferedInputStream);
				final int xMax = doReadInt32(bufferedInputStream);
				final int yMax = doReadInt32(bufferedInputStream);
				
				attributes.add(new OriginalDataWindowAttribute(xMin, yMin, xMax, yMax));
			} else if(attributeName.equals(Constants.ATTRIBUTE_NAME_PIXEL_ASPECT_RATIO) && attributeType.equals(Constants.ATTRIBUTE_TYPE_FLOAT)) {
				final float pixelAspectRatio = doReadFloat(bufferedInputStream);
				
				attributes.add(new PixelAspectRatioAttribute(pixelAspectRatio));
			} else if(attributeName.equals(Constants.ATTRIBUTE_NAME_SCREEN_WINDOW_CENTER) && attributeType.equals(Constants.ATTRIBUTE_TYPE_V2F)) {
				final float x = doReadFloat(bufferedInputStream);
				final float y = doReadFloat(bufferedInputStream);
				
				attributes.add(new ScreenWindowCenterAttribute(x, y));
			} else if(attributeName.equals(Constants.ATTRIBUTE_NAME_SCREEN_WINDOW_WIDTH) && attributeType.equals(Constants.ATTRIBUTE_TYPE_FLOAT)) {
				final float screenWindowWidth = doReadFloat(bufferedInputStream);
				
				attributes.add(new ScreenWindowWidthAttribute(screenWindowWidth));
			} else if(attributeName.equals(Constants.ATTRIBUTE_NAME_SOFTWARE) && attributeType.equals(Constants.ATTRIBUTE_TYPE_STRING)) {
				final String value = doReadString(bufferedInputStream, size);
				
				attributes.add(new SoftwareAttribute(value));
			} else if(attributeName.equals(Constants.ATTRIBUTE_NAME_TYPE) && attributeType.equals(Constants.ATTRIBUTE_TYPE_STRING)) {
				final String value = doReadString(bufferedInputStream, size);
				
				attributes.add(new TypeAttribute(value));
			} else if(attributeName.equals(Constants.ATTRIBUTE_NAME_VERSION) && attributeType.equals(Constants.ATTRIBUTE_TYPE_INT)) {
				final int version = doReadInt32(bufferedInputStream);
				
				attributes.add(new VersionAttribute(version));
			} else {
				final byte[] bytes = new byte[size];
				
				bufferedInputStream.read(bytes);
				
				attributes.add(new UnknownAttribute(attributeName, attributeType, bytes));
			}
		}
		
		return attributes;
	}
	
	private static String doReadString(final BufferedInputStream bufferedInputStream) throws IOException {
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(int character = bufferedInputStream.read(); character != 0; character = bufferedInputStream.read()) {
			stringBuilder.append((char)(character));
		}
		
		return stringBuilder.toString();
	}
	
	private static String doReadString(final BufferedInputStream bufferedInputStream, final int length) throws IOException {
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(int i = 0; i < length; i++) {
			stringBuilder.append((char)(bufferedInputStream.read()));
		}
		
		return stringBuilder.toString();
	}
	
	private static float doReadFloat(final BufferedInputStream bufferedInputStream) throws IOException {
		return Float.intBitsToFloat(doReadInt32(bufferedInputStream));
	}
	
	private static int doReadInt32(final BufferedInputStream bufferedInputStream) throws IOException {
		final int a = bufferedInputStream.read();
		final int b = bufferedInputStream.read();
		final int c = bufferedInputStream.read();
		final int d = bufferedInputStream.read();
		
		return ((a & 0xFF) << 0) | ((b & 0xFF) << 8) | ((c & 0xFF) << 16) | ((d & 0xFF) << 24);
	}
	
	private static long doReadLong64(final BufferedInputStream bufferedInputStream) throws IOException {
		final int a = bufferedInputStream.read();
		final int b = bufferedInputStream.read();
		final int c = bufferedInputStream.read();
		final int d = bufferedInputStream.read();
		final int e = bufferedInputStream.read();
		final int f = bufferedInputStream.read();
		final int g = bufferedInputStream.read();
		final int h = bufferedInputStream.read();
		
		return ((a & 0xFF) << 0) | ((b & 0xFF) << 8) | ((c & 0xFF) << 16) | ((d & 0xFF) << 24) | ((e & 0xFF) << 32) | ((f & 0xFF) << 40) | ((g & 0xFF) << 48) | ((h & 0xFF) << 56);
	}
}