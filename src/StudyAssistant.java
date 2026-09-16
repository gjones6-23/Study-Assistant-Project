import java.util.Scanner;
import java.net.URI;
import java.net.http.HttpClient;
import java .net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class StudyAssistant {

    public static void main(String[] args){

        // Get API key
        String apiKey = System.getenv("OPENAI_API_KEY");

       // Check API key
       if (apiKey == null || apiKey.isEmpty()) {
           System.out.println("API key not found.");
           return;
       }

       // Create HTTP client
       HttpClient client = HttpClient.newHttpClient();

        Scanner scanner = new Scanner(System.in);

        // Ask user for notes
        System.out.println("====================================");
        System.out.println("        AI STUDY ASSISTANT          ");
        System.out.println("====================================");
        System.out.println();

        System.out.println("What would you like to create?");
        System.out.println("1. Study Guide");
        System.out.println("2. Flashcards");
        System.out.println("3. Both");

        // Verifies user enters a number 1-3
        int choice;

        while (true) {
            System.out.print("Enter your choice (1-3): ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();

                if (choice >= 1 && choice <= 3) {
                    break;
                } else {
                    System.out.println("Please enter 1, 2, or 3.");
                }
            } else {
                System.out.println("Please enter a number.");
                scanner.nextLine();
            }
        }

        System.out.println("Paste your lecture notes below.");
        System.out.println("Type END on a new line when finished.");
        System.out.println();

        StringBuilder notes = new StringBuilder();

        while(true) {
            String line = scanner.nextLine();

            if(line.equals("END")){
                break;
            }

            notes.append(line).append("\n");
        }

        // Convert the notes into a string
        String userNotes = notes.toString();

        // Tells user the AI is working
        System.out.println();
        System.out.println("Generating your study guide...");
        System.out.println();

        // Creates options for user's method of studying
        String instruction;

        if (choice ==1) {
            instruction = "Create a concise study guide from these lecture notes, including a summary, key concepts, and practice questions.";
        } else if (choice == 2) {
            instruction = "Create study flashcards from these lecture notes. Include important terms and concepts as questions and answers.";
        } else if (choice == 3) {
            instruction = "Create both a concise study guide and study flashcards from the lecture notes. Include a summary, key concepts, practice questions, and important terms as question-and-answer flashcards.";
        } else {
            instruction = "Create a concise study guide from these lecture notes, including a summary, key concepts, and practice questions.";
        }

        // Tells java what title to use based on user's selection
        String outputTitle;

                if (choice == 1) {
                    outputTitle = "STUDY GUIDE";
                } else if (choice == 2) {
                    outputTitle = "FLASHCARDS";
                } else {
                    outputTitle = "STUDY GUIDE + FLASHCARDS";
                }

        // Create the API request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/responses"))
                .header("Content-Type","application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString("{\"model\":\"gpt-5.4-mini\", \"input\" : \"" + escapeJson(instruction) + "\\n\\nLecture notes:\\n" + escapeJson(userNotes) + "\"}"
                ))
                .build();

        // Send the request
        try {
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

            String studyGuide = jsonResponse
                    .getAsJsonArray("output")
                    .get(0)
                    .getAsJsonObject()
                    .getAsJsonArray("content")
                    .get(0)
                    .getAsJsonObject()
                    .get("text")
                    .getAsString();

           // Title
            System.out.println("================================");
            System.out.println("     " + outputTitle);
            System.out.println("================================");
            System.out.println();

            System.out.println(studyGuide);

        } catch (Exception e) {
            System.out.println("Something went wrong:" + e.getMessage());
        }

        // Print user's notes
        System.out.println("\n=== Your Notes ===");
        System.out.println(notes);

        scanner.close();
    }

    public static String escapeJson(String text) {
        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}