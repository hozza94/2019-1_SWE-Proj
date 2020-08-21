
/*
 * Created: 2019.05.24 - 2019.06.03
 * Author:
 * Description: 판매자가 판매 가능한 티켓들 중 옥션항목이 체크되어 있는 티켓을 구매할 구매자 페이지 User Interface
 */


package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Main extends JFrame{
	
	JFrame jframe;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGui loginGui = new LoginGui();
					loginGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/*
	public Main() {
		setLayout(null);
		setTitle("SoftwareEngineering");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 500);
		setVisible(false);
	}
	*/
}
