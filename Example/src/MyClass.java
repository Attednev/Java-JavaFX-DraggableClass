import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MyClass extends Draggable {

    public MyClass() {
        HBox box = new HBox();
        box.setPrefSize(130, 20);
        box.setStyle("-fx-border-color: black; -fx-background-color: white; -fx-alignment: center");
        Label label = new Label("Drag me!");
        box.getChildren().add(label);
        this.getChildren().add(box);
    }

    @Override
    protected Node getCopyOfInstance() {
        MyClass newObject = new MyClass();
        newObject.makeDraggable(this.getRootPane(), this.getPossibleContainers());
        return newObject;
    }
}
