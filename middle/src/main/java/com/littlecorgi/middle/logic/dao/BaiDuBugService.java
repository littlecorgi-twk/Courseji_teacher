package com.littlecorgi.middle.logic.dao;

import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

/**
 * 百度模糊搜索Service
 */
public class BaiDuBugService {

    private final PassedDataHelp.PassedBugSearchData passData;

    public BaiDuBugService(PassedDataHelp.PassedBugSearchData passedBugSearchData) {
        passData = passedBugSearchData;
    }

    OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
        @Override
        public void onGetSuggestionResult(SuggestionResult suggestionResult) {
            // 处理sug检索结果
            passData.passed(suggestionResult.getAllSuggestions());
        }
    };

    /**
     * 开始模糊搜索
     *
     * @param city 城市
     * @param key  key
     */
    public void startBugSearch(String city, String key) {
        SuggestionSearch suggestionSearch = SuggestionSearch.newInstance();
        suggestionSearch.setOnGetSuggestionResultListener(listener);
        suggestionSearch.requestSuggestion(new SuggestionSearchOption().city(city).keyword(key));
        suggestionSearch.destroy();
    }
}
