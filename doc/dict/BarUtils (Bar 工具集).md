# BarUtils详情

<br/>

## 方法介绍

<br/>

### getStatusBarHeight （获取状态栏高度 px ）

|参数|类型|描述|
|--|--|--|
|array|T|根据泛型对象返回|

<br/>

### setStatusBarVisibility （设置状态栏是否可见）

|参数|类型|描述|
|--|--|--|
|activity|Activity|Activity|
|window|Window|当前窗口|
|isVisible|boolean|是否可见|

<br/>

### isStatusBarVisible （判断状态栏是否可见）

|参数|类型|描述|
|--|--|--|
|activity|Activity|Activity|
|window|Window|当前窗口|

<br/>

### setStatusBarLightMode （设置状态栏是否为浅色模式）

|参数|类型|描述|
|--|--|--|
|activity|Activity|Activity|
|window|Window|当前窗口|
|isLightMode|boolean|是否为浅色模式|

<br/>

### isStatusBarLightMode （判断状态栏是否为浅色模式）

|参数|类型|描述|
|--|--|--|
|activity|Activity|Activity|
|window|Window|当前窗口|

<br/>

### addMarginTopEqualStatusBarHeight （为 view 增加 MarginTop 为状态栏高度式）

> Api > 19

|参数|类型|描述|
|--|--|--|
|view|View|视图|

<br/>

### subtractMarginTopEqualStatusBarHeight （为 view 减少 MarginTop 为状态栏高度式）

> Api > 19

|参数|类型|描述|
|--|--|--|
|view|View|视图|

<br/>

### setStatusBarColor （设置状态栏颜色）

> Api > 19

|参数|类型|描述|
|--|--|--|
|activity|Activity|Activity|
|window|Window|当前窗口|
|fakeStatusBar|View|假状态栏视图|
|color|ColorInt|设置的颜色|
|isDecor|ColorInt|true表示在DecorView中添加假状态栏,false表示在ContentView中添加假状态栏。|

<br/>

### setStatusBarColor4Drawer （为 DrawerLayout 设置状态栏颜色）

> Api > 19

|参数|类型|描述|
|--|--|--|
|drawer|DrawerLayout|给定DrawerLayout|
|fakeStatusBar|View|假状态栏视图|
|color|ColorInt|设置的颜色|
|isTop|boolean|true表示在顶层设置DrawerLayout, false表示不设置|

<br/>

### transparentStatusBar （透明状态栏）

> Api > 19

|参数|类型|描述|
|--|--|--|
|activity|Activity|Activity|
|window|Window|当前窗口|

<br/>

### getActionBarHeight （获取 ActionBar 高度）

|参数|类型|描述|
|--|--|--|
|activity|Activity|Activity|
|window|Window|当前窗口|

<br/>

### setNotificationBarVisibility （设置通知栏是否可见）

|参数|类型|描述|
|--|--|--|
|isVisible|boolean|true表示通知栏可见 false表示通知栏不可见|

<br/>

### getNavBarHeight （获取导航栏高度）

<br/>
<br/>

### setNavBarVisibility （设置导航栏是否可见）

|参数|类型|描述|
|--|--|--|
|activity|Activity|Activity|
|window|Window|当前窗口|
|isVisible|boolean|true表示导航栏可见 false表示导航栏不可见|

<br/>

### isNavBarVisible （判断导航栏是否可见）

|参数|类型|描述|
|--|--|--|
|activity|Activity|Activity|
|window|Window|当前窗口|

<br/>

### setNavBarColor （设置导航栏颜色）

|参数|类型|描述|
|--|--|--|
|activity|Activity|Activity|
|window|Window|当前窗口|
|color|ColorInt|设置的颜色|

<br/>

### getNavBarColor （获取导航栏颜色）

|参数|类型|描述|
|--|--|--|
|activity|Activity|Activity|
|window|Window|当前窗口|

<br/>

### isSupportNavBar （判断是否支持导航栏）

<br/>
<br/>

### setNavBarLightMode （设置导航栏是否为浅色模式）

> Api > 19

|参数|类型|描述|
|--|--|--|
|activity|Activity|Activity|
|window|Window|当前窗口|
|isLightMode|boolean|True设置导航栏灯光模式，false否则|

<br/>

### isNavBarLightMode （判断导航栏是否为浅色模式）

|参数|类型|描述|
|--|--|--|
|window|Window|当前窗口|
