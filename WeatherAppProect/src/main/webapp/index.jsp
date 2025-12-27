<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Smart Weather Forecast</title>

    <!-- Main CSS -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/home.css">

    <!-- Font Awesome -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body class="home-page">

<div class="card">

    <!-- Hero Image -->
    <img class="hero-img"
         src="${pageContext.request.contextPath}/images/weather.png"
         alt="Weather illustration">

    <!-- Heading -->
    <h1 class="title">🌤 Smart Weather Forecast</h1>

    <!-- Subtitle -->
    <p class="subtitle">
        Check real-time weather of any city worldwide
    </p>

    <!-- Weather Form -->
    <form action="MyServelet" method="post">

        <!-- City + Country Row -->
        <div class="form-row">
            <input type="text"
                   name="city"
                   placeholder="Enter city"
                   required>

            <input type="text"
                   name="country"
                   id="country"
                   placeholder="Country code (IN, US)"
                   maxlength="2"
                   required>
        </div>

        <!-- Popular countries -->
        <select class="country-select"
                onchange="setCountry(this.value)">
            <option value="">Quick select country</option>
            <option value="IN">🇮🇳 India</option>
            <option value="US">🇺🇸 USA</option>
            <option value="GB">🇬🇧 UK</option>
            <option value="FR">🇫🇷 France</option>
            <option value="DE">🇩🇪 Germany</option>
            <option value="JP">🇯🇵 Japan</option>
        </select>

        <!-- Submit -->
        <button type="submit">
            <i class="fa-solid fa-magnifying-glass"></i>
            Check Weather
        </button>
    </form>

    <!-- Footer -->
    <p class="footer">
        Powered by OpenWeatherMap API
    </p>

</div>

<!-- JS (only for country autofill, no clock logic here) -->
<script src="${pageContext.request.contextPath}/js/Script.js"></script>

</body>
</html>
