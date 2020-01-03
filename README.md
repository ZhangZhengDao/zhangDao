##项目启动第一天
创建项目  
部署在git中  
##前端框架  
bootstrap 可借助网址观看api  
网址：https://www.bootcss.com/  
网站头部搭建完成
##发送post请求运用了新模式 okhttp
网址：https://square.github.io/okhttp/  
发送post请求方式  
public static final MediaType JSON
    = MediaType.get("application/json; charset=utf-8");

OkHttpClient client = new OkHttpClient();

String post(String url, String json) throws IOException {
  RequestBody body = RequestBody.create(json, JSON);
  Request request = new Request.Builder()
      .url(url)
      .post(body)
      .build();
  try (Response response = client.newCall(request).execute()) {
    return response.body().string();
  }
}
