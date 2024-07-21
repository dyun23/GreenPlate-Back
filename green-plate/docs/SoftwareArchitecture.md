### **소프트웨어 아키텍처 설계 문서**

---

### **1. 아키텍처 개요**
**green-plate**는 **레이어드 아키텍처**를 기반으로 한 **모놀리식 아키텍처**를 채택하였습니다.

### **2. 모놀리식 아키텍처의 장점**

1. **단순한 배포**:
   - 모든 컴포넌트가 단일 애플리케이션으로 통합되어 있어 배포와 관리가 간편합니다. 예를 들어, **사용자 관리**와 **주문 처리** 기능이 하나의 애플리케이션에서 실행됩니다.

2. **통합된 개발 및 테스트**:
   - 코드베이스가 단일화되어 있어 기능 간 상호작용을 쉽게 이해하고 테스트할 수 있습니다. **사용자**와 **주문** 관련 기능이 동일한 코드베이스에서 동작하여 통합 테스트가 용이합니다.

3. **데이터 일관성**:
   - 단일 데이터베이스를 사용하여 데이터의 일관성을 유지할 수 있습니다. 예를 들어, **Order**와 **User** 정보가 같은 데이터베이스에서 관리됩니다.

4. **성능 최적화**:
   - 모든 기능이 단일 프로세스 내에서 실행되므로 컴포넌트 간 통신 비용이 낮아집니다. 주문 처리와 사용자 정보 조회가 동일한 프로세스 내에서 이루어집니다.

5. **간편한 트랜잭션 관리**:
   - 트랜잭션이 단일 데이터베이스에서 관리되어 트랜잭션의 일관성과 무결성을 보장할 수 있습니다. 예를 들어, 주문 생성 시 사용자 정보와 주문 상세 정보가 동일한 트랜잭션으로 처리됩니다.

### **3. 레이어드 아키텍처의 장점**

```sql
+--------------------------------------------------+
                 프레젠테이션 레이어                 
                     (Web UI)                     
   - 사용자 인터페이스와의 상호작용을 담당              
   - 컨트롤러 및 REST API                           
+--------------------------------------------------+
                          |
                          v
+--------------------------------------------------+
                 애플리케이션 레이어              
               (서비스, 비즈니스 로직)            
   - 비즈니스 로직 처리                           
   - 서비스 계층: 주문, 사용자, 상품, 레시피 서비스 
+--------------------------------------------------+
                          |
                          v
+--------------------------------------------------+
                  도메인 레이어                  
              (도메인 모델, 엔티티)               
   - Orders                                      
   - User                                        
   - Item                                        
   - Company                                     
   - Recipe                                      
   - Keyword                                     
   - LiveCommerce                                
   - 엔티티 및 도메인 비즈니스 로직               
+--------------------------------------------------+
                          |
                          v
+--------------------------------------------------+
                데이터 액세스 레이어              
               (MariaDB, 레포지토리)            
   - 레포지토리 인터페이스                         
   - CartRepository                              
   - UserRepository                              
   - OrderRepository                             
   - CompanyRepository                           
   - RecipeRepository                            
   - KeywordRepository                           
   - LiveCommerceRepository                      
   - QueryDSL 기반의 동적 쿼리 및 페이징          
+--------------------------------------------------+
                          |
                          v
+--------------------------------------------------+
                  외부 통신 서비스               
   - 파일 저장 및 관리 (AWS S3)                   
   - 이메일 전송 (SMTP)                          
   - 결제 처리 (PortOne, KakaoPay)  
+--------------------------------------------------+


```



1. **명확한 책임 분리**:
   - 각 계층이 명확한 책임을 가지므로 유지보수와 관리가 용이합니다. 예를 들어, **프레젠테이션 계층**은 사용자 인터페이스를 처리하고, **서비스 계층**은 비즈니스 로직을 처리하며, **도메인 계층**은 핵심 도메인 로직을 포함하고, **데이터 액세스 계층**은 데이터베이스와의 상호작용을 담당합니다.

2. **계층 간 독립성**:
   - 각 계층이 독립적으로 개발 및 테스트할 수 있어 시스템의 유지보수와 확장성이 향상됩니다. 예를 들어, **주문 처리** 로직을 서비스 계층에서 수정해도 프레젠테이션 계층에 영향이 없습니다.

3. **재사용성과 일관성**:
   - 비즈니스 로직과 데이터 액세스 로직이 분리되어 코드의 재사용성과 일관성을 높입니다. **OrderService**가 주문 관련 비즈니스 로직을 처리하고, **OrderRepository**가 데이터 액세스를 담당합니다.

### **4. 엔티티 상세**

**Orders 엔티티**:
- **주요 필드**: `id`, `user`, `orderDate`, `totalPrice`, `totalQuantity`, `orderState`, `refundYn`, `recipient`, `zipCode`, `address`, `phoneNum`, `invoice`, `impUid`, `delYn`, `createdDate`, `modifiedDate`, `orderDetails`
- **연관 관계**: 사용자 (`User`)와 Many-to-One, 주문 상세 (`OrderDetail`)와 One-to-Many

