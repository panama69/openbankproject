package net.mf;

import com.hp.lft.sdk.internal.common.MessageFieldNames;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.hp.lft.sdk.*;
import com.hp.lft.sdk.web.*;
import com.hp.lft.report.*;
import com.hp.lft.verifications.*;

import unittesting.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.*;
import java.util.UUID;

public class LeanFtTest extends UnitTestClassBase {
    private static String BASE_URL = "http://nimbusserver.aos.com:7080";
    private static String USERNAME = "corndog";
    private static String PASSWORD = "Password12!";
    private static String CONSUMER_KEY = "mneaqpzmjgc52lkpp1bypd3ooxntmz1g44eerv0g";

    public LeanFtTest() {
        //Change this constructor to private if you supply your own public constructor
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        instance = new LeanFtTest();
        globalSetup(LeanFtTest.class);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        globalTearDown();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void validateAccountCreation() throws GeneralLeanFtException, JSONException, ReportException {

        String runLocal;
        Browser browser;
        String tags[] = {"flynn","corndog","gartner",""};

        // To execute the test as a local SRF test, execute the maven project using the following:
        // clean test -DrunLocal=true
        runLocal = System.getProperty("runLocal");

        if (runLocal != null && runLocal.contentEquals("true")){
            browser = BrowserFactory.launch(BrowserType.CHROME);
            Reporter.reportEvent("Executed Locally","Executed this test locally");
        }else {
            BrowserDescription bd = new BrowserDescription();
            //bd.setType(BrowserType.INTERNET_EXPLORER); //or: bd.set("type", BrowserType.INTERNET_EXPLORER) or: bd.set("type", "INTERNET_EXPLORER")
            bd.setType(BrowserType.INTERNET_EXPLORER);
            bd.set("tags",tags);
            bd.set("version", "latest");
            bd.set("osType", "Windows");
            bd.set("osVersion", "10");
            bd.set("tunnelName", "flynn_tunnel");
            if (System.getenv("LFTRUNTIME_labs__srf__serverInfo__clientID") != null) {
                tags[3]="remote";
                bd.set("testName", "Open Bank Project - SRF Cloud Execution");
                Reporter.reportEvent("Executed Remotely","Executed this test using the SRF Cloud environments");
            }else{
                tags[3]="cloud";
                bd.set("testName","Open Bank Project - SRF Remote Execution");
                Reporter.reportEvent("Executed in SRF Cloud","Executed this test using the SRF Cloud environments");
            }
            browser = SrfLab.launchBrowser(bd);

        }
        browser.navigate(BASE_URL);

        OpenBankProjectModel appModel = new OpenBankProjectModel(browser);

        System.out.println("Logging in to: "+BASE_URL);
        appModel.openBankProjectHomePage().loginLink().click();
        appModel.openBankProjectLoginPage().usernameEditField().setValue(USERNAME);
        appModel.openBankProjectLoginPage().passwordEditField().setValue(PASSWORD);
        appModel.openBankProjectLoginPage().loginButton().click();

        Verify.contains(USERNAME, appModel.openBankProjectHomePage().logoutLink().getDisplayName());

        // Create account
        String uuid = UUID.randomUUID().toString();
        System.out.println("Create a new account using: "+uuid);
       appModel.openBankProjectHomePage().createBankAccountLink().click();

        appModel.openBankProjectCreateBankAccountPage().BankListBox().select("obp-bank-y-gh");
        appModel.openBankProjectCreateBankAccountPage().DesiredAccountIdEditField().setValue(uuid);
        appModel.openBankProjectCreateBankAccountPage().DesiredCurrencyEditField().setValue("USD");
        appModel.openBankProjectCreateBankAccountPage().DesiredInitialBalanceEditField().setValue("500");
        appModel.openBankProjectCreateBankAccountPage().createAccountButton().click();

        System.out.println("Verify the account was created correctly");
        Reporter.startReportingContext("Validate Creation via API", "Validate the creation of the account utilizing the REST API's");
        String token = authenticate();
        verifyAccount(token, uuid);
        Reporter.endReportingContext();

        System.out.println("Log out");
        appModel.openBankProjectHomePage().logoutLink().click();
        //browser.closeAllTabs();
        browser.close();


    }

    public String authenticate () throws JSONException, ReportException {
        String token = "";
        //http://nimbusserver.aos.com:7080/my/logins/direct
        try {

            URL url = new URL(BASE_URL+"/my/logins/direct");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization","DirectLogin username=\""+USERNAME+
                    "\",password=\""+PASSWORD +
                    "\",consumer_key="+CONSUMER_KEY);

            //String input = "{\"qty\":100,\"name\":\"iPad 4\"}";
            String input = "";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                Reporter.reportEvent("Authentication Failure", "Failed to authenticate.  HTTP code "+conn.getResponseCode(), Status.Failed);
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            JSONObject json ;
            //System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                //System.out.println(output);
                json = new JSONObject(output);
                //https://stackoverflow.com/questions/2591098/how-to-parse-json-in-java
                //String pageName = json.getJSONObject("pageInfo").getString("pageName");
                token = json.getString("token");
                System.out.println ("\tAuthentication Token: "+token);
                Reporter.reportEvent ("Autenticated","Authenticated and returned token: <br>"+token);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }
        return token;
    }

    public JSONObject verifyAccount(String token, String uuid) throws JSONException {
        // http://localhost:8080/RESTfulExample/json/product/get
        JSONObject json = null;

            try {

                URL url = new URL(BASE_URL+"/obp/v3.0.0/my/accounts");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Authorization","DirectLogin token="+token);

                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;
                //System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    //System.out.println(output);
                    json = new JSONObject(output);
                    //System.out.println("Number of accounts: "+json.getJSONArray("accounts").length());
//                    for (int i =0; i<json.getJSONArray("accounts").length(); i++){
//                        System.out.println("Account: "+json.getJSONArray("accounts").getJSONObject(i).getString("id"));
//                    }
                    System.out.println("\tVerify account: "+uuid+ " was created");
                    Verify.contains(uuid, json.toString());
                }

                conn.disconnect();

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }
            return json;
        }
}