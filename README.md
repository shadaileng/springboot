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

## 2. 模板引擎(Thymeleaf)

### 2.1 导入`thymeleaf`

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
### 2.2 使用`thymeleaf`

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

## 3. SpringMvc自动配置
`Spring Boot`自动配置了`SpringMvc`
以下是`Spring Boot`对`SpringMvc`的默认配置:
1. 自动配置了`ViewResolver`
    - `ContentNegotiatingViewResolver`组合了所有的`ViewResolver
    - 定制一个`ViewResolver`添加到容器中,就会被`ContentNegotiatingViewResolver`自动组合
2. 配置了静态文件夹路径以及`webjars`映射
3. 配置静态首页(`index.html`)映射
4. 配置了图标(`favicon.ico`)映射
5. 自动注册`Converter`, `GenericConverter`和`Formater`对象到容器中,定制只需要把组件添加到容器
6. 提供`HttpMessageConverters`获取容器中的`HttpMessageConverter`
    - `HttpMessageConverter`用于自动转换`Http`请求和响应的`Bean`
    - 定制`HttpMessageConverter`只需要添加到容器中
7. 定义了错误代码生成规则(`MessageCodeResolver`)
8. 定义了`ConfigurableWebBindingInitializer`对象

## 4.  扩展SpringMvc

为了保留`Spring Boot MVC`的配置的同时,扩展`MVC`的配置(拦截器,格式化器,试图控制器等),可以添加一个配置类(`@Configuration`注解).

==**这个类是`WebWebMvcConfigurerAdapter`的子类,且不能标注`@EnableWebMvc`注解.**==

==**需要添加的功能,重写对应的方法即可.**==

```
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/took").setViewName("success");
    }
}
```

### 4.1 原理

1. `WebMvcAutoConfiguration`类是`SpringMvc`的自动配置类,该类定义了一个静态类`WebMvcAutoConfigurationAdapter`,同时导入了`EnableWebMvcConfiguration`类

```
public class WebMvcAutoConfiguration {
	@Configuration
	@Import(EnableWebMvcConfiguration.class)
	@EnableConfigurationProperties({ WebMvcProperties.class, ResourceProperties.class })
	public static class WebMvcAutoConfigurationAdapter extends WebMvcConfigurerAdapter {
	}
}

```

2. `EnableWebMvcConfiguration`类的父类是`DelegatingWebMvcConfiguration`

```
@Configuration
public static class EnableWebMvcConfiguration extends DelegatingWebMvcConfiguration {}
```

3. `DelegatingWebMvcConfiguration`类从容器中获取所有的`WebMvcConfigurer`,并调用其添加配置的方法

```
@Configuration
public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {
	private final WebMvcConfigurerComposite configurers = new WebMvcConfigurerComposite();
	// 从容器中获取所有的WebMvcConfigurer
	@Autowired(required = false)
	public void setConfigurers(List<WebMvcConfigurer> configurers) {
		if (!CollectionUtils.isEmpty(configurers)) {
			this.configurers.addWebMvcConfigurers(configurers);
		}
	}
	// WebMvcConfigurer调用添加ViewController的方法
	@Override
	protected void addViewControllers(ViewControllerRegistry registry) {
	    // org.springframework.web.servlet.config.annotation.WebMvcConfigurerComposite#addViewControllers
		this.configurers.addViewControllers(registry);
	}

}
```
4. 调用所有`WebMvcConfigurer`(包括自定义的`WebMvcConfigurer`)添加配置的方法

```
class WebMvcConfigurerComposite implements WebMvcConfigurer {
    @Override
	public void addViewControllers(ViewControllerRegistry registry) {
		for (WebMvcConfigurer delegate : this.delegates) {
			delegate.addViewControllers(registry);
		}
	}
}
```
---
## 5 全面接管SpringMvc

==**自定义的`WebMvcConfigurer`类添加`@EnableWebMvc`注解,即可全面接管`SpringMvc`,`SpringMvc`自动配置失效**==

```
@EnableWebMvc
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/took").setViewName("success");
    }
}
```

### 5.1 原理

1. `@EnableWebMvc`注解导入了`DelegatingWebMvcConfiguration`类
    ```
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Documented
    @Import(DelegatingWebMvcConfiguration.class)
    public @interface EnableWebMvc {}
    ```
2. `DelegatingWebMvcConfiguration`类的父类是`WebMvcConfigurationSupport`
    ```
    @Configuration
    public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {}
    ```
3. `SpringMvc`的自动配置类`WebMvcAutoConfiguration`的生效条件`@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)`,如果容器中存在`WebMvcConfigurationSupport`类,自动配置不会生效.
    ```
    @Configuration
    @ConditionalOnWebApplication
    @ConditionalOnClass({ Servlet.class, DispatcherServlet.class,
    		WebMvcConfigurerAdapter.class })
    @ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
    @AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
    @AutoConfigureAfter({ DispatcherServletAutoConfiguration.class,
    		ValidationAutoConfiguration.class })
    public class WebMvcAutoConfiguration {}
    ```
---
## 6 CRUD案例

### 6.1 首页映射

```
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {
    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurer() {
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
            }
        };
        return adapter;
    }
}

```
### 6.2 导入webjars

```
<dependencies>
    <dependency>
        <groupId>org.webjars</groupId>
        <artifactId>bootstrap</artifactId>
        <version>4.0.0</version>
    </dependency>
    <dependency>
        <groupId>org.webjars</groupId>
        <artifactId>jquery</artifactId>
        <version>3.3.1-1</version>
    </dependency>
</dependencies>
```
### 6.3 HTML引用静态资源

```
    <!-- Bootstrap core CSS -->
    <link th:href="@{/webjars/bootstrap/4.0.0/css/bootstrap.css}" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link th:href="@{/asserts/floating-labels.css}" rel="stylesheet">
    
    <img class="mb-4" th:src="@{/asserts/bootstrap-solid.svg}" alt="" width="72" height="72">
```

### 6.4 项目的根路径
> `@{}`自动为路径添加根路径
```
# application.properties
server.context-path=/crud
```
---

## 7 国际化

### 7.1 添加国际化配置文件

```
# /i18n/login.properties
login.btn=登陆
login.email=电子邮箱
login.password=密码
login.remember=记住我
login.tip=请登陆

# /i18n/login_zh_CN.properties
login.btn=登陆
login.email=电子邮箱
login.password=密码
login.remember=记住我
login.tip=请登陆

# /i18n/login_en_US.properties
login.btn=Login in
login.email=Email
login.password=Password
login.remember=remember me
login.tip=Please login in
```

### 7.2 指定国际化配置文件位置

> 默认为根路径下的`message.properties`

```
# application.properties
spring.messages.basename=i18n.login
```

### 7.3 HTML文件使用i18n

```
<h1 class="h3 mb-3 font-weight-normal" th:text="#{login.tip}">Floating labels</h1>

<div class="form-label-group">
    <input type="email" id="inputEmail" class="form-control" th:placeholder="#{login.email}" placeholder="Email address" required="" autofocus="">
    <label for="inputEmail" th:text="#{login.email}">Email address</label>
</div>
<div class="form-label-group">
    <input type="password" id="inputPassword" class="form-control" th:placeholder="#{login.password}" placeholder="Password" required="">
    <label for="inputPassword" th:text="#{login.password}">Password</label>
