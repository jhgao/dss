package sg.edu.sutd.dss.data;

import java.util.Arrays;

public abstract class DataBlock implements Comparable<DataBlock> {
	// meta
	protected long ownerId;
	protected long fileId;
	protected long offset;
	// data
	protected byte[] dataArray;

	public DataBlock(long ownerId, long fileId, long offset, byte[] array) {
		this.ownerId = ownerId;
		this.fileId = fileId;
		this.offset = offset;

		int arrayLen = array.length;
		this.dataArray = new byte[arrayLen];
		System.arraycopy(array, 0, this.dataArray, 0, arrayLen);
	}

	public DataBlock(DataBlock b) {
		this.ownerId = b.getOwnerId();
		this.fileId = b.getFileId();
		this.offset = b.getOffset();

		this.dataArray = Arrays.copyOf(b.getDataArray(),
				b.getDataArray().length);
	}

	public boolean idEqualsTo(DataBlock b) {
		if (this.ownerId == b.getOwnerId() && this.fileId == b.getFileId()) {
			return true;
		} else
			return false;

	}

	public boolean dataEqualsTo(DataBlock b) {
		if (Arrays.equals(this.dataArray, b.getDataArray())) {
			return true;
		} else
			return false;
	}

	public int compareTo(DataBlock o) {
		if (this.ownerId != o.getOwnerId()) {
			return this.ownerId > o.getOwnerId() ? 1 : -1;
		} else if (this.fileId != o.getFileId()) {
			return this.fileId > o.getFileId() ? 1 : -1;
		} else if (this.offset != o.getOffset()) {
			return (this.offset > o.getOffset() ? 1 : -1);
		} else
			return 0;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public long getFileId() {
		return fileId;
	}

	public long getOffset() {
		return offset;
	}

	public byte[] getDataArray() {
		return dataArray;
	}
}
