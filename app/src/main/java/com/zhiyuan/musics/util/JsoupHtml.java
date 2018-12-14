package com.zhiyuan.musics.util;

import android.util.Log;
import com.zhiyuan.musics.model.MusicBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

/**
 * Created by Administrator on 2018/12/12.
 */

public class JsoupHtml {
    MusicBean musicBean;
    public void getUrl(final String url) throws IOException {
        musicBean = new MusicBean();
                Document document = null;
                try {
                    document = Jsoup.connect(url).get();
                    Log.d("location",document.location());
                    Log.d("nodeName",document.nodeName());
                    Log.d("title",document.title());
                    Log.d("outerHtml",document.outerHtml());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //   分区解析
        Elements element = document.select("table.mainTable>tbody");
        for(Element element1 : element){
            Log.d("xuezhiyuan",element1.html());
            Elements tr = element1.select("tr");
            for(Element element2 : tr){
                Elements td = element2.select("td");
                for(Element element3 : td){

                }
            }
        }
        musicBean.setSongId(document.select("a.onclick").attr("id"));
    }
}
