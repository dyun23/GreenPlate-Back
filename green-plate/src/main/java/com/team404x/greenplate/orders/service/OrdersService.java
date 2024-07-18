package com.team404x.greenplate.orders.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.company.model.entity.Company;
import com.team404x.greenplate.company.repository.CompanyRepository;
import com.team404x.greenplate.item.model.entity.Item;
import com.team404x.greenplate.item.repository.ItemRepository;
import com.team404x.greenplate.orders.model.entity.*;
import com.team404x.greenplate.orders.model.requset.OrderCancelReq;
import com.team404x.greenplate.orders.model.requset.OrderInvoiceReq;
import com.team404x.greenplate.orders.model.requset.OrderSearchReq;
import com.team404x.greenplate.orders.model.response.OrderUserSearchDetailRes;
import com.team404x.greenplate.orders.model.response.OrderUserSearchRes;
import com.team404x.greenplate.orders.repository.OrderDetailRepository;
import com.team404x.greenplate.orders.repository.OrderQueryRepository;
import com.team404x.greenplate.orders.repository.OrdersRepository;
import com.team404x.greenplate.orders.model.requset.OrderCreateReq;

import com.team404x.greenplate.user.model.entity.User;
import com.team404x.greenplate.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    JPAQueryFactory queryFactory;

    @Transactional
    public BaseResponse<String> createOrder(OrderCreateReq orderCreateReq) {

        Optional<User> user = userRepository.findById(orderCreateReq.getUserId());

        if (!user.isPresent()) {
            return new BaseResponse<>(ORDERS_CREATED_FAIL);
        }
        //주문
        Orders orders = Orders.builder()
                .user(user.get())
                .orderDate(orderCreateReq.getOrderDate())
                .totalPrice(orderCreateReq.getTotalPrice())
                .totalQuantity(orderCreateReq.getTotalQuantity())
                .orderState(OrderStatus.ready.toString())
                .refundYn(false)
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
                    .price(req.getPrice())
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
            OrderUserSearchRes orderUserSearchRes = new OrderUserSearchRes();
            orderUserSearchRes.setOrder_id(order.getId());
            orderUserSearchRes.setOrder_state(order.getOrderState());
            orderUserSearchRes.setTotal_price(order.getTotalPrice());
            orderUserSearchRes.setTotal_cnt(order.getTotalQuantity());
            orderUserSearchRes.setRefund_yn(order.getRefundYn());
            orderUserSearchRes.setOrder_date(order.getOrderDate());
            orderUserSearchResList.add(orderUserSearchRes);
        }
        return new BaseResponse<>(orderUserSearchResList);
    }

    //유저 주문 상품 상세조회
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
            OrderUserSearchDetailRes orderUserSearchDetailRes = new OrderUserSearchDetailRes();
            orderUserSearchDetailRes.setOrder_id(orders2.getId());
            orderUserSearchDetailRes.setOrder_state(orders2.getOrderState());
            orderUserSearchDetailRes.setPrice(orderDetail.getPrice());
            orderUserSearchDetailRes.setCnt(orderDetail.getCnt());
            orderUserSearchDetailRes.setRefund_yn(orders2.getRefundYn());
            orderUserSearchDetailRes.setOrder_date(orders2.getOrderDate());
            orderUserSearchResList.add(orderUserSearchDetailRes);
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

    //주문취소
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

    //사업자 배송 상태 변경
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


}
