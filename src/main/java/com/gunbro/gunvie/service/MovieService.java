package com.gunbro.gunvie.service;

import com.gunbro.gunvie.model.jpa.Movie;
import com.gunbro.gunvie.module.API;
import com.gunbro.gunvie.module.Parse;
import com.gunbro.gunvie.repository.MovieRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class MovieService {


    @Autowired
    MovieRepository movieRepository;

    public List<Movie> getMovieData(String targetDate) {
        List<Movie> result = movieRepository.findByRangeDate(Parse.StringToLocalDateParse(targetDate));
        if(!result.isEmpty()) {
            return result;
        } else {
            System.out.println("DB에 값이 없습니다! 한국영화진흥원 API를 통해 값을 가져옵니다..");
            try {
                result = getMovieDataViaAPI(targetDate);
            } catch (ParseException e) {
                System.out.println("JSON Parsing을 실패했습니다! API쪽에서 문제가 생긴 것 같습니다....");
                throw new RuntimeException(e);
            }
            return result;
        }
    }


    public List<Movie> getMovieDataViaAPI(String targetDate) throws ParseException {
        //TODO : API fetch 실패시 > 예외처리 로직 필요함
        API api = new API();
        Map<String,String> queryParam = new HashMap<>();
        queryParam.put("key", com.gunbro.gunvie.config.enumData.API.getKEY());
        queryParam.put("targetDt", targetDate);
        String getResult = api.getDataAPI("json", com.gunbro.gunvie.config.enumData.API.getURL(),queryParam);
        //String testResultJSON = "{\"boxOfficeResult\":{\"boxofficeType\":\"일별 박스오피스\",\"showRange\":\"20230130~20230130\",\"dailyBoxOfficeList\":[{\"rnum\":\"1\",\"rank\":\"1\",\"rankInten\":\"0\",\"rankOldAndNew\":\"OLD\",\"movieCd\":\"20228555\",\"movieNm\":\"더 퍼스트 슬램덩크\",\"openDt\":\"2023-01-04\",\"salesAmt\":\"362220546\",\"salesShare\":\"23.8\",\"salesInten\":\"-658228641\",\"salesChange\":\"-64.5\",\"salesAcc\":\"20268796211\",\"audiCnt\":\"36042\",\"audiInten\":\"-62222\",\"audiChange\":\"-63.3\",\"audiAcc\":\"1958764\",\"scrnCnt\":\"820\",\"showCnt\":\"2663\"},{\"rnum\":\"2\",\"rank\":\"2\",\"rankInten\":\"0\",\"rankOldAndNew\":\"OLD\",\"movieCd\":\"20190808\",\"movieNm\":\"교섭\",\"openDt\":\"2023-01-18\",\"salesAmt\":\"240175650\",\"salesShare\":\"15.8\",\"salesInten\":\"-544355834\",\"salesChange\":\"-69.4\",\"salesAcc\":\"14956754901\",\"audiCnt\":\"25085\",\"audiInten\":\"-50801\",\"audiChange\":\"-66.9\",\"audiAcc\":\"1465051\",\"scrnCnt\":\"857\",\"showCnt\":\"3244\"},{\"rnum\":\"3\",\"rank\":\"3\",\"rankInten\":\"0\",\"rankOldAndNew\":\"OLD\",\"movieCd\":\"20225061\",\"movieNm\":\"아바타: 물의 길\",\"openDt\":\"2022-12-14\",\"salesAmt\":\"323876273\",\"salesShare\":\"21.3\",\"salesInten\":\"-699917624\",\"salesChange\":\"-68.4\",\"salesAcc\":\"131951931479\",\"audiCnt\":\"23417\",\"audiInten\":\"-48630\",\"audiChange\":\"-67.5\",\"audiAcc\":\"10382387\",\"scrnCnt\":\"737\",\"showCnt\":\"1755\"},{\"rnum\":\"4\",\"rank\":\"4\",\"rankInten\":\"3\",\"rankOldAndNew\":\"OLD\",\"movieCd\":\"20229551\",\"movieNm\":\"상견니\",\"openDt\":\"2023-01-25\",\"salesAmt\":\"124595452\",\"salesShare\":\"8.2\",\"salesInten\":\"-143374945\",\"salesChange\":\"-53.5\",\"salesAcc\":\"1548641404\",\"audiCnt\":\"12695\",\"audiInten\":\"-13137\",\"audiChange\":\"-50.9\",\"audiAcc\":\"157901\",\"scrnCnt\":\"598\",\"showCnt\":\"1887\"},{\"rnum\":\"5\",\"rank\":\"5\",\"rankInten\":\"-1\",\"rankOldAndNew\":\"OLD\",\"movieCd\":\"20214823\",\"movieNm\":\"유령\",\"openDt\":\"2023-01-18\",\"salesAmt\":\"101018546\",\"salesShare\":\"6.6\",\"salesInten\":\"-244807205\",\"salesChange\":\"-70.8\",\"salesAcc\":\"5564035027\",\"audiCnt\":\"10345\",\"audiInten\":\"-22349\",\"audiChange\":\"-68.4\",\"audiAcc\":\"542008\",\"scrnCnt\":\"645\",\"showCnt\":\"1459\"},{\"rnum\":\"6\",\"rank\":\"6\",\"rankInten\":\"-1\",\"rankOldAndNew\":\"OLD\",\"movieCd\":\"20229205\",\"movieNm\":\"메간\",\"openDt\":\"2023-01-25\",\"salesAmt\":\"105000052\",\"salesShare\":\"6.9\",\"salesInten\":\"-201590119\",\"salesChange\":\"-65.8\",\"salesAcc\":\"1346089355\",\"audiCnt\":\"10242\",\"audiInten\":\"-18379\",\"audiChange\":\"-64.2\",\"audiAcc\":\"132756\",\"scrnCnt\":\"615\",\"showCnt\":\"1499\"},{\"rnum\":\"7\",\"rank\":\"7\",\"rankInten\":\"-1\",\"rankOldAndNew\":\"OLD\",\"movieCd\":\"20196478\",\"movieNm\":\"영웅\",\"openDt\":\"2022-12-21\",\"salesAmt\":\"92278488\",\"salesShare\":\"6.1\",\"salesInten\":\"-194319332\",\"salesChange\":\"-67.8\",\"salesAcc\":\"30300089731\",\"audiCnt\":\"9766\",\"audiInten\":\"-18317\",\"audiChange\":\"-65.2\",\"audiAcc\":\"3067312\",\"scrnCnt\":\"616\",\"showCnt\":\"1216\"},{\"rnum\":\"8\",\"rank\":\"8\",\"rankInten\":\"1\",\"rankOldAndNew\":\"OLD\",\"movieCd\":\"20228313\",\"movieNm\":\"오늘 밤, 세계에서 이 사랑이 사라진다 해도\",\"openDt\":\"2022-11-30\",\"salesAmt\":\"46091645\",\"salesShare\":\"3.0\",\"salesInten\":\"-57365352\",\"salesChange\":\"-55.4\",\"salesAcc\":\"10425281242\",\"audiCnt\":\"4464\",\"audiInten\":\"-5246\",\"audiChange\":\"-54\",\"audiAcc\":\"1010011\",\"scrnCnt\":\"243\",\"showCnt\":\"396\"},{\"rnum\":\"9\",\"rank\":\"9\",\"rankInten\":\"-1\",\"rankOldAndNew\":\"OLD\",\"movieCd\":\"20224109\",\"movieNm\":\"장화신은 고양이: 끝내주는 모험\",\"openDt\":\"2023-01-04\",\"salesAmt\":\"30989963\",\"salesShare\":\"2.0\",\"salesInten\":\"-141910802\",\"salesChange\":\"-82.1\",\"salesAcc\":\"7170198063\",\"audiCnt\":\"3382\",\"audiInten\":\"-14275\",\"audiChange\":\"-80.8\",\"audiAcc\":\"756523\",\"scrnCnt\":\"331\",\"showCnt\":\"415\"},{\"rnum\":\"10\",\"rank\":\"10\",\"rankInten\":\"2\",\"rankOldAndNew\":\"OLD\",\"movieCd\":\"20229518\",\"movieNm\":\"천룡팔부: 교봉전\",\"openDt\":\"2023-01-25\",\"salesAmt\":\"11778957\",\"salesShare\":\"0.8\",\"salesInten\":\"-13643029\",\"salesChange\":\"-53.7\",\"salesAcc\":\"130117575\",\"audiCnt\":\"1356\",\"audiInten\":\"-1135\",\"audiChange\":\"-45.6\",\"audiAcc\":\"13636\",\"scrnCnt\":\"238\",\"showCnt\":\"317\"}]}}";

        if(getResult == null) {
            //실패 로직
        }

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(getResult);

        JSONObject boxOfficeResult = (JSONObject) jsonObject.get("boxOfficeResult");
        //"showRange":"20230130~20230130" >> "20230130" 변환
        String showRange = boxOfficeResult.get("showRange").toString().substring(0,8);
        LocalDate rangeDate = Parse.StringToLocalDateParse(showRange);

        JSONArray dailyBoxOfficeList = (JSONArray) boxOfficeResult.get("dailyBoxOfficeList");
        for(int i = 0; i < dailyBoxOfficeList.size(); i++) {
            JSONObject result = (JSONObject) dailyBoxOfficeList.get(i);

            Movie movie = new Movie();
            movie.setRangeDate(rangeDate);
            movie.setName(result.get("movieNm").toString());
            movie.setRankMv(Integer.parseInt(result.get("rank").toString()));
            movie.setMovieCd(result.get("movieCd").toString());
            movie.setSalesAcc(result.get("salesAcc").toString());
            movie.setAudiAcc(result.get("audiAcc").toString());
            movie.setAudiCnt(result.get("audiCnt").toString());
            movie.setOpenDt(result.get("openDt").toString());
            movieRepository.save(movie);
        }
        List<Movie> targetMovieData = movieRepository.findByRangeDate(Parse.StringToLocalDateParse(targetDate));
        return targetMovieData;
    }

}
