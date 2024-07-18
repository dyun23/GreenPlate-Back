package com.team404x.greenplate.company.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.team404x.greenplate.item.model.entity.Item;
import com.team404x.greenplate.livecommerce.entity.Livecommerce;
import com.team404x.greenplate.recipe.model.entity.Recipe;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
