

/*
 * Created: 2019.05.24 - 2019.06.03
 * Author:
 * Description: �Ǹ��ڰ� ���ݱ��� ����� Ƽ���� ����Ʈ ��°� ���� ����ϰ� ���� Ƽ�� ����� �����ִ� User Interface
 */

package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import classes.Seller;
import utility.Info;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ScrollPaneConstants;

public class SellerMainPage extends JFrame {

	private JPanel panelSeller;
	
	private JTable ticketTable;
	private Seller seller;
	
	private String tableHeader[] = {"Ƽ�ϸ�", "���Ű���", "�߱� ��� ��¥", "����", "Ȩ��", "�������", "�¼���ġ"};

	
	JTextField toYear;
	JTextField toMonth;
	JTextField toDay;
	JTextField fromYear;
	JTextField fromMonth;
	JTextField fromDay;
	DefaultTableModel ticketTablemodel;


	public SellerMainPage() {
	
		
		//sellerŬ���� �̿��ϱ� ���� 	2019/05/31 ����
		seller = new Seller();
		seller.setSellerId(Info.seller_id);
		seller.setId(Info.member_id);
		seller.setEmail(Info.userEmail);
		
		setTitle("�Ǹ��� ������");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		panelSeller = new JPanel();
		panelSeller.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelSeller);
		getContentPane().setBackground(Color.WHITE);
		panelSeller.setLayout(null);
		

		//�Ǹ��� ������ �ʱ�ȭ (Ƽ������ �ҷ�����)
		initialize();
		
