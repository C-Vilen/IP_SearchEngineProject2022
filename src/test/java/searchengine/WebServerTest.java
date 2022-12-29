// // Packages
// package searchengine;

// // Imports
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import java.net.URI;
// import java.net.http.HttpClient;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse.BodyHandlers;
// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.TestInstance.Lifecycle;
// import java.io.IOException;
// import java.net.BindException;
// import java.util.Random;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.TestInstance;
// import com.sun.net.httpserver.HttpServer;


// @TestInstance(Lifecycle.PER_CLASS)
// class WebServerTest {
//     private static FileReader fileReader;
//     private static Search search;
//     private static WebServer server = null;
//     private static final String datafile = "data/test-file.txt";
//     // private static HttpServer httpServer;
//     // private static final int BACKLOG = 0;
//     private static int PORT;


//     @BeforeAll
//     void setUp() throws IOException {
//         var rnd = new Random();
//         while (server == null) {
//             try {
//                 fileReader = new FileReader(datafile);
//                 search = new Search(fileReader.readFile());
//                 PORT = rnd.nextInt(60000) + 1024;
//                 server = new WebServer(PORT, datafile);
//                 // httpServer = HttpServer.create(new InetSocketAddress(PORT), BACKLOG);
//                 // httpServer.start();
//             } catch (BindException e) {
//                 // port in use. Try again
//             } 
//         }
//     }

//     @AfterAll
//     void tearDown() {
//         server.httpServer.stop(0);
//         server = null;
//     }

//     @Test
//     void lookupWebServer() {
//         // httpServer.start();
//         String baseURL = String.format("http://localhost:%d/search?q=", server.httpServer.getAddress().getPort());
//         assertEquals("[{\"url\": \"http://page1.com\", \"title\": \"title1\"}, {\"url\": \"http://page2.com\", \"title\": \"title2\"}]", 
//             httpGet(baseURL + "word1"));
//         assertEquals("[{\"url\": \"http://page1.com\", \"title\": \"title1\"}]",
//             httpGet(baseURL + "word2"));
//         assertEquals("[{\"url\": \"http://page2.com\", \"title\": \"title2\"}]", 
//             httpGet(baseURL + "word3"));
//         assertEquals("[]", 
//             httpGet(baseURL + "word4"));
//     }

//     private String httpGet(String url) {
//         var uri = URI.create(url);
//         var client = HttpClient.newHttpClient();
//         var request = HttpRequest.newBuilder().uri(uri).GET().build();
//         try {
//             return client.send(request, BodyHandlers.ofString()).body();
//         } catch (IOException | InterruptedException e) {
//             e.printStackTrace();
//             return null;
//         }
//     }
// }