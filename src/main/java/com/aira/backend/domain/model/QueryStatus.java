package com.aira.backend.domain.model;

public enum QueryStatus {

    SUCCESS,
    // Everything worked as expected — we got the weather data, and everyone's happy!

    ERROR,
    // Something unexpected went wrong — maybe the server was down or there was a network issue.

    TIMEOUT,
    // The request took too long. Could be network latency, slow API, or just a bad day.

    INVALID_CITY,
    // The user entered a city name that couldn’t be found — time to suggest better input UX?

    API_LIMIT
    // We've reached the daily/monthly request limit from OpenWeather.
    // Maybe time to cache more or upgrade our plan?
}