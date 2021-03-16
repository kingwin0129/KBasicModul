# ActivityUtils 详情

## 方法介绍

<br/>

<br/>

### addActivityLifecycleCallbacks  （添加Activity生命周期监听回调）

|参数|类型|描述|
|--|--|--|
|activity|Activity|指定的Activity|
|callbacks|ActivityLifecycleCallbacks|Activity监听回调，传入ActivityLifecycleCallbacks对象|

<br/>

<br/>

### removeActivityLifecycleCallbacks  （移除Activity生命周期监听回调）

|参数|类型|描述|
|--|--|--|
|activity|Activity|指定的Activity|
|callbacks|ActivityLifecycleCallbacks|Activity监听回调，传入ActivityLifecycleCallbacks对象|

<br/>

<br/>

### getActivityByContext  （通过Context获取Activity）

|参数|类型|描述|
|--|--|--|
|context|Context|Context上下文|

<br/>

<br/>

### isActivityExists (Activity是否存在)

|参数|类型|描述|
|--|--|--|
|pkg|String|包名|
|cls|String|类名|

<br/>

### startActivity (启动Activity)

|参数|类型|描述|
|--|--|--|
|activity|Activity|当前的Activity|
|pkg|String|包名|
|clz|Class<? extends Activity>|需要被打开的Activity类|
|cls|String|类名|
|options|Bundle|参数|
|intent|Intent|要启动的活动的描述|
|sharedElements|View[]|要传输给被调用活动的共享元素的名称及其关联视图(<API:21 无效)|
|enterAnim|Int(Res)|进入动画|
|exitAnim|Int(Res)|离开动画|

<br/>

### startActivityForResult (启动Activity带返回结果)

|参数|类型|描述|
|--|--|--|
|requestCode|Int|请求码|
|activity|Activity|当前的Activity|
|fragment|Fragment|当前的Fragment|
|pkg|String|包名|
|clz|Class<? extends Activity>|需要被打开的Activity类|
|cls|String|类名|
|options|Bundle|参数|
|intent|Intent|要启动的活动的描述|
|sharedElements|View[]|要传输给被调用活动的共享元素的名称及其关联视图(<API:21 无效)|
|enterAnim|Int(Res)|进入动画|
|exitAnim|Int(Res)|离开动画|

<br/>

<br/>

### startActivities (启动多个Activity)

|参数|类型|描述|
|--|--|--|
|activity|Activity|当前的Activity|
|options|Bundle|参数|
|intents|Intent[]|要启动的活动的描述 数组|
|enterAnim|Int(Res)|进入动画|
|exitAnim|Int(Res)|离开动画|

<br/>

<br/>

<br/>

### startHomeActivity （回到桌面）

<br/>

<br/>

### startLauncherActivity （打开启动页）

|参数|类型|描述|
|--|--|--|
|pkg|String|包名|

<br/>

<br/>

### getLauncherActivity （获取启动页Activity名称）

|参数|类型|描述|
|--|--|--|
|pkg|String|包名|

<br/>

<br/>

### getMainActivities （获取入口Activity列表）

|参数|类型|描述|
|--|--|--|
|pkg|String|包名|

<br/>

<br/>

### getTopActivity （获取栈顶Activity）

### isActivityAlive （Activity是否存活）

|参数|类型|描述|
|--|--|--|
|context|Context|上下文|
|activity|Activity|Activity本身|

<br/>

<br/>

### isActivityExistsInStack （该活动是否存在于活动的堆栈中）

|参数|类型|描述|
|--|--|--|
|activity|Activity|Activity本身|
|clz|Class<? extends Activity>|Activity类|

<br/>

<br/>

### finishActivity （结束Activity）

|参数|类型|描述|
|--|--|--|
|activity|Activity|Activity本身|
|clz|Class<? extends Activity>|Activity类|
|isLoadAnim|boolean|是否使用退出动画|
|enterAnim|Int(Res)|进入动画|
|exitAnim|Int(Res)|离开动画|

<br/>

<br/>

### finishToActivity （结束到指定 Activity）

|参数|类型|描述|
|--|--|--|
|isIncludeSelf|boolean|结束是否包含指定的Activity|
|activity|Activity|Activity本身|
|clz|Class<? extends Activity>|Activity类|
|isLoadAnim|boolean|是否使用退出动画|
|enterAnim|Int(Res)|进入动画|
|exitAnim|Int(Res)|离开动画|

<br/>

<br/>

### finishOtherActivities （结束所有其他类型的 Activity）

|参数|类型|描述|
|--|--|--|
|clz|Class<? extends Activity>|Activity类|
|isLoadAnim|boolean|是否使用退出动画|
|enterAnim|Int(Res)|进入动画|
|exitAnim|Int(Res)|离开动画|

<br/>

<br/>

### finishAllActivities （结束所有的 Activity）

|参数|类型|描述|
|--|--|--|
|clz|Class<? extends Activity>|Activity类|
|isLoadAnim|boolean|是否使用退出动画|
|enterAnim|Int(Res)|进入动画|
|exitAnim|Int(Res)|离开动画|

<br/>

<br/>

### getActivityIcon （获取Activity Icon）

|参数|类型|描述|
|--|--|--|
|clz|Class<? extends Activity>|Activity类|
|activity|Activity|Activity本身|
|activityName|ComponentName|组件名称|

<br/>

<br/>

### getActivityLogo （获取Activity Logo）

|参数|类型|描述|
|--|--|--|
|clz|Class<? extends Activity>|Activity类|
|activity|Activity|Activity本身|
|activityName|ComponentName|组件名称|

<br/>

<br/>

### getActivityThread  （获取应用ActivityThread）

<br/>

<br/>

### getActivityThreadInActivityThreadStaticField  （通过反射获取ActivityThread sCurrentActivityThread变量）

<br/>

<br/>

### getActivityThreadInActivityThreadStaticMethod  （通过反射获取ActivityThread currentActivityThread方法返回值）

<br/>

<br/>

### getApplicationByReflect  （通过反射获取应用Application）

<br/>

<br/>

## 技术点

## [Context](https://github.com/kingwin0129/KBasicModul/blob/main/doc/note/Context/Context.md)

## [Activity](https://github.com/kingwin0129/KBasicModul/blob/main/doc/note/Activity/Activity.md)