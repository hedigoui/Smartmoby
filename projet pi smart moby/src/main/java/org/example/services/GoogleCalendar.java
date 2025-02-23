package org.example.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class GoogleCalendar {
    private static final String APPLICATION_NAME = "Google Calendar API Java";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "/client_secret.json"; // Chemin vers votre fichier client_secret.json
    private static final String TOKENS_DIRECTORY_PATH = "/tokens"; // Répertoire pour stocker les tokens
    /*private static final String CALENDAR_ID = /*"398fc7b488eeace3547f855cd05a40b64233feeaff4de8274e70017b2c1da90e@group.calendar.google.com";*/ // Remplacez par votre ID de calendrier

    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);

    private static Calendar getCalendarService() throws IOException, GeneralSecurityException {
        InputStream in = GoogleCalendar.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver.Builder().setPort(8888).build()).authorize("user");

        return new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void AjouterEventInCalendar(String summary, String location, String description, String startDateTime, String timeZone , String endDateTime) throws IOException, GeneralSecurityException {
        Calendar service = getCalendarService();

        Event event = new Event()
                .setSummary(summary)
                .setLocation(location)
                .setDescription(description)
                .setStart(new EventDateTime()
                        .setDateTime(new DateTime(startDateTime))
                        .setTimeZone(timeZone))
                .setEnd(new EventDateTime()  // Assurez-vous d'inclure cette partie
                        .setDateTime(new DateTime(endDateTime))
                        .setTimeZone(timeZone));


        event = service.events().insert(CALENDAR_ID, event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());
    }

    public static void AfficherEventInCalendar() throws IOException, GeneralSecurityException {
        Calendar service = getCalendarService();
        DateTime now = new DateTime(System.currentTimeMillis());

        Events events = service.events().list(CALENDAR_ID)
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();

        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events:");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        }
    }

    public static void main(String... args) throws IOException, GeneralSecurityException {
        AjouterEventInCalendar("Evenement Smart Moby", "Tunis",
                "A chance to hear more about Google's developer products.", "2025-02-22T09:00:00-07:00", "America/Los_Angeles","2025-02-22T09:00:00-07:00");
        AfficherEventInCalendar();
    }
}
