package katalon.com.sitemapservice.utils;


import java.util.List;

public class PromptGenerator {
    public static String generatePrompt(List<String> urls) {
        String prompt = """
                For the given list of URLs: %s
                The output template:
                WebUI.navigateToUrl('https://google.com')
                WebUI.comment("Test https://google.com")
                WebUI.takeScreenshot()
                the generated output based on the given list of URLs using the provided template
                """;
        prompt = String.format(prompt,urls);
        return prompt;
    }
}
