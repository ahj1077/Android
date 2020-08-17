# 자바 버전 별 특징
  
  ## JAVA 5 
  
  - 2004
  - 제네릭, 어노테이션, 오토 박싱,  enum, vararg, foreach, static imports 도입
  - java.util.concurrent API와 스캐너 class
  
  ## JAVA 6
  
  - 2006
  - 가비지 컬렉터 지원 (시험작)
  - 보안 , 성능 강화
  
  ## JAVA 7
  
  - 2011
  - JavaFX가 기본으로 포함
  - 정식으로 가비지 컬렉터를 포함하여 제공
  - 제네릭 타입 추론 지원
    ```
    //예제
    List<String> list = new ArratList<>();
    ```
  - Switch 문 case에 String 지원
  - 자동 리소스 관리. (파일 자동 close)
  
  ## JAVA 8
  
  - 2014
  - 오라클 JDK 와 OpenJDK로 나뉨
  - #### 람다 및 함수형 프로그래밍
  - 인터페이스에 구현체를 작성가능
  - LocalDataTime 같은 DateTime API추가
  
  ## JAVA 9
  
  - 2017
  - 인터페이스에 private 메서드와, private static 메서드를 추가할 수 있음 (중복 코드를 피하며 캡슐화)
  - 모듈 시스템 (Jigsaw) 지원
  
  ## JAVA 10
  
  - 2018
  - #### 지역변수에 var 키워드 사용 가능. 
  - Unmodifiable Collection 향상
  - 병렬처리 GC 도입
  - JIT 컴파일러 (테스트용) 추가
  
  ## JAVA 11
  
  - 2018
  - javafx가 jdk에서 분리되어 모듈로 제공
  - 람다 파라미터 변경 (var x, var y) -> x.process(y)  ==> (x,y) -> x.process(y)
  - HTTP 클라이언트 표준화 기능 추가. (java.net.http 패키지 추가)
  - 엡실론 가비지 컬렉터 추가 (메모리를 회수하지 않는 GC)
  - Z 가비지 컬렉터 추가 (성능 향상)
  
  ## JAVA 12
  
  - 2019
  - Switch문 확장 (변수 할당문에도 사용 가능)
  ```
  // 변경 전
  switch(day){
    case MONDAY:
    case FRIDAY:
      System.out.println(6);
    break;
  }
  
  // 변경 후
  switch(day){
    case MONDAY,FRIDAY -> System.out.println(6);
  }
  
  ```
  - GC 개선
  
  ## JAVA 14
  
  - 2020
  - instanceof 연산자 강화
  
  ```
  // 형변환 없이 if 블록 내에서지역변수로 사용할 수 있다.
  if (obj instanceof String s){
    System.out.println(s);
    if(s.length() > 2){
      //...
    }
  }
  
  if(obj instanceof String s && s.length() > 2){
    //...
  }
  ```
  - 플랫폼 별 패키지를 작성하는 도구인 jpackage 제공
  - record 추가 
  ```
  record Point(int x,int y){
    //상속 불가. final 클래스처럼
    
    //초기화 필드는 private final이다.
    
    //static 필드와 메서드를 가질 수 있다.
    static int LENGTH = 25;
    
    public static int getDefaultLengh(){
      return LENGTH;
    }
    
    //getter가 자동으로 생성되어 new 연산자로 생성, point.x 처럼 접근 가능
    
  }
  ```
  
