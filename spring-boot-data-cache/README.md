## 工程简介
SpringBoot工程中常用的缓存


### Caffeine本地缓存
- `CaffeineCacheAPI` : Caffeine缓存常用API
- `CaffeineCacheStrategy` : 缓存使用及策略

### GuavaCache本地缓存
- `GuavaCacheAPI` : GuavaCache缓存常用API
- `GuavaCacheStrategy` : 缓存使用及策略

### Redis缓存
- `RedisBasicOperation` : Redis基础操作
- `RedisBloomFilter` : 布隆过滤器
- `RedisComplexTemplate` : 管道操作、游标操作（禁止使用key *）

### Ehcache缓存
待添加......