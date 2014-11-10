package windy;


import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Event;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.*;

public class CalculatorOfWindy extends JFrame implements ActionListener {
	
	//Mảng chứa 22 nút chức năng
	JButton[] button = new JButton[22];
	//Nút đặc biệt thay đổi kích thước
	JButton buttonSpecial;
	//tối giản và tắt chương trình
	JButton min,close;
	//Mảng chứa các kí tự của nút
	String[] buttonString = {"M+", "M", "←", "C",
							 "√", "e", "+", "-",
							 "7", "8", "9", "×",
							 "4", "5", "6", "÷",
	 	   					 "1", "2", "3",
		 					 "0", ".", "="};
	//2 màn hình text hiện phép tình và kết quả
    JTextArea display1 = new JTextArea(1,20);
    JTextArea display2 = new JTextArea(1,20);
    //Thanh menubar
    JMenuBar menu= new JMenuBar();
    //Các menu trong menuBar
    JMenu menuFile,menuOption,menuView,menuHelp,menuTransparent;
    //check alwayTop
    JCheckBoxMenuItem menuAlwayTop;
    //menu exit
    JMenuItem menuExit;
    //thanh kéo trong suốt
    JSlider Transparent;
    //font chữ
    Font font = new Font("Times new Roman", Font.ITALIC, 22);
    //biến đếm.
    int i=0;
    //Kiểm tra có lỗi không.
    boolean checkError=false;
    //Kiểm tra xem đã ấn dấu bằng chưa.
    boolean checkResual=false;
    //Kiểm tra xem có được ấn dấu . tiếp không
    boolean checkDots=false;
    //Biến M lưu kết quả phép tính.
    double M;
    //Biến lưu kết quả.
    double temporary1=0;

	int pX,pY;
    
	//Khởi tạo chương trình.
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
    	
    	//tạo các button và set thông số
    	//vị trí, tên
    	for(int i=0;i<22;i++){
    		button[i] = new JButton();
    		button[i].setText(buttonString[i]);
    		button[i].setFont(font);
    		button[i].addActionListener(this);
    	}
    	for(int i=0;i<4;i++){
    		button[i].setSize(64, 32);
    		button[i].setLocation(10+i*72, 100);
    		add(button[i]);
    	}
    	for(int i=4,j=0;i<8;i++){
    		button[i].setSize(64, 32);
    		button[i].setLocation(10+j*72,140 );
    		add(button[i]);
    		j++;
    	}
    	for(int i=8,j=0;i<12;i++){
    		button[i].setSize(64, 32);
    		button[i].setLocation(10+j*72,180 );
    		add(button[i]);
    		j++;
    	}
    	for(int i=12,j=0;i<16;i++){
    		button[i].setSize(64, 32);
    		button[i].setLocation(10+j*72,220 );
    		add(button[i]);
    		j++;
    	}
    	for(int i=16,j=0;i<19;i++){
    		button[i].setSize(64, 32);
    		button[i].setLocation(10+j*72,260 );
    		add(button[i]);
    		j++;
    	}
    		
    	button[19].setSize(136,32);
    	button[19].setLocation(10,300);
    	add(button[19]);
    	button[20].setSize(64,32);
    	button[20].setLocation(154,300);
    	add(button[20]);
    	button[21].setSize(64,72);
    	button[21].setLocation(226,260);
    	add(button[21]);
    		
    	//Set các màn hình;
    	display1.setFont(font);
    	display1.setEditable(true);
    	display1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    	display1.setSize(280, 30);
    	display1.setLocation(10, 20);
    	add(display1);
    	display2.setFont(font);
    	display2.setEditable(false);
    	display2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    	display2.setSize(280, 30);
    	display2.setLocation(10, 50);
    	add(display2);
    	
    	//set button thay doi kich thuoc
    	buttonSpecial = new JButton();
    	buttonSpecial.addActionListener(this);
    	buttonSpecial.setSize(64, 10);
    	buttonSpecial.setLocation(118, 85);
    	add(buttonSpecial);
    	
