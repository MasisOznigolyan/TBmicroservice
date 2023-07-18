package com.TBmail.EmailService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.TBmail.EmailService.Collections.Email;
import com.TBmail.EmailService.Collections.LastSent;
import com.TBmail.EmailService.Collections.News;
import com.TBmail.EmailService.Collections.NewsCategory;
import com.TBmail.EmailService.Collections.User;
import com.TBmail.EmailService.Collections.UserCategory;
import com.TBmail.EmailService.Collections.UserEmail;
import com.TBmail.EmailService.Parser.GetHead;
import com.TBmail.EmailService.Parser.LastNews;
import com.TBmail.EmailService.Parser.MailContent;
import com.TBmail.EmailService.Service.UserService;

@EnableScheduling
@Component
@Service
public class ScheduledTask {
	

	@Autowired
	UserService userCrud;
	
	@Autowired
	EmailCrud emailCrud;
	
	@Autowired
	NewsCategoryCrud newsCategoryCrud;
	
	@Autowired
	NewsCrud newsCrud;
	
	@Autowired
	LastSentCrud lastSentCrud;
	
	@Autowired
	UserCategoryCrud userCategoryCrud;
	
	@Autowired
	UserEmailCrud userEmailCrud;
	
	String lastSent=new String();
	
	RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	private EmailSenderService senderService;
	
	//@Scheduled(cron = "0 * * * * *")   //   "0 10/2 * * *" 
	public void sendMail() {
		
		//initializeDb();
		
		
		List<User> data=userCrud.getAllUsers(); //getUsersFromAddress("http://localhost:8080/users"); 			
		
		System.out.println(data);
		System.out.println("+-+-+-+-+-+-+-+");
		for(int i=0; i<data.size(); i++) {
			UserCategory uC=userCategoryCrud.findByUserId(data.get(i).getId());
			String tag=uC.getNewsCategoryId().getCategoryUrl();		
			ArrayList<String> urls=new ArrayList<String>(); 
			for(int j=0; j<20; j++) {
				//System.out.println(LastNews.getNewsUrl(MailContent.getHtml(tag)));
				String t1=LastNews.getNewsUrl(MailContent.getHtml(tag));
				urls.add(t1);
				
			}
			//Email eMail=userEmailCrud.findByUserId(data.get(i).getId()).getEmailId(); 
			
			UserEmail userEmail=userEmailCrud.findByUserId(data.get(i).getId()); 
			LastSent ls=lastSentCrud.findByUserEmailId(userEmail);
			News n=ls.getNewsId();
			lastSent=n.getUrl();
			//System.out.println("last sent is "+lastSent);
			//System.out.println("last news in website is "+urls.get(0));
			if(!(urls.get(0).equals(lastSent))) {
				System.out.println("mail is being sent");
				ArrayList<String> mailUrls=new ArrayList<String>();
				int index=urls.indexOf(lastSent);
				System.out.println(index);
				if(index!=-1) {
					LastNews.resetIndex();
					for(int j=index-1; j>=0; j--) {
						mailUrls.add(urls.get(j));
					}
				}
				else{
					for(int j=0; j<urls.size(); j++) {
						mailUrls.add(urls.get(j));
					}
				}
				
				
				for(int j=0; j<mailUrls.size(); j++) {
					
					System.out.println("Following news will be sent: "+mailUrls.get(j));
					//senderService.sendEmail(userCrud.emailRepository.findByUserId(data.get(i).getId()).getEMail(),"TB özet",mailUrls.get(j) ); //MailContent.getContent(mailUrls.get(j))
					News news=new News();
					news.setContent(MailContent.getContent(mailUrls.get(j)));
					news.setUrl(mailUrls.get(j));
					
					
					 String page =MailContent.getHtml(mailUrls.get(j));
				        
				     String title = GetHead.getHead(page);
				     news.setTitle(title);
				     
				     
				     String postDate = LastNews.getLastNewsTime(mailUrls.get(j));
				        
				     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
				     LocalDateTime localDateTime = LocalDateTime.parse(postDate, formatter);
				     ZoneId gmtPlus3 = ZoneId.of("GMT+3");
				     ZonedDateTime zonedDateTime = localDateTime.atZone(gmtPlus3);
				     LocalDateTime dateTime = zonedDateTime.toLocalDateTime();
				     news.setPostDate(dateTime);
				     news.setCategoryId(uC.getNewsCategoryId());
				     newsCrud.addNews(news);
					
					
					if(j==mailUrls.size()-1) {
						//update lastSent
						LastSent url=lastSentCrud.findByUserEmailId(userEmailCrud.findByUserId(data.get(i).getId()));
						//System.out.println(url.getNewsId().getUrl());
						
						url.setNewsId(news);
						lastSentCrud.addLastSent(url);
						
					
					}
					
				}
				
				
				
			}
			else
				System.out.println("New news is not found");
			
			
			
			LastNews.resetIndex();
			
			
		}
		
		
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		System.out.println("Waiting...");
		System.out.println("current date and time is: "+dtf.format(now));
		
		System.out.println("++++++++++++++++++++++++++++++++++++");
		
	}
	
