package com.camunda.poc.starter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.annotation.Profile;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Profile("ordering")
@Entity(name="pm_order")
public class Order {

    private static final long serialVersionUID = -209110232715280386L;

    private @Version
    @JsonIgnore
    Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "order_items",
            joinColumns = @JoinColumn(name = "pm_item_id"),
            inverseJoinColumns = @JoinColumn(name = "pm_order_id"))
    List<OrderItem> itemsOrdered;

    @Column(name="order_date", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Column(name="order_key", nullable=true)
    private String orderKey;

    @Column(name="comment", nullable=true)
    private String comment;

    @Column(name="started", nullable=true)
    private boolean started;

    @Column(name="approved", nullable=true)
    private boolean approved;

    @Column(name="rejected", nullable=true)
    private boolean rejected;

    @Column(name="status", nullable=true)
    private String status;

    @Column(name="sales_manager", nullable=true)
    private String salesManager;

    @Column(name="category", nullable=true)
    private String category;

    @Column(name="rep_name", nullable=true)
    private String repName;

    @Column(name="email", nullable=true)
    private String email;

    @Column(name="delivery_address", nullable=true)
    private String deliveryAddress;

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSalesManager() {
        return salesManager;
    }

    public void setSalesManager(String salesManager) {
        this.salesManager = salesManager;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
