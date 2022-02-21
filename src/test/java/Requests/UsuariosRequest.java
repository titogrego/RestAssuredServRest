package Requests;

import DTO.UsuarioDTO;
import Helpers.BaseTest;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import java.util.Map;

public class UsuariosRequest extends BaseTest {
    private final String PATH_USUARIOS= "/usuarios";
    private final String PATH_USUARIOS_ID = "/usuarios/{_id}";
    public Response GETUsuarios(Map<String, String> queryParam){
        return given()
                .log().all()
                .queryParams(queryParam)
                .when()
                .get(PATH_USUARIOS);
    }

    public Response GETUsuariosId(Map<String, String> pathParam){
        return given()
                .log().all()
                .pathParams(pathParam)
                .when()
                .get(PATH_USUARIOS_ID);
    }

    public Response POSTUsuarios(UsuarioDTO body){
        return given()
                .log().all()
                .body(body)
                .when()
                .post(PATH_USUARIOS);
    }

    public Response PUTUsuariosId(UsuarioDTO body,Map<String, String> pathParam){
        return given()
                .log().all()
                .pathParams(pathParam)
                .body(body)
                .when()
                .put(PATH_USUARIOS_ID);
    }

    public Response DELETEUsuariosId(Map<String, String> pathParam){
        return given()
                .log().all()
                .pathParams(pathParam)
                .when()
                .delete(PATH_USUARIOS_ID);
    }
}
