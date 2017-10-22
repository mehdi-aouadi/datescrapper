package org.mehdi.datescrapper;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class DateScrapperTest {
	
	private static Logger logger = LoggerFactory.getLogger(DateScrapperTest.class);
	private DateScrapper ds;
	private DateMatcher dm;
	
	@Before
	public void setUp() {
		//This should be a Mock instead of a real DateMatcher Object
		dm = new DateMatcher();
		ds = new DateScrapper(dm);
	}
	
	
	@Test
	@Parameters({"src/test/resources/input.txt"})
	public void loaderFromExistingValidFileMapTest(String filePath) throws URISyntaxException, FileNotFoundException {
		Map<Date, Integer> finalDates = ds.extrcatDates(filePath);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date expectedDate = sdf.parse("24/08/2011");
			assertTrue(finalDates.containsKey(expectedDate));
			assertEquals(new Integer(1), finalDates.get(expectedDate));
			expectedDate = sdf.parse("18/09/2011");
			assertTrue(finalDates.containsKey(expectedDate));
			assertEquals(new Integer(2), finalDates.get(expectedDate));
			expectedDate = sdf.parse("15/05/2014");
			assertTrue(finalDates.containsKey(expectedDate));
			assertEquals(new Integer(1), finalDates.get(expectedDate));
			expectedDate = sdf.parse("20/05/2014");
			assertTrue(finalDates.containsKey(expectedDate));
			assertEquals(new Integer(2), finalDates.get(expectedDate));
			expectedDate = sdf.parse("07/04/2016");
			assertTrue(finalDates.containsKey(expectedDate));
			assertEquals(new Integer(1), finalDates.get(expectedDate));
			expectedDate = sdf.parse("27/01/2016");
			assertTrue(finalDates.containsKey(expectedDate));
			assertEquals(new Integer(1), finalDates.get(expectedDate));
			expectedDate = sdf.parse("23/06/2016");
			assertTrue(finalDates.containsKey(expectedDate));
			assertEquals(new Integer(1), finalDates.get(expectedDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
