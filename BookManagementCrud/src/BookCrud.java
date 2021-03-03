import java.awt.EventQueue;
import java.sql.*;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BookCrud {

	private JFrame frame;
	private JTextField txtBookName;
	private JTextField txtEdition;
	private JTextField txtPrice;
	private JTable table;
	private JTextField txtBookID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookCrud window = new BookCrud();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BookCrud() {
		initialize();
		Connect();
		table_load();
	}
	// these two components are coming from SQL namespace
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	
	//connecting to xampp server- mysql
	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//Connecting with our local mysql database --> bookmanagementcrud is the name of the db created on xampp mysql
			con=DriverManager.getConnection("jdbc:mysql://localhost/bookmanagementcrud","root",""); //"root , "" -> local user name and password .. these values are default
			
		}
		catch(ClassNotFoundException ex) {
			
		}
		catch(SQLException ex) {
			
		}
		
	}
	
	public void table_load() {
		try {
			
			pst =con.prepareStatement("select * from book");
			rs=pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1142, 623);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));
		lblNewLabel.setBounds(491, 21, 246, 58);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(44, 90, 489, 302);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_1.setBounds(56, 67, 163, 31);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_1_1.setBounds(56, 141, 163, 31);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Price");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_1_2.setBounds(56, 222, 163, 31);
		panel.add(lblNewLabel_1_2);
		
		txtBookName = new JTextField();
		txtBookName.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtBookName.setBounds(234, 67, 200, 31);
		panel.add(txtBookName);
		txtBookName.setColumns(10);
		
		txtEdition = new JTextField();
		txtEdition.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtEdition.setColumns(10);
		txtEdition.setBounds(234, 141, 200, 31);
		panel.add(txtEdition);
		
		txtPrice = new JTextField();
		txtPrice.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtPrice.setColumns(10);
		txtPrice.setBounds(234, 233, 200, 31);
		panel.add(txtPrice);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String bname, bedition, bprice;
				bname=txtBookName.getText();
				bedition=txtEdition.getText();
				bprice=txtPrice.getText();
				
				try {
					
					pst =con.prepareStatement("insert into book(name, edition,price)values(?,?,?)");
					pst.setString(1, bname);
					pst.setString(2, bedition);
					pst.setString(3, bprice);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Added!");
					table_load();
					txtBookName.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtBookName.requestFocus();
					
				}
				
				catch(SQLException ex) {
					ex.printStackTrace();
				}
				
				
				
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnNewButton.setBounds(56, 403, 97, 51);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnExit.setBounds(237, 403, 97, 51);
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtBookName.setText("");
				txtEdition.setText("");
				txtPrice.setText("");
				txtBookName.requestFocus();
				
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnClear.setBounds(406, 403, 111, 51);
		frame.getContentPane().add(btnClear);
		
		JScrollPane tableBook = new JScrollPane();
		tableBook.setBounds(543, 101, 477, 353);
		frame.getContentPane().add(tableBook);
		
		table = new JTable();
		tableBook.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(49, 483, 484, 92);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Book ID");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_1_1_1.setBounds(43, 36, 163, 31);
		panel_1.add(lblNewLabel_1_1_1);
		
		txtBookID = new JTextField();
		txtBookID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					
					String id=  txtBookID.getText();
					pst =con.prepareStatement("select name,edition,price from book where id=?");
					pst.setString(1, id);
					rs=pst.executeQuery();
					
					if(rs.next() == true) {
						String name=rs.getString(1);
						String edition=rs.getString(2);
						String price=rs.getString(3);
						
						txtBookName.setText(name);
						txtEdition.setText(edition);
						txtPrice.setText(price);
					}
					else {
						txtBookName.setText("");
						txtEdition.setText("");
						txtPrice.setText("");
						
					}
					
				}
				catch(Exception ex){
					ex.printStackTrace();
					
				}
			}
		});
		txtBookID.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtBookID.setColumns(10);
		txtBookID.setBounds(235, 36, 200, 31);
		panel_1.add(txtBookID);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String bname, bedition, bprice, bid;
				bname=txtBookName.getText();
				bedition=txtEdition.getText();
				bprice=txtPrice.getText();
				bid=txtBookID.getText();

				
				try {
					
					pst =con.prepareStatement("update book set name =?, edition=?, price=? where id =?");
					pst.setString(1, bname);
					pst.setString(2, bedition);
					pst.setString(3, bprice);
					pst.setString(4, bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Updated!");
					table_load();
					txtBookName.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtBookName.requestFocus();
					
				}
				
				catch(SQLException ex) {
					ex.printStackTrace();
				}
				
				
				
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnUpdate.setBounds(610, 501, 144, 51);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnNewButton_1_1 = new JButton("Delete");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String bid;
			
				bid=txtBookID.getText();

				
				try {
					
					pst =con.prepareStatement("delete from book where id =?");
				
					pst.setString(1, bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Deleted!");
					table_load();
					txtBookName.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtBookName.requestFocus();
					
				}
				
				catch(SQLException ex) {
					ex.printStackTrace();
				}
				
				
			}
		});
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnNewButton_1_1.setBounds(829, 501, 144, 51);
		frame.getContentPane().add(btnNewButton_1_1);
	}
}
