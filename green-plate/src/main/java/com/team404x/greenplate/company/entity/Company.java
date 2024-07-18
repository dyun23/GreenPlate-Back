package com.team404x.greenplate.company.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.team404x.greenplate.item.entity.Item;
import com.team404x.greenplate.livecommerce.entity.Livecommerce;
import com.team404x.greenplate.recipe.entity.Recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;
	private String password;
	private String comNum;
	private String role;
	private String name;
	private String address;
	private String telNum;
	@ColumnDefault("false")
	private Boolean delYn;
	@CreatedDate
	private LocalDateTime createdDate;
	@LastModifiedDate
	private LocalDateTime modifiedDate;

	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
	private List<Item> items = new ArrayList<>();

	@OneToOne(mappedBy = "company", fetch = FetchType.LAZY)
	private Livecommerce livecommerce;

	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
	private List<Recipe> recipes = new ArrayList<>();
}
