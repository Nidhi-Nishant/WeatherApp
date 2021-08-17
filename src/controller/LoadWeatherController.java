package controller;

import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import model.DayForecastSystem;
import model.WeatherSystem;

public class LoadWeatherController implements EventHandler<ActionEvent> {
	
	private TextField searchResult;
	private DayForecastSystem dfs;
	private String lastValidLocation = "";
	
	public LoadWeatherController(TextField searchResult, DayForecastSystem dfs) {
		this.searchResult = searchResult;
		this.setDfs(dfs);
	}
	
	public void setSearchResult(String s) {
		this.searchResult.setText(s);
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		Pattern containsNumbers = Pattern.compile("^.*([0-9]).*$");
		String location = searchResult.getText();
		
		if (!location.equals("") && !containsNumbers.matcher(location).matches()) {
			WeatherSystem ws = this.dfs.getWeatherSystem();
			
			ws.setLocation(location);
			this.dfs.updateWeatherSystem(ws);
			
			boolean valid = ws.getValidity();
		    
		    if (valid) {
		    	lastValidLocation = location;
		    	this.dfs.isValidSearch = true;
		    	ws.get5DayForecast();
		    	this.dfs.hasSearchOccurred = true;
		    	
				if (this.dfs.forecastDataExists()) {
					this.dfs.notifyObservers();
				}
				
		    } else {
		    	ws.setLocation(lastValidLocation);
		    	this.dfs.isValidSearch = false;
		    	ws.get5DayForecast();
		    	this.dfs.hasSearchOccurred = false;
		    	
		    	if (this.dfs.forecastDataExists()) {
					this.dfs.notifyObservers();
				}
		    }
		    
		}
	}

	public DayForecastSystem getDfs() {
		return dfs;
	}

	public void setDfs(DayForecastSystem dfs) {
		this.dfs = dfs;
	}
}
