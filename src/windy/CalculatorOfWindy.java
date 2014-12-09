package windy;


import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Chương trình calculator
 * @author Windy
 *
 */
public class CalculatorOfWindy extends JFrame implements ActionListener {
	
	/**
	 * Mảng chứa 22 nút chức năng
	 */
	JButton[] button = new JButton[22];
	/**Nút đặc biệt thay đổi kích thước*/
	JButton buttonSpecial;
	/**tối giản và tắt chương trình*/
	JButton min,close;
	/**Mảng chứa các kí tự của nút*/
	String[] buttonString = {"M+", "M", "←", "C",
							 "√", "e", "+", "-",
							 "7", "8", "9", "×",
							 "4", "5", "6", "÷",
	 	   					 "1", "2", "3",
		 					 "0", ".", "="};
	/**2 màn hình text hiện phép tình và kết quả*/
    JTextArea display1 = new JTextArea(1,1);
    JTextArea display2 = new JTextArea(1,1);
    /** thanh cuộn display1 */
    JScrollPane scroll;
    /**Thanh menu chuột phải.*/
    JPopupMenu popup= new JPopupMenu();
    JPopupMenu popupText= new JPopupMenu();
    /**Thanh menubar*/
    JMenuBar menu= new JMenuBar();
    /**Các menu trong menuBar*/
    JMenu menuFile,menuOption,menuView,menuHelp,menuTransparent;
    /**check alwayTop, menuAutoCopy*/
    JCheckBoxMenuItem menuAlwayTop,menuAutoCopy;
    /**menu exit*/
    JMenuItem menuExit;
    /**thanh kéo trong suốt*/
    JSlider Transparent;
    /**List chon background*/
    JList list;
    /**button mode text*/
    JRadioButtonMenuItem rightright,rightleft,leftright,leftleft;
    /**font chữ*/
    Font font = new Font("Times new Roman", Font.ITALIC, 22);
    /**biến đếm.*/
    int i=0;
    /**Kiểm tra có lỗi không.*/
    boolean checkError=false;
    /**Kiểm tra xem đã ấn dấu bằng chưa.*/
    boolean checkResual=false;
    /**Kiểm tra xem có được ấn dấu . tiếp không*/
    boolean checkDots=false;
    /**Kiểm tra xem có ở chế độ đặc biệt không.*/
    boolean checkMode=false;
    /**Biến M lưu kết quả phép tính.*/
    double M;
    /**Biến lưu kết quả.*/
    double temporary1=0;
    /**check xem đang ở chế đố rút gọn hay không.*/
    boolean special=false;
    /**Lấy ra container của jframe*/
    Container container;
    /**chứa màu cần thay đổi.*/
    Color colorI;
    /** ảnh nền*/
    Image imgbg = null;
    /** ảnh button*/
    ImageIcon imgbt1 = null;
    /** ảnh icon*/
    Image icon = null;
    /** panel background*/
    JPanel pane;
    /** List chứa kết quả lịch sử */
    List<String> operationL;
    List<String> resultL;
    /** Kiểm tra lần ấn trước là nút gì*/
    int historyNumber=16;
    /** check tu dong copy*/
    boolean checkAutoCopy=false;

