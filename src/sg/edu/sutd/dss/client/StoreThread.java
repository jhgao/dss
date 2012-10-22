package sg.edu.sutd.dss.client;

import java.util.logging.Logger;

import sg.edu.sutd.dss.data.UserFile;

/**
 * divide one UserFile, encoding RawBlocks, send EncodedBlocks to storage node
 * @author theme
 *
 */
public class StoreThread implements Runnable {
	private static Logger LOG;
	private UserFile userFile;

	public StoreThread( UserFile infile ) {
		LOG = Logger.getLogger(this.getClass().getName());
		userFile = infile;
	}

	@Override
	public void run() {
		LOG.info("StoreThread run to store file " + userFile.getAbsolutePath());
	}

}
