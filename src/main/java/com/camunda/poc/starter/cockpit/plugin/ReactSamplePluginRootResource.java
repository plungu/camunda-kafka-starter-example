package com.camunda.poc.starter.cockpit.plugin;

import org.camunda.bpm.cockpit.plugin.resource.AbstractCockpitPluginRootResource;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("plugin/" + ReactSamplePlugin.ID)
public class ReactSamplePluginRootResource extends AbstractCockpitPluginRootResource {

    public ReactSamplePluginRootResource() {
        super(ReactSamplePlugin.ID);
    }

    @Path("{engineName}/process-instance")
    public SampleProcessInstanceResource getProcessInstanceResource(@PathParam("engineName") String engineName) {
        return subResource(new SampleProcessInstanceResource(engineName), engineName);
    }
}