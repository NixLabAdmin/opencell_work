package org.meveo.model.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.meveo.commons.utils.StringUtils;
import org.meveo.model.BaseEntity;
import org.meveo.model.ExportIdentifier;
import org.meveo.model.billing.ProductInstance;
import org.meveo.model.billing.Subscription;
import org.meveo.model.billing.UserAccount;
import org.meveo.model.catalog.ProductOffering;
import org.meveo.model.shared.Address;

@Entity
@ExportIdentifier({ "order.code", "itemId"})
@Table(name = "ORD_ORDER_ITEM")
@GenericGenerator(name = "ID_GENERATOR", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {@Parameter(name = "sequence_name", value = "ORD_ORDER_ITEM_SEQ"), })
public class OrderItem extends BaseEntity {

    private static final long serialVersionUID = -6831399734977276174L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID", nullable = false, updatable = false)
    @NotNull
    private Order order;

    /**
     * Item id in the order
     */
    @Column(name = "ITEM_ID", length = 10, nullable = false)
    @NotNull
    private String itemId;

    /**
     * Action requested on a product or product offer
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "ACTION", length = 10, nullable = false)
    @NotNull
    private OrderItemActionEnum action;

    /**
     * Associated user account
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ACCOUNT_ID", nullable = false)
    @NotNull
    private UserAccount userAccount;

    /**
     * Product offerings associated to an order item. In case of bundled offers, the first item in a list is the parent offering.
     */
    @OneToMany(mappedBy = "orderItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "ITEM_ORDER")
    private List<OrderItemProductOffering> orderItemProductOfferings = new ArrayList<>();

    /**
     * Serialized orderItem dto.
     */
    @Column(name = "SOURCE", nullable = false, columnDefinition = "TEXT")
    private String source;

    /**
     * Order item processing status as defined by the workflow.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    @NotNull
    private OrderStatusEnum status = OrderStatusEnum.IN_CREATION;

    /**
     * Related product instances. Product instance(s) are created or updated by workflow while processing order item.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ORD_ITEM_PRD_INSTANCE", joinColumns = @JoinColumn(name = "ORDER_ITEM_ID"), inverseJoinColumns = @JoinColumn(name = "PRD_INSTANCE_ID"))
    private List<ProductInstance> productInstances;

    /**
     * Related subscription. Subscription is created or updated by workflow while processing order item.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBSCRIPTION_ID")
    private Subscription subscription;
    
	@Embedded
	private Address shippingAddress = new Address();

    @Transient
    private Object orderItemDto;

    @Transient
    private ProductOffering mainOffering;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public OrderItemActionEnum getAction() {
        return action;
    }

    public void setAction(OrderItemActionEnum action) {
        this.action = action;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String orderItemSource) {
        this.source = orderItemSource;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public List<ProductInstance> getProductInstances() {
        return productInstances;
    }

    public void setProductInstances(List<ProductInstance> productInstances) {
        this.productInstances = productInstances;
    }
    
    

    /**
	 * @return the shippingAddress
	 */
	public Address getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * @param shippingAddress the shippingAddress to set
	 */
	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public void addProductInstance(ProductInstance productInstance) {
        if (this.productInstances == null) {
            this.productInstances = new ArrayList<>();
        }
        this.productInstances.add(productInstance);
    }

    // public ProductInstance getProductInstance() {
    // Logger log = LoggerFactory.getLogger(getClass());
    // if (productInstances != null && productInstances.size() > 0) {
    // return productInstances.get(0);
    // }
    // return null;
    // }

    public Object getOrderItemDto() {
        return orderItemDto;
    }

    public void setOrderItemDto(Object orderItemDto) {
        this.orderItemDto = orderItemDto;
    }

    public ProductOffering getMainOffering() {

        if (mainOffering == null && orderItemProductOfferings != null && !orderItemProductOfferings.isEmpty()) {
            mainOffering = orderItemProductOfferings.get(0).getProductOffering();
        }

        return mainOffering;
    }

    public void setMainOffering(ProductOffering mainOffering) {
        this.mainOffering = mainOffering;
    }

    public void resetMainOffering(ProductOffering newMainOffer) {
        this.mainOffering = newMainOffer;
        orderItemProductOfferings.clear();
        if (newMainOffer != null) {
            orderItemProductOfferings.add(new OrderItemProductOffering(this, newMainOffer, orderItemProductOfferings.size()));
        }
        orderItemDto = null;
        source = null;
    }

    public List<OrderItemProductOffering> getOrderItemProductOfferings() {
        return orderItemProductOfferings;
    }

    public void setOrderItemProductOfferings(List<OrderItemProductOffering> orderItemProductOfferings) {
        this.orderItemProductOfferings = orderItemProductOfferings;
    }

    /**
     * Interested in comparing order items within the order only
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof OrderItem)) {
            return false;
        }

        return StringUtils.compare(getItemId(), ((OrderItem) obj).getItemId()) == 0;
    }
}