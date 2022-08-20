# Spring Retry

### 1.Overview

Spring Retry는 실패한 작업을 자동으로 다시 호출하는 기능을 제공합니다. 이는 오류가 일시적일 수 있는 경우에 유용합니다(예: 일시적인 네트워크 결함).

이 튜토리얼에서는 Spring Retry를 사용하는 다양한 방법(주석, RetryTemplate 및 콜백)을 볼 것입니다.

### ****2. Maven Dependencies****

```java
<dependency>
    <groupId>org.springframework.retry</groupId>
    <artifactId>spring-retry</artifactId>
    <version>1.2.5.RELEASE</version>
</dependency>

// spring aop
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aspects</artifactId>
    <version>5.2.8.RELEASE</version>
</dependency>
```

### ****3. Enabling Spring Retry****

```java
@Configuration
@EnableRetry
public class AppConfig { ... }
```

### ****4. Using Spring Retry****

### ****4.1. *@Retryable* Without Recovery**

@Retryable 주석을 사용하여 메서드에 재시도 기능을 추가할 수 있습니다.

```java
@Service
public interface MyService {
    @Retryable(value = RuntimeException.class)
    void retryService(String sql);

}
```

여기에서 RuntimeException이 발생하면 재시도를 시도합니다.

@Retryable의 기본 동작에 따라 재시도는 최대 3번까지 발생할 수 있으며 재시도 사이에는 1초의 지연이 있습니다.

### ****4.2. *@Retryable* and *@Recover***

이제 @Recover annotation을 사용하여 복구 방법을 추가해 보겠습니다.

```java
@Service
public interface MyService {
    @Retryable(value = SQLException.class)
    void retryServiceWithRecovery(String sql) throws SQLException;
        
    @Recover
    void recover(SQLException e, String sql);
}
```

여기에서 SQLException이 발생하면 재시도를 시도합니다. @Recover 어노테이션은 @Retryable 메소드가 지정된 예외로 실패할 때 별도의 복구 메소드를 정의합니다.

결과적으로 retryServiceWithRecovery 메서드가 세 번 시도한 후에도 SqlException을 계속 발생시키면 recover() 메서드가 호출됩니다.

복구 핸들러에는 Throwable(optional) 유형의 첫 번째 매개변수와 동일한 반환 유형이 있어야 합니다. 다음 인수는 실패한 메소드의 인수 목록에서 동일한 순서로 채워집니다.

### ****4.3. Customizing *@Retryable's* Behavior**

재시도 동작을 사용자 정의하기 위해 maxAttempts 및 backoff 매개변수를 사용할 수 있습니다.

```java
@Service
public interface MyService {
    @Retryable( value = SQLException.class, 
// 최대 2회의 시도와 100밀리초의 지연이 있습니다.
      maxAttempts = 2, backoff = @Backoff(delay = 100))
    void retryServiceWithCustomization(String sql) throws SQLException;
}
```

### ****4.4. Using Spring Properties****

@Retryable 주석에서 속성을 사용할 수도 있습니다.

이를 시연하기 위해 delay 및 maxAttempts 값을 속성 파일로 외부화하는 방법을 살펴보겠습니다.

먼저 retryConfig.properties라는 파일에서 속성을 정의해 보겠습니다.

```java
retry.maxAttempts=2
retry.maxDelay=100
```

그런 다음 @Configuration 클래스에 이 파일을 로드하도록 지시합니다.

```java
@PropertySource("classpath:retryConfig.properties")
public class AppConfig { ... }
```

마지막으로 @Retryable 정의에 retry.maxAttempts 및 retry.maxDelay 값을 삽입할 수 있습니다.

```java
@Service 
public interface MyService { 
  @Retryable( value = SQLException.class, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}")) 
  void retryServiceWithExternalConfiguration(String sql) throws SQLException; 
}
```

