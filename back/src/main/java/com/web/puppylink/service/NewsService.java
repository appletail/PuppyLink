package com.web.puppylink.service;

import java.io.IOException;
import java.util.List;

import com.web.puppylink.model.News;

public interface NewsService {
	
	List<News> getNews();
	List<News> getNewsData() throws IOException;
}
