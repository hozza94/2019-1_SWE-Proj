
/*
 * Created: 2019.05.24 - 2019.06.03
 * Author:
 * Description: �Ǹ��ڰ� ���Ӱ� Ƽ���� ����ϰ� ���� �� Ƽ�Ͽ� �ʿ��� �����Է¶��� ��Ÿ���� User Interface
 */

package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import classes.Seller;
import classes.Ticket;
import utility.Info;

import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.Font;

public class SellerTicketRegister extends JFrame {
	private JFrame frame;
	private JPanel panelSellerTIcketRegister;
	private JTextField inputTicketName;	
	private JTextField inputPrice;
	private JTextField inputMatchDate;
	private JTextField inputCity;
	private JTextField inputHomeTeam;
	private JTextField inputAwayTeam;
	private JTextField inputSeat;
	private JCheckBox chckbxAuction;
	private Seller seller;
	
	public SellerTicketRegister() {
		this.frame = frame;
		//sellerŬ���� �̿��ϱ� ���� 	2019/05/31 ����
		seller = new Seller();
		seller.setSellerId(Info.seller_id);
		
		setTitle("�Ǹ��� Ƽ�� ���");
		setBounds(100, 100, 800, 600);
		panelSellerTIcketRegister = new JPanel();
		panelSellerTIcketRegister.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelSellerTIcketRegister);
		getContentPane().setBackground(Color.WHITE);
		panelSellerTIcketRegister.setLayout(null);
		initialize();
		buttonAction();
	}
		
	/*
	 * Function: initialize()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: �������� �ʿ��� �����(Label, list, table ��)�� �����ϴ� �Լ�
	 */
	public void initialize(){
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(113, 54, 539, 416);
		panelSellerTIcketRegister.add(panel);
		panel.setLayout(null);
		
		JPanel panelLabel = new JPanel();
		panelLabel.setBackground(Color.WHITE);
		panelLabel.setBounds(0, 0, 130, 416);
		panel.add(panelLabel);
		panelLabel.setLayout(new GridLayout(7, 1, 0, 0));
		
		/*	---- Ƽ��ID�� �ڵ����� �ǹǷ� �ʿ� �����ϴ� 2019/5/30����
		JLabel lblNewLabel = new JLabel("Ƽ�� �̸�");
		panelLabel.add(lblNewLabel);
		*/
		
		/* 2019/05/31 �ڵ� ��ϵǴ� Ƽ���� ID�� �������� ǥ���ϰ� �������� �� ����� �߰� ��Ź�帳�ϴ�(���ȫ)
		 	�Ʒ� �Լ��� JLabel lblTicketNameValue�� �־��µ���
			JLabel lblTicketNameValue = new JLabel("");
			"" ���̿� �ڵ���ϵǴ� Ƽ�� ID�� �־��ּ���.
			
		*/
		
		
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
		
		
		JPanel panelOutput = new JPanel();
		panelOutput.setBackground(Color.WHITE);
		panelOutput.setBounds(153, 0, 386, 416);
		panel.add(panelOutput);
		panelOutput.setLayout(new GridLayout(7, 1, 0, 0));
		

		
		inputPrice = new JTextField();
		inputPrice.setFont(new Font("���� ���", Font.PLAIN, 18));
		inputPrice.setColumns(10);
		panelOutput.add(inputPrice);
		
		inputMatchDate = new JTextField();
		inputMatchDate.setFont(new Font("���� ���", Font.PLAIN, 18));
		inputMatchDate.setColumns(10);
		panelOutput.add(inputMatchDate);
		
		inputCity = new JTextField();
		inputCity.setFont(new Font("���� ���", Font.PLAIN, 18));
		inputCity.setColumns(10);
		panelOutput.add(inputCity);
		
		inputHomeTeam = new JTextField();
		inputHomeTeam.setFont(new Font("���� ���", Font.PLAIN, 18));
		inputHomeTeam.setColumns(10);
		panelOutput.add(inputHomeTeam);
		
		inputAwayTeam = new JTextField();
		inputAwayTeam.setFont(new Font("���� ���", Font.PLAIN, 18));
		inputAwayTeam.setColumns(10);
		panelOutput.add(inputAwayTeam);
		
		inputSeat = new JTextField();
		inputSeat.setFont(new Font("���� ���", Font.PLAIN, 18));
		inputSeat.setColumns(10);
		panelOutput.add(inputSeat);
		
		chckbxAuction = new JCheckBox("���� ���� ����");
		chckbxAuction.setFont(new Font("���� ���", Font.PLAIN, 18));
		chckbxAuction.setBackground(Color.WHITE);
		panelOutput.add(chckbxAuction);
	}
	
	/*
	 * Function: buttonAction()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: �������� �ʿ��� ��ư�� ����, ���, �� ��ɿ� �ʿ��� ����� ����ִ� �Լ�
	 */
	public void buttonAction(){

		JButton btnTicketRegister = new JButton("Ƽ�� ���");
		btnTicketRegister.setFont(new Font("���� ���", Font.PLAIN, 20));
		btnTicketRegister.setBounds(515, 498, 137, 43);

		btnTicketRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// ����ð� ���ϱ�
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String currentTime = format.format(date);
				
				// �Ǹ��� Ƽ�� ��� �κ� --------------------------------------------------2019/5/31����
				Ticket ticket = new Ticket(1, seller.getSellerId(), inputMatchDate.getText(),
						currentTime, inputCity.getText() ,inputHomeTeam.getText(), inputAwayTeam.getText(),
						Integer.parseInt(inputSeat.getText()), Integer.parseInt(inputPrice.getText()));
				seller.setSellerTicket(ticket);
				seller.getSellerTicket().registerSellerTicket();
				//------------------------------------------------------------------------
				JOptionPane.showMessageDialog(null,  "Ƽ���� �Ǹ� ����߽��ϴ�!");
				
				//���� ��� üũ���� ��		2019/05/31 ����
				//���� üũ�� Ÿ�̸Ӵ� ����� �ð� + 1�Ϸ� ����, winningBidderId�� WinningBid�� �ʱⰪ 0���� ����
				if(chckbxAuction.isSelected()) {
					try {
						Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
						Statement stmt = conn.createStatement();
					
						ResultSet rs = stmt.executeQuery("select MAX(id) from ticket;");
						rs.next();
						seller.sellerId = rs.getInt(1);
						rs.close();
	
						
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);	
						cal.add(Calendar.DATE, 1);
						String timer = format.format(cal.getTime());
						String sql = "insert into auction(ticketId, winningBidderId, winningBid, timer) "
								+ "values(" + seller.sellerId +", 0, 0,'"+ timer + "');";
						stmt.executeUpdate(sql);
						stmt.close();
						conn.close();
					} catch (SQLException ee) {
						// TODO Auto-generated catch block
						ee.printStackTrace();
					}
					
				}
				
				dispose();	
				
			}
		});
		
		panelSellerTIcketRegister.add(btnTicketRegister);
		
		
		
	}
}
