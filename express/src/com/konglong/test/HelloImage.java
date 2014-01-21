package com.konglong.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HelloImage {
	public static void main(String[] args) {
		try {
			BufferedImage image = new BufferedImage(80, 25, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.getGraphics();
			g.setColor(new Color(255,255,255));
			g.fillRect(0, 0, 80, 25);
			g.setColor(new Color(0,0,0));
			g.drawString("helloImage", 6, 16);
			g.dispose();
			ImageIO.write(image, "jpeg", new FileOutputStream("D:\\java"));
			System.out.println("--");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}