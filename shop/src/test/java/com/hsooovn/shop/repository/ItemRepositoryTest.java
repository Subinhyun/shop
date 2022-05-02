package com.hsooovn.shop.repository;

import com.hsooovn.shop.constant.ItemSellStatus;
import com.hsooovn.shop.entity.Item;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("item save test")
    public void createItemTest() {
        Item item = new Item();
        item.setItemName("test item");
        item.setPrice(10000);
        item.setItemDetail("test item detail ...");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }

    public void createItemList(){
        for(int i=1;i<=10;i++){
            Item item = new Item();
            item.setItemName("test item" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("test item detail" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100); item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    public void createItemList2() {
        for(int i=0; i<=5; i++) {
            Item item = new Item();
            item.setItemName("test item" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("test item detail" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100); item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }

        for(int i=6; i<=10; i++) {
            Item item = new Item();
            item.setItemName("test item" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("test item detail" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(0); item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("find item test")
    public void findByItemTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemName("test item1");
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("find item and item detail test")
    public void findByItemNameOrItemDetailTest() {
        this.createItemList();
        List<Item> itemList =
                itemRepository.findByItemNameOrItemDetail("test item1", "test item detail5");
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("price less than test")
    public void findByPriceLessThanTest() {
        this.createItemList();
        List<Item> itemList =
                itemRepository.findByPriceLessThan(10005);
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("price less than order by price desc test")
    public void findByPriceLessThanOrderByPriceDescTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@Query user item find test")
    public void findByItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("test item detail");
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("nativeQuery user item find test")
    public void findByItemDetailByNative() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetailByNative("test item detail");
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

//    @Test
//    @DisplayName("Querydsl find test")
//    public void queryDslTest() {
//        this.createItemList();
//        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
//        QItem qItem = QItem.item;
//        JPAQuery<Item> query  = queryFactory.selectFrom(qItem)
//                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
//                .where(qItem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"))
//                .orderBy(qItem.price.desc());
//
//        List<Item> itemList = query.fetch();
//
//        for(Item item : itemList){
//            System.out.println(item.toString());
//        }
//    }
//
//    @Test
//    @DisplayName("item Querydsl find test2")
//    public void queryDslTest2() {
//        this.createItemList2();
//
//        BooleanBuilder booleanBuilder = new BooleanBuilder();
//        QItem item = QItem.item;
//        String itemDetail = "test item detail";
//        int price = 10003;
//        String itemSellStat = "SELL";
//
//        booleanBuilder.and(item.itemDetail.like("%" + itemDetail + "%"));
//        booleanBuilder.and(item.price.gt(price));
//        System.out.println(ItemSellStatus.SELL);
//        if(StringUtils.equals(itemSellStat, ItemSellStatus.SELL)){
//            booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
//        }
//
//        Pageable pageable = PageRequest.of(0, 5);
//        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);
//        System.out.println("total elements : " + itemPagingResult. getTotalElements ());
//
//        List<Item> resultItemList = itemPagingResult.getContent();
//        for(Item resultItem : resultItemList) {
//            System.out.println(resultItem.toString());
//        }
//
//    }

}