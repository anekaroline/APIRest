package com.rest.apirest.service.dto;


import com.rest.apirest.domain.model.Contato;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContatoDTO {

  private Long id;
  private String nome;
  private String email;
  private String telefone;

  public ContatoDTO(Contato ct) {
  }

  public static List<ContatoDTO> converteParaDTO(List<Contato> contatos){
    List<ContatoDTO> contatosDTO = new ArrayList<>();
    for(Contato ct : contatos) {
      contatosDTO.add(new ContatoDTO(ct));
    }
    return contatosDTO;
  }


}
