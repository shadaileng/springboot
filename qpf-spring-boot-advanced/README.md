# <center>Spring boot 高级</center>

[toc]

# 一、 Spring Boot与缓存

## 1 JSR-107规范

`Java cahcing`定义了5个核心接口:
- `CachingProvider`: 定义了创建、配置、获取、管理和控制多个`CacheManager`,一个应用可以在运行期访问多个`CacheProvider`
- `CacheManager`: 定义了创建、配置、获取、管理和控制多个唯一命名的`Cache`,这些`Cache`存在于`CacheManager`上下文中.一个`CacheManager`只能被一个`CacheProvider`所拥有
- `Cache`: 一个类似`Map`的数据结构,用于临时存储以`key`为索引的值,一个`Cache`只能被一个`CacheManager`所拥有
- `Entery`: 一个储存在`Cache`中的键值对
- `Expiry`: 每个存储在`Cache`中的记录都有一个有效期.一旦超过有效期,条目则会过期,过期的条目将不能访问、修改和删除.可以通过`ExpiryPolicy`设置缓存有效期

## 2 Spring缓存抽象

### 2.1 简介

`Spring`从`3.1`的版本开始定义了`org.springframework.cache.Cache`和`org.springframework.cache.CacheManager`接口来统一不同的缓存技术,并支持使用`JSR-107`注解简化开发

- `Cache`为缓存组件的规范定义,包含缓存的各种操作集合
- `Spring`提供了`Cache`接口的各种`xxxCache`实现:
    - `RedisCache`
    - `EhCacheCache`
    - `ConCurrentMapCache`
- 每次调用需要缓存的方法时,`Spring`会检查指定参数的目标方法的是否已经被调用过;
    - 如果有被调用过,就从缓存中获取该方法调用后的结果
    - 如果没有被调用过,就调用该方法并缓存结果后返回给用户
- 使用缓存抽象需要注意:
    - 确定需要被缓存的方法以及缓存策略
    - 从缓存中读取缓存数据

### 2.2 重要概念以及注解


概念 / 注解 | 描述
---|---
`Cache` | 缓存接口,用于定义缓存操作;
`CacheManager` | 缓存管理器,管理各种缓存组件
`@Cacheable` | 针对方法配置,根据方法的请求参数对结果进行缓存
`@CacheEvict` | 清空缓存
`@CachePut` | 调用方法,并更新缓存
`@EnableCaching` | 开启基于注解的缓存
`keyGenerator` | 缓存数据`key`的生存策略
`serialize` | 缓存数据`value`序列化策略

### 2.3 Cache中使用SpEL


类型 | 位置 | 描述 | 示例
---|--- | --- | ---
`methodName` | `root object` | 当前标注的方法名 | `#root.methodName`
`method` | `root object` | 当前标注的方法 | `#root.method.name`
`tager` | `root object` | 当前被调用的目标对象 | `#root.target`
`targetClass` | `root object` | 当前被调用的目标对象类 | `#root.tagertClass`
`args` | `root object` | 当前被调用方法的参数列表 | `#root.args[0]`
`caches` | `root object` | 当前调用方法使用的缓存列表 | `#root.caches[0].name`
`argument name` | `evaluation context` | 当前调用方法参数名,或索引 | `#a0 / #p0 / #id`
`result` | `evaluation context` | 方法执行之后的返回值 | `#result`


### 2.4 缓存的使用

1. 搭建基本环境

    - 开启`mapper日志`:
    ```
    # application.properties
    logging.level.package.to.mapper=debug
    ```
2. 开启基于注解的缓存,主程序类添加`@EnableCaching`注解

```
@EnableCaching
@SpringBootApplication
public class SpringBootQuickApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootQuickApplication.class, args);
    }
}
```

