/*
 * Created: 2019.05.24 - 2019.06.03
 * Author:
 * Description: �����ڰ� ������ Ƽ�ϵ��� �� ��Ȳ�� ���ݱ��� �Һ��� �� �ݾ��� ��Ÿ���� User Interface
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import utility.Info;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JButton;

public class BuyerTicketStatistics extends JFrame {

	private JPanel panelBuyerStat;
	public JTable ticketTable;
	private JPanel panelDate = new JPanel();
	private String tableHeader[] = {"Ƽ��ID", "���Ű���", "������ ��¥", "�߱� ��� ��¥", "����", "Ȩ��", "�������", "�¼���ġ"};
	DefaultTableModel ticketModel;
	JTextField fromYear;
	JTextField fromMonth;
	JTextField fromDay;
	JTextField toYear;
	JTextField toMonth;
	JTextField toDay;
	JLabel totalPayment;
	
	
	public BuyerTicketStatistics() {
		setTitle("������ Ƽ�� ���");
		setBounds(100, 100, 1000, 600);
		panelBuyerStat = new JPanel();
		panelBuyerStat.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelBuyerStat);
		getContentPane().setBackground(Color.WHITE);
		panelBuyerStat.setLayout(null);
		
		// �����غ��ڽ��ϴ�. 19.05.29
		// TicketList
		String[][] tableListContents = {};
        ticketModel = new DefaultTableModel(tableListContents, tableHeader); 
		ticketTable = new JTable(ticketModel);
				
		createTable(tableListContents, ticketTable);
		
		JScrollPane scrollpane = new JScrollPane(ticketTable);
		scrollpane.setBounds(50, 118, 894, 319);
		panelBuyerStat.add(scrollpane);
		
		
		initialize();
		ticketToDateList();
		ticketFromDateList();
		buttonAction();
	}
	
	/*
	 * Function: initialize()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: �������� �ʿ��� �����(Label, list, table ��)�� �����ϴ� �Լ�
	 */
	public void initialize(){

		
		panelDate.setBackground(Color.WHITE);
		panelDate.setBounds(166, 30, 707, 53);
		panelBuyerStat.add(panelDate);
		panelDate.setLayout(null);
		
		totalPayment = new JLabel("0");
		totalPayment.setFont(new Font("���� ���", Font.PLAIN, 21));
		totalPayment.setBounds(552, 449, 200, 80);
		
		panelBuyerStat.add(totalPayment);
		
		JLabel lblTotalPrice = new JLabel("�ѱ��űݾ�");
		lblTotalPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalPrice.setFont(new Font("���� ���", Font.BOLD, 22));
		lblTotalPrice.setBounds(372, 449, 131, 80);
		panelBuyerStat.add(lblTotalPrice);
		
		
		JLabel lblNewLabel = new JLabel("�ð�(��/��/��)");
		lblNewLabel.setBounds(0, 12, 128, 27);
		panelDate.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("���� ���", Font.PLAIN, 18));
		
		
		
		JLabel label_4 = new JLabel("~");
		label_4.setBounds(351, 5, 33, 38);
		panelDate.add(label_4);
		label_4.setFont(new Font("����", Font.PLAIN, 25));
		
	}

	/*
 	* Function: createTable 
 	* Created: 2019.05.24 - 2019.06.03
 	* Author: 
 	* Description: ȸ��(������ �� �Ǹ���)�� ��� �� ���࿡ �ʿ��� Ƽ���� ������ִ� ����Ʈ(ǥ)�� �����ϴ� �Լ�
 	*/
	public void createTable(String[][] listContents, JTable table){
		table.setEnabled(false);	//���̺� �� ���� ���� ����
		table.getColumn("������ ��¥").setPreferredWidth(200);	//�ʱ� �� ���� ũ�� ����
		table.getColumn("�߱� ��� ��¥").setPreferredWidth(200);	//�ʱ� �� ���� ũ�� ����
		table.setFont(new Font("���� ���", Font.PLAIN, 16));
		table.setRowHeight(23);
		
		// DefaultTableCellHeaderRenderer ���� (��� ������ ����)

		DefaultTableCellRenderer tableCenterSort = new DefaultTableCellRenderer();
		DefaultTableCellRenderer tableRightSort = new DefaultTableCellRenderer();


		// DefaultTableCellHeaderRenderer�� ������ ��� ���ķ� ����

		tableCenterSort.setHorizontalAlignment(SwingConstants.CENTER);
		tableRightSort.setHorizontalAlignment(SwingConstants.RIGHT);
		// ������ ���̺��� ColumnModel�� ������

		TableColumnModel tcmSchedule = table.getColumnModel();


		// �ݺ����� �̿��Ͽ� ���̺��� ��� ���ķ� ����
		for(int i=0; i<tcmSchedule.getColumnCount(); i++){
			tcmSchedule.getColumn(i).setCellRenderer(tableCenterSort);
		}
		tcmSchedule.getColumn(1).setCellRenderer(tableRightSort);
	}
	

	/*
 	* Function: ticketToDateList 
 	* Created: 2019.05.24 - 2019.06.03
 	* Author: 
 	* Description: Ƽ�ϸ���Ʈ ��¿� ������~, �� ������ �� �ʿ��� ���Զ��� ���� �� �۵��ϴ� �Լ�
 	*/
	public void ticketToDateList(){
		

	toYear = new JTextField();
	toYear.setHorizontalAlignment(SwingConstants.RIGHT);
	toYear.setBounds(143, 11, 70, 30);
	panelDate.add(toYear);
	toYear.setFont(new Font("���� ���", Font.PLAIN, 17));
	toYear.setColumns(10);
	
	JLabel label_3 = new JLabel("/");
	label_3.setBounds(220, 9, 24, 30);
	panelDate.add(label_3);
	label_3.setFont(new Font("����", Font.PLAIN, 25));
	
	toMonth = new JTextField();
	toMonth.setHorizontalAlignment(SwingConstants.RIGHT);
	toMonth.setBounds(235, 11, 45, 30);
	panelDate.add(toMonth);
	toMonth.setFont(new Font("���� ���", Font.PLAIN, 17));
	toMonth.setColumns(10);
	
	JLabel label_2 = new JLabel("/");
	label_2.setBounds(285, 9, 24, 30);
	panelDate.add(label_2);
	label_2.setFont(new Font("����", Font.PLAIN, 25));
	
	toDay = new JTextField();
	toDay.setHorizontalAlignment(SwingConstants.RIGHT);
	toDay.setBounds(300, 11, 45, 30);
	panelDate.add(toDay);
	toDay.setFont(new Font("���� ���", Font.PLAIN, 17));
	toDay.setColumns(10);
		
		
		
		
	}
	
