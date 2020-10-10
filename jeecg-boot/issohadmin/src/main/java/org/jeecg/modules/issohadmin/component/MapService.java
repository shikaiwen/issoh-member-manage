package org.jeecg.modules.issohadmin.component;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlException;
import org.jeecg.modules.issohadmin.member.entity.IssohMember;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class MapService {


    @Value("${googlemap.apikey}")
    private String apiKey;


    public void fillIssohMemberAddress(IssohMember member){
        Double[] geoInfo = this.getGeoInfo(member.getAddress());
        member.setAddressLng(geoInfo[0]);
        member.setAddressLat(geoInfo[1]);
    }
    /**
     * 住所で住所のgeo（マップを書くに使う）情報を取得する
     * @param address　例：東京都江東区大島7-18-12
     */
    public Double[] getGeoInfo(String address){

        // lnt lat
        Double [] result = new Double[2];
        if (StringUtils.isEmpty(address)) {
            return result;
        }

//        Map<String, String> map = MapUtil.builder(address, "UTF8")
//                .put("key", ).build();

        Map<String, Object> map = new HashMap<>();
        map.put("address", address);
        map.put("key", apiKey);
//        map.put("region", "jp");
//        map.keySet().forEach(key->{
//            try {
//                String utf8 = URLEncoder.encode(map.get(key).toString(), "UTF8");
//                map.put(key, utf8);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        });


        try{
            String s = HttpUtil.get("https://maps.googleapis.com/maps/api/geocode/json", map);
            JSONObject jsonObject = JSON.parseObject(s);
            if(jsonObject != null && "OK".equals(jsonObject.getString("status"))){
                JSONObject location = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                Double lng = location.getDouble("lng");
                Double lat = location.getDouble("lat");
                result[0] = lng;
                result[1] = lat;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;

    }

    public static void main(String[] args) {
        new MapService().getGeoInfo("東京都江東区大島7-18-12");
    }
}
