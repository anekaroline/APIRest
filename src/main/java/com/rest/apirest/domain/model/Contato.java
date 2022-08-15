package com.rest.apirest.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "contato")
public class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String nome;
    @NotNull
    private String telefone;
    @NotNull
    private String email;
    @Column(name = "data_cadastro", updatable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant dataCadastro;
    @Column(name = "data_atualizacao", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant dataAtualizacao;

    @PrePersist
    public void onInsert() {
        this.dataCadastro = Instant.now();
        this.dataAtualizacao = this.dataCadastro;
    }

    @PreUpdate
    public void onUpdate() {
        this.dataAtualizacao = Instant.now();
    }

}
