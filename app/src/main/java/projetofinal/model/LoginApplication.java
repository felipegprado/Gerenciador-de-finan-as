package projetofinal.model; 

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/projetofinal/login.fxml"));
        
        Scene scene = new Scene(parent);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.setTitle("Meu gerenciador de finanças");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}