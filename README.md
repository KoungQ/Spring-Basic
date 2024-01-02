# Inflearn: 스프링 핵심 원리 - 기본편

<br><br>

> ## 목차 
>
 >1. [객체 지향 설계와 스프링](#chapter-1-객체-지향-설계와-스프링)
   >2. [스프링 핵심 원리 이해 1 - 예제 만들기](#chapter-2-스프링-핵심-원리-이해-1---예제-만들기)
 >3. [스프링 핵심 원리 이해 2 - 객체 지향 원리 적용](#chapter-3-스프링-핵심-원리-이해-2---객체-지향-원리-적용)
   >4. [스프링 컨테이너와 스프링 빈](#chapter-4-스프링-컨테이너와-스프링-빈)
 >5. [싱글톤 컨테이너](#chapter-5-싱글톤-컨테이너)
   >6. [컴포넌트 스캔](#chapter-6-컴포넌트-스캔)
 >7. [의존 관계 자동 주입](#chapter-7-의존-관계-자동-주입)
   >8. [빈 생명주기 콜백](#chapter-8-빈-생명주기-콜백)
 >9. [빈 스코프](#chapter-9-빈-스코프)

<br><br>

# Chapter 1. 객체 지향 설계와 스프링


**좋은 객체 지향 설계의 5가지 원칙 (SOLID)**

&emsp; **SRP : 단일 책임 원칙 (Single Responsibility Principle)**

&emsp; &emsp; 한 클래스는 하나의 책임만 가져야 한다.

&emsp; &emsp; 변경이 있을 때 파급효과가 적으면 단일 책임 원칙을 잘 따른 것

&emsp; **OCP : 개방-폐쇠 원칙 (Open/Closed Principle)**

&emsp; &emsp; 소프트웨어 요소는 확장에는 열려있으나 변경에는 닫혀있어야 한다.
 
&emsp; &emsp; 다형성 활용 → 인터페이스를 구현한 새로운 클래스를 하나 만들어서 새로운 기능을 구현

&emsp; &emsp; &emsp; → 구현 객체를 변경하려면 클라이언트 코드를 변경해야 한다 → OCP 위반

&emsp; &emsp; &emsp; → 인터페이스에 의존하지만 구현 클래스도 동시에 의존한다 → DIP 위반

&emsp; &emsp; 따라서 다형성 만으로는 OCP, DIP를 지킬 수 없음
 
&emsp; &emsp; 하지만 스프링을 활용한다면 DI(Dependency Injection), DI 컨테이너의 제공으로 다형성 + OCP, DIP를 가능하도록 지원한다.

&emsp; &emsp; &emsp; → 클라이언트 코드의 변경 없이 기능 확장

&emsp; &emsp; &emsp; → 쉽게 부품을 교체하듯 개발

&emsp; **LSP : 리스코프 치환 원칙 (Liskov Substitution Principle)**

&emsp; &emsp; 프로그램 객체는 프로그램의 정확성을 깨뜨리지 않으면서 하위 타입의 인스턴스로 바꿀 수 있어야 한다.
 
&emsp; &emsp; 단순히 컴파일에 성공하는 것을 넘어서는 이야기

&emsp; **ISP : 인터페이스 분리 원칙 (Interface Segregation Principle)**

&emsp; &emsp; 특정 클라이언트를 위한 인터페이스 여러 개가 범용 인터페이스 하나보다 낫다.

&emsp; **DIP : 의존관계 역전 원칙 (Dependency Inversion Principle)**

&emsp; &emsp; 프로그래머는 추상화에 의존해야지, 구체화에 의존하면 안된다.

&emsp; &emsp; 쉽게 말해 구현 클래스에 의존하지 말고, 인터페이스에 의존하라는 뜻

<br>

# Chapter 2. 스프링 핵심 원리 이해 1 - 예제 만들기

![Untitled](https://github.com/SpringArchive/Spring-Basic/assets/96408601/74f16632-1a79-444b-ad28-5ea26bef115b)

![Untitled](https://github.com/SpringArchive/Spring-Basic/assets/96408601/8a66aa6c-4fe9-4646-b269-f85a11e7bc0b)

![Untitled](https://github.com/SpringArchive/Spring-Basic/assets/96408601/6866fb21-0d0a-4b46-a957-b2cf900cb86f)

---

![Untitled](https://github.com/SpringArchive/Spring-Basic/assets/96408601/6f02c054-cbde-4048-bc8c-9e3d79c549a6)

![Untitled](https://github.com/SpringArchive/Spring-Basic/assets/96408601/0d90f3e7-e4d2-4eb4-a227-e9c218127117)

![Untitled](https://github.com/SpringArchive/Spring-Basic/assets/96408601/2eff66f9-9b3e-4d27-99c2-ad6ead271438)


<br>

# Chapter 3. 스프링 핵심 원리 이해 2 - 객체 지향 원리 적용


&emsp; **제어의 역전 IoC (Inversion Of Control)**

&emsp; &emsp; 기존 프로그램은 클라이언트 구현 객체가 스스로 필요한 서버 구현 객체를 생성, 연결, 실행했다.

&emsp; &emsp; 하지만 AppConfig가 등장한 이후에 구현 객체는 자신의 로직을 실행하는 역할만 담당하고 프로그램의 흐름은 AppConfig가 제어한다.

&emsp; &emsp; 이렇듯 **프로그램의 제어 흐름을 구현 객체가 직접 제어하는 것이 아니라 외부(AppConfig)에서 관리**하는 것을 제어의 역전(IoC)라고 한다.

&emsp; **의존 관계 주입 DI (Dependency Injection)**

&emsp; &emsp; DI는 외부에서 객체 간의 관계(의존성)를 결정해주는데 즉, 객체를 직접 생성하는 것이 아니라 외부에서 생성 후 주입시켜 주는 방식이라 할 수 있다. 이를 통해 객체 간의 관계를 동적으로 주입하여 유연성을 확보하고 결합도를 낮출 수 있다.

&emsp; DI 방법 3가지

&emsp; &emsp; 1. **Construct Injection (생성자 주입)**

&emsp; &emsp; &emsp; 현재 가장 권장되는 의존 관계 주입 방식이다.

&emsp; &emsp; &emsp;    오직 생성자 주입만이 **final 키워드를 사용**할 수 있고, 이를 사용하기에 값이 한번 할당 되면 변경할 수 없기에 **객체의 불변성**이 보장된다

&emsp; &emsp; &emsp;    초기에 할당되기에 NullPointerException이 절대 발생하지 않는다.

&emsp; &emsp; 2. **Field Injection (필드 주입)**

&emsp; &emsp; &emsp;    Bean으로 등록된 객체를 사용하고자 하는 클래스에 Field로 선언한 뒤 @Autowired 어노테이션만 붙여주면 자동으로 의존 관계가 주입된다. (세가지 방식 중 가장 간단한 방법)

&emsp; &emsp; &emsp;    생성자 주입을 뺀 나머지(필드 주입, Setter 주입)은 모두 생성자 이후에 호출되므로, 필드에 final 키워드를 사용할 수 없다.

&emsp; &emsp; 3. **Setter Injection (Setter 주입)**

&emsp; &emsp; &emsp;    Spring에서 @Autowired 어노테이션을 사용하여 Setter 메서드를 통해 주입하는 방법

&emsp; **Ioc 컨테이너, DI 컨테이너**

&emsp; &emsp; AppConfig처럼 객체를 생성하고 관리하면서 의존 관계를 연결해 주는 것을 Ioc 컨테이너, DI 컨테이너라고 부른다

<br>

# Chapter 4. 스프링 컨테이너와 스프링 빈



![Untitled 6](https://github.com/SpringArchive/Spring-Basic/assets/96408601/01c680ff-87f5-4ff9-bc33-8a5bc5048490)

&emsp; **Bean Factory**

&emsp; &emsp; - 스프링 컨테이너의 최상위 인터페이스이다.

&emsp; &emsp; - 스프링 빈을 관리하고 조회하는 역할을 담당한다.

&emsp; &emsp; - `getBean()`을 제공한다.

&emsp; &emsp; - 지금까지 우리가 사용했던 대부분의 기능은 BeanFactory가 제공하는 기능이다.

&emsp; **ApplicationContext**

&emsp; &emsp; - BeanFactory 기능을 모두 상속받아서 제공

&emsp; &emsp; - 빈을 관리하고 검색하는 기능을 BeanFactory가 제공해주는데 그 외의 필요한 부가 기능들은 ApplicationContext가 제공한다.

&emsp; &emsp; - ApplicationContext가 제공하는 부가 기능

&emsp; &emsp; &emsp;     1. 메세지 소스를 활용한 국제화 기능

&emsp; &emsp; &emsp;     2. 환경변수

&emsp; &emsp; &emsp;     3. 애플리케이션 이벤트

&emsp; &emsp; &emsp;     4. 편리한 리소스 조회

<br>

# Chapter 5. 싱글톤 컨테이너



만약 내가 만든 웹 어플리케이션이 싱글톤이 아닌 형태로 구성되어 있다면,

```java
public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        // 1. 조회: 호출할 때마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        // 참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService1 != memberService2
        assertThat(memberService1).isNotSameAs(memberService2);
    }
}
```

트래픽이 초 당 100회 발생한다면 AppConfig를 요청할 때마다 100개의 memberService 객체를 생성하고 삭제될 것이다.

이는 TPS가 증가함에 따라 메모리 낭비가 매우 심해지는데, 이를 방지할 수 있는 디자인 패턴을 **싱글톤 패턴**이라 부른다.

싱글톤 패턴이란, **클래스(객체)의 인스턴스가 오직 한 개만 생성되는 패턴**을 의미한다.

이러한 방식의 장점은 우선 메모리 측면에서 유리하다.

최초 한 번의 new 연산자를 통해 고정된 메모리 영역을 사용하기 때문에 추후 해당 객체에 접근할 때 새로 객체를 생성하고 삭제할 필요가 없어지게 되어 **메모리 낭비를 방지**할 수 있다.

또 다른 이점으로는 **다른 클래스(객체) 간에 데이터 공유가 쉽다**. 싱글톤 인스턴스가 전역으로 사용되는 인스턴스이기 때문에 다른 클래스의 인스턴스들이 접근하여 사용할 수 있다. 하지만 여러 클래스의 인스턴스에서 동시에 접근하게 된다면 동시성 문제가 발생하므로 유의하여 설계하는 것이 좋다.

```java
public class SingletonService {

    // static 영역에 자기 자신을 선언하여 중복되는 변수 생성 X
    // 조회가 가능한 창구는 getInstance() 메서드 하나 뿐, 이 외의 생성은 어디에서도 못함
    private static final SingletonService instance = new SingletonService();

    // 조회
    public static SingletonService getInstance() {
        return instance;
    }

    // private 으로 외부 생성자로 무단 생성 못하게 막음
    private SingletonService() {}

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}

public class SingletonTest {

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        // memberService1 == memberService2
        assertThat(singletonService1).isSameAs(singletonService2);
    }
}

```

하지만 싱글톤 패턴의 단점으로는 **싱글톤 패턴을 구현하는 코드 자체가 많이 필요**하다.

또한 **의존 관계상 클라이언트가 구체 클래스에 의존**하게 된다. new 키워드를 직접 사용하여 클래스 안에서 객체를 생성하고 있으므로, 이는 SOLID 원칙 중 DIP를 위반하게 되고 OCP 원칙 또한 위반할 가능성이 높다.

이외에도 **자식 클래스를 만들 수 없다는 점**과 **내부 상태를 변경하기 어렵다는 점** 등 여러가지 문제점이 존재한다.

이를 보완하기 위해 스프링 내부에서 만든 것을 **스프링 컨테이너**(싱글톤 컨테이너)라고 부른다.

스프링 컨테이너는 싱글톤 패턴을 적용하지 않아도 객체 인스턴스를 싱글톤으로 관리한다.

그리고 이렇게 싱글톤 객체를 생성하고 관리하는 기능을 싱글톤 레지스트리라고 한다.

```java
public class SingletonTest {

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        // 1. 조회: 호출할 때마다 객체를 생성
        MemberService memberService1 = annotationConfigApplicationContext.getBean("memberService", MemberService.class);
        MemberService memberService2 = annotationConfigApplicationContext.getBean("memberService", MemberService.class);

        // 참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService1 == memberService2
        assertThat(memberService1).isSameAs(memberService2);
    }
}
```

하지만 싱글톤 패턴이든, 스프링 컨테이너를 사용하든, 객체 인스턴스를 하나만 생성해서 공유하는 싱글톤 방식은 여러 클라이언트가 하나의 객체 인스턴스를 공유하기 때문에 **싱글톤 객체는 stateless하게 설계**해야 한다. (stateful X)

만약 그렇지 않는다면,

```java
public class StatefulService {

    private int price;

    public void order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = applicationContext.getBean(StatefulService.class);
        StatefulService statefulService2 = applicationContext.getBean(StatefulService.class);

        // Thread A: 사용자 A 10000원 주문
        statefulService1.order("userA", 10000);

        // Thread B: 사용자 B 20000원 주문
        statefulService2.order("userA", 20000);

        // Thread A: 사용자 A 주문 금액 조회
        int price = statefulService1.getPrice();

        // 결과: 사용자A 주문 금액이 20000원 -> ERROR
        System.out.println("price = " + price);

        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}
```

위와 같이 A 사용자의 주문 금액이 B 사용자의 주문 금액으로 덮어 쓰여져 제대로 된 동작을 하지 못하게 된다.

따라서 **무상태**(stateless)로 설계해야 한다.

1. 특정 클라이언트에 의존적인 필드가 있으면 안된다.
2. 특정 클라이언트가 값을 변경할 수 있는 필드가 있으면 안된다.
3. 가급적 읽기만 가능해야 한다.
4. 필드 대신에 자바에서 공유되지 않는 지역변수, 파라미터, ThreadLocal 등을 사용해야 한다.

스프링을 사용하다 보면

```java
@Configuration  // == 스프링 설정 정보
public class AppConfig {

    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
}
```

위 코드처럼 memberService 빈을 만드는 코드를 보면 `memberRepository()` 를 호출한다.

그럼 `memberRepository()` 는 `new MemoryMemberRepository()` 를 호출한다.

동일하게 orderService 빈을 만드는 코드를 보면 `memberRepository()` 를 호출한다.

그럼 또 다시 `memberRepository()` 는 `new MemoryMemberRepository()` 를 호출하여 싱글톤이 깨지는 것처럼 보인다.

하지만 테스트해보면 그렇지 않은 결과를 확인할 수 있는데 이는 **@Configuration 어노테이션** 덕분이다.

@Configuration을 적용한 코드의 Bean을 등록하여 확인해보면

```java
// 결과: bean = class hello.core.AppConfig$$SpringCGLIB$$0
```

이와 같은 결과를 확인할 수 있는데, 여기서 SpringCGLIB는 순수 클래스가 아닌 **스프링이 CGLIB라는 바이트 코드 조작 라이브러리를 사용하여 AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들고 그 클래스를 스프링 빈으로 등록**한 것을 의미한다.

![Untitled 7](https://github.com/SpringArchive/Spring-Basic/assets/96408601/bdf7e84f-55fd-49c1-a7c9-d8e00b250e15)

추측컨데 CGLIB의 내부 기술로 만든 클래스는

```java
@Bean
public MemberRepository memberRepository() {
    
    if(memoryMemberRepository가 이미 스프링 컨테이너에 등록되어 있다면) {
        return 스프링 컨테이너에서 찾아서 반환;
    } else {
        기존 로직을 호출하여 MemoryMemberRepository를 생성하고 스프링 컨테이너에 등록
        return 반환
    }
}
```

이와 같은 형태를 띌 것이다.

하지만 @Configuration 대신 @Bean만 적용하게 된다면 순수 자바 코드가 스프링 빈에 등록되기 때문에 싱글톤 패턴이 깨지게 된다.

따라서 스프링 설정 정보는 항상 @Configuration을 사용하는 것이 좋다.

<br>

# Chapter 6. 컴포넌트 스캔



지금까지는 자바 코드의 `@Bean`을 통해 설정 정보에 수동으로 등록할 스프링 빈을 나열했다.

하지만 등록할 빈의 개수가 프로젝트의 규모에 따라 매우 커지게 되면 하나하나 등록하기가 힘들다.

따라서 스프링은 설정 정보가 없어도 자동으로 스프링 빈을 등록하는 **컴포넌트 스캔**이라는 기능을 제공한다.

```java
@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
}
```

위 코드처럼 컴포넌트 스캔으로 사용할 클래스를 `@Configuration`으로 스프링 빈에 등록해준 다음 `@ComponentScan`을 붙이면 자동으로 스프링 빈을 등록시켜준다.

컴포넌트 스캔은 includeFilter/excludeFilter를 통해 특정 클래스를 자동 등록하는 스프링 빈 목록에 등록/제외 시킬 수 있다.

첫 번째 파라미터 타입에 들어갈 수 있는 속성들이다.

&emsp; 1. FilterType.ANNOTATION

&emsp; &emsp;    기본 값, 어노테이션을 인식하여 동작

&emsp;  2. FilterType.ASSIGNABLE_TYPE

&emsp; &emsp;    지정한 타입과 자식 타입을 인식해서 동작

&emsp; 3. FilterType..ASPECTJ

&emsp; &emsp;    AspectJ 패턴 사용
 
&emsp; 4. FilterType.REGEX
 
&emsp; &emsp;    정규 표현식

&emsp; 5. FilterType.CUSTOM

&emsp; &emsp;    TypeFilter라는 인터페이스를 구현해서 처리

다음으로 클래스는 앞에 해당하는 타입의 클래스를 담으면 되는데 위로 예시를 들자면 “어노테이션 타입의 Configuration 클래스를 제외한다”를 의미한다.

이렇게 설정해두고 스프링 빈에 자동 등록시키고 싶은 클래스들 위에 @Component 어노테이션을 붙여 컴포넌트 스캔이 확인할 수 있게 해준다.

그리고 컴포넌트 스캔이 스캔하는 범위를 지정할 수 있는데 지정하지 않으면 기본 값인 컴포넌트 스캔 클래스를 기준으로 그 이하의 레벨 클래스들을 탐색한다.

따라서 가능하다면 최상단 클래스에 위치하는 것이 좋다.

```java
@Component
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Autowired // == ac.getBean(MemberRepository.class)
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    ...
}
```

또한 이렇게만 하면 의존 관계는 주입할 수 없게 되는데, 이를 해결하려면 의존 관계를 주입하려는 클래스 파일의 생성자 위에 @Autowired 어노테이션을 붙여 자동으로 주입시켜 준다.

만약 스프링 빈에 자동 등록을 하던 중 중복 등록을 하게 된다면 충돌이 일어날 것이다.

여기엔 두 가지 상황이 존재한다.

1. 자동 빈 등록 VS 자동 빈 등록
2. 자동 빈 등록 VS 수동 빈 등록

우선 첫 번째 상황은 컴포넌트 스캔에 의해 자동으로 스프링 빈이 등록되는데, 그 이름이 같은 경우 스프링은 오류를 발생시킨다. → `ConflictingBeanDefinitionException` 예외 발생

두 번째 상황에서 스프링은 자동 빈 등록보다 수동 빈 등록을 우선시하여 자동 등록을 수동 등록으로 오버라이딩하게 된다.

하지만 코드를 작성하는데 개인 차가 있으므로 서로 꼬일 수 있는 상황이 존재한다. 따라서 이를 방지하기 위해 스프링은 오버라이딩하고 넘어가게 되지만, 스프링 부트에서는 에러를 발생한다. → `Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true` 에러 발생

<br>

# Chapter 7. 의존 관계 자동 주입



의존 관계 주입은 크게 4가지 방법이 있다.

- 생성자 주입
    - 가장 많이 사용되는 방법
    - 생성자 호출 시점에 딱 한 번만 호출되는 것이 보장된다.
    - 불변, 필수 의존 관계에 의존 (final을 유일하게 사용하기 때문)
        - 불변: 대부분의 의존 관계 주입은 한 번 일어나면 애플리케이션 종료 시점까지 변경할 일이 없고 변경되어선 안된다. 또한 다른 방법을 사용한다면 public으로 열어두어야 하는데 이 또한 최선의 방법이 아니다.
        - 누락: final을 사용하기 때문에 개발자의 실수로 의존 관계 주입을 누락했다면 컴파일 오류가 발생되기 때문이다. (컴파일 오류는 세상에서 가장 빠르고 좋은 오류이다.)
    - 생성자가 한 개만 존재하면 `@Autowired`를 생략해도 자동 주입이 됨
- 수정자 주입
    - 선택, 변경 가능성이 있는 의존 관계에 사용
    - 자바 빈 프로퍼티 규약의 메서드 방식(getXxx, setXxx)을 사용하는 방법이다.
- 필드 주입
    - 코드가 간결하지만 외부에서 변경이 불가능하여 테스트하기 힘들다는 단점이 있음
    - DI 프레임워크가 없으면 아무것도 할 수 없다.
    - 일반적으로 사용하지 않고 특수한 상황일 때 쓰는 방식이다.
        - 애플리케이션의 실제 코드와 관계가 없는 코드
        - 스프링 설정을 목적으로 하는 `@Configuration`과 같은 곳에서만 특별한 용도로 사용
- 일반 메서드 주입
    - 한 번에 여러 필드를 주입 받을 수 있다.
    - 일반적으로 잘 사용하지 않음

종종 주입할 스프링 빈이 없어도 동작해야 할 때가 있다.

하지만 `@Autowired`만 사용하면 required 옵션이 기본 값인 true로 되어 있어서 자동 주입 대상이 없으면 오류가 발생한다.

이를 처리하는 방법은 3가지가 존재한다.

- **@Autowired(required=false)**: 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출이 안됨
- org.springframework.lang.**@Nullable**: 자동 주입할 대상이 없으면 null이 입력된다.
- Optional<>: 자동 주입할 대상이 없으면 Optional.empty가 입력된다.

@Autowired는 타입 조회이기 때문에 같은 타입이 두 개 이상 존재한다면 에러가 발생한다.

이는 수동 주입 방식을 사용해도 해결할 수 있지만 자동 주입 방식을 활용한 세가지 방법을 알아보자

- @Autowired 필드명 매칭

    1. 타입 매칭
    2. 타입 매칭의 결과가 2개 이상일 때 필드 명, 파라미터 명으로 빈 이름 매칭

- @Qualifier

    1. @Qualifier끼리 매칭
    2. 빈 이름 매칭
    3. `NoSuchBeanException` 예외 발생

- @Primary

  @Primary는 우선순위를 정하는 방법이다.

  @Autowired 시에 여러 빈이 매칭되면 @Primary가 우선권을 가진다.

  하지만 @Qualifier와 같이 사용된다면, @Qualifier가 우선권이 높으므로 먼저 선택된다.

@Qualifier를 사용하면 문자열을 사용해야 하므로 컴파일 시 타입 체크가 안되므로 어노테이션을 만들어서 문제를 해결할 수 있다.

```java
package hello.core.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {
}
```

또한 쇼핑몰 개발을 하던 도중 정액 할인 정책과 정률 할인 정책 중 선택할 수 있는 상황을 구현하려고 할 때, 두 가지 정책을 모두 스프링 빈에서 꺼내야 하는데 지금까지 우리는 두 개 이상을 꺼낼 때면 `NoUniqueBeanDefinitionException`에러가 발생한다.

이처럼 조회한 빈이 모두 필요할 때는 List 또는 Map을 사용한다.

```java
package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static hello.core.member.Grade.VIP;
import static org.assertj.core.api.Assertions.*;

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext
																					(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", VIP);
        assertThat(discountService).isInstanceOf(DiscountService.class);

        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");
        assertThat(discountPrice).isEqualTo(1000);

        int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");
        assertThat(rateDiscountPrice).isEqualTo(2000);
    }

    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policyList;

        // @Autowired 생략
        public DiscountService(Map<String, DiscountPolicy> policyMap, 
															 List<DiscountPolicy> policyList) {
            this.policyMap = policyMap;
            this.policyList = policyList;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policyList = " + policyList);
        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }
    }
}
```

로직 분석

- DiscountService는 Map으로 모든 DiscountPolicy를 주입받는다.
- `discount()` 메서드는 discountCode로 “fixDiscountPolicy”가 넘어오면 map에서 fixDiscountPolicy 스프링 빈을 찾아서 실행한다.

주입 분석

- Map<String, DiscountPolicy>: map의 키에 스프링 빈의 이름을 넣어주고 그 값으로 DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아준다.
- List<DiscountPolicy>: DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아준다.
- 만약 해당하는 타입의 스프링 빈이 존재하지 않으면 빈 컬렉션이나 Map을 주입한다.

자동 주입 vs 수동 주입

편리한 자동 주입 기능을 기본으로 사용하자.

애플리케이션에 광범위하게 영향을 미치는 기술 지원 객체는 수동 빈으로 등록해서 설정 정보가 바로 나타나게 하는 것이 유지 보수 하기에 좋다.

<br>

# Chapter 8. 빈 생명주기 콜백



스프링 빈은 **객체 생성 → 의존관계 주입**의 라이프 사이클을 가진다.

스프링은 의존관계 주입이 완료되면 스프링 빈에게 콜백 메서드를 통해서 초기화 시점을 알려주는 다양한 기능을 제공한다. 또한 스프링은 컨테이너가 종료되기 직전에 소멸 콜백을 준다.

스프링 빈의 이벤트 라이프 사이클

**스프링 컨테이너 생성 → 스프링 빈 생성 → 의존관계 주입 → 초기화 콜백 → 사용 → 소멸 전 콜백 → 스프링 종료**

스프링 빈 생명주기 콜백을 지원하는 3가지 방법

1. 인터페이스 (InitializingBean, DisposableBean)

   InitializingBean, DisposableBean 인터페이스를 implements 하여 afterPropertiesSet, destroy메서드를 오버라이드 한다.

   위 인터페이스는 스프링 전용 인터페이스고 해당 코드가 스프링 전용 인터페이스에 의존하게 된다는 단점이 있다. 또한 초기화, 소멸 메서드의 이름을 변경할 수 없고, 내가 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없다.

   스프링 초창기에 나온 방식으로 현재는 잘 쓰이지 않는 방법이다.

2. 설정 정보에 빈 등록 초기화, 소멸 메서드 지정

   설정 정보 @Configuration 파일에 @Bean(initMethod = “초기화메서드명”, destroyMethod = “소멸메서드명”) 을 붙여 지정한다.

   이는 메서드 이름을 자유롭게 지정할 수 있고, 스프링 빈이 스프링 코드에 의존하지 않는다. 또한 코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 적용할 수 있다.

   그리고 이는 특별한 기능을 지원하는데, destroyMethod를 지정하지 않더라도 디폴트 값이 (inferred)이므로 close, shutdown라는 이름의 메서드를 자동으로 호출해준다. 따라서 추론 기능을 사용하고 싶지 않다면 destroyMethod = ”” 처럼 빈칸으로 지정해두면 된다.

3. @PostConstruct, @PreDestroy 어노테이션 지원

   최신 스프링에서 가장 권장하는 방법인데 초기화, 소멸 메서드에 어노테이션 하나만 붙이면 되므로 매우 편리하다.

   스프링에 종속된 기술이 아니라 자바 표준 기능이므로 스프링이 아닌 다른 컨테이너에서도 동작한다. 또한 컴포넌트 스캔과 잘 어울린다.

   단점으로는 외부 라이브러리에는 적용하지 못한다는 것이다. 따라서 외부 라이브러리를 초기화, 종료해야 한다면 두 번째 방법을 사용하자

<br>

# Chapter 9. 빈 스코프



빈 스코프란 스프링 빈이 존재할 수 있는 범위를 뜻한다.

스프링이 지원하는 다양한 스코프

1. 싱글톤: 기본 스코프, 스프링 컨테이너의 시작과 종료까지 유지되는 가장 넓은 범위의 스코프이다.
2. 프로토타입: 스프링 컨테이너는 프로토타입 빈의 생성과 의존관계 주입까지만 관여하고 더는 관리하지 않는 매우 짧은 범위의 스코프이다.
3. 웹 관련 스코프
    1. request: 웹 요청이 들어오고 나갈 때까지 유지되는 스코프이다.
    2. session: 웹 세션이 생성되고 종료될 때까지 유지되는 스코프이다.
    3. application: 웹의 서블릿 컨텍스트와 같은 범위로 유지되는 스코프이다.

프로토타입 스코프

싱글톤 스코프의 빈을 조회하면 스프링 컨테이너는 항상 같은 인스턴스의 스프링 빈을 반환하지만, 프로토타입 스코프의 빈을 조회하면 스프링 컨테이너는 항상 새로운 인스턴스를 반환한다.

과정

1. 프로토타입 스코프 빈을 스프링 컨테이너에 요청
2. 스프링 컨테이너는 이 시점에 프로토타입 빈을 생성하고 필요한 의존관계를 주입한다.
3. 스프링 컨테이너는 생성한 프로토타입 빈을 클라이언트에 반환한다.
4. 이후에 스프링 컨테이너에 같은 요청이 오면 항상 새로운 프로토타입 빈을 생성해서 반환한다.

정리

- 스프링 컨테이너에 요청할 때마다 새로 생성
- 스프링 컨테이너는 프로토타입 빈을 생성하고 의존관계 주입, 초기화까지만 관여
- 종료 메서드가 호출되지 않음
- 프로토타입 빈은 조회한 클라이언트가 관리해야 하고, 종료 메서드에 대한 호출도 클라이언트가 직접 해야한다.

ObjectFactory, ObjectProvider

ObjectProvider는 지정한 빈을 컨테이너에서 대신 찾아주는 DL(Dependency Lookup) 서비스를 제공한다.

Request 스코프

request 스코프는 HTTP 요청 당 하나씩 생성되고, HTTP 요청이 끝나는 시점에 소멸된다.

하지만 스프링 애플리케이션을 실행하는 시점에 싱글톤 빈은 생성해서 주입이 가능하지만 request 스코프는 실제 고객의 요청이 있어야만 생성될 수 있으므로 이를 해결하기 위해선 Provider를 사용하거나 프록시를 사용해야 한다.

Provider를 사용한다면 `ObjectProvider.getObject()`를 호출하는 시점까지 request scope 빈의 생성을 지연할 수 있다.

프록시

프록시는 `@Scope(value = “request”, proxyMode = ScopedProxyMode.Target_CLASS)` 를 이용하여 사용할 수 있는데, 프록시를 사용하면 가짜 프록시 클래스를 만들고 HTTP request와 상관없이 가짜 프록시 클래스를 다른 빈에 미리 주입해 둘 수 있다.

프록시를 출력해보면

```java
myLogger = class hello.core.common.MyLogger$$EnhancerBySpringCGLIB$$0
```

이렇게 나오는데 이는 @Configuration에서 봤던 같은 SpringCGLIB을 볼 수 있다.

Request 스코프를 사용하던, 프록시를 사용하던 핵심 아이디어는 진짜 객체 조회를 꼭 필요한 시점까지 지연한다는 점이다.

하지만 이런 특별한 scope는 꼭 필요한 곳에만 최소화해서 사용하자, 무분별하게 사용하면 유지보수하기 어려워진다.

<br>

[Notion](https://www.notion.so/Inflearn-8daa7d6e0e19464390d1488f50314e8b?pvs=4)
