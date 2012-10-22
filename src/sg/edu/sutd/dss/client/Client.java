package sg.edu.sutd.dss.client;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import sg.edu.sutd.dss.data.UserFile;

public class Client {

	public static void main(String[] args) {
		parseCmd(args);
	}

	private static void parseCmd(String[] args) {
		Options opts = new Options();
		opts.addOption("s", "storefile", true, "store a local file into dss");

		CommandLineParser parser = new PosixParser();
		CommandLine cmd;
		try {
			cmd = parser.parse(opts, args);

			if (cmd.hasOption("s")) {
				String fn = cmd.getOptionValue("s");
				System.out.println("has options store with tgt = " + fn);
				StoreThread st = new StoreThread(new UserFile(fn));
				st.run();
			} else {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("client", opts);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.err.println("Parsing command failed.  Reason: " + e.getMessage());
		}
	}
}
