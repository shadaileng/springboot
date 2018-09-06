# <center>Spring Boot</center>

[TOC]

# 一、简介

> 简化`spring`应用开发的一个框架

> 整个`spring`栈的整合

> `JEE`开发的一站式解决方案

## 1. 微服务

将一个应用细化为一系列的功能模块,功能模块之间通过`Http`互联,可独立替换和升级.

## 2. 环境配置

- `jdk1.8`
- `maven-3.x`

    - 配置`conf/setting.xml`
    ```
    <profile>
      <id>jdk-1.8</id>
      <activation>
        <activateByDefault>true</activateByDefault>
        <jdk>1.8</jdk>
      </activation>
      <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
      </properties>
    </profile>
    ```
    - 添加阿里云镜像
    ```
    <mirror>
      <id>nexus-aliyun</id>
      <mirrorOf>*</mirrorOf>
      <name>Nexus aliyun</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public</url>
    </mirror>
    ```
- `spring-boot-1.5.9`
- `IntelliJ IDEA`: 在`License Server address`输入`http://idea.toocruel.net`,设置`Maven`

# 二、构建Spring Boot应用

## HelloWorld

1. 新建`Maven`工程,`pom.xml`文件添加以下代码

```
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

2. 新建主题程序类`com.qpf.MainApplication`,类添加注解`@SpringBootApplication`标注`Spring_Boot`主体程序,`main`方法启动主体程序

```
@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }

}
```

3. 添加`Controller`类
```
# com.qpf.controller.HelloController

@Controller
public class HelloController {
    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
```
> `@RestController`可以替代`@Controller`和`@ResponseBody`注解

```
@RestController
public class HelloController {
```


4. 启动主程序,在浏览器访问`http://localhost:8080/hello`

5. `pom.xml`文件添加以下代码

```
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```
6. 打包工程`jar`包,使用用`java -jar`的命令进行执行即完成工程部署

``` 
$ java -jar [projectName].jar
```

## 深究

1. `pom.xml`文件,添加父项目`spring-boot-starter-parent`,该项目的父项目`spring-boot-dependencies`管理`Spring Boot`的所有依赖

```
# 项目的父项目spring-boot-starter-parent
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.5.9.RELEASE</version>
</parent>

# spring-boot-starter-parent的父项目spring-boot-dependencies
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-dependencies</artifactId>
	<version>1.5.9.RELEASE</version>
	<relativePath>../../spring-boot-dependencies</relativePath>
</parent>
```
2. 依赖`spring-boot-starter-web`,是`web`模块启动器

    > `spring-boot-starter`是`Spring Boot`的场景启动器,导入场景所依赖的组件
```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```
3. `SpringBootApplication`运行类必须添加注解`@SpringBootApplication`,以标注该类是`Spring Boot`的主配置类,运行该类的`main()`方法启动`Spring Boot`应用.

```
@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
}
```

> `@SpringBootApplication`注解是已经添加了`SpringBoot`相关配置注解的接口

```
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    excludeFilters = {@Filter(
    type = FilterType.CUSTOM,
    classes = {TypeExcludeFilter.class}
), @Filter(
    type = FilterType.CUSTOM,
    classes = {AutoConfigurationExcludeFilter.class}
)}
)
public @interface SpringBootApplication {
```
- `@SpringBootConfiguration`: 标注`Spring Boot`配置类
- `@EnableAutoConfiguration`: 启动自动配置
    - 将主程序所在包以及子包扫描到`Spring`容器
    - 启动自动配置导入选择器,自动配置导入选择器将自动配置类导入容器

    > `JEE`整合方案和自动配置保存在`spring-boot-autoconfigure.jar`中



## 快速生成Spring Boot应用

- eclipse生成Spring Boot应用

    - `New` -> `Spring starter project` -> `Next` 

      -> `Name`-> `Next` 

      -> `Spring Boot Version: 1.5.15` -> `Available: web` -> `Next` 

      -> `finish`

- Idea

    - `Create New Project` -> `Spring Initializr` -> `Project SDK: 1.8` -> `Next`

      -> `Group: com.qpf` -> `Artifact: spring-boot-quick` -> `Next` 

