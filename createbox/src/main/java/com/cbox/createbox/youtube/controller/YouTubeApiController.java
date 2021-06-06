package com.cbox.createbox.youtube.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;
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
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Comment;
import com.google.api.services.youtube.model.CommentListResponse;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.Video;

@RestController
@RequestMapping("/youtube/api")
public class YouTubeApiController {
	
	private static final String CLIENT_SECRETS= "/client_secret.json";
//    private static final Collection<String> SCOPES = Arrays.asList("https://www.googleapis.com/auth/youtube.force-ssl");
    private static final Collection<String> SCOPES =  Arrays.asList("https://www.googleapis.com/auth/youtube.readonly");
//    private static final Collection<String> SCOPES = null;
	
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
//	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final long NUMBER_OF_VIDEOS_RETURNED = 1;

	private static final String DEVELOPER_KEY = "AIzaSyDPq7Q3Pc2w5CSsNCxEegzTEJjpdKOnwxo";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    
    /**
     * oAuth
     */
    public static Credential authorize(final NetHttpTransport httpTransport) throws IOException {
        // Load client secrets.
        InputStream in = YouTubeApiController.class.getResourceAsStream(CLIENT_SECRETS);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    /*
     * API 통신을 위한 공통 Service
     */
    public static YouTube getService(String applicaton_name) throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = authorize(httpTransport);
        return new YouTube.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(applicaton_name).build();
    }
    
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
	        videos.setKey("AIzaSyDPq7Q3Pc2w5CSsNCxEegzTEJjpdKOnwxo");     
//	        videos.setKey("AIzaSyDx-HqMU7YIbR_zNeuVnuDvS29Ng0GGqCg");     
	        videos.setId("9U5Y7lbBZjw");
	        videos.setMaxResults(NUMBER_OF_VIDEOS_RETURNED); //조회 최대 갯수.
	        List<Video> videoList = videos.execute().getItems();
			
			System.out.println(videoList);
			
		} catch (GoogleJsonResponseException e) {
	        System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
	                + e.getDetails().getMessage());
	          } catch (IOException e) {
	            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
	          } catch (Throwable t) {
	            t.printStackTrace();
	          }

		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	
	@RequestMapping("/list/comment")
	public CommentThreadListResponse listYoutubeComment() throws GeneralSecurityException, IOException {
		String application_name = "list-comment-test";
		YouTube youtubeService = getService(application_name);
		
        // Define and execute the API request
		 YouTube.CommentThreads.List request = youtubeService.commentThreads().list("id,snippet,replies");
		 CommentThreadListResponse response = request.setKey(DEVELOPER_KEY).setVideoId("GJ96e9ovj1s").execute();
        
        List<CommentThread> commentList = response.getItems();
        System.out.println(commentList);

		return response;
	}
	
	
}
