package com.konglong.test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassLoaderTest {
	static final Logger log = Logger.getLogger(ClassLoaderTest.class.getName());
	public static void main(String[] args) {
		try {
			Integer r1 = 260;
			Integer r2 = 260;
			if(r1 == r2) {
				System.out.println("==");
			}
			
			System.out.println(ClassLoaderTest.class.getClassLoader());
			
			Class.forName("com.konglong.test.ClassLoaderTest", true, ClassLoaderTest.class.getClassLoader().getParent());
		} catch (ClassNotFoundException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
