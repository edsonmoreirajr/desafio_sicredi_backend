package com.edsonmoreirajr.votacao.repository;

import com.edsonmoreirajr.votacao.entity.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PautaRepository extends JpaRepository<Pauta, Long>, PautaRepositoryCustom {

}