	int pX,pY;
	
    
	/**
	 * Khởi tạo chương trình.
	 */
    CalculatorOfWindy(){
    	setDesign();
    	//Kích thước chương trình
    	setSize(300,400);
    	//Có trở lại kích thước ban đầu hay không
    	setResizable(false);
    	//Set vị trí mặc định trên màn hình khi chương trình chạy
    	setLocation(Toolkit.getDefaultToolkit().getScreenSize().width-305,50);
    	//mặc định tắt chương trình
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	//Không dùng layout
    	setLayout(null);
    	//luôn luôn ở trên cùng
    	setAlwaysOnTop(true);
    	//bỏ viền window
    	setUndecorated(true);
    	//set trong suốt
    	setOpacity(0.7f);

    	operationL=new ArrayList<String>();
    	resultL=new ArrayList<String>();
    	
    	try{
        	icon = ImageIO.read(new File("image/calculator.png"));
        	imgbt1 = new ImageIcon("image/bt.png");
    	}catch(Exception e){
    		System.out.println("1");
    	}
    	
    	this.setIconImage(icon);
    	this.setTitle("Calculator Of Windy");
    	
		pane = new JPanel() {
             @Override
             protected void paintComponent(Graphics g) {
            	 super.paintComponent(g);
                 g.drawImage(imgbg, 0, 0, null);
             }
         };
         
         pane.setBounds(0, 0, 300, 400); 
         pane.setFocusable(false);
    	container=this.getContentPane();
    	 

  
    	//tạo các button và set thông số
    	//vị trí, tên
    	for(int i=0;i<22;i++){
    		button[i] = new MyButton(imgbt1);
    		button[i].setText(buttonString[i]);
    		button[i].setFont(font);
    		button[i].addActionListener(this);
            button[i].setFocusable(false);
    	}
    	for(int i=0;i<4;i++){
    		button[i].setSize(64, 32);
    		button[i].setLocation(10+i*72, 120);
    		add(button[i]);
    	}
    	for(int i=4,j=0;i<8;i++){
    		button[i].setSize(64, 32);
    		button[i].setLocation(10+j*72,160 );
    		add(button[i]);
    		j++;
    	}
    	for(int i=8,j=0;i<12;i++){
    		button[i].setSize(64, 32);
    		button[i].setLocation(10+j*72,200 );
    		add(button[i]);
    		j++;
    	}
    	for(int i=12,j=0;i<16;i++){
    		button[i].setSize(64, 32);
    		button[i].setLocation(10+j*72,240 );
    		add(button[i]);
    		j++;
    	}
    	for(int i=16,j=0;i<19;i++){
    		button[i].setSize(64, 32);
    		button[i].setLocation(10+j*72,280 );
    		add(button[i]);
    		j++;
    	}
    		
    	button[19].setSize(136,32);
    	button[19].setLocation(10,320);
    	add(button[19]);
    	button[20].setSize(64,32);
    	button[20].setLocation(154,320);
    	add(button[20]);
    	button[21].setSize(64,72);
    	button[21].setLocation(226,280);
    	add(button[21]);
    		
    	//Set các màn hình;
    	display1.setFont(font);
    	display1.setEditable(false);
    	display1.setFocusable(false);
    	display1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    	display1.setSize(280, 30);
    	display1.setLocation(10, 20);
    	scroll = new JScrollPane(display1);
    	scroll.setSize(280, 30);
    	scroll.setLocation(10, 20);
    	scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    	scroll.setBorder(BorderFactory.createLineBorder(Color.white));
    	scroll.setFocusable(false);
    	display1.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent me)
            {
            	if(me.getComponent()==display1)
            		historyUp();
            }
        });
    	add(scroll);
    	display2.setFont(font);
    	display2.setEditable(false);
    	display2.setFocusable(false);
    	display2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    	display2.setSize(280, 30);
    	display2.setLocation(10, 50);
    	display2.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent me)
            {
            	if(me.getComponent()==display2)
            		historyDown();
            }
        });
    	add(display2);
    	//cho jframe nhận sự kiện bàn phím
    	this.setFocusable(true);
    	this.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent me)
            {
               int a=me.getKeyCode();
               if(a==38){
            	  historyUp();
            	   return;
               }
               else if(a==40){
            	   historyDown();
            	   return;
               }
               if(a>95&&a<106)
            	   setDisplay1(String.valueOf(a-96));
               else if(a==110)
            	   setDisplay1Spec3();
               else if(a==106)
            	   setDisplay1Spec1("×");
               else if(a==111)
            	   setDisplay1Spec1("÷");
               else if(a==107){
            	   setDisplay1("+");
            	   checkDots=false;
               }
               else if(a==109){
            	   setDisplay1("-");
            	   checkDots=false;
               }
               else if(a==8){
               	int d=display1.getText().length();
               	if(d>0)
               	display1.replaceRange("", d-1, d);
               }
               else if(a==10){
            	result();
               	checkResual=true;
               }
               else return;
               historyNumber=16;
            }
        });
    	//set button thay doi kich thuoc
    	buttonSpecial = new JButton();
    	buttonSpecial.addActionListener(this);
    	buttonSpecial.setSize(64, 10);
    	buttonSpecial.setLocation(118, 85);
    	buttonSpecial.setFocusable(false);
    	buttonSpecial.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
            	checkMode=!checkMode;
            	changeMode();
            }
        });
    	add(buttonSpecial);
    	
    	//Popup Menu
    	JMenuItem colorBackground=new JMenuItem("Color Background");
    	colorBackground.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			colorI=JColorChooser.showDialog(CalculatorOfWindy.this,"Choose a color",colorI);
    			if(colorI==null)
    				colorI=Color.lightGray;
    			container.setBackground(colorI);
    			pane.setBackground(colorI);
    			menu.setBackground(colorI);
    			}
    	});
    	JMenuItem colorButton=new JMenuItem("Color Button");
    	colorButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			colorI=JColorChooser.showDialog(CalculatorOfWindy.this,"Choose a color",colorI);
    			if(colorI==null)
    				colorI=Color.lightGray;
    			for(int i=0; i<22;i++)
    				button[i].setBackground(colorI);
    			}
    	});
    	JMenuItem colorDisplay=new JMenuItem("Color Display");
    	colorDisplay.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			colorI=JColorChooser.showDialog(CalculatorOfWindy.this,"Choose a color",colorI);
    			if(colorI==null)
    				colorI=Color.lightGray;
    			display1.setBackground(colorI);
    			scroll.setBorder(BorderFactory.createLineBorder(colorI));
    			display2.setBackground(colorI);
    			}
    	});
    	popup.add(colorBackground);
    	popup.add(colorButton);
    	popup.add(colorDisplay);
    	addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e){
    			checkForTriggerEvent(e);
    		}
    		public void mouseReleased(MouseEvent e){
    			checkForTriggerEvent(e);
    		}
    		public void checkForTriggerEvent(MouseEvent e){
    			if(e.isPopupTrigger()){
    				popup.show(e.getComponent(), e.getX(), e.getY());
    			}
    		}
    	});
    	
    	//pupup Text
    	leftleft=new JRadioButtonMenuItem("Left - Left");
    	leftleft.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    	    	display1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    	    	display2.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);  
    	    	
    		}
    	});
    	rightright=new JRadioButtonMenuItem("Right - Right");
    	rightright.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    	    	display1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    	    	display2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);  	
    	    	
    		}
    	});
    	leftright=new JRadioButtonMenuItem("Left - Right");
    	leftright.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    	    	display1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    	    	display2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);  	
    	    	
    		}
    	});
    	leftright.setSelected(true);
    	rightleft=new JRadioButtonMenuItem("Right - Left");
    	rightleft.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    	    	display1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    	    	display2.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);  			
    	    	
    		}
    	});
    	colorDisplay=new JMenuItem("Color Font");
    	colorDisplay.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			colorI=JColorChooser.showDialog(CalculatorOfWindy.this,"Choose a color",colorI);
    			if(colorI==null)
    				colorI=Color.lightGray;
    			display1.setForeground(colorI);
    			display2.setForeground(colorI);
    			}
    	});
    	ButtonGroup textGroup=new ButtonGroup();
    	textGroup.add(leftleft);
    	textGroup.add(rightright);
    	textGroup.add(leftright);
    	textGroup.add(rightleft);
    	popupText.add(leftleft);
    	popupText.add(rightright);
    	popupText.add(leftright);
    	popupText.add(rightleft);
    	popupText.add(colorDisplay);
    	display1.setComponentPopupMenu(popupText);
    	display2.setComponentPopupMenu(popupText);
    	
    	 //Menu File
    	menuFile=new JMenu("File");
    	menuExit=new JMenuItem("Restart");
    	menuExit.setMnemonic(KeyEvent.VK_R);
    	menuExit.setAccelerator(
    			KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
    	menuExit.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){restart();}
    	});
    	menuFile.add(menuExit);
    	menuExit=new JMenuItem("Exit");
    	menuExit.setMnemonic(KeyEvent.VK_E);
    	menuExit.setAccelerator(
    			KeyStroke.getKeyStroke(KeyEvent.VK_F1,Event.CTRL_MASK));
    	menuExit.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			saveToXML();
    			System.exit(0);
    			}
    	});
    	menuFile.add(menuExit);
    	menuFile.setMnemonic(KeyEvent.VK_F);
    	menu.add(menuFile);
    	
    	//nhận sự kiển thay đổi vị trí chương trình
    	menu.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent me)
            {
                // Get x,y and store them
                pX=me.getX();
                pY=me.getY();
            }
        });
        
        // Add MouseMotionListener for detecting drag
        menu.addMouseMotionListener(new MouseAdapter(){
            public void mouseDragged(MouseEvent me)
            {
                // Set the location
                // get the current location x-co-ordinate and then get
                // the current drag x co-ordinate, add them and subtract most recent
                // mouse pressed x co-ordinate
                // do same for y co-ordinate
                setLocation(getLocation().x+me.getX()-pX,getLocation().y+me.getY()-pY);
            }
        });
    	
    	//Menu option
        //box check alway top
    	menuAlwayTop=new JCheckBoxMenuItem("Always on top");
    	menuAlwayTop.setSelected(true);
    	menuAlwayTop.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			if(menuAlwayTop.isSelected())setAlwaysOnTop(true);
    			else setAlwaysOnTop(false);
    		}
    	});
    	//slider kéo trong suốt
    	Transparent=new JSlider();
    	Transparent.setMinimum(30);
    	Transparent.setValue(70);
    	Transparent.setMinorTickSpacing(5);
    	Transparent.setMajorTickSpacing(10);
    	Transparent.setPaintTicks(true);
    	Transparent.setPaintLabels(true);
    	Transparent.setLabelTable(Transparent.createStandardLabels(10));
    	Transparent.addChangeListener(new ChangeListener(){
    		 public void stateChanged(ChangeEvent changeEvent) {
    			setOpacity((float)Transparent.getValue()/100);
    		} 		
    	});
    	//menu ấn để ra slide kéo
    	menuTransparent=new JMenu("Transparent");
    	menuTransparent.add(Transparent);
    	
    	//Menu tren menu bar
    	colorBackground=new JMenuItem("Color Background");
    	colorBackground.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			colorI=JColorChooser.showDialog(CalculatorOfWindy.this,"Choose a color",colorI);
    			if(colorI==null)
    				colorI=Color.lightGray;
    			container.setBackground(colorI);
    			pane.setBackground(colorI);
    			menu.setBackground(colorI);
    			}
    	});
    	colorButton=new JMenuItem("Color Button");
    	colorButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			colorI=JColorChooser.showDialog(CalculatorOfWindy.this,"Choose a color",colorI);
    			if(colorI==null)
    				colorI=Color.lightGray;
    			for(int i=0; i<22;i++)
    				button[i].setBackground(colorI);
    			}
    	});
    	colorDisplay=new JMenuItem("Color Display");
    	colorDisplay.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			colorI=JColorChooser.showDialog(CalculatorOfWindy.this,"Choose a color",colorI);
    			if(colorI==null)
    				colorI=Color.lightGray;
    			display1.setBackground(colorI);
    			scroll.setBorder(BorderFactory.createLineBorder(colorI));
    			display2.setBackground(colorI);
    			}
    	});
    	JMenu menuColor =new JMenu("Color");
    	menuColor.add(colorBackground);
    	menuColor.add(colorButton);
    	menuColor.add(colorDisplay);
    	
    	String[] listS={"Style 1", "Style 2", "Style 3", "Style 4", 
    			"Style 5", "Style 6", "Style 7", "Style 8", };
    	list = new JList(listS);
    	list.setSelectedIndex(0);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				try{
		        	imgbg = ImageIO.read(new File("image/bg"+(list.getSelectedIndex()+1) +".png"));
		    	}catch(Exception ex){
		    	}
				repaint();
			}
		});
		JMenu menuStyleBG =new JMenu("Background Style");
		menuStyleBG.add(list);
		
    	//Menubar option
    	menuOption=new JMenu("Option");
    	menuOption.setMnemonic(KeyEvent.VK_O);
    	menuOption.add(menuAlwayTop);
    	menuOption.add(menuTransparent);
    	menuOption.add(menuColor);
    	menuOption.add(menuStyleBG);
    	menu.add(menuOption);
    	
    	//Menubar Edit
    	menuOption=new JMenu("Edit");
    	menuAutoCopy=new JCheckBoxMenuItem("Auto copy");
    	menuAutoCopy.setSelected(false);
    	menuAutoCopy.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			if(menuAutoCopy.isSelected()){
    			checkAutoCopy=true;
    			}
    			else checkAutoCopy=false;
    		}
    	});
    	menuOption.add(menuAutoCopy);
    	menuExit=new JMenuItem("Copy");
    	menuExit.setMnemonic(KeyEvent.VK_C);
    	menuExit.setAccelerator(
    			KeyStroke.getKeyStroke(KeyEvent.VK_C,Event.CTRL_MASK));
    	menuExit.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){		
    					Toolkit.getDefaultToolkit()
    							.getSystemClipboard()
    							.setContents(new StringSelection (display2.getText()), null);  				
            	}
    	});
    	menuOption.add(menuExit);
    	menuExit=new JMenuItem("Paste");
    	menuExit.setMnemonic(KeyEvent.VK_P);
    	menuExit.setAccelerator(
    			KeyStroke.getKeyStroke(KeyEvent.VK_V,Event.CTRL_MASK));
    	menuExit.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    				try{
    					display1.setText((String)Toolkit.getDefaultToolkit()
    							.getSystemClipboard()
    							.getData(DataFlavor.stringFlavor));
    				}catch(Exception ex){}
            	}
    	});
    	menuOption.setMnemonic(KeyEvent.VK_E);
    	menuOption.add(menuExit);
    	menu.add(menuOption);
    	
    	//nút tiêu giảm và tắt
    	min=new JButton("_");
    	min.setFocusable(false);
        close=new JButton("x");
        close.setFocusable(false);
        min.setFocusPainted(false);
        close.setFocusPainted(false);
        close.setBackground(Color.RED);
        // sự kiện tiêu giảm
        min.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
                // minimize
                setState(ICONIFIED);
            }
        });
        //sự kiện thoát chương trình
        close.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
                // terminate program
            	saveToXML();
                System.exit(0);
            }
        });
    	menu.add(Box.createHorizontalGlue());
    	menu.add(min);
    	menu.add(close);
    	this.setJMenuBar(menu);
    	container.add(pane);
    	if(!readXML()){
    		try{
        	imgbg = ImageIO.read(new File("image/bg1.png"));	
    		}catch(Exception e){}
    	}
       	setVisible(true);
    }
    
    public final void setDesign() {
        try {
            UIManager.setLookAndFeel(
                    "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception e) {   
        }
    }

    
	
	/**
	 * Sử lí các sự kiện khi nhấn vào các button
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
        if(event.getSource() == button[0]){
        	if(checkResual==true&&checkError==false){
        		M=temporary1;
        		display1.setText("M");
        	}
        }
        else if(event.getSource() == button[1]){
        	if(M>=0)setDisplay1Spec2(Double.toString(M));
        	else setDisplay1Spec2("-"+Double.toString(-M));
        }       	
        else if(event.getSource() == button[2]){
        	int d=display1.getText().length();
        	if(d>0)
        	display1.replaceRange("", d-1, d);
        }
        else if(event.getSource() == button[3]){
        	display1.setText("");
        	checkDots=false;
        }
        else if(event.getSource() == button[4])
        	setDisplay1Spec2("√");
        else if(event.getSource() == button[5])
        	setDisplay1Spec2(Double.toString(Math.E));
        else if(event.getSource() == button[6]){
        	setDisplay1("+");
        	checkDots=false;
        }
        else if(event.getSource() == button[7]){
        	setDisplay1("-");
        	checkDots=false;
        }
        else if(event.getSource() == button[8])
        	setDisplay1("7");
        else  if(event.getSource() == button[9])
        	setDisplay1("8");
        else  if(event.getSource() == button[10])
        	setDisplay1("9");
        else if(event.getSource() == button[11]){
        	setDisplay1Spec1("×");
        }
        else if(event.getSource() == button[12])
        	setDisplay1("4");
        else  if(event.getSource() == button[13])
        	setDisplay1("5");
        else if(event.getSource() == button[14])
        	setDisplay1("6");
        else if(event.getSource() == button[15]){
        	setDisplay1Spec1("÷");
        }
        else  if(event.getSource() == button[16])
        	setDisplay1("1");
        else  if(event.getSource() == button[17])
        	setDisplay1("2");
        else   if(event.getSource() == button[18])
        	setDisplay1("3");
        else  if(event.getSource() == button[19])
        	setDisplay1("0");
        else  if(event.getSource() == button[20]){
        	setDisplay1Spec3();
        }
        else  if(event.getSource() == button[21]){
        	result();
        	checkResual=true;
        }
        else return;
        historyNumber=16;
	}
	
	/**
	 * sử lý set màn hình phép tính khi nhấn vào các số
	 * @param c
	 */
	void setDisplay1(String c){
		if(checkResual==true){
			display1.setText("");
			checkResual=false;checkDots=false;
		}
		display1.append(c);
	}
	
	/**
	 * sử lý set màn hình phép tính khi nhấn các nút phép tính
	 * @param c
	 */
	void setDisplay1Spec1(String c){
    	checkDots=false;
		String in=display1.getText();
		if(in.length()==0)return;
		else if(!Character.isDigit(in.charAt(in.length()-1)))return;
		else if(checkResual==true){
			if(checkError==true)return;
		display1.setText(display2.getText());
		checkResual=false;
		}
		display1.append(c);
	}
	
	/**
	 * sử lí sự kiện set màn hình phép tính khi ấn các nút phụ
	 * @param a
	 */
	void setDisplay1Spec2(String a){
    	if(checkResual==true){
			display1.setText("");
			checkResual=false;checkDots=false;
		}
    	String in=display1.getText();
    	if(in.length()==0)display1.append(a);
    	else{
    		char c=in.charAt(in.length()-1);
    		if(c=='+'||c=='-'||c=='×'||c=='÷')
    			display1.append(a);   	
    	}
    }
	
	/**
	 * set man hinh khi an nut "."
	 */
	void setDisplay1Spec3(){
		if(checkResual==true){
			display1.setText("");
			checkResual=false;checkDots=false;
		}
    	String in=display1.getText();
    	if(checkDots==true);
    	else if(in.length()==0){
    		display1.append(".");
    		checkDots=true;
    	}
    	else{
    		char c=in.charAt(in.length()-1);
    		if(c!='.'){
    			display1.append(".");
    			checkDots=true;
    		}
    	}
	}
	
	/**
	 * Trả về kết quả khi ấn dấu =
	 */
	public void result(){
		String in=display1.getText();
		if(in.isEmpty())return;
		i=0;
		//biến để kiểm tra kí tự
		char c;
		//kết quả
		temporary1=0;
		//kiểm tra xem có lỗi ko
		checkError=false;
		//gán kết quả bằng số đầu tiên
		temporary1=getNumbers(in);
		//chạy hết chiều dài phép tính
		while(i<in.length()){
			c=in.charAt(i);
			//phép trừ
			if(c=='-'){i++;
				temporary1-=getNumbers(in);
				
			}
			//phép cộng
			else if(c=='+'){i++;
				temporary1+=getNumbers(in);
				
			}
		}
		//nếu lỗi in ra lỗi
		if(checkError==true){
			display2.setText("ERROR");
			temporary1=0;
			return;
		}
			display2.setText(Double.toString(temporary1));
			if(resultL.size()>10){
				resultL.remove(0);
				operationL.remove(0);
			}
			if(checkAutoCopy){
			Toolkit.getDefaultToolkit()
			.getSystemClipboard()
			.setContents(new StringSelection (display2.getText()), null);
			}
			resultL.add(display2.getText());
			operationL.add(display1.getText());
	}
	
	/**
	 * Trả về 1 số được tính bằng 1 loạt phép nhân và chia
	 * @param in : chuỗi string biểu thức
	 * @return kết quả double
	 */
	public double getNumbers(String in){
		double tem1=getNumber(in);
		char c;
		while(i<in.length()){
			c=in.charAt(i);
			if(c=='×'){
				i++;
				tem1=tem1*getNumber(in);
			}
			else if(c=='÷'){
				i++;
				tem1=tem1/getNumber(in);
			}
			else return tem1;
		}
		return tem1;
	}
	/**
	 * Trả về 1 số tiếp theo trong dãy số, tính luôn căn nếu có.
	 * @param in : chuỗi string biểu thức
	 * @return kết quả
	 */
	public double getNumber(String in){
		//xác định vị trí đầu của dãy số
		int j=i;
		//Kiểm tra có căn không
		boolean sqrt=false;
		//Kiểm tra âm hay dương, dương là 1,âm là -1
		int sign=1;
		char c=in.charAt(i);
		//bắt lỗi phép tính
		try{
			//sử lí 1 loạt các phép cộng trừ
		while(c=='+'||c=='-'){
			if(c=='+'){i++;c=in.charAt(i);}
			else{
				sign=-1*sign;i++;c=in.charAt(i);
			}
			j++;
		}
		//sử lí căn
		if(c=='√'){sqrt=true;i++;c=in.charAt(i);}
		//sử lí 1 loạt phép cộng trừ sau dấu căn
		while(c=='+'||c=='-'){
				if(c=='+'){i++;c=in.charAt(i);}
				else{
					sign=-1*sign;i++;c=in.charAt(i);
				}
				j++;
			}
		//trả về vị trí kết thúc của số
		while(c>45&&c<58){
			i++;
			if(i==in.length())break;
			c=in.charAt(i);
		}
		}catch(ArrayIndexOutOfBoundsException e) {
			checkError=true;
        }
		//nếu ko có sô thì trả về 0
		if(i==j)return 0;
		//tính căn
		if(sqrt==true){
			try{
				double result=sign*Double.parseDouble(in.substring(j+1, i));
				if (result<0){
					checkError=true;
					return 0;
				}
			return Math.sqrt(result);
			}catch(NumberFormatException e) {
				checkError=true;
	        }
		}
		try{
		return sign*Double.parseDouble(in.substring(j, i));
		}catch(NumberFormatException e) {
			checkError=true;
        }
		return 0;
	}
	
	/**
	 * Restart chương trình
	 */
	public void restart() {
        StringBuilder cmd = new StringBuilder();
          cmd.append(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java ");
          for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
              cmd.append(jvmArg + " ");
          }
          cmd.append("-cp ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
          cmd.append(Window.class.getName()).append(" ");
          System.out.println(cmd.toString());
          try {
              Runtime.getRuntime().exec(cmd.toString());
          } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
          System.exit(0);
	}
	
	/**
	 * Xem lịch sử phía cũ hơn
	 */
	public void historyUp(){
		if(resultL.size()==0||historyNumber==0)return;
  	   	if(historyNumber==16)historyNumber=resultL.size();
  	   	historyNumber--;
  	   	display1.setText(operationL.get(historyNumber));
  	   	display2.setText(resultL.get(historyNumber));
	}
	
	/**
	 * Xem lịch sử mới hơn
	 */
	public void historyDown(){
 	   if(resultL.size()==0||historyNumber>resultL.size()-2)return;
 	   historyNumber++;
 	   display1.setText(operationL.get(historyNumber));
 	   display2.setText(resultL.get(historyNumber));
	}
	
	/**
	 * Thay đổi mode theo checkMode
	 * nếu đúng kích thước chương trình sẽ thu nhỏ lại
	 */
	public void changeMode(){
		if(!checkMode){
            setSize(300,400);
    		for(int i=0;i<22;i++){
            	button[i].show();
            }
    	}
    	else{
    		for(int i=0;i<22;i++){
    			button[i].hide();
    		}
           	setSize(300,140);
    	}
	}
	
	/**
	 * lưu lại trạng thái hoạt động khi thoát chương trình.
	 * mặc định lưu vào file option
	 */
	public void saveToXML() {
		String xml="option";
	    Document dom;
	    Element e = null;
	    String sI=null;

	    // instance of a DocumentBuilderFactory
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    try {
	        // use factory to get an instance of document builder
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        // create instance of DOM
	        dom = db.newDocument();

	        // create the root element
	        Element rootEle = dom.createElement(xml);

	        // create data elements and place them under root
	        e = dom.createElement("background");
	        e.appendChild(dom.createTextNode(Integer.toString(1+list.getSelectedIndex())));
	        rootEle.appendChild(e);

	        e = dom.createElement("modeSpecial");
	        e.appendChild(dom.createTextNode(Boolean.toString(checkMode)));
	        rootEle.appendChild(e);

	        e = dom.createElement("colorBackground");
	        e.appendChild(dom.createTextNode(Integer.toString(container.getBackground().getRGB())));
	        rootEle.appendChild(e);
	        
	        e = dom.createElement("colorButton");
	        e.appendChild(dom.createTextNode(Integer.toString(button[1].getBackground().getRGB())));
	        rootEle.appendChild(e);
	        
	        e = dom.createElement("colorDisplay");
	        e.appendChild(dom.createTextNode(Integer.toString(display1.getBackground().getRGB())));
	        rootEle.appendChild(e);
	        
	        e = dom.createElement("colorText");
	        e.appendChild(dom.createTextNode(Integer.toString(display1.getForeground().getRGB())));
	        rootEle.appendChild(e);
	        
	        e = dom.createElement("modeTextDisplay1");
	        if(display1.getComponentOrientation().isLeftToRight())sI="0";
	        else sI="1";
	        e.appendChild(dom.createTextNode(sI));
	        rootEle.appendChild(e);
	        
	        e = dom.createElement("modeTextDisplay2");
	        if(display2.getComponentOrientation().isLeftToRight())sI="0";
	        else sI="1";
	        e.appendChild(dom.createTextNode(sI));
	        rootEle.appendChild(e);

	        e = dom.createElement("alwayOnTop");
	        e.appendChild(dom.createTextNode(Boolean.toString(menuAlwayTop.isSelected())));
	        rootEle.appendChild(e);
	        
	        e = dom.createElement("autoCopy");
	        e.appendChild(dom.createTextNode(Boolean.toString(this.checkAutoCopy)));
	        rootEle.appendChild(e);
	        
	        e = dom.createElement("Transparent");
	        e.appendChild(dom.createTextNode(Integer.toString(Transparent.getValue())));
	        rootEle.appendChild(e);


	        e = dom.createElement("positionX");
	        e.appendChild(dom.createTextNode(Integer.toString(this.getLocationOnScreen().x)));
	        rootEle.appendChild(e);
	        
	        e = dom.createElement("positionY");
	        e.appendChild(dom.createTextNode(Integer.toString(this.getLocationOnScreen().y)));
	        rootEle.appendChild(e);

	        dom.appendChild(rootEle);

	        try {
	            Transformer tr = TransformerFactory.newInstance().newTransformer();
	            tr.setOutputProperty(OutputKeys.INDENT, "yes");
	            tr.setOutputProperty(OutputKeys.METHOD, "xml");
	            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

	            // send DOM to file
	            tr.transform(new DOMSource(dom), 
	                                 new StreamResult(new FileOutputStream(xml)));

	        } catch (TransformerException te) {
	            System.out.println(te.getMessage());
	        } catch (IOException ioe) {
	            System.out.println(ioe.getMessage());
	        }
	    } catch (ParserConfigurationException pce) {
	        System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
	    }
	}
	
	/**
	 * đọc dữ liệu trạng thái lần hoạt động trước
	 * @return
	 */
	public boolean readXML() {
		String xml="option";
		String sI=null;
        Document dom;
        Color colorI;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the    
            // XML file
            dom = db.parse(xml);

            Element doc = dom.getDocumentElement();
            
            //background
            sI = getTextValue(sI,doc,"background");
            imgbg = ImageIO.read(new File("image/bg"+sI+".png"));
            list.setSelectedIndex(Integer.parseInt(sI)-1);
            //mode
            sI = getTextValue(sI,doc,"modeSpecial");
            checkMode=Boolean.parseBoolean(sI);
            changeMode();
            //colorBackground
            sI = getTextValue(sI,doc,"colorBackground");
            colorI=new Color(Integer.parseInt(sI));
            container.setBackground(colorI);
			pane.setBackground(colorI);
			menu.setBackground(colorI);
          //colorButton
            sI = getTextValue(sI,doc,"colorButton");
            colorI=new Color(Integer.parseInt(sI));
            for(int i=0; i<22;i++)
				button[i].setBackground(colorI);
          //colorDisplay
            sI = getTextValue(sI,doc,"colorDisplay");
            colorI=new Color(Integer.parseInt(sI));
            display1.setBackground(colorI);
			scroll.setBorder(BorderFactory.createLineBorder(colorI));
			display2.setBackground(colorI);
			//colorText
            sI = getTextValue(sI,doc,"colorText");
            colorI=new Color(Integer.parseInt(sI));
            display1.setForeground(colorI);
			display2.setForeground(colorI);
			//Mode Text 1
			sI = getTextValue(sI,doc,"modeTextDisplay1");
			if(sI.equals("0"))display1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			else display1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			//Mode Text 2
			sI = getTextValue(sI,doc,"modeTextDisplay2");
			if(sI.equals("0"))display2.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			else display2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            if(display1.getComponentOrientation().isLeftToRight())
            	if (display2.getComponentOrientation().isLeftToRight())
            		leftleft.setSelected(true);
            	else
            		leftright.setSelected(true);
            else
            	if (display2.getComponentOrientation().isLeftToRight())
            		rightleft.setSelected(true);
            	else
            		rightright.setSelected(true);
			//Alway on Top
            sI = getTextValue(sI,doc,"alwayOnTop");
            if(!Boolean.parseBoolean(sI)){
            	menuAlwayTop.setSelected(false);
            	this.setAlwaysOnTop(false);
            }
          //auto Copy
            sI = getTextValue(sI,doc,"autoCopy");
            checkAutoCopy=Boolean.parseBoolean(sI);
            if(checkAutoCopy){
            	menuAutoCopy.setSelected(true);
            }
            //Transparent
            sI = getTextValue(sI,doc,"Transparent");
            Transparent.setValue(Integer.parseInt(sI));
            setOpacity((float)Transparent.getValue()/100);
            //position
            this.setLocation(Integer.parseInt(getTextValue(sI,doc,"positionX")),
            		Integer.parseInt(getTextValue(sI,doc,"positionY")));
            
            
            return true;

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        return false;
    }
	
	private String getTextValue(String def, Element doc, String tag) {
	    String value = def;
	    NodeList nl;
	    nl = doc.getElementsByTagName(tag);
	    if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
	        value = nl.item(0).getFirstChild().getNodeValue();
	    }
	    return value;
	}
	
    public static void main(String[] arguments) throws IOException{
    				CalculatorOfWindy c = new CalculatorOfWindy();
    					
        
    }

	

}
