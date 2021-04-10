package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Controller {
    private CarShowroomData carShowroomData;

    private static final String CSV_SEPARATOR = ";";

    private List<Vehicle> soldCars;

    @FXML
    private ComboBox<String> cityBox;

    @FXML
    private ComboBox<String> showroomBox;

    @FXML
    private TableView<Vehicle> carsTable;

    @FXML
    private TableColumn<Vehicle, String> brandColumn;

    @FXML
    private TableColumn<Vehicle, String> modelColumn;

    @FXML
    private TableColumn<Vehicle, Double> priceColumn;

    @FXML
    private TableColumn<Vehicle, Integer> yearColumn;

    @FXML
    private TableColumn<Vehicle, ItemCondition> conditionColumn;

    @FXML
    private TableColumn<Vehicle, String> cityColumn;

    @FXML
    private TableColumn<Vehicle, String> statusColumn;

    @FXML
    private TextField searchField;

    @FXML
    private AnchorPane anchorPane;

    public Controller() {
        this.carShowroomData = new CarShowroomData();
        this.soldCars = new ArrayList<>();
    }

    public void initialize() {
        carsTable.setRowFactory(tv -> new TableRow<Vehicle>() {
            private Tooltip tooltip = new Tooltip();

            @Override
            public void updateItem(Vehicle vehicle, boolean empty) {
                super.updateItem(vehicle, empty);
                if (vehicle == null) {
                    setTooltip(null);
                } else {
                    tooltip.setText("Pojemność śilnika: " + vehicle.getEngineCapacity() + "  Przebieg: " + vehicle.getMileage());
                    setTooltip(tooltip);
                }
            }
        });

        readFromCSV();

        ObservableList<String> listShowroom = FXCollections.observableArrayList(carShowroomData.carShowroomContainer.listOfCarSaloons());
        listShowroom.add("Sprzedane");
        listShowroom.add("Salony");
        showroomBox.setItems(listShowroom);
        showroomBox.getSelectionModel().select("Salony");
        ObservableList<String> listCity = FXCollections.observableArrayList(carShowroomData.carShowroomContainer.listOfCity());
        listCity.add("Miasto");
        cityBox.setItems(listCity);
        cityBox.getSelectionModel().select("Miasto");


        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        brandColumn.setReorderable(false);
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelColumn.setReorderable(false);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setReorderable(false);
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("yearOfProduction"));
        yearColumn.setReorderable(false);
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));
        conditionColumn.setReorderable(false);
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        cityColumn.setReorderable(false);
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setReorderable(false);
        fillTable();

    }

    @FXML
    public void onSalonSelected() {
        if (!cityBox.getSelectionModel().getSelectedItem().equals("Miasto")) {
            fillWhenCityAndShowroomIsSelected(cityBox.getValue(), showroomBox.getValue());
        } else {
            if (showroomBox.getValue().equals("Salony")) {
                fillTable();
            } else if (showroomBox.getValue().equals("Sprzedane")) {
                fillTableSold();
            } else {
                fillTableWhenShowroomSelected(showroomBox.getValue());
            }
        }
    }

    @FXML
    public void onCitySelected() {
        String city = cityBox.getValue();
        if (city.equals("Miasto")) {
            fillTable();
        } else {
            fillWhenCitySelected(city);
        }
    }

    @FXML
    public void searchButton() {
        fillWhenSearch(searchField.getText());
    }

    public void fillTableWhenShowroomSelected(String name) {
        ObservableList<Vehicle> list = FXCollections.observableArrayList(carShowroomData.carShowroomContainer.selectedParticularShowroom(name).getCarList());
        if (!list.isEmpty()) carsTable.setItems(list);
    }

    public void fillTable() {
        ObservableList<String> list = FXCollections.observableArrayList(carShowroomData.carShowroomContainer.listOfCarSaloons());
        ObservableList<Vehicle> list2 = FXCollections.observableArrayList();
        for (String s : list) {
            List<Vehicle> temp = new ArrayList<>(carShowroomData.carShowroomContainer.selectedParticularShowroom(s).getCarList());
            list2.addAll(temp);
        }
        if (!list2.isEmpty()) carsTable.setItems(list2);
    }

    public void fillWhenCitySelected(String city) {
        ObservableList<String> list = FXCollections.observableArrayList(carShowroomData.carShowroomContainer.listOfCarSaloons());
        ObservableList<Vehicle> list2 = FXCollections.observableArrayList();
        for (String s : list) {
            List<Vehicle> temp = new ArrayList<>(carShowroomData.carShowroomContainer.selectedParticularShowroom(s).getCarList());
            for (Vehicle vehicle : temp) {
                if (vehicle.getCity().equals(city)) {
                    list2.add(vehicle);
                }
            }
        }
        if (!list2.isEmpty()) carsTable.setItems(list2);
    }

    public void fillWhenCityAndShowroomIsSelected(String city, String showroom) {
        if (showroom.equals("Salony")) {
            fillWhenCitySelected(city);
            return;
        }
        ObservableList<Vehicle> list = FXCollections.observableArrayList(carShowroomData.carShowroomContainer.selectedParticularShowroom(showroom).getCarList());
        ObservableList<Vehicle> list2 = FXCollections.observableArrayList();
        for (Vehicle vehicle : list) {
            if (vehicle.getCity().equals(city)) list2.add(vehicle);
        }
        if (!list2.isEmpty()) carsTable.setItems(list2);
    }

    public void fillWhenSearch(String name) {
        ObservableList<String> list = FXCollections.observableArrayList(carShowroomData.carShowroomContainer.listOfCarSaloons());
        ObservableList<Vehicle> list2 = FXCollections.observableArrayList();
        for (String s : list) {
            List<Vehicle> temp = new ArrayList<>(carShowroomData.carShowroomContainer.selectedParticularShowroom(s).getCarList());
            for (Vehicle vehicle : temp) {
                if (vehicle.getBrand().equals(name)) {
                    list2.add(vehicle);
                }
            }
        }
        if (!list2.isEmpty()) carsTable.setItems(list2);
    }

    // kupowanie samochodu czyli tak naprawdę usuwanie go
    public void buyButton() {
        if (carsTable.getSelectionModel().getSelectedItem() != null) {
            Vehicle vehicle = carsTable.getSelectionModel().getSelectedItem();
            if (vehicle.getStatus() != "Rez" && vehicle.getStatus() != "Niedostępny") {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(anchorPane.getScene().getWindow());
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("afterBuy.fxml"));
                    dialog.getDialogPane().setContent(root);
                } catch (IOException e) {
                    System.out.println("Couldn't load the dialog");
                    e.printStackTrace();
                    return;
                }
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);


                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    vehicle.setStatus("Niedostępny");
                    carShowroomData.carShowroomContainer.selectedParticularShowroom(carShowroomData.carShowroomContainer.findParticularSaloon(vehicle)).removeProduct(vehicle);
                    soldCars.add(vehicle);
                    fillTable();
                    showroomBox.getSelectionModel().select("Salony");
                    cityBox.getSelectionModel().select("Miasto");
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Samochod zarezerwowany tudziez niedostepny, nie mozesz tego kupic");
                alert.showAndWait();
            }
        }
    }

    // rezerwacja
    public void reservationButton() {
        ObservableList<Vehicle> selectedRow, allCars;
        allCars = carsTable.getItems();
        selectedRow = carsTable.getSelectionModel().getSelectedItems();

        for (Vehicle vehicle : selectedRow) {
            allCars.remove(vehicle);
            vehicle.setStatus("Rez");
            allCars.add(vehicle);
            carsTable.refresh();
        }
    }

    public void fillTableSold() {
        ObservableList<Vehicle> list2 = FXCollections.observableArrayList();
        list2.addAll(soldCars);
        if (!list2.isEmpty()) carsTable.setItems(list2);
    }

    public void readFromCSV() {
        ObservableList<String> list = FXCollections.observableArrayList(carShowroomData.carShowroomContainer.listOfCarSaloons());
        String stringCurrentLine;

        for (String s : list) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(s).append(".csv");
            try {
                BufferedReader bw = new BufferedReader(new InputStreamReader(new FileInputStream(stringBuilder.toString())));
                for (int i = 0; (stringCurrentLine = bw.readLine()) != null; i++) {
                    if (i == 0) {
                        continue;
                    }
                    String[] result = stringCurrentLine.split("\\;");
                    Vehicle vehicle = new Vehicle(result[0], result[1], ItemCondition.NEW, Double.parseDouble(result[2]), Integer.parseInt(result[3]), Double.parseDouble(result[4]),
                            Double.parseDouble(result[5]), Integer.parseInt(result[6]), result[7]);
                    carShowroomData.carShowroomContainer.selectedParticularShowroom(s).addProduct(vehicle);
                }

                bw.close();
            } catch (IOException e) {
                alert();
            }
        }

        try {
            BufferedReader bw = new BufferedReader(new InputStreamReader(new FileInputStream("SprzedaneSamochody.csv")));
            for (int i = 0; (stringCurrentLine = bw.readLine()) != null; i++) {
                if (i == 0) {
                    continue;
                }
                String[] result = stringCurrentLine.split("\\;");
                Vehicle vehicle = new Vehicle(result[0], result[1], ItemCondition.NEW, Double.parseDouble(result[2]), Integer.parseInt(result[3]), Double.parseDouble(result[4]),
                        Double.parseDouble(result[5]), Integer.parseInt(result[6]), result[7]);
                vehicle.setStatus("Niedostępny");
                soldCars.add(vehicle);
            }
        } catch (IOException e) {
            alert();
        }
    }

    public void writeToCSV() {
        ArrayList<String> columnNames = new ArrayList<String>();
        for (Field f : Vehicle.class.getDeclaredFields()) {
            CSVAnnotation annotation = f.getDeclaredAnnotation(CSVAnnotation.class);
            if (annotation != null)
                columnNames.add(annotation.value()); //dodanie adnotacji do listy
        }
        String concatenated = String.join(",", columnNames);


        ObservableList<String> list = FXCollections.observableArrayList(carShowroomData.carShowroomContainer.listOfCarSaloons());

        for (String s : list) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(s).append(".csv");


            List<Vehicle> temp = new ArrayList<>(carShowroomData.carShowroomContainer.selectedParticularShowroom(s).getCarList());
            try {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(stringBuilder.toString()), "UTF-8"));
                write(bw, concatenated);
                write(bw, temp);
                bw.flush();
                bw.close();
            } catch (IOException e) {
                alert();
            }
        }

        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("SprzedaneSamochody.csv"), "UTF-8"));
            write(bw, concatenated);
            write(bw, soldCars);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            alert();
        }

    }

    public void write(BufferedWriter bw, List<Vehicle> sold) throws IOException {
        for (Vehicle vehicle : sold) {
            StringBuffer oneLine = new StringBuffer();
            oneLine.append(vehicle.getBrand().trim().length() == 0 ? "" : vehicle.getBrand());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(vehicle.getModel().trim().length() == 0 ? "" : vehicle.getModel());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(vehicle.getPrice() < 0 ? "" : vehicle.getPrice());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(vehicle.getYearOfProduction() < 0 ? "" : vehicle.getYearOfProduction());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(vehicle.getMileage() < 0 ? "" : vehicle.getMileage());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(vehicle.getEngineCapacity() < 0 ? "" : vehicle.getEngineCapacity());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(vehicle.getAmount() < 0 ? "" : vehicle.getAmount());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(vehicle.getCity().trim().length() == 0 ? "" : vehicle.getCity());
            bw.write(oneLine.toString());
            bw.newLine();
        }
    }

    public void write(BufferedWriter bw, String s) throws IOException {
        bw.write(s);
        bw.newLine();
    }

    public void alert() {
        carsTable.setPlaceholder(new Label("ERROR! Nie znaleziono pliku!!!"));
    }
}
