package hello.core.singleton;

// 싱글톤: 같은 메서드가 여러번 콜 될 때마다 참조값은 다르지만 내용물은 같은 객체가 여러개가 생성되는 것을 막고자 하나의 공유된 객체만을 효율적으로 사용하게 만드는 방법
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
