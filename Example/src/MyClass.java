import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MyClass extends Draggable {

    public int i;

    public MyClass(int i) {
        this.i = i;
        HBox box = new HBox();
        box.setPrefSize(130, 20);
        box.setStyle("-fx-border-color: black; -fx-background-color: white; -fx-alignment: center");
        Label label = new Label("Drag me!");
        box.getChildren().add(label);
        this.getChildren().add(box);
    }
/*
    @Override
    protected Node getCopyOfInstance() {
        MyClass newObject = new MyClass(this.i);
        newObject.makeDraggable(this.getRootPane(), this.getPossibleContainers());
        newObject.setDeleteOnRelease(this.getDeleteOnRelease());
        return newObject;
    }*/
}
