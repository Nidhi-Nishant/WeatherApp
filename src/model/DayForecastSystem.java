package model;

import java.util.ArrayList;

public class DayForecastSystem extends Observable {
	
	private WeatherSystem ws;
	private ArrayList<ArrayList<String>> forecastData = new ArrayList<ArrayList<String>>();
	private String day;
	private String temp;
	private String weather;
	private String date;
	private int dayNumber;
	public boolean isValidSearch = false;
	public boolean hasSearchOccurred = false;
	
	public DayForecastSystem(WeatherSystem ws) {
		this.ws = ws;
		this.ws.get5DayForecast();
		this.forecastData = ws.getForecastData();
	}
	
	public boolean forecastDataExists() {
		if (this.forecastData != null && this.forecastData.size() != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public WeatherSystem getWeatherSystem() {
		return this.ws;
	}
	
	public void updateWeatherSystem(WeatherSystem newWs) {
		this.ws = newWs;
		this.ws.resetForecastData();
		this.ws.get5DayForecast();
		this.forecastData = ws.getForecastData();
	}
	
	public int getCurrentDay() {
		return this.dayNumber;
	}
	
	public void setForecastDay(int number) {
		this.dayNumber = 0;
		
		if (number == 1) {
			this.dayNumber = 0;
		} else if (number == 2) {
			this.dayNumber = 1;
		} else if (number == 3) {
			this.dayNumber = 2;
		} else if (number == 4) {
			this.dayNumber = 3;
		} else if (number == 5) {
			this.dayNumber = 4;
		} else if (number == -1) {
			setInvalidDayForecast();
		}
		
		if (this.forecastDataExists()) {
			setDay(this.forecastData.get(dayNumber).get(0));
			setTemp(this.forecastData.get(dayNumber).get(1));
			setWeather(this.forecastData.get(dayNumber).get(2));
			setDate(this.forecastData.get(dayNumber).get(3));
		}
	}
	
	public void setInvalidDayForecast() {
		this.day = "N/A";
		this.temp = "N/A";
		this.weather = "N/A";
		this.dayNumber = -1;
		this.notifyObservers();
	}
	
	public void setInvalid5DayForecast() {
		setInvalidDayForecast();
		setInvalidDayForecast();
		setInvalidDayForecast();
		setInvalidDayForecast();
		setInvalidDayForecast();
	}
	
	public String getDate() {
		return this.date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setDay(String day) {
		this.day = day;
	}
	
	public void setTemp(String temp) {
		this.temp = temp;
	}
	
	public void setWeather(String weather) {
		this.weather = weather;
	}
	
	public String getDay() {
		return this.day;
	}
	
	public String getTemp() {
		return this.temp;
	}
	
	public String getWeather() {
		return this.weather;
	}
	
	public void printForecastDay() {
		System.out.println("=============================================");
		System.out.println("Day of the Week: " + this.day);
		System.out.println("Temperature for the day: " + this.temp);
		System.out.println("Weather conditions for the day: " + this.weather);
		System.out.println("=============================================");
	}
	
	//testing purposes
	public void print5DayForecast() {
		System.out.println("Here is the 5 day forecast for " + ws.getLocation() + ".\n");
		System.out.println("=============================================");
		for(int i = 0; i < this.forecastData.size(); i++) {
			String day = this.forecastData.get(i).get(0);
			String temp = this.forecastData.get(i).get(1);
			String weatherCondition = this.forecastData.get(i).get(2);
			
			System.out.println("Day of the Week: " + day);
			System.out.println("Temperature for the day: " + temp);
			System.out.println("Weather conditions for the day: " + weatherCondition);
			System.out.println("=============================================" + "\n");
		}
	}
}
