package Mypackage;

import jakarta.servlet.ServletException;
 
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class MyServelet
 */
@WebServlet("/MyServelet")
public class MyServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServelet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
  
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	
		// API Setup 
		
		String apiKey = "e34fb8552911503f8af891f9029d1632";
		
		//  Get the city from input 
		String city = request.getParameter("city");
		String country = request.getParameter("country");


		
		
	//  Check API response status (handles invalid city or API errors)
	       
	       if (city == null || country == null || city.isEmpty() || country.isEmpty()) {
	    	    request.setAttribute("error", "Please enter city and country"); 
	    	    request.getRequestDispatcher("index.jsp").forward(request, response);
	    	    return;
	    	}
	       
			String displayCity = city.trim();

		// remove extra space and 
		city = city.trim().replace(" ", "%20");
		country = country.trim().toUpperCase();

		// Create the url for the openWeatherMap API request 
		String ApiUrl =
			    "https://api.openweathermap.org/data/2.5/weather?q="
			    + city + "," + country
			    + "&units=metric"
			    + "&appid="
			    + apiKey;

		
		// API Integration 
       URL url = new URL(ApiUrl);
       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
       connection.setRequestMethod("GET");
       
       //  Check API response status (handles invalid city or API errors)

       int responseCode = connection.getResponseCode();
       if (responseCode != 200) {
           request.setAttribute("error", "City not found");
           request.getRequestDispatcher("index.jsp").forward(request, response);
           return;
       }

       
       // Reading the data from network 
       InputStream inputstream = connection.getInputStream();
       InputStreamReader reader = new InputStreamReader(inputstream);
	
       //  want to store data in string 
     
       StringBuilder responseContent = new StringBuilder();
       
       // we will create scanner class to retrieve data from the reader 
       Scanner scanner = new Scanner(reader);
       while(scanner.hasNext())
       {
    	   responseContent.append(scanner.nextLine());
       }
       scanner.close();
       
       // TypeCasting = parsing the data into Json 
      // System.out.println(responseContent);
       
       Gson gson =new Gson();
       JsonObject jsonobject = gson.fromJson(responseContent.toString(),JsonObject.class);
       System.out.println(jsonobject);
	



	
	
	// Time
	long cityEpoch = jsonobject.get("dt").getAsLong()
            + jsonobject.get("timezone").getAsLong();

        LocalDateTime cityDateTime =
        Instant.ofEpochSecond(cityEpoch)
          .atZone(ZoneId.of("UTC"))
          .toLocalDateTime();

      String date = cityDateTime.toLocalDate().toString();
      String time = cityDateTime.toLocalTime().withNano(0).toString();
	// 

	long sunrise = jsonobject.getAsJsonObject("sys").get("sunrise").getAsLong();
	long sunset  = jsonobject.getAsJsonObject("sys").get("sunset").getAsLong();
	
	boolean isDay = cityEpoch >= sunrise && cityEpoch <= sunset;
	String dayNight = isDay ? "day" : "night";
	
	// Temperature
	
	int temperatureCelsius =
	        jsonobject.getAsJsonObject("main").get("temp").getAsInt();

	// Humidity
	
	int humidity =  jsonobject.getAsJsonObject("main").get("humidity").getAsInt();
	
	// wind speed 
	double windSpeed  = jsonobject.getAsJsonObject("wind").get("speed").getAsDouble();
	
	// Weather Condition 
	
	// Weather Array
	var weatherArray = jsonobject.getAsJsonArray("weather");

    String weatherCondition = jsonobject
                    .getAsJsonArray("weather")
                    .get(0)
                    .getAsJsonObject()
                    .get("main")
                    .getAsString();
    
 // Weather Icon
    String icon =
            weatherArray.get(0)
            .getAsJsonObject()
            .get("icon")
            .getAsString();
    

    // set the data as request attributes (for sending to the jsp page)
    
    request.setAttribute("date", date);
    request.setAttribute("time", time);
    request.setAttribute("city", displayCity);
    request.setAttribute("temperatureCelsius", temperatureCelsius);
    request.setAttribute("weatherCondition", weatherCondition);
    request.setAttribute("windSpeed", windSpeed);
    request.setAttribute("dayNight", dayNight);
    request.setAttribute("icon", icon);


    connection.disconnect();
    
    // forward the request to the weather.jsp page for rendering 
    
    request.getRequestDispatcher("weather.jsp").forward(request, response);
	}
}
