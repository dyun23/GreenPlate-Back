package com.team404x.greenplate.orders.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.AccessToken;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.company.model.entity.Company;
import com.team404x.greenplate.company.repository.CompanyRepository;
import com.team404x.greenplate.item.model.entity.Item;
import com.team404x.greenplate.item.repository.ItemRepository;
import com.team404x.greenplate.orders.model.entity.*;
import com.team404x.greenplate.orders.model.requset.*;
import com.team404x.greenplate.orders.model.response.OrderPaymentRes;
import com.team404x.greenplate.orders.model.response.OrderUserSearchDetailRes;
import com.team404x.greenplate.orders.model.response.OrderUserSearchRes;
import com.team404x.greenplate.orders.repository.OrderDetailRepository;
import com.team404x.greenplate.orders.repository.OrderQueryRepository;
import com.team404x.greenplate.orders.repository.OrdersRepository;

import com.team404x.greenplate.user.address.entity.Address;
import com.team404x.greenplate.user.address.repository.AddressRepository;
import com.team404x.greenplate.user.model.entity.User;
import com.team404x.greenplate.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.io.IOException;
import java.util.*;

import static com.team404x.greenplate.common.BaseResponseMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final AddressRepository addressRepository;
    private final IamportClient iamportClient;

    JPAQueryFactory queryFactory;

    @Value("${imp.imp_key}")
    private String impKey;
    @Value("${imp.imp_secret}")
    private String impSecret;


    @Transactional
    public BaseResponse<OrderPaymentRes> chosePayment(OrderCreateReq orderCreateReq) {
        Optional<User> user = userRepository.findById(orderCreateReq.getUserId());
        if (!user.isPresent()) {
            return new BaseResponse<>(ORDERS_CREATED_FAIL);
        }

        OrderPaymentRes oderPaymentRes = new OrderPaymentRes();
        oderPaymentRes.setUserId(orderCreateReq.getUserId());
        oderPaymentRes.setTotalPrice(orderCreateReq.getTotalPrice());
        oderPaymentRes.setTotalQuantity(orderCreateReq.getTotalQuantity());
        oderPaymentRes.setOrderDetailList(orderCreateReq.getOrderDetailList());
        oderPaymentRes.setRefundYn(false);

        List<Address> addressList = addressRepository.findByUserAndDefultAddrTrue(user.get());

        if (addressList.size() <= 0) {
            oderPaymentRes.setZipCode("");
            oderPaymentRes.setAddress("");
            oderPaymentRes.setAddressDetail("");
            oderPaymentRes.setPhoneNum("");
        }else{
            oderPaymentRes.setZipCode(addressList.get(0).getZipcode());
            oderPaymentRes.setAddress(addressList.get(0).getAddress());
            oderPaymentRes.setAddressDetail(addressList.get(0).getAddressDetail());
            oderPaymentRes.setPhoneNum(addressList.get(0).getPhoneNum());
        }
        return new BaseResponse<>(oderPaymentRes);
    }

    @Transactional
    public BaseResponse<String> createOrder(OrderCreateReq orderCreateReq) throws Exception {

        Optional<User> user = userRepository.findById(orderCreateReq.getUserId());
        if (!user.isPresent()) {
            return new BaseResponse<>(ORDERS_CREATED_FAIL);
        }
        LocalDateTime now = LocalDateTime.now();

        //주문
        Orders orders = Orders.builder()
                .user(user.get())
                .orderDate(now)
                .totalPrice(orderCreateReq.getTotalPrice())
                .totalQuantity(orderCreateReq.getTotalQuantity())
                .orderState(OrderStatus.ready.toString())
                .zipCode(orderCreateReq.getZipCode())
                .address(orderCreateReq.getAddress())
                .addressDetail(orderCreateReq.getAddressDetail())
                .phoneNum(orderCreateReq.getPhoneNum())
                .refundYn(false)
                .impUid(orderCreateReq.getImpUid())
                .build();
        orders = ordersRepository.save(orders);

        List<OrderCreateReq.OrderDetailDto> orderDetailReq = orderCreateReq.getOrderDetailList();
        //주문 디테일
        for (OrderCreateReq.OrderDetailDto req : orderDetailReq) {
            Item item = itemRepository.findById(req.getItemId()).get();
            //재고 확인
            if (item.getStock() <= req.getCnt())
                return new BaseResponse<>(ORDERS_CREATED_FAIL_STOCK);

            OrderDetail orderDetail = OrderDetail.builder()
                    .orders(orders)
                    .item(item)
                    .cnt(req.getCnt())
                    .price(req.getDiscountPrice())
                    .build();
            orderDetailRepository.save(orderDetail);

            //item 재고수량 수정
            item.updateStockQuantity(item.getStock() - req.getCnt());

        }
        return new BaseResponse<>(ORDERS_CREATED_SUCCESS);
    }

    //유저 주문 상품 목록 조회
    @Transactional
    public BaseResponse<List<OrderUserSearchRes>> searchForUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            return new BaseResponse<>(ORDERS_SEARCH_FAIL_USER);
        }

        List<OrderUserSearchRes> orderUserSearchResList = new ArrayList<OrderUserSearchRes>();
        List<Orders> orders = ordersRepository.findAllByUser(user.get());
        for(Orders order : orders){
            OrderUserSearchRes res = OrderUserSearchRes.builder()
                    .order_id(order.getId())
                    .order_state(order.getOrderState())
                    .total_price(order.getTotalPrice())
                    .total_cnt(order.getTotalQuantity())
                    .refund_yn(order.getRefundYn())
                    .order_date(order.getOrderDate())
                    .build();
            orderUserSearchResList.add(res);
        }
        return new BaseResponse<>(orderUserSearchResList);
    }

    //유저 주문 상품 상세조회
    @Transactional
    public BaseResponse<List<OrderUserSearchDetailRes>> searchForUserDetail(Long userId, Long ordersId) {
        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            return new BaseResponse<>(ORDERS_SEARCH_FAIL_USER);
        }

        Optional<Orders> orders = ordersRepository.findById(ordersId);
        Orders orders2 = orders.get();

        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrdersId(ordersId);

        List<OrderUserSearchDetailRes> orderUserSearchResList = new ArrayList<OrderUserSearchDetailRes>();
        for(OrderDetail orderDetail : orderDetailList){

            OrderUserSearchDetailRes res = OrderUserSearchDetailRes.builder()
                    .order_id(orders2.getId())
                    .orderDetail_id(orderDetail.getId())
                    .order_state(orders2.getOrderState())
                    .price(orderDetail.getPrice())
                    .cnt(orderDetail.getCnt())
                    .refund_yn(orders2.getRefundYn())
                    .order_date(orders2.getOrderDate())
                    .build();
            orderUserSearchResList.add(res);

        }
        return new BaseResponse<>(orderUserSearchResList);
    }

    //사업자 주문 상품 목록조회
    @Transactional
    public BaseResponse<List<OrdersQueryProjection>> searchForCompany(Long companyId, OrderSearchReq searchReq) {
        Optional<Company> company = companyRepository.findById(companyId);

        if (!company.isPresent()) {
            throw new RuntimeException(new EntityNotFoundException("회사가 없음"));
        }

//        if(searchReq.getStatus() != OrderStatus.ready.toString()
//            && searchReq.getStatus() != OrderStatus.shipped.toString()
//            && searchReq.getStatus() != OrderStatus.completed.toString())
//        {
//            searchReq.setStatus("%");
//        }

        List<OrdersQueryProjection> ordersList = orderQueryRepository.getOrders(companyId, searchReq);
        return new BaseResponse<>(ordersList);
    }

    //사업자 주문 상품 상세조회
    public BaseResponse<List<OrdersQueryProjection>> searchForCompanyDetail(Long companyId, Long orderId) {
        Optional<Company> company = companyRepository.findById(companyId);

        if (!company.isPresent()) {
            throw new RuntimeException(new EntityNotFoundException("회사가 없음"));
        }

        List<OrdersQueryProjection> ordersList = orderQueryRepository.getOrderDetail(companyId, orderId);
        return new BaseResponse<>(ordersList);
    }



    //사업자 배송 상태 변경
    @Transactional
    public BaseResponse changeDeliveryState(OrderSearchReq orderSearchReq) {
        if(!orderSearchReq.getStatus().equals(OrderStatus.ready.toString())
            && !orderSearchReq.getStatus().equals(OrderStatus.shipped.toString())
            && !orderSearchReq.equals(OrderStatus.completed.toString()))
        {
            return new BaseResponse<>(ORDERS_UPDATE_FAIL_CHANGE);
        }

        Optional<Orders> orders = ordersRepository.findById(orderSearchReq.getOrderId());
        if (!orders.isPresent()) {
            return new BaseResponse<>(ORDERS_SEARCH_FAIL_ORDERED);
        }else{
            Orders orders2 = orders.get();
            orders2.orderState(orderSearchReq.getStatus());
            ordersRepository.save(orders2);
        }

        return new BaseResponse<>(ORDERS_UPDATE_SUCCESS_CHANGE);
    }

    //사업자 송장 번호 입력
    @Transactional
    public BaseResponse inputInvoice(OrderInvoiceReq orderInvoiceReq) {
        Optional<Orders> orders = ordersRepository.findById(orderInvoiceReq.getOrderId());
        if (!orders.isPresent()) {
            return new BaseResponse<>(ORDERS_SEARCH_FAIL_ORDERED);
        }else{
            Orders orders2 = orders.get();
            orders2.invoice(orderInvoiceReq.getInvoiceNum());
            ordersRepository.save(orders2);
        }

        return new BaseResponse<>(ORDERS_UPDATE_SUCCESS_INVOICE);
    }

    //카카오페이 결제금액 체크
    public Boolean payCheck(OrderCreateReq orderCreateReq, IamportResponse<Payment> iamportResponse) {
        Long amount = iamportResponse.getResponse().getAmount().longValue();
        if (amount != orderCreateReq.getTotalPrice()){
            return false;
        }else{
            return true;
        }
    }

    public IamportResponse<Payment> getPaymentInfo(String impUid) throws IamportResponseException, IOException {
        return iamportClient.paymentByImpUid(impUid);
    }

    //카카오페이 결제중 환불
    public IamportResponse refund(OrderCreateReq orderCreateReq, IamportResponse<Payment> info) throws IamportResponseException, IOException {
        CancelData cancelData = new CancelData(orderCreateReq.getImpUid(), true, info.getResponse().getAmount());
        return iamportClient.cancelPaymentByImpUid(cancelData);
    }

    //카카오페이 결제 완료 후 환불
    public BaseResponse kakaoPayRefund(OrderCancelReq orderCancelReq) {
        Optional<Orders> orders = ordersRepository.findById(orderCancelReq.getOrderId());
        if (!orders.isPresent()) {
            return new BaseResponse<>(ORDERS_SEARCH_FAIL_ORDERED);
        }

        String accessToken = getAccessToken();      //토큰 요청
        if(requestsRefun(accessToken, orders.get())){ //카카오페이 결제 취소
            cancelOrder(orderCancelReq);                //주문취소 데이터 저장
            return new BaseResponse<>(ORDERS_CANCEL_FAIL_KAKAO);
        };

        return new BaseResponse<>(ORDERS_CANCEL_SUCCESS_KAKAO);
    }

    //카카오페이 결제 취소용 TOKEN
    public String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.ACCEPT, "application/json");

        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("imp_key", impKey);
        jsonObject.addProperty("imp_secret", impSecret);
        String jsonStr = gson.toJson(jsonObject);


        HttpEntity<String> request = new HttpEntity<>(jsonStr, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.iamport.kr/users/getToken",
                HttpMethod.POST,
                request,
                String.class
        );

        Map<String, Object> result = gson.fromJson(response.getBody(), Map.class);
        Map<String, Object> data = (Map<String, Object>) result.get("response");
        String access_token = (String) data.get("access_token");
        return access_token;
    }

    //pg사 kakao 결제 취소 요청
    public Boolean requestsRefun(String accessToken, Orders orders){
        HttpHeaders headers = new HttpHeaders();
        try{
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
            headers.add(HttpHeaders.ACCEPT, "application/json");
            headers.add(HttpHeaders.AUTHORIZATION, accessToken);

            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("reason", "결제 정보가 이상합니다.");
            jsonObject.addProperty("imp_uid", orders.getImpUid());
            jsonObject.addProperty("amount", orders.getTotalPrice());
            String jsonStr = gson.toJson(jsonObject);

            HttpEntity<String> request = new HttpEntity<>(jsonStr, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://api.iamport.kr/payments/cancel",
                    HttpMethod.POST,
                    request,
                    String.class
            );

            Map<String, Object> result = gson.fromJson(response.getBody(), Map.class);
            System.out.println(result);
        }catch(Exception e){
            return false;
        }

        return true;
    }

    //주문취소 데이터 저장
    @Transactional
    public BaseResponse<String> cancelOrder(OrderCancelReq orderCancelReq) {
        Optional<Orders> orders = ordersRepository.findById(orderCancelReq.getOrderId());

        if (!orders.isPresent()) {
            return new BaseResponse<>(ORDERS_SEARCH_FAIL_ORDERED);
        }else{
            Orders orders2 = orders.get();
            orders2.refundOrder();
            ordersRepository.save(orders2);
        }
        return new BaseResponse<>(ORDERS_CANCEL_SUCCESS);
    }


}
