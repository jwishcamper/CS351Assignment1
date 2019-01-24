import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI {
	private Converter c = new Converter();
	
	private JFrame frm = new JFrame("Converter App");
	private JLabel decimalLbl = new JLabel("Decimal  ");
	private JLabel binaryLbl = new JLabel("Binary  ");
	private JLabel octalLbl = new JLabel("Octal  ");
	private JLabel hexLbl = new JLabel("Hexidecimal  ");
	private JLabel charLbl = new JLabel("Characters  ");
	private JLabel colorLbl = new JLabel("Color  ");
	private JLabel floatLbl = new JLabel("Float Decimal  ");
	private JTextField decimalTxt = new JTextField();
	private JTextField binaryTxt = new JTextField();
	private JTextField octalTxt = new JTextField();
	private JTextField hexTxt = new JTextField();
	private JTextField charTxt = new JTextField();
	private JPanel colorTxt = new JPanel();
	private JTextField floatTxt = new JTextField();
	private JButton convertBtn = new JButton("Convert");
	private JButton clearBtn = new JButton("Clear");
	private JButton colorBtn = new JButton("Choose Color");
	private JPanel pnlMain = new JPanel();
	private JPanel pnlCenter = new JPanel();
	private JPanel pnlLeft = new JPanel();
	private JPanel pnlRight = new JPanel();
	private JPanel blank[] = new JPanel[8];
	private JColorChooser clr = new JColorChooser();
	
	public GUI() {
		
		for(int i=0;i<8;i++) {
			blank[i]=new JPanel();
			blank[i].setBackground(Color.lightGray);
		}
		pnlMain.setLayout(new BorderLayout());
		pnlMain.setBackground(Color.lightGray);
		pnlCenter.setBackground(Color.lightGray);
		pnlLeft.setBackground(Color.lightGray);
		pnlRight.setBackground(Color.lightGray);
		colorTxt.setBackground(Color.WHITE);
		colorTxt.setBorder(BorderFactory.createLineBorder(Color.gray));
		pnlMain.setBorder(BorderFactory.createMatteBorder(15,15,15,15, Color.lightGray));
		pnlLeft.setLayout(new GridLayout(8,1,75,75));
		pnlRight.setLayout(new GridLayout(8,2,75,75));
		pnlCenter.setLayout(new GridLayout(8,1,75,75));
		pnlCenter.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		pnlMain.add(pnlRight, BorderLayout.EAST);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.add(pnlLeft, BorderLayout.WEST);
		pnlMain.add(blank[0], BorderLayout.NORTH);
		pnlMain.add(blank[1], BorderLayout.SOUTH);
		
		pnlCenter.add(decimalTxt);
		pnlCenter.add(binaryTxt);
		pnlCenter.add(octalTxt);
		pnlCenter.add(hexTxt);
		pnlCenter.add(charTxt);
		pnlCenter.add(colorTxt);
		pnlCenter.add(floatTxt);
		pnlCenter.add(convertBtn);
		
		pnlLeft.add(decimalLbl);
		pnlLeft.add(binaryLbl);
		pnlLeft.add(octalLbl);
		pnlLeft.add(hexLbl);
		pnlLeft.add(charLbl);
		pnlLeft.add(colorLbl);
		pnlLeft.add(floatLbl);
		
		pnlRight.add(blank[2]);
		pnlRight.add(blank[3]);
		pnlRight.add(blank[4]);
		pnlRight.add(blank[5]);
		pnlRight.add(blank[6]);
		pnlRight.add(colorBtn);
		pnlRight.add(blank[7]);
		pnlRight.add(clearBtn);
		
		colorBtn.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) { //logic for color chooser
						Color updateColor = JColorChooser.showDialog(clr, "Choose a Color", Color.white);
						String b = c.hexToBinary(c.colorToHex(updateColor));
						System.out.println(b);
						decimalTxt.setText(Long.toString(c.binaryToDec(b)));
						binaryTxt.setText(b);
						octalTxt.setText(Long.toString(c.binaryToOct(b)));
						hexTxt.setText(c.binaryToHex(b));
						charTxt.setText(c.binaryToASCII(b));
						colorTxt.setBackground(updateColor);
						floatTxt.setText(c.IEEEToFloat(b));
					}
				});
		
		clearBtn.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) { //logic for clear button
						decimalTxt.setText("");
						binaryTxt.setText("");
						octalTxt.setText("");
						hexTxt.setText("");
						charTxt.setText("");
						colorTxt.setBackground(Color.white);
						floatTxt.setText("");
					}
				});
		
		convertBtn.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) { //convert button checks for input from top to bottom - whatever field
																//has text first will be the one chosen to convert to the other fields
						try {
							if(!decimalTxt.getText().equals("")) {  //convert from decimal to binary, then from binary to all the rest
								if(decimalTxt.getText().charAt(0)=='-') {
									String b = c.decToBinary(Long.parseLong(decimalTxt.getText().substring(1)));
									binaryTxt.setText("-"+b);
									octalTxt.setText("-"+Long.toString(c.binaryToOct(b)));
									hexTxt.setText("-"+c.binaryToHex(b));
									charTxt.setText(c.binaryToASCII(b));
									colorTxt.setBackground(c.hexToColor(c.binaryToHex(b)));
									floatTxt.setText("-"+c.IEEEToFloat(b));
								}
								else {
									String b = c.decToBinary(Long.parseLong(decimalTxt.getText()));
									binaryTxt.setText(b);
									octalTxt.setText(Long.toString(c.binaryToOct(b)));
									hexTxt.setText(c.binaryToHex(b));
									charTxt.setText(c.binaryToASCII(b));
									colorTxt.setBackground(c.hexToColor(c.binaryToHex(b)));
									floatTxt.setText(c.IEEEToFloat(b));
								}
							}
							else if(!binaryTxt.getText().equals("")) {
								if(binaryTxt.getText().charAt(0)=='-') {
									String b = binaryTxt.getText().substring(1);
									decimalTxt.setText("-"+Long.toString(c.binaryToDec(b)));
									octalTxt.setText("-"+Long.toString(c.binaryToOct(b)));
									hexTxt.setText("-"+c.binaryToHex(b));
									charTxt.setText(c.binaryToASCII(b));
									colorTxt.setBackground(c.hexToColor(c.binaryToHex(b)));
									floatTxt.setText("-"+c.IEEEToFloat(b));
								}
								else {
									decimalTxt.setText(Long.toString(c.binaryToDec(binaryTxt.getText())));
									octalTxt.setText(Long.toString(c.binaryToOct(binaryTxt.getText())));
									hexTxt.setText(c.binaryToHex(binaryTxt.getText()));
									charTxt.setText(c.binaryToASCII(binaryTxt.getText()));
									colorTxt.setBackground(c.hexToColor(c.binaryToHex(binaryTxt.getText())));
									floatTxt.setText(c.IEEEToFloat(binaryTxt.getText()));
								}
							}
							else if(!octalTxt.getText().equals("")) {
								if(octalTxt.getText().charAt(0)=='-') {
									String b = c.octToBinary(Long.parseLong(octalTxt.getText().substring(1)));
									decimalTxt.setText("-"+Long.toString(c.binaryToDec(b)));
									binaryTxt.setText("-"+b);
									hexTxt.setText("-"+c.binaryToHex(b));
									charTxt.setText(c.binaryToASCII(b));
									colorTxt.setBackground(c.hexToColor(c.binaryToHex(b)));
									floatTxt.setText("-"+c.IEEEToFloat(b));
								}
								else {
									String b = c.octToBinary(Long.parseLong(octalTxt.getText()));
									decimalTxt.setText(Long.toString(c.binaryToDec(b)));
									binaryTxt.setText(b);
									hexTxt.setText(c.binaryToHex(b));
									charTxt.setText(c.binaryToASCII(b));
									colorTxt.setBackground(c.hexToColor(c.binaryToHex(b)));
									floatTxt.setText(c.IEEEToFloat(b));
								}
							}
							else if(!hexTxt.getText().equals("")) {
								if(hexTxt.getText().charAt(0)=='-') {
									String b = c.hexToBinary(hexTxt.getText().substring(1));
									decimalTxt.setText("-"+Long.toString(c.binaryToDec(b)));
									binaryTxt.setText("-"+b);
									octalTxt.setText("-"+Long.toString(c.binaryToOct(b)));
									charTxt.setText(c.binaryToASCII(b));
									colorTxt.setBackground(c.hexToColor(c.binaryToHex(b)));
									floatTxt.setText("-"+c.IEEEToFloat(b));
								}
								else {
									String b = c.hexToBinary(hexTxt.getText());
									decimalTxt.setText(Long.toString(c.binaryToDec(b)));
									binaryTxt.setText(b);
									octalTxt.setText(Long.toString(c.binaryToOct(b)));
									charTxt.setText(c.binaryToASCII(b));
									colorTxt.setBackground(c.hexToColor(c.binaryToHex(b)));
									floatTxt.setText(c.IEEEToFloat(b));
								}
							}
							else if(!charTxt.getText().equals("")) {
								String b = c.ASCIIToBinary(charTxt.getText());
								decimalTxt.setText(Long.toString(c.binaryToDec(b)));
								binaryTxt.setText(b);
								octalTxt.setText(Long.toString(c.binaryToOct(b)));
								hexTxt.setText(c.binaryToHex(b));
								colorTxt.setBackground(c.hexToColor(c.binaryToHex(b)));
								floatTxt.setText(c.IEEEToFloat(b));
							}
							else if(!floatTxt.getText().equals("")) {
								if(floatTxt.getText().charAt(0)=='-') {
									String b = c.floatToBinary(floatTxt.getText().substring(1));
									decimalTxt.setText("-"+Long.toString(c.binaryToDec(b)));
									binaryTxt.setText("-"+b);
									octalTxt.setText("-"+Long.toString(c.binaryToOct(b)));
									hexTxt.setText("-"+c.binaryToHex(b));
									charTxt.setText(c.binaryToASCII(b));
									colorTxt.setBackground(c.hexToColor(c.binaryToHex(b)));
								}
								else {
									String b = c.floatToBinary(floatTxt.getText());
									decimalTxt.setText(Long.toString(c.binaryToDec(b)));
									binaryTxt.setText(b);
									octalTxt.setText(Long.toString(c.binaryToOct(b)));
									hexTxt.setText(c.binaryToHex(b));
									charTxt.setText(c.binaryToASCII(b));
									colorTxt.setBackground(c.hexToColor(c.binaryToHex(b)));
								}
							}
						} catch(NumberFormatException ex) {
							JOptionPane.showMessageDialog(null, "Invalid input entered, please try again", "Invalid Input", 0);
						} 
					}
				});



		frm.add(pnlMain);
		frm.setPreferredSize(new Dimension(1000, 1000));
		frm.setVisible(true);
		frm.pack();
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
