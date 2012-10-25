package sg.edu.sutd.dss.coding;

public abstract class Code {
	protected int fileBlockLength = 1024; 	//Byte, a file block length

	public int getFileBlockLength() {
		return fileBlockLength;
	}
}
