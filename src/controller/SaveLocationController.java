package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.StyleSetter;
import view.WeatherSystemView;

public class SaveLocationController implements EventHandler<MouseEvent>{

	private VBox savedLocations;
	private TextField searchedLocation;
	private Button searchButton;
	private WeatherSystemView view;
	private String deletedString = "nothing";
	private final ColumnConstraints c1Constraint = new ColumnConstraints(159);
	private final ColumnConstraints c2Constraint = new ColumnConstraints(39);
	public ArrayList<String> boxList = new ArrayList<String>();
	
	public SaveLocationController(WeatherSystemView view, TextField searchedLocation, 
			Button searchButton) {
		this.view = view;
		this.savedLocations = view.savedLocations;
		this.searchedLocation = searchedLocation;
		this.searchButton = searchButton;
	}

	public void setLocation(String s) {
		this.searchedLocation.setText(s);
	}
	
	public String getLocation() {
		return this.searchedLocation.getText();
	}
	
	public boolean isLocationAlreadySaved(Label locationToAdd) {
		for (Node location: this.savedLocations.getChildren()) {
			GridPane holder = (GridPane) location;
			HBox locationBox = (HBox) holder.getChildren().get(0);
   	 		Label savedLocation = (Label) locationBox.getChildren().get(1);
   	 		if (savedLocation.getText().toLowerCase().equals(locationToAdd.getText().toLowerCase())) {
   	 			return true;
   	 		}
   	 	}
		return false;
	}
	
	public void setDeletedString(String s) {
		this.deletedString = s;
	}
	
	public void remove(String deletedLocation) {
		try {
			System.out.println(this.view.locationsList);
			this.view.database.removeLocation(this.view.locationsList, deletedLocation);
			this.view.locationsList.remove(deletedLocation);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //removes from database
   	 	System.out.println(deletedLocation + " has been removed from the locations list.\n");
	}
	
	public void saveLocation() {
		for (Node location: this.savedLocations.getChildren()) {
			GridPane holder = (GridPane) location;
			HBox locationBox = (HBox) holder.getChildren().get(0);
   	 		Label savedLocation = (Label) locationBox.getChildren().get(1);
   	 		
   	 		if (boxList.contains(savedLocation.getText())) {
   	 			continue;
   	 		} else {
   	 			boxList.add(savedLocation.getText());
   	 		}
		}
		this.addAndSet(1);
	}
	
	@Override
	public void handle(MouseEvent arg0) {
   	 	this.addAndSet(0);
	}
	
	public void addAndSet(int type) {
		boolean valid = this.view.dfs.isValidSearch;
		boolean searchOccurred = this.view.dfs.hasSearchOccurred;
		Label locationToSave = new Label();
		HBox saved = new HBox(3);
		GridPane holder = new GridPane();
		HBox deleteBox = new HBox();
		ImageView pinpointImage = new ImageView();
		Button deleteLocation = new Button();

		saved.setAlignment(Pos.CENTER_LEFT);
		deleteBox.setAlignment(Pos.CENTER_RIGHT);
		holder.setStyle("-fx-background-color:rgba(211,211,211, 0.5); -fx-border-color: black;");
   	 	locationToSave.setText(searchedLocation.getText());
   	 	this.view.setImage(pinpointImage, "marker.png", 20, 20);
   	 	this.view.setExitButton(deleteLocation, 17.5, 17.5, 25, 25);
		
		deleteLocation.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
   	 		@Override
		     public void handle(MouseEvent event) {
   	 				
		    	 if (savedLocations.getChildren().contains(holder)) {
		    		 HBox locationBox = (HBox) holder.getChildren().get(0);
			    	 Label savedLocation = (Label) locationBox.getChildren().get(1);
			    	 setDeletedString(savedLocation.getText());
			    	 savedLocations.getChildren().remove(holder);
			    	 remove(deletedString);
		    	 }
   	 		 }
	   	});
		
		boolean works = false;
		
		//add location only if it doesnt already exist and the list isn't already full
		if (type == 0) {
			if (!isLocationAlreadySaved(locationToSave) && savedLocations.getChildren().size() < 10
	   	 			&& valid && searchOccurred && !locationToSave.getText().equals("")) {
				works = true;
			}
		} else if (type == 1) {
			if (!isLocationAlreadySaved(locationToSave) && savedLocations.getChildren().size() <= 10 
					&& !locationToSave.getText().equals("")) {
				works = true;
			}
		}
	   	 	
		if (works) {
			locationToSave.setStyle(StyleSetter.averageFontSize + "-fx-font-weight: 500;");
	   	 	locationToSave.setCursor(Cursor.HAND);
	   	 	locationToSave.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			     @Override
			     public void handle(MouseEvent event) {
			    	 searchedLocation.setText((locationToSave.getText()));
			    	 searchButton.fire();
			    	 searchedLocation.clear();
			    	 searchedLocation.setText((locationToSave.getText()));
			     }
			});
	   	 	deleteBox.getChildren().add(deleteLocation);
	   	 	saved.getChildren().addAll(pinpointImage, locationToSave);
	   	 	holder.add(saved, 0, 0);
	   	 	holder.add(deleteBox, 1, 0);
	   	 	holder.getColumnConstraints().add(c1Constraint);
	   	 	holder.getColumnConstraints().add(c2Constraint); 
	   	 	this.savedLocations.getChildren().add(holder);
	   	 	
	   	 	if (type == 0) {
	   			try {
					this.view.database.registerLocation(searchedLocation.getText());
					this.view.locationsList.add(searchedLocation.getText());
					System.out.println(searchedLocation.getText() + " has been added to the locations list.\n");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	   	 	}
		} else {
	 		System.out.println("The location has already been registered OR the search result was deemed invalid.\n");
	 	}
	}
}