현재 maxAttempts 및 delay 대신 maxAttemptsExpression 및 delayExpression을 사용하고 있습니다.

### ****5. *RetryTemplate***

### ****5.1. *RetryOperations***

Spring Retry는 일련의 execute() 메서드를 제공하는 RetryOperations 인터페이스를 제공합니다.

```java
public interface RetryOperations {
    <T> T execute(RetryCallback<T> retryCallback) throws Exception;

    ...
}

public interface RetryCallback<T> {
    T doWithRetry(RetryContext context) throws Throwable;
}
```

execute()의 매개변수인 RetryCallback은 실패 시 재시도해야 하는 비즈니스 로직을 삽입할 수 있는 인터페이스이다.

### ****5.2. *RetryTemplate* Configuration**

RetryTemplate은 RetryOperations의 구현입니다.

@Configuration 클래스에서 RetryTemplate 빈을 구성합시다.

```java
@Configuration
public class AppConfig {
    //...
    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
		
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(2000l);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(2);
        retryTemplate.setRetryPolicy(retryPolicy);
		
        return retryTemplate;
    }
}
```

RetryPolicy는 작업을 재시도해야 하는 시기를 결정합니다.

SimpleRetryPolicy는 고정된 횟수만큼 재시도하는 데 사용됩니다. 반면에 BackOffPolicy는 재시도 사이의 backoff를 제어하는 데 사용됩니다.

마지막으로 FixedBackOffPolicy는 계속하기 전에 고정된 기간 동안 일시 중지됩니다.

### ****5.3. Using the *RetryTemplate***

재시도 처리로 코드를 실행하려면 retryTemplate.execute() 메서드를 호출할 수 있습니다.

```java
retryTemplate.execute(new RetryCallback<Void, RuntimeException>() {
    @Override
    public Void doWithRetry(RetryContext arg0) {
        myService.templateRetryService();
        ...
    }
});

//익명 클래스 대신 람다 식을 사용할 수 있습니다.
retryTemplate.execute(arg0 -> {
    myService.templateRetryService();
    return null;
});
```

### ****6. Listeners****

리스너는 재시도 시 추가 콜백을 제공합니다. 그리고 이를 다양한 재시도에 걸쳐 다양한 교차 문제에 사용할 수 있습니다.

### ****6.1. Adding Callbacks****

콜백은 RetryListener 인터페이스에서 제공됩니다.

```java
public class DefaultListenerSupport extends RetryListenerSupport {
    @Override
    public <T, E extends Throwable> void close(RetryContext context,
      RetryCallback<T, E> callback, Throwable throwable) {
        logger.info("onClose);
        ...
        super.close(context, callback, throwable);
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context,
      RetryCallback<T, E> callback, Throwable throwable) {
        logger.info("onError"); 
        ...
        super.onError(context, callback, throwable);
    }

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context,
      RetryCallback<T, E> callback) {
        logger.info("onOpen);
        ...
        return super.open(context, callback);
    }
}
```

open 및 close 콜백은 전체 재시도 전후에 발생하는 반면 onError는 개별 RetryCallback 호출에 적용됩니다.

### ****6.2. Registering the Listener****

다음으로 RetryTemplate 빈에 리스너(DefaultListenerSupport)를 등록합니다.

```java
@Configuration
public class AppConfig {
    ...

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        ...
        retryTemplate.registerListener(new DefaultListenerSupport());
        return retryTemplate;
    }
}
```

### ****7. Testing the Results****

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
  classes = AppConfig.class,
  loader = AnnotationConfigContextLoader.class)
public class SpringRetryIntegrationTest {

    @Autowired
    private MyService myService;

    @Autowired
    private RetryTemplate retryTemplate;

    @Test(expected = RuntimeException.class)
    public void givenTemplateRetryService_whenCallWithException_thenRetry() {
        retryTemplate.execute(arg0 -> {
            myService.templateRetryService();
            return null;
        });
    }
}
```