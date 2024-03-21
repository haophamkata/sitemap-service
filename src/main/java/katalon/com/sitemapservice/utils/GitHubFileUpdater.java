package katalon.com.sitemapservice.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class GitHubFileUpdater {

    public static String accessToken = "";
    public static String repoFullName = "sonlekatalon/abcd-test-project";
    public static String filePath = "Scripts/TestSiteMap/Script1710934419690.groovy";
    public static final String GITHUB_API_BASE_URL = "https://api.github.com/repos/";
    //public static String newContent = "// Test Case 1: Google\nWebUI.openBrowser('');WebUI.navigateToUrl('https://google.com')\nWebUI.comment('Test Google')\nWebUI.takeScreenshot()\n\n// Test Case 2: VNExpress\nWebUI.navigateToUrl('https://vnexpress.net')\nWebUI.comment('Test VNExpress')\nWebUI.takeScreenshot()";

    public static String getFileSha(String repoFullName, String filePath, String accessToken) throws Exception {
        URL url = new URL(GITHUB_API_BASE_URL + repoFullName + "/contents/" + filePath);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "token " + accessToken);
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            JsonObject jsonObject = JsonParser.parseReader(in).getAsJsonObject();
            return jsonObject.get("sha").getAsString();
        }
    }

    public static void commitFileContent(String repoFullName, String filePath, String accessToken, String modifiedContent) {
        try {
            String fileSha = getFileSha(repoFullName, filePath, accessToken);
            String encodedModifiedContent = Base64.getEncoder().encodeToString(modifiedContent.getBytes());
            String payload = "{\"message\":\"Updated test case :)\",\"content\":\"" + encodedModifiedContent + "\",\"sha\":\"" + fileSha + "\"}";

            URL url = new URL(GITHUB_API_BASE_URL + repoFullName + "/contents/" + filePath);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Authorization", "token " + accessToken);
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(payload);
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("File updated successfully!");
            } else {
                System.out.println("Failed to update file. Response code: " + responseCode);
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    System.out.println("Response body: " + response.toString());
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while committing file content: " + e.getMessage());
        }
    }

    public static String getFileContent(String repoFullName, String filePath, String accessToken) throws Exception {
        URL url = new URL(GITHUB_API_BASE_URL + repoFullName + "/contents/" + filePath);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "token " + accessToken);
        connection.setRequestProperty("Accept", "application/vnd.github.v3.raw");
        StringBuilder content = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine).append('\n');
            }
        }
        connection.disconnect();
        return content.toString();
    }

    public static String updateFileContent(String currentContent, String newContent) throws Exception {
        String beginMarker = "// BEGIN TEST";
        String endMarker = "// END TEST";
        int beginIndex = currentContent.indexOf(beginMarker);
        int endIndex = currentContent.indexOf(endMarker);

        if (beginIndex != -1 && endIndex != -1) {
            String oldContent = currentContent.substring(beginIndex + beginMarker.length(), endIndex);
            return currentContent.replace(oldContent, '\n' + newContent + '\n');
        } else {
            System.out.println("Begin or end marker not found.");
            return currentContent;
        }
    }

    /**
     * Update and commit file content
     * @param newContent new content to be updated
     *                   The format of newContent should be like this:
     *                   // Test Case 1: Google
     *                   WebUI.openBrowser('');
     *                   WebUI.navigateToUrl('https://google.com')
     *                   WebUI.comment('Test Google')
     *                   WebUI.takeScreenshot()
     *                   // Test Case 2: VNExpress
     *                   WebUI.navigateToUrl('https://vnexpress.net')
     *                   WebUI.comment('Test VNExpress')
     *                   WebUI.takeScreenshot()
     *                   ...
     * @throws Exception
     */


    public static void updateAndCommitFileContent(String newContent) {
        try {
            String content = getFileContent(repoFullName, filePath, accessToken);
            System.out.println("Current content:\n" + content);
            String modifiedContent = updateFileContent(content, newContent);
            System.out.println("Modified content:\n" + modifiedContent);
            commitFileContent(repoFullName, filePath, accessToken, modifiedContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
