package com.TBmail.EmailService;
import java.util.UUID;

public class Uid {
	public static String generateUniqueId() {
		    
		    UUID uuid = UUID.randomUUID();
		    
		    String uniqueId = uuid.toString();
		    
		    return uniqueId;
		 }
}
