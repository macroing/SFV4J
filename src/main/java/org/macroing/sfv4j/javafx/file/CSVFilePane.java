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

import java.util.Objects;

import org.macroing.sfv4j.file.csv.CSVFile;
import org.macroing.sfv4j.file.csv.CSVFileReader;

import javafx.scene.layout.BorderPane;

public final class CSVFilePane extends BorderPane {
	private final CSVFile cSVFile;
	private final CSVFileTableView cSVFileTableView;
	private final FileConfiguration fileConfiguration;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public CSVFilePane(final FileConfiguration fileConfiguration) {
		this.fileConfiguration = Objects.requireNonNull(fileConfiguration, "fileConfiguration == null");
		this.cSVFile = new CSVFileReader().readCSVFile(this.fileConfiguration.getFile(), this.fileConfiguration.getCharacterEncoding(), this.fileConfiguration.getDelimiter(), this.fileConfiguration.hasHeader()).orElse(null);
		this.cSVFileTableView = this.cSVFile != null ? new CSVFileTableView(this.cSVFile) : null;
		
		if(this.cSVFileTableView != null) {
			setCenter(this.cSVFileTableView);
		}
	}
}