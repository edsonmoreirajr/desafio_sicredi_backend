package com.edsonmoreirajr.votacao.entity;

import com.edsonmoreirajr.votacao.entity.enums.EnumStatusPauta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pauta")
public class Pauta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 160)
    @NotNull
    @Column(name = "titulo", nullable = false, length = 160)
    private String titulo;

    @Size(max = 400)
    @NotNull
    @Column(name = "descricao", nullable = false, length = 400)
    private String descricao;

    @Column(name = "status", length = 8)
    @Enumerated(EnumType.STRING)
    private EnumStatusPauta status;

}