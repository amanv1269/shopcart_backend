package com.ecommerce.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Address;

public interface AddressReposatory extends JpaRepository<Address, Long> {

}
