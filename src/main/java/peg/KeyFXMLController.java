package peg;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import peg.encryption.Coordinate;
import peg.encryption.Encrypt;
import peg.encryption.Key;
import peg.text.TextConversion;

public class KeyFXMLController implements Initializable {

    public TextField bitCountTextField;
    public TextArea outputTextArea;
    public Button generateButton;
    public Label messageLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");

    }


    public void generatePressed(ActionEvent actionEvent) throws IOException {
        String bitCount = bitCountTextField.getText();
        if (bitCount.matches("^[0-9]+$")){
            String key = Key.generateKey(Integer.parseInt(bitCount));
            outputTextArea.setText(key);
        } else {
            messageLabel.setText("Please enter a number from 0-2048");
        }
    }
}