</div>
<div class="checkbox mb-3">
    <label>
      <input type="checkbox" value="remember-me"> [[#{login.remember}]]
    </label>
</div>
<button class="btn btn-lg btn-primary btn-block" type="submit" th:text="#{login.tip}">Sign in</button>
```

### 7.4 页面控制语言区域信息

1. 请求中添加区域信息
```
<a th:href="@{/index.html(l='zh_CN')}">中文</a>
<a th:href="@{/index.html(l='en_US')}">English</a>
```
2. 新建`LocaleResolver`类
```
public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String l = request.getParameter("l");
        Locale locale = Locale.getDefault();
        if (!StringUtils.isEmpty(l)) {
            String[] split = l.split("_");
            split = split.length == 2 ? split : new String[]{"zh", "CN"};
            locale = new Locale(split[0], split[1]);
            // 将i18n信息保存到session中
            request.getSession().setAttribute("I18N_LANGUAGE_SESSION", locale);
        } else {
            // 从session中取出区域信息
            Locale i18NLanguageSession = (Locale) request.getSession().getAttribute("I18N_LANGUAGE_SESSION");
            locale = i18NLanguageSession != null? i18NLanguageSession : locale;
        }
        return locale;
    }
    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {}
}
```
3. 将自定义的`LocalResolver`添加到容器
```
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }
}
```

4. 原理
    - `WebMvcAutoConfiguration`中定义的`WebMvcAutoConfigurationAdapter`类区域化信息设置方法`localeResolver`标注了`@ConditionalOnMissingBean`注解,如果容器中存在`LocaleResolver`对象,该方法则不会生效.因此在容器中添加`LocaleResolver`对象即可自定义区域化信息.
    ```
    public class WebMvcAutoConfiguration {
        public static class WebMvcAutoConfigurationAdapter extends WebMvcConfigurerAdapter {
            @Bean
    		@ConditionalOnMissingBean
    		@ConditionalOnProperty(prefix = "spring.mvc", name = "locale")
    		public LocaleResolver localeResolver() {
    			if (this.mvcProperties
    					.getLocaleResolver() == WebMvcProperties.LocaleResolver.FIXED) {
    				return new FixedLocaleResolver(this.mvcProperties.getLocale());
    			}
    			AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
    			localeResolver.setDefaultLocale(this.mvcProperties.getLocale());
    			return localeResolver;
    		}
        }
    }
    ```
---
## 8 登陆及拦截器

### 8.1 添加`LoginController`
    - 使用`POST`请求(`@PostMapping`)
    - 请求路径`/user/login`
    - 请求字段`email`和`password`,
    - 请求成功将用户信息放入`Session`中,并重定向到`/main`(`return "redirect:/main";`)防止表单重复提交
    - 请求失败则将错误信息放入`request`,转发到`login`页面

```
@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @PostMapping(value = "/user/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Map<String, Object> map, HttpSession session) {
        if (!StringUtils.isEmpty(password) && "123456".equals(password)) {
            logger.info(email + " [登陆成功]");
            return "redirect:/main";
        } else {
            logger.info(email + " [密码错误]");
            map.put("msg", "密码错误");
            return "login";
        }
    }
}
```

### 8.2 试图映射`/main`到`dashboard`页面

```
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {
    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurer() {
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
                registry.addViewController("/main").setViewName("dashboard");
            }
        };
        return adapter;
    }
}
```

### 8.3 登陆页面
指定`action`,`method`以及请求字段`email`和`password`
```
<form class="form-signin" th:action="@{/user/login}" th:method="POST">
  <div class="text-center mb-4">
    <img class="mb-4" src="./login_files/bootstrap-solid.svg" th:src="@{/asserts/bootstrap-solid.svg}" alt="" width="72" height="72">
    <h1 class="h3 mb-3 font-weight-normal" th:text="#{login.tip}">Floating labels</h1>
    <p th:if="not ${#strings.isEmpty(msg)}" style="color: #ff2b1a;" th:text="${msg}"></p>
  </div>

  <div class="form-label-group">
    <input type="email" id="inputEmail" th:name="email" class="form-control" th:placeholder="#{login.email}" placeholder="Email address" required="" autofocus="">
    <label for="inputEmail" th:text="#{login.email}">Email address</label>
  </div>

  <div class="form-label-group">
    <input type="password" id="inputPassword" th:name="password" class="form-control" th:placeholder="#{login.password}" placeholder="Password" required="">
    <label for="inputPassword" th:text="#{login.password}">Password</label>
  </div>

  <div class="checkbox mb-3">
    <label>
      <input type="checkbox" value="remember-me"> [[#{login.remember}]]
    </label>
  </div>
  <button class="btn btn-lg btn-primary btn-block" type="submit" th:text="#{login.btn}">Sign in</button>
  <p class="mt-5 mb-3 text-muted text-center">© 2017-2018</p>
    <a th:href="@{/index.html(l='zh_CN')}">中文</a>
    <a th:href="@{/index.html(l='en_US')}">English</a>
</form>
```
### 8.4 拦截器
定义一个拦截器组件,从`Session`中取出用户信息,成功则通过,否则拦截并转发回登陆页面,并显示错误下信息

```
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("msg", "用户未登录");
            request.getRequestDispatcher("/").forward(request, response);
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {}
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {}
}
```
### 8.5 将自定义拦截器添加到组件
    
```
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {
    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurer() {
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/", "/index.html", "/user/login");
            }
        };
        return adapter;
    }
}
```
---
## 9 Restful风格

> URI定位资源,Http请求方法区分对资源的操作

### 9.1 添加`EmployeeController`类映射员工管理请求路径,`EmployeeService`类管理数据

```
# com.qpf.springbootquick.controller.EmployeeController
@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @GetMapping(value = "/empls")
    public String list(Map<String, Object> map) {
        List<Employee> employees = employeeService.getAll();
        map.put("empls", employees);
        return "empl/list";
    }
}

