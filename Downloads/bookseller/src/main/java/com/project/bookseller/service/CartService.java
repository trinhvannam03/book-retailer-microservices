package com.project.bookseller.service;

import com.project.bookseller.authentication.UserDetails;
import com.project.bookseller.controller.CartController;
import com.project.bookseller.dto.CartRecordDTO;
import com.project.bookseller.entity.StockRecord;
import com.project.bookseller.entity.book.Book;
import com.project.bookseller.entity.user.CartRecord;
import com.project.bookseller.exceptions.ResourceNotFoundException;
import com.project.bookseller.exceptions.NotEnoughStockException;
import com.project.bookseller.repository.BookRepository;
import com.project.bookseller.repository.CartRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@RequiredArgsConstructor
public class CartService {
    private final CartRecordRepository recordRepository;
    private final BookRepository bookRepository;
    private final CartRecordRepository cartRecordRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addToCart(UserDetails userDetails, Long bookId, Integer quantity) throws ResourceNotFoundException, NotEnoughStockException {
        Optional<Book> optionalBook = bookRepository.findBriefBookByBookId(bookId);

        //book must exist!!!
        if (optionalBook.isPresent()) {
            int stock = optionalBook.get().getStockRecords().stream().mapToInt(StockRecord::getQuantity).sum();
            CartRecord cartRecord = new CartRecord();
            Optional<CartRecord> optionalCartRecord = recordRepository.findCartRecordByUser_UserIdAndBook_BookId(userDetails.getUser().getUserId(), bookId);
            //whether the item is in user's cart or not?
            //in case already in cart
            if (optionalCartRecord.isPresent()) {
                cartRecord = optionalCartRecord.get();
                if (stock >= cartRecord.getQuantity() + quantity) {
                    cartRecord.setQuantity(cartRecord.getQuantity() + quantity);
                    recordRepository.save(cartRecord);
                } else {
                    throw new NotEnoughStockException("Not enough stock");
                }
                // if not yet in cart
            } else {
                if (stock >= quantity) {
                    cartRecord.setQuantity(quantity);
                    cartRecord.setUser(userDetails.getUser());
                    cartRecord.setBook(optionalBook.get());
                    recordRepository.save(cartRecord);
                } else {
                    throw new NotEnoughStockException("Not enough stock");
                }
            }
        } else {
            throw new ResourceNotFoundException("No book found!");
        }
    }

    public List<CartRecordDTO> getCart(UserDetails userDetails) {
        /*
        shared findCartRecordsWithStock(Long userId, List<Long> cartRecordIds)
         method takes an array of id as parameters(get all, get checked items to check out,

        * */
        List<CartRecord> cartRecords = recordRepository.findCartRecordsWithStock(userDetails.getUser().getUserId(), null);
        List<CartRecordDTO> cartRecordDTOs = cartRecords.stream().map(CartRecordDTO::fromCartRecord).toList();
        for (int i = 0; i < cartRecords.size(); i++) {
            CartRecord cartRecord = cartRecords.get(i);
            int stock = cartRecord.getBook().getStockRecords().get(0).getQuantity();
            cartRecordDTOs.get(i).getBook().setStock(stock);
        }
        return cartRecordDTOs;
    }

    //delete from cart
    //transactional, delete if all records are from the same user. If not, roll back the transaction.
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ResourceNotFoundException.class)
    public void deleteFromCart(UserDetails userDetails, List<Long> cartRecordIds) throws ResourceNotFoundException {
        List<CartRecord> cartRecords = recordRepository.findCartRecordsWithStock(userDetails.getUser().getUserId(), cartRecordIds);
        for (CartRecord cartRecord : cartRecords) {
            if (cartRecord.getUser().getUserId() == userDetails.getUser().getUserId()) {
                recordRepository.delete(cartRecord);
            } else {
                throw new ResourceNotFoundException("No book found!");
            }
        }
    }

    @Transactional
    ///increase, decrease
    public CartRecordDTO updateCart(UserDetails userDetails, Long cartRecordId, Integer quantity) throws ResourceNotFoundException, NotEnoughStockException {
        List<CartRecord> cartRecords = recordRepository.findCartRecordsWithStock(userDetails.getUser().getUserId(), Collections.singletonList(cartRecordId));
       /*
       for higher reusability of the method findCartRecordsWithStock, which takes a List<> and returns a List<>,
       I wrap the cartRecordId parameter into a List<> and pass it to the method as parameter,
       hence the type of the return value
       */
        if (cartRecords.size() == 1) {
            CartRecord cartRecord = cartRecords.get(0);
            int stock = cartRecord.getBook().getStockRecords().get(0).getQuantity();
            //if enough quantity or if the request is to decrease the quantity (when a product suddenly out of stock)
            if (quantity <= stock || quantity < cartRecord.getQuantity()) {
                cartRecord.setQuantity(quantity);
                recordRepository.save(cartRecord);
                CartRecordDTO cartRecordDTO = CartRecordDTO.fromCartRecord(cartRecord);
                cartRecordDTO.getBook().setStock(cartRecord.getBook().getStockRecords().get(0).getQuantity());
                return cartRecordDTO;
            }
            throw new NotEnoughStockException("Not enough stock");
        }
        //
        throw new ResourceNotFoundException("No such item in cart!");
    }

    public List<CartRecordDTO> getCheckedItems(UserDetails userDetails, List<CartController.RequestData> requestData) {
        List<CartRecord> cartRecords = cartRecordRepository.findCartRecordsWithStock(userDetails.getUser().getUserId(), requestData.stream().map(CartController.RequestData::getCart_record_id).toList());
        //some items in the data have been deleted. throw an error
        if (cartRecords.size() != requestData.size()) {
            throw new ResourceNotFoundException("Cart Changed!");
        }
        //checking for stock. Use a map to leverage instant get() method.
        List<CartRecordDTO> cartRecordDTOs = new ArrayList<>();
        Map<Long, Integer> requestDataMap = new HashMap<>();
        for (CartController.RequestData data : requestData) {
            requestDataMap.put(data.getCart_record_id(), data.getQuantity());
        }
        for (CartRecord cartRecord : cartRecords) {
            //calculate stock for each record. Then compare to the quantity sent in the request (NOT THE QUANTITY IN THE DATABASE)
            int stock = cartRecord.getBook().getStockRecords().get(0).getQuantity();
            //same user and enough stock
            if (cartRecord.getUser().getUserId() == userDetails.getUser().getUserId() && stock >= requestDataMap.get(cartRecord.getCartRecordId())) {
                CartRecordDTO cartRecordDTO = CartRecordDTO.fromCartRecord(cartRecord);
                cartRecordDTOs.add(cartRecordDTO);
            } else {
                throw new ResourceNotFoundException("");
            }
        }
        return cartRecordDTOs;
    }

}
