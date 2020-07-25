import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Controller {
    public Pane root;
    public VBox initialContainer;
    public VBox upperContainer;
    public VBox lowerContainer;

    @FXML
    private void initialize() {
        MyClass myObject = new MyClass(0);
        myObject.makeDraggable(root, upperContainer, lowerContainer);
        myObject.setDeleteOnRelease(true);
        this.initialContainer.getChildren().add(myObject);
    }

}
