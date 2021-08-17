package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EnterKeypressHandler implements EventHandler<KeyEvent>{
	
	private Button searchButton;
	
	public EnterKeypressHandler(Button searchButton) {
		this.searchButton = searchButton;
	}
	
	@Override
	public void handle(KeyEvent event) {
		
		if (event.getCode().equals(KeyCode.ENTER)) {
			this.searchButton.fire();
		}
		
	}

}
