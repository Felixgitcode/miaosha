# miaosha

基于IDEA+Maven+SSM框架的高并发商品秒杀项目,

使用技术：Springboot+redis+Mybatis+thymeleaf。

#目录
```java
一、框架搭建及部署：
- 1、Linux安装redis
- 2、IDEA集成依赖
- 3、application.properties配置信息

二、数据库的设计
- 1、表：
- 2、字段：

三、用户登录及分布式session
- 1、实现用户登录功能
- 2、分布式session

四、秒杀功能开发
- 1、商品详情页
- 2、秒杀功能实现
- 3、订单详情页

五、秒杀压测
- 1、安装Jmeter
- 2、Jmeter压测

六、页面高并发秒杀优化
- 1、页面缓存+对象缓存
- 2、页面静态化分离

七、服务级高并发秒杀优化
- 1、安装与集成RabbitMQ
- 2、秒杀接口优化

```

## 一、框架搭建及部署：

### 1、Linux安装redis

具体看我的博客：<https://blog.csdn.net/Felix_ar/article/details/89857260>

### 2、IDEA集成依赖

```java
    
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>

        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.1</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.6</version>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId> //druid连接池
            <version>1.1.14</version>
        </dependency>

        <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.38</version>
        </dependency>

        <dependency>
            <groupId>commons-codec</groupId>
            <artifactId>commons-codec</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.6</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
```

### 3、application.properties配置信息

```java
#thymeleaf
spring.thymeleaf.cache=false
spring.thymeleaf.servlet.content-type=text/html
spring.thymeleaf.enabled=true
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.mode=HTML5
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
# mybatis
mybatis.type-aliases-package=com.imooc.miaosha.domain
mybatis.configuration.map-underscore-to-camel-case=true
mybatis.configuration.default-fetch-size=100
mybatis.configuration.default-statement-timeout=3000
mybatis.mapperLocations = classpath:com/imooc/miaosha/dao/*.xml
# druid
spring.datasource.url=jdbc:mysql://localhost:3306/miaosha?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.filters=stat
spring.datasource.maxActive=2
spring.datasource.initialSize=1
spring.datasource.maxWait=60000
spring.datasource.minIdle=1
spring.datasource.timeBetweenEvictionRunsMillis=60000
spring.datasource.minEvictableIdleTimeMillis=300000
spring.datasource.validationQuery=select 'x'
spring.datasource.testWhileIdle=true
spring.datasource.testOnBorrow=false
spring.datasource.testOnReturn=false
spring.datasource.poolPreparedStatements=true
spring.datasource.maxOpenPreparedStatements=20
#redis
redis.host=xxxxxx
redis.port=6379
redis.timeout=3
redis.password=xxxxxx
redis.poolMaxTotal=10
redis.poolMaxIdle=10
redis.poolMaxWait=3
#static
spring.resources.add-mappings=true
spring.resources.cache.period= 3600
spring.resources.chain.cache=true
spring.resources.chain.enabled=true
spring.resources.chain.compressed=true
spring.resources.chain.html-application-cache=true
spring.resources.static-locations=classpath:/static/
```



## 二、数据库的设计

### 1、表：



