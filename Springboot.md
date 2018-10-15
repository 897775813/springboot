# 一、Spring Boot 入门

## 1、Spring Boot 简介

	- 简化Spring应用开发的一个框架

	- 整个Spring技术栈的一个大整合

	- J2EE开发的一站式解决方案

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

