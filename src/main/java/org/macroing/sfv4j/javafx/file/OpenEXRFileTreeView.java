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
package org.macroing.sfv4j.javafx.file;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.macroing.sfv4j.file.openexr.Attribute;
import org.macroing.sfv4j.file.openexr.CapDateAttribute;
import org.macroing.sfv4j.file.openexr.Channel;
import org.macroing.sfv4j.file.openexr.ChannelsAttribute;
import org.macroing.sfv4j.file.openexr.ChromaticitiesAttribute;
import org.macroing.sfv4j.file.openexr.CompressionAttribute;
import org.macroing.sfv4j.file.openexr.DataWindowAttribute;
import org.macroing.sfv4j.file.openexr.DisplayWindowAttribute;
import org.macroing.sfv4j.file.openexr.ExifImageHistoryAttribute;
import org.macroing.sfv4j.file.openexr.GeneratedByAttribute;
import org.macroing.sfv4j.file.openexr.Header;
import org.macroing.sfv4j.file.openexr.LineOrderAttribute;
import org.macroing.sfv4j.file.openexr.NameAttribute;
import org.macroing.sfv4j.file.openexr.OpenEXRFile;
import org.macroing.sfv4j.file.openexr.OrientationAttribute;
import org.macroing.sfv4j.file.openexr.OriginalDataWindowAttribute;
import org.macroing.sfv4j.file.openexr.PixelAspectRatioAttribute;
import org.macroing.sfv4j.file.openexr.ScreenWindowCenterAttribute;
import org.macroing.sfv4j.file.openexr.ScreenWindowWidthAttribute;
import org.macroing.sfv4j.file.openexr.SoftwareAttribute;
import org.macroing.sfv4j.file.openexr.TypeAttribute;
import org.macroing.sfv4j.file.openexr.UnknownAttribute;
import org.macroing.sfv4j.file.openexr.VersionAttribute;
import org.macroing.sfv4j.javafx.scene.control.ObjectTreeView;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public final class OpenEXRFileTreeView extends HBox {
	private final ObjectTreeView<String, Object> objectTreeView;
	private final OpenEXRFile openEXRFile;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public OpenEXRFileTreeView(final OpenEXRFile openEXRFile) {
		this.openEXRFile = Objects.requireNonNull(openEXRFile, "openEXRFile == null");
		this.objectTreeView = doCreateObjectTreeView(this.openEXRFile);
		
		setBorder(new Border(new BorderStroke(Color.rgb(181, 181, 181), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0.0D, 0.0D, 0.0D, 1.0D))));
		setFillHeight(true);
		setPadding(new Insets(10.0D, 10.0D, 10.0D, 10.0D));
		setSpacing(20.0D);
		
		getChildren().add(this.objectTreeView);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Function<Object, ContextMenu> doCreateMapperUToContextMenu() {
		return object -> {
			return null;
		};
	}
	
	private static Function<Object, List<Object>> doCreateMapperUToListU() {
		return object -> {
			final List<Object> list = new ArrayList<>();
			
			if(object instanceof Attribute) {
				if(object instanceof CapDateAttribute) {
					final CapDateAttribute capDateAttribute = CapDateAttribute.class.cast(object);
					
					list.add(new Data("Value", capDateAttribute.getValue()));
				} else if(object instanceof ChannelsAttribute) {
					final ChannelsAttribute channelsAttribute = ChannelsAttribute.class.cast(object);
					
					final List<Channel> channels = channelsAttribute.getChannels();
					
					for(final Channel channel : channels) {
						list.add(channel);
					}
				} else if(object instanceof ChromaticitiesAttribute) {
					final ChromaticitiesAttribute chromaticitiesAttribute = ChromaticitiesAttribute.class.cast(object);
					
					list.add(new Data("redX", Float.toString(chromaticitiesAttribute.getRedX())));
					list.add(new Data("redY", Float.toString(chromaticitiesAttribute.getRedY())));
					list.add(new Data("greenX", Float.toString(chromaticitiesAttribute.getGreenX())));
					list.add(new Data("greenY", Float.toString(chromaticitiesAttribute.getGreenY())));
					list.add(new Data("blueX", Float.toString(chromaticitiesAttribute.getBlueX())));
					list.add(new Data("blueY", Float.toString(chromaticitiesAttribute.getBlueY())));
					list.add(new Data("whiteX", Float.toString(chromaticitiesAttribute.getWhiteX())));
					list.add(new Data("whiteY", Float.toString(chromaticitiesAttribute.getWhiteY())));
				} else if(object instanceof CompressionAttribute) {
					final CompressionAttribute compressionAttribute = CompressionAttribute.class.cast(object);
					
					list.add(new Data("compressionType", Integer.toString(compressionAttribute.getCompressionType())));
					list.add(new Data("compressionName", compressionAttribute.getCompressionName()));
				} else if(object instanceof DataWindowAttribute) {
					final DataWindowAttribute dataWindowAttribute = DataWindowAttribute.class.cast(object);
					
					list.add(new Data("xMin", Integer.toString(dataWindowAttribute.getXMin())));
					list.add(new Data("yMin", Integer.toString(dataWindowAttribute.getYMin())));
					list.add(new Data("xMax", Integer.toString(dataWindowAttribute.getXMax())));
					list.add(new Data("yMax", Integer.toString(dataWindowAttribute.getYMax())));
				} else if(object instanceof DisplayWindowAttribute) {
					final DisplayWindowAttribute displayWindowAttribute = DisplayWindowAttribute.class.cast(object);
					
					list.add(new Data("xMin", Integer.toString(displayWindowAttribute.getXMin())));
					list.add(new Data("yMin", Integer.toString(displayWindowAttribute.getYMin())));
					list.add(new Data("xMax", Integer.toString(displayWindowAttribute.getXMax())));
					list.add(new Data("yMax", Integer.toString(displayWindowAttribute.getYMax())));
				} else if(object instanceof ExifImageHistoryAttribute) {
					final ExifImageHistoryAttribute exifImageHistoryAttribute = ExifImageHistoryAttribute.class.cast(object);
					
					list.add(new Data("value", exifImageHistoryAttribute.getValue()));
				} else if(object instanceof GeneratedByAttribute) {
					final GeneratedByAttribute generatedByAttribute = GeneratedByAttribute.class.cast(object);
					
					list.add(new Data("generatedBy", generatedByAttribute.getGeneratedBy()));
				} else if(object instanceof LineOrderAttribute) {
					final LineOrderAttribute lineOrderAttribute = LineOrderAttribute.class.cast(object);
					
					list.add(new Data("lineOrder", Integer.toString(lineOrderAttribute.getLineOrder())));
				} else if(object instanceof NameAttribute) {
					final NameAttribute nameAttribute = NameAttribute.class.cast(object);
					
					list.add(new Data("value", nameAttribute.getValue()));
				} else if(object instanceof OrientationAttribute) {
					final OrientationAttribute orientationAttribute = OrientationAttribute.class.cast(object);
					
					list.add(new Data("orientation", Integer.toString(orientationAttribute.getOrientation())));
				} else if(object instanceof OriginalDataWindowAttribute) {
					final OriginalDataWindowAttribute originalDataWindowAttribute = OriginalDataWindowAttribute.class.cast(object);
					
					list.add(new Data("xMin", Integer.toString(originalDataWindowAttribute.getXMin())));
					list.add(new Data("yMin", Integer.toString(originalDataWindowAttribute.getYMin())));
					list.add(new Data("xMax", Integer.toString(originalDataWindowAttribute.getXMax())));
					list.add(new Data("yMax", Integer.toString(originalDataWindowAttribute.getYMax())));
				} else if(object instanceof PixelAspectRatioAttribute) {
					final PixelAspectRatioAttribute pixelAspectRatioAttribute = PixelAspectRatioAttribute.class.cast(object);
					
					list.add(new Data("pixelAspectRatio", Float.toString(pixelAspectRatioAttribute.getPixelAspectRatio())));
				} else if(object instanceof ScreenWindowCenterAttribute) {
					final ScreenWindowCenterAttribute screenWindowCenterAttribute = ScreenWindowCenterAttribute.class.cast(object);
					
					list.add(new Data("x", Float.toString(screenWindowCenterAttribute.getX())));
					list.add(new Data("y", Float.toString(screenWindowCenterAttribute.getY())));
				} else if(object instanceof ScreenWindowWidthAttribute) {
					final ScreenWindowWidthAttribute screenWindowWidthAttribute = ScreenWindowWidthAttribute.class.cast(object);
					
					list.add(new Data("screenWindowWidth", Float.toString(screenWindowWidthAttribute.getScreenWindowWidth())));
				} else if(object instanceof SoftwareAttribute) {
					final SoftwareAttribute softwareAttribute = SoftwareAttribute.class.cast(object);
					
					list.add(new Data("value", softwareAttribute.getValue()));
				} else if(object instanceof TypeAttribute) {
					final TypeAttribute typeAttribute = TypeAttribute.class.cast(object);
					
					list.add(new Data("value", typeAttribute.getValue()));
				} else if(object instanceof UnknownAttribute) {
					final UnknownAttribute unknownAttribute = UnknownAttribute.class.cast(object);
					
					list.add(new Data("size", Integer.toString(unknownAttribute.getBytes().length)));
				} else if(object instanceof VersionAttribute) {
					final VersionAttribute versionAttribute = VersionAttribute.class.cast(object);
					
					list.add(new Data("version", Integer.toString(versionAttribute.getVersion())));
				}
			} else if(object instanceof Channel) {
				final Channel channel = Channel.class.cast(object);
				
				list.add(new Data("pixelType", Integer.toString(channel.getPixelType())));
				list.add(new Data("pixelTypeName", channel.getPixelTypeName()));
				list.add(new Data("pLinear", Integer.toString(channel.getPLinear())));
				list.add(new Data("xSampling", Integer.toString(channel.getXSampling())));
				list.add(new Data("ySampling", Integer.toString(channel.getYSampling())));
			} else if(object instanceof Header) {
				final Header header = Header.class.cast(object);
				
				final List<Attribute> attributes = header.getAttributes();
				
				for(final Attribute attribute : attributes) {
					list.add(attribute);
				}
			} else if(object instanceof OpenEXRFile) {
				final OpenEXRFile openEXRFile = OpenEXRFile.class.cast(object);
				
				list.add(new Data("magicNumber", Integer.toString(openEXRFile.getMagicNumber())));
				list.add(new Data("version", Integer.toString(openEXRFile.getVersion())));
				list.add(new Data("versionNumber", Integer.toString(openEXRFile.getVersionNumber())));
				list.add(new Data("containsLongNames", Boolean.toString(openEXRFile.containsLongNames())));
				list.add(new Data("containsNonImageParts", Boolean.toString(openEXRFile.containsNonImageParts())));
				list.add(new Data("isMultiPartFileWithDeepData", Boolean.toString(openEXRFile.isMultiPartFileWithDeepData())));
				list.add(new Data("isMultiPartFileWithScanLines", Boolean.toString(openEXRFile.isMultiPartFileWithScanLines())));
				list.add(new Data("isMultiPartFileWithTiles", Boolean.toString(openEXRFile.isMultiPartFileWithTiles())));
				list.add(new Data("isSinglePartFileWithDeepData", Boolean.toString(openEXRFile.isSinglePartFileWithDeepData())));
				list.add(new Data("isSinglePartFileWithScanLines", Boolean.toString(openEXRFile.isSinglePartFileWithScanLines())));
				list.add(new Data("isSinglePartFileWithTiles", Boolean.toString(openEXRFile.isSinglePartFileWithTiles())));
				
				final List<Header> headers = openEXRFile.getHeaders();
				
				for(final Header header : headers) {
					list.add(header);
				}
			}
			
			return list;
		};
	}
	
	private static Function<Object, Node> doCreateMapperUToGraphic() {
		return object -> {
			return null;
		};
	}
	
	private static Function<Object, String> doCreateMapperUToT() {
		return object -> {
			if(object instanceof Attribute) {
				return Attribute.class.cast(object).getName();
			} else if(object instanceof Channel) {
				return Channel.class.cast(object).getName();
			} else if(object instanceof Data) {
				return Data.class.cast(object).getName() + " = " + Data.class.cast(object).getValue();
			} else if(object instanceof Header) {
				return "Header";
			} else if(object instanceof OpenEXRFile) {
				return OpenEXRFile.class.cast(object).getFileName();
			} else {
				return "";
			}
		};
	}
	
	private static ObjectTreeView<String, Object> doCreateObjectTreeView(final OpenEXRFile openEXRFile) {
		return new ObjectTreeView<>(doCreateMapperUToContextMenu(), doCreateMapperUToListU(), doCreateMapperUToGraphic(), doCreateMapperUToT(), openEXRFile);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class Data {
		private final Object value;
		private final String name;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Data(final String name, final Object value) {
			this.name = Objects.requireNonNull(name, "name == null");
			this.value = Objects.requireNonNull(value, "value == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Object getValue() {
			return this.value;
		}
		
		public String getName() {
			return this.name;
		}
	}
}