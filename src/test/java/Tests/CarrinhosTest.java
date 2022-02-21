package Tests;


import DTO.CarrinhosDTO;
import DTO.ProdutosDTO;
import DTO.UsuarioDTO;
import Data.CriarCarrinho;
import Data.CriarUsuario;
import Helpers.BaseTest;
import Requests.CarrinhosRequest;
import Requests.UsuariosRequest;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class CarrinhosTest extends BaseTest {
    CarrinhosRequest request = new CarrinhosRequest();

    @Test(priority = 0, description = "Validar o retorno da API")
    public void ValidarAdicionarUmCarrinhoSemAutorização() {
        CarrinhosDTO carrinho = CriarCarrinho.CriarCarrinho();
        request.POSTCarrinhos(carrinho,"").then()
                .log().all()
                .statusCode(401)
                .body("message",is("Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"))
                ;
    }

    @Test(priority = 0, description = "Validar o retorno da API")
    public void ValidarAdicionarUmCarrinho() {
        CarrinhosDTO carrinho = CriarCarrinho.CriarCarrinho();
        request.POSTCarrinhos(carrinho,TOKEN).then()
                .log().all()
                .statusCode(201)
                .body("message",is("Cadastro realizado com sucesso"))
                .body("_id",is(instanceOf(String.class)))
        ;
    }

    @Test
    public void ValidarCancelarUmaCompra(){
        request.DELETECancelarCarrinho(TOKEN).then()
                .log().all()
                .statusCode(200)
                ;
    }
}

