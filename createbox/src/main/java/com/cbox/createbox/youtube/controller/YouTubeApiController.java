package com.cbox.createbox.youtube.controller;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.FileCredentialStore;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;

@RestController
@RequestMapping("/youtube/api")
public class YouTubeApiController {
	  private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	  private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	  private static final long NUMBER_OF_VIDEOS_RETURNED = 1;
	 
	  
	  
	  // Oauth 방식을 위한 메서드(미완)
//	  /**
//	   * Authorizes the installed application to access user's protected data.
//	   *
//	   * @param scopes list of scopes needed to run upload.
//	   */
//	  private static Credential authorize(List<String> scopes) throws Exception {	
//		  Reader reader = new 
//	    // Load client secrets.
//	    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, )
//
//	    // Checks that the defaults have been replaced (Default = "Enter X here").
//	    if (clientSecrets.getDetails().getClientId().startsWith("Enter")
//	        || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
//	      System.out.println(
//	          "Enter Client ID and Secret from https://console.developers.google.com/project/_/apiui/credential"
//	          + "into youtube-cmdline-myuploads-sample/src/main/resources/client_secrets.json");
//	      System.exit(1);
//	    }
//
//	    // Set up file credential store.
//	    FileCredentialStore credentialStore = new FileCredentialStore(
//	        new File(System.getProperty("user.home"), ".credentials/youtube-api-myuploads.json"),
//	        JSON_FACTORY);
//
//	    // Set up authorization code flow.
//	    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//	        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, scopes).setCredentialStore(credentialStore)
//	        .build();
//
//	    // Build the local server and bind it to port 9000
//	    LocalServerReceiver localReceiver = new LocalServerReceiver.Builder().setPort(8080).build();
//
//	    // Authorize.
//	    return new AuthorizationCodeInstalledApp(flow, localReceiver).authorize("user");
//	  }
//	  
	  
	  
	@RequestMapping("/delete/comment")
	public ResponseEntity deleteYoutubeComment() {
		
		
		try {
			final YouTube youtube;
			youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
		          public void initialize(HttpRequest request) throws IOException {
		          }
		        }).setApplicationName("youtube-video-duration-get").build();

	        YouTube.Videos.List videos = youtube.videos().list("id,snippet,contentDetails");
	        videos.setKey("AIzaSyDx-HqMU7YIbR_zNeuVnuDvS29Ng0GGqCg");     
	        videos.setId("9U5Y7lbBZjw");
	        videos.setMaxResults(NUMBER_OF_VIDEOS_RETURNED); //조회 최대 갯수.
	        List<Video> videoList = videos.execute().getItems();
			
			System.out.println(videoList);
			
		}catch (GoogleJsonResponseException e) {
	        System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
	                + e.getDetails().getMessage());
	          } catch (IOException e) {
	            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
	          } catch (Throwable t) {
	            t.printStackTrace();
	          }
	

		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	
	
}
