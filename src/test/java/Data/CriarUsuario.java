package Data;

import DTO.UsuarioDTO;
import com.github.javafaker.Faker;

public class CriarUsuario {
    static Faker faker = new Faker();
    static String [] ad = {"true", "false"};
    public static UsuarioDTO GerarUsuarioAleatorio(){
        return UsuarioDTO.builder()
                .email("testeAuto"+faker.number().digits(8)+"@teste.com")
                .password(faker.internet().password())
                .nome(faker.name().fullName())
                .administrador(ad[faker.number().numberBetween(0,1)])
                .build();
    }


}
