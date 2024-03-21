package katalon.com.sitemapservice.service.impl;


import katalon.com.sitemapservice.dto.response.Execution;
import katalon.com.sitemapservice.dto.response.ExecutionResponse;
import katalon.com.sitemapservice.model.TestCase;
import katalon.com.sitemapservice.repository.TestCaseRepository;
import katalon.com.sitemapservice.service.SitemapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class JobTestResult {

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private SitemapService sitemapService;

    @Scheduled(cron = "${job.listener.test.result}")
    public void listenerTestResult() {
        log.info("============ listener test result =================", new Date());
        List<TestCase> testCases = testCaseRepository.findAllByStatusAndExecutionIdNotNull("RUNNING");
        if (CollectionUtils.isEmpty(testCases)) {
            return;
        }
        List<Execution> executionList = getTestResult();
        if (!CollectionUtils.isEmpty(executionList)) {
            testCases.forEach(testCase -> {
                executionList.forEach(execution -> {
                    if (testCase.getExecutionId().equals(execution.getId()) && !execution.getStatus().equals("RUNNING")) {
                        testCase.setStatus(execution.getStatus());
                        testCaseRepository.save(testCase);
                        sitemapService.updateStatus(testCase.getUrls(), execution.getStatus(), testCase.getSiteMapId());
                    }
                });
            });
        }
    }

    List<Execution> getTestResult() {
        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Define headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("authority", "testcloud.qa.katalon.com");
        headers.set("accept", "application/json, text/plain, */*");
        headers.set("authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJqWVpYSzBWUHROcDRpaVJzcUlfT3Q4LW56NURCRXNlM0VFd2RHSEZfUjZVIn0.eyJleHAiOjE3MTExMjU3OTEsImlhdCI6MTcxMDk1Mjk5MSwiYXV0aF90aW1lIjoxNzEwOTUyOTg4LCJqdGkiOiI1OTc4OWM1MS0yODNhLTQ1ZjUtOWY0ZS01MDE2M2JiZGViY2EiLCJpc3MiOiJodHRwczovL2xvZ2luLnFhLmthdGFsb24uY29tL3JlYWxtcy9rYXRhbG9uIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6Ijk3OWU1ZjdlLTE4MzYtNDM3Yi1hNGE2LTg1YzBmYmJmYmEzNyIsInR5cCI6IkJlYXJlciIsImF6cCI6ImthdGFsb24tdGVzdG9wcyIsInNlc3Npb25fc3RhdGUiOiI5YzJiMzQ2ZS0wNzQwLTRhYjUtOGFiMy1iYzc4NTFmNDhiNTAiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHBzOi8vdGVzdG9wcy5xYS5rYXRhbG9uLmNvbSIsImh0dHA6Ly9sb2NhbGhvc3Q6ODQ0NCIsImh0dHA6Ly9sb2NhbGhvc3Q6ODQ0MyIsImh0dHBzOi8vdGVzdG9wcy10ZW1wLnFhLmthdGFsb24uY29tIiwiaHR0cDovL2VjMi0zNC0xOTMtMTA4LTIwMi5jb21wdXRlLTEuYW1hem9uYXdzLmNvbTo4MDgwIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJ1bWFfYXV0aG9yaXphdGlvbiIsImRlZmF1bHQtcm9sZXMta2F0YWxvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIHByb2ZpbGUgZW1haWwiLCJzaWQiOiI5YzJiMzQ2ZS0wNzQwLTRhYjUtOGFiMy1iYzc4NTFmNDhiNTAiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwidXBkYXRlZF90aW1lc3RhbXAiOjE2OTMyODM0OTI2MDIsImxhc3RfbG9naW4iOjE3MTA5NTI5ODgwOTYsIm5hbWUiOiJMZSBIdW5nIFNvbiIsImNyZWF0ZWRfdGltZXN0YW1wIjoxNjg1NjkwNDE2MDkyLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzb24ubGVAa2F0YWxvbi5jb20iLCJnaXZlbl9uYW1lIjoiTGUiLCJmYW1pbHlfbmFtZSI6Ikh1bmcgU29uIiwiZW1haWwiOiJzb24ubGVAa2F0YWxvbi5jb20ifQ.JQ3DFzZFGb4banAEw4KWS8QxANQGyVnTADCKmec-eM8z6YTule27Uhqb8SEDZvmwn3zD4kSLBZM53Q8peq6TopiP9xCXjGFDZHfLusS1AJlcEqjVcED_ogF4mCwghX0dkZx1efTpPnL2QyZW2GmdkuWAESI2OnXDObtgT79LhNPrA-YJK0ZYYfXvI_4K_KwTe5XO0HbdxpDLWrMPR1Z7V7rje42sBRP79aZ7KAlhmAJrvmLVw0XoOfHmyxStohuj-nhhJUVqpkfMffIEMOECrIss0KCvLbTIc1zPJ0XFPC5KI_YBs0QXQ5dtrIdQt5ASDuDWSNA0OeMH2j21j4hdLw");

        // Create HttpEntity with headers
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Make the GET request
        ResponseEntity<ExecutionResponse> response = restTemplate.exchange(
                "https://testcloud.qa.katalon.com/ees/executions?q=Project.id==8166&pageToken=0&size=30&sort=-id",
                HttpMethod.GET,
                requestEntity,
                ExecutionResponse.class);

        // Handle the response
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().getContent();
        } else {
            System.err.println("Request failed with status code: " + response.getStatusCodeValue());
        }
        return null;
    }


}