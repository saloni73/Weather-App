<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Weather Report</title>


 <link rel="stylesheet" 
      href="${pageContext.request.contextPath}/css/Newstyle.css">
     
     <!-- font awesome -->

<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
 

</head>

<body class="result-page"
      data-weather="${weatherCondition}"
      data-time="${dayNight}">



<div class="weather-card">

    <h2> Weather Report</h2>
    
    <!-- error handling  -->

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <c:if test="${not empty temperatureCelsius}">
<img
    src="https://openweathermap.org/img/wn/${icon}@4x.png"
    alt="Weather Icon"
    class="weather-icon"
/>


  
        <div class="temp">
        <i class="fa-solid fa-temperature-half"></i>
        ${temperatureCelsius}°C
        </div>
        
        
        <div class="info">
          <i class="fa-solid fa-location-dot"></i>
        <b>City:</b> ${city}
        </div>
        
        
        <div class="info">
          <i class="fa-solid fa-cloud"></i>
        <b>Weather:</b> ${weatherCondition}
        </div>
        
        <div class="info">
          <i class="fa-solid fa-wind"></i>
        <b>Wind:</b> ${windSpeed} m/s
        </div>
        
        
        <div class="info">  
         <i class="fa-solid fa-calendar-days"></i>
        <b>Date:</b> ${date}
        </div>
        
        <div class="info">
          <i class="fa-solid fa-clock"></i>
         <b>Time:</b> ${time} 
        </div>

    </c:if>

</div>
<script src="${pageContext.request.contextPath}/js/Script.js"></script>
</body>
</html>
