package Helpers;


import DTO.LoginDTO;
import Requests.LoginRequest;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;

import io.restassured.response.Response;
import org.apache.commons.io.output.WriterOutputStream;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Locale;


public class BaseTest{


    public static Faker faker = new Faker(new Locale("pt-BR"));
    public StringWriter requestWriter;
    public PrintStream requestCapture;
    public StringWriter responseWriter;
    public PrintStream responseCapture;
    String baseUrlLocal = "http://localhost:3000/";
    String getBaseUrlDev = "https://serverest.dev/";
    ContentType APP_CONTENT_TYPE = ContentType.JSON;
    public String TOKEN;

    @BeforeTest
    public void setUp() {

            String ambiente = System.getProperty("customerCode");
        if (ambiente == null) {

            RestAssured.baseURI = baseUrlLocal;
        } else {
            if (ambiente.equals("LOCAL")) {
                RestAssured.baseURI = baseUrlLocal;
            }
            if (ambiente.equals("DEV")) {
                RestAssured.baseURI = getBaseUrlDev;
            }
        }
            LoginRequest loginRequest = new LoginRequest();
            RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
            reqBuilder.setContentType(APP_CONTENT_TYPE);
            RestAssured.requestSpecification = reqBuilder.build();
            ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
            RestAssured.responseSpecification = resBuilder.build();
            LoginDTO login = LoginDTO.builder().email("fulano@qa.com").password("teste").build();
            Response loginResponse = loginRequest.PostLogin(login);
            TOKEN = loginResponse.then().extract().path("authorization");

    }

    @BeforeMethod
    public void initLog(){
        requestWriter = new StringWriter();
        responseWriter = new StringWriter();
        requestCapture = new PrintStream(new WriterOutputStream(requestWriter));
        responseCapture = new PrintStream(new WriterOutputStream(responseWriter));
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.addFilter((new RequestLoggingFilter(requestCapture)));
        reqBuilder.addFilter((new ResponseLoggingFilter(responseCapture)));
        RestAssured.requestSpecification = reqBuilder.build();


    }

    @AfterMethod
    public void addLog(){
        requestCapture.flush();
        responseCapture.flush();
        Listener.insertLogToReport("Request: ", requestWriter.toString() );
        Listener.insertLogToReport("Response: ",responseWriter.toString());
    }
}
