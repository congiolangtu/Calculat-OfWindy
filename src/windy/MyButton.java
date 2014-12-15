package windy;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Lớp này kế thừa lớp JButton của java dùng để tạo ra các Button có độ bóng
 * @author Windy
 *
 */
public class MyButton extends JButton {
	
    Image image;
    ImageObserver imageObserver;

    /**
     * Khởi tạo button
     * @param img: hình ảnh tạo độ bóng
     */
    MyButton(ImageIcon img) {
            super();
            image = img.getImage();
            imageObserver=img.getImageObserver();
        }
    /**
     * Chỉnh lại hàm vẽ button của lớp cha
     */
     public void paint( Graphics g ) {
            super.paint( g );
            g.drawImage(image,  0 , 0 , getWidth() , getHeight(),imageObserver );
        }
  }


