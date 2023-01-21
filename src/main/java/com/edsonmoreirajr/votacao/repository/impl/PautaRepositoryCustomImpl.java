package com.edsonmoreirajr.votacao.repository.impl;

import com.edsonmoreirajr.votacao.config.message.MessageSourceService;
import com.edsonmoreirajr.votacao.dto.TotalVotosDto;
import com.edsonmoreirajr.votacao.exception.BusinessException;
import com.edsonmoreirajr.votacao.mapper.VotoMapper;
import com.edsonmoreirajr.votacao.repository.PautaRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PautaRepositoryCustomImpl implements PautaRepositoryCustom {
    private final static String BUSINESS_PAUTA_DADOS_NAO_ENCONTRADOS = "business.pauta.dados-nao-encontrados";
    private final MessageSourceService messageSourceService;
    private EntityManager entityManager;

    @Override
    public TotalVotosDto getTotalVotos(Long pautaId) {

        String selectFields = "SELECT " +
                "COUNT(v.id) filter (where v.voto = 'SIM') as total_votos_sim," +
                "COUNT(v.id) filter (where v.voto = 'NAO') as total_votos_nao," +
                "COUNT(v.id) as total_todos_votos," +
                "p.id as pauta_id," +
                "p.titulo as pauta_titulo," +
                "p.status as status_pauta ";

        String tableRelations = "FROM \"voto\" v LEFT OUTER JOIN \"sessao\" s ON v.sessao_id = s.id LEFT OUTER JOIN \"pauta\" p ON p.id = s.pauta_id ";
        String where = "WHERE p.id = :pautaId ";
        String groupBy = "GROUP BY p.id";

        String queryBuilderList = selectFields +
                tableRelations +
                where +
                groupBy;

        Query jpaQuery = entityManager.createNativeQuery(queryBuilderList, Tuple.class);
        jpaQuery.setParameter("pautaId", pautaId);

        Tuple totalVotosTuple;

        try {
            totalVotosTuple = (Tuple) jpaQuery.getSingleResult();
        } catch (NoResultException e) {
            throw new BusinessException(messageSourceService.getMessage(BUSINESS_PAUTA_DADOS_NAO_ENCONTRADOS, pautaId));
        }

        return VotoMapper.INSTANCE.tupleToTotalVotosDto(totalVotosTuple);
    }
}