3. 方法上标注缓存注解
- `@Cacheable`: 标注的方法执行之前检查缓存中是否有`keyGenerator`生成的`key`对应的数据,如果有就直接从缓存中返回对应数据,没有就运行标注的方法并把结果放入缓存中.
    - `cacheNames/value`: 指定缓存组件
        - 缓存组件名为缓存组件的唯一标识名,用于区分不同类型数据的管理
        - 可以指定多个缓存组件: `{"cache1", "cache2"}`
    - `key`: 缓存数据的`key`
        - 默认使用标注的方法的参数值(`{param:result}`)
        - `SpEl`,`key`在获取缓存之前生存,所以不能使用`#result`
    - `keyGenerator`:键生成器,`key`未指定时生效
        - 生成默认的`key`就是使用键生成器(`SimpleKeyGenerator`)
            - 调用的方法没有参数: `key = new SimpleKey()`
            - 调用的方法只有一个参数: `key = 参数值`
            - 调用方法有多个参数: `key = new Simple(params)`
        - 自定义`keyGenerator`
        ```
        @Configuration
        public class CacheConfig {
            @Bean
            public KeyGenerator customKeyGenerator () {
                return new KeyGenerator(){
                    @Override
                    public Object generate(Object target, Method method, Object... params) {
                        return method.getName + Arrays.asList(params);
                    }
                };
            }
        }
        ---------------------------------
        @Cacheable(value={"cache1"}, keyGenerator= "customKeyGenerator")
        ```
    - `cacheManager`: 指定缓存管理器
    - `cacheResolver`: 指定缓存解析器,与`cacheManager`二选一
    - `condition`: 缓存符合条件,`SpEL`
    - `unless`: 缓存不符合条件,`SpEL`
    - `sync`: 是否使用异步模式,设置为`true`,`unless`则会失效
- `@CachePut`: 先调用方法的,然后更新缓存.
    - 用于更新数据库数据,然后更新缓存
    - `value`、`key`和`CacheManager`应该与`@Cacheable`一致(`key="#result.id"`),保证缓存同步更新
- `@CacheEvict`: 默认先调用方法再清除缓存
    - 用于删除数据库数据,然后清除缓存
    - `allEntries`: 是否删除所有缓存,默认`false`
    - `beforeInvocation`: 是否在调用方法前清楚缓存,默认`false`
- `@Caching`: 复合缓存,可存放多个缓存
    - 注解中添加多个注解
    ```
    @Caching(
        cacheable = {
            @Cacheable(...),
            @Cacheable(...)
        },
        put = {
            @CachePut(...),
            @CachePut(...)
        },
        evict = {
            @CacheEvict(...),
            @CacheEvict(...)
        }
    )
    ```
- `CacheConfig`: 类注解,设置公共的`cacheName`、`keyGenerator`和`cacheManager`

```
@CacheConfig(cacheNames = "emp")
@Repository
public class EmployeeService {
    @Cacheable(value = "emp", key = "'emp:'+#id")
    public Employee getOne(Integer id) {
        logger.info("getEmp: " + id);
        return map.get(id);
    }
    @CachePut(value = "emp", key="'emp:' + #result.id")
    public Employee editOne(Employee employee) {
        logger.info("editEmp: " + employee);
        employee.getDepartment().setName(DepartmentService.getOne(employee.getDepartment().getId().toString()).getName());
        map.put(employee.getId(), employee);
        return employee;
    }
    @CacheEvict(value = "emp", key = "'emp:' + #id")
    public int delOne(Integer id) {
        logger.info("delEmp: " + id);
        if (map.containsKey(id)) {
            map.remove(id);
        } else{
            logger.info("该员工不在记录中");
        }
        return 0;
    }
}
```

### 2.5 缓存原理

> 默认使用`ConcurrentMapCacheManager`缓存管理器创建和获取`ConcurrentMapCache`缓存组件,缓存数据保存在`ConcurrentMap`中

1. 自动配置类`CacheAutoConfiguration`导入,缓存配置类,默认`SimpleCacheConfiguration`类生效