# com.qpf.springbootquick.dao.EmployeeService
@Repository
public class EmployeeService {
    private static List<Employee> empls = new ArrayList<Employee>();
    static {
        List<Department> depts = new ArrayList<Department>();
        depts.add(new Department(1, "A-a"));
        depts.add(new Department(2, "B-b"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            empls.add(new Employee(1, "qpf", "qpf@qq.com", "M", depts.get(0), dateFormat.parse("1992-05-10")));
            empls.add(new Employee(2, "cy", "cy@qq.com", "F", depts.get(1), dateFormat.parse("2000-04-13")));
            empls.add(new Employee(3, "cxl", "cxl@qq.com", "F", depts.get(1), dateFormat.parse("1993-05-16")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public List<Employee> getAll() {
        List<Employee> employees = empls;
        return employees;
    }
}
```

### 9.2 管理页面跳转到员工列表,员工列表页面放在`templates/empl`下

```
<a th:href="@{/empls}" />员工</a>
```

### 9.3 员工列表页面使用`thymeleaf`抽取公共元素
    - 抽取公共元素到`common/bar.html`,`activeUrl`为引用公共元素时传入的参数
    ```
    # common/bar.html
    <nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow" th:fragment="topbar">
    <!-- 顶部菜单 -->
    </nav>
    <nav class="col-md-2 d-none d-md-block bg-light sidebar" th:fragment="sidebar">
        <!-- 侧边菜单 -->
        <li class="nav-item"><a th:class="${activeUrl=='main'?'nav-link active':'nav-link'}" th:href="@{/main}">Dashboard</a></li>
        <li class="nav-item"><a th:class="${activeUrl=='empl'?'nav-link active':'nav-link'}" th:href="@{/empls}">员工</a></li> 
    </nav>
    ```
    - 引用公共元素
    ```
    # /empl/list.html
    <!-- 顶部菜单 -->
    <div th:replace="dashboard::sidebar"></div>
    <div class="container-fluid">
      <div class="row">
        <!-- 侧边菜单 -->
        <div th:replace="dashboard::topbar('empl')"></div>
        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
        <!-- 主界面 -->
        </main>
      </div>
    </div>
    ```
    - 原理
    ```
    # 将公共片段插入到当前元素
    th:insert="模板名::片段名(param='value')"
    th:insert="模板名::选择器(param='value')"
    # 公共片段替代当前元素
    th:replace="模板名::片段名(param='value')"
    th:replace="模板名::选择器(param='value')"
    # 公共片段内部元素插入当前元素
    th:include="模板名::片段名(param='value')"
    th:include="模板名::选择器(param='value')"
    # 内联模式插入公共片段
    [[~{模板名::片段名(param='value')}]]
    [(~{模板名::选择器(param='value')}]
    ```

### 9.4 列表显示数据
```
<div class="table-responsive">
    <a th:href="@{/empl}" class="btn btn-sm btn-success">添加员工</a>
    <table class="table table-striped table-sm">
      <thead>
        <tr>
          <th>#</th>
          <th>Name</th>
          <th>Email</th>
          <th>Gender</th>
          <th>Department</th>
          <th>Birth</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr th:each="empl: ${empls}">
          <td th:text="${empl.id}"></td>
          <td th:text="${empl.name}"></td>
          <td th:text="${empl.email}"></td>
          <td th:text="${empl.gender} == 'M'?'男': '女'"></td>
          <td th:text="${empl.department.name}"></td>
          <td th:text="${#dates.format(empl.birth, 'yyyy-MM-dd')}"></td>
          <td>
            <a class="btn btn-sm btn-primary">修改</a>
            <a class="btn btn-sm btn-danger">删除</a>
          </td>
        </tr>
      </tbody>
    </table>
</div>
```

### 9.5 添加员工

    - `/empl`(get): 添加页面
    - `/empl`(post):添加请求
    ```
    @Controller
    public class EmployeeController {
        @GetMapping(value = "/empl")
        public String add(Map<String, Object> map) {
            Collection<Department> departments = departmentService.getAll();
            map.put("depts", departments);
            return "empl/add";
        }
        @PostMapping("/empl")
        public String addOne(Employee employee) {
            employeeService.save(employee);
            return "redirect:/empls";
        }
    }
    
    # empl/add.html
    <form th:action="@{/empl}" th:method="POST">
        <div class="form-group">
          <label for="exampleFormControlInput1">Name</label>
          <input type="text" class="form-control" id="" placeholder="name" name="name">
        </div>
        <div class="form-group">
          <label for="exampleFormControlInput1">Email</label>
          <input type="email" class="form-control" id="exampleFormControlInput1" placeholder="name@example.com" name="email">
        </div>
        <div class="form-group">
            <label for="exampleFormControlInput1">Gender</label>
            <div class="form-check form-check-inline">
                <input type="radio" class="form-check-input" name="gender" value="M">
                <label class="form-check-label">男</label>
            </div>
            <div class="form-check form-check-inline">
                <input type="radio" class="form-check-input" name="gender" value="F">
                <label class="form-check-label">女</label>
            </div>
        </div>
        <div class="form-group">
          <label for="exampleFormControlSelect1">Department</label>
          <select class="form-control" name="department.id" >
            <option th:each="dept: ${depts}" th:text="${dept.name}" th:value="${dept.id}">1</option>
          </select>
        </div>
        <div class="form-group">
            <label for="exampleFormControlInput1">Birth</label>
            <input type="text" class="form-control" placeholder="1900/01/01" name="birth">
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit" th:text="add">Sign in</button>
    </form>
    ```

> `Spring Boot Mvc`默认设置日期转换格式为`yyyy/MM/dd`,设置日期格式:
```
spring.mvc.data-format=yyyy-MM-dd
```

### 9.6 修改员工
    - 回显,修改员工和添加员工共用一个界面,修改页面回显员工数据
    - `/empl/{id}`(`get`):修改页面
    - `/empl`(`put`): 修改数据
    ```
    # com.qpf.springbootquick.controller.EmployeeController
    @GetMapping(value = "/empl/{id}")
    public String edit(@PathVariable("id") Integer id, Map<String, Object> map) {
        Collection<Department> departments = departmentService.getAll();
        map.put("depts", departments);
        Employee employee = employeeService.getOne(id);
        // 放入request中，用作回显
        map.put("empl", employee);
        return "empl/add";
    }
    @PutMapping("/empl")
    public String editEmpl(Employee employee) {
        logger.info("修改: " + employee);
        int update = employeeService.editOne(employee);
        return "redirect:empls";
    }
    ```
    - 页面显示员工数据
    - 隐藏标签`<input type="hidden" name="_method" value="put" th:if="${empl != null}">`用于`PUT`请求
    ```
    <form th:action="@{/empl}" th:method="POST">
      <input type="hidden" name="_method" value="put" th:if="${empl != null}">
      <input type="hidden" name="id" th:value="${empl.id}" th:if="${empl != null}">
    <div class="form-group">
      <label for="exampleFormControlInput1">Name</label>
      <input type="text" class="form-control" id="" placeholder="name" name="name" th:value="${empl != null} ? ${empl.name} : ''">
    </div>
    <div class="form-group">
      <label for="exampleFormControlInput1">Email</label>
      <input type="email" class="form-control" id="exampleFormControlInput1" placeholder="name@example.com" name="email" th:value="${empl != null} ? ${empl.email} : ''">
    </div>
    <div class="form-group">
        <label for="exampleFormControlInput1">Gender</label>
        <div class="form-check form-check-inline">
            <input type="radio" class="form-check-input" name="gender" value="M" checked="checked" th:checked="${empl != null} ? (${empl.gender} == 'M' ? true : false) : true">
            <label class="form-check-label">男</label>
        </div>
        <div class="form-check form-check-inline">
            <input type="radio" class="form-check-input" name="gender" value="F" th:checked="${empl != null} and ${empl.gender} == 'F' ? true : false">
            <label class="form-check-label">女</label>
        </div>
    </div>
    <div class="form-group">
      <label for="exampleFormControlSelect1">Department</label>
      <select class="form-control" name="department.id" >
        <option th:each="dept: ${depts}" th:text="${dept.name}" th:value="${dept.id}" th:selected="(${empl != null} and ${empl.department.id} == ${dept.id}) ? true :false">1</option>
      </select>
    </div>
    <div class="form-group">
        <label for="exampleFormControlInput1">Birth</label>
        <input type="text" class="form-control" placeholder="1900/01/01" name="birth"  th:value="${empl != null} ? ${#dates.format(empl.birth, 'yyyy/MM/dd')} : ''">
    </div>
      <button class="btn btn-lg btn-primary btn-block" type="submit" th:text="${empl != null} ? update: add">Sign in</button>
    </form>
    ```
### 9.7 删除员工
    - 使用`Ajax`发送`DELETE`请求,需要带上参数`{_method: "delete"}`
    ```
    <a class="btn btn-sm btn-danger" th:href="@{/empl/} + ${empl.id}" th:attr="targetName= ${empl.name}" onclick="delEmpl(event)">删除</a>
    
    
    function delEmpl(event) {
        event = event || window.event
        event.preventDefault()
        let url = event.currentTarget.attributes['href'].value
        let targetName = event.currentTarget.attributes['targetName'].value
        if (!confirm('是否确定[ ' + targetName + ' ]? ')) return
        $.ajax({
            url: url,
            method: 'POST',
            data: {
              _method: "delete"
            },
            success: (result) => {
                location.assign("/empls")
                // console.log(result)
                // alert("删除成功")
            }
        })
        return false
    }
    
    # com.qpf.springbootquick.controller.EmployeeController
    @DeleteMapping("/empl/{id}")
    public String delEmpl(@PathVariable("id") Integer id) {
        logger.info("删除: " + id);
        int delOne = employeeService.delOne(id);
        logger.info("delone: " + delOne);
        return "redirect:/empls";
    }
    ```
---
## 10 Spring Boot 错误处理机制

### 10.1 默认的处理
    - 浏览器访问,返回错误页面,显示错误信息
    - `Ajax`访问,返回`Json`数据,
### 10.2 原理
    - `Spring Boot`错误处理由`ErrorMvcAutoConfiguration`类自动配置
    - 自动配置向容器中添加了以下组件
        - `DefaultErrorAttributes`: 定义页面显示信息`ModelView`
        - `BasicErrorController`: 处理`/error`请求
        ```
        @Controller
        @RequestMapping("${server.error.path:${error.path:/error}}")
        public class BasicErrorController extends AbstractErrorController {
            // 处理浏览器请求,返回html数据
            @RequestMapping(produces = "text/html")
        	public ModelAndView errorHtml(HttpServletRequest request,
        			HttpServletResponse response) {
        		HttpStatus status = getStatus(request);
        		Map<String, Object> model = Collections.unmodifiableMap(getErrorAttributes(
        				request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
        		response.setStatus(status.value());
        		// 解析错误页面,传入参数封装在model中
        		ModelAndView modelAndView = resolveErrorView(request, response, status, model);
        		return (modelAndView != null ? modelAndView : new ModelAndView("error", model));
        	}
            // 处理客户端请求,返回json数据
        	@RequestMapping
        	@ResponseBody
        	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        		Map<String, Object> body = getErrorAttributes(request,
        				isIncludeStackTrace(request, MediaType.ALL));
        		HttpStatus status = getStatus(request);
        		return new ResponseEntity<Map<String, Object>>(body, status);
        	}
        }
        ```
        - `ErrorPageCustomizer`: 注册错误页面请求
        ```
        @Value("${error.path:/error}")
    	private String path = "/error";
        @Override
		public void registerErrorPages(ErrorPageRegistry errorPageRegistry) {
			ErrorPage errorPage = new ErrorPage(this.properties.getServletPrefix()
					+ this.properties.getError().getPath());
			errorPageRegistry.addErrorPages(errorPage);
		}
        ```
        - `DefaultErrorViewResolverConfiguration`: 解析`ModelAndView`
        ```
        public class DefaultErrorViewResolver implements ErrorViewResolver, Ordered {
            static {
        		Map<Series, String> views = new HashMap<Series, String>();
        		views.put(Series.CLIENT_ERROR, "4xx");
        		views.put(Series.SERVER_ERROR, "5xx");
        		SERIES_VIEWS = Collections.unmodifiableMap(views);
        	}
        	@Override
        	public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status,
        			Map<String, Object> model) {
        		ModelAndView modelAndView = resolve(String.valueOf(status), model);
        		if (modelAndView == null && SERIES_VIEWS.containsKey(status.series())) {
        			modelAndView = resolve(SERIES_VIEWS.get(status.series()), model);
        		}
        		return modelAndView;
        	}

        	private ModelAndView resolve(String viewName, Map<String, Object> model) {
        		String errorViewName = "error/" + viewName;
        		TemplateAvailabilityProvider provider = this.templateAvailabilityProviders
        				.getProvider(errorViewName, this.applicationContext);
        		if (provider != null) {
        			return new ModelAndView(errorViewName, model);
        		}
        		return resolveResource(errorViewName, model);
        	}
        }
        ```
    - 步骤
        1. 当访问服务发生`4xx`或`5xx`错误,`ErrorPageCustomizer`注册了错误页面(`#{error.path:/error}`)
        2. `BasicErrorController`处理错误页面`/error`请求
            - `"${server.error.path:${error.path:/error}}"`
            - 请求头`Asccept:text/html`则会返回`html`页面
            - 父类`AbstractErrorController`中获取`request`属性`javax.servlet.error.status_code`,值为错误状态码
                ```
                Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
                ```
        3. 所有的`ErrorViewResolver`(自动配置的`DefaultErrorViewResolverConfiguration`)
            - 从`ErrorAttributes.getErrorAttributes()`方法获取`ModelView`
            - 解析跳转的页面: 
                - 模板引擎: `/templates/error/{4xx,5xx}.html`
                - 静态文件夹: `/error/{4xx,5xx}.html`
                - 默认的错误页面
        4. `DefaultErrorAttributes`实现了`ErrorAttributes`接口的`getErrorAttributes`方法,添加返回信息:
            - `timestamp`: 时间戳
            - `status`: 状态码
            - `error`: 错误提示
            - `exception`: 异常对象
            - `message`: 异常信息
            - `errors`: `JSR303`数据校验错误
### 10.3 定制错误响应
    - 定制错误页面
        - 模板引擎: `/templates/error/{4xx,5xx}.html`
        - 静态文件夹: `/error/{4xx,5xx}.html`
    - 定制错误`Json`数据
        1. 自定义异常处理`MyExceptionHandler`
            - `request`设置错误状态码
            - `request`设置自定义信息
        ```
        # com.qpf.springbootquick.components.MyExceptionHandler
        @ControllerAdvice
        public class MyExceptionHandler {
            // 捕获异常
            @ExceptionHandler(UserNotExistException.class)
            public String handlerUserNotExistException(Exception e, HttpServletRequest request) {
                // 自定义异常信息
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("code", "200");
                map.put("message", e.getMessage());
        
                // 指定请求状态码,/error/5xx.html
                request.setAttribute("javax.servlet.error.status_code", 500);
                // 将异常信息放入request中
                request.setAttribute("ext", map);
                // 转发到/error,BasicErrorController自适应返回类型
                return "forward:/error";
            }
        }
        ```
        2. 自定义`DefaultErrorAttributes`类,设置返回数据信息
        ```
        # com.qpf.springbootquick.components.MyErrorAttribute
        @Component
        public class MyErrorAttribute extends DefaultErrorAttributes {
            @Override
            public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
                Map<String, Object> map = super.getErrorAttributes(requestAttributes, includeStackTrace);
        
                map.put("company", "qpf");
                // 从request(0)中取出异常信息
                map.put("ext", requestAttributes.getAttribute("ext", 0));
        
                return map;
            }
        }
        ```
---

## 11 嵌入式Servlet容器
`Spring Boot`默认使用嵌入式`Servlet`容器
- `Tomcat`: 默认
- `Jetty`: 适用长连接
- `Undertow`: 异步

### 11.1 定制Servlet容器配置

1. 配置`ServerProperties`类属性
```
server.port=8081
server.context-path=/crud
server.tomcat.uri-encoding=UTF-8
# server.xxx 通用属性
# server.tomcat.xxx tomcat属性
```

2. 向容器中添加`EmbeddedServletContainerCustomizer`组件
```
@Configuration
public class MyServerConfig {
    @Bean
    public EmbeddedServletContainerCustomizer coustomeServerConfig() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.setPort(8081);
            }
        };
    }
}
```

> `ServerProperties`类也是`EmbeddedServletContainerCustomizer`接口的实现类

---

### 11.2 注册Servlet三大组件

> `Spring Boot`默认以`jar`包方式启动嵌入式`Servlet`容器来加载`web`应用的,所有没有`web.xml`文件.

1. 注册`Servlet`组件是通过向容器中添加`ServletRegistrationBean`,`FilterRegistrationBean`或`ServletListenenRegistrationBean`的方式.

- `ServletRegistrationBean`

```
# com.qpf.springbootquick.components.servlet.MyServlet
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("Hello MyServlet");
    }
}
# com.qpf.springbootquick.config.MyServerConfig
@Bean
public ServletRegistrationBean registrationServlet() {
    return new ServletRegistrationBean(new MyServlet(), "/test/myServlet");
}
```

- `FilterRegistrationBean`

```
# com.qpf.springbootquick.components.filter.MyFilter
public class MyFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("init MyFilter ....");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("MyFilter check ....");
    }

    @Override
    public void destroy() {
        logger.info("MyFilter destroy ....");
    }
}

# com.qpf.springbootquick.config.MyServerConfig
@Bean
public FilterRegistrationBean registrationFilter() {
    FilterRegistrationBean registrationFilter = new FilterRegistrationBean(new MyFilter());
    registrationFilter.setUrlPatterns(Arrays.asList("/test/myServlet"));
    return registrationFilter;
}
```
- `ServletListenenRegistrationBean`

```
# com.qpf.springbootquick.components.listener.MyListener
public class MyListener implements ServletContextListener {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("init MyListener ....");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("destroy MyListener ....");
    }
}

# com.qpf.springbootquick.config.MyServerConfig
@Bean
public ServletListenerRegistrationBean<ServletContextListener> registrationServletListener() {
    ServletListenerRegistrationBean<ServletContextListener> registrationServletListener = new ServletListenerRegistrationBean<ServletContextListener>(new MyListener());
    return registrationServletListener;
}
```

2. `SpringBoot`在`DispatcherServletAutoConfiguration`类中自动配置`SpringMvc`的`DispatcherServlet`
    - `SpringMvc`默认拦截(`/`)所有请求,包括静态资源,处理`*.jsp`
    - 设置`servlet.servletPath`设置`SpringMvc`默认拦截请求路径

```
# org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(DispatcherServlet.class)
@AutoConfigureAfter(EmbeddedServletContainerAutoConfiguration.class)
public class DispatcherServletAutoConfiguration {
    @Configuration
	@Conditional(DispatcherServletRegistrationCondition.class)
	@ConditionalOnClass(ServletRegistration.class)
	@EnableConfigurationProperties(WebMvcProperties.class)
	@Import(DispatcherServletConfiguration.class)
    protected static class DispatcherServletRegistrationConfiguration {
        @Bean(name = DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME)
		@ConditionalOnBean(value = DispatcherServlet.class, name = DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
		public ServletRegistrationBean dispatcherServletRegistration(
				DispatcherServlet dispatcherServlet) {
			// 注册Servlet 和url-mapping
			ServletRegistrationBean registration = new ServletRegistrationBean(
					dispatcherServlet, this.serverProperties.getServletMapping());
			registration.setName(DEFAULT_DISPATCHER_SERVLET_BEAN_NAME);
			registration.setLoadOnStartup(
					this.webMvcProperties.getServlet().getLoadOnStartup());
			if (this.multipartConfig != null) {
				registration.setMultipartConfig(this.multipartConfig);
			}
			return registration;
		}
    }
}

# org.springframework.boot.autoconfigure.web.ServerProperties
@ConfigurationProperties(prefix = "server", ignoreUnknownFields = true)
public class ServerProperties
		implements EmbeddedServletContainerCustomizer, EnvironmentAware, Ordered {
    public String getServletMapping() {
		if (this.servletPath.equals("") || this.servletPath.equals("/")) {
			return "/";
		}
		if (this.servletPath.contains("*")) {
			return this.servletPath;
		}
		if (this.servletPath.endsWith("/")) {
			return this.servletPath + "*";
		}
		return this.servletPath + "/*";
	}
}
```
### 11.3 替换使用其他嵌入式Servlet容器

1. `Tomcat`(默认使用)

```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```
2. `Jetty`

```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <exclusions>
            <exclusion>
                <groupId>org.springframework.boot</groupId>
			    <artifactId>spring-boot-starter-tomcat</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-jetty</artifactId>
    </dependency>
</dependencies>
```
2. `Undertow`

```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <exclusions>
            <exclusion>
                <groupId>org.springframework.boot</groupId>
			    <artifactId>spring-boot-starter-tomcat</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-undertow</artifactId>
    </dependency>
</dependencies>
```

---
### 11.4 嵌入式Servlet容器自动配置原理
1. `EmbeddedServletContainerAutoConfiguration`类自动配置`Servlet`容器
    - `web`环境下导入`BeanPostProcessorsRegistrar`类,该类注册了`EmbeddedServletContainerCustomizerBeanPostProcessor`类
    - 根据导入的嵌入式`Servlet`容器,想容器添加对应的`EmbeddedServletContainerFactory`

```
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@ConditionalOnWebApplication
// 导入BeanPostProcessorsRegistrar类
@Import(BeanPostProcessorsRegistrar.class)
public class EmbeddedServletContainerAutoConfiguration {
    // 1. 根据导入的嵌入式`Servlet`容器,想容器添加对应的`EmbeddedServletContainerFactory`
    
    // 2. BeanPostProcessorsRegistrar类给容器注册EmbeddedServletContainerCustomizerBeanPostProcessor
}
```

2. `EmbeddedServletContainerFactory`嵌入式`Servlet`容器工厂,不同的嵌入式`Servlet`容器有不同的实现
```
EmbeddedServletContainerFactory (org.springframework.boot.context.embedded)
    AbstractEmbeddedServletContainerFactory (org.springframework.boot.context.embedded)
        TomcatEmbeddedServletContainerFactory (org.springframework.boot.context.embedded.tomcat)
        UndertowEmbeddedServletContainerFactory (org.springframework.boot.context.embedded.undertow)
        JettyEmbeddedServletContainerFactory (org.springframework.boot.context.embedded.jetty)
        
# org.springframework.boot.context.embedded.EmbeddedServletContainerFactory
public interface EmbeddedServletContainerFactory {
	EmbeddedServletContainer getEmbeddedServletContainer(
			ServletContextInitializer... initializers);
}
```

3. `EmbeddedServletContainer`: 嵌入式`Servlet`容器, 不同的服务器有不同的实现

```
EmbeddedServletContainer (org.springframework.boot.context.embedded)
    UndertowEmbeddedServletContainer (org.springframework.boot.context.embedded.undertow)
    TomcatEmbeddedServletContainer (org.springframework.boot.context.embedded.tomcat)
    JettyEmbeddedServletContainer (org.springframework.boot.context.embedded.jetty)

```

4. `EmbeddedServletContainerFactory`类通过`getEmbeddedServletContainer()`方法创建`EmbeddedServletContainer`, 以`TomcatEmbeddedServletContainerFactory`为例
    - 创建`Tomcat`对象
    - 配置`Tomcat`
    - 将`Tomcat`对象放入容器,并启动`tomcat`
```
# org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory
public class TomcatEmbeddedServletContainerFactory
		extends AbstractEmbeddedServletContainerFactory implements ResourceLoaderAware {

    @Override
    public EmbeddedServletContainer getEmbeddedServletContainer(
    		ServletContextInitializer... initializers) {
    	Tomcat tomcat = new Tomcat();
    	File baseDir = (this.baseDirectory != null ? this.baseDirectory
    			: createTempDir("tomcat"));
    	tomcat.setBaseDir(baseDir.getAbsolutePath());
    	Connector connector = new Connector(this.protocol);
    	tomcat.getService().addConnector(connector);
    	customizeConnector(connector);
    	tomcat.setConnector(connector);
    	tomcat.getHost().setAutoDeploy(false);
    	configureEngine(tomcat.getEngine());
    	for (Connector additionalConnector : this.additionalTomcatConnectors) {
    		tomcat.getService().addConnector(additionalConnector);
    	}
    	prepareContext(tomcat.getHost(), initializers);
    	return getTomcatEmbeddedServletContainer(tomcat);
    }
    
    protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(
    		Tomcat tomcat) {
    	return new TomcatEmbeddedServletContainer(tomcat, getPort() >= 0);
    }
}

# org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer
public class TomcatEmbeddedServletContainer implements EmbeddedServletContainer {
    public TomcatEmbeddedServletContainer(Tomcat tomcat, boolean autoStart) {
		Assert.notNull(tomcat, "Tomcat Server must not be null");
		this.tomcat = tomcat;
		this.autoStart = autoStart;
		initialize();
	}
}
```

5. `EmbeddedServletContainerCustomizerBeanPostProcessor`: `Bean`初始化前后起作用
    - 前置处理: 判断注入的`Bean`是不是`ConfigurableEmbeddedServletContainer`类型(`ConfigurableEmbeddedServletContainer`是`TomcatEmbeddedServletContainerFactory`的父类),即注入`TomcatEmbeddedServletContainerFactory`类时,前置处理生效,调用`postProcessBeforeInitialization()`方法
    - 处理方法: 遍历容器中的`EmbeddedServletContainerCustomizer`类(嵌入式`Servlet`容器定制类),调用其`customize`为容器进行属性赋值
```
public class EmbeddedServletContainerCustomizerBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {
	// 前置处理,初始化前	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		if (bean instanceof ConfigurableEmbeddedServletContainer) {
			postProcessBeforeInitialization((ConfigurableEmbeddedServletContainer) bean);
		}
		return bean;
	}
    // 后置处理,初始化后
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}
    // 前置处理生效,处理方法
	private void postProcessBeforeInitialization(
			ConfigurableEmbeddedServletContainer bean) {
		for (EmbeddedServletContainerCustomizer customizer : getCustomizers()) {
			customizer.customize(bean);
		}
	}
	// 取出容器中的EmbeddedServletContainerCustomizer
	private Collection<EmbeddedServletContainerCustomizer> getCustomizers() {
		if (this.customizers == null) {
			// Look up does not include the parent context
			this.customizers = new ArrayList<EmbeddedServletContainerCustomizer>(
					this.beanFactory
							.getBeansOfType(EmbeddedServletContainerCustomizer.class,
									false, false)
							.values());
			Collections.sort(this.customizers, AnnotationAwareOrderComparator.INSTANCE);
			this.customizers = Collections.unmodifiableList(this.customizers);
		}
		return this.customizers;
	}
}
```
> `ServerProperties`类就是`EmbeddedServletContainerCustomizer`类的一个实现,`customize()`方法设置`Servlet`容器属性

```
@Override
public void customize(ConfigurableEmbeddedServletContainer container) {
	if (getPort() != null) {
		container.setPort(getPort());
	}
	if (getAddress() != null) {
		container.setAddress(getAddress());
	}
	if (getContextPath() != null) {
		container.setContextPath(getContextPath());
	}
	if (getDisplayName() != null) {
		container.setDisplayName(getDisplayName());
	}
	if (getSession().getTimeout() != null) {
		container.setSessionTimeout(getSession().getTimeout());
	}
	container.setPersistSession(getSession().isPersistent());
	container.setSessionStoreDir(getSession().getStoreDir());
	if (getSsl() != null) {
		container.setSsl(getSsl());
	}
	if (getJspServlet() != null) {
		container.setJspServlet(getJspServlet());
	}
	if (getCompression() != null) {
		container.setCompression(getCompression());
	}
	container.setServerHeader(getServerHeader());
	if (container instanceof TomcatEmbeddedServletContainerFactory) {
		getTomcat().customizeTomcat(this,
				(TomcatEmbeddedServletContainerFactory) container);
	}
	if (container instanceof JettyEmbeddedServletContainerFactory) {
		getJetty().customizeJetty(this,
				(JettyEmbeddedServletContainerFactory) container);
	}

	if (container instanceof UndertowEmbeddedServletContainerFactory) {
		getUndertow().customizeUndertow(this,
				(UndertowEmbeddedServletContainerFactory) container);
	}
	container.addInitializers(new SessionConfiguringInitializer(this.session));
	container.addInitializers(new InitParameterConfiguringServletContextInitializer(
			getContextParameters()));
}
```

#### 总结

1. `Spring Boot`根据依赖情况,向容器中添加对应的嵌入式`Servlet`容器工厂(`EmbeddedServletContainerFactory`)
3. 容器中添加嵌入式`Servlet`容器工厂(`EmbeddedServletContainerFactory`)时,触发嵌入式`Servlet`容器后置处理(`EmbeddedServletContainerCustomizerBeanPostProcessor`)获取所有的嵌入式`Servlet`容器定制类(`EmbeddedServletContainerCustomizer`)配置嵌入式`Servlet`容器工厂(`ConfigurableEmbeddedServletContainer`)
2. 嵌入式`Servlet`容器工厂(`EmbeddedServletContainerFactory`)创建嵌入式`Servlet`容器(`EmbeddedServletContainer`),将服务器对象放入容器并启动

## 12 使用外置`Servlet`容器

### 12.1 步骤

- Idea

    - `Create New Project` -> `Spring Initializr` -> `Project SDK: 1.8` -> `Next`

      -> `Group: com.qpf` -> `Artifact: web-jsp` -> packaging: war -> `Next` 

      -> `Spring boot Version: 1.5.15` -> `Dependencies: web` -> `Next`

      -> `Finish`
> `pom.xml`文件中, 嵌入式`tomcat`指定为`provided`

```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-tomcat</artifactId>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

> 创建`SpringBootServletInitializer`的子类，并调用`configure`方法,启动`SpringBootApplication`主程序

```
public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebJspApplication.class);
    }
}
```

> 需要配置视图解析器`prefix`和`suffix`

```
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
```
或者在`Controller`中指定根路径下的`jsp`文件

```
@Controller
public class HelloController {
    @GetMapping("/hello")
    public String hello(Map<String, Object> map) {
        map.put("msg", "hello");
        return "WEB-INF/jsp/hello.jsp";
    }
}
```
---
### 12.2 原理

- `jar`包形式: 执行`Spring Boot`主程序的`main()`方法, 启动`ioc`容器, 然后创建嵌入式`Servlet`容器
- `war`包形式: 启动服务器,服务器启动`Spring Boot`应用(`SpringBootServletInitializer`),启动`ioc`容器

> `servlet3.0`规范

`ServletContainerInitializer`是`Servlet 3.0`新增的一个接口，主要用于在容器启动阶段通过编程风格注册`Filter`,`Servlet`以及`Listener`，以取代通过`web.xml`配置注册。这样就利于开发内聚的`web`应用框架

- 服务器启动`web`应用,会创建当前应用每个`jar`包中的`ServletContainerInitializer`实例,并执行其`onStartup()`方法

    - `spring-web`包下的`javax.servlet.ServletContainerInitializer`文件记录着`SpringServletContainerInitializer`类的全类名
    ```
    # spring-web-5.0.8.RELEASE.jar!/META-INF/services/javax.servlet.ServletContainerInitializer
    org.springframework.web.SpringServletContainerInitializer
    ```

- `ServletContainerInitializer`实现类添加`@HandlesTypes`注解指定希望处理的类型.

```
@HandlesTypes(WebApplicationInitializer.class)
public class SpringServletContainerInitializer implements ServletContainerInitializer {
}
```

> 流程

 1. 启动服务器时, 根据`spring-web`包下的`javax.servlet.ServletContainerInitializer`文件,创建`SpringServletContainerInitializer`实例,并其执行`onStartup()`方法
 2. `onStartup()`方法获取容器内所有的`WebApplicationInitializer`对象实例(`ServletInitializer`),并其执行`onStartup()`方法
    ```
    public void onStartup(@Nullable Set<Class<?>> webAppInitializerClasses, ServletContext servletContext)
			throws ServletException {

		List<WebApplicationInitializer> initializers = new LinkedList<>();

		if (webAppInitializerClasses != null) {
			for (Class<?> waiClass : webAppInitializerClasses) {
				// Be defensive: Some servlet containers provide us with invalid classes,
				// no matter what @HandlesTypes says...
				if (!waiClass.isInterface() && !Modifier.isAbstract(waiClass.getModifiers()) &&
						WebApplicationInitializer.class.isAssignableFrom(waiClass)) {
					try {
						initializers.add((WebApplicationInitializer)
								ReflectionUtils.accessibleConstructor(waiClass).newInstance());
					}
					catch (Throwable ex) {
						throw new ServletException("Failed to instantiate WebApplicationInitializer class", ex);
					}
				}
			}
		}

		if (initializers.isEmpty()) {
			servletContext.log("No Spring WebApplicationInitializer types detected on classpath");
			return;
		}

		servletContext.log(initializers.size() + " Spring WebApplicationInitializers detected on classpath");
		AnnotationAwareOrderComparator.sort(initializers);
		for (WebApplicationInitializer initializer : initializers) {
			initializer.onStartup(servletContext);
		}
	}
    ```
 3. `createRootApplicationContext()`方法调用`configure()`方法获取`SpringApplicationBuilder`对象,创建`Spring`应用
    ```
    protected WebApplicationContext createRootApplicationContext(
			ServletContext servletContext) {
		SpringApplicationBuilder builder = createSpringApplicationBuilder();
		builder.main(getClass());
		ApplicationContext parent = getExistingRootWebApplicationContext(servletContext);
		if (parent != null) {
			this.logger.info("Root context already created (using as parent).");
			servletContext.setAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, null);
			builder.initializers(new ParentContextApplicationContextInitializer(parent));
		}
		builder.initializers(
				new ServletContextApplicationContextInitializer(servletContext));
		builder.contextClass(AnnotationConfigServletWebServerApplicationContext.class);
		builder = configure(builder);
		builder.listeners(new WebEnvironmentPropertySourceInitializer(servletContext));
		SpringApplication application = builder.build();
		if (application.getAllSources().isEmpty() && AnnotationUtils
				.findAnnotation(getClass(), Configuration.class) != null) {
			application.addPrimarySources(Collections.singleton(getClass()));
		}
		Assert.state(!application.getAllSources().isEmpty(),
				"No SpringApplication sources have been defined. Either override the "
						+ "configure method or add an @Configuration annotation");
		// Ensure error pages are registered
		if (this.registerErrorPageFilter) {
			application.addPrimarySources(
					Collections.singleton(ErrorPageFilterConfiguration.class));
		}
		return run(application);
	}
    ```
4. 启动`Spring`应用,并创建`IOC`容器
---
# 六 Docker

`Docker`是一个开源的应用引擎,是一个轻量级技术.

`Docker`支持将如那件编译成一个镜像,然后配置镜像中的软件.镜像发布出去之后,使用者可以直接使用该镜像.

镜像启动之后生成一个镜像容器

## 1 核心概念

- `Docker`主机(`Host`): 安装`Docker`的机器
- `Docker`客户端(`Client`): 连接`Docker`主机进行操作
- `Docker`仓库(`Registry`):用于保存各种打包好的软件镜像
- `Docker`镜像(`Images`): 软件打包好的镜像,存放在镜像仓库中
- `Docker`容器(`Container`): 镜像启动后的实例称为容器, 容器是独立运行的一个或一组应用

## 2 安装Docker

### 2.1 Ubuntu

```
$ wget -qO- https://get.docker.com/ | sh
```

### 2.2 centos

```
# 1. 检查内核版本3.10及以上
$ uname -r

# 2. 安装Docker
$ sudo yum install docker

# 3. 启动Docker
$ sudo systemctl start docker

# 4. 开机启动Docker
$ sudo systemctl enbale docker

# 5.关闭开机启动Docker
$ sudo systemctl disable docker

# 6. 停止Docker
$ sudo systemctl stop docker
```

### 2.3 ArchLinux

```
# 1. 安装Docker
$ sudo pacman -S docker

# 2. 启动Docker
$ sudo systemctl start docker

# 3. 开机启动Docker
$ sudo systemctl enable docker

# 4. 关闭开机启动Docker
$ sudo systemctl disable docker

# 5. 停止Docker
$ sudo systemctl stop docker

# 6.修改镜像源
# /etc/docker/daemon.json
{
  "registry-mirrors": ["https://docker.mirrors.ustc.edu.cn"]
}
```

## 3 Docker的常用操作和命令

### 3.1 镜像操作

操作 | 命令 | 描述
---|---|---
检索 | `docker search {key}` | 在`docker hub`检索镜像
拉取 | `docker pull {image:tag}` | `:tag`为可选,默认是`latest`
列表 | `docker images` | 查看本地镜像
删除 | `docker rmi {image-id}` | 删除`image-id`对应的镜像

### 3.2 容器操作

1. 启动容器

```
$ docker run --name {container_name} -p {port:port} -d {image:tag}
```
- `--name` 指定容器名
- `-p` 端口映射,`-p 主机端口:容器端口`
- `-d` 后台运行
- `image:tag` 镜像:标签

2. 查看启动的容器

```
$ docker ps
```
3. 查看所有容器

```
$ docker ps -a
```

4. 查看容器日志

```
$ docker logs {container_name/container_id}
```

5. 停止容器

```
$ docker stop {container_id}
```

6. 删除容器

```
$ docker rm {container_id}
```

### 3.3 示例(Mysql)

```
# 从仓库中拉取mysql:latest
$ docker pull mysql

# 启动Mysql容器
$ docker run -d --name mysql5_6 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root mysql:5.6
# 停止Mysql容器
$ docker stop mysql01
```

> 远程登陆可能会出现错误,

```
ERROR 2059 (HY000): Authentication plugin 'caching_sha2_password' cannot be loaded: /usr/lib/mysql/plugin/caching_sha2_password.so: cannot open shared object file: No such file or directory
```
> 需要本地登陆启动远程权限

```
# 1.进入mysql容器
$ docker exec -it mysql2 /bin/bash

# 2.进入mysql
$ mysql -uroot -p

> ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'root';
```
---
# 七 数据访问

## 数据源
### JDBC

#### 1.1 配置JDBC数据源

- 导入`JDBC`以及`mysql`驱动

```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```
- 配置数据源

```
# application.yaml

spring:
  datasource:
    username: {name}
    password: {password}
    url: jdbc:mysql://{hostname}:3306/{database}
    driver-class-name: com.mysql.jdbc.Driver
    schema: classpath:sql/tables.sql
```

- 添加建表sql文件`classpath:sql/tables.sql`

```
-- CREATE DATABASE IF NOT EXISTS ssm DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS dept (
  id int(11) auto_increment not null comment '部门id',
  name varchar(255) not null comment '部门名称',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment = '部门表';

CREATE TABLE IF NOT EXISTS empl (
  id int(11) auto_increment not null comment '员工id',
  name varchar(255) not null comment '员工姓名',
  gender char(1) not null comment '员工性别',
  email varchar(255) not null comment '员工邮箱',
  birth date not null comment '员工生日',
  dept_id int(11) not null comment '员工部门id',
  PRIMARY KEY (id),
--  KEY `dept_id` (`dept_id`),
  CONSTRAINT `empl_ibfk_1` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment = '员工表';
```
- 自动注入数据源,获取连接

```
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootQuickApplicationTests {
    @Test
    public void datasource() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
    }
}
------------------------------------
class org.apache.tomcat.jdbc.pool.DataSource
ProxyConnection[PooledConnection[com.mysql.jdbc.JDBC4Connection@12ad1b2a]]
```
> 默认使用`class org.apache.tomcat.jdbc.pool.DataSource`作为数据源,相关的数据源配置在`DataSourceProperties`类中

#### 1.2 原理
1. `Spring Boot`默认支持一下数据源

- `org.apache.tomcat.jdbc.pool.DataSource`
- `HikariDataSource`
- `org.apache.commons.dbcp.BasicDataSource`
- `org.apache.commons.dbcp2.BasicDataSource`
- 还可以配置`spring.datasource.type`属性,利用反射创建数据源,并且绑定相关属性
    ```
    @ConditionalOnMissingBean(DataSource.class)
	@ConditionalOnProperty(name = "spring.datasource.type")
	static class Generic {

		@Bean
		public DataSource dataSource(DataSourceProperties properties) {
			return properties.initializeDataSourceBuilder().build();
		}

	}
    ```
2. 数据源自动配置(`DataSourceSAutoConfiguration`)向容器中添加了`DataSourceInitializer`组件,可以执行建表`sql`和数据插入`sql`.

- `runSchemaScripts()`:
    - `spring.datasource.schema`指向建表`sql`
    - 默认为:`classpath:schema-all.sql`或`classpath:schema.sql`
- `runDataScripts()`:
    - `spring.datasource.data`指向数据插入`sql`
    - 默认为:`classpath:schema-all.sql`或`classpath:data.sql`

```
class DataSourceInitializer implements ApplicationListener<DataSourceInitializedEvent> {
    @PostConstruct
	public void init() {
		if (!this.properties.isInitialize()) {
			logger.debug("Initialization disabled (not running DDL scripts)");
			return;
		}
		if (this.applicationContext.getBeanNamesForType(DataSource.class, false,
				false).length > 0) {
			this.dataSource = this.applicationContext.getBean(DataSource.class);
		}
		if (this.dataSource == null) {
			logger.debug("No DataSource found so not initializing");
			return;
		}
		runSchemaScripts();
	}

	private void runSchemaScripts() {
		List<Resource> scripts = getScripts("spring.datasource.schema",
				this.properties.getSchema(), "schema");
		if (!scripts.isEmpty()) {
			String username = this.properties.getSchemaUsername();
			String password = this.properties.getSchemaPassword();
			runScripts(scripts, username, password);
			try {
				this.applicationContext
						.publishEvent(new DataSourceInitializedEvent(this.dataSource));
				// The listener might not be registered yet, so don't rely on it.
				if (!this.initialized) {
					runDataScripts();
					this.initialized = true;
				}
			}
			catch (IllegalStateException ex) {
				logger.warn("Could not send event to complete DataSource initialization ("
						+ ex.getMessage() + ")");
			}
		}
	}

	@Override
	public void onApplicationEvent(DataSourceInitializedEvent event) {
		if (!this.properties.isInitialize()) {
			logger.debug("Initialization disabled (not running data scripts)");
			return;
		}
		// NOTE the event can happen more than once and
		// the event datasource is not used here
		if (!this.initialized) {
			runDataScripts();
			this.initialized = true;
		}
	}
	private void runDataScripts() {
		List<Resource> scripts = getScripts("spring.datasource.data",
				this.properties.getData(), "data");
		String username = this.properties.getDataUsername();
		String password = this.properties.getDataPassword();
		runScripts(scripts, username, password);
	}
    private List<Resource> getScripts(String propertyName, List<String> resources,
			String fallback) {
		if (resources != null) {
			return getResources(propertyName, resources, true);
		}
		String platform = this.properties.getPlatform();
		List<String> fallbackResources = new ArrayList<String>();
		fallbackResources.add("classpath*:" + fallback + "-" + platform + ".sql");
		fallbackResources.add("classpath*:" + fallback + ".sql");
		return getResources(propertyName, fallbackResources, false);
	}
}
```
3. 自动配置`JdbcTemplate`和`NamedParameterJdbcTemplate`

```
@Configuration
@ConditionalOnClass({ DataSource.class, JdbcTemplate.class })
@ConditionalOnSingleCandidate(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class JdbcTemplateAutoConfiguration {

	private final DataSource dataSource;

	public JdbcTemplateAutoConfiguration(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Bean
	@Primary
	@ConditionalOnMissingBean(JdbcOperations.class)
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(this.dataSource);
	}

	@Bean
	@Primary
	@ConditionalOnMissingBean(NamedParameterJdbcOperations.class)
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(this.dataSource);
	}

}
```

### 2 Druid

1. 导入`Druid`

```
<dependences>
    <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
        <version>1.1.10</version>
    </dependency>
</dependences>
```

2. 配置数据源
```
# application.yaml
spring:
  datasource:
    username: mysql
    password: mysql
    url: jdbc:mysql://116.85.54.176:3306/ssm
    driver-class-name: com.mysql.jdbc.Driver
    schema: classpath:sql/tables.sql
    type: com.alibaba.druid.pool.DruidDataSource
    initialSize: 5
    minIdle: 5
    maxActive: 20
    maxWait: 60000
    timebetweenEvictionRunsMillis: 60000
    minEvictableidleTimeMillis: 300000
    validationQuery: SELECT 1 FROM DUAL
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true
#   配置监控统计拦截的filters, 去掉之后监控界面无法统计sql,wall用于防火墙
    filters: stat,wall,log4j
    maxPoolPreparedStatementPerConnectionSize: 20
    useGlobalDataSourceStat: true
    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
```
3. 向容器中添加
- `DruidSDataSource`: 数据源
- `StatViewServlet`: 监控`Servlet`(`/druid/*`)
- `WebStatFilter`: 监控过滤器

```
@Configuration
public class DruidConfig {
    @ConfigurationProperties(value = "spring.datasource")
    @Bean
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
//      参数设置com.alibaba.druid.support.http.ResourceServlet
        Map<String, String> initParam = new HashMap<>();
//      登陆用户
        initParam.put("loginUsername", "admin");
//      登陆密码
        initParam.put("loginPassword", "admin");
//      默认或者设为空,允许所有IP地址
        initParam.put("allow", "");
//        禁止访问的IP地址
//        initParam.put("deny", "");

        bean.setInitParameters(initParam);

        return bean;
    }
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        bean.setUrlPatterns(Arrays.asList("/test/*"));

        Map<String, String> initParam = new HashMap<>();
        initParam.put("exclusions", "/public/*,*.js,*.css,/druid/*,*.jsp,*.swf");
        bean.setInitParameters(initParam);

        return bean;
    }
}
```

4. 测试`JdbcTemplate`操作数据库
```
@RestController
public class DruidController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/test/query")
    public Map<String, Object> query() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from dept");
        return list.size() > 0 ? list.get(0) : new HashMap<>();
    }
}
```
## 3 整合MyBatis

### 3.1 注解版

1. 编写一个`Mapper`接口, 添加`@Mapper`注解标记其为一个`Mapper`接口

```
@Mapper
public interface DepartmentMapper {
    // 返回自增主键
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into dept(name) values(#{name})")
    public int insertDepartment(Department department);
    @Delete("delete from dept where id = #{id}")
    public int deleteDepartmentById(Integer id);
    @Update("update dept set name = #{name} where id = #{id}")
    public int updateDepartmentById(Integer id);
    @Select("Select * from dept where id = #{id}")
    public Department selectDepartmentById(Integer id);
}
```

> 标记`Mapper`接口的方法:

- `Mapper`接口添加`@Mapper`注解
- 主程序类添加注解`@MapperScan(basePackages = "com.qpf.springbootquick.mapper")`注定`Mapper`包

2. 自定义`MyBatis`配置规则,向容器中添加`ConfigurationCustomizer`组件

```
@Configuration
public class MyBatisConfig {
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                // 启用驼峰命名法
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}
```

3. `Conctroller`中使用`Mapper`接口

```
@RestController
public class MybatisController {
    @Autowired
    private DepartmentMapper departmentMapper;

    @GetMapping("/test/getdept/{id}")
    public Map<String, Object> selectDepart(@PathVariable("id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        Department department = departmentMapper.selectDepartmentById(id);
        System.out.println("department: " + department);
        map.put("dept", department);
        return map;
    }

    @GetMapping("/test/dept")
    public Map<String, Object> insertDepartment(Department department) {
        Map<String, Object> map = new HashMap<>();
        int i = departmentMapper.insertDepartment(department);
        System.out.println("insert: " + i);
        map.put("dept", department);
        return map;
    }
}
```
### 3.2 配置版

1. 配置文件指定`MyBatis`配置文件和`sql`映射文件位置
```
# application.yaml
mybatis:
  config-location: classpath:mybatis/mybatis-config.xml
  mapper-locations: classpath:mybatis/mapper/*.xml
```

2. `MyBatis`全局配置文件,启用驼峰命名法

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
</configuration>
```
3. `Mapper`接口,添加`@Mapper`注解或者放在主程序类`@MapperScan`注解指定的包路径下

```
@Mapper
public interface EmployeeMapper {
    public Employee selectEmployeeById(Integer id);
    public int insertEmployee(Employee employee);
}
```
4. `Mapper`映射文件

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.qpf.springbootquick.mapper.EmployeeMapper">
    <select id="selectEmployeeById" resultType="com.qpf.springbootquick.bean.Employee">
      select * from empl where id = #{id}
    </select>
    <insert id="insertEmployee" useGeneratedKeys="true" keyProperty="id">
        INSERT INTO empl(name, gender, email, birth, dept_id) value (#{name}, #{gender}, #{email}, #{birth}, #{dept_id})
    </insert>
</mapper>
```
5. `Conctroller`中使用`Mapper`接口

```
@RestController
public class MybatisController {
    private DepartmentMapper departmentMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping("test/getempl/{id}")
    public Map<String, Object> selectEmpl(@PathVariable("id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        Employee employee = employeeMapper.selectEmployeeById(id);
        System.out.println("employee: " + employee);
        map.put("empl", employee);
        return map;
    }

    @GetMapping("/test/empl")
    public Map<String, Object> insertEmployee(Employee employee) {
        Map<String, Object> map = new HashMap<>();
        int insert = employeeMapper.insertEmployee(employee);
        System.out.println("insert: " + insert);
        map.put("empl", employee);
        return map;
    }
}
```
## 整合SpringData JPA

1. 编写一个实体类映射数据表,并配置映射关系
    - `@Entity`: 标记实体类
    - `@Table`: 指定对应数据表,默认表名为类名首字母小写
    - `@Id`: 指定主键
    - `@GeneratedValue`: 指定主键增长类型
    - `@Column`: 指定字段

```
// 指定实体类
@Entity
// 指定对应数据表,默认类名首字母小写
@Table(name = "company")
public class Company {
    // 主键
    @Id
    // 自增长
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 指定字段名和长度
    @Column(name = "name", length = 255)
    private String name;
    // 默认属性名就是字段名
    @Column
    private Integer status;
    // constructor getter setter tostring
}
```
2. 编写一个`Dao`接口,继承`JpaRepository`,并指定操作的实体类类型及其主键类型

```
// 继承JpaRepository类,范型指定操作类的类型,主键类型
public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
```
3. 配置`Jpa`属性

```
spring:
    jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```
4. `Controller`中使用`Dao`接口

```
@RestController
public class JpaController {
    @Autowired
    CompanyRepository companyRepository;

    @GetMapping("/test/getcompany/{id}")
    public Map<String, Object> getCompany(@PathVariable("id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        Company company = companyRepository.findOne(id);
        System.out.println("Company: " + company);
        map.put("company", company);
        return map;
    }
    @GetMapping("/test/company")
    public Map<String, Object> insertCompany(Company company) {
        Map<String, Object> map = new HashMap<>();
        Company company1 = companyRepository.save(company);
        System.out.println("company: " + company);
        map.put("company", company1);
        return map;
    }
}
```
---
# 八 启动原理

> 从`META-INF/spring.factories`文件中读取`ApplicationContextInitializer`和`SpringApplicationRunListener`,并保存起来.

> 加载`IOC`容器中的`ApplcationRunner`和`CommandLineRunner`

## 1 启动流程:

1. 创建`SpringApplication`对象
    - 保存主配置类
    - 判断是否为`web`环境
    - 从`META-INF/spring.factories`文件中读取`ApplicationContextInitializer和SpringApplicationListener`,并保存起来.
    - 找到有`main`方法的主配置类
2. 运行`SringApplication`对象的`run()`方法
    - 从`META-INF/spring.factories`文件中获取`SpringApplicationRunListener`监听器,并调用其`starting()`方法
    - 封装命令行参数
    - 创建环境
    - 创建`IOC`容器
    - 准备上下文: 
        - `IOC`容器保存环境,
        - 注册组件(`Add boot specific singleton beans`),
        - 回调所有`ApplicationContextInitializer`的`initialize()`方法
        - 回调所以`SpringApplicationRunListener`的`contextPrepare()`方法
    - 刷新`IOC`容器
    - 从`IOC`容器获取所以`ApplcationRunner`和`CommandLineRunner`,并回调
3. 自定义事件监听
    - 编写自定义的`SpringApplicationInitializer`和`SpringApplicationRunListener`
    ```
    public class HelloApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            System.out.println("ApplicationContextInitializer----initializer----" + applicationContext);
        }
    }
    
    public class HelloSpringApplicationRunListener implements SpringApplicationRunListener {
        public HelloSpringApplicationRunListener(SpringApplication application, String[] args){
            System.out.println("HelloSpringApplicationRunListener--------------------");
            System.out.println("application: " + application);
            System.out.println("args: " + Arrays.asList(args));
            System.out.println("--------------------HelloSpringApplicationRunListener");
        }
        @Override
        public void starting() {
            System.out.println("SpringApplicationRunListener---- starting----");
        }
        @Override
        public void environmentPrepared(ConfigurableEnvironment environment) {
            System.out.println("SpringApplicationRunListener---- environmentPrepared----" + environment.getSystemProperties().get("os.name"));
        }
        @Override
        public void contextPrepared(ConfigurableApplicationContext context) {
            System.out.println("SpringApplicationRunListener---- contextPrepared----");
        }
        @Override
        public void contextLoaded(ConfigurableApplicationContext context) {
            System.out.println("SpringApplicationRunListener---- contextLoaded----");
        }
        @Override
        public void finished(ConfigurableApplicationContext context, Throwable exception) {
            System.out.println("SpringApplicationRunListener---- finished----");
        }
    }
    ```
    - `META-INF/spring.factories`文件中注册`SpringApplicationInitializer`和`SpringApplicationRunListener`
    ```
    org.springframework.context.ApplicationContextInitializer=\
    com.qpf.springbootquick.listeners.HelloApplicationContextInitializer
    
    org.springframework.boot.SpringApplicationRunListener=\
    com.qpf.springbootquick.listeners.HelloSpringApplicationRunListener
    ```
    - 编写自定义的`ApplicationRunner`和`CommandLineRunner`
    ```
    public class HelloApplicationRunner implements ApplicationRunner {
        @Override
        public void run(ApplicationArguments args) throws Exception {
            System.out.println("ApplicationRunner---run: " + Arrays.asList(args));
        }
    }
    
    public class HelloCommandLineRunner implements CommandLineRunner {
        @Override
        public void run(String... args) throws Exception {
            System.out.println("CommandLineRunner----run: " + Arrays.asList(args));
        }
    }
    ```
    - 容器添加自定义的`ApplicationRunner`和`CommandLineRunner`
    ```
    @Configuration
    public class MyAppConfig {
        @Bean
        public ApplicationRunner addApplicationRunner() {
            return new HelloApplicationRunner();
        }
        @Bean
        public CommandLineRunner addCommandLineRunner() {
            return new HelloCommandLineRunner();
        }
    }
    ```
> 执行的结果

```
HelloSpringApplicationRunListener--------------------
application: org.springframework.boot.SpringApplication@3108bc
args: []
--------------------HelloSpringApplicationRunListener
SpringApplicationRunListener---- starting----
SpringApplicationRunListener---- environmentPrepared----Linux
ApplicationContextInitializer----initializer----org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@77fbd92c: startup date [Thu Jan 01 08:00:00 CST 1970]; root of context hierarchy
SpringApplicationRunListener---- contextPrepared----
SpringApplicationRunListener---- contextLoaded----
ApplicationRunner---run: [org.springframework.boot.DefaultApplicationArguments@57f0bfc3]
CommandLineRunner----run: []
SpringApplicationRunListener---- finished----
```

# 九 自定义starter

## 1 starter

1. 场景依赖
2. 自动配置注解
```
@Configuration // 指定这个类为配置类
@ConditiinXXXX // 指定自动配置类的生效条件
@AutoConfigAfter // 自动配置类的生效条件
@Bean //向容器中添加组件

@ConfigurationProperties // 绑定配置文件到xxxxproperties类
@EnableConfigurationProperties // 导入xxxProperties类到自动配置类

自动配置类要注册在META-INF/spring.factories文件中
# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
package.to.autoConfigurationClass
```
3. 模式
    - `xxx-spring-boot-starter`模块只用来导入依赖
    - `xxx-spring-boot-starter-autoconfigurer`模块完成自动配置
## 2 步骤
### 2.1 启动器模块

1. 导入自动配置模块

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.qpf.starter</groupId>
    <artifactId>qpf-spring-boot-start</artifactId>
    <version>1.0-SNAPSHOT</version>
    <dependencies>
        <!-- 导入自动配置模块 -->
        <dependency>
            <groupId>com.qpf.starter</groupId>
            <artifactId>qpf-spring-boot-start-autoconfigurter</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
    </dependencies>
</project>
```

### 2.2 自动配置模块
1. 导入`spring-boot-starter`基础模块
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.qpf.starter</groupId>
    <artifactId>qpf-spring-boot-start-autoconfigurter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>
    <name>qpf-spring-boot-start-autoconfigurter</name>
    <description>Demo project for Spring Boot</description>
    <!-- 继承spring-boot-starter-parent -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.16.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>
    <dependencies>
        <!-- 导入spring-boot-starter 基模 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
    </dependencies>
</project>
```
2. 场景组件
```
public class HelloService {
    private HelloProperties properties;
    public HelloService(HelloProperties properties) {
        this.properties = properties;
    }
    public String sayHello(String name) {
        return properties.getPrefix() + " - " + name + " - " + properties.getSuffix();
    }
}
```
3. 配置文件类
```
@ConfigurationProperties(prefix = "qpf.hello")
public class HelloProperties {
    private String prefix;
    private String suffix;
    public String getPrefix() {
        return prefix;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public String getSuffix() {
        return suffix;
    }
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
```
4. 自动配置类,注册场景组件
```
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(HelloProperties.class)
public class HelloAutoConfigurer {
    @Autowired
    private HelloProperties properties;
    @Bean
    public HelloService addHelloService() {
        return new HelloService(properties);
    }
}
```
5. 在`META-INF/spring.factories`文件注册自动配置类
```
# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.qpf.starter.autoconfigurer.HelloAutoConfigurer
```

6. 安装自动配置模块和启动器模块到`Maven`仓库

### 2.3 工程中使用自定义的启动器

1. 导入自定义场景启动器
```
<dependence>
    <dependency>
        <groupId>com.qpf.starter</groupId>
        <artifactId>qpf-spring-boot-start</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependence>
```

2. `StarterController`使用自定义场景的组件
```
@RestController
public class StarterController {
    @Autowired
    private HelloService helloService;
    @GetMapping("/test/starter/{name}")
    public Map<String, Object> hello(@PathVariable("name") String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("starter", helloService.sayHello(name));
        return map;
    }
}
```
3. 配置自定义组件
```
qpf.hello.prefix=request
qpf.hello.suffix=[ok]
```

---
# 参考

- [SpringMVC+Thymeleaf 简单使用](https://www.cnblogs.com/litblank/p/7988689.html)
- [Jetty使用教程（一）开始使用Jetty](https://blog.csdn.net/qq_37878579/article/details/78404931)
- [嵌入式tomcat学习笔记](https://www.cnblogs.com/lmq-1048498039/p/8329481.html)
- [详解Spring MVC4 纯注解配置教程](https://www.jb51.net/article/112496.htm)