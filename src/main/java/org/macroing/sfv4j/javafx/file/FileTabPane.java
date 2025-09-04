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

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public final class FileTabPane extends TabPane {
	private static final String TEXT_C_S_V_TABLE = "CSV Table";
	private static final String TEXT_OPEN_E_X_R = "OpenEXR";
	private static final String TEXT_OVERVIEW = "Overview";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final FileConfiguration fileConfiguration;
	private final FileOverviewPane fileOverviewPane;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public FileTabPane(final FileConfiguration fileConfiguration) {
		this.fileConfiguration = Objects.requireNonNull(fileConfiguration, "fileConfiguration == null");
		this.fileOverviewPane = new FileOverviewPane(fileConfiguration);
		
		getTabs().add(new Tab(TEXT_OVERVIEW, this.fileOverviewPane));
		
		if(fileConfiguration.isFileFormatCSV()) {
			getTabs().add(new Tab(TEXT_C_S_V_TABLE, new CSVFilePane(fileConfiguration)));
		}
		
		if(fileConfiguration.isFileFormatOpenEXR()) {
			getTabs().add(new Tab(TEXT_OPEN_E_X_R, new OpenEXRFilePane(fileConfiguration)));
		}
		
		getSelectionModel().select(0);
		
		setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public FileConfiguration getFileConfiguration() {
		return this.fileConfiguration;
	}
	
	public void handleExitRequest() {
		this.fileOverviewPane.handleExitRequest();
	}
}