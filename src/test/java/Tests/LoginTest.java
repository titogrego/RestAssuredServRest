package Tests;


import DTO.LoginDTO;
import Helpers.BaseTest;
import Requests.LoginRequest;
import Requests.UsuariosRequest;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class LoginTest extends BaseTest {
    LoginRequest request = new LoginRequest();

    @Test(priority = 0, description = "Validar o retorno da API")
    public void DevoRealizarLoginCorretamente() {
        Response response = request.PostLogin(LoginDTO.builder().email("fulano@qa.com").password("teste").build());
        response.then().log().all()
                .statusCode(200)
                .body("message", is("Login realizado com sucesso"))
                .body("authorization",is(instanceOf(String.class)))
                ;
    }

    @Test(priority = 0, description = "Validar o retorno da API")
    public void DevoRealizarLoginSemDados() {

        Response response = request.PostLogin(new LoginDTO());
        response.then().log().all()
                .statusCode(400)
                .body("email", is("email deve ser uma string"))
                .body("password", is("password deve ser uma string"))
        ;
    }
    @Test(priority = 0, description = "Validar o retorno da API")
    public void DevoRealizarLoginComEmailInexistente() {
        Response response = request.PostLogin(LoginDTO.builder().email(faker.number().digits(8)+"@qa.com").password("teste").build());
        response.then().log().all()
                .statusCode(401)
                .body("message", is("Email e/ou senha inválidos"))
                ;
    }
    @Test(priority = 0, description = "Validar o retorno da API")
    public void DevoRealizarLoginComSenhaEmBranco() {
        Response response = request.PostLogin(LoginDTO.builder().email("fulano@qa.com").password("").build());
        response.then().log().all()
                .statusCode(400)
                .body("password", is("password não pode ficar em branco"))
        ;
    }

    @Test(priority = 0, description = "Validar o retorno da API")
    public void DevoRealizarLoginComEmailEmBranco() {

        Response response = request.PostLogin(LoginDTO.builder().email("").password("teste").build());
        response.then().log().all()
                .statusCode(400)
                .body("email", is("email não pode ficar em branco"))
        ;
    }
}

