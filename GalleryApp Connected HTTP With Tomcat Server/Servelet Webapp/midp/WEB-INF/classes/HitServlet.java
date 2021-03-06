import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.lang.reflect.Field;
import java.util.Random;




public class HitServlet extends HttpServlet {
  private int mCount;
  
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
		ServletContext sc = getServletContext();

        try {
			InputStream is = new FileInputStream("./WebContents/images/8b52.jpg");
            // it is the responsibility of the container to close output stream
            OutputStream os = response.getOutputStream();

            if (is == null) {

                response.setContentType("text/plain");
                os.write("Failed to send image".getBytes());
            } else {

                response.setContentType("image/jpg");

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = is.read(buffer)) != -1) {

                    os.write(buffer, 0, bytesRead);
                }
            }
        } catch (Exception e) {
			System.out.println("Error: " + e);
		}
  }
  
  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
	  System.out.println("doPut Method");
	  try {
		String dir = "./WebContents/images/";

        File file = new File(dir);

        // true if the directory was created, false otherwise
        if (file.mkdirs()) {
            System.out.println("%tomcat&/bin/WebContents/images/ Directory is created to save files.");
        } else {
            System.out.println("%tomcat&/bin/WebContents/images/ Directory already exists. ");
		}
		InputStream inputStream =  req.getInputStream();
		
		String body = inputStreamSave(inputStream);
		System.out.println(body);
	  } catch (Exception e) {
		System.out.println("Error: " + e);
	  }
      System.out.println("Do put method done");
  }
  

	private String inputStreamSave(InputStream inputStream) throws IOException {
		InputStream input = inputStream;
		byte[] buf = new byte[1024];
		int bytesRead;
		String fileName = fileNameGeneration() + ".jpg";
		OutputStream output = new FileOutputStream("./WebContents/images/" + fileName);
		while ((bytesRead = input.read(buf)) > 0) {
			output.write(buf, 0, bytesRead);
		}
        return fileName + " saved";
    }
	
	private String fileNameGeneration() {
		int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();
	    StringBuilder buffer = new StringBuilder(targetStringLength);
	    for (int i = 0; i < targetStringLength; i++) {
	        int randomLimitedInt = leftLimit + (int) 
	          (random.nextFloat() * (rightLimit - leftLimit + 1));
	        buffer.append((char) randomLimitedInt);
	    }
	    String generatedString = buffer.toString();
	 
	    return generatedString;
	}
}
