package com.littlecorgi.middle.logic.dao;

import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

public class BaiDuBugService {
    private final passedDataHelp.passedBugSearchData passData;
    public BaiDuBugService(passedDataHelp.passedBugSearchData passedBugSearchData) {
        passData = passedBugSearchData;
    }
    OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
        @Override
        public void onGetSuggestionResult(SuggestionResult suggestionResult) {
            //处理sug检索结果
            passData.passed(suggestionResult.getAllSuggestions());
        }
    };
    public void startBugSearch(String city,String key){
        SuggestionSearch mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(listener);
        mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
                .city(city)
                .keyword(key));
        mSuggestionSearch.destroy();
    }
}
