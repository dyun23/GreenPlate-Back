package com.team404x.greenplate.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.team404x.greenplate.cart.model.entity.Cart;
import com.team404x.greenplate.cart.repository.CartRepository;
import com.team404x.greenplate.company.model.entity.Company;
import com.team404x.greenplate.company.repository.CompanyRepository;
import com.team404x.greenplate.item.category.entity.Category;
import com.team404x.greenplate.item.category.repository.CategoryRepository;
import com.team404x.greenplate.item.model.entity.Item;
import com.team404x.greenplate.item.repository.ItemRepository;
import com.team404x.greenplate.keyword.entity.Keyword;
import com.team404x.greenplate.keyword.repository.KeywordRepository;
import com.team404x.greenplate.orders.model.entity.OrderDetail;
import com.team404x.greenplate.orders.model.entity.Orders;
import com.team404x.greenplate.orders.repository.OrderDetailRepository;
import com.team404x.greenplate.orders.repository.OrdersRepository;
import com.team404x.greenplate.recipe.item.RecipeItem;
import com.team404x.greenplate.recipe.model.entity.Recipe;
import com.team404x.greenplate.recipe.repository.RecipeItemRepository;
import com.team404x.greenplate.recipe.repository.RecipeRepository;
import com.team404x.greenplate.recipe.review.model.RecipeReview;
import com.team404x.greenplate.recipe.review.repository.RecipeReviewRepository;
import com.team404x.greenplate.user.address.entity.Address;
import com.team404x.greenplate.user.address.repository.AddressRepository;
import com.team404x.greenplate.user.keyword.entity.UserKeyword;
import com.team404x.greenplate.user.model.entity.User;
import com.team404x.greenplate.user.repository.UserKeywordRepository;
import com.team404x.greenplate.user.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class InitDb {

	private final UserRepository userRepository;
	private final CompanyRepository companyRepository;
	private final CategoryRepository categoryRepository;
	private final KeywordRepository keywordRepository;
	private final ItemRepository itemRepository;
	private final RecipeRepository recipeRepository;
	private final AddressRepository addressRepository;
	private final UserKeywordRepository userKeywordRepository;
	private final RecipeItemRepository recipeItemRepository;
	private final OrdersRepository ordersRepository;
	private final OrderDetailRepository orderDetailRepository;
	private final CartRepository cartRepository;
	private final RecipeReviewRepository recipeReviewRepository;

	private final BCryptPasswordEncoder passwordEncoder;

	private final Random random = new Random();

	@PostConstruct
	private void execute() {

		if(userRepository.count() == 0){
			signupDummyUser();
			signupDummyCompany();
			createCategory();
			createKeyword();
			createItem();
			createRecipe();
			registerAddress();
			createUserKeyword();;
			createItemRecipe();
			createOrders();
			createOrderDetail();
			createCart();
			createRecipeReview();
		}

	}

	private void signupDummyUser() {
		for (int i = 1; i <= 10; i++) {
			User user = User.builder()
				.email("user" + i + "@gmail.com")
				.password(passwordEncoder.encode("qwer1234"))
				.birthday(getRandomLocalDate())
				.enabled(true)
				.nickName("user"+i)
				.name("user"+i)
				.role("ROLE_USER")
				.build();
			userRepository.save(user);
		}
	}

	private void signupDummyCompany() {
		for (int i = 1; i <= 10; i++) {
			Company company = Company.builder()
				.email("company" + i + "@gmail.com")
				.password(passwordEncoder.encode("qwer1234"))
				.address("address" + i)
				.name("company" + i)
				.comNum("comNum" + i)
				.telNum("010-" + (i-1) + (i-1) + (i-1) + (i-1) + "-" + (i-1) + (i-1) + (i-1) + (i-1))
				.role("ROLE_COMPANY")
				.build();
			companyRepository.save(company);
		}
	}

	private LocalDate getRandomLocalDate() {
		int minDay = (int) LocalDate.of(2000, 1, 1).toEpochDay();
		int maxDay = (int) LocalDate.of(2024, 12, 31).toEpochDay();
		long randomDay = minDay + random.nextInt(maxDay - minDay);

		return LocalDate.ofEpochDay(randomDay);
	}

	private void createCategory() {
		List<String> mains = List.of("채소", "과일", "수산", "정육", "커피", "베이커리", "건강식품");
		List<String> subs1 = List.of("친환경", "고구마,감자,당근", "시금치,쌈채소,나물", "냉동,간편채소");
		List<String> subs2 = List.of("친환경", "국산과일", "수입과일", "간편과일");
		List<String> subs3 = List.of("제철수산", "생선류", "갑각류", "젓갈류");
		List<String> subs4 = List.of("스페셜티 원두", "드립백", "캡슐커피", "콜드브루", "인스턴트커피");
		List<String> subs5 = List.of("잼,스프레드", "식빵,모닝빵,베이글", "타르트", "디저트", "케이크");
		List<String> subs6 = List.of("영양제", "프로틴", "체중관리", "비타민");

		List<List<String>> subs = List.of(subs1, subs2, subs3, subs4, subs5, subs6);

		for (String main : mains) {
			for (List<String> sub : subs) {
				for (String elem : sub) {
					Category category = Category.builder()
						.mainCategory(main)
						.subCategory(elem)
						.build();
					categoryRepository.save(category);
				}
			}
		}
	}

	private void createKeyword() {
		List<String> keywords = List.of("저당", "비건", "다이어트", "고단백", "저지방", "글루텐프리", "슈가프리");
		for (String name : keywords) {
			Keyword keyword = Keyword.builder()
				.name(name)
				.build();
			keywordRepository.save(keyword);
		}
	}

	private void createItem() {
		Category dessert = categoryRepository.findCategoryByMainCategoryAndSubCategory("베이커리", "디저트");
		Category weight = categoryRepository.findCategoryByMainCategoryAndSubCategory("건강식품", "체중관리");
		List<String> desserts = List.of("[GANSIK] 두부 티라미수 4종", "[몽슈슈] 도지마롤 2종", "[오설록] 한라산 녹차 케이크", "[아우어베이커리] 더티초코 케이크");
		List<String> weights = List.of("[삼진제약] 그린녹차 다이어트 리턴핏", "[스키니랩] 애플페논 풋사과 다이어트", "[에버비키니] 빠질라카노 헤이즐넛향", "슬림톡 콤부차 레몬");

		for (int i = 0; i < desserts.size(); i++) {
			int price = (i+1) * 100;
			Item item = Item.builder()
				.name(desserts.get(i))
				.contents("정말 맛있는 제품 "+(i+1)+"번 입니다")
				.calorie(random.nextInt(600 - 200 + 1) + 200)
				.price(price)
				.stock(100)
				.state("판매중")
				.discountPrice(price)
				.imageUrl("imageUrl")
				.category(dessert)
				.company(Company.builder()
					.id(Long.parseLong(String.valueOf(i + 1)))
					.build())
				.build();
			itemRepository.save(item);
		}

		for (int i = 0; i < weights.size(); i++) {
			int price = (random.nextInt(200 - 100 + 1) + 100) * 100;
			Item item = Item.builder()
				.name(weights.get(i))
				.contents("정말 다이어트 제품입니다.")
				.calorie(random.nextInt(300 - 100 + 1) + 100)
				.price(price)
				.stock(100)
				.state("판매중")
				.discountPrice(price)
				.imageUrl("imageUrl")
				.category(weight)
				.company(Company.builder()
					.id(Long.parseLong(String.valueOf(6 + i)))
					.build())
				.build();
			itemRepository.save(item);
		}
	}

	private void createRecipe() {
		List<String> titles = List.of("제육볶음", "샤브샤브", "계란볶음밥", "김치볶음밥", "소금빵", "티라미수");
		List<User> users = userRepository.findAll();
		for (String title : titles) {
			int randomIndex = random.nextInt(users.size());
			Recipe recipe = Recipe.builder()
				.title(title)
				.contents(title + "만드는 방법!! 어쩌구 저쩌구")
				.user(users.get(randomIndex))
				.totalCalorie(1600)
				.build();
			recipeRepository.save(recipe);
		}
	}

	private void registerAddress() {
		List<User> users = userRepository.findAll();
		for (int i = 1; i <= 10; i++) {
			Address address = Address.builder()
				.zipcode("00101")
				.address("address" + i)
				.addressDetail("addrDetails" + i)
				.recipient("user"+i)
				.phoneNum("010-" + (i-1) + (i-1) + (i-1) + (i-1) + "-" + (i-1) + (i-1) + (i-1) + (i-1))
				.defultAddr(true)
				.user(users.get(i-1))
				.build();
			addressRepository.save(address);
		}
	}

	private void createUserKeyword() {
		List<User> users = userRepository.findAll();
		List<Keyword> keywords = keywordRepository.findAll();
		for (int i = 0; i < 10; i ++) {
			UserKeyword userKeyword = UserKeyword.builder()
				.user(users.get(i))
				.keyword(keywords.get(i%6))
				.build();
			userKeywordRepository.save(userKeyword);
		}
	}

	private void createItemRecipe() {
		Item item = itemRepository.findByNameContaining("헤이즐넛").get(0);
		Recipe recipe = recipeRepository.findByTitleContains("티라미수").get(0);

		recipeItemRepository.save(RecipeItem.builder()
			.item(item)
			.recipe(recipe)
			.build());
	}

	private void createOrders() {
		List<Item> items = itemRepository.findAll();
		List<User> users = userRepository.findAll();
		List<Integer> prices = new ArrayList<>();
		for (int i = 0; i < items.size(); i += 2) {
			int price = items.get(i).getPrice() + items.get(i+1).getPrice();
			prices.add(price);
		}

		int priceSize = prices.size() - 1;

		for (int i = 0; i < 30; i++) {
			Orders orders = Orders.builder()
				.createdDate(LocalDateTime.now())
				.orderDate(LocalDateTime.now())
				.totalPrice(prices.get(i % priceSize))
				.totalQuantity(2)
				.recipient("user" + (10 - i))
				.zipCode("00101")
				.address("address"+(i+1))
				.phoneNum("010-" + "1111"+ "-" + "2222")
				.invoice("송장번호" + (i+1))
				.orderState("ready")
				.user(users.get(i % 4))
				.build();
			ordersRepository.save(orders);
		}
	}

	private void createOrderDetail() {
		List<Item> items = itemRepository.findAll();
		List<Orders> orders = ordersRepository.findAll();
		List<Integer> orderIndex = List.of(0, 0, 1, 1, 2, 2, 3, 3);
		for (int i = 0; i < items.size(); i ++) {
			OrderDetail orderDetail = OrderDetail.builder()
				.cnt(1)
				.price(items.get(i).getPrice())
				.orders(orders.get(orderIndex.get(i)))
				.item(items.get(i))
				.build();
			orderDetailRepository.save(orderDetail);
		}
	}

	private void createCart() {
		List<Item> items = itemRepository.findAll();
		List<User> users = userRepository.findAll();
		int itemSize = items.size() - 1;
		int userSize = users.size() - 1;
		/*
		TODO
		같은 유저가 같은 상품을 담을 때 INSERT가 아닌 quantity가 UPDATE가 되도록 수정
		* */

		for (int i = 0; i < 30; i++) {
			Cart cart = Cart.builder()
				.quantity(1)
				.item(items.get(i % itemSize))
				.user(users.get(i % userSize))
				.build();
			cartRepository.save(cart);
		}
	}

	private void createRecipeReview() {
		List<User> users = userRepository.findAll();
		List<Recipe> recipes = recipeRepository.findAll();
		int userSize = users.size() - 1;
		int recipeSize = recipes.size() - 1;
		for (int i = 0; i < 10; i++) {
			RecipeReview recipeReview = RecipeReview.builder()
				.rating(random.nextInt(5 - 1 + 1) + 1)
				.contents("좋아요! 맛있어요")
				.user(users.get(i % userSize))
				.recipe(recipes.get(i % recipeSize))
				.build();
			recipeReviewRepository.save(recipeReview);
		}
	}
}
