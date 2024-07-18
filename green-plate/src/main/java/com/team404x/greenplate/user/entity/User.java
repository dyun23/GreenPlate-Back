package com.team404x.greenplate.user.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.team404x.greenplate.cart.entity.Cart;
import com.team404x.greenplate.item.review.entity.ItemReview;
import com.team404x.greenplate.orders.entity.Orders;
import com.team404x.greenplate.recipe.entity.Recipe;
import com.team404x.greenplate.recipe.likes.RecipeLike;
import com.team404x.greenplate.recipe.review.RecipeReview;
import com.team404x.greenplate.user.address.entity.Address;
import com.team404x.greenplate.user.keyword.entity.UserKeyword;

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
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String password;
	private String name;
	private String nickName;
	private LocalDate birthday;
	private int loginCount;
	private String role;
	private boolean enabled;
	@ColumnDefault("false")
	private boolean delYN;
	@CreatedDate
	LocalDateTime createdDate;
	@LastModifiedDate
	LocalDateTime modifiedDate;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Orders> orders = new ArrayList<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<ItemReview> itemReviews = new ArrayList<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Cart> carts = new ArrayList<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<RecipeReview> recipeReviews = new ArrayList<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Recipe> recipes = new ArrayList<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<RecipeLike> recipeLikes = new ArrayList<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<UserKeyword> userKeywords = new ArrayList<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Address> addresses = new ArrayList<>();


}
