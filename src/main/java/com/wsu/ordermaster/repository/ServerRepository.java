package com.wsu.ordermaster.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wsu.ordermaster.model.Server;

public interface ServerRepository extends JpaRepository<Server, Integer> {

    @Query(nativeQuery = true, value = "Select s.server_id AS id, s.first_name AS firstName, s.last_name AS lastName, "
            + "s.availability AS availability, "
            + "from server s ")
            //left join work_order wo on wo.work_order_number  = (Select max(wo1.work_order_number) from "
            //+ "work_order wo1 where wo1.customer_id=c.customer_id) left join work_order_status wos on "
            //+ "wo.work_order_status_code = wos.work_order_status_code where (:search IS NULL OR (c.customer_first_name like %:search% "
            //+ "OR c.customer_last_name like %:search% OR c.customer_city like %:search% OR c.customer_state like %:search% "
            //+ "OR wos.work_order_status_desc like %:search%))")
    Page<Object[]> findBySearch(String search, Pageable pageable);

    boolean existsByEmail(String email);

}
