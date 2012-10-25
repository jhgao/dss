package sg.edu.sutd.dss.data;

/**
 * Class <code>FileBlock</code> is used as source of getting
 * <code>RawBlock</code>, it is meta data refers to a certain section of a user
 * file, using offset and size parameter.
 * 
 * @author theme
 */
public class FileBlock implements Comparable<FileBlock> {

	UserFile srcFile; // source file
	private long sn; // serial number within one user file
	private long offset; // offset in source file
	private long length;

	public FileBlock(UserFile srcFile, long sn, long offset, long length) {
		this.srcFile = srcFile;
		this.sn = sn;
		this.offset = offset;
		this.length = length;
	}

	@Override
	public int compareTo(FileBlock o) {
		if (this.sn == o.sn) {
			return 0;
		} else {
			return (this.sn > o.sn ? 1 : -1);
		}
	}

	public UserFile getSrcFile() {
		return srcFile;
	}

	public long getSn() {
		return sn;
	}

	public long getOffset() {
		return offset;
	}

	public long getLength() {
		return length;
	}
}
