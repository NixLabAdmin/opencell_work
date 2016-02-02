package org.meveo.api.dto;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.meveo.model.crm.CustomFieldTemplate;

/**
 * @author Edward P. Legaspi
 **/
@XmlRootElement(name = "CustomFieldTemplate")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomFieldTemplateDto extends BaseDto {

    private static final long serialVersionUID = 1L;

    @XmlAttribute(required = true)
    protected String code;

    @XmlAttribute(required = true)
    protected String description;

    @XmlElement(required = true)
    protected String fieldType;

    @XmlElement(required = false)
    @Deprecated
    protected String accountLevel;

    @XmlElement(required = false)
    protected String appliesTo;

    @XmlElement
    protected String defaultValue;

    @XmlElement(required = true)
    protected String storageType;

    @XmlElement
    protected boolean valueRequired;

    @XmlElement
    protected boolean versionable;

    @XmlElement
    protected boolean triggerEndPeriodEvent;

    @XmlElement
    protected String calendar;

    @XmlElement
    protected Integer cacheValueTimeperiod;

    @XmlElement
    protected String entityClazz;

    @XmlElement
    protected Map<String, String> listValues = new HashMap<String, String>();

    @XmlElement
    protected boolean allowEdit = true;

    @XmlElement
    protected boolean hideOnNew;

    @XmlElement
    protected Long maxValue;

    @XmlElement
    protected Long minValue;

    @XmlElement
    protected String regExp;

    @XmlElement
    protected boolean cacheValue;

    @XmlElement
    protected String guiPosition;

    @XmlElement()
    protected String mapKeyType;

    @XmlElement()
    protected String applicableOnEl;

    public CustomFieldTemplateDto() {

    }

    public CustomFieldTemplateDto(CustomFieldTemplate cf) {
        code = cf.getCode();
        description = cf.getDescription();
        fieldType = cf.getFieldType().name();
        accountLevel = cf.getAppliesTo();
        appliesTo = cf.getAppliesTo();
        defaultValue = cf.getDefaultValue();
        storageType = cf.getStorageType().name();
        valueRequired = cf.isValueRequired();
        versionable = cf.isVersionable();
        triggerEndPeriodEvent = cf.isTriggerEndPeriodEvent();
        entityClazz = cf.getEntityClazz();
        if (cf.getCalendar() != null) {
            calendar = cf.getCalendar().getCode();
        }
        allowEdit = cf.isAllowEdit();
        hideOnNew = cf.isHideOnNew();
        minValue = cf.getMinValue();
        maxValue = cf.getMaxValue();
        regExp = cf.getRegExp();
        cacheValue = cf.isCacheValue();
        cacheValueTimeperiod = cf.getCacheValueTimeperiod();
        guiPosition = cf.getGuiPosition();
        listValues = cf.getListValues();
        applicableOnEl = cf.getApplicableOnEl();
        if (cf.getMapKeyType() != null) {
            mapKeyType = cf.getMapKeyType().name();
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(String accountLevel) {
        this.accountLevel = accountLevel;
    }

    public String getAppliesTo() {
        return appliesTo;
    }

    public void setAppliesTo(String appliesTo) {
        this.appliesTo = appliesTo;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public boolean isVersionable() {
        return versionable;
    }

    public void setVersionable(boolean versionable) {
        this.versionable = versionable;
    }

    public boolean isTriggerEndPeriodEvent() {
        return triggerEndPeriodEvent;
    }

    public void setTriggerEndPeriodEvent(boolean triggerEndPeriodEvent) {
        this.triggerEndPeriodEvent = triggerEndPeriodEvent;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public boolean isValueRequired() {
        return valueRequired;
    }

    public void setValueRequired(boolean valueRequired) {
        this.valueRequired = valueRequired;
    }

    @Override
    public String toString() {
        return "CustomFieldTemplateDto [code=" + code + ", description=" + description + ", fieldType=" + fieldType + ", accountLevel=" + accountLevel + ", appliesTo=" + appliesTo
                + ", defaultValue=" + defaultValue + ", storageType=" + storageType + ", mapKeyType=" + mapKeyType + ", valueRequired=" + valueRequired + ", versionable="
                + versionable + ", triggerEndPeriodEvent=" + triggerEndPeriodEvent + ", calendar=" + calendar + ", entityClazz=" + entityClazz + "]";
    }

    public String getEntityClazz() {
        return entityClazz;
    }

    public void setEntityClazz(String entityClazz) {
        this.entityClazz = entityClazz;
    }

    /**
     * @return the listValues
     */
    public Map<String, String> getListValues() {
        return listValues;
    }

    /**
     * @param listValues the listValues to set
     */
    public void setListValues(Map<String, String> listValues) {
        this.listValues = listValues;
    }

    public boolean isAllowEdit() {
        return allowEdit;
    }

    public void setAllowEdit(boolean allowEdit) {
        this.allowEdit = allowEdit;
    }

    public boolean isHideOnNew() {
        return hideOnNew;
    }

    public void setHideOnNew(boolean hideOnNew) {
        this.hideOnNew = hideOnNew;
    }

    public Long getMinValue() {
        return minValue;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    public String getRegExp() {
        return regExp;
    }

    public void setRegExp(String regExp) {
        this.regExp = regExp;
    }

    public boolean isCacheValue() {
        return cacheValue;
    }

    public void setCacheValue(boolean cacheValue) {
        this.cacheValue = cacheValue;
    }

    public Integer getCacheValueTimeperiod() {
        return cacheValueTimeperiod;
    }

    public void setCacheValueTimeperiod(Integer cacheValueTimeperiod) {
        this.cacheValueTimeperiod = cacheValueTimeperiod;
    }

    public String getGuiPosition() {
        return guiPosition;
    }

    public void setGuiPosition(String guiPosition) {
        this.guiPosition = guiPosition;
    }

    public String getMapKeyType() {
        return mapKeyType;
    }

    public void setMapKeyType(String mapKeyType) {
        this.mapKeyType = mapKeyType;
    }

    public String getApplicableOnEl() {
        return applicableOnEl;
    }

    public void setApplicableOnEl(String applicableOnEl) {
        this.applicableOnEl = applicableOnEl;
    }
}