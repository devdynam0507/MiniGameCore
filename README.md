# MiniGameCore
- 미니게임 서버 개발에 필요한 Utility와 Module을 지원합니다.
- 개발되는 미니게임, 플러그인을 등록시켜 의존성을 높여줍니다.

### GameModule Example
```java
public class SampleGame extends Game{

    @Override
    public void start(List<PlayerData> players){...}/** 게임 시작 */
    @Override
    public abstract void stop(){...} /** 게임 종료 */
    @Override    
    public RunningType getRunningType(){...}
    @Override
    public DataManager getGameDataManager(){...}
    @Override
    public void recover(){...} /** 게임 맵 복구 */
    @Override    
    public void teamDesignate(){...} /** 팀 추첨 */
    @Override
    public String getCustomName(){...} /** GUI에 표시될 게임 이름 */
    @Override
    public List<String> getGameLore(){...} /** GUI에 표시될 게임 설명 */
    @Override
    public String getName(){...} /** 게임 이름 반환 */

}
```
```java
public class Main extends JavaPlugin{

  @Override
  public void onEnable(){
    GameModuleManager.module().register(new SampleGame("sample")); /* Register GameModule */
  }

}
```

### PluginModule Example
```java
public class SamplePlugin extends PluginModule{

  @Override
  public boolean load(){...}
  
}
```
```java
public class Main extends JavaPlugin{

  @Override
  public void onEnable(){
    PluginModuleManager.module().register(new SamplePlugin(this));
  }

}
```
### PlayerData
- 유저 기록을 로드합니다.
- 유저 기록을 담고있습니다.
## Example
```java
PlayerData data = PlayerData.get("playerName");
```


