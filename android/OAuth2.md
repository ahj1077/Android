# OAuth 서비스 인증
  
  온라인 서비스에 안전하게 엑세스하려면 OAuth2 프로토콜을 이용하여 인증 토큰을 발급 받아야 한다.
  
  이 인증 토큰이라는 값은 사용자의 신원 뿐만아니라 애플리케이션이 사용자를 대신하는 권한도 나타낸다.
  
  
## 정보 수집하기
  
  OAuth2  사용을 시작하려면 액세스하려는 API에 관해 다음과 같은 사항을 알아야 합니다.
  
  - 액세스할 서비스의 #### URL
  - #### 인증 범위
  - #### 클라이언트 ID와 클라이언트 비밀번호
  
  
## 인터넷 권한 요청하기
  
  ```
    <uses-permission android:name="android.permission.INTERNET" />
  ```
  
## 인증 토큰 요청하기
  
  토큰을 가져오려면 AccountManager.getAuthToken()을 호출합니다.
  
  이때 대부분의 AccountManager 메서드는 네트워크 통신이 포함될 수 있어 비동기식입니다.
  
  따라서 콜백이 필요합니다.
  
  ```
  AccountManager am = AccountManager.get(this);
  Bundle options = new Bundle():
  
  am.getAuthToken(
    myAccount_, //  getAccountByType으로 받아온 Account
    "Manager your tasks", // 인증 범위
    options,
    this, //액티비티
    new OnTokenAcquired(),  //  AccountManagerCallback을 구현하는 클래스. 성공적으로 토큰을 받아오면 실행되는 콜백
    new Handler(new OnError())); // 에러 발생시 콜되는 콜백
  
  ```
  
  호출이 성공하면 토큰은 Bundle 내에 있습니다.
  
  ```
  private class OnTokenAcquired implements AccountManagerCallback<Bundle> {
        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            Bundle bundle = result.getResult();
            
            //번들에서 토큰을 가져옵니다.
            String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
            ...
        }
    }
  ```
  
  ## 인증 토큰 다시 요청하기
  
  첫 번째 인증 토큰 요청이 다음과 같은 이유로 인해 실패할 수 있습니다.
  
  - 저장된 인증 정보가 충분치 않아 계정 액세스 권한을 얻지 못했을 떼
  - 캐시된 인증 토큰이 만료되었을 때
  
  이때 불충분한 사용자 인증 정보는 AccountManagerCallback에서 수신한 Bundle을 통해 전달됩니다.
  
  ```
  private class OnTokenAcquired implements AccountManagerCallback<Bundle> {
        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            ...
            Intent launch = (Intent) result.getResult().get(AccountManager.KEY_INTENT);
            if (launch != null) {
                startActivityForResult(launch, 0);
                return;
            }
        }
    }
  
  ```
  
  startActivityForResult()를 사용하였으므로 onActivityResult()를 구현하여 Intent의 결과를 받을 수 있습니다.
  
  결과가 RESULT_OK면 인증자는 개발자가 요청한 액세스 수준에 충분하도록 인증 정보를 업데이트한 것이며,
  
  getAuthToken()을 다시 호출하여 새 인증 토큰을 요청해야 합니다.
  
  
  ## 온라인 서비스 연결하기 (Google)
  
  ```
      URL url = new URL("https://www.googleapis.com/tasks/v1/users/@me/lists?key=" + your_api_key);
    URLConnection conn = (HttpURLConnection) url.openConnection();
    conn.addRequestProperty("client_id", your client id);
    conn.addRequestProperty("client_secret", your client secret);
    conn.setRequestProperty("Authorization", "OAuth " + token);  //위에서 구한 토큰 값
    
  ```
  
  여기서 http 오류코드 401이 반환된다면 토큰이 거부된 것입니다. 일반적인 이유는 토큰 만료입니다.
  
  토큰이 만료되었을 땐 invalidateAuthToken() 을 호출하고, 토큰 획득 정차를 한 번 더 반복하면 됩니다.
  
  
