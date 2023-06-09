package com.web.puppylink.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.web.puppylink.model.News;
import com.web.puppylink.repository.NewsRepository;

@Component("newsService")
public class NewsServiceImpl implements NewsService{
	
	private final NewsRepository newsRepository;
	private static String targetURL = "https://search.daum.net/search?w=news&nil_search=btn&DA=NTB&enc=utf8&cluster=y&cluster_page=1&q=%ED%95%B4%EC%99%B8%EC%9D%B4%EB%8F%99%EB%B4%89%EC%82%AC";
	
	public NewsServiceImpl(NewsRepository newsRepository){
		this.newsRepository = newsRepository;
	}

	@Override
	public List<News> getNewsData() throws IOException {
		List<News> newsList = new ArrayList<>();
		Document doc = Jsoup.connect(targetURL).get();
		
		Elements contents = doc.select("article ul.list_news li");

		for(Element content : contents) {
			String subject = content.select(".wrap_cont").select("a").text();						// 제목
			String cont = content.select(".wrap_cont").select("p").text();							// 내용
			String picURL = content.select(".thumb_bf").select("img").attr("abs:src");			    // 사진URL
			String newsURL = content.select(".wrap_cont").select("a").attr("abs:href");				// 뉴스URL
			if(cont!="" && picURL!="" && newsURL!="") {
				News news = News.builder()
						.subject(subject) 
						.content(cont) 
						.picURL(picURL)
						.newsURL(newsURL)
						.build();
				
				newsList.add(news);
			}
			
		}
		return newsList;
	}

	@Override
	public List<News> getNews() {
		return newsRepository.findNewsAllByOrderByNewsNo();
	}
	
}