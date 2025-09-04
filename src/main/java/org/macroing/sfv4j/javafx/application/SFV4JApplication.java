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
package org.macroing.sfv4j.javafx.application;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.macroing.sfv4j.javafx.file.FileConfiguration;
import org.macroing.sfv4j.javafx.file.FileConfigurationDialog;
import org.macroing.sfv4j.javafx.file.FileTabPane;
import org.macroing.sfv4j.javafx.scene.control.NodeSelectionTabPane;
import org.macroing.sfv4j.javafx.scene.control.PathMenuBar;

public final class SFV4JApplication extends Application {
	private static final File INITIAL_DIRECTORY = new File("./");
	private static final String PATH_ELEMENT_FILE = "File";
	private static final String PATH_FILE = "File";
	private static final String TEXT_EXIT = "Exit";
	private static final String TEXT_FILE = "File";
	private static final String TEXT_OPEN = "Open";
	private static final String TITLE = "SFV4J";
	private static final String TITLE_OPEN = "Open";
	private static final double MINIMUM_RESOLUTION_X = 1024.0D;
	private static final double MINIMUM_RESOLUTION_Y = 768.0D;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final AtomicReference<Stage> stage;
	private final BorderPane borderPane;
	private final NodeSelectionTabPane<FileTabPane, FileConfiguration> nodeSelectionTabPane;
	private final PathMenuBar pathMenuBar;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public SFV4JApplication() {
		this.stage = new AtomicReference<>();
		this.borderPane = new BorderPane();
		this.nodeSelectionTabPane = new NodeSelectionTabPane<>(FileTabPane.class, fileTabPane -> fileTabPane.getFileConfiguration(), fileConfiguration -> new FileTabPane(fileConfiguration), (a, b) -> a.getFilePath().equals(b.getFilePath()), fileConfiguration -> fileConfiguration.getFileName());
		this.pathMenuBar = new PathMenuBar();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void start(final Stage stage) {
		doSetStage(stage);
		doConfigureBorderPane();
		doConfigurePathMenuBar();
		doConfigureAndShowStage();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
		launch(args);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Stage doGetStage() {
		return this.stage.get();
	}
	
	private void doConfigureAndShowStage() {
		final
		Stage stage = doGetStage();
		stage.setHeight(MINIMUM_RESOLUTION_Y);
		stage.setOnCloseRequest(e -> doExit());
		stage.setResizable(true);
		stage.setScene(new javafx.scene.Scene(this.borderPane));
		stage.setTitle(TITLE);
		stage.setWidth(MINIMUM_RESOLUTION_X);
		stage.show();
	}
	
	private void doConfigureBorderPane() {
		this.borderPane.setCenter(this.nodeSelectionTabPane);
		this.borderPane.setTop(this.pathMenuBar);
	}
	
	private void doConfigurePathMenuBar() {
		this.pathMenuBar.setPathElementText(PATH_ELEMENT_FILE, TEXT_FILE);
		this.pathMenuBar.addMenuItem(PATH_FILE, TEXT_OPEN, e -> doOpen(), null, true);
		this.pathMenuBar.addSeparatorMenuItem(PATH_FILE);
		this.pathMenuBar.addMenuItem(PATH_FILE, TEXT_EXIT, e -> doExit(), null, true);
	}
	
	private void doExit() {
		for(final Tab tab : this.nodeSelectionTabPane.getTabs()) {
			final Node content = tab.getContent();
			
			if(content instanceof FileTabPane) {
				final
				FileTabPane fileTabPane = FileTabPane.class.cast(content);
				fileTabPane.handleExitRequest();
			}
		}
		
		Platform.exit();
	}
	
	private void doOpen() {
		try {
			final
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(INITIAL_DIRECTORY);
			fileChooser.setTitle(TITLE_OPEN);
			
			final File file = fileChooser.showOpenDialog(doGetStage());
			
			if(file != null) {
				final FileConfiguration fileConfiguration = new FileConfiguration(file);
				
				final
				FileConfigurationDialog fileConfigurationDialog = new FileConfigurationDialog(fileConfiguration, doGetStage());
				fileConfigurationDialog.showAndWait();
				
				this.nodeSelectionTabPane.addLater(fileConfiguration, tab -> {
					tab.setOnCloseRequest(e -> {
						final Node content = tab.getContent();
						
						if(content instanceof FileTabPane) {
							final
							FileTabPane fileTabPane = FileTabPane.class.cast(content);
							fileTabPane.handleExitRequest();
						}
					});
				});
			}
		} catch(final IllegalArgumentException e) {
//			Do nothing for now.
		}
	}
	
	private void doSetStage(final Stage stage) {
		this.stage.set(Objects.requireNonNull(stage, "stage == null"));
	}
}