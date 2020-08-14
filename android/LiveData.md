# LiveData
  
  jetpack의 구성요소
  
  데이터를 담아둘 수 있는 데이터 홀더 클래스로, 다른 클래스와 달리 액티비티, 프래그먼트, 서비스 들의 수명주기를 고려합니다.
  
  이때, 활성 수명주기(start or resume)에 있는 구성요소만 관련 데이터를 업데이트합니다.
  
## 왜 LiveData를 쓸까
  
  #### 1. UI와 데이터 상태의 일치 보장
    
    livedata는 관찰자 패턴을 따릅니다. 구성요소의 수명주기가 변경될 때마다 Observer 객체에 알립니다. 
    따라서 이러한 Observer 객체(주로 ui 구성요소)에 UI를 업데이트 할 수 있습니다.
  
  #### 2. 최신 데이터 유지
    
    수명 주기가 비활성화되면 다시 활성화될 때 최신 데이터를 수신합니다. 
    예를 들어 백그라운드에 있었던 활동은 포그라운드로 돌아온 직후 최신 데이터를 받습니다  
  
  #### 3. 수명 주기를 더 이상 수동으로 처리하지 않음
    
    UI 구성요소는 관련 데이터를 관찰하기만 할 뿐 관찰을 중지하거나 다시 시작하지 않습니다. 
    LiveData는 관찰하는 동안 관련 수명 주기 상태의 변경을 인식하므로 이 모든 것을 자동으로 관리합니다.
    
## 사용 방법
  
  LiveData는 List와 같은 Collections를 구현하는 객체를 비롯하여 모든 데이터와 함께 사용할 수 있는 래퍼입니다.
  
  LiveData 객체는 일반적으로 ViewModel 객체 내에 저장되며 다음 예에서 보는 것과 같이 getter 메서드를 통해 액세스됩니다.
  
  ```
     public class NameViewModel extends ViewModel {

        //MutableLiveData란 변경할 수 있는 LiveData 형입니다.    
        //그냥 LiveData는 변경불가능함!
        private MutableLiveData<String> currentName;

        public MutableLiveData<String> getCurrentName() {
            if (currentName == null) {
                currentName = new MutableLiveData<String>();
            }
            return currentName;
        }

    // Rest of the ViewModel...
    }
  ```
  
  대부분의 경우 앱 구성요소의 onCreate() 메서드는 LiveData 객체 관찰을 시작하기 적합한 장소이며 그 이유는 다음과 같습니다.
  
    1. 시스템이 액티비티나 프래그먼트의 onResume() 메서드에서 중복 호출을 하지 않도록 하기 위해서입니다.
  
    2. 액티비티 또는 프래그먼트가 활성 상태가 되는 즉시 표시할 수 있는 데이터를 보유하도록 하기 위해서입니다. 
  
  
  다음은 액티비티에서 뷰모델을 생성해  LiveData를 관찰하도록 하는 과정입니다.
  
  ```
   public class NameActivity extends AppCompatActivity {

        private NameViewModel model;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Other code to setup the activity...

            // 뷰모델을 생성합니다.
            model = new ViewModelProvider(this).get(NameViewModel.class);

            // 관찰자를 생성하도록 함.
            final Observer<String> nameObserver = new Observer<String>() {
                @Override
                public void onChanged(@Nullable final String newName) {
                
                    // ui를 갱신하는 코드
                    nameTextView.setText(newName);
                }
            };

            // 옵저버를 넘겨 뷰모델을 관찰할 수 있도록 합니다.
            model.getCurrentName().observe(this, nameObserver);
        }
    }
    
  ```
  
  버튼 클릭으로 LiveData 갱신하는 예제
  
  ```
  // setValue 메서드 호출로 인해 Observer의 onChange 메서드가 호출되어 관련 ui가 갱신된다.
   button.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            String anotherName = "John Doe";
            model.getCurrentName().setValue(anotherName);
        }
    });
  ```
  
  #### ※ 기본 스레드에서 LiveData 객체를 업데이트하려면 setValue(T) 메서드를 호출해야 합니다.
  ####    코드가 작업자 스레드에서 실행된다면 대신 postValue(T) 메서드를 사용하여 LiveData 객체를 업데이트할 수 있습니다.
  
  
