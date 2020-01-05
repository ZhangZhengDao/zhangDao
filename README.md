##项目启动第一天
创建项目  
部署在git中  
##前端框架  
bootstrap 可借助网址观看api  
网址：https://www.bootcss.com/  
网站头部搭建完成
###网页样式参考网址  
http://www.mawen.co/publish
##发送post请求运用了新模式 okhttp
网址：https://square.github.io/okhttp/  
发送post请求方式    

     public String getAccessToken(AccessTokenDTO accessTokenDTO) {
         MediaType mediaType = MediaType.get("application/json; charset=utf-8");
         OkHttpClient client = new OkHttpClient();
         RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
         Request request = new Request.Builder()
                 .url("https://github.com/login/oauth/access_token")
                 .post(body)
                 .build();
         try (Response response = client.newCall(request).execute()) {
             String string = response.body().string();
             String token = string.split("&")[0].split("=")[1];
             return token;
         } catch (Exception e) {
            log.error("getAccessToken error,{}", accessTokenDTO, e);
         }
         return null;
     }
                
                public GithubUser getUser(String accessToken) {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://api.github.com/user?access_token=" + accessToken)
                            .build();
                    try {
                        Response response = client.newCall(request).execute();
                        String string = response.body().string();
                        GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
                        return githubUser;
                    } catch (Exception e) {
                       log.error("getUser error,{}", accessToken, e);
                    }
                    return null;
                }
git登录以实现  
