import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> strings = new ArrayList<>();
    String s;

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Please use /add or /search");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    strings.add(parameters[1]);
                    return String.format("Added %s to list", parameters[1]);
                }
            }
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                String searchResult = "";
                if (parameters[0].equals("s")) {
                    for (String s : strings) {
                        if (s.contains(parameters[1])) {
                            searchResult += s + " ";
                        }
                    }
                }
                return String.format("Here are your results: %s", searchResult);
            }
            return "404 Not Found!";
        }
    }
}