# ArrayUtils详情

<br/>

## 方法介绍

<br/>

### newArray （给定元素的新数组）

|参数|类型|描述|
|--|--|--|
|array|T|根据泛型对象返回|

<br/>

### newLongArray （给定元素的新数组）

|参数|类型|描述|
|--|--|--|
|array|long[]|给定元素数组|

<br/>

### newIntArray （给定元素的新数组）

|参数|类型|描述|
|--|--|--|
|array|int[]|给定元素数组|

<br/>

### newShortArray （给定元素的新数组）

|参数|类型|描述|
|--|--|--|
|array|short[]|给定元素数组|

<br/>

### newCharArray （给定元素的新数组）

|参数|类型|描述|
|--|--|--|
|array|char[]|给定元素数组|

<br/>

### newByteArray （给定元素的新数组）

|参数|类型|描述|
|--|--|--|
|array|byte[]|给定元素数组|

<br/>

### newDoubleArray （给定元素的新数组）

|参数|类型|描述|
|--|--|--|
|array|double[]|给定元素数组|

<br/>

### newFloatArray （给定元素的新数组）

|参数|类型|描述|
|--|--|--|
|array|float[]|给定元素数组|

<br/>

### newBooleanArray （给定元素的新数组）

|参数|类型|描述|
|--|--|--|
|array|boolean[]|给定元素数组|

<br/>

### isEmpty （数组是否为空 true ==> 为空  false ==> 不为空）

|参数|类型|描述|
|--|--|--|
|array|Object|给定元素数组|

<br/>

### getLength （获取数组长度）

|参数|类型|描述|
|--|--|--|
|array|Object|给定元素数组|

<br/>

### isSameLength （两组数组长度是否一致 true ==> 一致  false ==> 不一致）

|参数|类型|描述|
|--|--|--|
|array1|Object|给定元素数组1|
|array2|Object|给定元素数组2|

<br/>

### get （获取数组的指定索引的值）

|参数|类型|描述|
|--|--|--|
|array|Object|给定元素数组|
|index|int|指定索引|
|defaultValue|Object|默认值|

<br/>

### set （设置指定数组索引的值）

|参数|类型|描述|
|--|--|--|
|array|Object|给定元素数组|
|index|int|指定索引|
|value|Object|索引组件的新值|

<br/>

### equals （两个数组是否相等）

> 比对类型需保持一致，支持所有基本数据类型

|参数|类型|描述|
|--|--|--|
|array1|Basic Data Types Of Array|给定元素数组1|
|array2|Basic Data Types Of Array|给定元素数组2|

<br/>

### reverse （反转给定数组的顺序）

> 支持所有基本数据类型

|参数|类型|描述|
|--|--|--|
|array|Basic Data Types Of Array|给定元素数组|

<br/>

### copy （复制数组）

> 支持所有基本数据类型

|参数|类型|描述|
|--|--|--|
|array|Basic Data Types Of Array|浅克隆的数组，可以是null|

<br/>

### subArray （截取指定的数组）

> 支持所有基本数据类型

|参数|类型|描述|
|--|--|--|
|array|Basic Data Types Of Array|给定元素数组|
|startIndexInclusive|int|开始截取的下标|
|endIndexExclusive|int|结束截取的下标|

<br/>

### add （复制给定数组并在新数组的末尾添加新的给定元素或新的给定元素数组）

> 支持所有基本数据类型

|参数|类型|描述|
|--|--|--|
|array1|Basic Data Types Of Array|给定源数组|
|element|Basic Data Types Of Object|新的给定元素|
|array2|Basic Data Types Of Array|新的给定元素数组|
|index|int|指定开始插入位置下标|

<br/>

### remove （从指定数组中移除指定位置的元素）

> 支持所有基本数据类型

|参数|类型|描述|
|--|--|--|
|array1|Basic Data Types Of Array|给定源数组|
|element|Basic Data Types Of Object|移除的给定元素|
|index|int|指定移除的位置下标|

<br/>

### indexOf （查找第一个元素的索引）

> 支持所有基本数据类型

|参数|类型|描述|
|--|--|--|
|array1|Basic Data Types Of Array|给定源数组|
|element|Basic Data Types Of Object|查找的给定元素|
|startIndex|int|开始找寻的起始索引，由前往后找|

<br/>

### lastIndexOf （查找最后一个元素的索引）

> 支持所有基本数据类型

|参数|类型|描述|
|--|--|--|
|array1|Basic Data Types Of Array|给定源数组|
|element|Basic Data Types Of Object|查找的给定元素|
|startIndex|int|开始找寻的结束索引，由后往前找|

<br/>

### contains （给定数组中是否包含指定元素 true ==> 包含  false ==> 不包含）

> 支持所有基本数据类型

|参数|类型|描述|
|--|--|--|
|array|Basic Data Types Of Array|给定源数组|
|element|Basic Data Types Of Object|给定元素|
|startIndex|int|开始找寻的结束索引，由后往前找|

<br/>

### toPrimitive （拆箱操作）

> 支持所有基本数据类型 
char    <->   Character
long    <->   Long
int     <->   Integer
short   <->   Short
byte    <->   Byte
double  <->   Double
float   <->   Float
boolean <->   Boolean

|参数|类型|描述|
|--|--|--|
|array|Basic Data Types Of Array|给定源数组|

<br/>

### toObject （装箱操作）

> 支持所有基本数据类型 
char    <->   Character
long    <->   Long
int     <->   Integer
short   <->   Short
byte    <->   Byte
double  <->   Double
float   <->   Float
boolean <->   Boolean

|参数|类型|描述|
|--|--|--|
|array|Basic Data Types Of Array|给定源数组|

<br/>

### asList （转为链表）

|参数|类型|描述|
|--|--|--|
|array|T...|给定源的泛型数组|

<br/>

### asUnmodifiableList （转为不可变链表）

|参数|类型|描述|
|--|--|--|
|array|T...|给定源的泛型数组|

<br/>

### asArrayList （转为数组链表）

|参数|类型|描述|
|--|--|--|
|array|T...|给定源的泛型数组|

<br/>

### asLinkedList （转为双向链表）

|参数|类型|描述|
|--|--|--|
|array|T...|给定源的泛型数组|

<br/>

### sort （排序）

> 支持所有基本数据类型

|参数|类型|描述|
|--|--|--|
|array|T...|给定源的数组|
|comparator|Comparator|排序规则|

<br/>

### forAllDo （对所有元素做操作）

> 支持所有基本数据类型

|参数|类型|描述|
|--|--|--|
|array|Basic Data Types Of Array|给定源的数组|
|closure|Closure|要执行的闭包,自定义接口|

<br/>

### toString （数组转为字符串）

> 支持所有基本数据类型

|参数|类型|描述|
|--|--|--|
|array|Basic Data Types Of Array|给定源的数组|
