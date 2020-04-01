import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Draggable extends Region {
    private Pane rootPane = null;
    private ArrayList<Node> possibleContainers = null;

    public void makeDraggable(Pane rootPane, Node... possibleContainers) {
        this.makeDraggable(rootPane, new ArrayList<>(Arrays.asList(possibleContainers)));
    }

    public void makeDraggable(Pane rootPane, ArrayList<Node> possibleContainers) {
        this.rootPane = rootPane;
        this.possibleContainers = possibleContainers;
        this.setOnMousePressed(e -> {
            Node node = this.getCopyOfInstance();
            node.setLayoutX(this.localToScene(this.getBoundsInLocal()).getMinX() - e.getX());
            node.setLayoutY(this.localToScene(this.getBoundsInLocal()).getMinY() - e.getY());
            node.setTranslateX(e.getX());
            node.setTranslateY(e.getY());
            this.rootPane.getChildren().add(node);
        });
        this.setOnMouseDragged(e -> {
            if (this.rootPane.getChildren().size() > 1) {
                this.rootPane.getChildren().get(this.rootPane.getChildren().size() - 1).setTranslateX(e.getX());
                this.rootPane.getChildren().get(this.rootPane.getChildren().size() - 1).setTranslateY(e.getY());
            }
        });
        this.setOnMouseReleased(e -> {
            if (this.rootPane.getChildren().size() > 1) {
                this.rootPane.getChildren().remove(this.rootPane.getChildren().size() - 1);
                for (Node n : possibleContainers) {
                    if (overNode(n, e)) {
                        ((Pane) n).getChildren().add(this.getCopyOfInstance());
                        break;
                    }
                }
            }
        });
    }

    public void makeUnDraggable() {
        this.setOnMousePressed(e -> {});
        this.setOnMouseDragged(e -> {});
        this.setOnMouseReleased(e -> {});
        this.rootPane = null;
        this.possibleContainers = null;
    }

    private boolean overNode(Node node, MouseEvent e) {
        return e.getSceneX() >= node.localToScene(node.getBoundsInLocal()).getMinX() &&
                e.getSceneY() >= node.localToScene(node.getBoundsInLocal()).getMinY() &&
                e.getSceneX() <= node.localToScene(node.getBoundsInLocal()).getMaxX() &&
                e.getSceneY() <= node.localToScene(node.getBoundsInLocal()).getMaxY();
    }

    protected abstract Node getCopyOfInstance();

    public ArrayList<Node> getPossibleContainers() {
        return this.possibleContainers;
    }

    public Pane getRootPane() {
        return this.rootPane;
    }

}
