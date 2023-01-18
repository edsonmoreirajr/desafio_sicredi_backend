package com.edsonmoreirajr.votacao.mapper;

import com.edsonmoreirajr.votacao.dto.PautaDto;
import com.edsonmoreirajr.votacao.dto.request.PautaRequest;
import com.edsonmoreirajr.votacao.entity.Pauta;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PautaMapper {

    PautaMapper INSTANCE = Mappers.getMapper(PautaMapper.class);

    PautaDto toPautaDto(Pauta associado);

    Pauta toPauta(PautaRequest pautaRequest);

    void updatePautaFromPautaRequest(PautaRequest pautaRequest, @MappingTarget Pauta pauta);

}