```
----------------------------------------------------------
# 缓存配置类
org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
org.springframework.boot.autoconfigure.cache.GuavaCacheConfiguration
org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration
org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
----------------------------------------------------------
@Import(CacheConfigurationImportSelector.class)
public class CacheAutoConfiguration {
    static class CacheConfigurationImportSelector implements ImportSelector {
		@Override
		public String[] selectImports(AnnotationMetadata importingClassMetadata) {
			CacheType[] types = CacheType.values();
			String[] imports = new String[types.length];
			for (int i = 0; i < types.length; i++) {
				imports[i] = CacheConfigurations.getConfigurationClass(types[i]);
			}
			return imports;
		}

	}
}

final class CacheConfigurations {
	private static final Map<CacheType, Class<?>> MAPPINGS;
	static {
		Map<CacheType, Class<?>> mappings = new HashMap<CacheType, Class<?>>();
		mappings.put(CacheType.GENERIC, GenericCacheConfiguration.class);
		mappings.put(CacheType.EHCACHE, EhCacheCacheConfiguration.class);
		mappings.put(CacheType.HAZELCAST, HazelcastCacheConfiguration.class);
		mappings.put(CacheType.INFINISPAN, InfinispanCacheConfiguration.class);
		mappings.put(CacheType.JCACHE, JCacheCacheConfiguration.class);
		mappings.put(CacheType.COUCHBASE, CouchbaseCacheConfiguration.class);
		mappings.put(CacheType.REDIS, RedisCacheConfiguration.class);
		mappings.put(CacheType.CAFFEINE, CaffeineCacheConfiguration.class);
		addGuavaMapping(mappings);
		mappings.put(CacheType.SIMPLE, SimpleCacheConfiguration.class);
		mappings.put(CacheType.NONE, NoOpCacheConfiguration.class);
		MAPPINGS = Collections.unmodifiableMap(mappings);
	}
	@Deprecated
	private static void addGuavaMapping(Map<CacheType, Class<?>> mappings) {
		mappings.put(CacheType.GUAVA, GuavaCacheConfiguration.class);
	}
	public static String getConfigurationClass(CacheType cacheType) {
		Class<?> configurationClass = MAPPINGS.get(cacheType);
		Assert.state(configurationClass != null, "Unknown cache type " + cacheType);
		return configurationClass.getName();
	}
}
```

2. `SimpleCacheConfiguration`类向容器注册了一个`CacheManager`组件: `ConcurrentMapCacheManager`

```
class SimpleCacheConfiguration {
    @Bean
	public ConcurrentMapCacheManager cacheManager() {
		ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
		List<String> cacheNames = this.cacheProperties.getCacheNames();
		if (!cacheNames.isEmpty()) {
			cacheManager.setCacheNames(cacheNames);
		}
		return this.customizerInvoker.customize(cacheManager);
	}
}
```

3. `ConcurrentMapCacheManager`或获取`ConcurrentMapCache`缓存组件,如果未取到则创建`ConcurrentMapCache`缓存组件

```
public class ConcurrentMapCacheManager implements CacheManager, BeanClassLoaderAware {
    @Override
	public Cache getCache(String name) {
		Cache cache = this.cacheMap.get(name);
		if (cache == null && this.dynamic) {
			synchronized (this.cacheMap) {
				cache = this.cacheMap.get(name);
				if (cache == null) {
					cache = createConcurrentMapCache(name);
					this.cacheMap.put(name, cache);
				}
			}
		}
		return cache;
	}
	protected Cache createConcurrentMapCache(String name) {
		SerializationDelegate actualSerialization = (isStoreByValue() ? this.serialization : null);
		return new ConcurrentMapCache(name, new ConcurrentHashMap<Object, Object>(256),
				isAllowNullValues(), actualSerialization);

	}
}
```

