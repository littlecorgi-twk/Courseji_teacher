package com.example.middle.logic.dao;

import com.baidu.mapapi.search.sug.SuggestionResult;

import java.util.List;

public class passedDataHelp {

    public interface passedStartTime {

        void noCustomPassed(String startTime, int position);

        void customPassed(String startTime, long milliseconds);
    }

    public interface passedEndTime {
        void passed(String endTime, long duration);
    }

    public interface passedLocation {
        void passed(String placeName,String Lat,String Ing);
    }

    public interface passedTitle {
        void passed(String title);
    }
    public interface passedBugSearchData{
        void passed(List<SuggestionResult.SuggestionInfo> list);
    }


}