    	 //Menu File
    	menuExit=new JMenuItem("Exit");
    	menuExit.setMnemonic(KeyEvent.VK_0);
    	menuExit.setAccelerator(
    			KeyStroke.getKeyStroke(KeyEvent.VK_0,Event.CTRL_MASK));
    	menuExit.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){System.exit(0);}
    	});
 
    	menuFile=new JMenu("File");
    	menuFile.setMnemonic(KeyEvent.VK_F);
    	menuFile.add(new JMenuItem("Restart"));
    	menuFile.add(menuExit);
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
    	
    	//Menu Edit
    	menuAlwayTop=new JCheckBoxMenuItem("Always on top");
    	menuAlwayTop.setSelected(true);
    	menuAlwayTop.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			if(menuAlwayTop.isSelected())setAlwaysOnTop(true);
    			else setAlwaysOnTop(false);
    		}
    	});
    	Transparent=new JSlider();
    	Transparent.setMajorTickSpacing(100);
    	Transparent.setMinorTickSpacing(30);
    	Transparent.setValue(70);
    	Transparent.addChangeListener(new ChangeListener(){
    		 public void stateChanged(ChangeEvent changeEvent) {
    			setOpacity((float)Transparent.getValue()/100);
    		} 		
    	});
    	menuTransparent=new JMenu("Transparent");
    	menuTransparent.add(Transparent);
    	menuOption=new JMenu("Option");
    	menuOption.setMnemonic(KeyEvent.VK_O);
    	menuOption.add(menuAlwayTop);
    	menuOption.add(menuTransparent);
    	menu.add(menuOption);
    	
    	
    	min=new JButton("_");
        close=new JButton("x");
        min.setFocusPainted(false);
        close.setFocusPainted(false);
        close.setBackground(Color.RED);
        
        min.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
                // minimize
                setState(ICONIFIED);
            }
        });
        
        close.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
                // terminate program
                System.exit(0);
            }
        });
    	menu.add(Box.createHorizontalGlue());
    	menu.add(min);
    	menu.add(close);
    	this.setJMenuBar(menu);
       	setVisible(true);
    }
    
    public final void setDesign() {
        try {
            UIManager.setLookAndFeel(
                    "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception e) {   
        }
    }

    
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
        if(event.getSource() == button[0]){
        	if(checkResual==true&&checkError==false){
        		M=temporary1;
        		display1.setText("M");
        	}
        }
        if(event.getSource() == button[1]){
        	if(M>=0)setDisplay1Spec2(Double.toString(M));
        	else setDisplay1Spec2("-"+Double.toString(-M));
        }       	
        if(event.getSource() == button[2]){
        	int d=display1.getText().length();
        	if(d>0)
        	display1.replaceRange("", d-1, d);
        }
        if(event.getSource() == button[3]){
        	display1.setText("");
        	checkDots=false;
        }
        if(event.getSource() == button[4])
        	setDisplay1Spec2("√");
        if(event.getSource() == button[5])
        	setDisplay1Spec2(Double.toString(Math.E));
        if(event.getSource() == button[6]){
        	setDisplay1("+");
        	checkDots=false;
        }
        if(event.getSource() == button[7]){
        	setDisplay1("-");
        	checkDots=false;
        }
        if(event.getSource() == button[8])
        	setDisplay1("7");
        if(event.getSource() == button[9])
        	setDisplay1("8");
        if(event.getSource() == button[10])
        	setDisplay1("9");
        if(event.getSource() == button[11]){
        	setDisplay1Spec1("×");
        	checkDots=false;
        }
        if(event.getSource() == button[12])
        	setDisplay1("4");
        if(event.getSource() == button[13])
        	setDisplay1("5");
        if(event.getSource() == button[14])
        	setDisplay1("6");
        if(event.getSource() == button[15]){
        	setDisplay1Spec1("÷");
        	checkDots=false;
        }
        if(event.getSource() == button[16])
        	setDisplay1("1");
        if(event.getSource() == button[17])
        	setDisplay1("2");
        if(event.getSource() == button[18])
        	setDisplay1("3");
        if(event.getSource() == button[19])
        	setDisplay1("0");
        if(event.getSource() == button[20]){
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
        if(event.getSource() == button[21]){
        	result();
        	checkResual=true;
        }
	}
	
	void setDisplay1(String c){
		if(checkResual==true){
			display1.setText("");
			checkResual=false;checkDots=false;
		}
		display1.append(c);
	}
	void setDisplay1Spec1(String c){
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
	 * Trả về kết quả khi ấn dấu =
	 */
	public void result(){
		String in=display1.getText();
		i=0;
		char c;
		temporary1=0;
		checkError=false;
		temporary1=getNumbers(in);
		while(i<in.length()){
			c=in.charAt(i);
			if(c=='-'){i++;
				temporary1-=getNumbers(in);
				
			}
			else if(c=='+'){i++;
				temporary1+=getNumbers(in);
				
			}
		}
		if(checkError==true){
			display2.setText("ERROR");
			temporary1=0;
			return;
		}
		if(temporary1>=0)
			display2.setText(Double.toString(temporary1));
		else
			display2.setText(Double.toString(-temporary1)+"-");
	}
	
	/**
	 * Lấy ra 1 số được tính bằng 1 loạt phép nhân và chia
	 * @param in
	 * @return
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
	 * Trả về 1 số, tính luôn căn nếu có.
	 * @param in
	 * @return
	 */
	public double getNumber(String in){
		int j=i;
		//Kiểm tra có căn không
		boolean sqrt=false;
		//Kiểm tra âm hay dương, dương là 1,âm là -1
		int sign=1;
		char c=in.charAt(i);
		try{
		while(c=='+'||c=='-'){
			if(c=='+'){i++;c=in.charAt(i);}
			else{
				sign=-1*sign;i++;c=in.charAt(i);
			}
			j++;
		}
		if(c=='√'){sqrt=true;i++;c=in.charAt(i);}
		while(c=='+'||c=='-'){
				if(c=='+'){i++;c=in.charAt(i);}
				else{
					sign=-1*sign;i++;c=in.charAt(i);
				}
				j++;
			}
		while(c>45&&c<58){
			i++;
			if(i==in.length())break;
			c=in.charAt(i);
		}
		}catch(ArrayIndexOutOfBoundsException e) {
			checkError=true;
        }
		if(i==j)return 0;
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
	
	
    public static void main(String[] arguments) {
    				CalculatorOfWindy c = new CalculatorOfWindy();
    		
        
    }
	

}
