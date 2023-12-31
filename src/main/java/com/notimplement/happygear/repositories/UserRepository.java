package com.notimplement.happygear.repositories;

import com.notimplement.happygear.entities.Order;
import com.notimplement.happygear.entities.OrderDetail;
import com.notimplement.happygear.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    User findByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);
    @Query("SELECT o FROM Order o WHERE o.user.username = :username")
    List<Order> findOrdersByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findUserByEmail(String email);
    @Query("SELECT od FROM OrderDetail od WHERE od.order.user.username = :username ORDER BY od.detailId DESC")
    List<OrderDetail> findAllOrderDetailByUsername(String username);
}