4. `ConcurrentMapCache`缓存组件将缓存数据以键值对的方式存放在`ConcurrentMap`中,从`ConcurrentMapp`中未获取到对应数据,则调用标注方法,返回结果保存到缓存中

```
public class ConcurrentMapCache extends AbstractValueAdaptingCache {
	private final ConcurrentMap<Object, Object> store;
	@Override
	protected Object lookup(Object key) {
		return this.store.get(key);
	}
    @Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		// Try efficient lookup on the ConcurrentHashMap first...
		ValueWrapper storeValue = get(key);
		if (storeValue != null) {
			return (T) storeValue.get();
		}
		// No value found -> load value within full synchronization.
		synchronized (this.store) {
			storeValue = get(key);
			if (storeValue != null) {
				return (T) storeValue.get();
			}
			T value;
			try {
				value = valueLoader.call();
			}
			catch (Throwable ex) {
				throw new ValueRetrievalException(key, valueLoader, ex);
			}
			put(key, value);
			return value;
		}
	}
}
```

> 流程:

1. 标注的方法运行之前,`CacheManager`根据`cacheName`查询`Cache`缓存组件,第一次获取缓存时如果没有获取到`Cache`组件则会自动创建.
2. `keyGenerator`默认使用`SimpleKeyGenerator`生成一个`key`.
    - 标注的方法没有参数: `key = new SimpleKey()`
    - 标注的方法只有一个参数: `key = 参数值`
    - 标注方法有多个参数: `key = new SimpleKey(params)`
3. `Cache`组件根据`key`获取缓存内容,如果没有获取到缓存内容,则会调用标注方法,返回结果保存到缓存中.

总结: `@Cacheable`标注的方法执行之前检查缓存中是否有`keyGenerator`生成的`key`对应的数据,如果有就直接从缓存中返回对应数据,没有就运行标注的方法并把结果放入缓存中.

## 3 Redis缓存


### 3.1 搭建Redis环境

1. 运行`Redis`的`Docker`镜像

```
# 拉取Redis镜像
$ docker pull redis
# 运行Redis镜像
$ docker run -d -p 6379:6379 --name redis01 redis
# 运行Redis容器的bash
$ docker exec -it {container_id} /bin/bash
```
2. 配置`Redis`
    - 导入`Redis`启动器
        ```
        <dependencys>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-data-redis</artifactId>
            </dependency>
        </dependencys>
        ```
    - 配置`Redis`
        ```
        spring.redis.host=xxx.xxx.xxx.xxx
        ```
    - 测试连通
        ```
        @RunWith(SpringRunner.class)
        @SpringBootTest
        public class SpringBootQuickApplicationTests {
            @Autowired
            private StringRedisTemplate stringRedisTemplate;
            @Test
            public void testRedis() {
                ValueOperations<String, String> strOps = stringRedisTemplate.opsForValue();
                strOps.set("msg", "hello");
                System.out.println(String.format("msg: %s", strOps.get("msg")));
                strOps.append("msg", " world");
                System.out.println(String.format("msg: %s", strOps.get("msg")));
            }
        }
        ```
3. 自定义对应实体类的`CacheManager`和`RedisTemplate`
    - 设置`key`使`cacheName:`作为前缀: `redisCacheManager.setUsePrefix(true);`

```
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<Object, Employee> employeeRedisTemplate(
            RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Employee> template = new RedisTemplate<Object, Employee>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Employee> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Employee.class);
        template.setDefaultSerializer(jsonRedisSerializer);
        return template;
    }
    @Bean
    public RedisTemplate<Object, Department> departmentRedisTemplate(
            RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Department> template = new RedisTemplate<Object, Department>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Department> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Department.class);
        template.setDefaultSerializer(jsonRedisSerializer);
        return template;
    }
    @Bean
    public RedisCacheManager employeeRedisCacheManager(RedisTemplate employeeRedisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(employeeRedisTemplate);
        redisCacheManager.setUsePrefix(true);
        return redisCacheManager;
    }
    @Bean
    public RedisCacheManager departmentRedisManager(RedisTemplate departmentRedisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(departmentRedisTemplate);
        redisCacheManager.setUsePrefix(true);
        return redisCacheManager;
    }
}
```
4. 同一实体类使用相同的`CacheManage`和`Cache`组件

