# 안드로이드에서 뷰가 그려지는 과정
  
  
  안드로이드에서 UI를 렌더링하기 위한 여러 개념들이 있는데 window, surface, canvas, view 가 있다.
  
  각 요소들의 크기는 Window > Surface > Canvas > View 순이다.
  
  
  ## Window
  
  하나의 화면 안에서는 여러개의 Window를 가질 수 있고, 이러한 Window들은 WindowManager가 관리를 합니다.
  
  Window는 뭔가를 그릴 수 있는 창이며, 보통 하나의 Surface를 가지고 있습니다. 어플리케이션은 WindowManager와 상호작용하여
  
  Window를 만들어내고 각각의 Window 표면에 그리기 위해 Surface를 만듭니다.
  
  일반적으로 Activity가 window를 가지게 됩니다.
  
  
  ## Surface
  
  Surface는 화면에 합성되는 픽셀을 보유한 객체입니다. 화면에 표시되는 모든 Window는 자신만의 Surface가 포함되어 있으며,
  
  Surface Flinger가 여러 소스로부터 그래픽 데이터 버퍼를 받고, 그것들을 합성해서 Display로 보냅니다.
  
  개별 Surface는 이중 버퍼 렌더링을 위한 1개 이상 (보통 2개)의 버퍼를 가집니다.
  
  
  #### 이중 버퍼 렌더링?
  
  - 스크린에 출력될 화면 데이터는 **프레임 버퍼**에 저장되는데, 하나의 버퍼만 가지는 경우 이미지가 반복해서 그려지게 되거나, 이미지가 다 그려지지 않아도
  
  화면 주사율 때문에 렌더링을 해야할 때, 다 그려지지 않은 프레임 버퍼가 렌더링이 되어서 이미지가 깜빡이는 Flicker 현상이 
  
  나타날 수 있습니다. 이를 해결하기 위해 프레임 버퍼에 바로 렌더링 하지 않고, 다른 버퍼를 만들어서 그 버퍼에 렌더링을 완료 하고,
  
  다음 프레임 버퍼에 옮기는 방식을 사용하여 Flicker 현상을 해결할 수 있습니다.
  
  
  ## Canvas
  
  실제 UI를 그리기 위한 공간으로 비트맵이 그려지는 공간이다.
  
  
  ## View
  
  View는 Window 내부의 대화식 UI요소입니다. Window에는 단일 뷰 계층 구조가 연결되어 있으며 모든 Window의 동작을 제공합니다.
  
   Window가 다시 뭔가를 그려야할때마다 Window의 Surface에서 작업이 수행됩니다. 만약 Surface가 잠기면 그리는데 사용할 수 있는
   
   Canvas가 반환이 되니다. 그럼 Draw Traversal로 인해 각 뷰를 계층적으로 Canvas에 전달하여 UI를 그리기 시작합니다.
  
   완료가 되면 Surface가 잠기고 방금 그린 Buffer가 포그라운드 상태로 바뀌고 Surface Flinger에 의해서 화면에 합성됩니다.
   
   
  ## SurfaceView
  
  기존의 뷰를 상속받으며, 그래픽처리가 빠른 View 이다.
  
  일반적인 View는 Main Thread에서 캔버스를 그리기 때문에, 그리기를 하는 동안에는 사용자의 입력을 받을 수 없고 그로 인해 반응성이 좋지 못합니다.
  
  그렇다고 그리는 작업을 작업 스레드에서 하고 싶어도 안드로이드 정책상 main thread 이외에서는 할 수가 없습니다.
  
  이때 사용하는게 SurfaceView입니다.
  
  일반 view와 달리 그리기를 onDraw를 호출하는 것이 아닌 스레드를 이용해 강제로 화면에 그려버린다.
  
  SurfaceView는 Canvas가 아닌 Surface(= 가상 메모리 화면)에 그리고 그려진 Surface를 화면에 뿌리기 때문에 게임이나 카메라같은 
  
  높은 반응성이 필요한 UI 작업이 필요한 경우 사용할 수 있습니다.
  
  SurfaceView의 구조는 Surface와 SurfaceHolder 로 구성되며, SurfaceHolder가 Surface에 미리 그리고, 이 Surface가 SurfaceView에 반영하는 구조입니다.
  
  
  
  
