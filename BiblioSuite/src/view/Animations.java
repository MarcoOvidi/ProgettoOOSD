package view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.util.Duration;

public final class Animations {

	public static void blurOut(Node node, int duration) {
	    GaussianBlur blur = (GaussianBlur) node.getEffect();
	    Timeline timeline = new Timeline();
	    KeyValue kv = new KeyValue(blur.radiusProperty(), 0.0);
	    KeyFrame kf = new KeyFrame(Duration.millis(duration), kv);
	    timeline.getKeyFrames().add(kf);
	    timeline.setOnFinished(actionEvent -> node.setEffect(null));
	    timeline.play();
	}

	public static void blurIn(Node node,int duration) {
	    GaussianBlur blur = new GaussianBlur(0.0);
	    node.setEffect(blur);
	    Timeline timeline = new Timeline();
	    KeyValue kv = new KeyValue(blur.radiusProperty(), 10.0);
	    KeyFrame kf = new KeyFrame(Duration.millis(duration), kv);
	    timeline.getKeyFrames().add(kf);
	    timeline.play();
	}
	
	public static void blurOut(Node node) {
	    blurOut(node,200);
	}
	
	public static void blurIn(Node node) {
	    blurIn(node,200);
	}
	
	//public static void fade

}