```
@CacheConfig(cacheNames = "emp", cacheManager = "employeeRedisCacheManager")
@Repository
public class EmployeeService {

    private static Map<Integer, Employee> map = new HashMap<Integer, Employee>();
    private static int index = 0;

    @Autowired
    private DepartmentService departmentService;

    private Logger logger = LoggerFactory.getLogger(getClass());
 
    static {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            index = 0;
            map.put(index, new Employee(index, "qpf", "qpf@qq.com", "M", DepartmentService.getDepartment(1), dateFormat.parse("1992-05-10")));
            index++;
            map.put(index, new Employee(index, "qpf", "qpf@qq.com", "M", DepartmentService.getDepartment(1), dateFormat.parse("1992-05-10")));
            index++;
            map.put(index, new Employee(index, "qpf", "qpf@qq.com", "M", DepartmentService.getDepartment(1), dateFormat.parse("1992-05-10")));
            index++;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public Collection<Employee> getAll() {

        Collection<Employee> employees = map.values();

        return employees;
    }
    public int save(Employee employee) {
        employee.setId(++index);
        employee.getDepartment().setName(departmentService.getOne(employee.getDepartment().getId()).getName());
        map.put(index, employee);
        return 0;
    }

    @Cacheable(value = "emp", key = "'emp:'+#id")
    public Employee getOne(Integer id) {
        logger.info("getEmp: " + id);
        return map.get(id);
    }
    @CachePut(value = "emp", key="#result.id")
    public Employee editOne(Employee employee) {
        logger.info("editEmp: " + employee);
        employee.getDepartment().setName(departmentService.getOne(employee.getDepartment().getId()).getName());
        map.put(employee.getId(), employee);
        return employee;
    }
    @CacheEvict(value = "emp", key = "#id")
    public int delOne(Integer id) {
        logger.info("delEmp: " + id);
        if (map.containsKey(id)) {
            map.remove(id);
        } else{
            logger.info("该员工不在记录中");
        }
        return 0;
    }
}
```


### 原理

1. 自动配置类`RedisAutoConfiguration`,添加`RedisTemplate`和`StringRedisTemplate`组件
    - `RedisTemplate`保存对象默认使用`jdk`序列化机制将序列化后的数据保存到`Redis`

2. `RedisCacheConfiguration`向容器中添加的`RedisCacheManager`
    -  默认缓存管理器: `RedisCacheManager`
    -  `RedisCacheManager`创建和获取`RedisCache`缓存组件,`RedisCache`通过操作`Redis`缓存数据
    - 自定义`CacheManager`,管理不同实体类的缓存操作,指定不同的序列化器

# 二、 消息队列

## 1 介绍

通过消息中间件可以提升系统异步通信能力和扩展接偶能力.消息服务中有两个重要概念: 消息代理(`message broker`)和目的地(`destination`).

当消息发送者发送消息之后,消息由消息代理接管.消息代理保证消息传递到目的地.

消息目的地有两种形式:
- `Queue`: 队列,点对点消息通信(`point-to-point`),一对一
    - 消息发送者发送消息后,消息代理将消息放入队列中.消息接收者从队列中获取消息,消息读取之后被移出队列
    - 消息只有唯一的发送者和最终接受者,但接收者不唯一
- `topic`: 主题,发布(`publish`)/订阅(`subscribe`)消息通信,一对多
    - 发送者发布消息到主题,多个接收者订阅这个主题,消息到达是同时受到消息

### 1.2 消息代理规范:

