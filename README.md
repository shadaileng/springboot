# Spring Boot

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
## @Value注入

