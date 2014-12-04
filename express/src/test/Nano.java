package test;

public class Nano {
	public static void main(String[] args) {
		try {
			testNano();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static void testNano() throws InterruptedException {
		double start = System.nanoTime();
		Thread.sleep(10);
		double end = System.nanoTime();
		
		System.out.println("¼ä¸ô£º" + (end - start)/1000000);
		
		System.out.println(System.lineSeparator());
		System.out.println(System.getProperties());
		System.out.println(System.getProperty("java.class.path"));
	}
}
