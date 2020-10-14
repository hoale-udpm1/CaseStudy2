import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class DetailController {
    @FXML
    Label idLabel;
    @FXML
    Label nameLabel;
    @FXML
    Label authorLabel;
    @FXML
    Label priceLabel;
    @FXML
    Label amountLabel;

    public void setBook(Book book){
        idLabel.setText(String.valueOf(book.getId()));
        nameLabel.setText(book.getName());
        authorLabel.setText(book.getAuthor());
        priceLabel.setText(String.valueOf(book.getPrice()));
        amountLabel.setText(String.valueOf(book.getAmount()));
    }
    public void goBack(ActionEvent e) throws IOException {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        Parent sampleParent = loader.load();
        Scene scene = new Scene(sampleParent);
        stage.setScene(scene);
    }
}
