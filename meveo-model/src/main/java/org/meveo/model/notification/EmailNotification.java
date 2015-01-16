package org.meveo.model.notification;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.meveo.model.admin.User;

@Entity
@Table(name="ADM_NOTIF_EMAIL")
public class EmailNotification extends Notification {
	
	private static final long serialVersionUID = -8948201462950547554L;

	@Column(name="EMAIL_FROM",length=1000)
	@Size(max=1000)
	private String emailFrom;
	
	@Column(name="EMAIL_TO_EL",length=1000)
	@Size(max=1000)
	private String emailToEl;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="ADM_NOTIF_EMAIL_LIST")
	private Set<String> emails;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="ADM_NOTIF_EMAIL_USER")
	private Set<User> users;
	
	@Column(name="EMAIL_SUBJECT",length=500,nullable=false)
	@Size(max=500)
	private String subject;

	@Column(name="EMAIL_BODY",length=2000)
	private String body;

	@Column(name="EMAIL_HTML_BODY",length=2000)
	private String htmlBody;

	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="ADM_NOTIF_EMAIL_ATTACH")
	private Set<String> attachmentExpressions;


	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getEmailToEl() {
		return emailToEl;
	}

	public void setEmailToEl(String emailToEl) {
		this.emailToEl = emailToEl;
	}

	public Set<String> getEmails() {
		return emails;
	}

	public void setEmails(Set<String> emails) {
		this.emails = emails;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getHtmlBody() {
		return htmlBody;
	}

	public void setHtmlBody(String htmlBody) {
		this.htmlBody = htmlBody;
	}

	public Set<String> getAttachmentExpressions() {
		return attachmentExpressions;
	}

	public void setAttachmentExpressions(Set<String> attachmentExpressions) {
		this.attachmentExpressions = attachmentExpressions;
	}
	
}
