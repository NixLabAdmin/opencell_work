package org.meveo.api.rest.account;

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
import org.meveo.api.dto.account.BillingAccountDto;
import org.meveo.api.dto.response.account.BillingAccountsResponseDto;
import org.meveo.api.dto.response.account.GetBillingAccountResponseDto;
import org.meveo.api.dto.response.billing.GetCountersInstancesResponseDto;
import org.meveo.api.rest.IBaseRs;

/**
 * @author Edward P. Legaspi
 **/
@Path("/account/billingAccount")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })

public interface BillingAccountRs extends IBaseRs {

    @POST
    @Path("/")
    ActionStatus create(BillingAccountDto postData);

    @PUT
    @Path("/")
    ActionStatus update(BillingAccountDto postData);

    /**
     * Search for a billing account with a given code.
     * 
     * @param billingAccountCode
     * @return
     */
    @GET
    @Path("/")
    GetBillingAccountResponseDto find(@QueryParam("billingAccountCode") String billingAccountCode);

    @DELETE
    @Path("/{billingAccountCode}")
    ActionStatus remove(@PathParam("billingAccountCode") String billingAccountCode);

    /**
     * List BillingAccount filter by customerAccountCode.
     * 
     * @param customerAccountCode
     * @return
     */
    @GET
    @Path("/list")
    BillingAccountsResponseDto listByCustomerAccount(@QueryParam("customerAccountCode") String customerAccountCode);

    /**
     * Create or update Billing Account based on code.
     * 
     * @param postData
     * @return
     */
    @POST
    @Path("/createOrUpdate")
    ActionStatus createOrUpdate(BillingAccountDto postData);
    
    /**
     * filter counters by period date
     * @param billingAccountCode
     * @param date
     * @return
     */
    @GET
    @Path("/filterCountersByPeriod")
	GetCountersInstancesResponseDto filterBillingAccountCountersByPeriod(@QueryParam("billingAccountCode") String billingAccountCode, 
			@QueryParam("date") String date);
}
