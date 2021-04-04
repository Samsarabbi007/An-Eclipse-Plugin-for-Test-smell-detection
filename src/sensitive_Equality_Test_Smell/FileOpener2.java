//package sensitive_Equality_Test_Smell;
//
//import java.awt.BorderLayout;
//import java.awt.EventQueue;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;
//import javax.swing.filechooser.FileNameExtensionFilter;
//import javax.swing.JButton;
//import javax.swing.JFileChooser;
//
//import java.awt.event.ActionListener;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.awt.event.ActionEvent;
//import javax.swing.JLabel;
//import java.awt.Color;
//import java.awt.Desktop;
//
//import javax.swing.JTextField;
//import javax.swing.SwingConstants;
//import java.awt.Font;
//import javax.swing.JTextPane;
//import javax.swing.JScrollBar;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
//
//
//public class FileOpener2 extends JFrame {
//	
//
//	private JPanel contentPane;
//	
//	private final JFileChooser openFileChooser;
//	private final JFileChooser openFolderChooser;
//	private final JFileChooser chooseFolder;
//	private String javaFileName ;
//	private String errorLog ;
//	private JTextArea txtErrorLog;
//	private JScrollPane scroll;
//	private int returnValue = -1;;
//	private boolean isProject = true;
//	private boolean fileClickTest = false;
//
//
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					FileOpener2 frame = new FileOpener2();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//	public FileOpener2() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 856, 630);
//		contentPane = new JPanel();
//		//contentPane.setBackground(new Color(30, 144, 255));
//		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
//		contentPane.setLayout(null);
//		
//		openFileChooser = new JFileChooser();
//		openFileChooser.setFileFilter(new FileNameExtensionFilter("JAVA Class", "java"));
//		
//		openFolderChooser = new JFileChooser();
//		openFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//		
//		chooseFolder = new JFileChooser();
//		chooseFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//		
//		
//		JLabel messageBox = new JLabel("");
//		messageBox.setBackground(new Color(255, 255, 255));
//		messageBox.setForeground(Color.BLACK);
//		messageBox.setFont(new Font("SansSerif", Font.ITALIC, 18));
//		messageBox.setBounds(395, 13, 435, 41);
//		contentPane.add(messageBox);
//		
//		JButton btnSelectProject = new JButton("Select Project....");
//		btnSelectProject.setForeground(new Color(255, 255, 255));
//		btnSelectProject.setFont(new Font("SansSerif", Font.PLAIN, 12));
//		btnSelectProject.setBackground(new Color(0, 0, 0));
//		btnSelectProject.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				txtErrorLog.setText("");
//				messageBox.setForeground(Color.MAGENTA);
//				messageBox.setText("Choose a Project... ");
//				returnValue = openFolderChooser.showOpenDialog(FileOpener2.this);
//				
//				if(returnValue == JFileChooser.APPROVE_OPTION) {
//					javaFileName = openFolderChooser.getSelectedFile().toString();
//					System.out.println("Folder Name = " + javaFileName);
//					isProject = true;
//					fileClickTest = true;
//					
//					messageBox.setForeground(Color.RED);
//					messageBox.setText("Successfull ");
//				}
//				else {
//					messageBox.setForeground(Color.RED);
//					messageBox.setText("No folder Chosen!!! ");
//				}
//			}
//		});
//		btnSelectProject.setBounds(28, 13, 165, 41);
//		contentPane.add(btnSelectProject);
//		
//		
//		//JButton SelectFileButton = new JButton("Select File.....");
//		//SelectFileButton.setForeground(new Color(102, 51, 0));
//		//SelectFileButton.setFont(new Font("Source Code Pro Black", Font.PLAIN, 12));
//		//SelectFileButton.setBackground(new Color(102, 255, 0));
////		SelectFileButton.addActionListener(new ActionListener() {
////			public void actionPerformed(ActionEvent arg0) {
////				txtErrorLog.setText("");
////				messageBox.setForeground(Color.red);
////				messageBox.setText("Choose a File... ");
////				returnValue = openFileChooser.showOpenDialog(FileOpener2.this);
////				
////				if(returnValue == JFileChooser.APPROVE_OPTION) {
////					javaFileName = openFileChooser.getSelectedFile().toString();
////					System.out.println("File Name = " + javaFileName);
////					isProject = false;
////					fileClickTest = true;
////					messageBox.setForeground(Color.GREEN);
////					messageBox.setText("JAVA file Successfull");
////				}
////				else {
////					messageBox.setForeground(Color.RED);
////					messageBox.setText("No File Chosen!!! ");
////				}
////				
////			}
////		});
////		SelectFileButton.setBounds(203, 13, 170, 41);
////		contentPane.add(SelectFileButton);
//		
//		
//		
//		txtErrorLog = new JTextArea();
//		
//		//contentPane.add(txtErrorLog);
//		//txtErrorLog.add(scroll);
//		scroll = new JScrollPane(txtErrorLog , JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
//				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		txtErrorLog.setFont(new Font("SansSerif", Font.BOLD, 16));
//		scroll.setBounds(12, 69, 818, 406);
//		scroll.setVisible(true);
//		contentPane.add(scroll);
//		//scroll.setViewportView(txtErrorLog);
//		
//		JButton btnShowResult = new JButton("Show Result");
//		btnShowResult.setForeground(new Color(255, 255, 255));
//		btnShowResult.setFont(new Font("SansSerif", Font.PLAIN, 12));
//		btnShowResult.setBackground(new Color(128, 128, 128));
//		btnShowResult.setBounds(313, 522, 204, 37);
//		contentPane.add(btnShowResult);
//		
//		JButton btnCheckForErrors = new JButton("Check For Smells");
//		btnCheckForErrors.setForeground(new Color(128, 128, 128));
//		btnCheckForErrors.setFont(new Font("SansSerif", Font.PLAIN, 12));
//		btnCheckForErrors.setBackground(new Color(255, 255, 255));
//		btnCheckForErrors.setBounds(38, 522, 204, 37);
//		contentPane.add(btnCheckForErrors);
//		
////		JButton btnSaveResultAs = new JButton("Save Result As Text File");
////		btnSaveResultAs.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 12));
////		btnSaveResultAs.setBackground(new Color(255, 215, 0));
////		btnSaveResultAs.addActionListener(new ActionListener() {
////			public void actionPerformed(ActionEvent arg0) {
////				
////				if(fileClickTest == true) {
////					messageBox.setForeground(Color.MAGENTA);
////					messageBox.setText("Select / Create  a Folder ... ");
////					returnValue = chooseFolder.showOpenDialog(FileOpener2.this);
////					
////					if(returnValue == JFileChooser.APPROVE_OPTION) {
////						javaFileName = chooseFolder.getSelectedFile().toString();
////						System.out.println("File Name = " + javaFileName);
////						messageBox.setForeground(Color.GREEN);
////						messageBox.setText("Error Log successfully saved in " + javaFileName + " .");
////						FileWriter fw;
////						try {
////							String fileName = javaFileName + "\\ErrorLog.txt";
////							fw = new FileWriter(fileName);
////							fw.write(errorLog);
////							fw.close();
////							File file = new File(fileName);
////							if (System.getProperty("os.name").toLowerCase().contains("windows")) {
////								  String cmd = "rundll32 url.dll,FileProtocolHandler " + file.getCanonicalPath();
////								  Runtime.getRuntime().exec(cmd);
////								} 
////							else {
////								  Desktop.getDesktop().edit(file);
////							}
////						} catch (IOException e) {
////							// TODO Auto-generated catch block
////							e.printStackTrace();
////						}    
////					}
////					else {
////						messageBox.setForeground(Color.RED);
////						messageBox.setText("No Folder Chosen!!! ");
////					}
////				
////				}
////				
////				
////				else {
////					messageBox.setForeground(Color.MAGENTA);
////					messageBox.setText("Choose a file First !!! ");			
////				}
////			}
////				
////		});
////		btnSaveResultAs.setBounds(580, 522, 204, 37);
////		contentPane.add(btnSaveResultAs);
////		
//		btnCheckForErrors.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				
//				if(returnValue != -1) {
//					
//					SensititiveEqualitySmell se = new SensititiveEqualitySmell();
//					try {
//						if(isProject == true) {
//							
//							File folder = new File(javaFileName);
//							se.chooseFolder(folder);
//							errorLog = se.getErrorLog();
//							se.setErrorLog("");
//							int errorCount = se.getErrorCount();
//							se.setErrorCount(0);
//							txtErrorLog.setText("");
//							
//							if(errorCount == 0) {
//								messageBox.setForeground(Color.RED);
//							//messageBox.setText("There are " + errorCount + " test smells in project");
//								messageBox.setText("There are no test smells in project");
//							}
//							else {
//								messageBox.setForeground(Color.RED);
//								messageBox.setText("There are " + errorCount + " test smells in project");
//								//messageBox.setText("There are Some test smells in project");
//							}
//						}
//						else {
//							se.chooseFile(javaFileName);
//							errorLog = se.getErrorLog();
//							se.setErrorLog("");
//							int errorCount = se.getErrorCount();
//							//messageBox.setForeground(Color.RED);
//							if(errorCount == 0) {
//								messageBox.setForeground(Color.GREEN);
//								//messageBox.setText("There are " + errorCount + " test smells in project");
//									messageBox.setText("There are no test smells in file");
//								}
//								else {
//									messageBox.setForeground(Color.RED);
//									messageBox.setText("There are " + errorCount + " test smells in project");
//									//messageBox.setText("There are Some test smells in file");
//								}
//						}
//						
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				else {
//					messageBox.setForeground(Color.MAGENTA);
//					messageBox.setText("Choose a file First !!! ");			
//				}
//				
//			}
//		});
//		
//		btnShowResult.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				
//				if(returnValue != -1) {
//					System.out.println(errorLog);
//					txtErrorLog.setText(errorLog);
//				}
//				else {
//					messageBox.setForeground(Color.MAGENTA);
//					messageBox.setText("Choose a file First !!! ");			
//				}
//			}
//		});
//		
//	}
//}
