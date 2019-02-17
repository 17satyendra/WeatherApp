package com.api.weather.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.weather.models.SearchHistory;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Integer> {

	List<SearchHistory> findAllByUserId(Integer userId);

}
