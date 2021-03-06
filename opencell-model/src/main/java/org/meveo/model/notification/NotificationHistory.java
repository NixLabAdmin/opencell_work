package org.meveo.model.notification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.meveo.model.EnableEntity;

@Entity
@Table(name="ADM_NOTIF_HISTORY")
@GenericGenerator(name = "ID_GENERATOR", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {@Parameter(name = "sequence_name", value = "ADM_NOTIF_HISTORY_SEQ"), })
public class NotificationHistory extends EnableEntity {
	
	private static final long serialVersionUID = -6882236977852466160L;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INBOUND_REQUEST_ID")
	private InboundRequest inboundRequest;
	
	@ManyToOne(fetch=FetchType.LAZY, optional= false)
	@NotNull
	@JoinColumn(name="NOTIFICATION_ID")
	private Notification notification;
	
	@Column(name="ENTITY_CLASSNAME",length=255, nullable = false)
	@Size(max = 255)
	@NotNull
	private String entityClassName;
	
	@Column(name="ENTITY_CODE",length=35)
	@Size(max = 35)
	private String entityCode;

	@Column(name="SERIALIZED_ENTITY", columnDefinition="TEXT") 
	private String serializedEntity;

	@Column(name="NB_RETRY")
	@Max(10)
	private int nbRetry;

	@Column(name="RESULT",length=1000)
	@Size(max=1000)
	private String result;
	
	@Column(name="STATUS")
	@Enumerated(EnumType.STRING)
	private NotificationHistoryStatusEnum status;

	public InboundRequest getInboundRequest() {
		return inboundRequest;
	}

	public void setInboundRequest(InboundRequest inboundRequest) {
		this.inboundRequest = inboundRequest;
		if(!inboundRequest.getNotificationHistories().contains(this)){
			inboundRequest.getNotificationHistories().add(this);
		}
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public String getEntityClassName() {
		return entityClassName;
	}

	public void setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public String getSerializedEntity() {
		return serializedEntity;
	}

	public void setSerializedEntity(String serializedEntity) {
		this.serializedEntity = serializedEntity;
	}

	public int getNbRetry() {
		return nbRetry;
	}

	public void setNbRetry(int nbRetry) {
		this.nbRetry = nbRetry;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		if(result!=null && result.length()>1000){
			this.result = result.substring(0,997)+"...";
		} else {
			this.result = result;
		}
	}

	public NotificationHistoryStatusEnum getStatus() {
		return status;
	}

	public void setStatus(NotificationHistoryStatusEnum status) {
		this.status = status;
	}

    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof NotificationHistory)) {
            return false;
        }

        NotificationHistory other = (NotificationHistory) obj;
        boolean inReq = this.inboundRequest != null && other.getInboundRequest() != null && this.inboundRequest.getCode().equals(other.getInboundRequest().getCode());
        boolean notif = this.notification != null && other.getNotification() != null && this.notification.getCode().equals(other.getNotification().getCode());
        return inReq && notif;
    }
	
}