- `JMS`(`Java Message Service`): 基于`JVM`消息代理的规范,实现有:`ActiveMQ`和`HornetMQ`
- `AMQP`(`Advanced Message Queuing Protpcol`): 高级消息队列,兼容`JMS`,实现有: `RebbitMQ`


 - | `JMS` | `AMQP`
---|---|---
定义 | `Java API` | 网络线级协
跨语言 | 否 | 是
跨平台 | 否 | 是
Model | `Peer-2Peer`和`Pub/Sub` | `direct exchange`,`fonout exchange`, `topic change`,`headers exchange`,`system exchange`
支持消息类型 | `TextMessage`,`MapMessage`,`BytesMessage`,`StreamMessage`,`ObjecMessage`,`Message`  | `byte[]` 
综合 | `JMS`定义了`Java  API`层面的标准.在`Java`体系中,多个`client`均可通过`JMS`交互,不需要修改应用代码,但跨平台支持较差 | `AMQP`定义了`wire-level`层的协议标准;具有跨平台和跨语言的特性

### 1.3 Spring支持

- `spring-jms`提供了对`JMS`的支持
- `spring-rabbit`提供了对`AMQP`的支持
- `ConnectionFactory`实现连接消息代理
- `JmsTemplate`和`RabbitTemplate`用来发送消息
- `@JmsListener`和`@RabbitListener`注解添加在方法上监听消息代理发送的消息
- `@EnableJms`和`@EnableRabbit`注解开启消息代理支持

### 1.4 自动配置

- `JmsAutoConfiguration`
- `RabbitAutoConfiguration`

## 2 RabbitMQ

`RabbitMQ`是`erlang`开发的`AMQP`的开源实现

### 2.1 核心概念

- `Message`: 消息由消息头和消息体组成.
    - 消息体是不透明的
    - 消息头是有一系列可选属性组成
        - `routing-key`: 路由键
        - `priority`: 相对于其他消息的优先级
        - `delivary-mode`: 消息是否持久性存储
- `Publisher`: 消息生产者,向交换器发布消息的客户端程序
- `Exchange`: 交换器,用来接收生产者发送的消息并将这些消息路由交给服务器中的队列
    - `Exchange`有4种类型:
        - `direct`: 单播(默认).
            - `Exchange`和`Queue`根据路由键关联,消息的分发对应路由键的队列
        - `fanout`: 广播.
            - 消息会分发到每个与交换器绑定的队列.转发消息最快
        - `topic`: 选择性广播.
            - 路由键与消息队列模式匹配,队列模式由路由键和绑定键组成(`key.#  | key.* | #.key | *.key`)
            - `#`: 匹配0到多个单词
            - `*`: 匹配一个单词
        - `headers`
- `Queue`: 消息队列,用来保存消息直到发送给消费者.
    - 消息容器,消息在队列列中等待消费者取出
- `Binding`: 绑定,用于消息队列和交换器之间的关联.
    - 基于路由键将交换器和消息队列连接起来的路由规则,路由表
    - 交换器和消息队列可以是多对多的关系
- `Connection`: 网络连接
- `Channel`: 信道,多路复用连接中的一条独立的双向数据通.
    - 信道是建立在真实`TCP`连接中的虚拟连接
    - `AMQP`命令都是通过信道发送出去的,包括发布消息,订阅消息和接收消息
    - 信道是为了节省系统消耗而复用的一条`TCP`连接
- `Comsumer`: 消息消费者,从消息队列中取出消息的客户端程序
- `Virtual Host`: 虚拟主机,表示一批交换器和消息队列和相关对象.
    - 共享相同身份认证和加密环境的独服务器域
    - 每个`vhost`都是独立的`RabbitMQ`服务器,拥有自己的队列,交换器,绑定和权限机制
    - 连接是需要指定`vhost`,默认为`/`
- `Broker`:消息代理,消息队列服务器主体

### 2.2 安装RabbitMQ

1. 下载`rabbitmq:3-management`
    
    ```
    $ docker pull rabbitmq:3-management
    ```

