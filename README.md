# simpleWebServer
用java实现一个简单的Web服务器容器
SimpleWebServer
## 1.1 介绍
基于Java实现的一个简单仿TomCat的Web容器，允许建立一定的连接，能够获取一些简单的请求和获取返回静态资源，能处理简单的动态资源请求。
## 1.2 类介绍
1. Container
容器类，用于运行整个web server的容器，所有连接均在这里被进行。
2. RequestTask
请求任务类，每个socket建立连接后，会在容器中包装为RequestTask，然后分配一个线程执行此次的HTTP请求。
3. Request
对于一个连接所进行的http请求，要从数据流中提取请求报文，然后封装为响应的Request，送给后续的环节进行报文内容简单获取和处理。因此此接口简单的规定了以下要求：
- 要能判断此次请求为静态请求还是动态请求
- 要可以获取到报文中的请求头
- 要可以获取到报文中的请求体
- 要可以获取到报文中的请求参数
4. Response
对于一个连接所进行的http请求，在进行完所有的处理环节后，要封装为一个合法的HTTP报文返回给客户端，因此此接口简单的规定了以下要求：
- 要可以设置请求头
- 要可以设置请求体的内容
- 要可以将整个响应封装为合法的字符串报文
5. HttpUtil
此类用于将从socket中获取到的报文构建为Request和Response，支持的方法有：
- buildRequest：将数据流中的报文进行解析，从中获取此次HTTP请求的请求方法、请求路径、是否是静态资源请求、请求头、请求体等信息，包装为一个Request后返回。
- buildResponse：构建一个请求流程中的Response
- buildStaticResponse：构建一个静态资源的响应
- execute：用于执行动态资源的HTTP请求
6. SimpleServer
此类是用于实现web服务的接口类，要想完成对应的web请求，只需要在规定的路径下创建自己的服务，然后去实现这个接口，并打上注解即可。
7. ServerAnnotation
一个注解类，用于实现扫描固定路径下的所有服务，然后获取注解上的请求映射，后续有对应的请求路径过来时可以进行运行。
## 2.1 实现需求
需要明确的是，一个基本的web服务器一定要有的功能：
1. 能对外开发连接，运行一定数量的客户端同时进行连接
2. 要能响应静态资源的HTTP请求
3. 能处理动态资源的HTTP请求
## 2.2 实现思路

暂时无法在飞书文档外展示此内容

## 3.1 测试
能够支持简单的动态请求和静态资源请求，由于项目比较简单，所以默认配置了跨域的请求头，并且该web server只支持简单的动态请求，暂未实现从请求体中获取参数。
1. 请求简单的动态资源：http://localhost:8080/user?userid=dasf&password=dafsasg
[图片]
2. 请求存在的静态html网页：http://localhost:8080/index.html
[图片]
3. 请求不存在的静态html网页：http://localhost:8080/dasf.html
[图片]
4. 请求不存在的动态资源：http://localhost:8080/test
[图片]
## 4.1 实现自己的web server
继承SimpleServer接口，同时在类上标注ServerAnnotation注解，然后在com.simplewebserver.server路径下创建该类即可。
[图片]

飞书文档链接：https://i6xbnkktn0.feishu.cn/docx/XLiRdx8fuon9prxU9HDc5EuInug?from=from_copylink
