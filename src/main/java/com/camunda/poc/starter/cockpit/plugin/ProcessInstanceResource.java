package com.camunda.poc.starter.cockpit.plugin;

import java.util.List;
import javax.ws.rs.GET;

import org.camunda.bpm.cockpit.db.QueryParameters;
import org.camunda.bpm.cockpit.plugin.resource.AbstractCockpitPluginResource;

public class ProcessInstanceResource extends AbstractCockpitPluginResource {

    public ProcessInstanceResource(String engineName) {
        super(engineName);
    }

    @GET
    public List<ProcessInstanceCountDto> getProcessInstanceCounts() {

        return getQueryService()
                .executeQuery(
                        "cockpit.sample.selectProcessInstanceCountsByProcessDefinition",
                        new QueryParameters<ProcessInstanceCountDto>());
    }
}