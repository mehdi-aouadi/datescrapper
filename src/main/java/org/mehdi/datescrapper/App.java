package org.mehdi.datescrapper;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
        System.out.println( "Welcome to the Date Scrapper." );
        System.out.println( "Please enter the file path you want to scrap :" );
        Scanner in = new Scanner(System.in);
        String filePath = in.next();
        DateMatcher dm = new DateMatcher();
        DateScrapper ds = new DateScrapper(dm);
        ds.extrcatDates(filePath);
        in.close();
        
    }
}
