package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestListener implements ITestListener {
    private static final String EVIDENCE_DIR = System.getProperty("user.dir") + File.separator + "test-reports" + File.separator + "evidence" + File.separator;

    @Override
    public void onStart(ITestContext context) {
        // Create evidence directory
        File evidenceDir = new File(EVIDENCE_DIR);
        if (!evidenceDir.exists()) {
            evidenceDir.mkdirs();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String className = result.getTestClass().getName();
        String description = result.getMethod().getDescription();
        if (description == null || description.isEmpty()) {
            description = "Test: " + testName;
        }
        ReportManager.createTest(testName, description);
        ReportManager.getTest().assignCategory(className);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = ReportManager.getTest();
        if (test != null) {
            test.log(Status.PASS, MarkupHelper.createLabel("Test Passed", ExtentColor.GREEN));
            logTestDetails(result, "PASSED");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = ReportManager.getTest();
        if (test != null) {
            test.log(Status.FAIL, MarkupHelper.createLabel("Test Failed", ExtentColor.RED));
            test.log(Status.FAIL, result.getThrowable());
            logTestDetails(result, "FAILED");
            
            // Save failure details to file
            saveFailureEvidence(result);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = ReportManager.getTest();
        if (test != null) {
            test.log(Status.SKIP, MarkupHelper.createLabel("Test Skipped", ExtentColor.YELLOW));
            logTestDetails(result, "SKIPPED");
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        ReportManager.flush();
    }

    private void logTestDetails(ITestResult result, String status) {
        ExtentTest test = ReportManager.getTest();
        if (test != null) {
            test.info("<b>Test Class:</b> " + result.getTestClass().getName());
            test.info("<b>Test Method:</b> " + result.getMethod().getMethodName());
            test.info("<b>Status:</b> " + status);
            test.info("<b>Execution Time:</b> " + (result.getEndMillis() - result.getStartMillis()) + " ms");
            
            // Log parameters if any
            Object[] parameters = result.getParameters();
            if (parameters != null && parameters.length > 0) {
                test.info("<b>Parameters:</b> " + java.util.Arrays.toString(parameters));
            }
        }
    }

    private void saveFailureEvidence(ITestResult result) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String timestamp = LocalDateTime.now().format(formatter);
            String fileName = result.getMethod().getMethodName() + "_" + timestamp + ".txt";
            File evidenceFile = new File(EVIDENCE_DIR + fileName);
            
            try (FileWriter writer = new FileWriter(evidenceFile)) {
                writer.write("Test Failure Evidence\n");
                writer.write("====================\n\n");
                writer.write("Test Class: " + result.getTestClass().getName() + "\n");
                writer.write("Test Method: " + result.getMethod().getMethodName() + "\n");
                writer.write("Status: FAILED\n");
                writer.write("Timestamp: " + timestamp + "\n\n");
                
                if (result.getThrowable() != null) {
                    writer.write("Exception:\n");
                    writer.write(result.getThrowable().getMessage() + "\n\n");
                    writer.write("Stack Trace:\n");
                    for (StackTraceElement element : result.getThrowable().getStackTrace()) {
                        writer.write(element.toString() + "\n");
                    }
                }
                
                writer.write("\nTest Parameters:\n");
                Object[] parameters = result.getParameters();
                if (parameters != null && parameters.length > 0) {
                    for (int i = 0; i < parameters.length; i++) {
                        writer.write("Parameter " + i + ": " + parameters[i] + "\n");
                    }
                } else {
                    writer.write("No parameters\n");
                }
            }
            
            ExtentTest test = ReportManager.getTest();
            if (test != null) {
                test.info("Failure evidence saved to: " + evidenceFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Failed to save evidence: " + e.getMessage());
        }
    }

    public static void logResponse(Response response) {
        ExtentTest test = ReportManager.getTest();
        if (test != null && response != null) {
            test.info("<b>Response Status Code:</b> " + response.getStatusCode());
            test.info("<b>Response Time:</b> " + response.getTime() + " ms");
            
            String responseBody = response.getBody().asPrettyString();
            if (responseBody.length() > 1000) {
                responseBody = responseBody.substring(0, 1000) + "... (truncated)";
            }
            test.info("<details><summary><b>Response Body</b></summary><pre>" + 
                     responseBody.replace("<", "&lt;").replace(">", "&gt;") + 
                     "</pre></details>");
        }
    }

    public static void logRequest(String method, String url, Object queryParams) {
        ExtentTest test = ReportManager.getTest();
        if (test != null) {
            test.info("<b>Request Method:</b> " + method);
            test.info("<b>Request URL:</b> " + url);
            if (queryParams != null) {
                test.info("<b>Query Parameters:</b> " + queryParams.toString());
            }
        }
    }
}
