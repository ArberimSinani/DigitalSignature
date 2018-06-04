import java.awt.EventQueue;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrmMain extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
					    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					        if ("Nimbus".equals(info.getName())) {
					            UIManager.setLookAndFeel(info.getClassName());
					            break;
					        }
					    }
					} catch (Exception e) {
					    // If Nimbus is not available, you can set the GUI to another look and feel.
					}
					FrmMain frame = new FrmMain();
					frame.setUndecorated(true);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setResizable(false);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public FrmMain() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 621, 438);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLexo = new JButton("Read the signed text");
		btnLexo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tekst;

				try (
				    InputStream fis = new FileInputStream("MyData/SignedData.txt");
				    InputStreamReader isr = new InputStreamReader(fis);
				    BufferedReader br = new BufferedReader(isr);
				) {
				    while ((tekst = br.readLine()) != null) {
				    	
				    	//JScrollPane jsc=new JScrollPane();
				    	JLabel lab=new JLabel();
				    	lab.setText(tekst);
				    	JOptionPane.showMessageDialog(null, lab);
				    		 
				    		   
				    }
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnLexo.setFont(new Font("Consolas", Font.PLAIN, 16));
		btnLexo.setBounds(174, 228, 268, 41);
		contentPane.add(btnLexo);
		
		JButton btnVerifiko = new JButton("Verify ");
		btnVerifiko.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
					new VerifyMessage("MyData/SignedData.txt", "MyKeys/publicKey");
				} 
				catch (Exception e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		btnVerifiko.setFont(new Font("Consolas", Font.PLAIN, 16));
		btnVerifiko.setBounds(174, 281, 268, 41);
		contentPane.add(btnVerifiko);
		
		JButton btnTekstiPerNenshkrim = new JButton("Sign");
		btnTekstiPerNenshkrim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String teksti = JOptionPane.showInputDialog("Shkruani mesazhin tuaj !");
				try
				{
					new SignMessage(teksti, "MyKeys/privateKey").writeToFile("MyData/SignedData.txt");
				}
				catch (Exception e1)
				{
					
				}
				
			}
		});
		btnTekstiPerNenshkrim.setFont(new Font("Consolas", Font.PLAIN, 16));
		btnTekstiPerNenshkrim.setBounds(174, 175, 268, 41);
		contentPane.add(btnTekstiPerNenshkrim);
		
		
		JLabel label_1 = new JLabel("");
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
		});
		
		JButton btnGenerateKeys = new JButton("Generate Keys");
		btnGenerateKeys.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GenerateKeys myKeys = null;
				try {
					try {
						myKeys = new GenerateKeys(1024);
					} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					myKeys.createKeys();
					try {
						myKeys.writeToFile("MyKeys/publicKey", myKeys.getPublicKey().getEncoded());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						myKeys.writeToFile("MyKeys/privateKey", myKeys.getPrivateKey().getEncoded());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Keys succesfuly generated!");
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, "Failed to generate the keys!", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnGenerateKeys.setFont(new Font("Consolas", Font.PLAIN, 16));
		btnGenerateKeys.setBounds(174, 122, 268, 41);
		contentPane.add(btnGenerateKeys);
		
		label_1.setIcon(new ImageIcon(FrmMain.class.getResource("/img/close_essential_set_x_icon_white.png")));
		label_1.setBounds(583, 6, 30, 35);
		contentPane.add(label_1);
		
		JLabel lblDigitalSignature = new JLabel("Digital Signature");
		lblDigitalSignature.setHorizontalAlignment(SwingConstants.CENTER);
		lblDigitalSignature.setForeground(Color.WHITE);
		lblDigitalSignature.setFont(new Font("Consolas", Font.PLAIN, 32));
		lblDigitalSignature.setBounds(141, 51, 330, 49);
		contentPane.add(lblDigitalSignature);
		
		JLabel label = new JLabel("");
		label.setBounds(0, 0, 626, 442);
		label.setIcon(new ImageIcon(FrmMain.class.getResource("/img/digital-data-encryption-background_1406-108.jpg")));
		contentPane.add(label);
	}
}
