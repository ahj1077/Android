## 키보드 엔터키 바꾸기
  
  
  xml 의 EditText 태그에서 imeOptions 속성에 값을 줄 수 있다. 
  
  속성에 주는 값마다 키보드에 나타나는 모양이 바뀐다.

  ```
  <EditText

     ... 
    android:imeOptions="actionSearch"/> 
  ```
  
  
## ImeOptions
  
  - normal : 특별한 의미 없음
  - actionGo : 이동의 의미 (웹 브라우저에서 사용)
  - actionSearch : 검색의 의미
  - actionSend : 보내기의 의미
  - actionNext : 다음의 의미
  - actionPrevious : 이전의 의미 (api 11 부터 가능)
  

## 엔터키 동작 시키기
  
  
  editText 객체에 setOnEditorActionListener를 이용하여 동작시킬 수 있다.
  
  ```
  // java
  
  editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (actionId) {
            case EditorInfo.IME_ACTION_SEARCH:
                // 검색 동작
                break;
            default:
                // 기본 엔터키 동작
                return false;
        }
        return true;
    }
 });

 ```
 
 ```
 //kotlin
 
  editText.setOnEditorActionListener{v, id, event ->
            if(id == EditorInfo.IME_ACTION_SEND){
                // 보내기 동작
                editText.setText("")
            }
            true
        }
 
 ```






  

