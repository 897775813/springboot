# 一、Spring Boot 入门

## 1、Spring Boot 简介

	- 简化Spring应用开发的一个框架

	- 整个Spring技术栈的一个大整合

	- J2EE开发的一站式解决方案

	- 官方文档：https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/

	- 前端页面素材：https://getbootstrap.com

## 2、微服务

由Martin fowler在其博客上提出了微服务思想

微服务：架构风格

​	简而言之，微服务架构风格是将单个应用程序开发为**一套小型服务的方法**，每个**小型服务**都**在自己的流程中运行，**并与轻量级机制（通常是HTTP资源API）进行通信。这些服务**围绕业务功能构建**， **可**通过全自动部署机制**独立部署**。有一个**集中管理的最低限度的**这些服务，可以用不同的编程语言和使用不同的数据存储技术。

单体应用：ALL IN ONE 

## 3、统一环境

- jdk 1.8

- maven 3.x

- IDEA2017

- Spring Boot 1.5.9.RELEASE:1.5.9

  MAVEN 设置：

  给maven的setting.xml配置文件profiles标签添加

  ```xml
  <profile>
        <id>jdk-1.8</id>

        <activation>
  	  	<activeByDefault>true</activeByDefault>
          <jdk>1.8</jdk>
        </activation>
  	  <properties>
  	  	<maven.compiler.source>1.8</maven.compiler.source>
  		<maven.compiler.target>1.8</maven.compiler.target>
  		<maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
  	  </properties>
  </profile>
  ```

  ​

## 4、Spring Boot HelloWorld

一个功能：

​	浏览器发送hello请求，服务器接收请求并处理，响应Hello World 字符串；

#### 	1、创建maven工程：（jar）

#### 	2、导入Spring Boot相关依赖

```java
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.9.RELEASE</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
```

#### 	3、编写一个主程序：启动Spring Boot应用

```java
package com.briup.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication 标注一个主程序类，说明这是一个Spring Boot应用
 */
@SpringBootApplication
public class HelloWorldMain {
    public static void main(String[] args) {

        //Spring应用启动起来
        SpringApplication.run(HelloWorldMain.class,args);
    }
}

```

#### 4、编写相关的Controller、Service

```java
package com.briup.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "hello world";
    }
}

```



#### 5、运行主程序测试

#### 6、简化部署

```java
    <!--spring boot打包插件，将应用打包为一个可执行jar包-->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

将这个应用打成jar，直接使用java -jar命令执行，可以没有tomcat环境，jar包中自带

## 5、Hello World探究

### 		1、POM文件

#### 1、父项目

```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.9.RELEASE</version>
    </parent>
```

它的父项目为：spring-boot-dependencies

``` xml
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-dependencies</artifactId>
		<version>1.5.9.RELEASE</version>
		<relativePath>../../spring-boot-dependencies</relativePath>
	</parent>
```

用来真正管理Spring Boot应用里面的所有依赖版本；

Spring Boot的版本仲裁中心，

以后我们导入依赖默认是不需要写版本：（没有在dependencies中管理的依赖仍然需要些版本）

#### 2、导入的依赖

``` xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
```

​	导入web模块正常运行所依赖的组件

 Spring Boot将所有功能场景抽取出来，做成一个个的starters（启动器），只需要在项目里引入这些启动器，相关场景所有依赖都会注入

####  3、主程序类，主入口类

@SpringBootApplication：Spring Boot应用标注在某个类上说明这个类是Spring Boot的配置类，SpringBoot就应该运行这个类的main方法来启动SpringBoot应用

``` java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
		@Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
