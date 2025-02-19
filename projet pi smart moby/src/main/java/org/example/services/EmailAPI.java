package org.example.services;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;

public class EmailAPI {

    private MailjetClient client;

    public EmailAPI() throws MailjetException {
        ClientOptions options = ClientOptions.builder()
                .apiKey("a25fd74d32bd2376110cb3dda263dd84")
                .apiSecretKey("7ea34b9c12a0479080861fdf3be12b34")
                .build();
        this.client = new MailjetClient(options);
    }

    public void sendEmail(String fromEmail, String fromName, String toEmail, String toName, String subject, String textPart, String htmlPart) throws MailjetException {
        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", fromEmail)
                                        .put("Name", fromName))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", toEmail)
                                                .put("Name", toName)))
                                .put(Emailv31.Message.SUBJECT, subject)
                                .put(Emailv31.Message.TEXTPART, textPart)
                                .put(Emailv31.Message.HTMLPART, htmlPart)));

        MailjetResponse response = client.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
    }
}
