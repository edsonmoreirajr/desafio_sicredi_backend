package com.edsonmoreirajr.votacao.gateway;

import com.edsonmoreirajr.votacao.entity.Associado;
import com.edsonmoreirajr.votacao.entity.enums.EnumStatusAssociado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AssociadoGateway {

    Page<Associado> getAllAssociados(Pageable pageable);

    Associado createOrUpdateAssociado(Associado associado);

    Optional<Associado> getAssociadoById(Long id);

    void deleteAssociado(Long id);

    EnumStatusAssociado getStatusByAssociadoId(Long id);

    Optional<Associado> getAssociadoByCPF(String cpf);

}
