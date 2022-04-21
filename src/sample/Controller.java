package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class Controller {
    CalcModel calcModel = new CalcModel();
    private float conductWidth;
    private float effectDielConst;
    private float waveLenghtFreeSpace;
    private float freq;
    private float waveLenght;
    private float subThickness;
    private float stripThickness;
    private float lossTangent;
    private float dielectricConst;
    private float waveResist;
    private float dielectricLoss;
    private float conductLoss;
    private float conduct;
    private float totalAttenuation;

    private final String[] freqS = {"Гц", "кГц", "МГц", "ГГц"};
    private final String[] subThicknesseS = {"мм", "см", "м"};
    private final String[] stripThicknesseS = {"мкм", "мм"};
    private final String[] totalAttenS = {"дБ/мм", "дБ/см", "дБ/м"};
    private final String[] waveLengthS = {"мкм", "мм", "см", "м"};
    private final String[] dielectricLosseS = {"дБ/мм", "дБ/см", "дБ/м"};
    private final String[] conductLosseS = {"дБ/мм", "дБ/см", "дБ/м"};

    private boolean isCalculated = false;
    @FXML
    private ChoiceBox<String> subThicknessChoiceBox;
    @FXML
    private ChoiceBox<String> totalAttenChoiceBox;
    @FXML
    private ChoiceBox<String> stripThicknessChoiceBox;
    @FXML
    private ChoiceBox<String> freqChoiceBox;
    @FXML
    private ChoiceBox<String> conductLossChoiceBox;
    @FXML
    private ChoiceBox<String> dielectricLossChoiceBox;
    @FXML
    private ChoiceBox<String> waveLengthChoiceBox;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button calcButton;
    @FXML
    private TextField conductField;
    @FXML
    private TextField conductLossField;
    @FXML
    private TextField conductWidthField;
    @FXML
    private TextField dielectricConstField;
    @FXML
    private TextField dielectricLossField;
    @FXML
    private TextField effectDielConstField;
    @FXML
    private TextField freqField;
    @FXML
    private TextField stripThicknessField;
    @FXML
    private TextField lossTangetField;
    @FXML
    private TextField subThicknessField;
    @FXML
    private TextField totalAttenField;
    @FXML
    private TextField waveLengthField;
    @FXML
    private TextField waveResistField;
    @FXML
    void initialize() {

        freqChoiceBox.setValue("Гц");
        subThicknessChoiceBox.setValue("мм");
        stripThicknessChoiceBox.setValue("мкм");
        totalAttenChoiceBox.setValue("дБ/м");
        waveLengthChoiceBox.setValue("м");
        dielectricLossChoiceBox.setValue("дБ/м");
        conductLossChoiceBox.setValue("дБ/м");

        conductField.setText("5.88E+07");

        freqChoiceBox.getItems().addAll(freqS);
        subThicknessChoiceBox.getItems().addAll(subThicknesseS);
        stripThicknessChoiceBox.getItems().addAll(stripThicknesseS);
        waveLengthChoiceBox.getItems().addAll(waveLengthS);
        dielectricLossChoiceBox.getItems().addAll(dielectricLosseS);
        conductLossChoiceBox.getItems().addAll(conductLosseS);
        totalAttenChoiceBox.getItems().addAll(totalAttenS);

        addTextLimiter(waveResistField, 6);
        addTextLimiter(freqField, 6);
        addTextLimiter(dielectricConstField, 6);
        addTextLimiter(subThicknessField, 6);
        addTextLimiter(stripThicknessField, 6);
        addTextLimiter(lossTangetField, 6);
        addTextLimiter(conductField, 6);

        calcButton.setOnAction(actionEvent -> {
            initVars();
            calculate();
            output();
            isCalculated = true;
        });

        freqChoiceBox.setOnAction(actionEvent -> {
            if (isCalculated) {
                initVars();
                calculate();
                output();
            }
        });

        subThicknessChoiceBox.setOnAction(actionEvent -> {
            if (isCalculated){
                initVars();
                calculate();
                output();
            }
        });

        stripThicknessChoiceBox.setOnAction(actionEvent -> {
            if (isCalculated){
                initVars();
                calculate();
                output();
            }
        });

        totalAttenChoiceBox.setOnAction(actionEvent -> {
            if (isCalculated){
                output();
            }
        });

        waveLengthChoiceBox.setOnAction(actionEvent -> {
            if (isCalculated){
                output();
            }
        });

        waveLengthChoiceBox.setOnAction(actionEvent -> {
            if (isCalculated){
                output();
            }
        });

    }

    public float parseFloat(TextField textField){
        String string = textField.getText();
        float result = 0;
        string = string.replace(",", ".");
        try {
            result = Float.parseFloat(string);
        }catch (NumberFormatException e){
            textField.setText("0");
        }
        return result;
    }


    public String getFormatedString(float value){
        String string = String.format("%.4f", value);
        return string;
    }
    public void initVars(){
        waveResist = parseFloat(waveResistField);
        freq = parseFloat(freqField);
        switch (freqChoiceBox.getValue()){
            case "кГц": freq = freq * 1000; break;
            case "МГц": freq = (float) (freq * Math.pow(10, 6)); break;
            case "ГГц": freq = (float) (freq * Math.pow(10, 9)); break;
            default:
        }
        dielectricConst = parseFloat(dielectricConstField);
        subThickness = parseFloat(subThicknessField);
        switch (subThicknessChoiceBox.getValue()){
            case "мм": subThickness = subThickness/1000; break;
            case "см": subThickness = subThickness/100; break;
            default:
        }

        stripThickness = parseFloat(stripThicknessField);
        switch (stripThicknessChoiceBox.getValue()){
            case "мкм": stripThickness = (float) (stripThickness * Math.pow(10, -6)); break;
            case "мм": stripThickness = stripThickness/1000; break;
            default:
        }
        lossTangent = parseFloat(lossTangetField);
        conduct = parseFloat(conductField);
    }

    public void calculate(){
        conductWidth = calcModel.getConductWidth(waveResist, subThickness, dielectricConst, stripThickness);
        effectDielConst = calcModel.getEffectDielConst(dielectricConst, subThickness, conductWidth);
        waveLenghtFreeSpace = calcModel.getWaveLenghtFreeSpace(freq);
        waveLenght = calcModel.getWaveLenght(freq, effectDielConst);
        dielectricLoss = calcModel.getDielectricLoss(dielectricConst, effectDielConst, lossTangent, waveLenghtFreeSpace);
        conductLoss = calcModel.getConductLoss(conduct, freq, waveResist, conductWidth);
        totalAttenuation = calcModel.getTotalAttenuation(dielectricLoss, conductLoss);
    }

    public void output(){
        conductWidthField.setText(getFormatedString(conductWidth * 1000));
        effectDielConstField.setText(getFormatedString(effectDielConst));
        waveLengthField.setText(getFormatedString(waveLenght));
        switch (dielectricLossChoiceBox.getValue()) {
            case "дБ/см" -> dielectricLossField.setText(getFormatedString(dielectricLoss / 100));
            case "дБ/мм" -> dielectricLossField.setText(getFormatedString(dielectricLoss / 1000));
            default -> dielectricLossField.setText(getFormatedString(dielectricLoss));
        }
        dielectricLossField.setText(getFormatedString(dielectricLoss));


        switch (conductLossChoiceBox.getValue()) {
            case "дБ/см" -> conductLossField.setText(getFormatedString(conductLoss / 100));
            case "дБ/мм" -> conductLossField.setText(getFormatedString(conductLoss / 1000));
            default -> conductLossField.setText(getFormatedString(conductLoss));
        }

        switch (totalAttenChoiceBox.getValue()) {
            case "дБ/см" -> totalAttenField.setText(getFormatedString(totalAttenuation / 100));
            case "дБ/мм" -> totalAttenField.setText(getFormatedString(totalAttenuation / 1000));
            default -> totalAttenField.setText(getFormatedString(totalAttenuation));
        }
    }

    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().equals(",") || tf.getText().equals(".")){
                    tf.setText("");
                }

                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }



}