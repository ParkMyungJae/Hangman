//https://www.youtube.com/watch?v=0FVx64hJvlo
//디자인 참고 사이트

package views;

import application.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;

public class MainController implements Controller {

	public void Exit(ActionEvent e) throws Exception {
		Platform.exit();
	}
	
	public void ID_Change() {
		Main.instance.changeScene("Login");
	}
	
	public void gameStart() {
		Main.instance.changeScene("GameStage");
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}
