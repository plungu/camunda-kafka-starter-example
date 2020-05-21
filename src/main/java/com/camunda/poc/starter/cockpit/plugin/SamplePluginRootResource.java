package com.camunda.poc.starter.cockpit.plugin;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.camunda.bpm.cockpit.impl.plugin.base.sub.resources.ProcessInstanceResource;
import org.camunda.bpm.cockpit.plugin.resource.AbstractCockpitPluginRootResource;

@Path("plugin/" + SamplePlugin.ID)
public class SamplePluginRootResource extends AbstractCockpitPluginRootResource {

    public SamplePluginRootResource() {
        super(SamplePlugin.ID);
    }

    @Path("{engineName}/process-instance")
    public ProcessInstanceResource getProcessInstanceResource(@PathParam("engineName") String engineName) {
        return subResource(new ProcessInstanceResource(engineName, SamplePlugin.ID), engineName);
    }
}