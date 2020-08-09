# Retrofit  

TypeSafe한 HttpClient 라이브러리 중 하나로, OkHttp에 의존하고 있다.  



## 매니페스트에 인터넷 사용권한 추가  

```
<uses-permission android:name="android.permission.INTERNET"/>
```

## gradle에 의존성 추가  

```
    implementation 'com.squareup.retrofit2:converter-gson:2.6.2' 
    implementation 'com.squareup.retrofit2:retrofit:2.6.0'
```


## retrofit 설정  

```
Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl("https://baseurl.com") // 요청할 base url을 넣는다
                .addConverterFactory(GsonConverterFactory.create()) // 데이터를 자동으로 컨버팅하기위한 gsonfactory사용
                .build();
RetrofitAPI mRetrofitAPI = mRetrofit.create(RetrofitAPI.class); //실제 api 메소드들이 선언된 인터페이스 객체 선언
```

## retrofit 요청  

```
Call<SearchResultItem> mCallSearchResultList = mRetrofitAPI.getSearchResultList(/* parameters */); //요청에 필요한 파라미터들을 넘겨줄 수 있다
mCallSearchResultList.enqueue(mRetrofitCallback);
```

## 요청을 보내고 난 후 호출되는 callback함수  

```
private Callback<Object> mRetrofitCallback = new Callback<Object>() {
        @Override
        public void onResponse(Call<Object> call, Response<Object> response) {
            Object results = (Object) response.body();
            // 여기선 객체타입을 Object로 했지만, response json 데이터에 맞게 pojo class를 작성 후 해당 클래스에 맞게 변환 가능

            // 요청 성공시 코드
        }

        @Override
        public void onFailure(Call<SearchResultItem> call, Throwable t) {
            //요청 실패시 코드
            t.printStackTrace();
        }
    };
```

##  API interface 정의  
  
@Header 어노테이션을 이용해 http 헤더에  key:value를 추가할 수 있다.  
@Query 어노테이션을 이용해 요청 파라미터를 넣을 수 있다. 밑의 코드는 baseurl/local.json?query=text&display=display... 같은 형식이 된다.  

POST, PUT 방식의 경우에는 @Query 대신 @field 를 사용한다.  

```
public interface RetrofitAPI {
    @GET("local.json") // BASE URL 뒤에 붙을 경로를 명시
    Call<Object> getSearchResultList(
            @Header("X-Naver-Client-Id") String client_id,
            @Header("X-Naver-Client-Secret") String client_secret,
            @Query("query") String text,
            @Query("display") int display,
            @Query("start") int start,
            @Query("sort") String sort
    );
}
```

## pojo class 정의시 @SeriallizedName 이란?  

 Response 와 DTO간의 변수 맵핑을 위해 사용한다.  
 
서버에서 전송되는 변수 명과 내가 정의한 pojo class의 필드 명이 다를 경우 @SeriallizedName("변수 명")으로 매핑이 가능하다.  

일치하는 경우는 쓰지않아도 자동으로 매핑된다.  

반드시 getter와 setter를 생성하도록 하자.  