```

@SpringBootConfiguration:Spring Boot配置类；

​	标注在某个类上，表示这是一个Spring Boot的配置类

​	@Configuration：配置类上来标注这个注解

@EnableAutoConfiguration：开启自动配置功能

​	

```
@AutoConfigurationPackage
@Import(AutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration
```

​	 @AutoConfigurationPackage：自动配置包

​		

```
@Import(AutoConfigurationPackages.Registrar.class)
public @interface AutoConfigurationPackage
```

​	Spring的底层注解@Import，给容器中导入一个组件；导入的组件由AutoConfigurationPackages.Registrar.class；

​	`将主配置类(@SpringBootApplication标注的类)的所在包及子包下面的所有组件扫描到Spring容器里。`

EnableAutoConfiguration.class:

```
@Import(AutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration
```

给容器导入选择器：

AutoConfigurationImportSelector：

将所有需要导入的组件以全类名的方式返回；这些组件就会被添加到容器中。会给容器导入非常多的自动配置类。

## 6、快速创建Spring Boot

STS插件创建，或者官网创建。

- resource文件夹目录结构
  1. static：静态资源：js、css、images
  2. templates：模板页面（Spring Boot默认从jar包使用嵌入式tomcat，默认不支持JSP页面）；可以使用模板引擎（freemarker、thymeleaf）
  3. application.properties：Spring Boot应用的配置文件；


# 二、配置文件

## 1、配置文件

使用全局的配置文件，固定名称：

- application.propertie
- application.yaml

配置文件作用：修改默认配置

YAML：以数据为中心

Example：

YAML

```yaml
server:
  port: 8081
```

XML

```
<server>
	<port>8081</port>
</server>
```

##2、YAML语法：

### 1、基本语法

- 'key：[空格]value':表示一堆键值对（空格必须有）
- 以空格的缩进来控制层级关系:只要是左对齐的一列数据，都是同一个层级
- 属性和值大小写敏感

### 2、值得写法

#### 1、字面量： 普通值（数字、字符串、boolean）：

​	格式：key:[空格]value

​	字符串默认不用加上单引号或者双引号；

​	"" : 双引号，不会转义字符串里面的特殊字符；

​	''  : 单引号，会转义特殊字符； 

#### 2、对象、Map（属性和值）（键值对）：

​	格式：key:[空格]value

​	Example:

​	friend对象	

```yaml
friend:
    lastname: zhangsan
    age: 20
-------------------------------
行内写法
-------------------------------
friend: {lastname: zhangsan,age: 20}
```

####3、数组（List、Set）：

​	格式：用 - 值表示数组中的一个元素

​	数组名：

​            -[空格]paramter1,

​	    -[空格]paramter2,

​	    -[空格]paramter3,

​	Example：

```yaml
pets:
 - cat
 - dog
 - pig
-------------------------------
行内写法
-------------------------------
pets: [cat,dog,pig]
```

##### 1、@ConfigurationProperties(prefix="name")

​	告诉SpringBoot将本类中的所有属性和配置文件中相关配置进行绑定

​	只有这个组件是容器中的组件，才能容器提供ConfigurationProperties功能

所以要添加@Component

在pom.xml文件中添加注解配置文件绑定提示依赖：

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-configuration-processor</artifactId>
	<optional>true</optional>
</dependency>
```

JavaBean：

```java
@Component
@ConfigurationProperties(prefix="person")
public class person{}
```

##### 2、不用@ConfigurationProperties配合配置文件进行赋值

用@Value("parameter_value\${key}从环境变量、配置文件获取值 \ #{SpEL}")（Spring底层方法）

##### 3、@Value和@ConfigurationProperties获取值区别

|                     | Value | ConfigurationProperties |
| ------------------- | ----- | ----------------------- |
| 支持松散语法绑定（例：_和-代替大写) | ×     | √                       |
| 支持SpEL              | √     | ×                       |
| 功能                  | 一个个指定 | 批量指定                    |
| JSR303数据校验          | ×     | √                       |
| 复杂类型封装              | ×     | √                       |

*JSR303数据校验：使用@Validated（校验注解)

Example:

```java
@Component
@ConfigurationProperties(prefix="people")
@Validated
public class people{
  @Email	//校验必须是Email格式字符串
  String lastName;
}
```

*配置文件yml和properties都能获取值

*如果说，我们只是在业务逻辑中需要获取配置文件中某项值，用@Value,如果说，我们专门编写一个JavaBean来和配置文件进行映射，使用@ConfigurationProperties

##### 4、@PropertySource&@ImportResource

###### 	1、@PropertySource

​	加载制定配置文件

​	格式:@PropertySource(value = "classpath:filename")

######         2、@ImportResource

​	导入Spring的配置文件

​	Spring Boot 没有Spring配置文件，不能自动识别自己编写的Spring文件

​	格式：@ImportResource(location = "classpath:filename")

