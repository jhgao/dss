package sg.edu.sutd.dss.data;

/**
 * Class <code>FileBlock</code> is used as source of getting <code>RawBlock</code>,
 * it is meta data refers to a certain section of a user file,
 * using offset and size parameter. 
 * @author theme
 *
 */
public class FileBlock {
	UserFile srcFile;	//source file
	private long sn;	//serial number within one user file
	private long offset;	//offset in source file
	private long length;
}
