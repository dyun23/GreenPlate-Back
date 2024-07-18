package com.team404x.greenplate.keyword.entity;

import java.util.ArrayList;
import java.util.List;

import com.team404x.greenplate.livecommerce.keyword.LivecommerceKeyword;
import com.team404x.greenplate.recipe.keyword.entity.RecipeKeyword;
import com.team404x.greenplate.user.keyword.entity.UserKeyword;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Keyword {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	String name;

	@OneToMany(mappedBy = "keyword", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<UserKeyword> userKeywords;

	@OneToMany(mappedBy = "keyword", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<LivecommerceKeyword> livecommerceKeywords = new ArrayList<>();

	@OneToMany(mappedBy = "keyword", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<RecipeKeyword> recipeKeywords = new ArrayList<>();
}
