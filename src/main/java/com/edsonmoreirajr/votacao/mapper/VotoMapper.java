package com.edsonmoreirajr.votacao.mapper;

import com.edsonmoreirajr.votacao.dto.TotalVotosDto;
import com.edsonmoreirajr.votacao.dto.VotoDto;
import com.edsonmoreirajr.votacao.dto.request.VotoRequest;
import com.edsonmoreirajr.votacao.entity.Voto;
import jakarta.persistence.Tuple;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VotoMapper {

    VotoMapper INSTANCE = Mappers.getMapper(VotoMapper.class);

    @Mapping(target = "associado.id", source = "associadoId")
    @Mapping(target = "sessao.id", source = "sessaoId")
    Voto toVoto(VotoRequest voto);

    @Mapping(target = "sessaoId", source = "sessao.id")
    VotoDto toVotoDto(Voto voto);

    default TotalVotosDto tupleToTotalVotosDto(Tuple tuple) {
        return TotalVotosDto.builder()
                .pautaId(tuple.get("pauta_id", Long.class))
                .titulo(tuple.get("pauta_titulo", String.class))
                .status(tuple.get("status_pauta", String.class))
                .totalVotosSim(tuple.get("total_votos_sim", Long.class))
                .totalVotosNao(tuple.get("total_votos_nao", Long.class))
                .totalTodosVotos(tuple.get("total_todos_votos", Long.class))
                .build();
    }
}