* Spring Boot 推荐给容器中添加组件的方式：全注解方式

   1、配置类======Spring配置文件

   ​	@Configuration：指明当前类是配置类

   ​	<bean>对应@Bean:将方法返回值添加到容器中

#### 3、配置文件占位符

格式： ${object.parameter:[default_value]}

Example：

```xml
person.age=${random.int}
person.dog.name=${person.hello:hello} 
//dog的name获取person.hello属性的名字，如果hello不存在，则默认为hello，即:后面的值
```

#### 4、Profile

​	解决不同环境，不同配置文件转换问题。

##### 1、多profile文件

在主配置文件编写的时候，文件名可以是 application-{profile}.properties/yml.

创建多个配置文件，默认为application.properties。

##### 2、yml支持多文档块方式

用 --- 分割文档

Example:

```ym
server: 
	port: 8081
spring:
	profile: choose_profile_name
---
server:
	port: 8082
spring:
	profile: profile_name1
---
server:
	port: 8082
spring:
	profile: profile_name2
```



##### 3、激活制定profile

* 在默认配置文件中，配置spring.profile.acive=profile_name

* 或则命令行模式：

  java -jar jar_name.jar --spring.profile.acive=profile_name

  可以直接在测试的时候，配置传入命令行参数

* 虚拟机参数：-Dspring.profile.acive=profile_name

#### 5、配置文件加载位置

* springboot启动会扫描以下位置的application.properties \ yml文件作为默认配置文件
  - file: ./config/
  - file:./
  - classpath:/config/
  - classpath:/
  - 以上是按照优先级从高到低的顺序，所有位置的文件都会被加载，高优先级配置内容会覆盖低优先级配置内容。
  - 可以通过配置spring.config.location改变默认位置，项目打包好之后，用命令行参数来制定配置文件的新位置
  - 互补配置：相同内容采取高优先级文件内容，不同内容则都会加载

#### 6、外部配置加载顺序

​	Springboot可以从以下位置加载配置；优先级从高到低；高优先级配置覆盖低优先级配置内容，所有配置形成互补

 - 命令行参数 --[paramter]=value
 - 来自Java：comp/env的JNDI属性
 - Java系统属性(System.getProperties() )
 - 操作系统环境变量
 - RandomValuePropertySource配置的random.*属性值
 - jar包外部的application-{profile}.properties/yml(带spring.profile)配置文件
 - jar包内部的application-{profile}.properties/yml(带spring.profile)配置文件
 - jar包外部的application-{profile}.properties/yml(不带spring.profile)配置文件
- jar包内部的application-{profile}.properties/yml(不带spring.profile)配置文件
- @configuration注解类上的@p'ropertysource
- 通过@SpringApplication.setDefaultProperties指定的默认属性

#### 7、自动配置原理

配置文件到底能写什么？怎么写？

##### 1.自动配置原理：

  1. SpringBoot启动的时候加载主配置类，开启了自动配置功能。

  2. @EnableAutoConfiguration作用：

     利用EnableAutoConfigurationImportSelector给容器导入组件。

     * 详细看selectimport方法。

     * get CandidateConfigurations(annotationMetadate,attributes);获取候选的配置

       - SpringFactoriesLoader.loadFactoryNames()

       - 扫描所有jar包类路径下的 META-INF/spring.factories

       - 把扫描到的这些文件的内容包装成properties对象

       - 从properties中获取到EnableAutoConfiguration.class类（类名）对应的值，然后添加到容器中

         将META-INF/spring.factories的所有EnableAutoConfiguration的值加入到容器中。

     * 每一个自动配置类进行自动配置

  3. 以HttpEncodingAutoConfiguration为例解释自动配置原理：

       根据当前不同的判断条件，决定这个配置类是否生效

       ``` java
       @Configuration //表示这是一个配置类，以前编写的配置文件一样，也可以给容器中添加组件
       @EnableConfigurationProperties(HttpEncodingProperties.class)//启动指定类的ConfigurationProperties功能；
       //将配置文件中对应的值和HttpEncodingProperties绑定起来；
       @ConditionalOnWebApplication //Sping底层@Conditional注解，根据不同的条件，如果满足指定的条件，整个配置类里面的配置就会生效；判断当前应用是否是web应用，如果是，当前配置生效
       @ConditionalOnClass(CharacterEncodingFilter.class) //判断当前项目有没有这个类
       //CharacterEncodingFilter:Srping MVC中进行乱码解决的过滤器
       @ConditionalOnProperty(prefix = "spring.http.encoding",value="enabled",matchIfMissing = true)  
       //判断配置文件中是否存在某个配置，spring.http.encoding；如果不存在，判断也是成立的
       public class HttpEncodingAutoConfiguration{...}
       ```


    4. 所有在配置文件中嫩配置的属性都是在xxxxProperties类中封装的；

    5. 精髓：


         1. SpringBoot启动加载大量的自动配置类
         2. 判断需要的功能有没有自动配置类
         3. 看自动配置类中配置了哪些组件
         4. 给容器中自动配置类添加组件时候，从properties类中获取某些属性，我们可以在配置文件中指定这些属性的值
    
     xxxxAutoConfiguration:自动配置类；
    
     给容器添加组件
    
     xxxxProperties:封装配置文件中相关属性；

