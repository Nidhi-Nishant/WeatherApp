package view;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StyleSetter {

	Stage stage;
	
	public static final String HIGHLIGHT = "-fx-background-color: rgb(217, 191, 253);"
			+ " -fx-font-size:16;";
			
	public static final String REGULAR = "-fx-background-color: rgb(140, 143, 186);"
			+ " -fx-font-size:16;";
	
	public static final String TEXT = "-fx-font-size:16;"
			+ " -fx-prompt-text-fill: derive(-fx-control-inner-background,-30%); }";
	
	public static final String toggleStyle = "-fx-font-size: 18; -fx-background-radius: 0; "
			+ "-fx-background-color: grey; -fx-border-color: white;";
	
	public static final String toggleStyle2 = "-fx-font-size: 18; -fx-background-radius: 0; "
			+ "-fx-background-color: white; -fx-border-color: grey;";
	
	public static final String averageFontSize = "-fx-font-size: 16;";
	public static final String bold = "-fx-font-size: 16;-fx-font-weight: BOLD;";

	
	public static void modifyColour(Button button, String oldStyle, String newStyle) {
		button.setOnMouseEntered(
			event -> {
				button.setStyle(newStyle);
			}
		);
		
		button.setOnMouseExited(
			event -> {
				button.setStyle(oldStyle);

			}
		);
	}
}

