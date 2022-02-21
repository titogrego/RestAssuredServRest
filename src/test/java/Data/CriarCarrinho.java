package Data;

import DTO.CarrinhosDTO;
import DTO.ProdutosDTO;

import java.util.List;

public class CriarCarrinho {
    public static CarrinhosDTO CriarCarrinho(){
        return CarrinhosDTO.builder()
                .produto(ProdutosDTO.builder().idProduto("BeeJh5lz3k6kSIzA").quantidade(1).build())
                .produto(ProdutosDTO.builder().idProduto("K6leHdftCeOJj8BJ").quantidade(1).build())
                .build();
    }

    public static CarrinhosDTO CriarCarrinho(List<ProdutosDTO> produtos){
        return CarrinhosDTO.builder()
                .produtos(produtos)
                .build();
    }
}
