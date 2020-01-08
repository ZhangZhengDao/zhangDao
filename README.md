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
###使用mybatis.generator
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <!--添加mybase依赖，更方便数据库的调用和编写-->
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.4.0</version>
                <dependencies>
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>5.1.47</version>
                        <scope>runtime</scope>
                    </dependency>
                </dependencies>
            </plugin>
            <!--同样也要再次引入当前数据库-->
        </plugins>
    </build>  
       
       需要引入以上配置文件，并且在根目录新建generatorConfig.xml 
       
       genratorConfig.xml 需要以下配置
    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE generatorConfiguration
            PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
            "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
    
    <generatorConfiguration>
    
        <context id="DB2Tables" targetRuntime="MyBatis3">
    
            <plugin type="org.mybatis.generator.plugins.RowBoundsPlugin"></plugin>
            <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                            connectionURL="jdbc:mysql://localhost:3306/Test?useUnicode=true&amp;characterEncoding=utf8"
                            userId="root"
                            password="214834">
            </jdbcConnection>
    
            <javaTypeResolver>
                <property name="forceBigDecimals" value="false"/>
            </javaTypeResolver>
    
            <javaModelGenerator targetPackage="cn.zhang.com.model" targetProject="src/main/java">
                <property name="enableSubPackages" value="true"/>
                <property name="trimStrings" value="true"/>
            </javaModelGenerator>
    
            <sqlMapGenerator targetPackage="mapper" targetProject="src/main/resources">
                <property name="enableSubPackages" value="true"/>
            </sqlMapGenerator>
    
            <javaClientGenerator type="XMLMAPPER" targetPackage="cn.zhang.com.mapper"
                                 targetProject="src/main/java">
                <property name="enableSubPackages" value="true"/>
            </javaClientGenerator>
    
            <table tableName="user" domainObjectName="User"></table>
        </context>
    </generatorConfiguration>
  需要在命令行运行此段命令mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate  
   需要在配置文件中添加    
   mybatis.type-aliases-package=cn.zhang.com.model    
   mybatis.mapper-locations=classpath:mapper/*.xml  
   ###Example详细使用可从一下链接观看  
   https://blog.csdn.net/qq_35174296/article/details/81389017
