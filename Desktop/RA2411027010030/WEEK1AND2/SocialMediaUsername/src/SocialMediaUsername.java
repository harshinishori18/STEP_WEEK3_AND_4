import java.util.*;

public class SocialMediaUsername {

    // username -> userId
    HashMap<String, Integer> users = new HashMap<>();

    // username -> attempt frequency
    HashMap<String, Integer> attempts = new HashMap<>();


    // Check availability
    public boolean checkAvailability(String username) {

        // track attempts
        attempts.put(username, attempts.getOrDefault(username, 0) + 1);

        return !users.containsKey(username);
    }


    // Register user
    public void registerUser(String username, int userId) {

        if (checkAvailability(username)) {
            users.put(username, userId);
            System.out.println(username + " registered successfully.");
        } else {
            System.out.println(username + " is already taken.");
        }
    }


    // Suggest alternative usernames
    public List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        suggestions.add(username + "1");
        suggestions.add(username + "2");
        suggestions.add(username.replace("_", "."));
        suggestions.add(username + "_official");

        return suggestions;
    }


    // Get most attempted username
    public String getMostAttempted() {

        String most = "";
        int max = 0;

        for (Map.Entry<String, Integer> entry : attempts.entrySet()) {

            if (entry.getValue() > max) {
                max = entry.getValue();
                most = entry.getKey();
            }
        }

        return most + " (" + max + " attempts)";
    }


    // Main method
    public static void main(String[] args) {

        SocialMediaUsername system = new SocialMediaUsername();

        // existing users
        system.registerUser("john_doe", 101);
        system.registerUser("admin", 102);

        // check availability
        System.out.println("john_doe available? " + system.checkAvailability("john_doe"));
        System.out.println("jane_smith available? " + system.checkAvailability("jane_smith"));

        // suggestions
        System.out.println("Suggestions: " + system.suggestAlternatives("john_doe"));

        // simulate attempts
        system.checkAvailability("admin");
        system.checkAvailability("admin");
        system.checkAvailability("admin");

        // most attempted
        System.out.println("Most attempted username: " + system.getMostAttempted());
    }
}
