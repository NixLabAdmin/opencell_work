package org.meveo.api.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.meveo.api.MeveoApiErrorCodeEnum;

/**
 * Determine the status of the MEVEO API web service response.
 * 
 * @author Edward P. Legaspi
 **/
@XmlRootElement(name = "ActionStatus")
@XmlType(name = "ActionStatus")
@XmlAccessorType(XmlAccessType.FIELD)
public class ActionStatus {

    /**
     * Tells whether the instance of this <code>ActionStatus</code> object ok or not.
     */
    @XmlElement(required = true)
    private ActionStatusEnum status;

    /**
     * An error code
     */
    private MeveoApiErrorCodeEnum errorCode;

    /**
     * A detailed error message if applicable
     */
    @XmlElement(required = true)
    private String message;

    public ActionStatus() {
        status = ActionStatusEnum.SUCCESS;
    }

    /**
     * Sets status and message.
     * 
     * @param status
     * @param message
     */
    public ActionStatus(ActionStatusEnum status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * Sets status, error code and message.
     * 
     * @param status
     * @param errorCode
     * @param message
     */
    public ActionStatus(ActionStatusEnum status, MeveoApiErrorCodeEnum errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ActionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ActionStatusEnum status) {
        this.status = status;
    }

    /**
     * Error code
     * 
     * @return Error code
     */
    public MeveoApiErrorCodeEnum getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(MeveoApiErrorCodeEnum errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "ActionStatus [status=" + status + ", errorCode=" + errorCode + ", message=" + message + "]";
    }
}
