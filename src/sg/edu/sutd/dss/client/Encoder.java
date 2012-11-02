package sg.edu.sutd.dss.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import sg.edu.sutd.dss.coding.Code;
import sg.edu.sutd.dss.data.EncodedBlock;
import sg.edu.sutd.dss.data.FileBlock;
import sg.edu.sutd.dss.data.RawBlock;
import sg.edu.sutd.dss.data.UserFile;

/**
 * Encoder take <code>RawBlock</code> and encode it into
 * <code>Encoded Block</code>. extra: divide file
 * 
 * @author theme
 * 
 */
public class Encoder {
	private Code code;
	private static Logger LOG;

	public Encoder(Code code) {
		this.code = code;

		try {
			LOG = Logger.getLogger(this.getClass().getName());
			Handler h = new FileHandler("Encoder.log", 0, 25480);
			h.setFormatter(new SimpleFormatter());
			LOG.addHandler(h);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public TreeSet<FileBlock> divideUserFile(UserFile auf) {
		TreeSet<FileBlock> fileBlockSet = new TreeSet<FileBlock>();

		if (!auf.exists())
			return fileBlockSet;

		long fileBytes = auf.length();
		long fileBlockSize = this.code.getFileBlockSize();
		LOG.info("Divide a file of len=" + fileBytes + " with file block size="
				+ fileBlockSize);

		long integralBlockCounts = fileBytes / fileBlockSize;
		long tailFragmentLength = fileBytes % fileBlockSize;
		long totalBlockCounts = (tailFragmentLength == 0 ? integralBlockCounts
				: (integralBlockCounts + 1L));

		for (long i = 0; i < totalBlockCounts; i++) {
			FileBlock block = new FileBlock(auf, i, i * fileBlockSize,
					fileBlockSize);
			fileBlockSet.add(block);
			// LOG.info("Got a file block of offset=" + block.getOffset());
		}

		LOG.info("Divided file into FileBlocks, counts =" + fileBlockSet.size());
		return fileBlockSet;
	}

	public TreeSet<RawBlock> divideFileBlock(FileBlock fb)
			throws FileNotFoundException {
		TreeSet<RawBlock> rbs = new TreeSet<RawBlock>();
		LOG.info("Divide FileBlock, from file with hash= "
				+ fb.getSrcFile().getNameHash());

		FileInputStream in = new FileInputStream(fb.getSrcFile());
		int rawBlockSize = this.code.getRawBlockSize();
		byte[] ba = new byte[rawBlockSize];
		ByteBuffer inbuf = ByteBuffer.wrap(ba);

		FileChannel fc = in.getChannel();
		int k = this.code.getK();
		StringBuffer rbsbuf = new StringBuffer();
		for (int i = 0; i < k; i++) {
			try {
				fc.position((long) (i * rawBlockSize));
				fc.read(inbuf);
			} catch (IOException e) {
				e.printStackTrace();
			}

			RawBlock rb = new RawBlock(fb.getSrcFile().getOwnerHash(), fb
					.getSrcFile().getNameHash(), fb.getOffset() + i
					* rawBlockSize, ba);
			rbsbuf.append(Encoder.bytesToHex(rb.getDataArray()) + "\n");

			rbs.add(rb);
			inbuf.clear();
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOG.info("Got raw block data =\n" + rbsbuf.toString());
		return rbs;
	}

	public TreeSet<EncodedBlock> encode(TreeSet<RawBlock> rbs) {
		LOG.info("Encode a RawBlockSet of size" + rbs.size());
		TreeSet<EncodedBlock> ebs = new TreeSet<EncodedBlock>();
		for (Iterator<RawBlock> i = rbs.iterator(); i.hasNext();) {
			RawBlock rb = i.next();
			// TODO empty stub
			EncodedBlock eb = new EncodedBlock(rb.getOwnerId(), rb.getFileId(),
					rb.getOffset(), rb.getDataArray());
			ebs.add(eb);
		}
		return ebs;
	}

	public static String bytesToHex(byte[] bytes) {
		final char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] hexChars = new char[bytes.length * 2];
		int v;
		for (int j = 0; j < bytes.length; j++) {
			v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
}
