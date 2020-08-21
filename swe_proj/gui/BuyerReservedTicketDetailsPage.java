
/*
 * Created: 2019.05.24 - 2019.06.03
 * Author:
 * Description: 구매자가 예약한 티켓을 BuyerReservedTicket User Interface에서 선택할 시 그에 대한 자세한 정보를 보여주는 User Interface
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utility.Info;

public class BuyerReservedTicketDetailsPage extends JFrame {

	private JPanel panelBuyerReservation;
	JLabel lblNameValue;

	public BuyerReservedTicketDetailsPage(String reservationId) {
		setTitle("구매자 예약한 티켓 상세정보");
		setBounds(100, 100, 800, 600);
		panelBuyerReservation = new JPanel();
		panelBuyerReservation.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(panelBuyerReservation);
		getContentPane().setBackground(Color.WHITE);
		panelBuyerReservation.setLayout(null);
		
		String ticketdate = null, city = null, home = null, away = null, seat = null, price = null;
		Connection conn;
		try {
			conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
			Statement stmt = conn.createStatement();
			String sql = "select T.* from reservation R, ticket T where R.ticketId=T.id and R.id = " + reservationId + ";";
			ResultSet rs = stmt.executeQuery(sql);
			
			rs.next();
	        ticketdate = rs.getString(3);
	        city = rs.getString(5);
	        home = rs.getString(6);
	        away = rs.getString(7);
	        seat = rs.getString(8);
	        price = rs.getString(9);
	        
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initialize(reservationId, ticketdate, city, home, away, seat, price);
		buttonAction();
	}	
	
	/*
	 * Function: buttonAction()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: 페이지에 필요한 버튼을 생성, 출력, 및 기능에 필요한 기능이 담겨있는 함수
	 */
	public void buttonAction(){
		JButton btnBack = new JButton("뒤로가기");
		btnBack.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btnBack.setBounds(595, 492, 124, 48);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				BuyerReservedTicket buyerReservedTicket = new BuyerReservedTicket();
				buyerReservedTicket.setVisible(true);
				dispose();
				
			}
		});
		panelBuyerReservation.add(btnBack);
		
		JButton cancelReservedButton = new JButton("예약취소");
		cancelReservedButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		cancelReservedButton.setBounds(437, 492, 137, 48);
		
		cancelReservedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
					Statement stmt = conn.createStatement();
					String sql = "delete from reservation where id="+lblNameValue.getText()+";";
					stmt.executeUpdate(sql);
					
					conn.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(null,  "예약 취소됐습니다!");
				dispose();
			}
		});
		
		panelBuyerReservation.add(cancelReservedButton);
	}
	
	
	/*
	 * Function: initialize()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: 페이지에 필요한 입출력(Label, list, table 등)을 설정하는 함수
	 */
	public void initialize(String ticketId, String matchDate, String city, String homeTeam, String awayTeam, String seat, String price){
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(113, 54, 539, 416);
		panelBuyerReservation.add(panel);
		panel.setLayout(null);
		

		JPanel panelLabel = new JPanel();
		panelLabel.setBackground(Color.WHITE);
		panelLabel.setBounds(0, 0, 130, 416);
		panel.add(panelLabel);
		panelLabel.setLayout(new GridLayout(7, 1, 0, 0));
		
		JLabel lblName = new JLabel("예약ID");
		lblName.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblName);
		
		JLabel lblPrice = new JLabel("구매 금액");
		lblPrice.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblPrice);
		
		JLabel lblMatchDate = new JLabel("야구 경기 날짜");
		lblMatchDate.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblMatchDate);
		
		JLabel lblCity = new JLabel("도시");
		lblCity.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblCity);
		
		JLabel lblAwayTeam = new JLabel("홈팀");
		lblAwayTeam.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblAwayTeam);
		
		JLabel lblHomeTeam = new JLabel("어웨이팀");
		lblHomeTeam.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblHomeTeam);
		
		JLabel lblSeat = new JLabel("좌석위치");
		lblSeat.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblSeat);
		
	
		JPanel panelOutput = new JPanel();
		panelOutput.setBackground(Color.WHITE);
		panelOutput.setBounds(153, 0, 386, 416);
		panel.add(panelOutput);
		panelOutput.setLayout(new GridLayout(7, 1, 0, 0));
		

		lblNameValue = new JLabel(ticketId);
		lblNameValue.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panelOutput.add(lblNameValue);
		
		JLabel lblPriceValue = new JLabel(price);
		lblPriceValue.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panelOutput.add(lblPriceValue);
		
		JLabel lblMatchDateValue = new JLabel(matchDate);
		lblMatchDateValue.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panelOutput.add(lblMatchDateValue);
		
		JLabel lblCityValue = new JLabel(city);
		lblCityValue.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panelOutput.add(lblCityValue);
		
		JLabel lblHomeTeamValue = new JLabel(homeTeam);
		lblHomeTeamValue.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panelOutput.add(lblHomeTeamValue);
		
		JLabel lblAwayTeamValue = new JLabel(awayTeam);
		lblAwayTeamValue.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panelOutput.add(lblAwayTeamValue);
		

		JLabel lblSeatValue = new JLabel(seat);
		lblSeatValue.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panelOutput.add(lblSeatValue);

	}
	

}
