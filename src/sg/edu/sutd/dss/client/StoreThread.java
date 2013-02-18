package sg.edu.sutd.dss.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import sg.edu.sutd.dss.coding.Code;
import sg.edu.sutd.dss.coding.CodingSetting;
import sg.edu.sutd.dss.coding.Simple;
import sg.edu.sutd.dss.data.EncodedBlock;
import sg.edu.sutd.dss.data.FileBlock;
import sg.edu.sutd.dss.data.RawBlock;
import sg.edu.sutd.dss.data.UserFile;
import sg.edu.sutd.dss.protocol.cmd.CmdProtocol.Cmd;

/**
 * divide <code>UserFile</code>, encoding <code>RawBlock</code>s, and populate
 * threads to send <code>EncodedBlock</code>s to <code>StorageNode</code>
 * 
 * @author theme
 */
public class StoreThread implements Runnable {
	private static Logger LOG;
	private UserFile userFile;
	private TreeSet<FileBlock> userFileBlockSet;

	private Code code;
	private Encoder encoder;
	
	private String snodeAddr;
	private Integer snodePort;

	public StoreThread(UserFile infile, String aAddr, Integer aPort) {
		try {
			LOG = Logger.getLogger(this.getClass().getName());
			Handler h = new FileHandler("StoreThread.log", 0, 25480);
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
		encoder = new Encoder(new Simple()); // use a Code to initialize a
												// Encoder
		snodeAddr = aAddr;
		snodePort = aPort;
	}

	@Override
	public void run() {
		LOG.info("StoreThread run to store file " + userFile.getAbsolutePath());

		// divide file
		this.userFileBlockSet = encoder.divideUserFile(userFile);
		LOG.info("userFile Block Set size = " + userFileBlockSet.size());

		// process file block
		for (Iterator<FileBlock> ifb = userFileBlockSet.iterator(); ifb
				.hasNext();) {
			FileBlock fb = ifb.next();
			LOG.info("Process a FileBlock, SN=" + fb.getSn() + ", Offset ="
					+ fb.getOffset());
			// get raw block
			TreeSet<RawBlock> rawBlockSet = null;
			try {
				rawBlockSet = encoder.divideFileBlock(fb);
			} catch (FileNotFoundException e) {
				LOG.severe("File not found: "
						+ fb.getSrcFile().getAbsolutePath());
				e.printStackTrace();
			}

			// encode raw blockSet
			TreeSet<EncodedBlock> encodedBlockSet = encoder.encode(rawBlockSet);

			// transport encoded block
			StringBuffer ensbuf = new StringBuffer();
			for (Iterator<EncodedBlock> ieb = encodedBlockSet.iterator(); ieb
					.hasNext();) {
				EncodedBlock eb = ieb.next();
				ensbuf.append(Encoder.bytesToHex(eb.getDataArray()) + "\n");
			}
			LOG.info("Got EncodedBlock:\n" + ensbuf.toString());
			
			try {
				Socket cmdskt = new Socket(snodeAddr,snodePort);
				//write out store request
				Cmd.Builder req = Cmd.newBuilder();
				req.setName("REQ_save_file");
				req.setId(0);
				req.setType(Cmd.CmdType.STORAGE);
				req.setDbgString("[REQ] save file request");
				
				req.build().writeTo(cmdskt.getOutputStream());
				
				
			} catch (UnknownHostException e) {
				LOG.severe("Unknown host:" + snodeAddr);
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
