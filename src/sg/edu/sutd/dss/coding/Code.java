package sg.edu.sutd.dss.coding;

public abstract class Code {
	public abstract int getK();

	public abstract int getN();

	public abstract int getFileBlockSize();

	public abstract int getRawBlockSize();

	public abstract int getEncodedBlockSize();
}
