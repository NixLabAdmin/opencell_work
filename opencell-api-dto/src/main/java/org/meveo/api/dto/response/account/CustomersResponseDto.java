package org.meveo.api.dto.response.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.meveo.api.dto.account.CustomersDto;
import org.meveo.api.dto.response.BaseResponse;

/**
 * @author Edward P. Legaspi
 **/
@XmlRootElement(name = "CustomersResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomersResponseDto extends BaseResponse {

	private static final long serialVersionUID = -7212880976584184812L;

	private CustomersDto customers = new CustomersDto();

	public CustomersDto getCustomers() {
		return customers;
	}

	public void setCustomers(CustomersDto customers) {
		this.customers = customers;
	}

	@Override
	public String toString() {
		return "CustomersResponseDto [customers=" + customers + ", toString()=" + super.toString() + "]";
	}

}
