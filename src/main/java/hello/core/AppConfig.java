package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// DI 컨테이너
// 관심사의 분리: 객체를 생성하고 연결하는 역할(AppConfig) + 실행하는 역할(memberServiceImpl/orderServiceImpl)
@Configuration  // == 스프링 설정 정보
public class AppConfig {

    // @Bean memberService -> new MemoryMemberRepository()
    // @Bean orderService -> new MemoryMemberRepository() + new RateDiscountPolicy()
    //                           MemoryMemberRepository 두번 호출 -> 두 개의 인스턴스로 싱글톤 깨지는거 아님?
    //                                                          -> 아님, 모두 다 같은 하나의 인스턴스임
    //                                                          -> @Configuration이 싱글톤을 지켜준 것

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