      -> `Spring boot Version: 1.5.15` -> `Dependencies: web` -> `Next`

      -> `Finish`

自动生成的目录结构

```
/spring-boot-quick
	|-------/src
			|----/java
			 		|----/com.example.springbootquick
							|-------SpringBootQuickApplication.java
			 |----/resources
					|------/static
					|------/templates
					|------application.properties
	|--------/test
```

# 三、配置文件

`Spring Boot`的全局配置文件可以修改`SpringBoot`自动配置的默认值,配置文件有两种格式:

 - `application.properties`
 - `application.yaml`

## 配置文件值注入

> 导入配置文件处理器,在编写配置文件时有提示

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```
> Idea编写`properties`文件防止中文乱码,需要配置文件编码 

- `File` -> `Settings` -> `Editor` -> `File Encodings` -> `Project Encoding: UTF-8`, `Default encoding for properties files: UTF-8 √ Transparent native-ascii conversion`

### @ConfigurationProperties

1. 实体类, 注入的实体类添加`@Component`注解,添加到`Spring`容器,`@ConfigurationProperties`直接指向配置文件
```java
@Component
@ConfigurationProperties(prefix = "persion")
public class Persion {
    private String name;
    private Integer age;
    private Date birth;
    private Address address;
    private Map<String, Object> maps;
    private List<String> lists;
    // constractor getter setter toString
}

@Component
public class Address {
    private String province;
    private String city;
	// constractor getter setter toString
}
```

2. `@ConfigurationProperties`可以使用两种配置文件,(测试可知优先使用`yaml`,但不确定)

  - `yaml`配置文件

   ```yaml
   persion:
     name: qpf
     age: 26
     birth: 1992/05/10
     address:
       province: 广东
       city: 广州
     maps: {a: 1, b: 2}
     lists: [a, b]
   ```
   - `properties`配置文件
  ```
  persion.name=qpf
  persion.age=26
  persion.birth=1992/05/10
  persion.address.province=广东
  persion.address.city=广州
  persion.maps.a=1
  persion.maps.b=2
  persion.lists=1, 2
  ```
3. `test`类,使用`@SpringBootTest`测试,自动注入`Persion`类

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootQuickApplicationTests {
    @Autowired
    Persion persion;
    
    @Test
    public void contextLoads() {
        System.out.println(persion);
    }
}
```

4. 执行结果
```shell
# yaml配置文件
Persion{name='qpf', age=26, birth=Sun May 10 00:00:00 CST 1992, address=Address{province='广东', city='广州'}, maps={a=1, b=2}, lists=[a, b]}

# properties配置文件
Persion{name='qpf', age=26, birth=Sun May 10 00:00:00 CST 1992, address=Address{province='广东', city='广州'}, maps={b=2, a=1}, lists=[1, 2]}
```

> `JSR-303`数据校验

1. 实体类
```
@Component
@ConfigurationProperties(prefix = "persion")
@Validate
public class Persion {
    private String name;
    @Email
    private String email;
    // constractor getter setter toString
}
```
2. 配置文件
```yaml
persion:
 name: qpf
 email: qpf@qq.com
```

### @Value注入
`Spring`的底层注解,用于注入`bean`的属性,可以写入
- 字面量
- `${key}`: 从环境变量或配置文件中取值
- `#{spEL}`: `spring`的`el`表达式

1. 实体类,

```
@Component
public class Dog {
    @Value("${dog.name}")
    private String name;
    @Value("#{2 + 2}")
    private Integer age;
    @Value("true")
    private Boolean isHungury;
    // constractor getter setter toString
}
```
2. 配置文件

```yaml
dog:
  name: buluto
  age: 4
  isHungury: false
```

3. 测试类

```
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootQuickApplicationTests {
    @Autowired
    private Dog dog;
    @Test
    public void  testValue() {
        System.out.println(dog);
    }
}
```

> `@Value`和`@ConfigurationProperties`比较

