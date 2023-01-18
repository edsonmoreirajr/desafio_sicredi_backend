package com.edsonmoreirajr.votacao.mapper;

import com.edsonmoreirajr.votacao.dto.SessaoDto;
import com.edsonmoreirajr.votacao.dto.request.SessaoRequest;
import com.edsonmoreirajr.votacao.entity.Sessao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SessaoMapper {

    SessaoMapper INSTANCE = Mappers.getMapper(SessaoMapper.class);

    @Mapping(target = "pautaId", source = "pauta.id")
    SessaoDto toSessaoDto(Sessao sessao);

    @Mapping(target = "pauta.id", source = "pautaId")
    Sessao toSessao(SessaoRequest sessaoRequest);

    void updateSessaoFromSessaoRequest(SessaoRequest sessaoRequest, @MappingTarget Sessao sessao);

}
