package sg.edu.sutd.dss.client;

public class StoreThread implements Runnable {
	String testString;
	
	public StoreThread(){
		System.out.println("StoreThread object created.");
		System.out.println("testString = " + testString);
	}
	@Override
	public void run() {
		testString = "testString assigned inside run()";
		System.out.println(testString);
	}

}
