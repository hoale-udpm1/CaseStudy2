import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    private ImageView imageView;

    @FXML
    private TableView<Book> table;

    @FXML
    private TableColumn<Book, Integer> idColumn;

    @FXML
    private TableColumn<Book, String> nameColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, Integer> amountColumn;

    @FXML
    private TableColumn<Book, Integer> priceColumn;

    public ObservableList<Book> bookList;

    @FXML
    private TextField idText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField authorText;

    @FXML
    private TextField amountText;

    @FXML
    private TextField priceText;
    private ActionEvent e;

    @FXML
    private TextField searchField;
    private final NumberFormat numberFormat= new DecimalFormat("#,###");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookList = FXCollections.observableArrayList(
                readFile()
        );
        idColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("price"));
        priceColumn.setCellFactory(tc -> new TableCell<Book, Integer>() {
            @Override
            protected void updateItem(Integer price, boolean empty) {
                super.updateItem(price, empty);
                if (price == null || empty) {
                    setText(null);
                } else {
                    setText(numberFormat.format(price));
                }
            }
        });

        amountColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("amount"));
        table.setItems(bookList);
        search();

    }

    public void imageButton() throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        File fileSelected = fileChooser.showOpenDialog(null);
        if (fileSelected != null) {
            String imageFile = fileSelected.toURI().toURL().toString();
            Image image = new Image(imageFile);
            imageView.setImage(image);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Wrong File!");
            alert.setHeaderText(null);
            alert.setContentText("Please choose a file!");
            alert.showAndWait();
        }
    }

    public List<Book> readFile() {
        List<Book> list = new ArrayList<>();
        FileInputStream file;
        ObjectInputStream object;
        try {
            file = new FileInputStream("Book.txt");
            object = new ObjectInputStream(file);
            while (true) {
                list.add((Book) object.readObject());
            }
        } catch (EOFException exception) {
            exception.getMessage();
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void writeFile() {
        FileOutputStream file;
        ObjectOutputStream object;
        try {
            file = new FileOutputStream("Book.txt");
            object = new ObjectOutputStream(file);
            for (Book food : bookList) {
                object.writeObject(food);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void add(ActionEvent e) {
        Book newBook = new Book();
//        newBook.setId(Integer.parseInt(idText.getText()));
        newBook.setName(nameText.getText());
        newBook.setAuthor(authorText.getText());
        newBook.setPrice(Integer.parseInt(priceText.getText()));
        newBook.setAmount(Integer.parseInt(amountText.getText()));
        bookList.add(newBook);
    }

    public void delete(ActionEvent e) {
        Book selected = table.getSelectionModel().getSelectedItem();
        bookList.remove(selected);

    }

    public void changeSceneBookDetail(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Detail.fxml"));
        Parent studentViewParent = loader.load();
        Scene scene = new Scene(studentViewParent);
        DetailController controller = loader.getController();
        Book selected = table.getSelectionModel().getSelectedItem();
        controller.setBook(selected);
        stage.setScene(scene);
    }

    public void search() {
        FilteredList<Book> searchList = new FilteredList<>(bookList, b -> true);
        searchField.textProperty().addListener(((observable, oldValue, newValue) -> {
            searchList.setPredicate(book -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowercaseValue = newValue.toLowerCase();
                if (String.valueOf(book.getId()).indexOf(lowercaseValue) != -1) return true;
                else if (book.getName().toLowerCase().indexOf(lowercaseValue) != -1) return true;
                else if (String.valueOf(book.getAuthor()).indexOf(lowercaseValue) != -1) return true;
                else if (String.valueOf(book.getPrice()).indexOf(lowercaseValue) != -1) return true;
                else if (String.valueOf(book.getAmount()).indexOf(lowercaseValue) != -1) return true;
                else return false;
            });
        }));
        table.setItems(searchList);
    }

    public void update(ActionEvent e) {
        Book selected = table.getSelectionModel().getSelectedItem();
        idText.setText(String.valueOf(selected.getId()));
        nameText.setText(String.valueOf(selected.getName()));
        authorText.setText(String.valueOf(selected.getAuthor()));
        priceText.setText(String.valueOf(selected.getPrice()));
        amountText.setText(String.valueOf(selected.getAmount()));
        bookList.remove(selected);
    }

}