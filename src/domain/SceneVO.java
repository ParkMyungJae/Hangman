package domain;

import javafx.scene.Scene;
import views.Controller;

public class SceneVO {
	private Scene scene;
	private Controller con;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SceneVO(Scene s, Controller c, String name) {
		this.scene = s;
		this.con = c;
		this.name = name;
	}
	public Scene getScene() {
		return scene;
	}
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	public Controller getCon() {
		return con;
	}
	public void setCon(Controller con) {
		this.con = con;
	}
	
}
