
/*
 * Created: 2019.05.24 - 2019.06.03
 * Author:
 * Description: SellerMainPage���� �Ǹ��ڰ� ����� Ƽ���� ���ý� �׿� ���� �������� �Բ� �����ϰ� ���� �ݾ� �Է¶��� ��Ÿ���� User Interface
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utility.Info;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class SellerTicketDetailsPage extends JFrame {

	private JPanel panelSellerTicketDetail;
	private JTextField textField;
	JTextField textModifyPrice;
	JRadioButton rdbtnAuction;
	boolean defalutAuctionCheck;

	
	public SellerTicketDetailsPage(String ticketName, String price, String matchDate, String city, String homeTeam, String awayTeam, String seat) {
		
		setTitle("�Ǹ��� �˻� Ƽ�� ����ȸ");
		setBounds(100, 100, 800, 600);
		panelSellerTicketDetail = new JPanel();
		panelSellerTicketDetail.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelSellerTicketDetail);
		getContentPane().setBackground(Color.WHITE);
		panelSellerTicketDetail.setLayout(null);
		
		
		
		
		
		initialize(ticketName, price, matchDate, city, homeTeam, awayTeam, seat);
		buttonAction(ticketName, price);
		

		}
		
	/*
	 * Function: initialize()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: �������� �ʿ��� �����(Label, list, table ��)�� �����ϴ� �Լ�
	 */
		public void initialize(String ticketName, String price, String matchDate, String city, String homeTeam, String awayTeam, String seat){
			JPanel panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setBounds(113, 54, 539, 416);
			panelSellerTicketDetail.add(panel);
			panel.setLayout(null);
			
			JPanel panelLabel = new JPanel();
			panelLabel.setBounds(0, 0, 130, 416);
			panelLabel.setBackground(Color.WHITE);
			panel.add(panelLabel);
			panelLabel.setLayout(new GridLayout(9, 1, 0, 0));
			
			JLabel lblNewLabel = new JLabel("Ƽ�� �̸�");
			lblNewLabel.setFont(new Font("���� ���", Font.BOLD, 18));
			panelLabel.add(lblNewLabel);
			
			JLabel lblPrice = new JLabel("�ǸŰ���");
			lblPrice.setFont(new Font("���� ���", Font.BOLD, 18));
			panelLabel.add(lblPrice);
			
			JLabel lblMatchDate = new JLabel("�߱� ��� ��¥");
			lblMatchDate.setFont(new Font("���� ���", Font.BOLD, 18));
			panelLabel.add(lblMatchDate);
			
			JLabel lblCity = new JLabel("����");
			lblCity.setFont(new Font("���� ���", Font.BOLD, 18));
			panelLabel.add(lblCity);
			
			JLabel lblHomeTeam = new JLabel("Ȩ��");
			lblHomeTeam.setFont(new Font("���� ���", Font.BOLD, 18));
			panelLabel.add(lblHomeTeam);
			
			JLabel lblAwayTeam = new JLabel("�������");
			lblAwayTeam.setFont(new Font("���� ���", Font.BOLD, 18));
			panelLabel.add(lblAwayTeam);
			
			JLabel lblSeat = new JLabel("�¼���ġ");
			lblSeat.setFont(new Font("���� ���", Font.BOLD, 18));
			panelLabel.add(lblSeat);
			
			JLabel lblAuction = new JLabel("���� ��� ����");
			lblAuction.setFont(new Font("���� ���", Font.BOLD, 18));
			panelLabel.add(lblAuction);
			
			JLabel lblModifyPrice = new JLabel("���� �Ǹ� ����");
			lblModifyPrice.setFont(new Font("���� ���", Font.BOLD, 18));
			panelLabel.add(lblModifyPrice);
			
			JPanel panelOutput = new JPanel();
			panelOutput.setBounds(157, 0, 382, 416);
			panelOutput.setBackground(Color.WHITE);
			panel.add(panelOutput);
			panelOutput.setLayout(new GridLayout(9, 1, 0, 0));
			


			JLabel lblticketNameValue = new JLabel(ticketName);
			lblticketNameValue.setFont(new Font("���� ���", Font.PLAIN, 18));
			panelOutput.add(lblticketNameValue);
			
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
			
			
			
			rdbtnAuction = new JRadioButton("���� ���");
			rdbtnAuction.setBackground(Color.WHITE);
			rdbtnAuction.setSelected(true);
			rdbtnAuction.setFont(new Font("���� ���", Font.PLAIN, 18));
			
			/*	2019.05.31 �Ǹ��� Ƽ�� ���ǵ�� ���� üũ�ϴ� ��� �߰� (����)(���ȫ)
				if ����üũ�� �����Ǿ� ���� ���					rdbtnAuction.setSelected(false); seller.ticketAuctionCheck = false 
				if ����üũ�Ǿ� ���� ���					rdbtnAuction.setSelected(true); seller.ticketAuctionCheck = true
				���� ������ �߰� ��Ź�帳�ϴ�.
			 */
			// Ƽ�� ���ǿ��� üũ---------------------------
			try {
			Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
			Statement stmt = conn.createStatement();
			String sql = "select * from auction where ticketId =" + Integer.parseInt(ticketName) + ";"; 
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				rdbtnAuction.setSelected(true);
				defalutAuctionCheck = true;
			}
			else {
				rdbtnAuction.setSelected(false);
				defalutAuctionCheck = false;
			}
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// -------------------------------------------
			
			panelOutput.add(rdbtnAuction);
			
			/* 2019.05.31 �Ǹ��� Ƽ�� ���� ������ �����ϰ� ��� �Ǹ� ���� ���� ���Զ��� ���������� �߰�(���ȫ)
			
			 */
			textModifyPrice = new JTextField();
			textModifyPrice.setFont(new Font("���� ���", Font.PLAIN, 18));
			panelOutput.add(textModifyPrice);
			textModifyPrice.setColumns(10);
			
			
		}
		
		/*
		 * Function: buttonAction()
		 * Created: 2019.05.24 - 2019.06.03
		 * Author: 
		 * Description: �������� �ʿ��� ��ư�� ����, ���, �� ��ɿ� �ʿ��� ����� ����ִ� �Լ�
		 */
	public void buttonAction(String ticketName, String price){

		JButton btnModify = new JButton("����");
		btnModify.setBounds(383, 498, 130, 43);
		btnModify.setFont(new Font("���� ���", Font.PLAIN, 20));

		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//2019.05.31 �Ʒ� �߰� �Լ� ����. �ش� �Լ��� Seller�� ����� Ƽ���� ������ �����ϴ� ���. ���� ������ �߰� �ٶ�(���ȫ)
				ticketModifyAction(ticketName, price);
				JOptionPane.showMessageDialog(null,  "����� Ƽ���� �����߽��ϴ�!");
				dispose();

			}
		});
		
		panelSellerTicketDetail.add(btnModify);
		
		
		JButton btnCancel = new JButton("��� ���");
		btnCancel.setBounds(527, 498, 130, 43);
		btnCancel.setFont(new Font("���� ���", Font.PLAIN, 20));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteTicket(ticketName);
				JOptionPane.showMessageDialog(null,  "����� Ƽ���� ����߽��ϴ�!");
				dispose();				
			}
		});
		
		panelSellerTicketDetail.add(btnCancel);
		
	}
	
	
	/*
 	* Function: ticketModifyAction
 	* Created: 2019.05.24 - 2019.06.03
 	* Author: 
 	* Description: �Ǹ��ڰ� ����� Ƽ���� �Ǹűݾ� �� ���ǿ��θ� �����Ϸ��� ����� ����ִ� �Լ�
 	*/
	
	/*2019/06/01 ����
	 *Ƽ�� ��ȸ�� ���� ���� �ȵ��ִ� Ƽ�� ���� �����ϸ� ���� ����ϵ��� ����, ���� ��� ����ϴ� ����� �� ����
	 *Ƽ�ϰ��� �ٲ� �� �ֵ��� ��� �߰�
	*/
	public void ticketModifyAction(String ticketName, String price){
		
		Connection conn = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		
		if(rdbtnAuction.isSelected() == true && defalutAuctionCheck == false) {
			try {
				conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
				Statement stmt = conn.createStatement();
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);	
				cal.add(Calendar.DATE, 1);
				String timer = format.format(cal.getTime());
				String sql = "insert into auction(ticketId, winningBidderId, winningBid, timer) "
						+ "values(" + Integer.parseInt(ticketName) +", 0, 0,'"+ timer + "');";
				stmt.executeUpdate(sql);
				
				if(!textModifyPrice.getText().equals("")){
					sql = "update ticket set ticketPrice ="+ Integer.parseInt(textModifyPrice.getText())
							+ " where id=" + Integer.parseInt(ticketName) +";";
					stmt.executeUpdate(sql);
					
				}
				stmt.close();
				conn.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else {
			try {
				conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
				Statement stmt = conn.createStatement();
				
				if(!textModifyPrice.getText().equals("")){
					String sql = "update ticket set ticketPrice ="+ Integer.parseInt(textModifyPrice.getText())
							+ " where id=" + Integer.parseInt(ticketName) +";";
					stmt.executeUpdate(sql);
					
				}
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*
 	* Function: deleteTicket()
 	* Created: 2019.05.24 - 2019.06.03
 	* Author: 
 	* Description: ����� Ƽ���� �Ǹ����� Database���� �����Ϸ��� �Լ�
 	*/
	
	public void deleteTicket(String ticketName) {
		try {
			Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
			Statement stmt = conn.createStatement();
			String sql = "delete from ticket where id =" + Integer.parseInt(ticketName) + ";"; 
			stmt.executeUpdate(sql);
			
			conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
