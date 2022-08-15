package com.rest.apirest.service;

import com.rest.apirest.domain.model.Contato;
import com.rest.apirest.domain.repository.ContatoRepository;
import com.rest.apirest.service.dto.ContatoDTO;
import com.rest.apirest.service.exception.EmailInvalidoException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    public Contato buscarContatoPorId(Long id){
        return this.contatoRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    public List<Contato> buscarTodos(){
        return this.contatoRepository.findAll();
    }

    public Contato salvar(Contato contato){
        if(contato.getEmail() != null && !contato.getEmail().isEmpty()){
            if(!contato.getEmail().contains("@")){
                throw new EmailInvalidoException();
            }
        }
        return this.contatoRepository.save(contato);
    }

    public void deletar(Long id){
        buscarContatoPorId(id);
        this.contatoRepository.deleteById(id);
    }

    public Contato atualizar(Long id, Contato contato){
        Contato contatoSalvo = buscarContatoPorId(id);
        BeanUtils.copyProperties(contato, contatoSalvo, "id");
        return this.contatoRepository.save(contatoSalvo);
    }

}