**User 엔티티**:
- **주요 필드**: `id`, `email`, `password`, `name`, `nickName`, `birthday`, `loginCount`, `role`, `enabled`, `delYN`, `createdDate`, `modifiedDate`, `orders`, `itemReviews`, `carts`, `recipeReviews`, `recipes`, `recipeLikes`, `userKeywords`, `addresses`
- **연관 관계**: 주문 (`Orders`)과 One-to-Many, 리뷰, 장바구니, 레시피 등과의 관계

**Item 엔티티**:
- **주요 필드**: `id`, `category`, `company`, `recipeItems`, `orderDetails`, `carts`, `itemReviews`, `name`, `contents`, `price`, `stock`, `state`, `calorie`, `imageUrl`, `delYn`, `createdDate`, `modifiedDate`, `discountPrice`
- **연관 관계**: 카테고리 (`Category`), 회사 (`Company`)와 Many-to-One, 레시피 아이템 (`RecipeItem`), 주문 상세 (`OrderDetail`), 장바구니 (`Cart`), 상품 리뷰 (`ItemReview`)와 One-to-Many

**Recipe 엔티티**:
- **주요 필드**: `id`, `user`, `company`, `recipeItems`, `recipeLikes`, `recipeReviews`, `livecommerces`, `title`, `contents`, `imageUrl`, `totalCalorie`, `delYn`, `createdDate`, `modifiedDate`
- **연관 관계**: 사용자 (`User`), 회사 (`Company`)와 Many-to-One, 레시피 항목 (`RecipeItem`), 레시피 좋아요 (`RecipeLike`), 레시피 리뷰 (`RecipeReview`), 라이브 커머스 (`Livecommerce`)와 One-to-Many

### **5. 데이터 액세스 레이어 및 QueryDSL**

**QueryDSL을 활용한 데이터 액세스**:

**OrdersRepository.java**:
```java
    public Page<OrdersQueryProjection> getOrders(Long companyId, OrderSearchListReq searchReq, Pageable pageable) {
        QOrders orders = QOrders.orders;
        QOrderDetail orderDetail = QOrderDetail.orderDetail;
        QItem item = QItem.item;
        QCompany company = QCompany.company;

        // Create dynamic conditions
        BooleanBuilder whereBuilder = new BooleanBuilder();
        whereBuilder.and(company.id.eq(companyId));
        if (searchReq.getStatus() != null) {
            whereBuilder.and(orders.orderState.eq(searchReq.getStatus()));
        }

        return applyPagination(pageable, queryFactory -> {
            return queryFactory
                    .select(new QOrdersQueryProjection(
                            orders.id,
                            item.id,
                            item.name,
                            orderDetail.price.multiply(orderDetail.cnt).sum(),
                            orderDetail.cnt.sum(),
                            orders.orderDate,
                            orders.orderState,
                            orders.refundYn
                    ))
                    .from(orders)
                    .leftJoin(orderDetail).on(orderDetail.orders.eq(orders))
                    .leftJoin(item).on(orderDetail.item.eq(item))
                    .leftJoin(company).on(company.eq(item.company))
                    .where(whereBuilder)
                    .groupBy(orders.id);
        });
    }
```


### **6. 외부 통신 서비스**

- **AWS S3**: 이미지 및 기타 파일을 클라우드에 저장합니다. 예를 들어, 상품 이미지를 AWS S3에 저장하고 필요 시 다운로드합니다.
- **이메일 SMTP**: 이메일 발송 기능을 구현합니다. 사용자 인증을 SMTP 서버를 통해 전송합니다.
- **결제 처리** : PortOne을 통해 KakaoPay 결제를 처리합니다.

### **7. 모놀리식 아키텍처와 레이어드 아키텍처를 택한 이유**

**모놀리식 아키텍처**는 작은 규모의 프로젝트나 초기 개발 단계에서 배포와 관리의 용이성을 제공합니다. 예를 들어, **green-plate**의 사용자 관리와 주문 처리 기능이 하나의 애플리케이션으로 통합되어 배포와 관리가 간편합니다. 데이터 일관성과 성능 최적화 측면에서도 유리합니다.

반면, **MSA(Microservices Architecture)**는 독립적인 서비스로 시스템을 구성하여 확장성과 유연성을 제공합니다. 그러나 이는 복잡한 배포와 관리, 서비스 간 통신 문제를 동반할 수 있습니다.

**레이어드 아키텍처**는 명확한 책임 분리와 계층 간 독립성을 통해 유지보수와 확장성을 높입니다. 예를 들어, 주문 처리 시스템에서 **프레젠테이션 계층**은 사용자 인터페이스를 담당하고, **서비스 계층**은 비즈니스 로직을 처리하며, **도메인 계층**과 **데이터 액세스 계층**은 각각 핵심 비즈니스 로직과 데이터베이스 상호작용을 분리하여 효율적인 개발과 유지보수를 지원합니다.