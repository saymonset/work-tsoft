/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ImagenSerializable.java
 * Apr 17, 2008
 */
package com.bursatec.seguridad.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

/**
 * @author Emigdio
 *
 */
public class ImagenSerializable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	transient BufferedImage image;
	 
	  
	  public BufferedImage getImage(){
		  return image;
	  }
	  public void setImage(BufferedImage image){
		  this.image=image;
	  }
	 
	 
	  private void writeObject(java.io.ObjectOutputStream out)throws IOException{
	    
	    ImageIO.write(image,"jpeg",ImageIO.createImageOutputStream(out));
	  }
	 
	  private void readObject(java.io.ObjectInputStream in)throws IOException, ClassNotFoundException{
	    
	    image=ImageIO.read(ImageIO.createImageInputStream(in));
	  }

}
