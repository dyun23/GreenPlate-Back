package com.team404x.greenplate.recipe.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.team404x.greenplate.company.entity.Company;
import com.team404x.greenplate.livecommerce.entity.Livecommerce;
import com.team404x.greenplate.recipe.item.RecipeItem;
import com.team404x.greenplate.recipe.likes.RecipeLike;
import com.team404x.greenplate.recipe.review.RecipeReview;
import com.team404x.greenplate.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
	private List<RecipeItem> recipeItems = new ArrayList<>();

	@OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
	private List<RecipeLike> recipeLikes = new ArrayList<>();

	@OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
	private List<RecipeReview> recipeReviews = new ArrayList<>();

	@OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
	private List<Livecommerce> livecommerces = new ArrayList<>();


	private String title;

	private String contents;

	private String imageUrl;

	private Integer totalCalorie;

	@ColumnDefault("false")
	private Boolean delYn;

	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime modifiedDate;

}
