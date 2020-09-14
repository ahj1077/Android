# Multidex
  
  
  안드로이드는 JVM기반의 ART위에서 동작한다.
  
  ART위에서 동작하기 위해 안드로이드 코드는 dex파일로 컴파일 된다. 이때 메서드 수가 64k (65536)개를 초과할 수 없어서
  
  나오게 된 개념이 Multidex다.
  
  Multidex는 메서드가 64k(65536)개를 초과하지 않도록 dex파일을 여러개로 쪼개주고, 쪼개진 dex를 읽을 수 있게 해준다.
  

## HOW TO?
  
  minSdkVersion 이 21 이상인 경우 multidex가 기본적으로 사용 설정된다.
  
  20이하 인 경우에는 multidex를 명시적으로 선언해 줘야한다.
  
  ```
  // build.gradle
  
  android{
    complieSdkVersion 28
    defaultConfig{
      ...
      
      // 멀티덱스 기능 true
      multiDexEnabled true
    }
    ...
  }
  
  dependencies{
    // 의존성 추가
    implementation 'com.android.support:multidex:1.0.3'
  }
  ```
  
  ```
  import android.support.multidex.MultiDexApplication;
  
  public class App extends MultiDexApplication{ // 매니페스트의 앱 이름과 같은 클래스가 상속받아야한다.
    @Override
    public void onCreate(){
      super.onCreate();
    }
  }
  ```
  
  
  
