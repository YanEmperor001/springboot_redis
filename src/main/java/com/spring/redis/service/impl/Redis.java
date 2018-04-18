/*
package com.spring.redis.service.impl;

import com.google.common.base.Function;
import com.spring.redis.config.TypeConvert;
import com.spring.redis.config.Zuple;
import com.spring.redis.service.IRedisService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.*;

@Service("iRedisService")
public class Redis implements IRedisService {
    public static final Logger logger = Logger.getLogger(Redis.class);

    @Autowired
    protected JedisPool jedisPool;
    protected String app;
    protected TypeConvert typeConvert;

    public Redis(JedisPool jedisPool) {
        this.typeConvert = TypeConvert.DEFAULT;
        this.jedisPool = jedisPool;
    }

    public long del(final String key) {
        return this.execute(jedis -> jedis.del(key), key);
    }

    public String getApp() {
        return this.app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public long incr(final String key) {
        return this.execute(jedis -> jedis.incr(key), key);
    }

    public long decr(final String key) {
        return this.execute(jedis -> jedis.decr(key), key);
    }

    public boolean exists(final String key) {
        return this.execute(jedis -> jedis.exists(key), key);
    }

    public long expire(final String key, final int seconds) {
        return this.execute(jedis -> jedis.expire(key, seconds), key);
    }

    public long ttl(final String key) {
        return this.execute(jedis -> jedis.ttl(key), key);
    }

    public String rename(final String oldkey, final String newkey) {
        return this.execute(jedis -> jedis.rename(oldkey, newkey), oldkey, newkey);
    }

    public String type(final String key) {
        return this.execute(jedis -> jedis.type(key), key);
    }

    public <T, F> long hset(final String key, final F field, final T value) {
        return this.execute(jedis -> jedis.hset(key, Redis.this.typeConvert.toString(field), Redis.this.typeConvert.toString(value)), key);
    }

    public String hmset(final String key, final Map<String, String> hashMap) {
        return this.execute(jedis -> jedis.hmset(key, hashMap), key);
    }

    public <F> boolean hexists(final String key, final F field) {
        return this.execute(jedis -> jedis.hexists(key, Redis.this.typeConvert.toString(field)), key);
    }

    public <T, F> T hget(final String key, final F field, final Class<T> clazz) {
        return this.execute(jedis -> Redis.this.typeConvert.toType(jedis.hget(key, Redis.this.typeConvert.toString(field)), clazz), key);
    }

    public <T, F> Map<F, T> hgetAll(String key, final Class<F> keyClazz, final Class<T> valueClazz) {
        Map<String, String> stringStringMap = this.getJedis().hgetAll(key);
        Map<F, T> ftMap = this.typeConvert.transformMap(stringStringMap, input -> Redis.this.typeConvert.toType(input, keyClazz), new Function<String, T>() {
            public T apply(String input) {
                return Redis.this.typeConvert.toType(input, valueClazz);
            }
        });
        return ftMap;
    }

    public Map<String, String> hgetAll(final String key) {
        return this.execute(jedis -> jedis.hgetAll(key), key);
    }

    public <F> Long hdel(final String key, final F field) {
        return this.execute(jedis -> jedis.hdel(key, new String[]{Redis.this.typeConvert.toString(field)}), key);
    }

    public long hincrBy(final String key, final String field, final long value) {
        return this.execute(jedis -> jedis.hincrBy(key, field, value), key);
    }

    public <T> long srem(final String key, final T member) {
        return this.execute(jedis -> jedis.srem(key, new String[]{Redis.this.typeConvert.toString(member)}), key);
    }

    public <T> long sadd(final String key, final T member) {
        return this.execute(jedis -> jedis.sadd(key, new String[]{Redis.this.typeConvert.toString(member)}), key);
    }

    public long sadds(final String key, final String[] members) {
        return this.execute(jedis -> jedis.sadd(key, members), key);
    }

    public <T> Set<T> smembers(final String key, final Class<T> clazz) {
        return this.execute(jedis -> Redis.this.typeConvert.toSet(jedis.smembers(key), clazz), key);
    }

    public <T> boolean sisMember(final String key, final T member) {
        return this.execute(jedis -> jedis.sismember(key, Redis.this.typeConvert.toString(member)), key);
    }

    public long scard(final String key) {
        return this.execute(jedis -> jedis.scard(key), key);
    }

    public <T> T spop(final String key, final Class<T> clazz) {
        return this.execute(jedis -> Redis.this.typeConvert.toType(jedis.spop(key), clazz), key);
    }

    public <T> T srandMember(final String key, final Class<T> clazz) {
        return this.execute(jedis -> Redis.this.typeConvert.toType(jedis.srandmember(key), clazz), key);
    }

    public <T> String set(String key, T value) {
        return this.set(key, value, -1);
    }

    public <T> String set(final String key, final T value, final int seconds) {
        return value == null ? null : this.execute(jedis -> seconds <= 0 ? jedis.set(key, Redis.this.typeConvert.toString(value)) : jedis.setex(key, seconds, Redis.this.typeConvert.toString(value)), key);
    }

    public <T> T get(final String key, final Class<T> clazz) {
        return this.execute(jedis -> Redis.this.typeConvert.toType(jedis.get(key), clazz), key);
    }

    public Set<String> keys(final String key) {
        return this.execute(jedis -> {
            Set<String> keySet = jedis.keys(key);
            return keySet;
        }, key);
    }

    public <T> List<T> mget(final String[] keys, final Class<T> clazz) {
        return this.execute(jedis -> Redis.this.typeConvert.toList(jedis.mget(keys), clazz), keys);
    }

    public <T> List<T> mget(List<String> keys, final Class<T> clazz) {
        final String[] keysStr = (String[])keys.toArray(new String[keys.size()]);
        return this.execute(jedis -> Redis.this.typeConvert.toList(jedis.mget(keysStr), clazz), keysStr);
    }

    public Long llength(final String key) {
        return this.execute(jedis -> jedis.llen(key), key);
    }

    public String ltrim(final String key, final long startIndex, final long endIndex) {
        return this.execute(jedis -> jedis.ltrim(key, startIndex, endIndex), key);
    }

    public <T> List<T> lrange(final String key, final long start, final long size, final Class<T> clazz) {
        return this.execute(jedis -> {
            long end = Redis.this.getEnd(start, size);
            List<T> list = Redis.this.typeConvert.toList(jedis.lrange(key, start, end), clazz);
            return list;
        }, key);
    }

    public List<String> lrange(final String key, final long start, final long end) {
        return this.execute(jedis -> jedis.lrange(key, start, end), key);
    }

    public <T> List<T> lrangeStartEnd(final String key, final long start, final long end, final Class<T> clazz) {
        return this.execute(jedis -> {
            List<T> list = Redis.this.typeConvert.toList(jedis.lrange(key, start, end), clazz);
            return list;
        }, key);
    }

    public <T> T lindex(final String key, final long index, final Class<T> clazz) {
        return this.execute(jedis -> Redis.this.typeConvert.toType(jedis.lindex(key, index), clazz), key);
    }

    public <T> Long lpush(final String key, final T value) {
        return value == null ? null : this.execute(jedis -> jedis.lpush(key, new String[]{Redis.this.typeConvert.toString(value)}), key);
    }

    public <T> Long lpushList(final String key, final List<T> valueList) {
        return CollectionUtils.isEmpty(valueList) ? null : this.execute(jedis -> {
            int length = valueList.size();
            String[] values = new String[length];

            for(int i = 0; i < length; ++i) {
                values[i] = Redis.this.typeConvert.toString(valueList.get(i));
            }

            return jedis.lpush(key, values);
        }, key);
    }

    public <T> T lpop(final String key, final Class<T> clazz) {
        return this.execute(jedis -> Redis.this.typeConvert.toType(jedis.lpop(key), clazz), key);
    }

    public <T> Long rpush(final String key, final T value) {
        return value == null ? null : this.execute(jedis -> jedis.rpush(key, new String[]{Redis.this.typeConvert.toString(value)}), key);
    }

    public <T> T rpop(final String key, final Class<T> clazz) {
        return this.execute(jedis -> Redis.this.typeConvert.toType(jedis.rpop(key), clazz), key);
    }

    public <T> Long lrem(String key, T value) {
        return this.lrem(key, 0L, value);
    }

    public <T> Long lrem(final String key, final long count, final T value) {
        return this.execute(jedis -> jedis.lrem(key, count, Redis.this.typeConvert.toString(value)), key);
    }

    public <T> Long zadd(final String key, final Number score, final T member) {
        return score != null && member != null ? this.execute(jedis -> jedis.zadd(key, score.doubleValue(), Redis.this.typeConvert.toString(member)), key) : null;
    }

    public <T> Long zrem(final String key, final T member) {
        return this.execute(jedis -> jedis.zrem(key, new String[]{Redis.this.typeConvert.toString(member)}), key);
    }

    public Long zremrangeByRank(final String key, final long start, final long size) {
        return this.execute(jedis -> {
            long end = Redis.this.getEnd(start, size);
            return jedis.zremrangeByRank(key, start, end);
        }, key);
    }

    public Long zremrangeByScore(final String key, final Number min, final Number max) {
        return this.execute(jedis -> jedis.zremrangeByScore(key, min.doubleValue(), max.doubleValue()), key);
    }

    public long zcard(final String key) {
        return this.execute(jedis -> jedis.zcard(key), key);
    }

    public <T> Number zincrby(final String key, final Number increment, final T member) {
        return this.execute((IExecutor<Number>) jedis -> jedis.zincrby(key, increment.doubleValue(), Redis.this.typeConvert.toString(member)), key);
    }

    public <T> long zrank(final String key, final T member) {
        return this.execute(jedis -> {
            Long rank = jedis.zrank(key, Redis.this.typeConvert.toString(member));
            return rank == null ? -1L : rank;
        }, key);
    }

    public <T> long zrevrank(final String key, final T member) {
        return this.execute(jedis -> {
            Long rank = jedis.zrevrank(key, Redis.this.typeConvert.toString(member));
            return rank == null ? -1L : rank;
        }, key);
    }

    public <T> Number zscore(final String key, final T member) {
        return this.execute((IExecutor<Number>) jedis -> jedis.zscore(key, Redis.this.typeConvert.toString(member)), key);
    }

    public long zcount(final String key, final Number min, final Number max) {
        return this.execute(jedis -> jedis.zcount(key, min.doubleValue(), max.doubleValue()), key);
    }

    public <T> Set<T> zrange(final String key, final long start, final long size, final Class<T> clazz) {
        return this.execute(jedis -> {
            long end = Redis.this.getEnd(start, size);
            return Redis.this.typeConvert.toSet(jedis.zrange(key, start, end), clazz);
        }, key);
    }

    public <T> Set<T> zrange(final String key, final int start, final int end, final Class<T> clazz) {
        return this.execute(jedis -> Redis.this.typeConvert.toSet(jedis.zrange(key, start, end), clazz), key);
    }

    public <T> Set<Zuple<T>> zrangeWithScores(final String key, final long start, final long size, final Class<T> clazz) {
        return this.execute(jedis -> {
            long end = Redis.this.getEnd(start, size);
            return Redis.this.toTuples(jedis.zrangeWithScores(key, start, end), clazz);
        }, key);
    }

    public <T> Set<T> zrevrange(final String key, final long start, final long size, final Class<T> clazz) {
        return this.execute(jedis -> {
            long end = Redis.this.getEnd(start, size);
            return Redis.this.typeConvert.toSet(jedis.zrevrange(key, start, end), clazz);
        }, key);
    }

    public <T> Set<Zuple<T>> zrevrangeWithScores(final String key, final long start, final long size, final Class<T> clazz) {
        return this.execute(jedis -> {
            long end = Redis.this.getEnd(start, size);
            return Redis.this.toTuples(jedis.zrevrangeWithScores(key, start, end), clazz);
        }, key);
    }

    public <T> Set<T> zrangeByScore(final String key, final Number min, final Number max, final Class<T> clazz) {
        return this.execute(jedis -> Redis.this.typeConvert.toSet(jedis.zrangeByScore(key, min.doubleValue(), max.doubleValue()), clazz), key);
    }

    public <T> Set<Zuple<T>> zrangeByScoreWithScores(final String key, final Number min, final Number max, final Class<T> clazz) {
        return this.execute(jedis -> Redis.this.toTuples(jedis.zrangeByScoreWithScores(key, min.doubleValue(), max.doubleValue()), clazz), key);
    }

    public <T> Set<T> zrevrangeByScore(final String key, final Number min, final Number max, final Class<T> clazz) {
        return this.execute(jedis -> Redis.this.typeConvert.toSet(jedis.zrevrangeByScore(key, min.doubleValue(), max.doubleValue()), clazz), key);
    }

    public <T> Set<Zuple<T>> zrevrangeByScoreWithScores(final String key, final Number min, final Number max, final Class<T> clazz) {
        return this.execute(jedis -> Redis.this.toTuples(jedis.zrevrangeByScoreWithScores(key, min.doubleValue(), max.doubleValue()), clazz), key);
    }

    public long zunionStore(final String newKey, final String... keys) {
        return this.execute(jedis -> jedis.zunionstore(newKey, keys), (String[]) ArrayUtils.addAll(new String[]{newKey}, keys));
    }

    public long zinterStore(final String newKey, final String... keys) {
        return this.execute(jedis -> jedis.zinterstore(newKey, keys), (String[])ArrayUtils.addAll(new String[]{newKey}, keys));
    }

    public Jedis getJedis() {
        return this.jedisPool.getResource();
    }

    protected <T> Set<Zuple<T>> toTuples(Set<Tuple> tuples, Class<T> clazz) {
        if (tuples == null) {
            return Collections.emptySet();
        } else {
            Set<Zuple<T>> zuples = new LinkedHashSet(tuples.size());
            Iterator i$ = tuples.iterator();

            while(i$.hasNext()) {
                Tuple tuple = (Tuple)i$.next();
                T element = this.typeConvert.toType(tuple.getElement(), clazz);
                Zuple<T> zuple = new Zuple(element, tuple.getScore());
                zuples.add(zuple);
            }

            return zuples;
        }
    }

    private long getEnd(long start, long size) {
        return size < 0L ? -1L : start + size - 1L;
    }

    protected <T> T execute(Redis.IExecutor<T> executor, String... keys) {
        if (keys != null && keys.length != 0) {
            String[] arr$ = keys;
            int len$ = keys.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String key = arr$[i$];
                if (key == null || key.trim().length() == 0) {
                    return null;
                }
            }

            Jedis jedis = null;
            Object result = null;

            try {
                jedis = this.jedisPool.getResource();
                result = executor.run(jedis);
            } catch (Exception var10) {
                logger.error("keys: " + keys, var10);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }

            }

            return (T) result;
        } else {
            return null;
        }
    }

    protected interface IExecutor<T> {
        T run(Jedis var1);
    }
}
*/
