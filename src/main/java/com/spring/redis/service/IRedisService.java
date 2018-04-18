package com.spring.redis.service;

import com.spring.redis.config.Zuple;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IRedisService {

    String getApp();

    void setApp(String var1);

    boolean exists(String var1);

    long expire(String var1, int var2);

    String type(String var1);

    long ttl(String var1);

    String rename(String var1, String var2);

    <T> String set(String var1, T var2);

    <T> String set(String var1, T var2, int var3);

    <T> T get(String var1, Class<T> var2);

    Set<String> keys(String var1);

    <T> List<T> mget(String[] var1, Class<T> var2);

    <T> List<T> mget(List<String> var1, Class<T> var2);

    long del(String var1);

    long incr(String var1);

    long decr(String var1);

    <T, F> long hset(String var1, F var2, T var3);

    String hmset(String var1, Map<String, String> var2);

    <F> boolean hexists(String var1, F var2);

    <T, F> T hget(String var1, F var2, Class<T> var3);

    <T, F> Map<F, T> hgetAll(String var1, Class<F> var2, Class<T> var3);

    <F> Long hdel(String var1, F var2);

    long hincrBy(String var1, String var2, long var3);

    <T> long sadd(String var1, T var2);

    <T> long srem(String var1, T var2);

    long sadds(String var1, String[] var2);

    <T> Set<T> smembers(String var1, Class<T> var2);

    <T> boolean sisMember(String var1, T var2);

    long scard(String var1);

    <T> T spop(String var1, Class<T> var2);

    <T> T srandMember(String var1, Class<T> var2);

    Long llength(String var1);

    String ltrim(String var1, long var2, long var4);

    <T> List<T> lrange(String var1, long var2, long var4, Class<T> var6);

    List<String> lrange(String var1, long var2, long var4);

    <T> T lindex(String var1, long var2, Class<T> var4);

    <T> Long lpush(String var1, T var2);

    <T> Long lpushList(String var1, List<T> var2);

    <T> T lpop(String var1, Class<T> var2);

    <T> Long rpush(String var1, T var2);

    <T> T rpop(String var1, Class<T> var2);

    <T> Long lrem(String var1, T var2);

    <T> Long lrem(String var1, long var2, T var4);

    <T> Long zadd(String var1, Number var2, T var3);

    <T> Long zrem(String var1, T var2);

    Long zremrangeByRank(String var1, long var2, long var4);

    Long zremrangeByScore(String var1, Number var2, Number var3);

    long zcard(String var1);

    <T> Number zincrby(String var1, Number var2, T var3);

    <T> long zrank(String var1, T var2);

    <T> long zrevrank(String var1, T var2);

    <T> Number zscore(String var1, T var2);

    long zcount(String var1, Number var2, Number var3);

    <T> Set<T> zrange(String var1, long var2, long var4, Class<T> var6);

    <T> Set<T> zrange(String var1, int var2, int var3, Class<T> var4);

    <T> Set<Zuple<T>> zrangeWithScores(String var1, long var2, long var4, Class<T> var6);

    <T> Set<T> zrevrange(String var1, long var2, long var4, Class<T> var6);

    <T> Set<Zuple<T>> zrevrangeWithScores(String var1, long var2, long var4, Class<T> var6);

    <T> Set<T> zrangeByScore(String var1, Number var2, Number var3, Class<T> var4);

    <T> Set<Zuple<T>> zrangeByScoreWithScores(String var1, Number var2, Number var3, Class<T> var4);

    <T> Set<T> zrevrangeByScore(String var1, Number var2, Number var3, Class<T> var4);

    <T> Set<Zuple<T>> zrevrangeByScoreWithScores(String var1, Number var2, Number var3, Class<T> var4);

    long zunionStore(String var1, String... var2);

    long zinterStore(String var1, String... var2);

    Jedis getJedis();
}
