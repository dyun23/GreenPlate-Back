package com.team404x.greenplate.item.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.team404x.greenplate.cart.entity.Cart;
import com.team404x.greenplate.company.entity.Company;
import com.team404x.greenplate.item.category.entity.Category;
import com.team404x.greenplate.item.review.entity.ItemReview;
import com.team404x.greenplate.orders.entity.OrderDetail;
import com.team404x.greenplate.recipe.item.RecipeItem;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
	private List<RecipeItem> recipeItems = new ArrayList<>();

	@OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
	private List<OrderDetail> orderDetails = new ArrayList<>();

	@OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
	private List<Cart> carts = new ArrayList<>();

	@OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
	private List<ItemReview> itemReviews = new ArrayList<>();

	private String name;

	private String contents;

	private Integer price; // 왜 BigDecimal..?

	private Integer stock; // 수정

	private String state;

	private Integer calorie;

	private String imageUrl; // 1을 붙인 이유..?

	private Boolean delYn;

	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime modifiedDate;

	private Integer discountPrice;


}