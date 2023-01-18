package com.edsonmoreirajr.votacao.mapper;

import com.edsonmoreirajr.votacao.dto.AssociadoDto;
import com.edsonmoreirajr.votacao.dto.request.AssociadoRequest;
import com.edsonmoreirajr.votacao.entity.Associado;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AssociadoMapper {

    AssociadoMapper INSTANCE = Mappers.getMapper(AssociadoMapper.class);

    AssociadoDto toAssociadoDto(Associado associado);

    Associado toAssociado(AssociadoRequest associadoRequest);

    void updateAssociadoFromAssociadoRequest(AssociadoRequest associadoRequest, @MappingTarget Associado associado);

}
