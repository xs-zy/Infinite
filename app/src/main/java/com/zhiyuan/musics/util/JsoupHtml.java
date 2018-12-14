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
        Element element1 = element.get(0);
        Elements tr = element1.select("tr");
        Element element4 = tr.get(2);
        Elements th1 = element4.select("th");
        String id = th1.select("i.checkboxI>input").attr("id");
        Log.d("xuezhiyuan",id);
    }
}
