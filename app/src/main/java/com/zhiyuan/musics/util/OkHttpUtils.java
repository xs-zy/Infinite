package com.zhiyuan.musics.util;

import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Administrator on 2018/12/12.
 */

public class OkHttpUtils {

    private String returnData = "";

    public String okHttpGet(String music,int page){
        //构造一个Request对象，参数最起码有个url，
        // 当然你可以通过Request.Builder设置更多的参数比如：header、method等。
        final Request request = new Request.Builder()
                .url("http://musicmini.baidu.com/app/search/searchList.php?qword="+music+"&ie=utf-8&page="+page)
                .build();
        String response = getResponse(request);
        return response;
    }


    public String okHttpPost(String MENU,String COOK_KEY,String COOK_URL_POST) {
        RequestBody body = new FormEncodingBuilder()
                .add("menu", MENU)
                .add("key", COOK_KEY)
                .build();
        //构造一个Request对象，参数最起码有个url，
        // 当然你可以通过Request.Builder设置更多的参数比如：header、method等。
        final Request request = new Request.Builder()
                .url(COOK_URL_POST)
                .post(body)
                .build();
        String response = getResponse(request);
        return response;
    }


    public String getResponse(final Request request){
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //通过request的对象去构造得到一个Call对象，类似于将你的请求封装成了任务，
        // 既然是任务，就会有execute()和cancel()等方法
        Call call = mOkHttpClient.newCall(request);
        //以异步的方式去执行请求,所以我们调用的是call.enqueue，将call加入调度队列，
        // 然后等待任务执行完成，我们在Callback中即可得到结果。
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Request request, IOException e)
            {
                Log.d("xuezhiyuan","fail");
                returnData = "fail";
            }
            @Override
            public void onResponse(final Response response) throws IOException
            {
                //String responseJSON = request.urlString();
                final String responseJSON =  response.body().source().readUtf8();
                //onResponse执行的线程并不是UI线程，如果你希望操作控件，还是需要使用handler等
                Log.d("xuezhiyuan",responseJSON);
                returnData = responseJSON;
            }
        });
        return  returnData;
    }

}
