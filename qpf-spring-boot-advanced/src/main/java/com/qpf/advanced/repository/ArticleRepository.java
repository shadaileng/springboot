package com.qpf.advanced.repository;

import com.qpf.advanced.bean.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleRepository extends ElasticsearchRepository<Article, Integer> {
}