	/*public Pair<MongoClient,MongoDatabase> getDb() {
		Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\masis.oznigolyan\\Desktop\\EmailService\\src\\main\\resources\\application.properties");
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String mongoURI = properties.getProperty("mongodb.uri");
        String databaseName = properties.getProperty("mongodb.database");
        MongoClient mongoClient = MongoClients.create(mongoURI);
        Pair<MongoClient,MongoDatabase> ans= Pair.of(mongoClient, mongoClient.getDatabase(databaseName));
        return ans;
	}*/
	
	/*public void disconnectDb( Pair<MongoClient,MongoDatabase> db) {
		MongoClient mongoClient=db.getFirst();
		mongoClient.close();
	}	*/
	
	
	public void wait(int ms)
	{
	    try
	    {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex)
	    {
	        Thread.currentThread().interrupt();
	    }
	}
	
	public void initializeDb() {     
	
		
        userCrud.deleteAllUsers();
        emailCrud.deleteAllEmails();
        newsCategoryCrud.deleteAllNewsCategory();
        userCategoryCrud.deleteAllUserCategory();
        newsCrud.deleteAllNews();
        lastSentCrud.deleteAllLastSent();
        userEmailCrud.deleteAllUserEmail();
        
        User user1 = new User();
       
        user1.setName("Mikael Öznigolyan");
        userCrud.createUser(user1);
        
        Email email1=new Email();
        email1.setEMail("oznigolyan3@gmail.com");
       
        
        emailCrud.createNewEmail(email1);
        userCrud.createUser(user1);
        UserEmail ue= new UserEmail();
        ue.setEmailId(email1);
        ue.setUserId(user1);
        userEmailCrud.addUserEmail(ue);
        
        User user2 = new User();
        user2.setName("Masis Öznigolyan");
        
        userCrud.createUser(user2);
        
        Email email2=new Email();
        email2.setEMail("oznigolyan3@su.khas.edu.tr");
       
        emailCrud.createNewEmail(email2);
        
        
        UserEmail ue1= new UserEmail();
        ue1.setEmailId(email2);
        ue1.setUserId(user2);
        userEmailCrud.addUserEmail(ue1);
        
        NewsCategory category= new NewsCategory();
        category.setName("Darrüşaffaka Lassa");
        category.setCategoryUrl("https://trendbasket.net/tag/darussafaka-lassa/");
        newsCategoryCrud.addNewsCategory(category);
        
        NewsCategory category2= new NewsCategory();
        category2.setName("Fenerbahçe Beko");
        category2.setCategoryUrl("https://trendbasket.net/tag/fenerbahce-beko/");
        newsCategoryCrud.addNewsCategory(category2);
        
        UserCategory uk= new UserCategory();
        uk.setUserId(user1);
        uk.setNewsCategoryId(category2);
        userCategoryCrud.createUserCategory(uk);
        
        UserCategory uk1= new UserCategory();
        uk1.setUserId(user2);
        uk1.setNewsCategoryId(category);
        userCategoryCrud.createUserCategory(uk1);
        
        String url="https://trendbasket.net/sertac-sanli-boyle-bir-camiaya-geldigim-icim-heyecanliyim/";
        String page= MailContent.getHtml(url);
        
        
        
        String title=GetHead.getHead(page);
        String content=MailContent.getContent(url);
        String postDate=LastNews.getLastNewsTime(url);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(postDate, formatter);
        
        ZoneId gmtPlus3 = ZoneId.of("GMT+3");
        ZonedDateTime zonedDateTime = localDateTime.atZone(gmtPlus3);
        LocalDateTime dateTime = zonedDateTime.toLocalDateTime();
        
        News news1= new News(url,title,content,dateTime,category2 );
        newsCrud.addNews(news1);
        
        LastSent lastSent= new LastSent();
        lastSent.setUserEmailId(ue);
        lastSent.setNewsId(news1);
        lastSentCrud.addLastSent(lastSent);
        
        String url1 = "https://trendbasket.net/basketbol-sampiyonlar-liginde-temsilcilerimizin-gruplari-belli-oldu/";
        String page1 =MailContent.getHtml(url1);
        
        String title1 = GetHead.getHead(page1);
        String content1 = MailContent.getContent(url1);
        String postDate1 = LastNews.getLastNewsTime(url1);
        
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime localDateTime1 = LocalDateTime.parse(postDate1, formatter1);

        ZonedDateTime zonedDateTime1 = localDateTime1.atZone(gmtPlus3);
        LocalDateTime dateTime1 = zonedDateTime1.toLocalDateTime();
        
        
        News news2 = new News(url1, title1, content1, dateTime1, category);
        newsCrud.addNews(news2);
        
        LastSent lastSent2 = new LastSent();
        lastSent2.setUserEmailId(ue1);
        lastSent2.setNewsId(news2);
        lastSentCrud.addLastSent(lastSent2);
	 
	 
	
	}
	public List<User> getUsersFromAddress(String address) {
	    ResponseEntity<User[]> responseEntity = restTemplate.exchange(address,HttpMethod.GET,null, User[].class);

	    if (responseEntity.getStatusCode() == HttpStatus.OK) {
	        User[] users = responseEntity.getBody();
	        return Arrays.asList(users);
	    } else {
	        // Handle the case when the request is not successful
	        return Collections.emptyList();
	    }
	}
}
