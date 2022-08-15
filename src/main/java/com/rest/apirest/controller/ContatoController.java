package com.rest.apirest.controller;

import com.rest.apirest.domain.exceptionhandler.APIRestExceptionHandler;
import com.rest.apirest.domain.model.Contato;
import com.rest.apirest.domain.repository.ContatoRepository;
import com.rest.apirest.event.RecursoCriadoEvent;
import com.rest.apirest.service.ContatoService;
import com.rest.apirest.service.exception.EmailInvalidoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contatos")
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Contato> buscarTodos(){
        return contatoService.buscarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contato> buscarPorId(@PathVariable Long id){
        Contato contato = contatoService.buscarContatoPorId(id);
        return ResponseEntity.ok(contato);
    }

    @PostMapping
    public ResponseEntity<Contato> adicionar(@Valid @RequestBody Contato contato, HttpServletResponse response){
        Contato contatoSalvo = contatoService.salvar(contato);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, contatoSalvo.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(contatoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contato> atualizar(@PathVariable Long id, @RequestBody Contato contato){
       Contato contatoSalvo = contatoService.atualizar(id, contato);
       return ResponseEntity.ok(contatoSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Contato> deletar(@PathVariable Long id){
        contatoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({EmailInvalidoException.class})
    public ResponseEntity<Object> handleEmailInvalidoException(EmailInvalidoException ex){
        String mensagemUsuario = "Email inválido";
        String mensagemDesenvolvedor = ex.toString();
        List<APIRestExceptionHandler.Erro> erros = List.of(new APIRestExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));
        return ResponseEntity.badRequest().body(erros);
    }

}