/*
	* Function: ticketFromDateList 
	* Created: 2019.05.24 - 2019.06.03
	* Author: 
	* Description: Ƽ�ϸ���Ʈ ��¿� ~������, �� ������ �� �ʿ��� ���Զ��� ���� �� �۵��ϴ� �Լ�
	*/
	public void ticketFromDateList(){
		
		fromYear = new JTextField();
		fromYear.setHorizontalAlignment(SwingConstants.RIGHT);
		fromYear.setBounds(387, 11, 70, 30);
		panelDate.add(fromYear);
		fromYear.setFont(new Font("���� ���", Font.PLAIN, 17));
		fromYear.setColumns(10);
		
		JLabel label = new JLabel("/");
		label.setBounds(465, 9, 24, 30);
		panelDate.add(label);
		label.setFont(new Font("����", Font.PLAIN, 25));
		
		fromMonth = new JTextField();
		fromMonth.setHorizontalAlignment(SwingConstants.RIGHT);
		fromMonth.setBounds(480, 11, 45, 30);
		panelDate.add(fromMonth);
		fromMonth.setFont(new Font("���� ���", Font.PLAIN, 17));
		fromMonth.setColumns(10);
		
		JLabel label_1 = new JLabel("/");
		label_1.setBounds(531, 9, 24, 30);
		panelDate.add(label_1);
		label_1.setFont(new Font("����", Font.PLAIN, 25));
		
		fromDay = new JTextField();
		fromDay.setHorizontalAlignment(SwingConstants.RIGHT);
		fromDay.setBounds(547, 11, 45, 30);
		panelDate.add(fromDay);
		fromDay.setFont(new Font("���� ���", Font.PLAIN, 17));
		fromDay.setColumns(10);

	}
	
	/*
	 * Function: buttonAction()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: �������� �ʿ��� ��ư�� ����, ���, �� ��ɿ� �ʿ��� ����� ����ִ� �Լ�
	 */
	public void buttonAction(){
		
		JButton btnTicketDetail = new JButton("��ȸ");
		btnTicketDetail.setBounds(613, 5, 80, 41);
		panelDate.add(btnTicketDetail);
		btnTicketDetail.setFont(new Font("���� ���", Font.BOLD, 19));
		btnTicketDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = ticketModel.getRowCount() - 1; i > -1; i--) {
					ticketModel.removeRow(i);
			    }
				
				try {
					Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
					Statement stmt = conn.createStatement();
					String date1 = fromYear.getText() + "-" + fromMonth.getText() + "-" + fromDay.getText() + " 00:00:00";
					String date2 = toYear.getText() + "-" + toMonth.getText() + "-" + toDay.getText() + " 00:00:00";
					String sql = "select T.*, R.reservationDate from reservation R, ticket T where R.ticketId=T.id and buyerId="+Info.buyer_id+" and reservationDate<'"+date1+"' and reservationDate>'"+date2+"';";
					ResultSet rs = stmt.executeQuery(sql);
					
					int total_cost = 0;
					while(rs.next()){
				        String ticketId = rs.getString(1);
				        String sellerId = rs.getString(2);
				        String ticketdate = rs.getString(3);
				        String city = rs.getString(5);
				        String home = rs.getString(6);
				        String away = rs.getString(7);
				        String seat = rs.getString(8);
				        String price = rs.getString(9);
				        String reservationDate = rs.getString(10);
				        
				        total_cost += Integer.parseInt(price);
				        
				        String input[] = {ticketId, price, reservationDate, ticketdate, city, home, away, seat};
				        ticketModel.addRow(input);
				    }
					totalPayment.setText(Integer.toString(total_cost));
					
					conn.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});

		
	}
}
