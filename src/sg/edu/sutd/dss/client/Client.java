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
		parseMainArg(args);
	}

	private static void parseMainArg(String[] args) {
		Options opts = new Options();
		opts.addOption("s", "storefile", true, "store a local file into dss");
		opts.addOption("a", "snodeaddr", true, "storage node address");
		opts.addOption("p", "snodeport", true, "storage node port");

		CommandLineParser parser = new PosixParser();
		CommandLine cmd;
		try {
			cmd = parser.parse(opts, args);

			if (cmd.hasOption("s") && cmd.hasOption("a") && cmd.hasOption("p")) {
				// save a file to a storage node				
				String fn = cmd.getOptionValue("s");
				String addr = cmd.getOptionValue("a");
				Integer port = Integer.parseInt(cmd.getOptionValue("p"));
				System.out.println("store a file" + fn);

				StoreThread st = new StoreThread(new UserFile(fn),addr,port);
				st.run();
			} else {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("client", opts);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.err.println("Parsing command failed.  Reason: "
					+ e.getMessage());
		}
	}
}
