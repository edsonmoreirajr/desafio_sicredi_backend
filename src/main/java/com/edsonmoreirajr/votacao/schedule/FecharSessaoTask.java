package com.edsonmoreirajr.votacao.schedule;

import com.edsonmoreirajr.votacao.usecase.SessaoUseCase;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ScheduledFuture;

@RequiredArgsConstructor
public class FecharSessaoTask implements Runnable {

    private final Long sessaoId;
    private final SessaoUseCase sessaoUseCase;
    private ScheduledFuture<?> scheduledFuture;

    @Override
    public void run() {
        sessaoUseCase.fecharSessao(sessaoId);
        scheduledFuture.cancel(true);
    }

    public void setScheduledFuture(ScheduledFuture<?> scheduledFuture) {
        this.scheduledFuture = scheduledFuture;
    }
}