##### 2、细节

###### 1、@Conditional派生注解(Spring注解版原生@Conditional作用)

​	作用：必须是其指定条件成立，才给容器中添加组件，配置内容才生效

###### 2、@Conditional扩展

| @Conditional扩展注解                | 作用（判断是否满前当前条件）                 |
| ------------------------------- | ------------------------------ |
| @ConditionalOnJava              | 系统的java版本是否符合要求                |
| @ConditionalOnBean              | 容器中存在指定Bean                    |
| @ConditionalOnMissingBean       | 容器中不存在指定Bean                   |
| @ConditionalOnExpression        | 满足SpEL表达式指定                    |
| @ConditionalOnClass             | 系统中有指定类                        |
| @ConditionalOnMissingClass      | 系统中没有指定类                       |
| @ConditionalOnSingleCandidate   | 容器中只有一个指定的Bean，或者这个Bean是首选Bean |
| @ConditionalOnProperty          | 系统中指定的属性是否有指定的值                |
| @ConditionalOnResource          | 类路径下是否存在指定的资源文件                |
| @ConditionalOnWebApplication    | 当前是否是web环境                     |
| @ConditionalOnNotWebApplication | 当前不是web环境                      |
| @ConditionalOnJndi              | JNDI存在指定项                      |

自动配置类必须在一定条件下才能生效！！

我们怎么知道哪些自动配置类生效？

开启Debug模式：在配置文件配置debug=true，让控制台打印自动配置类的信息；

# 三、日志

## 1、日志框架

​	市面上常用的日志框架：

​	JUL、JCL、jboss-logging、logback、log4j、log4j2、sft4j...

​	

| 日志门面（日志抽象层）             | 日志实现                     |
| ----------------------- | ------------------------ |
| JCL、SLF4j、jboss-logging | Log4j、JUL、Log4j2、Logback |

左边选一个门面（抽象层）、右边选一个实现；

日志门面：SLF4J;

日志实现：Logback;



SpringBoot ：底层是Spring框架，Spring框架默认使用JCL；

​	SpringBoot选用SLF4J和Logback

## 2、SLF4j使用

### 1、如何在系统中使用SLF4j

以后开发的时候，日志记录方法的调用，不应该直接调用日志的实现类，而是调用日志抽象层里面的方法

官网：

[SLF4j官网]: https://www.slf4j.org/

在系统里面导入slf4j的jar和logback的实现jar

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {
  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(HelloWorld.class);
    logger.info("Hello World");
  }
}
```

每一个日志的实现框架都有自己的配置文件。使用slf4j以后，配置文件还是做成日志实现框架的配置文件；

### 2、遗留问题

a(slf4j+logback):Spring（common-logging）、Hibernate（jboss-logging）、MyBatis、xxx

统一日志记录，即使是别的框架和我一起统一使用slf4j进行输出？

查看官网legacy API

1、将系统中其他日志框架先排除

2、用中间包来替换原有日志框架

3、我们导入slf4j其他的实现

## 3、Spring Boot日志关系

``` xml
<dependency>               				 			<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter</artifactId>
	<version>2.0.5.RELEASE</version>