 -| `@Value` | `@ConfigurationProperties`
---|--- | ---
功能 | 单个注入 | 批量注入
松散绑定(驼峰法、横杠和下划线混用) | 不支持 | 支持
`SpEL` | 支持 | 不支持
`JSR-303` | 不支持 | 支持
复杂类型封装 | 不支持 | 支持

==如果需要从配置文件中取一项值,使用@Value==

==Bean和配置文件进行映射,使用@ConfiguraProperties==

### @PropertySource

加载指定的配置文件

1. 实体类, 注入的实体类添加`@Component`注解,添加到`Spring`容器,`@PropertySource`直接指向配置文件
```java
@Component
@PropertySource(value = {"classpath:persion.properties"})
@ConfigurationProperties(prefix = "persion")
public class Persion {
    private String name;
    private Integer age;
    private Date birth;
    private Address address;
    private Map<String, Object> maps;
    private List<String> lists;
    // constractor getter setter toString
}

@Component
public class Address {
    private String province;
    private String city;
	// constractor getter setter toString
}
```

2. 配置文件`persion.properties`

```
persion.name=qpf
persion.age=26
persion.birth=1992/05/10
persion.address.province=广东
persion.address.city=广州
persion.maps.a=1
persion.maps.b=2
persion.lists=1, 2
```
3. `test`类,使用`@SpringBootTest`测试,自动注入`Persion`类

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootQuickApplicationTests {
    @Autowired
    Persion persion;
    
    @Test
    public void contextLoads() {
        System.out.println(persion);
    }
}
```

### @ImportResource

让`Spring`配置文件生效

1. 新建一个类`Temp`
```
package com.qpf.springbootquick.bean;
public class Temp {
}
```
2. 新建`spring`配置文件

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="temp" class="com.qpf.springbootquick.bean.Temp"/>
</beans>
```
3. 配置类添加`@ImportResource`注解
```
@ImportResource(locations = {"classpath:beans.xml"})
@SpringBootApplication
public class SpringBootQuickApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootQuickApplication.class, args);
    }
}
```

4. 测试`Spring`容器中是否有配置的类
```
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootQuickApplicationTests {
    @Autowired
    ApplicationContext ioc;

    @Test
    public void  testImportResource() {
        System.out.println(ioc.containsBean("temp"));
    }
}
```

> `Spring Boot` 推荐使用注解的方式添加组件

1. 编写一个配置类`MyAppConfig`,
    - 添加注解`@Configuration`声明该类为配置类
    - 添加注解`@Bean`标注的方法将返回值添加到`Spring`容器,`id`为函数名

```
@Configuration
public class MyAppConfig {
    @Bean
    public Temp temp () {
        return new Temp();
    }
}
```

2. 取消主配置类添加`@ImportResource`注解

```
//@ImportResource(locations = {"classpath:beans.xml"})
@SpringBootApplication
public class SpringBootQuickApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootQuickApplication.class, args);
    }
}
```
3. 测试`Spring`容器中是否有配置的类
```
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootQuickApplicationTests {
    @Autowired
    ApplicationContext ioc;

    @Test
    public void  testImportResource() {
        System.out.println(ioc.containsBean("temp"));
    }
}
```

## 配置文件占位符

- 随机数:
    ```
    ${random.value} ${random.int} ${random.long}    ${random.int(value, [max])}  
    ```
- 默认值: 如果属性未被配置,则使用默认值
    ```
    ${xxx:default}
    ```
1. 实体类
```
@Component
@PropertySource(value = {"classpath:temp.properties"})
@ConfigurationProperties(prefix = "temp")
public class TempPlaceHolder {
    private String name;
    private Integer age;
    private Boolean isOk;
    private String randomStr;
    // constractor getter setter toString
}
```

2. 配置文件

```
temp.name=${random.uuid}_name
temp.age=${random.int(2,8)}
temp.isOk=${temp.age:true}
temp.randomStr=${temp.name:default}
```

3. 测试类

```
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootQuickApplicationTests {
    @Autowired
    private TempPlaceHolder tempPlaceHolder;
    @Test
    public void testPlaceHolder() {
        System.out.println(tempPlaceHolder);
    }
}
```

4. 结果
```
Temp{name='08724405-dd28-4f8c-be8f-7fdf0988df2c_name', age=2, isOk=null, randomStr='d1694a91-fa8a-4809-be0b-2b18b3126fbc_name'}
```

## profiles多环境配置

- 多个`properties`文件
    - 添加多个`properties`文件,`Spring Boot`默认使用`application.properties`文件,其中使用`spring.profiles.active=[profiles]`激活相应的配置文件`application-[profiles].properties`
    ```
    # application.properties
    server.port=8080
    spring.profiles.active=dev
    
    # application-dev.properties
    server.port=8081
    
    # application-prod.properties
    server.port=80
    ```
- `yaml`多文档块
    - `application.yaml`通过`---`分成多个文档块,以第一个文档块为入口,激活相应文档块
    ```
    server:
        port: 8080
    spring:
        profiles:
            active: dev
    ---
    server:
        port: 8081
    spring:
        profiles: dev
    ---
    server:
        port: 80
    spring:
        profiles: prod
    
    ```
> 可以在启动命令中添加`--spring.profiles.active=dev`启动相应`profiles`,优先级高于配置文件

> 虚拟机参数`-Dspring.profiles.active=dev`

## 配置文件加载优先级

`Spring Boot`会扫描项目路径,加载`application.properties`和`application.yaml`文件作为默认配置,优先级如下
- `file:./config/`
- `file:./`
- `classpath:/config`
- `classpath:/`
优先级从高到低,优先级高的配置文件会覆盖低优先级的配置文件

> 打包后的项目可以使用`--spring.config.location`参数指定`jar`包外的配置文件,优先级最高

```
$ java -jar xxxx.jar --spring.config.location=path/to/properties
```

## 配置优先级

`Spring Boot`支持多种外部配置方式,优先级由高到底,互补覆盖配置:

1. 主目录开发工具全局设置(开发工具激活时的配置文件:`~/.spring-boot-devtools.properties`)
2. 测试中的`@TestPropertySource`注解
3. 测试中的`@SpringBootTest#properties`注解
4. ==命令行参数==
5. 嵌套中在环境变量或系统配置的`SPRING_APPLICATION_JSON`配置
6. `ServletConfig`初始化参数
7. `ServletContext`初始化参数
8. ==`java:comp/env`的`JNDI`属性==
9. ==`Java`系统配置(`System.getProperties()`)==
10. ==系统环境变量==
11. ==`RandomValuePropertySource`配置的`randon.*`属性==
12. ==`jar`包外的`application-{profile}.properties`配置文件和`application-{profile}.yaml`配置文件==
13. ==`jar`包内的`application-{profile}.properties`配置文件和`application-{profile}.yaml`配置文件==
14. ==`jar`包外的`application.properties`配置文件和`application-.yaml`配置文件==
15. ==`jar`包内的`application.properties`配置文件和`application-.yaml`配置文件==
16. ==`@Configuration`注解类的`@PropertySource`==
17. ==通过`pringApplication.setDefaultProperties`设置的默认配置==


> 优先规则: 
- `jar`包外 > `jar`包内
- `application-{profile}.properties`或`application-{profile}.yaml` > `application.properties`或`application.yaml`

> [参考](https://docs.spring.io/spring-boot/docs/1.5.15.RELEASE/reference/htmlsingle/#boot-features-external-config)

## 自动配置原理

[spring boot 常用配置](https://docs.spring.io/spring-boot/docs/1.5.15.RELEASE/reference/htmlsingle/#common-application-properties)

### 步骤

1. SpringBoot启动时加载主配置程序,开启自动配置功能`@EnableAutoConfiguration`
2. `@EnableAutoConfiguration`注解导入`EnableAutoConfigurationImportSelector`类
    ```
    @Import(EnableAutoConfigurationImportSelector.class)
    public @interface EnableAutoConfiguration {
    ```
3. `EnableAutoConfigurationImportSelector`的父类`AutoConfigurationImportSelector`
    - `selectImports()`方法调用`getCandidateConfigurations(annotationMetadata,attributes);`返回所有自动配置类全类名的字符串列表
    ```
    SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(), getBeanClassLoader());
    扫描所有jar包类路径下META-INF/spring.factories文件,
    包装成Properties对象,获取 org.springframework.boot.autoconfigure.EnableAutoConfiguration 对应的属性值
    返回自动配置类的字符串列表
    
    # org.springframework.boot.autoconfigure.jar/META-INF/spring.factories文件
    
    # Auto Configure
    org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
    org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
    ....\
    org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration
    ```
    
4. 得到的自动配置类(`*AutoConfiguration`)添加到`Spring`容器,用这些自动配置类为容器添加组件.以`HttpEncodingAutoConfiguration`类为例解释自动配置类原理

    ```
    // 标注这个类是配置类
    @Configuration  
    // 启动HttpEncodingProperties类的@ConfigurationProperties注解
    @EnableConfigurationProperties(HttpEncodingProperties.class)
    // 判断当前应用是否为web应用
    @ConditionalOnWebApplication 
    // 判断当前项目有没有CharacterEncodingFilter类
    @ConditionalOnClass(CharacterEncodingFilter.class)
    // 判断配置文件中是否配置了spring.http.encoding.enabled,默认值为true
    @ConditionalOnProperty(prefix = "spring.http.encoding", value = "enabled", matchIfMissing = true) 
    public class HttpEncodingAutoConfiguration {
        
        // @EnableConfigurationProperties注解已经启动了这个类的@ConfigurationProperties,属性值根据配置文件自动注入
        private final HttpEncodingProperties properties;
        
        // 容器为构造方法赋值
        public HttpEncodingAutoConfiguration(HttpEncodingProperties properties) {
		    this.properties = properties;
	    }
        
        // 为容器添加组件,组件的属性值从对应的Properties类(HttpEncodingProperties)中获取
        @Bean
    	@ConditionalOnMissingBean(CharacterEncodingFilter.class)
    	public CharacterEncodingFilter characterEncodingFilter() {
    		CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
    		filter.setEncoding(this.properties.getCharset().name());
    		filter.setForceRequestEncoding(this.properties.shouldForce(Type.REQUEST));
    		filter.setForceResponseEncoding(this.properties.shouldForce(Type.RESPONSE));
    		return filter;
    	}    
    }
    ```
    
    - `HttpEncodingAutoConfiguration`类的注解决定这个自动配置类能否生效
    
    - 如果自动配置类生效,这个自动配置类就回向容器中添加各种组件,这些组件的属性从对应的`Properties`类中获取
    
    - 每个自动配置类都有一个对应的`Properties`类,这个类决定可以在配置文件中添加哪些配置
    ```
    // 从配置文件中注入属性值
    @ConfigurationProperties(prefix = "spring.http.encoding") 
    public class HttpEncodingProperties {}
    ```
### 总结

1. `SpringBoot`加载大量自动配置类,项目需要的功能都封装在自动配置类中
2. 自动配置类和配置文件之间有一个`XXXXProperties`类,这个类的属性值从配置文件注入,自动配置类根据这个类的属性添加组件.
    - `XXXXProperties`类封装配置文件中的相关属性
    - `XXXXAutoConfiguration`类向容器中添加组件

### @Conditional派生注解

`@Conditional`派生注解指定条件成立的,自动配置类的所有内容才生效,注解的方法才向容器中添加组件


`@Conditional`派生注解 | 满足条件
---|---
`@ConditionOnJava` | 系统的`Java`版本符合要求
`@ConditionOnBean` | 容器中存在指定`Bean`
`@ConditionOnMissingBean` | 容器中不存在指定`Bean`
`@ConditionOnExpression` | 满足`SPEL`表达式(`#{}`)
`@ConditionOnClass` | 系统中有指定的类
`@ConditionOnMissionClass` | 系统中没有指定类
`@ConditionOnSingleCandidate` | 容器中只有一个指定`Bean`,或者这个`Bean`是首选`Bean`
`@ConditionOnProperty` | 系统中指定属性有指定值
`@ConditionOnResoucrce` | 类路径下存在指定资源文件
`@ConditionOnWebApplication` | 当前为`web`环境
`@ConditionOnNotWebApplication` | 当前不是`web`环境
`@ConditionOnJndi` | `JNDI`存在指定值

> 满足`@Conditional`派生注解条件的自动配置类才能生效,启用`debug=true`属性,可以打印自动配置报告,查看哪些自动配置类生效了

# 四、日志

1. `Spring Boot`底层使用`slf4j`接口 + `logback`方式式进行日志记录.
    
    ```
    graph LR
    spring-boot-starter-->spring-boot-starter-logging
    spring-boot-starter-logging-->logback-clssic
    logback-clssic-->logback-core
    logback-clssic-->slf4j-api
    ```
    > `logback-clssic`是`slf4j-api`的实现

2. 由于`Spring Boot`依赖的框架中有的使用其他日志系统,所以需要统一日志记录,导入对应的日志转换包和移除其日志依赖包,然后导入`slf4j`其他实现`jar`包,使用`slf4j`进行日志输出.
    
    ```
    graph LR
    spring-boot-starter-->spring-boot-starter-logging
    spring-boot-starter-logging-->jul-to-slf4j
    spring-boot-starter-logging-->log4j-over-slf4j
    spring-boot-starter-logging-->jcl-over-slf4j
    jul-to-slf4j-->slf4j-api
    log4j-over-slf4j-->slf4j-api
    jcl-over-slf4j-->slf4j-api
    ```
    
    > `jul-to-slf4j`是替换`jul`的包,并实现了`slf4j-api`
    
    > `log4j-over-slf4j`是替换`log4j`的包,并实现了`slf4j-api`
    
    > `jcl-over-slf4j`是替换`jcl`的包,并实现了`slf4j-api`
    
    ```
    <!-- 导入框架时,移除该框架依赖的日志包 -->
    <dependency>
        <groupId>org.springframeworl</groupId>
        <artifactId>spring-core</artifactId>
        <exclusions>
            <exclusion>
                <groupId>commons-logging</groupId>
                <artifactId>commons-logging</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    ```

## 原理

> ==`Spring Boot`能自动适配所有日志,底层使用`slf4j`接口 + `logback`方式式进行日志记录.导入框架时,移除该框架依赖的其他日志包==

## 使用

1. 获取日志记录器

```
Logger logger = LoggerFactory.getLogger(getClass());
```
2. 设置日志级别,只会打印该级别及以上的信息,由高到底`trace` < `debug` < `info` < `warn` < `error`.

```
logger.trace("打印trace信息");
logger.debug("打印debug信息");
logger.info("打印info信息");
logger.warn("打印warn信息");
logger.error("打印error信息");
```

3. `Spring Boot`默认配置`root`级别为`info`级别信息,配置文件中可以设置指定包的日志级别

```
logging.level.com.qpf = trace
```
4. 日志输出
    - 未设置`logging.path`和`logging.file`,默认在控制台输出
    - 只设置`logging.file`,则会输出到指定文件,相对路径在当前目录
    - 只设置`logging.path`,则会输出到指定路径下的`spring.log`文件
    - 如果两个都设置,则会输出到`logging.file`文件

5. 日志格式
    - `logging.pattern.console`: 控制台日志格式
    - `logging.pattern.file`: 日志文件日志格式
    - 字符含义:
        ```
        %d{...}: 日期时间
        %thread: 线程名
        %-5level: 靠左显示的5位字符
        %logger{n}: logger名最长n字符
        %msg: 日志信息
        %n: 换行符
        ```
6. 指定配置
    - 在类路径下添加日志框架的配置文件,即可替代`Spring Boot`默认配置.
    
    日志框架 | 日志配置文件
    ---|---
    `logback` | `logback.xml`\`logback.groovy`; `logback-spring.xml`\`logback-spring.groovy` 
    `log4j2` | `log4j2.xml log4j2-spring.xml`
    `jul(Java Util Logging)` | `logging.properties`

    > `logback.xml`会被日志框架直接识别;`logback-spring.xml`会被`Spring Boot`加载,配置项可以指定运行环境
    
    ```
    <springProfile name="dev">
        <!-- 配置项 -->
    </springProfile>
    ```

# 五、web开发

使用`Spring Boot`进行`web`开发的基本步骤:
1. 创建`Spring Boot` 应用
2. 简单编写编写配置文件
3. 业务逻辑代码

## 1. Spring Boot 对静态资源的映射

静态资源的配置类
```
@ConfigurationProperties(prefix = "spring.resources", ignoreUnknownFields = false)
public class ResourceProperties implements ResourceLoaderAware, InitializingBean {
```

静态资源的映射默认配置
```
# org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration
public class WebMvcAutoConfiguration {
    @Configuration
	@Import(EnableWebMvcConfiguration.class)
	@EnableConfigurationProperties({ WebMvcProperties.class, ResourceProperties.class })
	public static class WebMvcAutoConfigurationAdapter extends WebMvcConfigurerAdapter {
        @Override
    	public void addResourceHandlers(ResourceHandlerRegistry registry) {
    		if (!this.resourceProperties.isAddMappings()) {
    			logger.debug("Default resource handling disabled");
    			return;
    		}
    		Integer cachePeriod = this.resourceProperties.getCachePeriod();
    		// '/webjars/**'映射以jar包形式导入的静态资源
    		if (!registry.hasMappingForPattern("/webjars/**")) {
    			customizeResourceHandlerRegistration(registry
    					.addResourceHandler("/webjars/**")
    					.addResourceLocations("classpath:/META-INF/resources/webjars/")
    					.setCachePeriod(cachePeriod));
    		}
    		String staticPathPattern = this.mvcProperties.getStaticPathPattern();
    		// '/**'映射静态资源文件夹下的静态资源
    		if (!registry.hasMappingForPattern(staticPathPattern)) {
    			customizeResourceHandlerRegistration(
    					registry.addResourceHandler(staticPathPattern)
    							.addResourceLocations(
    									this.resourceProperties.getStaticLocations())
    							.setCachePeriod(cachePeriod));
    		}
    	}
    	// '/'映射index.html首页
    	@Bean
		public WelcomePageHandlerMapping welcomePageHandlerMapping(
				ResourceProperties resourceProperties) {
			return new WelcomePageHandlerMapping(resourceProperties.getWelcomePage(),
					this.mvcProperties.getStaticPathPattern());
		}
    }
}
```

- `/webjars/**`: 映射`classpath:/META-INF/resources/webjars/`下的资源,这些资源以`jar`包的形式引入项目.
[`webjars`](https://www.webjars.org/)得到的坐标写入`pom.xml`文件

    ```
    localhosts:8080/webjars/jquery/3.3.1/jquery.js
    
    <dependency>
        <groupId>org.webjars</groupId>
        <artifactId>jquery</artifactId>
        <version>3.3.1-1</version>
    </dependency>
    ```

- `/**`: 访问当前项目的资源文件夹下的资源
    ```
    "classpath:/META-INF/resources/",
    "classpath:/resources/",
    "classpath:/static/", 
    "classpath:/public/",
    "/"
    
    localhost:8080/img/logo.png
    ```
- `/`: 静态文件夹下的所有`index.html`
    - `localhost:8080/`
- `**/favicon.ico`: 静态文件夹下的`favicon.ico`文件

## 模板引擎(Thymeleaf)

### 1. 导入`thymeleaf`

```
<properties>
    <thymeleaf.version>3.0.9.RELEASE</thymeleaf.version>
    <thymeleaf-layout-dialect.version>2.1.1</thymeleaf-layout-dialect.version>
</properties>
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
</dependencies>
```
### 2. 使用`thymeleaf`

```
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Success</title>
</head>
<body>
    <h1>成功</h1>
    <div th:utext="${hello}"></div>
</body>
</html>
```

- 添加命名空间
    ```
    <html lang="en" xmlns:th="http://www.thymeleaf.org">
    ```
- 请求域添加内容
    ```
    @Controller
    public class HelloController {
        //查出用户数据，在页面展示
        @RequestMapping("/success")
        public String success(Map<String,Object> map){
            map.put("hello","<h1>你好</h1>");
            map.put("users",Arrays.asList("zhangsan","lisi","wangwu"));
            return "success";
        }
    }
    ```
- 获取请求域的内容
    ```
    <div th:utext="${hello}"></div>
    <ul th:each="user: ${users}">
        <li th:text="${user}"></li>
    </ul>
    ```
- 