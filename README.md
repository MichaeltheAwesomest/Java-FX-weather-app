# JavaFX Weather Dashboard

A lightweight desktop weather application built with **Java** and **JavaFX**, featuring automatic IP geolocation, live weather data from OpenWeather, light/dark CSS themes, and animated weather visuals.

## Features
- **Automatic IP Geolocation:** Uses `ipapi.com` to resolve coordinates dynamically at runtime and falls back to current location when an invalid city name is entered.
- **Live Weather & Forecast Telemetry:** Queries the OpenWeather API for current conditions, hourly/daily forecasts, sunrise/sunset times, and weather metrics (temperature, humidity, wind speed, pressure, UV).
- **Light & Dark Themes + Dynamic Visuals:** Supports light (`application.css`) and dark (`application_dark.css`) stylesheets, animated weather GIFs with caching to prevent JVM memory bloat, and scene root swapping to avoid window resizing glitches.
- **Modular JavaFX Architecture:** Separates API clients, JSON response models, and UI components into dedicated classes.

## Tech Stack
- **Language:** Java
- **UI Framework:** JavaFX + Custom CSS (`application.css`, `application_dark.css`)
- **JSON Parsing:** Gson (`gson-2.14.0.jar`)
- **APIs:** OpenWeather API, ipapi.com
- **IDE / Build Config:** Eclipse (`build.fxbuild`, `.classpath`, `.project`)

## Screenshots / Demo
<!-- Drag and drop your .mp4 clip or screenshot directly into the GitHub web editor here -->

## Running Locally
1. Clone the repository:
   ```bash
   git clone [https://github.com/MichaeltheAwesomest/JavaFxWeatherDashBoardApp.git](https://github.com/MichaeltheAwesomest/JavaFxWeatherDashBoardApp.git)