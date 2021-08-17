package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import view.WeatherSystemView;

public class SetFavouriteController implements EventHandler<ActionEvent> {

	private TextField searchResult;
	private WeatherSystemView view;
	
	public SetFavouriteController(WeatherSystemView view, TextField searchResult) {
		this.searchResult = searchResult;
		this.view = view;
	}

	@Override
	public void handle(ActionEvent arg0) {
		
		String newFavourite = searchResult.getText();

		// sets favourite location in application and database
		if (!view.favouriteLocation.equals(newFavourite)) {
			this.view.database.setFavouriteLocation(newFavourite);
			this.view.favouriteLocation = newFavourite;
			System.out.println(newFavourite + " has been set as your favourite location."
					+ " \nWeather results for " + newFavourite +" will be automatically "
							+ "displayed on application launch.\n");
		} else {
			System.out.println(view.favouriteLocation + " is already your favourite location!\n");
		}
	}
}
