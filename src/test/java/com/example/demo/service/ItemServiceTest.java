package com.example.demo.service;

import com.example.demo.Repository.ItemRepository;
import com.example.demo.domain.item.Book;
import com.example.demo.domain.item.Item;
import com.example.demo.exception.NotEnoughStockException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private EntityManager em;

    @Test
    public void 아이템_저장() {

        //gone
        Book book = new Book();
        book.setName("aaaa");
        book.setPrice(2000);
        book.setStockQuantity(3000);
        //when
        itemService.saveItem(book);
        em.flush();
        //then
        itemService.findItems();
    }

    @Test
    public void 아이템_수량_추가() {

        //gone
        Book book = new Book();
        book.setName("aaaa");
        book.setPrice(2000);
        book.setStockQuantity(3000);
        //when
        itemService.saveItem(book);
        em.flush();

        book.addStock(2000);
        itemService.saveItem(book);

        //then
        Item item =  itemService.findOne(book.getId());

        System.out.println("stock :" + item.getStockQuantity());
    }

    @Test
    public void 아이템_수량_빼기() {

        //gone
        Book book = new Book();
        book.setName("aaaa");
        book.setPrice(2000);
        book.setStockQuantity(3000);
        //when
        itemService.saveItem(book);
        em.flush();

        book.removeStock(2000);
        itemService.saveItem(book);

        //then
        Item item =  itemService.findOne(book.getId());

        System.out.println("stock :" + item.getStockQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
    public void 아이템_수량_빼기_예외처리() throws Exception {

        //gone
        Book book = new Book();
        book.setName("aaaa");
        book.setPrice(2000);
        book.setStockQuantity(3000);
        //when
        itemService.saveItem(book);
        em.flush();

        book.removeStock(4000);
        itemService.saveItem(book);
        //then
        fail("예외가 발행해야 한다.");

    }

    @Test
    public void findItems() {
        Book book = new Book();
        book.setName("aaaa");
        book.setPrice(2000);
        book.setStockQuantity(3000);

        Book book1 = new Book();
        book1.setName("bbbb");
        book1.setPrice(2000);
        book1.setStockQuantity(3000);

        em.persist(book);
        em.persist(book1);

        em.flush();

        List<Item> list = itemRepository.findAll();
        for(Item item : list){
            System.out.println(item.getName());
        }

    }

    @Test
    public void findOne() {

        Book book = new Book();
        book.setName("aaaa");
        book.setPrice(2000);
        book.setStockQuantity(3000);

        Book book1 = new Book();
        book1.setName("bbbb");
        book1.setPrice(2000);
        book1.setStockQuantity(3000);

        em.persist(book);
        em.persist(book1);

        em.flush();

        Item item  = itemRepository.findOne(book.getId());

        System.out.println(item.getName());
    }
}