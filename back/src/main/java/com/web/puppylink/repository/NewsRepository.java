package com.web.puppylink.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.puppylink.model.News;

public interface NewsRepository  extends JpaRepository<News,String>{
	List<News> findNewsAllByOrderByNewsNo();
}
