package com.bursatec.seguridad.middleware.services.captcha.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import com.bursatec.seguridad.util.CaptchaHelper;
import com.bursatec.seguridad.util.ImagenSerializable;


public class CaptchaTest extends AbstractDependencyInjectionSpringContextTests {

	
	public void testCaptcha(){
		String id = CaptchaHelper.generateUniqueId();
		ImagenSerializable img = CaptchaHelper.generateCaptchaForId(id);
		Paint p = new Paint();
		p.image = CaptchaHelper.generateCaptchaForId(id).getImage();
		
		 JFrame frame = new JFrame("");
		    frame.setBackground(Color.BLACK);
		   
		    frame.setSize(400, 300);
		    frame.setContentPane(p);
		    frame.addWindowListener(new ExitListener());
		    frame.setVisible(true);
		    
		    System.out.println("");
		
	}
	
	protected String[] getConfigLocations() {
        return new String[] {"classpath:/com/bursatec/seguridad/middleware/spring/applicationContext.xml"};
	}
	
}

class Paint extends JPanel{
	public BufferedImage image = null;
	public void paint (Graphics g)
	   {
	      
	      
	      g.drawImage(image, 0, 0, this);
	   }
}
class ExitListener extends WindowAdapter {
	  public void windowClosing(WindowEvent event) {
	    System.exit(0);
	  }
	}
