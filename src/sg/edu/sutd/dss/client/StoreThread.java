package sg.edu.sutd.dss.client;

import java.io.IOException;
import java.util.TreeSet;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import sg.edu.sutd.dss.coding.Code;
import sg.edu.sutd.dss.coding.CodingSetting;
import sg.edu.sutd.dss.coding.Simple;
import sg.edu.sutd.dss.data.FileBlock;
import sg.edu.sutd.dss.data.UserFile;

/**
 * divide one <code>UserFile</code>, encoding <code>RawBlock</code>s, and
 * populate threads to send <code>EncodedBlock</code>s to
 * <code>StorageNode</code>
 * 
 * @author theme
 */
public class StoreThread implements Runnable {
	private static Logger LOG;
	private UserFile userFile;
	private Code code;
	private TreeSet<FileBlock> userFileBlockSet;
	private Encoder encoder;

	public StoreThread(UserFile infile) {
		try {
			LOG = Logger.getLogger(this.getClass().getName());
			Handler h = new FileHandler("testFileBlockDivide.log", 0, 25480);
			h.setFormatter(new SimpleFormatter());
			LOG.addHandler(h);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		userFile = infile;
		code = CodingSetting.getInstance().getCurrentCode();
		encoder = new Encoder(new Simple());
	}

	@Override
	public void run() {
		LOG.info("StoreThread run to store file " + userFile.getAbsolutePath());
		this.userFileBlockSet = encoder.divideUserFile(userFile);

		for (FileBlock fb : this.userFileBlockSet) {
			LOG.info("[sn=" + fb.getSn() + "]" + " off= " + fb.getOffset()
					+ " len= " + fb.getLength());
		}
	}

}
