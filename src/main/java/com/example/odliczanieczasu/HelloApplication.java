package com.example.odliczanieczasu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadListener;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime nowyRok = LocalDateTime.from(LocalDate.of(2024,1,1).atStartOfDay(ZoneId.systemDefault()));
//        LocalDateTime nowyRok = LocalDateTime.now();




        Label label_czas = new Label();
        Thread timerThread = new Thread(()->{
            boolean countdownTrue = true;
            while (countdownTrue==true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                LocalDateTime teraz = LocalDateTime.now();



                long roznicaSekundy = teraz.until(nowyRok, ChronoUnit.SECONDS);

                if (roznicaSekundy<=0){
                    countdownTrue=false;
                    Platform.runLater(()->{
                        label_czas.setText("Szczęśliwego nowego roku!");
                    });
                }else{
                    int days = (int) TimeUnit.SECONDS.toDays(roznicaSekundy);
                    long hours = TimeUnit.SECONDS.toHours(roznicaSekundy) - (days*24);
                    long minutes = TimeUnit.SECONDS.toMinutes(roznicaSekundy) - (TimeUnit.SECONDS.toHours(roznicaSekundy)* 60);
                    long seconds = TimeUnit.SECONDS.toSeconds(roznicaSekundy) - (TimeUnit.SECONDS.toMinutes(roznicaSekundy) *60);


                    String now = teraz.format(myFormatObj);
                    String newYear = nowyRok.format(myFormatObj);
                    String diff = String.valueOf(days) + " dni " + String.format("%02d",hours) + ":" + String.format("%02d",minutes) + ":" + String.format("%02d",seconds);


                    Platform.runLater(()->{
                        label_czas.setText(diff);
                    });
                }





            }

        }); timerThread.start();


        Group root = new Group(label_czas);

        Scene scene = new Scene(root, 320, 240);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}