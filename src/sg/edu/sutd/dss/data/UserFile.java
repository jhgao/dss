package sg.edu.sutd.dss.data;

import java.io.File;
import java.net.UnknownHostException;

/**
 * populate and organize <code>FileBlock</code>s of a UserFile
 * 
 * @author theme
 * 
 */
public class UserFile extends File {
	int nameHash;
	int ownerHash;

	public UserFile(String pathname) {
		super(pathname);
		this.nameHash = this.hashCode();

		try {
			java.net.InetAddress localAddr = java.net.InetAddress
					.getLocalHost();
			this.ownerHash = localAddr.getHostName().hashCode();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getNameHash() {
		return nameHash;
	}

	public int getOwnerHash() {
		return ownerHash;
	}

}
