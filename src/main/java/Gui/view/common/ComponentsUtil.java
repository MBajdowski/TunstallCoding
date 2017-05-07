package Gui.view.common;

import javafx.scene.control.TextField;

public class ComponentsUtil {

    public static void convertToGTZeroIntegerField(final TextField textField, final Integer defaultValue) {
        textField.setText(String.valueOf(defaultValue));
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^([1-9][0-9]*)*$")) {
                textField.setText(oldValue);
            }
            if (!newValue.equals("")) {
                try {
                    Integer.valueOf(newValue);
                } catch (NumberFormatException e) {
                    textField.setText(oldValue);
                }
            }
        });
        textField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            // Lost focus
            if (!newPropertyValue) {
                try {
                    Integer.parseInt(textField.getText());
                } catch (NumberFormatException e) {
                    textField.setText(String.valueOf(defaultValue));
                }
            }
        });
    }
}
