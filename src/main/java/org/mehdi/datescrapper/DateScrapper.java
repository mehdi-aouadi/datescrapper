package org.mehdi.datescrapper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mehdi AOUADI
 *
 */
public class DateScrapper {

	private static Logger logger = LoggerFactory.getLogger(DateScrapper.class);
	private DateMatcher dm;
	private Map<Pattern, String> regexpMap;

	/**
	 * Initialize a rexexpMap containing the dates patterns as Pattern in the
	 * key and their SimpleDateFormat as a String in the value. The rexexpMap is
	 * retrieved from a DateMatcher.
	 */
	public DateScrapper(DateMatcher dm) {
		this.dm = dm;
		regexpMap = dm.getPatternsMap();
	}

	/**
	 * Extracts the dates from a text file and put them in a Map with a Date
	 * Object in the key and an Integer occurrence number in the value.
	 * 
	 * @param filePath
	 *            : The path of the input text file to scrap
	 * @return a Map containing the extracted dates as a Date Object in the key
	 *         and its occurrence in the value.
	 */
	public Map<Date, Integer> extrcatDates(String filePath) {
		Map<Date, Integer> dates = new HashMap<>();
		try {
			BufferedReader bf = new BufferedReader(new FileReader(filePath));
			Map<String, String> raxDates = extracDatesFromInput(bf);
			dates = formatDates(raxDates);
		} catch (FileNotFoundException e) {
			logger.error("File not found in {}. Please check the file path.", filePath);
			e.printStackTrace();
		}

		printDatesToConsole(dates);

		return dates;
	}

	/**
	 * Prints the dates and their occurrence to the Console.
	 * 
	 * @param dates
	 *            : a Map containing the dates in the key and their occurrence
	 *            in the value.
	 */
	public void printDatesToConsole(Map<Date, Integer> dates) {
		String year = "0000";
		String month = "00";
		System.out.println("Found " + dates.size() + " different dates :");
		for (Entry<Date, Integer> entry : dates.entrySet()) {
			String entryYear = new SimpleDateFormat("YYYY").format(entry.getKey());
			String entryMonth = new SimpleDateFormat("MM").format(entry.getKey());
			String entryDay = new SimpleDateFormat("dd").format(entry.getKey());
			if (entryYear.equals(year)) {
				if (entryMonth.equals(month)) {
					System.out.println("\t\t -" + entryDay + " (" + entry.getValue() + ")");
				} else {
					System.out.println("\t -" + entryMonth);
					System.out.println("\t\t -" + entryDay + " (" + entry.getValue() + ")");
				}
			} else {
				year = entryYear;
				month = entryMonth;
				System.out.println(entryYear + " :");
				System.out.println("\t -" + entryMonth);
				System.out.println(
						"\t\t -" + new SimpleDateFormat("dd").format(entry.getKey()) + " (" + entry.getValue() + ")");
			}
		}
	}

	/**
	 * Parses the extracted String dates to Date Objects stored in a Map with
	 * the Date Object in the Key and its occurrence number in the Integer
	 * value.
	 * 
	 * @param textDatesMap
	 *            a Map of String dates and their SimpleDateFormat as a String.
	 * @return a Map of Date Objects in the key and their occurrence number as
	 *         an Integer in the value.
	 */
	public Map<Date, Integer> formatDates(Map<String, String> textDatesMap) {
		Map<Date, Integer> dates = new TreeMap<>();

		for (Entry<String, String> entry : textDatesMap.entrySet()) {			
			try {
				DateFormat df = new SimpleDateFormat(entry.getValue(), Locale.US);
				Date result = df.parse(entry.getKey());
				if (dates.containsKey(result)) {
					dates.put(result, dates.get(result) + 1);

				} else {
					dates.put(result, 1);
				}
			} catch (ParseException e) {
				logger.error("Unable to parse the date : {} using the date format {}.", entry.getKey(),
						entry.getValue());
				e.printStackTrace();
			}
		}

		return dates;
	}

	/**
	 * Extracts the dates from a BufferedReader.
	 * 
	 * @param reader
	 *            the BufferedReader containing the text to be scrapped.
	 * @return a Map of the extracted dates as a String in the Key and their
	 *         SimpleDateFormat as a String in the value.
	 */
	public Map<String, String> extracDatesFromInput(BufferedReader reader) {

		Map<String, String> extractedDates = new HashMap<>();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				extractedDates.putAll(extractDatesFromLine(line));
			}
			reader.close();
		} catch (IOException e) {
			logger.error("Error when reading the file.");
			e.printStackTrace();
		}

		return extractedDates;
	}

	/**
	 * Scraps the dates from a String using the Map<Pattern, String> regexpMap.
	 * The scrapped dates are stored as a String (key) with their
	 * SimpleDateFormat (value).
	 * 
	 * @param line
	 *            : a String to scrap.
	 * @return a Map with the scrapped dates in the key and their
	 *         SimpleDateFormat in the value.
	 */
	public Map<String, String> extractDatesFromLine(String line) {

		Map<String, String> extractedDates = new HashMap<>();
		for (Entry<Pattern, String> entry : regexpMap.entrySet()) {
			Matcher matcher = entry.getKey().matcher(line);
			while (matcher.find()) {
				extractedDates.put(matcher.group(), entry.getValue());
			}
		}

		return extractedDates;
	}

}