</dependency>
```

SpringBoot使用它来做日志功能:

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-logging</artifactId>
	<version>2.0.5.RELEASE</version>
</dependency>
```

总结：

​	1、Springboot底层也是使用slf4j+logback的方式进行日志记录

​	2、Springboot也把其他的日志都替换成了slf4j

​	3、中间的替换包：

```xml
  <dependencies>
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <version>1.2.3</version>
      <scope>compile</scope>
    </dependency>
    <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-to-slf4j</artifactId>
      <version>2.10.0</version>
      <scope>compile</scope>
    </dependency>
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>jul-to-slf4j</artifactId>
      <version>1.7.25</version>
      <scope>compile</scope>
    </dependency>
  </dependencies>
```

​	4、如果我们要引入其他框架，一定要把默认日志框架的jar移除

​		SrpingBoot能自动适配所有日志，而且底层使用slf4j+logback方式记录日志，引入其他框架的时候，只需要把这个框架依赖的日志框架排除掉；

## 4、日志使用

### 1、默认配置

Springboot默认帮我们配置好了日志

```java
	//记录器
	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void contextLoads() {
		
		//日志的级别
		//由低到高
		//可以调整需要输出的日志级别
		logger.trace("这是trace日志。。。");
		logger.debug("这是debug日志。。。");
		//SpringBoot默认给我们使用的是info级别的，没有指定别的级别默认规定级别：root级别
		logger.info("这是info日志。。。");
		logger.warn("这是warn日志");
		logger.error("这是error日志");
	}
```

可以在application.properties中配置日志的默认设置

```xml
logging.level.com.atguigu = [root_value]

#在当前磁盘的根路径下创建Spring文件夹和里面的log文件夹；使用spring.log作为默认文件
logging.path = [path]

#输出到指定的文件内，不指定路径，在当前项目下生成，如果指定则在指定的路径生成

logging.file=[path]

#在控制台输出的日志的格式

logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n

#指定文件中日志输出的格式
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n
```

日志输出格式：

| 参数          | 含义                         |
| ----------- | -------------------------- |
| %thread     | 表示线程名                      |
| %d          | 表示日期格式                     |
| %-5level    | 级别从做显示5个字符宽度               |
| %logger{50} | 表示logger名字最长50个字符，否则按照句点分割 |
| %msg        | 日志消息                       |
| %n          | 换行符                        |

### 2、指定配置

给类路径下放上每个日志框架自己的配置文件即可；SpringBoot就不使用他默认配置

| Logging System          | Customization                            |
| ----------------------- | ---------------------------------------- |
| Logback                 | `logback-spring.xml`, `logback-spring.groovy`, `logback.xml`, or `logback.groovy` |
| Log4j2                  | `log4j2-spring.xml` or `log4j2.xml`      |
| JDK (Java Util Logging) | `logging.properties`                     |

`logback.xml`:直接被日志框架识别

`logback-spring.xml`：日志框架不直接加载，日志的配置项由SpringBoot解析日志配置，可使用SpringBoot的高级Profile功能

```xml
<springProfile name="staging">
	<!-- configuration to be enabled when the "staging" profile is active -->
</springProfile>

<springProfile name="dev | staging">
	<!-- configuration to be enabled when the "dev" or "staging" profiles are active -->
</springProfile>

<springProfile name="!production">
	<!-- configuration to be enabled when the "production" profile is not active -->
</springProfile>
```

## 5、切换日志框架

可以按照slf4j日志适配图，进行相关的切换；

排除不需要的依赖，导入需要的依赖即可

切换为log4j2：

先排除starter-logging

导入starter-log4j2即可



# 四、Web开发

## 1、简介

 使用SpringBoot：

1、创建SpringBoot应用，选中我们需要的模块 

2、SpringBoot已经默认将这些场景已经配置好了，只需要在配置文件中指定少量配置就可以运行起来

3、自己编写业务代码



自动配置原理？

这个场景SpringBoot帮我们配置了什么？能不能修改？能修改哪些配置？能不能扩展？xxx

```xml
xxxxAutoConfiguration:帮我们给容器中自动配置组件
xxxxProperties:配置类封装配置文件的内容
```

## 2、SpringBoot对静态资源的映射规则

