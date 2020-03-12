package com.goldcard.iot.collect.rule;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.stereotype.Component;

@Component
public class RuleEngine {

    public ProtocolRule execute(ProtocolRule rule) {
        this.getStatelessKieSession().execute(rule);
        return rule;
    }

    /**
     * 获取无状态Session
     *
     * @return
     */
    private StatelessKieSession getStatelessKieSession() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();
        return kieContainer.newStatelessKieSession();
    }
}
