// OpenAIClient.java
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class OpenAIClient {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "your-openai-api-key"; // Replace with your API key

    public String generateQuestions(String prompt) {
        try {
            // Set up connection to OpenAI API
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setDoOutput(true);

            // Create the request body
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "gpt-3.5-turbo");
            requestBody.put("messages", new JSONObject[]{
                    new JSONObject() {{
                        put("role", "system");
                        put("content", "You are a helpful assistant that generates food and nutrition-related questions.");
                    }},
                    new JSONObject() {{
                        put("role", "user");
                        put("content", prompt);
                    }}
            });

            // Write the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response from the API
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder result = new StringBuilder();
            while (scanner.hasNextLine()) {
                result.append(scanner.nextLine());
            }
            scanner.close();

            // Parse the response
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(result.toString());
            JSONObject choice = (JSONObject) ((org.json.simple.JSONArray) json.get("choices")).get(0);
            JSONObject message = (JSONObject) choice.get("message");
            return (String) message.get("content");

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unable to get response from OpenAI.";
        }
    }
}
