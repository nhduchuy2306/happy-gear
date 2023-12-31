package com.notimplement.happygear.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "date")
    private Date date;

    @Column(name = "total")
    private Double total;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "user_name")
    @JsonManagedReference
    private User user;
}
