package org.meveo.api.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.meveo.api.dto.ActionStatus;
import org.meveo.api.dto.billing.EdrDto;

/**
 * @author Edward P. Legaspi
 **/
@WebService
public interface MediationWs extends IBaseWs {

	@WebMethod
	ActionStatus create(@WebParam(name = "edr") EdrDto postData);

}