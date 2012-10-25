package sg.edu.sutd.dss.client;

import java.util.TreeSet;

import sg.edu.sutd.dss.coding.Code;
import sg.edu.sutd.dss.data.FileBlock;
import sg.edu.sutd.dss.data.UserFile;

public class Encoder {
	private Code code;

	public Encoder(Code code) {
		this.code = code;
	}

	public TreeSet<FileBlock> divideUserFile(UserFile auf) {
		TreeSet<FileBlock> fileBlockSet = new TreeSet<FileBlock>();

		if (!auf.exists())
			return fileBlockSet;

		long fileBytes = auf.length();
		long fileBlockLength = code.getFileBlockLength();

		long integralBlockCounts = fileBytes / fileBlockLength;
		long tailFragmentLength = fileBytes % fileBlockLength;
		long totalBlockCounts = (tailFragmentLength == 0 ? integralBlockCounts
				: (integralBlockCounts + 1L));

		for (long i = 0; i < totalBlockCounts; i++) {
			FileBlock block = new FileBlock(auf, i, i * fileBlockLength,
					fileBlockLength);
			fileBlockSet.add(block);
		}
		return fileBlockSet;
	}
}