![1558773712407](https://github.com/Felixgitcode/miaosha/blob/master/image/1558773712407.png)

### 2、字段：

![1558774479896](https://github.com/Felixgitcode/miaosha/blob/master/image/1558774479896.png)





## 三、用户登录及分布式session

### 1、实现用户登录功能

```java
LoginController.java
MiaoshaUserService.java
添加参数校验和全局异常处理
```

### 2、分布式session

> 使用Redis作为session存储容器，登录时将session信息存储至cookie客户端，同时服务端将session信息存至redis缓存，双重保障，接下来的接口调用直接可以获取到cookie中的token信息作为参数传递进来即可，如果发现token为空，则再从redis中获取，如果两者都为空，则说明session已过期。

```java
//登录
public boolean login(HttpServletResponse response, LoginVo loginVo) {
		if(loginVo == null){
			throw new GlobalException(CodeMsg.SERVER_ERROR);
		}
		String mobile = loginVo.getMobile();
		String formPass = loginVo.getPassword();
		//判断手机号是否存在
		MiaoshaUser user = getById(Long.parseLong(mobile));
		if(user == null){
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		//验证密码
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass,saltDB);
		if(!calcPass.equals(dbPass)){
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}

		//生成cookie
		String token = UUIDUtil.uuid();
		addCookie(response, token, user);
		/*redisService.set(MiaoshaUserKey.token, token, user);
		Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
		cookie.setMaxAge(MiaoshaUserKey.TOKEN_EXPIRE);
		cookie.setPath("/");
		response.addCookie(cookie);*/

		return true;
	}

	private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
       //将token写入redis缓存中
		redisService.set(MiaoshaUserKey.token, token, user);
		Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
		cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
	}

  //根据token获取user信息
   public MiaoshaUser getByToken(HttpServletResponse response, String token){
		if(StringUtils.isEmpty(token)){
			return null;
		}
		MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
		//延长有效期
		if(user != null){
			addCookie(response, token, user);
		}
		return user;
	}
```



## 四、秒杀功能开发

### 1、商品详情页

创建相应的controller、service以及dao，关键代码：

```java
@RequestMapping("/to_list")
    public String list(Model model,MiaoshaUser user) {
    	model.addAttribute("user", user);
    	//查询商品列表
    	List<GoodsVo> goodsList = goodsService.listGoodsVo();
    	model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }
 @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model,MiaoshaUser user,
    		@PathVariable("goodsId")long goodsId) {
    	model.addAttribute("user", user);
    	
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	model.addAttribute("goods", goods);
    	
    	long startAt = goods.getStartDate().getTime();
    	long endAt = goods.getEndDate().getTime();
    	long now = System.currentTimeMillis();
    	
    	int miaoshaStatus = 0;
    	int remainSeconds = 0;
    	if(now < startAt ) {//秒杀还没开始，倒计时
    		miaoshaStatus = 0;
    		remainSeconds = (int)((startAt - now )/1000);
    	}else  if(now > endAt){//秒杀已经结束
    		miaoshaStatus = 2;
    		remainSeconds = -1;
    	}else {//秒杀进行中
    		miaoshaStatus = 1;
    		remainSeconds = 0;
    	}
    	model.addAttribute("miaoshaStatus", miaoshaStatus);
    	model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }
```

### 2、秒杀功能实现

创建相应的controller、service以及dao，关键代码：

```java
@RequestMapping("/do_miaosha")
    public String list(Model model,MiaoshaUser user,
    		@RequestParam("goodsId")long goodsId) {
    	model.addAttribute("user", user);
    	if(user == null) {
    		return "login";
    	}
    	//判断库存
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	int stock = goods.getStockCount();
    	if(stock <= 0) {
    		model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
    		return "miaosha_fail";
    	}
    	//判断是否已经秒杀到了
    	MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
    	if(order != null) {
    		model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
    		return "miaosha_fail";
    	}
    	//减库存 下订单 写入秒杀订单
    	OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
    	model.addAttribute("orderInfo", orderInfo);
    	model.addAttribute("goods", goods);
        return "order_detail";
    }	
```

### 3、订单详情页

创建相应的controller、service以及dao



## 五、秒杀压测

### 1、安装Jmeter

官网自行下载

### 2、Jmeter压测

> 在5000个线程循环十次的压测下，对http://localhost:8080/goods/to_list进行压力测试，结果如下：
>
> QPS：1267，CPU负载约为：15，MySQL进程占用较高
>
> 结论：QPS较低，每秒中能处理的并发请求不高，CPU负载过高，MySQL数据库访问频繁，造成性能瓶颈。



## 六、页面高并发秒杀优化

### 1、页面缓存+对象缓存

```java
 @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response,
                       Model model, MiaoshaUser user) {
        model.addAttribute("user", user);
        //若有缓存则取缓存
        String html = redisService.get(GoodsKey.getGoodsList,"",String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);

        //return "goods_list";不直接返回文件，进行以下的页面缓存优化
       /*优化访问速度，应对高并发，可以把页面信息全部获取出来存到redis缓存中，
        这样每次访问就不用客户端进行渲染了，速度能快不少*/

        //手动渲染
        IWebContext ctx =new WebContext(request,response,
                request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list",ctx);
        if(!StringUtils.isEmpty(html)){//将渲染好的数据存入redis缓存，过期时间设为60s
            redisService.set(GoodsKey.getGoodsList,"",html);
        }
        return html;
    }
```

### 2、页面静态化分离

将商品详情、秒杀、订单页面静态化，在访问是，页面的静态化内容交给浏览器缓存，动态内容则直接从服务器获取，能有效提高访问速度，提升用户体验。
