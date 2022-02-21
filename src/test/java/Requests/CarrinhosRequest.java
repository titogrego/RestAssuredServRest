package Requests;

import DTO.CarrinhosDTO;
import DTO.UsuarioDTO;
import Helpers.BaseTest;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class CarrinhosRequest extends BaseTest {
    private final String PATH_CARRINHOS= "/carrinhos";
    private final String PATH_CANCELAR_CARRINHO = "carrinhos/cancelar-compra";

    public Response POSTCarrinhos(CarrinhosDTO body, String token){
        return given()
                .log().all()
                .header("Authorization",token)
                .body(body)
                .when()
                .post(PATH_CARRINHOS);
    }

    public Response DELETECancelarCarrinho(String token){
        return given()
                .log().all()
                .header("Authorization",token)
                .when()
                .delete(PATH_CANCELAR_CARRINHO);
    }

}
