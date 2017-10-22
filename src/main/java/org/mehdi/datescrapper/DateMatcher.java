package org.mehdi.datescrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Represents the dates patterns used to parse the input text. The regular
 * expressions are stored in a map with the Java regular expression
 * representation in the key and the SimpleDateFormat format in the value. The
 * patterns are statically declared and could be gathered from an input
 * configuration file. The regular expression MUST match one and only one
 * SimpleDateFormat. The regular expression MUST match one and only one Date
 * otherwise the date will be counted multiple time.
 * 
 * @author Mehdi AOUADI
 *
 */
public class DateMatcher {

	private static Logger logger = LoggerFactory.getLogger(DateMatcher.class);

	/**
	 * This Map contains the Java regular expressions in the key and the
	 * SimpleDateFormat in the value (used to parse the extracted string dates
	 * in a Date object).
	 */
	private static final Map<String, String> dateRegexpMap = new HashMap<String, String>() {
		{
			put("\\d{2}\\s(JANUARY|FEBRUARY|MARCH|APRIL|MAY|JUNE|JULY|AUGUST|SEPTEMBER|OCTOBER|NOVEMBER|DECEMBER)\\s\\d{4}",
					"dd MMMM yyyy");
			put("\\d{2},\\s(JANUARY|FEBRUARY|MARCH|APRIL|MAY|JUNE|JULY|AUGUST|SEPTEMBER|OCTOBER|NOVEMBER|DECEMBER)\\s\\d{4}",
					"dd, MMMM yyyy");
			put("\\d{2}\\s(JANUARY|FEBRUARY|MARCH|APRIL|MAY|JUNE|JULY|AUGUST|SEPTEMBER|OCTOBER|NOVEMBER|DECEMBER),\\s\\d{4}",
					"dd MMMM, yyyy");
			put("(JANUARY|FEBRUARY|MARCH|APRIL|MAY|JUNE|JULY|AUGUST|SEPTEMBER|OCTOBER|NOVEMBER|DECEMBER)\\s\\d{2},\\s\\d{4}",
					"MMMM dd, yyyy");
			put("(JANUARY|FEBRUARY|MARCH|APRIL|MAY|JUNE|JULY|AUGUST|SEPTEMBER|OCTOBER|NOVEMBER|DECEMBER)\\s\\d{2},\\s\\d{4}",
					"MMMM dd, yyyy");
			put("\\d{4}-\\d{2}-\\d{2}", "yyyy-MM-dd");
			put("\\d{4}/\\d{2}/\\d{2}", "yyyy/mm/dd");
		}
	};

	/**
	 * @return a Map with a compiled Pattern object in the key and the
	 *         SimpleFormatDate format as a value.
	 */
	public Map<Pattern, String> getPatternsMap() {
		logger.debug("Loading the regular expressions.");
		Map<Pattern, String> patterns = new HashMap<>();
		for (Entry<String, String> entry : dateRegexpMap.entrySet()) {
			try {
				Pattern pattern = Pattern.compile(entry.getKey(), Pattern.CASE_INSENSITIVE);
				patterns.put(pattern, entry.getValue());
			} catch (PatternSyntaxException e) {
				logger.error(
						"Error when compiling he regular expression : {}. Please check the regular expression declaration.",
						entry.getKey());
				continue;
			}

		}
		logger.debug("All regular expressions have been loaded.");
		return patterns;
	}

}