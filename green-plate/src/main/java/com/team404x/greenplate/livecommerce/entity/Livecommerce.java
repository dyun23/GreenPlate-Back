package com.team404x.greenplate.livecommerce.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.team404x.greenplate.company.entity.Company;
import com.team404x.greenplate.livecommerce.keyword.LivecommerceKeyword;
import com.team404x.greenplate.recipe.entity.Recipe;

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
public class Livecommerce {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;

	@OneToMany(mappedBy = "livecommerce", fetch = FetchType.LAZY)
	private List<LivecommerceKeyword> livecommerceKeywords = new ArrayList<>();


	private String title;


	private String description;


	private LocalDate liveDate;


	private Instant startTime;


	private Instant endTime;


	private String keyword;

	private String item;

	private String imageUrl;

	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime modifiedDate;

}