```java
@ConfigurationProperties(prefix = "spring.resources", ignoreUnknownFields = false)
public class ResourceProperties {
  //可以设置和静态资源有关的参数，缓存时间等
```



```java
WebMvcAutoConfig.class:


@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			if (!this.resourceProperties.isAddMappings()) {
				logger.debug("Default resource handling disabled");
				return;
			}
			Duration cachePeriod = this.resourceProperties.getCache().getPeriod();
			CacheControl cacheControl = this.resourceProperties.getCache()
					.getCachecontrol().toHttpCacheControl();
			if (!registry.hasMappingForPattern("/webjars/**")) {
				customizeResourceHandlerRegistration(registry
						.addResourceHandler("/webjars/**")
						.addResourceLocations("classpath:/META-INF/resources/webjars/")
						.setCachePeriod(getSeconds(cachePeriod))
						.setCacheControl(cacheControl));
			}
			String staticPathPattern = this.mvcProperties.getStaticPathPattern();
			if (!registry.hasMappingForPattern(staticPathPattern)) {
				customizeResourceHandlerRegistration(
						registry.addResourceHandler(staticPathPattern)
								.addResourceLocations(getResourceLocations(
										this.resourceProperties.getStaticLocations()))
								.setCachePeriod(getSeconds(cachePeriod))
								.setCacheControl(cacheControl));
			}
		}

	//首页配置
		@Bean
		public WelcomePageHandlerMapping welcomePageHandlerMapping(
				ApplicationContext applicationContext) {
			return new WelcomePageHandlerMapping(
					new TemplateAvailabilityProviders(applicationContext),
					applicationContext, getWelcomePage(),
					this.mvcProperties.getStaticPathPattern());
		}

	//配置图标
		@Configuration
		@ConditionalOnProperty(value = "spring.mvc.favicon.enabled", matchIfMissing = true)
		public static class FaviconConfiguration implements ResourceLoaderAware {

			private final ResourceProperties resourceProperties;

			private ResourceLoader resourceLoader;

			public FaviconConfiguration(ResourceProperties resourceProperties) {
				this.resourceProperties = resourceProperties;
			}

			@Override
			public void setResourceLoader(ResourceLoader resourceLoader) {
				this.resourceLoader = resourceLoader;
			}

			@Bean
			public SimpleUrlHandlerMapping faviconHandlerMapping() {
				SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
				mapping.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
				mapping.setUrlMap(Collections.singletonMap("**/favicon.ico",
						faviconRequestHandler()));
				return mapping;
			}

			@Bean
			public ResourceHttpRequestHandler faviconRequestHandler() {
				ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
				requestHandler.setLocations(resolveFaviconLocations());
				return requestHandler;
			}
```



1、所有/webjars/**，都去classpath:/META-INF/resources/webjars/找资源；

webjars: 以jar包方式引入静态资源

[webjars官网]: https://www.webjars.org/

![img](file:///C:\Users\Administrator\AppData\Roaming\Tencent\Users\897775813\QQ\WinTemp\RichOle\94ZL_N5K3X[AYPEJ12T2}~T.png)

URL:http://localhost:8080/webjars/jquery/3.3.1-1/jquery.js

2、“/** ” 访问当前项目的任何资源

注意：自动创建的项目中resources路径是根路径，包含在classpath中！！！

```prop
"classpath:/META-INF/resources/", "classpath:/resources/",
"classpath:/static/", 
"classpath:/public/",
"/":当前项目的根路径
```

3、欢迎页：静态资源文件下的所有index.html页面；被 ‘ /** ’ 映射

4、图标：所有的/**/favicon.ico都是在静态资源路径下找

## 3、模板引擎

JSP、Velocity、Freemarker、Thymeleaf；



SpringBoot推荐的Thymeleaf；

语法更简单、功能更强大！

### 1、引入Thymeleaf

```xml
		<!-- 默认2.1.6-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>
			<!-- 更改版本-->	
		<propertise>
			<thymeleaf.version>3.0.9.RELEASE</thymeleaf.version>
          <!-- 布局功能的程序支持，thymeleaf3主程序，layout2以上版本-->
			<thymeleaf-layout-dialect.version>2.2.2</thymeleaf-layout-dialect.version>
		</propertise>
```

