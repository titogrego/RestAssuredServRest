package Requests;

import static io.restassured.RestAssured.*;

import DTO.LoginDTO;
import Helpers.BaseTest;
import io.restassured.response.Response;

public class LoginRequest extends BaseTest {

    public Response PostLogin(LoginDTO login){
        return given()
                .body(login)
                .when()
                .post("login")

                ;
    }
}
