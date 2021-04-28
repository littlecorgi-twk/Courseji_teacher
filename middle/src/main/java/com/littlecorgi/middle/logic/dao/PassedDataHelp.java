package com.littlecorgi.middle.logic.dao;

import com.baidu.mapapi.search.sug.SuggestionResult;
import java.util.List;

/**
 *
 */
public class PassedDataHelp {

    /**
     *
     */
    public interface PassedStartTime {

        void noCustomPassed(String startTime, int position);

        void customPassed(String startTime, long milliseconds);
    }

    /**
     *
     */
    public interface PassedEndTime {

        void passed(String endTime, long duration);
    }

    /**
     *
     */
    public interface PassedLocation {

        void passed(String placeName, String lat, String ing);
    }

    /**
     *
     */
    public interface PassedTitle {

        void passed(String title);
    }

    /**
     *
     */
    public interface PassedBugSearchData {

        void passed(List<SuggestionResult.SuggestionInfo> list);
    }
}
