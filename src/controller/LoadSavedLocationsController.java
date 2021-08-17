package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import view.WeatherSystemView;

public class LoadSavedLocationsController implements EventHandler <MouseEvent> {
	
	private WeatherSystemView view;
	
	public LoadSavedLocationsController(WeatherSystemView view) {
		this.view = view;
	}

	@Override
	public void handle(MouseEvent arg0) {
		
		VBox savedLocations = this.view.getLocationsBox();
		
		if (!this.view.getChildren().contains(savedLocations)) {
			this.view.getChildren().add(savedLocations);
		} else {
			this.view.getChildren().remove(savedLocations);
		}
	}
	
	
}
