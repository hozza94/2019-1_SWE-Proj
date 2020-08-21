
/*
 * Created: 2019.05.24 - 2019.06.03
 * Author:
 * Description: �����ڰ� ������ Ƽ���� BuyerReservedTicket User Interface���� ������ �� �׿� ���� �ڼ��� ������ �����ִ� User Interface
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
		setTitle("������ ������ Ƽ�� ������");
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
	 * Description: �������� �ʿ��� ��ư�� ����, ���, �� ��ɿ� �ʿ��� ����� ����ִ� �Լ�
	 */
	public void buttonAction(){
		JButton btnBack = new JButton("�ڷΰ���");
		btnBack.setFont(new Font("���� ���", Font.PLAIN, 20));
		btnBack.setBounds(595, 492, 124, 48);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				BuyerReservedTicket buyerReservedTicket = new BuyerReservedTicket();
				buyerReservedTicket.setVisible(true);
				dispose();
				
			}
		});
		panelBuyerReservation.add(btnBack);
		
		JButton cancelReservedButton = new JButton("�������");
		cancelReservedButton.setFont(new Font("���� ���", Font.PLAIN, 20));
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
				
				JOptionPane.showMessageDialog(null,  "���� ��ҵƽ��ϴ�!");
				dispose();
			}
		});
		
		panelBuyerReservation.add(cancelReservedButton);
	}
	
	
	/*
	 * Function: initialize()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: �������� �ʿ��� �����(Label, list, table ��)�� �����ϴ� �Լ�
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
		
		JLabel lblName = new JLabel("����ID");
		lblName.setFont(new Font("���� ���", Font.BOLD, 18));
		panelLabel.add(lblName);
		
		JLabel lblPrice = new JLabel("���� �ݾ�");
		lblPrice.setFont(new Font("���� ���", Font.BOLD, 18));
		panelLabel.add(lblPrice);
		
		JLabel lblMatchDate = new JLabel("�߱� ��� ��¥");
		lblMatchDate.setFont(new Font("���� ���", Font.BOLD, 18));
		panelLabel.add(lblMatchDate);
		
		JLabel lblCity = new JLabel("����");
		lblCity.setFont(new Font("���� ���", Font.BOLD, 18));
		panelLabel.add(lblCity);
		
		JLabel lblAwayTeam = new JLabel("Ȩ��");
		lblAwayTeam.setFont(new Font("���� ���", Font.BOLD, 18));
		panelLabel.add(lblAwayTeam);
		
		JLabel lblHomeTeam = new JLabel("�������");
		lblHomeTeam.setFont(new Font("���� ���", Font.BOLD, 18));
		panelLabel.add(lblHomeTeam);
		
		JLabel lblSeat = new JLabel("�¼���ġ");
		lblSeat.setFont(new Font("���� ���", Font.BOLD, 18));
		panelLabel.add(lblSeat);
		
	
		JPanel panelOutput = new JPanel();
		panelOutput.setBackground(Color.WHITE);
		panelOutput.setBounds(153, 0, 386, 416);
		panel.add(panelOutput);
		panelOutput.setLayout(new GridLayout(7, 1, 0, 0));
		

		lblNameValue = new JLabel(ticketId);
		lblNameValue.setFont(new Font("���� ���", Font.PLAIN, 18));
		panelOutput.add(lblNameValue);
		
		JLabel lblPriceValue = new JLabel(price);
		lblPriceValue.setFont(new Font("���� ���", Font.PLAIN, 18));
		panelOutput.add(lblPriceValue);
		
		JLabel lblMatchDateValue = new JLabel(matchDate);
		lblMatchDateValue.setFont(new Font("���� ���", Font.PLAIN, 18));
		panelOutput.add(lblMatchDateValue);
		
		JLabel lblCityValue = new JLabel(city);
		lblCityValue.setFont(new Font("���� ���", Font.PLAIN, 18));
		panelOutput.add(lblCityValue);
		
		JLabel lblHomeTeamValue = new JLabel(homeTeam);
		lblHomeTeamValue.setFont(new Font("���� ���", Font.PLAIN, 18));
		panelOutput.add(lblHomeTeamValue);
		
		JLabel lblAwayTeamValue = new JLabel(awayTeam);
		lblAwayTeamValue.setFont(new Font("���� ���", Font.PLAIN, 18));
		panelOutput.add(lblAwayTeamValue);
		

		JLabel lblSeatValue = new JLabel(seat);
		lblSeatValue.setFont(new Font("���� ���", Font.PLAIN, 18));
		panelOutput.add(lblSeatValue);

	}
	

}
