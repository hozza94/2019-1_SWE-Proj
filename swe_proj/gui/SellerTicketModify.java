package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;

public class SellerTicketModify extends JFrame {

	private JPanel panelSellerModify;
	

	
	public SellerTicketModify(String ticketName, String price) {
		
		setTitle("판매자 티켓 수정");
		setBounds(100, 100, 800, 600);
		panelSellerModify = new JPanel();
		panelSellerModify.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelSellerModify);
		panelSellerModify.setLayout(null);
		
		
		initialize(ticketName, price);
		buttonAction(ticketName, price);
		

		}
		
		public void initialize(String ticketName, String price){
			JPanel panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setBounds(113, 54, 539, 416);
			panelSellerModify.add(panel);
			panel.setLayout(null);
			
			JPanel panelLabel = new JPanel();
			panelLabel.setBackground(Color.WHITE);
			panelLabel.setBounds(0, 0, 163, 416);
			panel.add(panelLabel);
			panelLabel.setLayout(new GridLayout(3, 1, 0, 0));
			
			JLabel lblName = new JLabel("티켓 이름");
			lblName.setHorizontalAlignment(SwingConstants.CENTER);
			lblName.setFont(new Font("맑은 고딕", Font.BOLD, 19));
			panelLabel.add(lblName);
			
			JLabel lblCurrentPrice = new JLabel("현재 판매 가격");
			lblCurrentPrice.setHorizontalAlignment(SwingConstants.CENTER);
			lblCurrentPrice.setFont(new Font("맑은 고딕", Font.BOLD, 19));
			panelLabel.add(lblCurrentPrice);
			JLabel lblModifyPrice = new JLabel("수정 판매 가격");
			lblModifyPrice.setHorizontalAlignment(SwingConstants.CENTER);
			lblModifyPrice.setFont(new Font("맑은 고딕", Font.BOLD, 19));
			panelLabel.add(lblModifyPrice);
			
			
			JPanel panelOutput = new JPanel();
			panelOutput.setBackground(Color.WHITE);
			panelOutput.setBounds(177, 0, 362, 416);
			panel.add(panelOutput);
			panelOutput.setLayout(new GridLayout(3, 1, 0, 0));
			
			JLabel lblNameValue = new JLabel(ticketName);
			lblNameValue.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
			panelOutput.add(lblNameValue);
			
			JLabel lblCurrentPriceValue = new JLabel(price);
			lblCurrentPriceValue.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
			panelOutput.add(lblCurrentPriceValue);
			
			JTextField inputModifyPrice = new JTextField();
			inputModifyPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
			panelOutput.add(inputModifyPrice);
			inputModifyPrice.setColumns(10);
			
			
			
			
		}
	public void buttonAction(String ticketName, String price){

		JButton btnModify = new JButton("수정");
		btnModify.setBounds(365, 498, 137, 43);

		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*등록한 티켓 수정 작업 함수 추가 예정*/
				JOptionPane.showMessageDialog(null,  "수정했습니다!");
				dispose();				
			
				
			}
		});
		
		panelSellerModify.add(btnModify);
		
		
		
	}
}
