/* 해당 게임은 모니터 해상도  1920 X 1080 환경에서만 최적의 해상도로 동작합니다. */
/* (해당 해상도보다 낮으면 깨짐 현상으로 인한 플레이 불가) */
package application;
	
import java.util.ArrayList;
import java.util.List;

import domain.SceneVO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import views.Controller;


public class Main extends Application {
	
	public static Main instance;
	
	private List<SceneVO> sList = new ArrayList<>();
	private Stage ps;
	
	public static MediaPlayer mp;
	
	private void loadScene(String view, String css) {
		if(css == null) css = "application.css";
		
		FXMLLoader l = new FXMLLoader();
		l.setLocation(getClass().getResource(view));
		try {
			AnchorPane root = l.load();
			Controller c = l.getController();
			Scene scene = new Scene(root);
			
			scene.getStylesheets().add(getClass().getResource(css).toExternalForm());
			
			sList.add(new SceneVO(scene, c, view));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(view + "씬 로드 중 오류 발생");
		}
	}
	
	@Override
	public void start(Stage primaryStage) {
		instance = this;
		try {
			//초기에 모든 씬을 로드함.
			loadScene("/views/Login.fxml", null);
			loadScene("/views/Register.fxml", null);
			loadScene("/views/MainLayout.fxml", null);
			loadScene("/views/GameStage.fxml", null);
			
			ps = primaryStage;
			changeScene("Login");
			
			Image image = new Image("/imgs/LogoImg.png");
			primaryStage.getIcons().add(image);
			primaryStage.setTitle("10116 박명재 행맨 게임 프로젝트");
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			Media media = new Media(getClass().getResource("/imgs/sound.wav").toURI().toString());
			mp = new MediaPlayer(media);
			mp.play();
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("재생장치 없음 (스피커를 연결해주세요)");
		}
	}
	
	private SceneVO findScene(String name ) {
		for(SceneVO s : sList) {
			if(s.getName().contains(name)) {
				return s;
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void changeScene(String name) {
		SceneVO s = findScene(name);
		if(s == null) {
			System.out.println("로딩 실패");
			return;
		}
		ps.setScene(s.getScene());
		s.getCon().init();
		
		if(s.getName().equals("/views/MainLayout.fxml") || s.getName().equals("/views/GameStage.fxml") ) {
			ps.setFullScreen(true);
		}else {
			ps.setFullScreen(false);
		}
	}
}
