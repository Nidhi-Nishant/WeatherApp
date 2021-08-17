package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import model.WeatherSystem;
import view.StyleSetter;

public class ToggleButtonController implements EventHandler<ActionEvent> {
	
	private Button b1;
	private Button b2;
	private LoadWeatherController lwc;
	
	public ToggleButtonController(Button b1, Button b2, LoadWeatherController lwc) {
		this.b1 = b1;
		this.b2 = b2;
		this.lwc = lwc;
	}
	
	@Override
	public void handle(ActionEvent e) {
		
		String val = ((Button)e.getSource()).getText();
		
		WeatherSystem ws = this.lwc.getDfs().getWeatherSystem();
		
		if (val.equals(this.b1.getText())) {
			b1.setStyle(StyleSetter.toggleStyle);
			b2.setStyle(StyleSetter.toggleStyle2);
			ws.changeMeasurementSystem("metric");
		}
		
		if (val.equals(this.b2.getText())) {
			b2.setStyle(StyleSetter.toggleStyle);
			b1.setStyle(StyleSetter.toggleStyle2);
			ws.changeMeasurementSystem("imperial");
		}
		
		this.lwc.handle(e);
	}
}
