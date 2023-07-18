package com.TBmail.EmailService.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MailContent {
	
	public static String getHtml(String website) {
		String page=new String();
        try {
            String parseLine; 
                        
            URL URL = new URL(website); 
            BufferedReader br = new BufferedReader(new InputStreamReader(URL.openStream()));  
            /*
              URL.openStream() returns InputStream
              constructor of InputStreamReader class -> InputStreamReader( InputStream x )  
             constructor of BufferedReader class -> BufferedReader(InputStreamReader x  ) 
             */
            while ((parseLine = br.readLine()) != null) { 
               
            	page=page+"/n"+parseLine;
            }
            br.close();

        } catch (MalformedURLException me) {
            System.out.println(me);

        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        
        page=page.replace("&#8217;","'");
        page=page.replace("&#8230","...");
        page=page.replace("&#8216;","'");
        page=page.replace("&#8220;","\"");
        page=page.replace("&#8221;","\"");
        page=page.replace("&nbsp;","	");
        page=page.replace("&#x27a1;", "➡");
        page=page.replace("&#xfe0f;","");
        
        
        
        return page;
	}
	
	public static String getContent(String website ) {
		String mailContent=new String();
		
		String page= getHtml(website);
        
        
        
        String head=GetHead.getHead(page);
        
        mailContent+=head;
        mailContent+="\n";
        mailContent+="\n";
        mailContent+="\n";
        //System.out.println(head);
        //System.out.println();
        	
        int start=page.indexOf("<p>"); 
        while(start!=-1) {
        	
            
            String news1=GetOne.get(page,start);
            mailContent+=news1;
            //System.out.print(news1);
            
            start=page.indexOf("<p>",start+1);
            
        }
        
        mailContent+="Tags: ";
        mailContent+=GetTags.get(page);
        //System.out.print("Tags: ");
        //System.out.println(GetTags.get(page));
        //System.out.print(mailContent);
        
        
        
        return mailContent;
	}
	
}
