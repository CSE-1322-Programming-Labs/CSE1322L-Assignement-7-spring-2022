package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Locale;

public class HelloController {
    @FXML
    private Label pleaseEnterADoubleLabel;
    @FXML
    private Label annualPaymentLabel;
    @FXML
    private TextField textField;
    @FXML
    private ListView<Integer> listView;
    @FXML
    private ComboBox<Double> interestRatesComboBox;
    @FXML
    private RadioButton yesRadioButton;
    @FXML
    private RadioButton noRadioButton;
    @FXML
    private CheckBox noMissedPaymentsCheckbox;
    @FXML
    private CheckBox automaticWithdrawalCheckbox;


    @FXML
    public void initialize(){ //add the data to the listview and the combobox.
        ObservableList<Double> boardSizeList = FXCollections.observableArrayList(3.00,3.50,4.00,4.50,5.00,5.50,6.00,6.50,7.00);
        interestRatesComboBox.setValue(3.00); //set it to 3.00 as a default.
        interestRatesComboBox.setItems(boardSizeList); //set the items in the list

        ObservableList<Integer> yearsList = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
        listView.setItems(yearsList); //set the items in the list.
        listView.getSelectionModel().select(0); //select the first item by default.
    }

    @FXML
    protected void onCalculateButtonPressed() {
        pleaseEnterADoubleLabel.setVisible(false); //reset the error message, but reshow it if they mess up again.
        if(yesRadioButton.isSelected()){ //if it's in deferment, the annual payment will be 0.
            annualPaymentLabel.setText("0.00");
        }else{
            double amountOfLoan = 0; //this will be initialized to 0, however, the user must put a valid value, else this method will end.
            try{
                amountOfLoan = Double.parseDouble(textField.getText());
            }catch (NumberFormatException e){
                pleaseEnterADoubleLabel.setVisible(true);
                return;
            }

            double interestRate = interestRatesComboBox.getSelectionModel().getSelectedItem() / 100d; //this will get the interest rate based on what user put.
            int numOfYears = listView.getSelectionModel().getSelectedItem(); //this will get the numOfYears based on what user put.

            if(noMissedPaymentsCheckbox.isSelected()){ //check if no missed payments box is on.
                interestRate -= 0.0025d; //lower interest rate by 0.25% if so
            }
            if(automaticWithdrawalCheckbox.isSelected()){ //check if automatic withdrawal is on.
                interestRate -= 0.0025d; //lower interest rate by 0.25% if so
            }

            //calculate the annual payment.
            annualPaymentLabel.setText(
                    "$"+String.format(Locale.US,"%,.2f" , ((interestRate * amountOfLoan) / (1-Math.pow((1+interestRate),numOfYears * -1)))) //format the string as well as set the text.
            );


        }
    }

}