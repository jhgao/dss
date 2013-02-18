package sg.edu.sutd.dss.coding;

/**
 * used in demo as a simple encoding
 * 
 * @author theme
 * 
 */
public class Simple extends Code {
	private int k = 5;
	private int n = 5;
	private int rawBlockSize = 2 * 2;
	private int fileBlockSize = rawBlockSize * k;
	private int encodedBlockSize = rawBlockSize;

	@Override
	public int getK() {
		return k;
	}

	@Override
	public int getN() {
		return n;
	}

	@Override
	public int getFileBlockSize() {
		return this.fileBlockSize;
	}

	@Override
	public int getRawBlockSize() {
		return this.rawBlockSize;
	}

	@Override
	public int getEncodedBlockSize() {
		return this.encodedBlockSize;
	}
}
