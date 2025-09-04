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

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public final class FileConfigurationDialog extends Dialog<Boolean> {
	public FileConfigurationDialog(final FileConfiguration fileConfiguration, final Stage stage) {
		Objects.requireNonNull(fileConfiguration, "fileConfiguration == null");
		Objects.requireNonNull(stage, "stage == null");
		
		final ComboBox<String> comboBoxCharacterEncoding = doCreateComboBox(Arrays.asList(FileConfiguration.CHARACTER_ENCODING_I_S_O_8859_1, FileConfiguration.CHARACTER_ENCODING_U_T_F_8, FileConfiguration.CHARACTER_ENCODING_WINDOWS_1252), FileConfiguration.CHARACTER_ENCODING_U_T_F_8, true);
		final ComboBox<String> comboBoxDelimiterName = doCreateComboBox(Arrays.asList(FileConfiguration.DELIMITER_COMMA_NAME, FileConfiguration.DELIMITER_SEMICOLON_NAME, FileConfiguration.DELIMITER_SPACE_NAME, FileConfiguration.DELIMITER_TAB_NAME), FileConfiguration.DELIMITER_COMMA_NAME, false);
		final ComboBox<String> comboBoxFileFormat = doCreateComboBox(Arrays.asList(FileConfiguration.FILE_FORMAT_BINARY, FileConfiguration.FILE_FORMAT_C_S_V, FileConfiguration.FILE_FORMAT_OPEN_E_X_R, FileConfiguration.FILE_FORMAT_PLAIN_TEXT), FileConfiguration.FILE_FORMAT_PLAIN_TEXT, true);
		
		final CheckBox checkBoxHasHeader = doCreateCheckBox(false, false);
		
		final Text textCharacterEncoding = doCreateText("Character Encoding", true);
		final Text textDelimiterName = doCreateText("Delimiter", false);
		final Text textFileFormat = doCreateText("File Format", true);
		final Text textHasHeader = doCreateText("Header", false);
		
		final String[] characterEncoding = {FileConfiguration.CHARACTER_ENCODING_U_T_F_8};
		final String[] delimiter = {FileConfiguration.DELIMITER_COMMA};
		final String[] fileFormat = {FileConfiguration.FILE_FORMAT_PLAIN_TEXT};
		
		final boolean[] hasHeader = {false};
		
		checkBoxHasHeader.setOnAction(e -> {
			final CheckBox checkBox = CheckBox.class.cast(e.getSource());
			
			hasHeader[0] = checkBox.isSelected();
		});
		
		comboBoxCharacterEncoding.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			characterEncoding[0] = newValue;
		});
		
		comboBoxDelimiterName.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			delimiter[0] = FileConfiguration.getDelimiterByName(newValue);
		});
		
		comboBoxFileFormat.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			fileFormat[0] = newValue;
			
			switch(newValue) {
				case FileConfiguration.FILE_FORMAT_BINARY:
					comboBoxCharacterEncoding.setVisible(true);
					comboBoxDelimiterName.setVisible(false);
					
					checkBoxHasHeader.setVisible(false);
					
					textCharacterEncoding.setVisible(true);
					textDelimiterName.setVisible(false);
					textHasHeader.setVisible(false);
					
					break;
				case FileConfiguration.FILE_FORMAT_C_S_V:
					comboBoxCharacterEncoding.setVisible(true);
					comboBoxDelimiterName.setVisible(true);
					
					checkBoxHasHeader.setVisible(true);
					
					textCharacterEncoding.setVisible(true);
					textDelimiterName.setVisible(true);
					textHasHeader.setVisible(true);
					
					break;
				case FileConfiguration.FILE_FORMAT_OPEN_E_X_R:
					comboBoxCharacterEncoding.setVisible(true);
					comboBoxDelimiterName.setVisible(false);
					
					checkBoxHasHeader.setVisible(false);
					
					textCharacterEncoding.setVisible(true);
					textDelimiterName.setVisible(false);
					textHasHeader.setVisible(false);
					
					break;
				case FileConfiguration.FILE_FORMAT_PLAIN_TEXT:
					comboBoxCharacterEncoding.setVisible(true);
					comboBoxDelimiterName.setVisible(false);
					
					checkBoxHasHeader.setVisible(false);
					
					textCharacterEncoding.setVisible(true);
					textDelimiterName.setVisible(false);
					textHasHeader.setVisible(false);
					
					break;
				default:
					break;
			}
			
			getDialogPane().getScene().getWindow().sizeToScene();
		});
		
		final
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_LEFT);
		gridPane.setHgap(10.0D);
		gridPane.setPadding(new Insets(10.0D, 10.0D, 10.0D, 10.0D));
		gridPane.setVgap(10.0D);
		
		gridPane.add(textFileFormat, 0, 0);
		gridPane.add(comboBoxFileFormat, 1, 0);
		
		gridPane.add(textCharacterEncoding, 0, 1);
		gridPane.add(comboBoxCharacterEncoding, 1, 1);
		
		gridPane.add(textDelimiterName, 0, 2);
		gridPane.add(comboBoxDelimiterName, 1, 2);
		
		gridPane.add(textHasHeader, 0, 3);
		gridPane.add(checkBoxHasHeader, 1, 3);
		
		final
		DialogPane dialogPane = getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		dialogPane.setContent(gridPane);
		
		final
		Button button = Button.class.cast(dialogPane.lookupButton(ButtonType.OK));
		button.setOnAction(event -> {
			fileConfiguration.setCharacterEncoding(characterEncoding[0]);
			fileConfiguration.setDelimiter(delimiter[0]);
			fileConfiguration.setFileFormat(fileFormat[0]);
			fileConfiguration.setHeader(hasHeader[0]);
		});
		
		initOwner(stage);
		
		setResultConverter(buttonType -> Boolean.valueOf(buttonType.getButtonData() == ButtonType.OK.getButtonData()));
		setTitle("File Configuration");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static CheckBox doCreateCheckBox(final boolean isSelected, final boolean isVisible) {
		final
		CheckBox checkBox = new CheckBox();
		checkBox.managedProperty().bind(checkBox.visibleProperty());
		checkBox.setSelected(isSelected);
		checkBox.setVisible(isVisible);
		
		return checkBox;
	}
	
	private static ComboBox<String> doCreateComboBox(final Collection<String> items, final String value, final boolean isVisible) {
		final
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll(items);
		comboBox.managedProperty().bind(comboBox.visibleProperty());
		comboBox.setMaxWidth(Double.MAX_VALUE);
		comboBox.setValue(value);
		comboBox.setVisible(isVisible);
		
		return comboBox;
	}
	
	private static Text doCreateText(final String string, final boolean isVisible) {
		final
		Text text = new Text(string);
		text.managedProperty().bind(text.visibleProperty());
		text.setVisible(isVisible);
		
		return text;
	}
}