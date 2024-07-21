package com.team404x.greenplate.orders.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.team404x.greenplate.user.address.entity.Address;
import com.team404x.greenplate.user.model.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private LocalDateTime orderDate;
	private Integer totalPrice;
	private Integer totalQuantity;
	private String orderState;
	private boolean refundYn;
	private String recipient;
	private String zipCode;
	private String address;
	private String phoneNum;
	private String invoice;
	private String impUid;

	@ColumnDefault("false")
	private Boolean delYn;

	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime modifiedDate;

	@OneToMany(mappedBy = "orders")
	private List<OrderDetail> orderDetails = new ArrayList<>();

	public void orderState(String orderStatus){
		orderState = orderStatus;
	}

	public void refundOrder(){
		refundYn = true;
	}

	public void invoice(String invoiceNum){
		invoice = invoiceNum;
	}

	public boolean getRefundYn() {
		return this.refundYn;
	}
}
