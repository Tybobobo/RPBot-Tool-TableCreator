/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablecreator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author ivanskodje
 */
public class TableCreatorController implements Initializable
{
	final FileChooser fileChooser = new FileChooser();		// File Chooser
	private File table;										// Table file
	
	@FXML
	private TextArea pasteText;
	@FXML
	private MenuItem newTableItem;
	@FXML
	private MenuItem selectTableItem;
	@FXML
	private MenuItem closeTableItem;
	@FXML
	private MenuItem aboutItem;
	@FXML
	private Button insertBtn;
	@FXML
	private Button clearBtn;
	@FXML
	private AnchorPane window;
	@FXML
	private Menu menuTableInfo;
	
	
	/**
	 * Init
	 * @param url
	 * @param rb 
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		// New Table
		newTableItem.setOnAction((event) ->
		{
            fileChooser.setTitle("New Table File");
            File file = fileChooser.showSaveDialog((Stage)window.getScene().getWindow());
            if (file != null) 
			{
				createFile(file);
            }
		});
		
		
		// Select Table
		selectTableItem.setOnAction((event) ->
		{
			File file = fileChooser.showOpenDialog((Stage)window.getScene().getWindow());
			if (file != null) 
			{
				openFile(file);
			}
		});
		
		
		// Close
		closeTableItem.setOnAction((event) ->
		{
			Platform.exit();
		});
		
		
		// About
		aboutItem.setOnAction((event) ->
		{
			// TODO: Open new Stage with information about how this 
			displayMessage("About", "An Experimental RPBot tool for creating tables\n\n"
					+ "Ivan Skodje @ https://github.com/Tybobobo/RPBot");
		});
		
		
		// Clear
		clearBtn.setOnAction((event) ->
		{
			pasteText.clear();
		});
	}
	
	
	/**
	 * Open an existing file
	 * @param file 
	 */
	private void openFile(File selectedTable) 
	{
		try
		{
			// Get first number
			String[] lineZero = Files.readAllLines(Paths.get(selectedTable.getPath())).get(0).split(" ");
			
			// Try get line count (making sure it is a valid file)
			int testLineCounter = Integer.parseInt(lineZero[0]);
				
			// Assign table if nothing went wrong
			table = selectedTable;
		}
		catch(IOException | NumberFormatException ex)
		{
			// Something went wrong - Not a valid table file!
			// TODO: Refine this so we cant just pick a random file that just happen to have a number on the first line
			displayError("ERROR", "Invalid table file selected");
			table = null;
		}
		
		// Update info and buttons
		update();
    }
	
	
	/**
	 * Creates a new File
	 * @param newFile 
	 */
	private void createFile(File newFile)
	{
		try 
		{
			// Empty list
			List<String> list = new ArrayList<>();
			list.add("0"); // Zero rows added - the first row tells you how many rows we will check for.
			
			// Write new file
			Files.write(newFile.toPath(), list, Charset.forName("UTF-8"), StandardOpenOption.CREATE_NEW);
			
			// Store file as table
			table = newFile;
			
			// Enable input and table interaction
			update();
		} 
		catch (IOException ex) 
		{
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * Turns inserted text into a formatted row that is readable by the Table.class in RPBot
	 * @param event 
	 */
	@FXML
    void addData(ActionEvent event) 
	{
		try
		{
			// Line to insert
			List<String> lines = new ArrayList<>();

			// Get pasted string (separated by newline)
			String[] pastedString = pasteText.getText().split(System.getProperty("line.separator"));

			// First line will always be the title
			String dataString = "__**" + pastedString[0] + "**__";

			// Iterate each line (except the first)
			for(int i = 1; i < pastedString.length; i++)
			{
				// Check if we have a : that indicates a "type".
				if(pastedString[i].contains(":"))
				{
					String[] splitText = pastedString[i].split(":");
					dataString += "<br><br>**" + splitText[0] + ":**<br>" + pastedString[i].replace(splitText[0] + ":", "").replaceFirst("^\\s*", "");
				}
				else
				{
					// Does not contain : means we are appending the line to the last, without a "type".
					dataString += pastedString[i];
				}
			}

			lines.add(dataString);
			
			try
			{
				// Append to end of file
				Files.write(table.toPath(), lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
				
				// Update lineCount in the beginning of the file
				incrementLineCounter();
				
				// Clear text
				pasteText.clear();
			}
			catch(NoSuchFileException ex)
			{
				table = null;
				update();
				ex.printStackTrace();
			}
			
				
			
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
    }
	
	
	/**
	 * Makes sure that everything is valid and updates text and buttons
	 */
	private void update()
	{
		if(table != null)
		{
			menuTableInfo.setText(table.getName());
			insertBtn.setDisable(false);
			clearBtn.setDisable(false);
		}
		else
		{
			menuTableInfo.setText("<No Table Selected>");
			insertBtn.setDisable(true);
			clearBtn.setDisable(true);
		}
	}
	
	
	/**
	 * Increments the line counter by one
	 */
	private void incrementLineCounter()
	{
		try
		{
			// Get first line
			String firstLine = Files.readAllLines(Paths.get(table.getPath())).get(0);
			
			// Split firstline up into number and an optional URL
			String[] splitLine = firstLine.split(" ");
			
			// Try get line count from the split line (making sure it is a valid file with a row counter)
			int lineCount = Integer.parseInt(splitLine[0]);
			
			// Increment line counter by one
			lineCount += 1;
			
			// Add everything else that were there at the end
			String newFirstLine = lineCount + firstLine.replaceFirst(splitLine[0], "");
			
			// Store new first line
			List<String> lines = Files.readAllLines(table.toPath());
			lines.set(0, newFirstLine);
			Files.write(table.toPath(), lines);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}		
	}
	
	
	/**
	 * Displays a short error message
	 * @param headerMsg
	 * @param msg 
	 */
	private void displayError(String headerMsg, String msg)
	{
		Alert alert = new Alert(Alert.AlertType.ERROR, msg);
		alert.setTitle("ERROR");
		alert.setHeaderText(headerMsg);
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.showAndWait();
	}
	
	
	/**
	 * Displays a short Message
	 * @param headerMsg
	 * @param msg 
	 */
	private void displayMessage(String headerMsg, String msg)
	{
		Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
		alert.setTitle(headerMsg);
		alert.setHeaderText("");
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.showAndWait();
	}
}