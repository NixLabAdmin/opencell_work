package org.meveo.api.rest.catalog;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.meveo.api.dto.ActionStatus;
import org.meveo.api.dto.catalog.BusinessServiceModelDto;
import org.meveo.api.dto.response.catalog.GetBusinessServiceModelResponseDto;
import org.meveo.api.dto.response.module.MeveoModuleDtosResponse;
import org.meveo.api.rest.IBaseRs;

/**
 * @author Edward P. Legaspi
 **/
@Path("/catalog/businessServiceModel")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })

public interface BusinessServiceModelRs extends IBaseRs {

    @Path("/")
    @POST
    ActionStatus create(BusinessServiceModelDto postData);

    @Path("/")
    @PUT
    ActionStatus update(BusinessServiceModelDto postData);

    @Path("/")
    @GET
    GetBusinessServiceModelResponseDto find(@QueryParam("businessServiceModelCode") String businessServiceModelCode);

    @Path("/{businessServiceModelCode}")
    @DELETE
    ActionStatus remove(@PathParam("businessServiceModelCode") String businessServiceModelCode);

    @Path("/createOrUpdate")
    @POST
    ActionStatus createOrUpdate(BusinessServiceModelDto postData);

    @GET
    @Path("/list")
    public MeveoModuleDtosResponse list();

    @PUT
    @Path("/install")
    public ActionStatus install(BusinessServiceModelDto moduleDto);
}