		//------------
		ticketToDateList();
		ticketFromDateList();
		buttonAction();
	}
	
	/*
 	* Function: createTable 
 	* Created: 2019.05.24 - 2019.06.03
 	* Author: 
 	* Description: ȸ��(������ �� �Ǹ���)�� ��� �� ���࿡ �ʿ��� Ƽ���� ������ִ� ����Ʈ(ǥ)�� �����ϴ� �Լ�
 	*/

	public void createTable(String[][] listContents, JTable table){
		
		
		table.setEnabled(false);	//���̺� �� ���� ���� ����
		table.getColumn(table.getColumnName(2)).setPreferredWidth(300);	//�ʱ� �� ���� ũ�� ����
		table.setFont(new Font("���� ���", Font.PLAIN, 16));
		table.setRowHeight(25);
		
		table.setBackground(Color.WHITE);
		
		
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
 	* Function: tableAction() 
 	* Created: 2019.05.24 - 2019.06.03
 	* Author: 
 	* Description: ȸ��(������ �� �Ǹ���)�� Ƽ�� ����Ʈ�� �����ҽ� �۵��ϴ� ����� ����ִ� �Լ�
 	*/
	public void tableAction(JTable table, int tableType){

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = table.rowAtPoint(arg0.getPoint());
				int col = table.columnAtPoint(arg0.getPoint());
				if(row>=0 && col==0){
					switch(tableType){
					case 0:
						SellerTicketDetailsPage sellerTicketDetailsPage = new SellerTicketDetailsPage(
								table.getValueAt(row,col).toString(),
								table.getValueAt(row,col+1).toString(),
								table.getValueAt(row,col+2).toString(),
								table.getValueAt(row,col+3).toString(),
								table.getValueAt(row,col+4).toString(),
								table.getValueAt(row,col+5).toString(),
								table.getValueAt(row,col+6).toString()
								);
						sellerTicketDetailsPage.setVisible(true);
						break;
					

					}
					
				}
			}
		});
	}
	
	
	/*
 	* Function: ticketToDateList 
 	* Created: 2019.05.24 - 2019.06.03
 	* Author: 
 	* Description: Ƽ�ϸ���Ʈ ��¿� ������~, �� ������ �� �ʿ��� ���Զ��� ���� �� �۵��ϴ� �Լ�
 	*/

	public void ticketToDateList(){

		
		JLabel lblNewLabel = new JLabel("�ð�(��/��/��)");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("���� ���", Font.PLAIN, 20));
		lblNewLabel.setBounds(247, 129, 158, 38);
		panelSeller.add(lblNewLabel);
		

		
		
		
		
		JLabel label_1 = new JLabel("/");
		label_1.setFont(new Font("����", Font.PLAIN, 25));
		label_1.setBounds(889, 129, 24, 38);
		panelSeller.add(label_1);
		
		toDay = new JTextField();
		toDay.setHorizontalAlignment(SwingConstants.CENTER);
		toDay.setFont(new Font("���� ���", Font.PLAIN, 20));
		toDay.setColumns(10);
		toDay.setBounds(648, 129, 67, 38);
		panelSeller.add(toDay);
		
		JLabel label_2 = new JLabel("/");
		label_2.setFont(new Font("����", Font.PLAIN, 25));
		label_2.setBounds(623, 129, 24, 38);
		panelSeller.add(label_2);
		
		toMonth = new JTextField();
		toMonth.setHorizontalAlignment(SwingConstants.CENTER);
		toMonth.setFont(new Font("���� ���", Font.PLAIN, 20));
		toMonth.setColumns(10);
		toMonth.setBounds(542, 129, 67, 38);
		panelSeller.add(toMonth);
		
		JLabel label_3 = new JLabel("/");
		label_3.setFont(new Font("����", Font.PLAIN, 25));
		label_3.setBounds(518, 129, 24, 38);
		panelSeller.add(label_3);
		
		toYear = new JTextField();
		toYear.setHorizontalAlignment(SwingConstants.CENTER);
		toYear.setFont(new Font("���� ���", Font.PLAIN, 20));
		toYear.setColumns(10);
		toYear.setBounds(419, 129, 86, 38);
		panelSeller.add(toYear);
		
	}
	
	/*
 	* Function: ticketFromDateList 
 	* Created: 2019.05.24 - 2019.06.03
 	* Author: 
 	* Description: Ƽ�ϸ���Ʈ ��¿� ~������, �� ������ �� �ʿ��� ���Զ��� ���� �� �۵��ϴ� �Լ�
 	*/
	public void ticketFromDateList(){
		

		fromYear = new JTextField();
		fromYear.setHorizontalAlignment(SwingConstants.CENTER);
		fromYear.setFont(new Font("���� ���", Font.PLAIN, 20));
		fromYear.setColumns(10);
		fromYear.setBounds(789, 129, 86, 38);
		panelSeller.add(fromYear);
		
		fromDay = new JTextField();
		fromDay.setHorizontalAlignment(SwingConstants.CENTER);
		fromDay.setFont(new Font("���� ���", Font.PLAIN, 20));
		fromDay.setColumns(10);
		fromDay.setBounds(1022, 129, 67, 38);
		panelSeller.add(fromDay);
		
		fromMonth = new JTextField();
		fromMonth.setHorizontalAlignment(SwingConstants.CENTER);
		fromMonth.setFont(new Font("���� ���", Font.PLAIN, 20));
		fromMonth.setColumns(10);
		fromMonth.setBounds(913, 129, 67, 38);
		panelSeller.add(fromMonth);
		
		JLabel label_3 = new JLabel("/");
		label_3.setFont(new Font("����", Font.PLAIN, 25));
		label_3.setBounds(994, 129, 24, 38);
		panelSeller.add(label_3);
		
		JLabel label = new JLabel("~");
		label.setFont(new Font("����", Font.PLAIN, 25));
		label.setBounds(742, 129, 33, 38);
		panelSeller.add(label);
		
	}
	

	/*
	 * Function: buttonAction()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: �������� �ʿ��� ��ư�� ����, ���, �� ��ɿ� �ʿ��� ����� ����ִ� �Լ�
	 */
	public void buttonAction(){
		
		JPanel panelBtn = new JPanel();
		panelBtn.setBounds(873, 12, 375, 55);
		panelSeller.add(panelBtn);
		panelBtn.setBackground(Color.WHITE);
		panelBtn.setLayout(new GridLayout(1, 3, 5, 0));
		
		JButton btnStat = new JButton("���");
		btnStat.setFont(new Font("���� ���", Font.PLAIN, 20));
		btnStat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SellerTicketStatistics sellerTicketStatistics = new SellerTicketStatistics();
				sellerTicketStatistics.setVisible(true);
				
			}
		});
		panelBtn.add(btnStat);
		
		JButton btnLogout = new JButton("�α׾ƿ�");
		btnLogout.setFont(new Font("���� ���", Font.PLAIN, 20));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,  "�α׾ƿ��߽��ϴ�!");
				LoginGui loginGui = new LoginGui();
				loginGui.setVisible(true);
				dispose();
			}
		});
		panelBtn.add(btnLogout);
		
		
		
		JButton btnUserDropOut = new JButton("ȸ��Ż��");
		btnUserDropOut.setFont(new Font("���� ���", Font.PLAIN, 20));
		btnUserDropOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/*�Ǹ��� ����� Ƽ���� ������ Ȯ�� �� ȸ��DB�� ȸ������ ��� �߰� ����*/
				//�Ϸ� 2019/06/01
				try {
					Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
					Statement stmt = conn.createStatement();
					String sql = "select * from ticket "
							+ "where sellerId=" + seller.getSellerId()
							+ ";";
					ResultSet rs = stmt.executeQuery(sql);
					
					if(rs.next()){
						JOptionPane.showMessageDialog(null,  "����� Ƽ���� �ֽ��ϴ�. Ż���� �� �����ϴ�");
						rs.close();
						stmt.close();
						conn.close();
				    }
					else {
						sql = "delete from seller where id =" + seller.getSellerId() + ";";
						stmt.executeUpdate(sql);
						sql = "delete from member where id =" + seller.getId() + ";";
						stmt.executeUpdate(sql);
						JOptionPane.showMessageDialog(null,  "Ż�� �Ϸ�");
						rs.close();
						stmt.close();
						conn.close();
						
						LoginGui loginGui = new LoginGui();
						loginGui.setVisible(true);
						dispose();
					}
					
					
				} catch (SQLException ee) {
					// TODO Auto-generated catch block
					ee.printStackTrace();
				}
				
			}
		});
		panelBtn.add(btnUserDropOut);
		
		
		//2019.05.31 ���ΰ�ħ ��ư�� �߰������� �մϴ�. Ƽ�� ����̳� ���� �� ���̺� �ٷ� �ݿ��Ϸ��� ��ư�� ���� ���� ������ �������ų� �ؾ��� �� �մϴ�		
		//2019.06.01 ���ΰ�ħ ��ư �߰�
		JButton btnRefresh = new JButton("���ΰ�ħ");
		btnRefresh.setFont(new Font("���� ���", Font.PLAIN, 20));
		btnRefresh.setBounds(34, 12, 121, 55);
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				SellerMainPage sellerMainPage = new SellerMainPage();
				sellerMainPage.setVisible(true);
			}
		});
		panelSeller.add(btnRefresh);
		
		
		JButton btnTicketDetail = new JButton("��ȸ");
		btnTicketDetail.setFont(new Font("���� ���", Font.PLAIN, 20));
		btnTicketDetail.setBounds(1135, 129, 86, 38);
		btnTicketDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*����� Ƽ�� ��¥���� ���� ��� �Լ� �߰� ����*/
				for (int i = ticketTablemodel.getRowCount() - 1; i > -1; i--) {
					ticketTablemodel.removeRow(i);
			    }
				
				
				try {
					Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
					Statement stmt = conn.createStatement();
					String date1 = fromYear.getText() + "-" + fromMonth.getText() + "-" + fromDay.getText() + " 00:00:00";
					String date2 = toYear.getText() + "-" + toMonth.getText() + "-" + toDay.getText() + " 00:00:00";
					String sql = "select * from ticket where ticketDate<'"+date1+"' and ticketDate>'"+date2+"' and ticketPrice!=0;";
					ResultSet rs = stmt.executeQuery(sql);
					
					while(rs.next()){
				        String ticketId = rs.getString(1);
				        
				        String ticketdate = rs.getString(3);
				        String city = rs.getString(5);
				        String home = rs.getString(6);
				        String away = rs.getString(7);
				        String seat = rs.getString(8);
				        String price = rs.getString(9);
				        
				        String input[] = {ticketId, price, ticketdate, city, home, away, seat};
				        ticketTablemodel.addRow(input);
				    }
					
					
					conn.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		panelSeller.add(btnTicketDetail);
		
		JButton btnTicketInput = new JButton("Ƽ�� ���");
		btnTicketInput.setFont(new Font("���� ��� Semilight", Font.PLAIN, 20));
		btnTicketInput.setBounds(1075, 597, 167, 48);
		btnTicketInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SellerTicketRegister sellerTicketRegister = new SellerTicketRegister();
				sellerTicketRegister.setVisible(true);
			}
		});
		panelSeller.add(btnTicketInput);
		
		
		
		
		
	}
	
	
	// -----------�Ǹ��� ���� â�� �α����� �Ǹ����� Ƽ�� ����Ʈ�� �ҷ��� 
	//			  2019/05/31 ���� ����� 

	/*
	 * Function: initialize()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: �������� �ʿ��� �����(Label, list, table ��)�� �����ϴ� �Լ�
	 */
	public void initialize() {
		
		/*
		 * 2019/05/30 ���� �߰�
		 �Ʒ��� ���̺� ������̸��� ������ �߰����ּ���.
		 �������� ���� ���������� ���� ��� ���ȫ�� ����������, �� ���λ���Ʈ�� ������ ����� �ͽ��ϴ�. 
		 
		 �߰� �Ϸ�	2019/05/31
		 */
		
		JLabel lblNewLabel_1 = new JLabel(seller.getEmail());
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("���� ���", Font.BOLD, 23));
		lblNewLabel_1.setBounds(477, 23, 145, 55);
		panelSeller.add(lblNewLabel_1);
		
		JLabel lblMain = new JLabel("���� ������");
		lblMain.setHorizontalAlignment(SwingConstants.CENTER);
		lblMain.setFont(new Font("���� ���", Font.BOLD, 23));
		lblMain.setBounds(630, 23, 145, 61);
		panelSeller.add(lblMain);
		
		
		String ticketTableContents[][] = null;
				
		ticketTablemodel = new DefaultTableModel(ticketTableContents, tableHeader);
		
		
		try {
			Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
			Statement stmt = conn.createStatement();
			String sql = "select * from ticket "
					+ "where sellerId=" + seller.getSellerId()
					+ " order by ticketPrice DESC;";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				
		        String id = rs.getString(1);
		        String sellerId = rs.getString(2);
		        String ticketdate = rs.getString(3);
		        String city = rs.getString(5);
		        String home = rs.getString(6);
		        String away = rs.getString(7);
		        String seat = rs.getString(8);
		        String price = rs.getString(9);
		        
		        
		        
		        String input[] = {id, price, ticketdate, city, home, away, seat};
		        ticketTablemodel.addRow(input);
		    }
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ticketTable = new JTable(ticketTablemodel);
		createTable(ticketTableContents, ticketTable);
		tableAction(ticketTable,0);
		JScrollPane scrollpane = new JScrollPane(ticketTable);
		scrollpane.setBounds(34, 186, 1214, 380);
		panelSeller.add(scrollpane);
		
		
	}
}
