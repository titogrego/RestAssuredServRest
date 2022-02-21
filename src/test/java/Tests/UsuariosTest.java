package Tests;



import DTO.UsuarioDTO;
import Data.CriarUsuario;
import Helpers.BaseTest;
import Requests.UsuariosRequest;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.hamcrest.Matchers.*;

public class UsuariosTest extends BaseTest {
    UsuariosRequest request = new UsuariosRequest();
    Map<String,String> pathUsuario = new HashMap<>();
    UsuarioDTO usuarioDTO = CriarUsuario.GerarUsuarioAleatorio();
    //https://www.jsonschema.net/home
    @Test(priority = 0, description = "Validar o retorno da API")
    public void ValidarRetornoDeTodosOsUsuario() {
        Map<String,String> query = new HashMap<>();
        Response response = request.GETUsuarios(query);
        response.then().log().all()
                .statusCode(200)
                .body("quantidade",is(instanceOf(Integer.class)))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("GetUsuarios.json"))
                ;
    }

    @Test(priority = 0, description = "Validar o retorno da API")
    public void ValidarRetornoDeUsuarioComTodosOsFiltros() {
        Map<String,String> query = new HashMap<>();
        query.put("_id", "0uxuPY0cbmQhpEz1");
        query.put("nome","Fulano da Silva");
        query.put("email","fulano@qa.com");
        query.put("password","teste");
        query.put("administrador","true");
        Response response = request.GETUsuarios(query);
        response.then().log().all()
                .statusCode(200)
        ;
    }

    @Test(priority = 0, description = "Validar o retorno da API")
    public void ValidarRetornoDeUsuarioComFiltrosInvalidos() {
        Map<String,String> query = new HashMap<>();
        String filtro = "teste"+faker.number().digits(8);
        query.put(filtro, "0uxuPY0cbmQhpEz1");
        Response response = request.GETUsuarios(query);
        response.then().log().all()
                .statusCode(400)
                .body(filtro,is(filtro+" não é permitido"))
        ;
    }

    @Test(priority = 0, description = "Validar o retorno da API")
    public void ValidarRetornoDeUsuarioComFiltrosNomeInexistente() {
        Map<String,String> query = new HashMap<>();
        String filtro = "teste"+faker.number().digits(8);
        query.put("nome", filtro);
        Response response = request.GETUsuarios(query);
        response.then().log().all()
                .statusCode(200)
                .body("quantidade",is(0))
                .body("usuarios", is(instanceOf(List.class)))
        ;
    }

    @Test(priority = 0, description = "Validar o retorno da API")
    public void DevoAdicionarUmNovoUsuario() {

        Response response = request.POSTUsuarios(usuarioDTO);
        String id_usuario= response.then().log().all()
                .statusCode(200)
                .body("message",is("Cadastro realizado com sucesso"))
                .body("_id",is(notNullValue()))
               .extract().path("_id")
        ;
        System.out.println("TESTE "+id_usuario);
        pathUsuario.put("_id", id_usuario);
        System.out.println("MAP: " + pathUsuario.get("_id"));
    }

    @Test(priority = 1, description = "Validar o retorno da API",dependsOnMethods = {"DevoAdicionarUmNovoUsuario"})
    public void DevoValidarOsDadosDoUsuarioExistente() {

                request.GETUsuariosId(pathUsuario)
               .then()
               .log().all()
               .statusCode(200)
               .body("nome", is(usuarioDTO.getNome()))
               .body("email", is(usuarioDTO.getEmail()))
               .body("password",is(usuarioDTO.getPassword()))
               .body("administrador",is(usuarioDTO.getAdministrador()))
               .body("_id",is(pathUsuario.get("_id")))
               ;
     }

    @Test(priority = 2, description = "Validar o retorno da API",dependsOnMethods = {"DevoAdicionarUmNovoUsuario"})
    public void DevoAlterarOsDadosDoUsuarioExistente() {

        UsuarioDTO usuarioPut = CriarUsuario.GerarUsuarioAleatorio();
        request.PUTUsuariosId(usuarioPut,pathUsuario)
                .then()
                .log().all()
                .statusCode(200)
                .body("message",is("Registro alterado com sucesso"))
               ;

        request.GETUsuariosId(pathUsuario)
                .then()
                .log().all()
                .statusCode(200)
                .body("nome", is(usuarioPut.getNome()))
                .body("email", is(usuarioPut.getEmail()))
                .body("password",is(usuarioPut.getPassword()))
                .body("administrador",is(usuarioPut.getAdministrador()))
                .body("_id",is(pathUsuario.get("_id")))
        ;
    }

    @Test(priority = 3, description = "Validar o retorno da API",dependsOnMethods = {"DevoAdicionarUmNovoUsuario"})
    public void DevoExcluirUmUsuarioExistente() {

        request.DELETEUsuariosId(pathUsuario)
                .then()
                .log().all()
                .statusCode(200)
                .body("message",is("Registro excluído com sucesso"))
        ;
    }

}

