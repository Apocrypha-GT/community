package com.study.community.provider;

import com.alibaba.fastjson.JSON;
import com.study.community.dto.AccessTokenDTO;
import com.study.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String str =response.body().string();
                //access_token=79f5bd2be2c8ef8d9ca594ed8b1f6999485f884d&scope=user&token_type=bearer
                //分割str,得到access_token
                String[] split = str.split("&");
                String tokenstr = split[0];
                String token = tokenstr.split("=")[1];
                //等价于String token = str.split("&")[0].split("=")[1];
                return token;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //将String类型自动转换成java对象
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
        }
        return null;
    }
}
