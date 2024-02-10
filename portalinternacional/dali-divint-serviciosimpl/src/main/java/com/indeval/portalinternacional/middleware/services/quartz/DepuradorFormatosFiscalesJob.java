package com.indeval.portalinternacional.middleware.services.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;

public class DepuradorFormatosFiscalesJob extends QuartzJobBean {

    private ControlBeneficiariosService beneficiariosService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        this.beneficiariosService.depurarFormatosFiscalesBeneficiarios();
    }

    public void setBeneficiariosService(ControlBeneficiariosService beneficiariosService) {
        this.beneficiariosService = beneficiariosService;
    }

}
