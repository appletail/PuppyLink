package com.web.puppylink.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.PagingAndSortingRepository;

import com.web.puppylink.model.Member;
import com.web.puppylink.model.Foundation;

public interface FoundationRepository extends JpaRepository<Foundation, String> {

	Optional<Foundation>  findFoundationByBusinessNo(String businessNo);
}

//
//public class FoundationRepository extends PagingAndSortingRepository {
//
//}
