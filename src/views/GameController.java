package views;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import application.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

public class GameController implements Controller {
	public static GameController gc;

	@FXML
	private ImageView imgView;

	private List<Image> imgList = new ArrayList<>();

	private ArrayList<String> arr = new ArrayList<String>();

	@FXML
	private Text SuccessField;

	@FXML
	private Text useBox;

	@FXML
	private Button reload;

	@FXML
	private Text noUseFlag;
	
	public String word; // 현재 문제

	public String answer; // 사용자 답안

	public String value;

	private HashSet<String> hs = new HashSet<String>();
	
	private int imgCnt = 0;
	
	private int noUseCnt = 10;
	
	private boolean gameSTOP;
	
	public static MediaPlayer mp;
	
	@FXML
	private void initialize() {
		useBox.setText("사용자가 사용한 단어가 표시됩니다.");
		for (int i = 1; i <= 10; i++) {
			Image img = new Image(getClass().getResource("/imgs/GameImg/" + i + ".png").toString());
			imgList.add(img);
		}
		Image img = new Image(getClass().getResource("/imgs/GameImg/11.gif").toString());
		imgList.add(img);
		
		
		GamePS();
		imgView.setImage(imgList.get(0));
		imgCnt = 0;
		noUseCnt = 10;
		noUseFlag.setText(noUseCnt + "번 남음");

	}

	public void Exit(ActionEvent e) throws Exception {
		Platform.exit();
	}

	public void ID_Change() {
		Main.instance.changeScene("Login");
	}

	@Override
	public void init() {
		try {
			Media media = new Media(getClass().getResource("/imgs/gameSound.mp3").toURI().toString());
			mp = new MediaPlayer(media);
			mp.play();
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("재생장치 없음 (스피커를 연결해주세요)");
		}
	}

	public void GamePS() {
		try {
			File file = new File(getClass().getResource("/views/word.txt").getFile());
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);

			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				arr.add(line);
			}

			fis.close();
			isr.close();
			br.close();
			useBox.setText("");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("FILE READ ERROR");
		}

		getQuestion();
		draw();
	}

	public void getQuestion() {
		Random rnd = new Random();
		int idx = rnd.nextInt(arr.size());
		word = arr.get(idx);
		answer = "";
		for (int i = 0; i < word.length(); i++) {
			answer += "_";
		}
	}

	public void draw() {
		SuccessField.setText(answer);
	}

	public void keyboard(ActionEvent e) {
		Node targetNode = (Node) e.getTarget();
		value = (String) targetNode.getUserData();
		String newAnswer = "";
		int cnt = 0;

		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == value.charAt(0)) {
				newAnswer += value;
				cnt++;
			} else if (answer.charAt(i) != '_') {
				newAnswer += answer.charAt(i);
				cnt++;
			} else {
				newAnswer += "_";
			}
		}
		answer = newAnswer;
		if (useBox.getText().toString().contains(value.toString())) {
			System.out.println("이미 사용한 단어");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Check!");
			alert.setHeaderText(null);
			alert.setContentText("이미 사용하신 단어 입니다.");

			alert.show();
			
			return;
		} else {
			hs.clear();
			String tmp = useBox.getText();
			for (int i = 0; i < tmp.length(); i++) {
				hs.add(tmp.substring(i, i + 1));
			}
			hs.add(value);
			tmp = "";
			Iterator<String> itr = hs.iterator();
			while (itr.hasNext()) {
				tmp += itr.next();
			}
			useBox.setText(tmp);
		}

		draw();
		
		if (word.length() == cnt) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(null);
			alert.setContentText("정답입니다. 행맨을 구출하셨습니다. 멋져요!");

			alert.show();
		}
		
		imgCnts();
	}

	public void imgCnts() {
		if (!word.contains(value)) {
			System.out.println("틀림");
			imgCnt++;
			noUseCnt--;
			System.out.println(imgCnt);
			imgView.setImage(imgList.get(imgCnt));
			String noUseFlagVar;
			
			noUseFlagVar = Integer.toString(noUseCnt);

			noUseFlag.setText(noUseFlagVar + "번 남음");
		}
		
		if (imgCnt == 10) {
			imgView.setImage(new Image(this.getClass().getResource("/imgs/GameImg/11.gif").toExternalForm()));
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Fail");
			alert.setHeaderText("행맨을 살리지 못했습니다.. 패배..");
			alert.setContentText("정답 : " + word);

			alert.show();
			
			SuccessField.setDisable(true);
			useBox.setDisable(true);
			noUseFlag.setDisable(true);
			imgCnt = 0;
			noUseFlag.setText(noUseCnt + "번 남음");
			return;
		}
	}
}
