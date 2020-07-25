import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public abstract class Draggable extends Region implements Cloneable {
    private Pane rootPane = null;
    private ArrayList<Node> possibleContainers = null;
    private boolean deleteOnRelease = false;
    private boolean allowDuplicatesInDestination = true;

    public void makeDraggable(Pane rootPane, Node... possibleContainers) {
        this.makeDraggable(rootPane, new ArrayList<>(Arrays.asList(possibleContainers)));
    }

    public void makeDraggable(Pane rootPane, ArrayList<Node> possibleContainers) {
        this.rootPane = rootPane;
        this.possibleContainers = possibleContainers;
        this.setOnMousePressed(e -> {
            //Node node = this.getCopyOfInstance();
            try {
                Node node = (Node)this.clone();
                node.setLayoutX(this.localToScene(this.getBoundsInLocal()).getMinX() - e.getX());
                node.setLayoutY(this.localToScene(this.getBoundsInLocal()).getMinY() - e.getY());
                node.setTranslateX(e.getX());
                node.setTranslateY(e.getY());
                this.rootPane.getChildren().add(node);
            } catch (CloneNotSupportedException i) {
                i.printStackTrace();
            }

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
                        // check if destination contains object
                        if (!this.allowDuplicatesInDestination) {
                            for (Node node : ((Pane) n).getChildren()) {

                            }
                        }
                        //((Pane) n).getChildren().add(this.getCopyOfInstance());
                        try {
                            ((Pane) n).getChildren().add((Node)this.clone());
                        } catch (CloneNotSupportedException i) {
                            i.printStackTrace();
                            return;
                        }
                        if (this.deleteOnRelease) {
                            ((Pane) this.getParent()).getChildren().remove(this);
                        }
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

    //protected abstract Node getCopyOfInstance();

    public ArrayList<Node> getPossibleContainers() {
        return this.possibleContainers;
    }

    public Pane getRootPane() {
        return this.rootPane;
    }

    public void setDeleteOnRelease(boolean deleteOnRelease) {
        this.deleteOnRelease = deleteOnRelease;
    }

    public boolean getDeleteOnRelease() {
        return this.deleteOnRelease;
    }

    public boolean getAllowDuplicatesInDestination() {
        return this.allowDuplicatesInDestination;
    }

    public void setAllowDuplicatesInDestination(boolean allowDuplicatesInDestination) {
        this.allowDuplicatesInDestination = allowDuplicatesInDestination;
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        return true;
/*
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(this.getClass());
            for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
                Method m = pd.getReadMethod();
                Object o1 = m.invoke(this);
                Object o2 = m.invoke(o);
                if (!Objects.equals(o1, o2)) {
                    return false;
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return true;

        if (o.getClass().getAnnotatedSuperclass() instanceof Draggable) {
            return false;
        }
        for (Field f : this.getClass().getDeclaredFields()) {
            try {
                if (!f.get(this).equals(f.get(o))) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;*/
    }

}
