package sg.edu.sutd.dss.coding;


public class CodingSetting {
	private static CodingSetting instance;
	private Code currentCode;
	
	private CodingSetting() {
		currentCode = new Simple();
	}
	
	public static CodingSetting getInstance() {
		if (null == instance) {
			instance = new CodingSetting();
		}
		return instance;
	}

	public Code getCurrentCode() {
		return currentCode;
	}

	public void setCurrentCode(Code currentCode) {
		this.currentCode = currentCode;
	}
}

