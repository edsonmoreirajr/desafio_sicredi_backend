package com.edsonmoreirajr.votacao.gateway;

import com.edsonmoreirajr.votacao.entity.Associado;
import com.edsonmoreirajr.votacao.entity.enums.StatusAssociadoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AssociadoGateway {

    Page<Associado> getAllAssociados(Pageable pageable);

    Associado createOrUpdateAssociado(Associado associado);

    Optional<Associado> getAssociadoById(Long id);

    void deleteAssociado(Long id);

    StatusAssociadoEnum getStatusByAssociadoId(Long id);

    Optional<Associado> getAssociadoByCPF(String cpf);

}