2. 运行镜像
    - `5672`是客户端连接`RabbitMQ`的通信端口
    - `15672`是管理界面`web`端口
    ```
    $ docker run -d -p 5672:5672 -p 15672:15672 --name rabbitmq01 {image_id}
    ```
3. 登陆管理界面
    - `host:15672`
    - `name: guest, password: guest` 
4. 测试
    - 新建`Exchange`: `amq.direct`,`amq.fanout`和`amq.topic`
    - 新建`Queue`: `queue.direct`, `queue.fanout`和`queue.topic`
    - 绑定`Exchange`和`Queue`:每个`Exchange`绑定所有`Queue`,`routingKey`为`Queue`名
```
graph LR
Exchange-->'routingKey'
'routingKey'-->Queue
```   
```
amq.direct =='queue.direct'==>queue.direct
```
```
graph LR
amq.direct-->'queue.direct'
'queue.direct'-->queue.direct
```
    

```
amq.fanout ==''==>queue.direct, queue.fanout, queue.topic
```
```
graph LR
amq.fanout-->'queue'
'queue'-->queue.direct
'queue'-->queue.fanout
'queue'-->queue.topic
```
```
amq.topic =='queue.#'==> queue.direct, queue.fanout, queue.topic
amq.topic =='queue.direct'==> queue.direct
```

```
graph LR
amq.topic-->'queue.direct'
amq.topic-->'queue.direct.abc'
amq.topic-->'queue'
'queue.direct'-->queue.direct
'queue.direct'-->queue.fanout
'queue.direct'-->queue.topic
'queue.direct.abc'-->queue.fanout
'queue.direct.abc'-->queue.topic
'queue'-->queue.fanout
'queue'-->queue.topic
```

## 3 搭建环境

1. 引入`spring-boot-start-amqp`

2. 配置`RabbitMQ`
    ```
    # 默认host=localhost
    spring.rabbitmq.host=xxx.xxx.xxx.xxx
    spring.rabbitmq.username=guest
    spring.rabbitmq.password=guest
    # 默认port=5673
    spring.rabbitmq.port=5672
    # 默认virtual-host=/
    spring.rabbitmq.virtual-host=/
    ```
3. 测试`RabbitTemplate`
    - `rabbitTemplate.send(exchange, routeKey, message)`: 自定义`Message`对象
        - 获取消息体(`byte[]`),
        - 配置消息头(`MessageProperties`)
    - `rabbitTemplate.convertAndSend(exchange, routeKey, object)`: `object`对象默认自动序列化为消息体
    - `rabbitTemplate.receive(queue)`: 接收消息并自动转化为`Message`对象
    - `rabbitTemplate.receiveAndConvert(queue)`: 接受消息并转化为`Object`
4. 向容器中添加指定`MessageConverter`: `jackson2JsonMessageConverter`

5. 监听队列并调用方法
    - `@RabbitListener(queue = "xxxx.xx")`: 标注监听回调函数
    - `@EnableRabbit`: 主程序开启基于注解的`RabbitMQ`
6. 

### 3.1 自动配置原理

1. `RabbitAutoConfiguretion`
    - `ConnentionFactory`: 根据`RabbitProperties`配置获取`RabbitMQ`连接
    - `RabbitProperties`: 配置`RabbitMQ`
    - `RabbitTemplate`: 发送和接收消息
    - `AmqpAdmin`: `RabbitMQ`: 系统管理功能组件,创建和删除`Exchange`, `Binding`和`Queue`
2. 

# 三 检索 (Elasticsearch)

3. 安装`Elasticsearch`

```
$ docker pull elasticsearch
# Elasticsearch 启动默认占用2G内存,需要限制启动内存
# web通信: 9200
# 分布式节点通信: 9300
$ docker run -e ES_JAVA_OPTS="-Xms=256 -Xmx=256" -d -p 9200:9200 -p 9300:9300 --name es01 {image_id}
```