package com.team404x.greenplate.user.address.entity;

import org.hibernate.annotations.ColumnDefault;
import com.team404x.greenplate.orders.model.entity.Orders;
import com.team404x.greenplate.user.model.entity.User;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer zipcode;
	private String address;
	private String addressDetail;
	private String recipient;
	private String phoneNum;
	@ColumnDefault("false")
	private Boolean defultAddr;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
