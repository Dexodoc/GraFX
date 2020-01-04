package peg;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import peg.encryption.Coordinate;
import peg.encryption.Encrypt;
import peg.encryption.Key;
import peg.text.TextConversion;

public class EncryptFXMLController implements Initializable {

    public Button generateKeyButton;
    public Button leftClear;
    public Button rightClear;
    public TextField keyEntryTextField;
    @FXML private TextField keyTextField;
    @FXML private TextArea leftTextArea;
    @FXML private TextArea rightTextArea;
    @FXML private Label keyLabel;
    @FXML private Label rightTextLabel;
    @FXML private ToggleButton encryptButton;
    @FXML private ToggleGroup group1;
    @FXML private ToggleButton decryptButton;
    @FXML private Label leftTextLabel;
    @FXML private SplitPane splitPane;
    @FXML private Button computeButton;
    @FXML private Label messageLabel;
    private String todo = "encrypt";

    public EncryptFXMLController() {
    }

    private void resetMessage(){
        messageLabel.setText("");
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        encryptButton.setSelected(true);
        resetMessage();
    }

    public void onEncryptClicked(ActionEvent actionEvent) {
        rightTextLabel.setText("Plaintext");
        leftTextLabel.setText("Encrypted Text");
//        leftTextArea.setText("");
//        rightTextArea.setText("");
        todo = "encrypt";
    }

    public void onDecryptClicked(ActionEvent actionEvent) {
        rightTextLabel.setText("Encrypted Text");
        leftTextLabel.setText("Plaintext");
//        leftTextArea.setText("");
//        rightTextArea.setText("");
        todo = "decrypt";
    }

    public void onComputeClicked(ActionEvent actionEvent) {
        if (encryptButton.isSelected()) encryptData();
        else decryptData();
    }

    private void encryptData(){
        String key = keyTextField.getText().toLowerCase();

        if (key.isEmpty()) messageLabel.setText("No Key Entered");
        else if(Key.keyValidity(key)) messageLabel.setText("Invalid Key: Requires even number of characters or 0-f");
        else {
            resetMessage();
            String plaintext = leftTextArea.getText();
            boolean keyLengthTooShort = true;
            String newKey = "";

            while (keyLengthTooShort) {
                if (newKey.length() < plaintext.length() * 2) {
                    newKey = newKey + key;
                } else keyLengthTooShort = false;
            }
            key = newKey;

            Coordinate[] coords = TextConversion.textIntoCoords(plaintext);
            int[][] keys = Key.transformKey(key);
            Coordinate[] encryp = Encrypt.encrypt(coords, keys);
            StringBuilder sb = new StringBuilder();
            for (Coordinate cor : encryp) {
                sb.append(cor.toString());
                sb.append("\n");
            }
            String encryptedPT = sb.toString();
//            System.out.println(key);
            rightTextArea.setText(encryptedPT);
        }
    }

    private void decryptData(){
        String key = keyTextField.getText().toLowerCase();
        if (key.isEmpty()) messageLabel.setText("No Key Entered");
        else if(Key.keyValidity(key)) messageLabel.setText("Invalid Key: Requires even number of characters or 0-f");
        else {
            resetMessage();
            String encrypt = leftTextArea.getText();
            String[] stringCoords = encrypt.split("\n");
            Coordinate[] coords = new Coordinate[stringCoords.length];
            for (int i = 0; i < stringCoords.length; i++) {
                coords[i] = new Coordinate(stringCoords[i]);
            }
            boolean keyLengthTooShort = true;
            String newKey = "";
            while (keyLengthTooShort) {
                if (newKey.length() < coords.length * 2) {
                    newKey = newKey + key;
                } else keyLengthTooShort = false;
            }
            key = newKey;
            System.out.println(key);
            int[][] keys = Key.transformKey(key);
            Coordinate[] decrypted = Encrypt.decrypt(coords, keys);
            for (Coordinate i: decrypted) {
                System.out.println(i.toRoundedString());
            }
            String plaintext = TextConversion.coordsIntoText(decrypted);
            rightTextArea.setText(plaintext);
        }
    }

    public void generatePressed(ActionEvent actionEvent) throws IOException {
        String bitCount = keyEntryTextField.getText();
        if (bitCount.matches("^[0-9]+$")){
            String key = Key.generateKey(Integer.parseInt(bitCount));
            keyTextField.setText(key);
        } else {
            keyTextField.setText("Please enter a number from 0-2048");
        }
    }

    public void leftClearPressed(ActionEvent actionEvent) {
        leftTextArea.setText("");
    }

    public void rightClearPressed(ActionEvent actionEvent) {
        rightTextArea.setText("");
    }
}