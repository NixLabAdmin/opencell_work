package org.tmf.dsmapi.catalog.resource.order;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.tmf.dsmapi.serialize.CustomDateSerializer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@XmlRootElement
@JsonInclude(value = Include.NON_NULL)
public class Note implements Serializable {

    private static final long serialVersionUID = 989785267987326681L;
    private String text;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date date;
    private String author;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
