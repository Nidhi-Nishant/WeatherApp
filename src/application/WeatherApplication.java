package application;


import javafx.application.Application;
import javafx.stage.Stage;
import model.*;
import view.*;
import controller.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WeatherApplication extends Application {

    Stage window;
    private AddressObtainer aoObtainer = new AddressObtainer();

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;

        // MODEL
        WeatherSystem weatherModel = new WeatherSystem("");
        DayForecastSystem forecastModel = new DayForecastSystem(weatherModel);

        // VIEW setup
        WeatherSystemView weatherView = new WeatherSystemView(window, forecastModel);

        // MODEL -> VIEW hookup
        weatherModel.attach(weatherView);
        forecastModel.attach(weatherView);

        LoadWeatherController weatherController = new LoadWeatherController
                (weatherView.locationField, forecastModel);


        weatherView.searchButton.setOnAction(weatherController);
        weatherView.tb.setOnAction(new ToggleButtonController(weatherView.tb, weatherView.tb2, weatherController));
        weatherView.tb2.setOnAction(new ToggleButtonController(weatherView.tb, weatherView.tb2, weatherController));



        // SCENES SETUP
        Scene weatherScene = new Scene(weatherView);

        // STAGE
        window.setScene(weatherScene);
        window.setTitle("Weather Application");
        window.setResizable(false);

        // icon setup
        ImageView iconImage = new ImageView();
        weatherView.setImage(iconImage, "weatherIcon.png", 100, 100);
        Image icon = iconImage.getImage();
        window.getIcons().add(icon);

        String macAddress = aoObtainer.getMACAddress();

        // LAUNCH THE GUI
        window.show();

        //database stuff
        weatherView.database.connect();

        if (!weatherView.database.isRegistered(macAddress)) {
            weatherView.database.registerUser(macAddress);
            System.out.println("New user registered!");
        }

        //load database locations for user
        weatherView.database.setDbLocationsData(weatherView.locationsList);
        weatherView.setLocationController(weatherView, weatherView.locationField, weatherView.searchButton);

        for (int i = 0; i < weatherView.locationsList.size(); i++) {
            weatherView.locationController.setLocation(weatherView.locationsList.get(i));
            weatherView.locationController.saveLocation();
        }

        // load favourite location on launch
        if (weatherView.database.checkFavouriteExists(weatherView)) {
            weatherController.setSearchResult(weatherView.favouriteLocation);
            weatherController.handle(null);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
