# SSM

- SpringMVC + Spring  +  MyBatis
- Maven多模块

---

**什么是SSM框架？**  

*SpringMVC:*  
  1. 客户端发送请求到DispacherServlet（分发器)  
  2. 由DispacherServlet控制器查询HanderMapping，找到处理请求的Controller  
  3. Controller调用业务逻辑处理后，返回ModelAndView  
  4. DispacherSerclet查询视图解析器，找到ModelAndView指定的视图  
  5. 视图负责将结果显示到客户端   

![来源于网络](https://img-blog.csdn.net/20180522142940402?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3lvcmlfY2hlbg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

*Spring:*  
  1. Spring是一个轻量级的控制反转（IOC）和面向切面（AOP）的容器架构，IOC容器包含并管理应用对象的配置和生命周期，你可以配置你的每个bean如何被创建，也可以配置每个bean是只有一个实例，还是每次需要时都生成一个新的实例，以及它们是如何相互关联的  
  2. IOC思想最核心的地方在于，资源不由使用资源的双方管理，而由不使用资源的第三方管理，这可以带来很多好处。第一，资源集中管理，实现资源的可配置和易管理。第二，降低了使用资源双方的依赖程度，也就是我们说的耦合度  
  3. 容器提供了AOP技术，利用它很容易实现如权限拦截、运行期监控等功能

*MyBatis:*  
  1.  MyBatis是支持普通SQL查询，存储过程和高级映射的优秀持久层框架。MyBatis消除了几乎所有的JDBC代码和参数的手工设置以及结果集的检索。MyBatis使用简单的XML或注解用于配置和原始映射，将接口和Java的POJOs（Plan Old Java Objects，普通的Java对象）映射成数据库中的记录  
  2.  MyBatis的操作都是围绕一个sqlSessionFactory实例展开的。MyBatis通过配置文件关联到各实体类的Mapper文件，Mapper文件中配置了每个类对数据库所需进行的sql语句映射。在每次与数据库交互时，通过sqlSessionFactory拿到一个sqlSession，再执行sql命令  

**为什么使用Maven多模块？**  

  1.  JavaEE项目开发中为了便于后期的维护，一般会进行分层开发，分层之后，各个层之间的职责会比较明确，后期维护起来也相对比较容易

## 项目结构

```
.
└── ssm-parent
	├── pom.xml #父POM [Spring、Servlet等依赖]
	├── ssm-dao #数据库访问层
	│   ├── pom.xml #描述工程资源的目录,编译打包 mapper.xml
	│   └── src
	│       └── main
	│           └── java
	│               └── com #具体代码
	├── ssm-domain #域模型层
	│   ├── pom.xml #常用工具类依赖
	│   └── src
	│       └── main
	│           └── java
	│               └── com #具体代码
	├── ssm-service #业务逻辑层
	│   ├── pom.xml #JDBC、MyBatis依赖
	│   └── src
	│       └── main
	│           ├── java
	│           │   └── com #具体代码
	│           └── resources
	│               └── applicationContext-service.xml #扫描注解配置
	└── ssm-web #表现层
	    ├── pom.xml #定义一些常量 [jdk version]
	    └── src
	        └── main
	            ├── java
	            │   └── com
	            ├── resources
	            │   ├── applicationContext-aop.xml #事务配置文件
	            │   ├── applicationContext.properties #配置文件
	            │   ├── applicationContext-thread.xml #线程池配置文件
	            │   ├── applicationContext-web.xml #HTTP相关配置文件
	            │   ├── applicationContext.xml #Spring配置文件
	            │   ├── dataSource.xml #连接池数据源配置文件
	            │   ├── log4j.properties #日志配置文件
	            │   └── mybatis-config.xml #Mybatis配置文件
	            └── webapp #Web静态资源文件
	                ├── index.jsp
	                ├── js
	                ├── view
	                └── WEB-INF #Web应用程序配置文件
```
---

## 创建Maven多模块项目

### 一、创建ssm-parent项目

创建 *ssm-parent*，用来给各个子模块继承

使用 `cmd` 或者 `Terminal`，进入命令行，输入以下命令:

```
mvn archetype:generate -DgroupId=com.ryan -DartifactId=ssm-parent -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

命令执行完成之后可以看到在当前目录生成了*ssm-parent*目录，里面有一个*src*目录和一个 *pom.xml*文件，如下图所示：

```
.
└── ssm-parent
    ├── pom.xml
    └── src
```

将*src*文件夹删除，然后修改*pom.xml*文件，将`<packaging>jar</packaging>`修改为`<packaging>pom</packaging>`，**pom**表示它是一个被继承的模块，修改后的内容如下：

``` xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.ryan</groupId>
  <artifactId>ssm-parent</artifactId>
  <packaging>pom</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>ssm-parent</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
</project>
```

**注：**
- archetype：maven工程的模板工具包
- mvn archetype:generate：创建项目(在maven 3.02以下的版本,使用archetype:create 命令创建)
- DgroupId：组织ID
- DartifactId：项目ID
- DarchetypeArtifactId：工程类型
- DinteractiveMode：是否已交互模式进行，如果是false的话就会采用默认设置建立项目


### 二、创建ssm-domain模块

命令行进入创建好的*ssm-parent*目录，然后执行下列命令：

```
mvn archetype:generate -DgroupId=com.ryan -DartifactId=ssm-domain -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

命令执行完成之后可以看到在*ssm-parent*目录中生成了*ssm-domain*，里面包含*src*目录和*pom.xml*文件。如下图所示：

```
ssm-domain
├── pom.xml
└── src
```

修改*ssm-domain*目录中的*pom.xml*文件，把`<groupId>com.ryan</groupId>`和`<version>1.0-SNAPSHOT</version>`去掉，加上`<packaging>jar</packaging>`，因为*groupId*和*version*会继承*ssm-parent*中的*groupId*和*version*，*packaging*设置打包方式为**jar**，修改过后的*pom.xml*文件如下：

``` xml
<?xml version="1.0"?>
<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>com.ryan</groupId>
    <artifactId>ssm-parent</artifactId>
    <version>1.0-SNAPSHOT</version>
  </parent>
  <artifactId>ssm-domain</artifactId>
  <packaging>jar</packaging>
  <name>ssm-domain</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
</project>
```

### 三、创建ssm-dao模块
	
命令行进入创建好的*ssm-parent*目录，然后执行下列命令：

```
mvn archetype:generate -DgroupId=com.ryan -DartifactId=ssm-dao -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

修改*ssm-dao*目录中的*pom.xml*文件，把`<groupId>com.ryan</groupId>`和`<version>1.0-SNAPSHOT</version>`去掉，加上`<packaging>jar</packaging>`，因为*groupId*和*version*会继承*ssm-parent*中的*groupId*和*version*，*packaging*设置打包方式为**jar**，同时添加对*ssm-domain*模块的依赖，修改后的内容如下：

``` xml
<?xml version="1.0"?>
<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>com.ryan</groupId>
    <artifactId>ssm-parent</artifactId>
    <version>1.0-SNAPSHOT</version>
  </parent>
  <artifactId>ssm-dao</artifactId>
  <packaging>jar</packaging>
  <name>ssm-dao</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <dependency>
      <groupId>com.ryan</groupId>
      <artifactId>ssm-domain</artifactId>
      <version>${project.version}</version>
    </dependency>
  </dependencies>
</project>
```

### 四、创建ssm-service模块

命令行进入创建好的*ssm-parent*目录，然后执行下列命令：

```
mvn archetype:generate -DgroupId=com.ryan -DartifactId=ssm-service -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

修改*ssm-service*目录中的*pom.xml*文件，把`<groupId>com.ryan</groupId>`和`<version>1.0-SNAPSHOT</version>`去掉，加上`<packaging>jar</packaging>`，因为*groupId*和*version*会继承*ssm-parent*中的*groupId*和*version*，*packaging*设置打包方式为**jar**，同时添加对*ssm-dao*模块的依赖，*ssm-service*依赖*ssm-dao*和*ssm-domain*，但是我们只需添加*ssm-dao*的依赖即可，因为*ssm-dao*已经依赖了*ssm-domain*。修改后的内容如下：

``` xml
<?xml version="1.0"?>
<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>com.ryan</groupId>
    <artifactId>ssm-parent</artifactId>
    <version>1.0-SNAPSHOT</version>
  </parent>
  <artifactId>ssm-service</artifactId>
  <packaging>jar</packaging>
  <name>ssm-service</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <dependency>
      <groupId>com.ryan</groupId>
      <artifactId>ssm-dao</artifactId>
      <version>${project.version}</version>
    </dependency>
  </dependencies>
</project>
```

### 五、创建ssm-web模块

命令行进入创建好的*ssm-parent*目录，然后执行下列命令：

```
mvn archetype:generate -DgroupId=com.ryan -DartifactId=ssm-web -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false
```

命令执行完成之后可以看到在*ssm-parent*目录中生成了*ssm-web*，里面包含*src*目录和*pom.xml*文件。如下图所示：

```
ssm-web
├── pom.xml
└── src
    └── main
        ├── resources
        └── webapp
            ├── index.jsp
            └── WEB-INF
                └── web.xml
```

修改*ssm-web*目录中的*pom.xml*文件，把`<groupId>com.ryan</groupId>`和`<version>1.0-SNAPSHOT</version>`去掉，因为*groupId*和*version*会继承*ssm-parent*中的*groupId*和*version*,*packaging*设置打包方式为**war**，同时添加对*ssm-service*模块的依赖，修改后的内容如下：

``` xml
<?xml version="1.0"?>
<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>com.ryan</groupId>
    <artifactId>ssm-parent</artifactId>
    <version>1.0-SNAPSHOT</version>
  </parent>
  <artifactId>ssm-web</artifactId>
  <packaging>war</packaging>
  <name>ssm-web Maven Webapp</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <dependency>
      <groupId>com.ryan</groupId>
      <artifactId>ssm-service</artifactId>
      <version>${project.version}</version>
    </dependency>
  </dependencies>
  <build>
    <finalName>ssm-web</finalName>
  </build>
</project>
```

至此，Maven多模块项目已经搭建完成！

## SSM框架整合配置

我们使用的是maven来管理的jar，所以只需要在pom.xml中加入相应的依赖，就能导入需要的jar包。这里把项目用到的jar贴在下面，可以自己调整版本号。因为是多模块项目，每个子模块下面都有pom.xml，这里只举例ssm-parent及ssm-service下的pom.xml，当然所有模块的依赖也可以都放在ssm-parent下的pom.xml(即父项目的pom.xml)，可按照需要自行调整

**ssm-parent pom.xml**

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.ryan</groupId>
  <artifactId>ssm-parent</artifactId>
  <packaging>pom</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>ssm-parent</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <!-- spring webmvc -->
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>4.3.10.RELEASE</version>
    </dependency>
    <!-- servlet api -->
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>servlet-api</artifactId>
      <version>2.5</version>
      <scope>provided</scope>
    </dependency>
    <!-- jstl -->
    <!-- https://mvnrepository.com/artifact/javax.servlet/jstl -->
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>jstl</artifactId>
      <version>1.2</version>
    </dependency>
    <!-- junit -->
    <!-- https://mvnrepository.com/artifact/junit/junit -->
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
    </dependency>
    <!-- log4j -->
    <!-- https://mvnrepository.com/artifact/log4j/log4j -->
    <dependency>
      <groupId>log4j</groupId>
      <artifactId>log4j</artifactId>
      <version>1.2.17</version>
    </dependency>
    <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-slf4j-impl</artifactId>
      <version>2.6.2</version>
    </dependency>
    <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-jcl</artifactId>
      <version>2.6.2</version>
    </dependency>
    <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-web</artifactId>
      <version>2.6.2</version>
    </dependency>
    <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-core</artifactId>
      <version>2.6.2</version>
    </dependency>
    <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-api</artifactId>
      <version>2.6.2</version>
    </dependency>
    <!-- fileupload -->
    <!-- https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload -->
    <dependency>
      <groupId>commons-fileupload</groupId>
      <artifactId>commons-fileupload</artifactId>
      <version>1.3.1</version>
    </dependency>
  </dependencies>
  <modules>
    <module>ssm-domain</module>
    <module>ssm-dao</module>
    <module>ssm-service</module>
    <module>ssm-web</module>
  </modules>
  <build>
    <!-- jdk version -->
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.1</version>
        <configuration>
          <source>1.8</source>
          <target>1.8</target>
          <encoding>UTF-8</encoding>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```

**ssm-service pom.xml**

```
<?xml version="1.0"?>
<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>com.ryan</groupId>
    <artifactId>ssm-parent</artifactId>
    <version>1.0-SNAPSHOT</version>
  </parent>
  <artifactId>ssm-service</artifactId>
  <packaging>jar</packaging>
  <name>ssm-service</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <!-- spring jdbc -->
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>4.3.10.RELEASE</version>
    </dependency>
    <!-- spring aspect -->
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-aspects -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aspects</artifactId>
      <version>4.3.10.RELEASE</version>
    </dependency>
    <!-- mybatis -->
    <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.2.6</version>
    </dependency>
    <!-- mybatis spring -->
    <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis-spring</artifactId>
      <version>1.2.2</version>
    </dependency>
    <!-- mysql -->
    <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.18</version>
    </dependency>
    <!-- druid -->
    <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>1.0.29</version>
    </dependency>
    <dependency>
      <groupId>com.ryan</groupId>
      <artifactId>ssm-dao</artifactId>
      <version>${project.version}</version>
    </dependency>
  </dependencies>
</project>
```

下面开始进行编写框架所需配置文件

**参数配置文件**

通常我们把数据库配置、redis等相关参数放到配置文件中，所以在ssm-web模块中的resources文件夹里新建applicationContext.properties文件，存放项目相关参数配置

applicationContext.properties

```
#db
#jdbc.driver=oracle.jdbc.driver.OracleDriver
jdbc.driver=com.mysql.jdbc.Driver
#jdbc.url=jdbc:oracle:thin:@172.18.100.223:1521:duvet
jdbc.url=jdbc:mysql://116.62.22.2:3306/student?useUnicode=true&amp;characterEncoding=utf8
jdbc.username=student
jdbc.password=Password@123456

pool.maxPoolSize=10
pool.removeAbandonedTimeout=180000
pool.maxWait=10000
pool.timeBetweenEvictionRunsMillis=60000
pool.minEvictableIdleTimeMillis=300000
pool.validationQuery=select 1
```

**注：**

- 配置中的数据库信息为测试库，只有select权限

**连接池数据源配置文件**

在ssm-web模块中的resources文件夹里新建dataSource.xml文件，配置连接池数据源

- 数据库连接信息
- 配置数据库连接池
- 声明式事务
- 配置SqlSessionFactory对象
- 扫描dao层接口，动态实现dao接口

dataSource.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:tx="http://www.springframework.org/schema/tx"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/tx
		http://www.springframework.org/schema/tx/spring-tx-4.0.xsd">

	<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
		<property name="url" value="${jdbc.url}" />
		<property name="driverClassName" value="${jdbc.driver}" />
		<property name="maxActive" value="${pool.maxPoolSize}" />
		<property name="username" value="${jdbc.username}" />
		<property name="password" value="${jdbc.password}" />
		<!-- 超过时间限制是否回收 -->
		<property name="removeAbandoned" value="true" />
		<!-- 超时时间；单位为秒。180秒=3分钟 -->
		<property name="removeAbandonedTimeout" value="${pool.removeAbandonedTimeout}" />
		<!-- 配置获取连接等待超时的时间 -->
		<property name="maxWait" value="${pool.maxWait}" />
		<!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
		<property name="timeBetweenEvictionRunsMillis" value="${pool.timeBetweenEvictionRunsMillis}" />
		<!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
		<property name="minEvictableIdleTimeMillis" value="${pool.minEvictableIdleTimeMillis}" />
		<property name="validationQuery" value="${pool.validationQuery} " />
		<property name="testWhileIdle" value="true" />
		<property name="testOnBorrow" value="false" />
		<property name="testOnReturn" value="false" />
	</bean>
	
	
	<!-- 事务 控制 begin -->
	<bean name="transactionManager"
		  class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"></property>
	</bean>
	<tx:annotation-driven transaction-manager="transactionManager" proxy-target-class="true" order="10"/>
	<!-- 事务 控制 end -->

	<!-- 自动扫描mapping文件 start -->
	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="dataSource" ref="dataSource" />
		<property name="configLocation" value="classpath:mybatis-config.xml" />
		<property name="mapperLocations">
			<list>
				<!-- 表示在com/ryan/mapper包或以下所有目录中，以-Mapper.xml结尾所有文件 -->
				<value>classpath*:com/ryan/mapper/**/*Mapper.xml</value>
			</list>
		</property>
		<!--<property name="typeAliasesSuperType" value="com.ryan.framework.entity.BaseEntity" />-->
		<property name="configurationProperties">
			<props>
				<!--<prop key="dialect">oracle</prop>-->
 				<prop key="dialect">mysql</prop>
			</props>
		</property>
		<property name="plugins">
			<list>
				<!--<bean class="com.ryan.framework.mybatis.SqlInterceptor" />-->
			</list>
		</property>
	</bean>
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<property name="basePackage" value="com.ryan.dao" />
		<property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
	</bean>
	<!-- 自动扫描mapping文件 end -->
</beans>
```

**MyBatis配置文件**

项目使用MyBatis持久层框架，所以需要配置MyBatis核心文件，在ssm-web模块中recources文件夹里新建mybatis-config.xml文件

- 指定日志格式
- 是否开启自动驼峰命名规则
- 延迟加载
- 按需加载对象
- 分页插件

mybatis-config.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
 <settings>
  <!-- 指定 MyBatis 所用日志的具体实现 -->
  <setting name="logImpl" value="LOG4J" />
  <!-- 是否开启自动驼峰命名规则 -->
  <setting name="mapUnderscoreToCamelCase" value="true" />
  <!-- 延迟加载的全局开关 -->
  <setting name="lazyLoadingEnabled" value="true"/>
  <!-- 按需加载对象 -->
  <setting name="aggressiveLazyLoading" value="false"/>
 </settings>

 <!-- 配置分页插件 -->
 <plugins>
  <plugin interceptor="com.github.pagehelper.PageHelper">
   <!-- 设置数据库类型 Oracle,Mysql,MariaDB,SQLite,Hsqldb,PostgreSQL六种数据库-->
   <property name="dialect" value="mysql"/>
   <!--<property name="dialect" value="oracle"/>-->
  </plugin>
 </plugins>
</configuration>
```

**业务集成配置**

处理业务相关，在ssm-service模块的recources文件夹里新建applicationContext-service.xml文件

- 扫描注解

applicationContext-service.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:context="http://www.springframework.org/schema/context"
	   xmlns:aop="http://www.springframework.org/schema/aop"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context-4.0.xsd
		 http://www.springframework.org/schema/aop
		 http://www.springframework.org/schema/aop/spring-aop-4.0.xsd">

	<aop:config />
	<!-- enable @AspectJ support with XML based configuration -->
	<aop:aspectj-autoproxy />
	<context:annotation-config  />
	<!-- 启动包扫描功能，以便注册带有@Controller、@Service、@repository、@Component等注解的类成为spring的bean -->
	<context:component-scan base-package="com.ryan" />
</beans>
```

**Web应用程序配置文件**

- 开启SpringMVC注解模式
- 视图解析器

rest-servlet.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xmlns:mvc="http://www.springframework.org/schema/mvc"
    xsi:schemaLocation="
	http://www.springframework.org/schema/beans
	http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
	http://www.springframework.org/schema/mvc   
	http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd"
>
	<mvc:annotation-driven/>

	<bean id="contentNegotiationManager"  class="org.springframework.web.accept.ContentNegotiationManagerFactoryBean">
		<property name="mediaTypes">
			<map>
				<entry key="xml" value="application/xml"/>
				<entry key="html" value="text/html"/>
			</map>
		</property>
	</bean>

	<bean class="org.springframework.web.servlet.view.ContentNegotiatingViewResolver">
		<property name="contentNegotiationManager" ref="contentNegotiationManager"/>
		<property name="viewResolvers">
			<list>
				<bean class="org.springframework.web.servlet.view.BeanNameViewResolver"/>
				<bean id="viewResolver" class="org.springframework.web.servlet.view.UrlBasedViewResolver">
					<property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
					<property name="prefix" value="/view/"/>
					<property name="suffix" value=".jsp"/>
				</bean>
			</list>
		</property>
	</bean>
</beans>
```

web.xml

- 字符编码过滤器
- 配置Spring需要加载的配置文件
- 配置DispatcherServlet分发器

```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://java.sun.com/xml/ns/javaee"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" version="3.0">
  <!--filter -->
  <filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
      <param-name>forceRequestEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
    <init-param>
      <param-name>forceResponseEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>characterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
  <filter>
    <filter-name>contextfilter</filter-name>
    <filter-class>com.ryan.filter.WebContextFilter</filter-class>
  </filter>
  <filter-mapping>
    <filter-name>contextfilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>

  <!--mode1 start-->
  <!-- spring -->
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
  </context-param>
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  <!-- spring mvc -->
  <servlet>
    <servlet-name>rest</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>rest</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
  <!--mode1 end-->

  <!--mode2 start-->
  <!--<servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:applicationContext.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>dispatcherServlet</servlet-name>
    <url-pattern>/*</url-pattern>
  </servlet-mapping>-->
  <!--mode2 end-->

  <display-name>ssm-parent</display-name>
</web-app>
```
**其他配置**

项目中还配置了日志、事务、线程池及Spring跳转配置，可根据实际情况斟酌增删配置

## 数据库脚本

这里贴上demo的数据库，并为student表初始化一些数据

student.sql

``` sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_student_info
-- ----------------------------
DROP TABLE IF EXISTS `t_student_info`;
CREATE TABLE `t_student_info`  (
  `stu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `stu_number` int(11) NULL DEFAULT NULL COMMENT '学号',
  `stu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `stu_gender` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `stu_age` int(3) NULL DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (`stu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10004 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_student_info
-- ----------------------------
INSERT INTO `t_student_info` VALUES (10001, 95001, '张三', '男', 20);
INSERT INTO `t_student_info` VALUES (10002, 95002, '李四', '男', 21);
INSERT INTO `t_student_info` VALUES (10003, 95003, '王五', '女', 22);

SET FOREIGN_KEY_CHECKS = 1;
```

看到这里，其实我们的ssm框架与maven多模块的整合配置已经完成了，demo里也已经实现了简单的增删改查，只不过缺少页面而已，后台处理的数据返回到页面上是一串json,不过这些已经足够展示了，需要的同学可以去下载，感觉还不错就给个star吧！
