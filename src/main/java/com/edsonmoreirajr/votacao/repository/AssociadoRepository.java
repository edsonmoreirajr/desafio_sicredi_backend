package com.edsonmoreirajr.votacao.repository;

import com.edsonmoreirajr.votacao.entity.Associado;
import com.edsonmoreirajr.votacao.entity.enums.StatusAssociadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    @Query("SELECT status FROM Associado WHERE id = :id")
    StatusAssociadoEnum findStatusByAssociadoId(Long id);

    Optional<Associado> findByCpf(String cpf);
}
