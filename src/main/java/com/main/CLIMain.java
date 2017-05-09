package com.main;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.clients.FlickrClient;
import com.google.api.client.http.javanet.NetHttpTransport;

public class CLIMain {

	//would rather inject dependencies but spring config is painful. guice.. ? 
	static FlickrClient fc;

	public static void main(String[] args) {

		Options options = new Options();
		options.addOption("t", "tag", true, "tag to search with");
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		try {
			CommandLine cmd = parser.parse(options, args);
			if (cmd.hasOption("t")) {
				String tag = cmd.getOptionValue("t");
				System.out.println("the 'tag' option: " + tag);
				FlickrClient fc = setFC();
				try {
					System.out.println(fc.getFeedWithTag(tag));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				formatter.printHelp("CLIMain", options, true);
			}
		} catch (ParseException e) {
			formatter.printHelp("CLIMain", options, true);
			e.printStackTrace();
		}

	}

	protected static FlickrClient setFC() {
		if (null == fc) {
			fc = new FlickrClient(new NetHttpTransport());
		}
		return fc;
	}

}
