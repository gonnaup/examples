### 																																																						Redis

------

#### 简介

​		Redis 是一个开源（BSD许可）的，内存中的数据结构存储系统，它可以用作数据库、缓存和消息中间件。 它支持多种类型的数据结构，如 [字符串（strings）](http://www.redis.cn/topics/data-types-intro.html#strings)， [散列（hashes）](http://www.redis.cn/topics/data-types-intro.html#hashes)， [列表（lists）](http://www.redis.cn/topics/data-types-intro.html#lists)， [集合（sets）](http://www.redis.cn/topics/data-types-intro.html#sets)， [有序集合（sorted sets）](http://www.redis.cn/topics/data-types-intro.html#sorted-sets) 与范围查询， [bitmaps](http://www.redis.cn/topics/data-types-intro.html#bitmaps)， [hyperloglogs](http://www.redis.cn/topics/data-types-intro.html#hyperloglogs) 和 [地理空间（geospatial）](http://www.redis.cn/commands/geoadd.html) 索引半径查询。 Redis 内置了 [复制（replication）](http://www.redis.cn/topics/replication.html)，[LUA脚本（Lua scripting）](http://www.redis.cn/commands/eval.html)， [LRU驱动事件（LRU eviction）](http://www.redis.cn/topics/lru-cache.html)，[事务（transactions）](http://www.redis.cn/topics/transactions.html) 和不同级别的 [磁盘持久化（persistence）](http://www.redis.cn/topics/persistence.html)， 并通过 [Redis哨兵（Sentinel）](http://www.redis.cn/topics/sentinel.html)和自动 [分区（Cluster）](http://www.redis.cn/topics/cluster-tutorial.html)提供高可用性（high availability）。



#### 基本数据结构

##### String

###### 	描述

​		二进制安全的字符串	

​		`key -> value`

###### 	常用命令

> SET key value [EX seconds] [PX milliseconds] [NX|XX]	->	O(1)

将字符串值 `value` 关联到 `key`

**可选参数：**

- `EX seconds` ： 将键的过期时间设置为 `seconds` 秒。 执行 `SET key value EX seconds` 的效果等同于执行 `SETEX key seconds value` 。

- `PX milliseconds` ： 将键的过期时间设置为 `milliseconds` 毫秒。 执行 `SET key value PX milliseconds` 的效果等同于执行 `PSETEX key milliseconds value` 。

- `NX` ： 只在键不存在时， 才对键进行设置操作。 执行 `SET key value NX` 的效果等同于执行 `SETNX key value` 。

- `XX` ： 只在键已经存在时， 才对键进行设置操作。

**返回值：**     

​		 `SET` 命令只在设置操作成功完成时才返回 `OK` ； 如果命令使用了 `NX` 或者 `XX` 选   项， 但是因为条件没达到而造成设置操作未执行， 那么命令将返回空批量回复（NULL Bulk Reply）。



> SETNX key value    ->    O(1)
>

只在键 `key` 不存在的情aaa况下， 将键 `key` 的值设置为 `value` 。

命令在设置成功时返回 `1` ， 设置失败时返回 `0` 。



> SETEX key seconds value    ->    O(1)
>

将键 `key` 的值设置为 `value` ， 并将键 `key` 的生存时间设置为 `seconds` 秒钟。



> PSETEX key milliseconds value    ->    O(1)

将键 `key` 的值设置为 `value` ， 并将键 `key` 的生存时间设置为 `milliseconds` 毫秒。



> GET key    ->    O(1)

如果键 `key` 不存在， 那么返回特殊值 `nil` ； 否则， 返回键 `key` 的值。



> GETSET key value    ->    O(1)
>

键 `key` 的值设为 `value` ， 并返回键 `key` 在被设置之前的旧值。 `key` 不存在，则返回 `nil` 。



> STRLEN		APPEND		SETRANGE		GETRANGE



> INCR key，INCRBY key increment，INCRBYFLOAT key increment，DECR key， DECRBY key decrement    ->    O(1)
>

将键 `key` 储存的整数值加上(减去)1( `decrement` )。



> MSET key value [key value …]，MSETNX key value [key value …]，MGET key [key …]



------

##### Lists

###### 	描述

​	    按插入顺序排序的字符串元素集合。是链表结构(Linked lists)，也可作列队(Queue)		

​		`key -> v1--v2--v3--...--vn`

###### 常用命令

> LPUSH key value [value …]	->	O(1)

将一个或多个值 `value` 插入到列表 `key` 的表头，如果 `key` 不存在，一个空列表会被创建并执行 [LPUSH](http://redisdoc.com/list/lpush.html#lpush) 操作。

**返回值：**
执行 LPUSH 命令后，列表的长度。



> LPUSHX key value	->	O(1)

当且仅当 `key` 存在并且是一个列表，将值 `value` 插入到列表 `key` 的表头。返回列表的长度。



> RPUSH key value [value …]		RPUSHX key value

从表尾插入数据



> LPOP key		RPOP key		->		O(1)

移除并返回列表 `key` 的头(尾)元素。当 `key` 不存在时，返回 `nil` 。



> RPOPLPUSH source destination	->	O(1)

命令 [RPOPLPUSH](http://redisdoc.com/list/rpoplpush.html#rpoplpush) 在一个原子时间内，执行以下两个动作：

- 将列表 `source` 中的**最后一个元素**(尾元素)弹出，并**返回给客户端**。
- 将 `source` 弹出的元素插入到列表 `destination` ，作为 `destination` 列表的的头元素。

如果 `source` 不存在，值 `nil` 被返回，并且不执行其他动作。

如果 `source` 和 `destination` 相同，则列表中的表尾元素被移动到表头，并返回该元素，可以把这种特殊情况视作列表的旋转(rotation)操作。



> LREM key count value

根据参数 `count` 的值，移除列表中与参数 `value` 相等的元素。

`count` 的值可以是以下几种：

- `count > 0` : 从表头开始向表尾搜索，移除与 `value` 相等的元素，数量为 `count` 。
- `count < 0` : 从表尾开始向表头搜索，移除与 `value` 相等的元素，数量为 `count` 的绝对值。
- `count = 0` : 移除表中所有与 `value` 相等的值。

 **返回值：**

被移除元素的数量。 因为不存在的 `key` 被视作空表(empty list)，所以当 `key` 不存在时， [LREM](http://redisdoc.com/list/lrem.html#lrem) 命令总是返回 `0` 。



> LLEN key	->	O(1)

返回列表 `key` 的长度。

如果 `key` 不存在，则 `key` 被解释为一个空列表，返回 `0` .



> LINDEX key index	->	O(n)

返回列表 `key` 中，下标为 `index` 的元素。

下标(index)参数 `start` 和 `stop` 都以 `0` 为底，也就是说，以 `0` 表示列表的第一个元素，以 `1` 表示列表的第二个元素，以此类推。也可以使用负数下标，以 `-1` 表示列表的最后一个元素， `-2` 表示列表的倒数第二个元素，以此类推。



> LINSERT key BEFORE|AFTER pivot value	->	O(n)

将值 `value` 插入到列表 `key` 当中，位于值 `pivot` 之前或之后。

当 `pivot` 不存在于列表 `key` 时，不执行任何操作。

当 `key` 不存在时， `key` 被视为空列表，不执行任何操作。

**返回值：**

如果命令执行成功，返回插入操作完成之后，列表的长度。 如果没有找到 `pivot` ，返回 `-1` 。 如果 `key` 不存在或为空列表，返回 `0` 。



> LSET key index value	->	头尾操作O(1)，其他O(n)

​	将列表 `key` 下标为 `index` 的元素的值设置为 `value` 。操作成功返回 `ok` ，`index` 参数超出范围，或对一个空列表( `key` 不存在)进行 [LSET](http://redisdoc.com/list/lset.html#lset) 时，返回一个错误。



> LRANGE key start stop	->	O(s+n) `S` 为偏移量 `start` ， `N` 为指定区间内元素的数量。

​	返回列表 `key` 中<u>指定区间内的元素</u>，区间以偏移量 `start` 和 `stop` 指定。

下标(index)参数 `start` 和 `stop` 都以 `0` 为底，也可以使用负数下标，以 `-1` 表示列表的最后一个元素， `-2` 表示列表的倒数第二个元素，以此类推。**返回值包含 `start` 和 `stop` 所指的值**。**超出范围的下标值不会引起错误。**



> LTRIM key start stop	->	O(n)

对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。



> BLPOP		BRPOP		BRPOPLPUSH

对应命令的阻塞形式，没有任何元素可以操作时，连接将被命令阻塞，直到等待超时或有元素操作为止。



------

##### Sets

###### 描述

​	不重复且无序的字符串集合

​	`key -> v3--v6--v1--v9--v125--...--vn`

###### 常用命令

> SADD key member [member …]	->	O(n)

将一个或多个 `member` 元素加入到集合 `key` 当中，已经存在于集合的 `member` 元素将被忽略。

假如 `key` 不存在，则创建一个只包含 `member` 元素作成员的集合。

**返回值：**
被添加到集合中的新元素的数量，不包括被忽略的元素。



> SISMEMBER key member	->	O(1)

判断 `member` 元素是否集合 `key` 的成员。返回值

如果 `member` 元素是集合的成员，返回 `1` 。 如果 `member` 元素不是集合的成员，或 `key` 不存在，返回 `0` 。



> SPOP key	->	O(1)

**移除**并返回集合中的一个随机元素，当 `key` 不存在或 `key` 是空集时，返回 `nil` 。





> SRANDMEMBER key [count]	->	O(count)

如果命令执行时，只提供了 `key` 参数，那么返回集合中的**一个随机元素**。

如果提供了可选的 `count` 参数：

- 如果 `count` 为正数，且小于集合基数，那么命令返回一个包含 `count` 个元素的数组，数组中的元素**各不相同**。如果 `count` 大于等于集合基数，那么返回整个集合。
- 如果 `count` 为负数，那么命令返回一个数组，数组中的元素**可能会重复出现多次**，而数组的长度为 `count` 的绝对值。

该操作和 [SPOP key](http://redisdoc.com/set/spop.html#spop) 相似，但 [SPOP key](http://redisdoc.com/set/spop.html#spop) 将随机元素从集合中移除并返回，而 [SRANDMEMBER](http://redisdoc.com/set/srandmember.html#srandmember) 则仅仅返回随机元素，而不对集合进行任何改动。



> SREM key member [member …]	->	O(n)

移除集合 `key` 中的一个或多个 `member` 元素，不存在的 `member` 元素会被忽略。

**返回值：**
被成功移除的元素的数量，不包括被忽略的元素。



> SMOVE source destination member	->	O(1)

将 `member` 元素从 `source` 集合移动到 `destination` 集合。

[SMOVE](http://redisdoc.com/set/smove.html#smove) 是原子性操作。

如果 `source` 集合不存在或不包含指定的 `member` 元素，则 [SMOVE](http://redisdoc.com/set/smove.html#smove) 命令不执行任何操作，仅返回 `0` 。否则， `member` 元素从 `source` 集合中被移除，并添加到 `destination` 集合中去。

当 `destination` 集合已经包含 `member` 元素时， [SMOVE](http://redisdoc.com/set/smove.html#smove) 命令只是简单地将 `source` 集合中的 `member` 元素删除。

当 `source` 或 `destination` 不是集合类型时，返回一个错误。

**返回值：**

如果 `member` 元素被成功移除，返回 `1` 。 如果 `member` 元素不是 `source` 集合的成员，并且没有任何操作对 `destination` 集合执行，那么返回 `0` 。



> SCARD key	->	O(1)

返回集合 `key` 的基数(集合中元素的数量)。当 `key` 不存在时，返回 `0` 。



> SMEMBERS key	->	O(n)

返回集合 `key` 中的所有成员。不存在的 `key` 被视为空集合。



> SINTER key [key …]	->	O(n*m)  `N` 为基数最小的集合， `M` 为给定集合的个数。

返回一个集合的全部成员，该集合是所有给定集合的**交集**(`intersection`)。

不存在的 `key` 被视为空集。

当给定集合当中有一个空集时，结果也为空集(根据集合运算定律)。



> SINTERSTORE destination key[key …]	->	O(n*m) 

这个命令类似于 [SINTER key [key …\]](http://redisdoc.com/set/sinter.html#sinter) 命令，但它将结果保存到 `destination` 集合，而不是简单地返回结果集。

如果 `destination` 集合已经存在，则将其覆盖。

`destination` 可以是 `key` 本身。

**返回值：**
结果集中的**成员数量**。



> SUNION key [key  …]		SUNIONSTORE destination key [key …]	->	O(n*m) 

返回一个集合的全部成员，该集合是所有给定集合的**并集**。(将**并集**结果保存到 `destination` 集合，返回结果集中的元素数量)。

不存在的 `key` 被视为空集。



> SDIFF key [key …]		SDIFFSTORE destination key [key …]	->	O(n*m) 

返回一个集合的全部成员，该集合是所有给定集合之间的**差集**。(将差集结果保存到`destination`集合，返回届国际中的元素数量)。

不存在的 `key` 被视为空集。



------

##### Sorted sets

###### 描述

​	类似Sets，但每个字符串元素都关联一个(score)浮动数值(floating number value)。元素通过score值大小排序

​	`key -> v3[score]--v6[score]--v1[score]--v9[score]--v125[score]--...--vn[score]`

###### 常用命令

> ZADD key score member [[score member ] [score member] …]	->	O(M*log(n))   `N` 是有序集的基数， `M` 为成功添加的新成员的数量。

将一个或多个 `member` 元素及其 `score` 值加入到有序集 `key` 当中。

如果某个 `member` 已经是有序集的成员，那么更新这个 `member` 的 `score` 值，并通过重新插入这个 `member` 元素，来保证该 `member` 在正确的位置上。

`score` 值可以是整数值或双精度浮点数。

如果 `key` 不存在，则创建一个空的有序集并执行 [ZADD](http://redisdoc.com/sorted_set/zadd.html#zadd) 操作。

**返回值：**
被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员。



> ZSCORE key member	->	O(1)

返回有序集 `key` 中，成员 `member` 的 `score` 值。

如果 `member` 元素不是有序集 `key` 的成员，或 `key` 不存在，返回 `nil` 。



> ZINCRBY key increment member	->	O(log(n))

为有序集 `key` 的成员 `member` 的 `score` 值加上增量 `increment` 。

可以通过传递一个负数值 `increment` ，让 `score` 减去相应的值，比如 `ZINCRBY key -5 member` ，就是让 `member` 的 `score` 值减去 `5` 。

当 `key` 不存在，或 `member` 不是 `key` 的成员时， `ZINCRBY key increment member` 等同于 `ZADD key increment member` 。

当 `key` 不是有序集类型时，返回一个错误。

`score` 值可以是整数值或双精度浮点数。

**返回值：**
member 成员的新 score 值，以字符串形式表示。



> ZCARD key	->	O(1)

**返回值：**
当 `key` 存在且是有序集类型时，返回有序集的基数。 当 `key` 不存在时，返回 `0` 。



> ZCOUNT key min max	->	O(log(n))

返回有序集 `key` 中， `score` 值在 `min` 和 `max` 之间(默认包括 `score` 值等于 `min` 或 `max` )的成员的数量。

关于参数 `min` 和 `max` 的详细使用方法，请参考 [ZRANGEBYSCORE key min max [WITHSCORES\] [LIMIT offset count]](http://redisdoc.com/sorted_set/zrangebyscore.html#zrangebyscore) 命令。



> ZRANGE key start stop [WITHSCORES]	->	O(log(n)+m) `N` 为有序集的基数，而 `M` 为结果集的基数。

返回有序集 `key` 中，指定区间内的成员。

其中成员的位置按 `score` 值**递增(从小到大)**来排序。

具有相同 `score` 值的成员按字典序([lexicographical order](http://en.wikipedia.org/wiki/Lexicographical_order) )来排列。

如果你需要成员按 `score` 值递减(从大到小)来排列，请使用  [ZREVRANGE key start stop [WITHSCORES]](http://redisdoc.com/sorted_set/zrevrange.html#zrevrange)  命令。

下标参数 `start` 和 `stop` 都以 `0` 为底，也就是说，以 `0` 表示有序集第一个成员，以 `1` 表示有序集第二个成员，以此类推。 你也可以使用负数下标，以 `-1` 表示最后一个成员， `-2` 表示倒数第二个成员，以此类推。

超出范围的下标并不会引起错误。 比如说，当 `start` 的值比有序集的最大下标还要大，或是 `start > stop` 时， [ZRANGE](http://redisdoc.com/sorted_set/zrange.html#zrange) 命令只是简单地返回一个空列表。 另一方面，假如 `stop` 参数的值比有序集的最大下标还要大，那么 Redis 将 `stop` 当作最大下标来处理。

可以通过使用 `WITHSCORES` 选项，来让成员和它的 `score` 值一并返回，返回列表以 `value1,score1, ..., valueN,scoreN` 的格式表示。 客户端库可能会返回一些更复杂的数据类型，比如数组、元组等。

**返回值：**

指定区间内，带有 `score` 值(可选)的有序集成员的列表。



> ZREVRANGE key start stop [WITHSCORES]	->	O(log(n)+m)

回有序集 `key` 中，指定区间内的成员。

其中成员的位置按 `score` 值**递减(从大到小)**来排列。 具有相同 `score` 值的成员按字典序的逆序([reverse lexicographical order](http://en.wikipedia.org/wiki/Lexicographical_order#Reverse_lexicographic_order))排列。

除了成员按 `score` 值递减的次序排列这一点外， [ZREVRANGE](http://redisdoc.com/sorted_set/zrevrange.html#zrevrange) 命令的其他方面和 [ZRANGE key start stop [WITHSCORES]](http://redisdoc.com/sorted_set/zrange.html#zrange) 命令一样。

**返回值：**

指定区间内，带有 `score` 值(可选)的有序集成员的列表。



> ZRANGBYSCORE key min max [WITHSCORES] [LIMIT offset count]	->	O(log(n)+m)

返回有序集 `key` 中，所有 `score` 值介于 `min` 和 `max` 之间(包括等于 `min` 或 `max` )的成员。有序集成员按 `score` 值**递增(从小到大)**次序排列。

具有相同 `score` 值的成员按字典序([lexicographical order](http://en.wikipedia.org/wiki/Lexicographical_order))来排列(该属性是有序集提供的，不需要额外的计算)。

可选的 `LIMIT` 参数指定返回结果的数量及区间(就像SQL中的 `SELECT LIMIT offset, count` )，注意当 `offset` 很大时，定位 `offset` 的操作可能需要遍历整个有序集，此过程最坏复杂度为 O(N) 时间。

可选的 `WITHSCORES` 参数决定结果集是单单返回有序集的成员，还是将有序集成员及其 `score` 值一起返回。 该选项自 Redis 2.0 版本起可用。

**区间及无限**

`min` 和 `max` 可以是 `-inf` 和 `+inf` ，这样一来，你就可以在不知道有序集的最低和最高 `score` 值的情况下，使用 [ZRANGEBYSCORE](http://redisdoc.com/sorted_set/zrangebyscore.html#zrangebyscore) 这类命令。

默认情况下，区间的取值使用[闭区间](http://zh.wikipedia.org/wiki/區間) (小于等于或大于等于)，你也可以通过给参数前增加 `(` 符号来使用可选的[开区间](http://zh.wikipedia.org/wiki/區間) (小于或大于)。

举个例子：

```
ZRANGEBYSCORE zset (1 5
```

返回所有符合条件 `1 < score <= 5` 的成员，而

```
ZRANGEBYSCORE zset (5 (10
```

则返回所有符合条件 `5 < score < 10` 的成员。

**返回值：**

指定区间内，带有 `score` 值(可选)的有序集成员的列表。



> ZREVRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]	->	O(log(n)+m)

返回有序集 `key` 中， `score` 值介于 `max` 和 `min` 之间(默认包括等于 `max` 或 `min` )的所有的成员。有序集成员按 `score` 值**递减(从大到小)**的次序排列。

具有相同 `score` 值的成员按字典序的逆序([reverse lexicographical order](http://en.wikipedia.org/wiki/Lexicographical_order) )排列。

除了成员按 `score` 值递减的次序排列这一点外， [ZREVRANGEBYSCORE](http://redisdoc.com/sorted_set/zrevrangebyscore.html#zrevrangebyscore) 命令的其他方面和 [ZRANGEBYSCORE key min max [WITHSCORES\] [LIMIT offset count]](http://redisdoc.com/sorted_set/zrangebyscore.html#zrangebyscore) 命令一样。

**返回值：**

指定区间内，带有 `score` 值(可选)的有序集成员的列表。



> ZRANK key member		ZREVRANK key member	->	O(log(n))

返回有序集 `key` 中成员 `member` 的排名。其中有序集成员按 `score` 值**递增(从小到大)**顺序排列。

排名以 `0` 为底，也就是说， `score` 值最小的成员排名为 `0` 。

使用 [ZREVRANK key member](http://redisdoc.com/sorted_set/zrevrank.html#zrevrank) 命令可以获得成员按 `score` 值递减(从大到小)排列的排名。

**返回值：**

如果 `member` 是有序集 `key` 的成员，返回 `member` 的排名。 如果 `member` 不是有序集 `key` 的成员，返回 `nil` 。



> ZREM key member [member …]	->	O(M*log(N))， `N` 为有序集的基数， `M` 为被成功移除的成员的数量

移除有序集 `key` 中的一个或多个成员，不存在的成员将被忽略。

**返回值：**

被成功移除的成员的数量，不包括被忽略的成员。



> ZREMRANGEBYRANK key start stop	->	O(log(N)+M)， `N` 为有序集的基数，而 `M` 为被移除成员的数量。

移除有序集 `key` 中，指定排名(rank)区间内的所有成员。

区间分别以下标参数 `start` 和 `stop` 指出，包含 `start` 和 `stop` 在内。

下标参数 `start` 和 `stop` 都以 `0` 为底，也就是说，以 `0` 表示有序集第一个成员，以 `1` 表示有序集第二个成员，以此类推。 你也可以使用负数下标，以 `-1` 表示最后一个成员， `-2` 表示倒数第二个成员，以此类推。

**返回值：**

被移除成员的数量。



> ZREMRANGEBYSCORE key min max	->	O(log(N)+M)， `N` 为有序集的基数，而 `M` 为被移除成员的数量。
>

移除有序集 `key` 中，所有 `score` 值介于 `min` 和 `max` 之间(包括等于 `min` 或 `max` )的成员。

自版本2.1.6开始， `score` 值等于 `min` 或 `max` 的成员也可以不包括在内，详情请参见 [ZRANGEBYSCORE key min max [WITHSCORES\] [LIMIT offset count]](http://redisdoc.com/sorted_set/zrangebyscore.html#zrangebyscore) 命令。

** 返回值：**

被移除成员的数量。



> ZRANGEBYLEX key min max [LIMIT offset count]	->	O(log(N)+M)

当有序集合的所有成员都具有相同的分值时， 有序集合的元素会根据成员的字典序（lexicographical ordering）来进行排序， 而这个命令则可以返回给定的有序集合键 `key` 中， 值介于 `min` 和 `max` 之间的成员。

如果有序集合里面的成员带有不同的分值， 那么命令返回的结果是未指定的（unspecified）。

**返回值：**

数组回复：一个列表，列表里面包含了有序集合在指定范围内的成员。

**代码示例：**

```
redis> ZADD myzset 0 a 0 b 0 c 0 d 0 e 0 f 0 g
(integer) 7

redis> ZRANGEBYLEX myzset - [c
1) "a"
2) "b"
3) "c"

redis> ZRANGEBYLEX myzset - (c
1) "a"
2) "b"

redis> ZRANGEBYLEX myzset [aaa (g
1) "b"
2) "c"
3) "d"
4) "e"
5) "f"
```



> ZLEXCOUNT key min max	->	O(log(N))，其中 N 为有序集合包含的元素数量。
>

对于一个所有成员的分值都相同的有序集合键 `key` 来说， 这个命令会返回该集合中， 成员介于 `min` 和 `max` 范围内的元素数量。

这个命令的 `min` 参数和 `max` 参数的意义和 [ZRANGEBYLEX key min max [LIMIT offset count]](http://redisdoc.com/sorted_set/zrangebylex.html#zrangebylex) 命令的 `min` 参数和 `max` 参数的意义一样。

**返回值：**

整数回复：指定范围内的元素数量。

**代码示例：**

```
redis> ZADD myzset 0 a 0 b 0 c 0 d 0 e
(integer) 5

redis> ZADD myzset 0 f 0 g
(integer) 2

redis> ZLEXCOUNT myzset - +
(integer) 7

redis> ZLEXCOUNT myzset [b [f
(integer) 5
```



> ZREMRANGEBYLEX key min max	->	O(log(N)+M)

对于一个所有成员的分值都相同的有序集合键 `key` 来说， 这个命令会移除该集合中， 成员介于 `min` 和 `max` 范围内的所有元素。

这个命令的 `min` 参数和 `max` 参数的意义和 [ZRANGEBYLEX key min max [LIMIT offset count\]](http://redisdoc.com/sorted_set/zrangebylex.html#zrangebylex) 命令的 `min` 参数和 `max` 参数的意义一样。

**返回值：**

整数回复：被移除的元素数量。



> ZUNIONSTORE destination numkeys key [key …] [WEIGHTS weight [weight …]] [AGGREGATE SUM|MIN|MAX]	->	O(N)+O(M log(M))， `N` 为给定有序集基数的总和， `M` 为结果集的基数。
>

计算给定的一个或多个有序集的并集，其中给定 `key` 的数量必须以 `numkeys` 参数指定，并将该并集(结果集)储存到 `destination` 。

默认情况下，结果集中某个成员的 `score` 值是所有给定集下该成员 `score` 值之 *和* 。

**WEIGHTS：**

使用 `WEIGHTS` 选项，你可以为 *每个* 给定有序集 *分别* 指定一个乘法因子(multiplication factor)，每个给定有序集的所有成员的 `score` 值在传递给聚合函数(aggregation function)之前都要先乘以该有序集的因子。

如果没有指定 `WEIGHTS` 选项，乘法因子默认设置为 `1` 。

**AGGREGATE：**

使用 `AGGREGATE` 选项，你可以指定并集的结果集的聚合方式。

默认使用的参数 `SUM` ，可以将所有集合中某个成员的 `score` 值之 *和* 作为结果集中该成员的 `score` 值；使用参数 `MIN` ，可以将所有集合中某个成员的 *最小* `score` 值作为结果集中该成员的 `score` 值；而参数 `MAX` 则是将所有集合中某个成员的 *最大* `score` 值作为结果集中该成员的 `score` 值。

**返回值：**

保存到 `destination` 的结果集的基数。



> ZINTERSTORE destination numkeys key [key …] [WEIGHTS weight [weight …]] [AGGREGATE SUM|MIN|MAX]	->	O(N*K)+O(M*log(M))， `N` 为给定 `key` 中基数最小的有序集， `K` 为给定有序集的数量， `M` 为结果集的基数。

计算给定的一个或多个有序集的交集，其中给定 `key` 的数量必须以 `numkeys` 参数指定，并将该交集(结果集)储存到 `destination` 。

默认情况下，结果集中某个成员的 `score` 值是所有给定集下该成员 `score` 值之和.

关于 `WEIGHTS` 和 `AGGREGATE` 选项的描述，参见  [ZUNIONSTORE destination numkeys key [key …] [WEIGHTS weight [weight …]] [AGGREGATE SUM|MIN|MAX]] [AGGREGATE SUM|MIN|MAX]] 命令。

**返回值：**

保存到 `destination` 的结果集的基数。



------

##### Hashes

###### 描述

​	由field和关联的value组成的map。field和alue都是字符串。

​	`key -> {k1 : v1, k2 : v2, k3 : v3, ... , kn : vn}`

###### 常用命令

> HSET hash field value	->	O(1)	

将哈希表 `hash` 中域 `field` 的值设置为 `value` 。

 **返回值：**
当 `HSET` 命令在哈希表中新创建 `field` 域并成功为它设置值时， 命令返回 `1` ； 如果域 `field` 已经存在于哈希表， 并且 `HSET` 命令成功使用新值覆盖了它的旧值， 那么命令返回 `0` 。



> HSETNX hash field value	->	O(1)

当且仅当域 `field` 尚未存在于哈希表的情况下， 将它的值设置为 `value` 。

如果给定域已经存在于哈希表当中， 那么命令将放弃执行设置操作。

如果哈希表 `hash` 不存在， 那么一个新的哈希表将被创建并执行 `HSETNX` 命令。

**返回值：**
HSETNX 命令在设置成功时返回 1 ， 在给定域已经存在而放弃执行设置操作时返回 0 。



> HGET hash field	->	O(1)

返回哈希表中给定域的值。如果给定域不存在于哈希表中， 又或者给定的哈希表并不存在， 那么命令返回 `nil` 。



> HEXISTS hash field	->	O(1)

检查给定域 `field` 是否存在于哈希表 `hash` 当中。`HEXISTS` 命令在给定域存在时返回 `1` ， 在给定域不存在时返回 `0` 。



> HDEL key field [field …] O(n)		HLEN key O(1)		HSTRLEN key field O(1)		



> HINCRBY key field increment		HINCRBYFLOAT key field increment

为哈希表 `key` 中的域 `field` 的值加上增量 `increment` 。

增量也可以为负数，相当于对给定域进行减法操作。

如果 `key` 不存在，一个新的哈希表被创建并执行 [HINCRBY](http://redisdoc.com/hash/hincrby.html#hincrby) 命令。

如果域 `field` 不存在，那么在执行命令前，域的值被初始化为 `0` 。

对一个储存字符串值的域 `field` 执行 [HINCRBY](http://redisdoc.com/hash/hincrby.html#hincrby) 命令将造成一个错误。

**返回值：**

执行 [HINCRBY](http://redisdoc.com/hash/hincrby.html#hincrby) 命令之后，哈希表 `key` 中域 `field` 的值。



> HMSET key field value [field value …]	->	O(n)

同时将多个 `field-value` (域-值)对设置到哈希表 `key` 中。

此命令会覆盖哈希表中已存在的域。

如果 `key` 不存在，一个空哈希表被创建并执行 [HMSET](http://redisdoc.com/hash/hmset.html#hmset) 操作。

**返回值：**

如果命令执行成功，返回 `OK` 。

当 `key` 不是哈希表(hash)类型时，返回一个错误。



> HMGET key field [field …]	->	O(n)

返回哈希表 `key` 中，一个或多个给定域的值。

如果给定的域不存在于哈希表，那么返回一个 `nil` 值。

因为不存在的 `key` 被当作一个空哈希表来处理，所以对一个不存在的 `key` 进行 [HMGET](http://redisdoc.com/hash/hmget.html#hmget) 操作将返回一个只带有 `nil` 值的表。

**返回值：**

一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。



> HKEYS key	->	O(n)

返回哈希表 `key` 中的所有域。

当 `key` 不存在时，返回一个空表。



> HVALS key	->	O(n)

返回哈希表 `key` 中所有域的值。

当 `key` 不存在时，返回一个空表。



> HGETALL key	->	O(n)

以列表形式返回哈希表 `key` 中，所有的域和值。

若 `key` 不存在，返回空列表。



------

##### ~~Bit arrays~~

​	simply bitmaps，通过特殊命令，可以将String值当作一系列bits处理





------

##### ~~HyperLogLogs~~

​	用于估计一个 set 中元素数量的概率性的数据结构



