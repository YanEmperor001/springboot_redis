package com.spring.redis.config;

import com.google.common.base.Function;
import com.google.common.base.Functions;

import java.util.*;

public class TypeConvert {
    public static final TypeConvert DEFAULT = new TypeConvert();

    public TypeConvert() {
    }

    public <T> String toString(T value) {
        if (value == null) {
            return null;
        } else {
            Class<?> clazz = value.getClass();
            String result = "";
            if (value instanceof Number) {
                result = value.toString();
            } else if (clazz.equals(Date.class)) {
                Date date = (Date)value;
                result = "" + date.getTime();
            } else if (!clazz.equals(Boolean.class) && !clazz.equals(Boolean.TYPE)) {
                if (!clazz.equals(Character.class) && !clazz.equals(String.class)) {
                    result = JsonUtils.toSimpleJson(value);
                } else {
                    result = value.toString();
                }
            } else {
                Boolean bool = (Boolean)value;
                result = bool ? "1" : "0";
            }

            return result;
        }
    }

    public <T> T toType(String value, Class<T> clazz) {
        T t = null;
        if (!clazz.equals(Double.class) && !clazz.equals(Double.TYPE)) {
            if (!clazz.equals(Float.class) && !clazz.equals(Float.TYPE)) {
                if (!clazz.equals(Long.class) && !clazz.equals(Long.TYPE)) {
                    if (!clazz.equals(Integer.class) && !clazz.equals(Integer.TYPE)) {
                        if (!clazz.equals(Short.class) && !clazz.equals(Short.TYPE)) {
                            if (!clazz.equals(Byte.class) && !clazz.equals(Byte.TYPE)) {
                                if (!clazz.equals(Character.class) && !clazz.equals(Character.TYPE)) {
                                    if (clazz.equals(String.class)) {
                                        String val = "";
                                        if (value != null) {
                                            val = value;
                                        }

                                        t = (T) val;
                                    } else if (!clazz.equals(Boolean.class) && !clazz.equals(Boolean.TYPE)) {
                                        if (clazz.equals(Date.class)) {
                                            Date dateVal = new Date(0L);
                                            if (value != null) {
                                                try {
                                                    Long time = Long.parseLong(value);
                                                    dateVal = new Date(time);
                                                } catch (Exception var6) {
                                                    var6.printStackTrace();
                                                }
                                            }

                                            t = (T) dateVal;
                                        } else if (value != null) {
                                            t = JsonUtils.fromSimpleJson(value, clazz);
                                        }
                                    } else {
                                        Boolean val = false;
                                        if (value != null) {
                                            val = value.equals("1");
                                        }

                                        t = (T) val;
                                    }
                                } else {
                                    Character val = new Character(' ');
                                    if (value != null) {
                                        val = value.length() > 0 ? value.charAt(0) : new Character(' ');
                                    }

                                    t = (T) val;
                                }
                            } else {
                                Byte val = 0;
                                if (value != null) {
                                    try {
                                        val = Byte.parseByte(value);
                                    } catch (Exception var7) {
                                        var7.printStackTrace();
                                    }
                                }

                                t = (T) val;
                            }
                        } else {
                            Short val = Short.valueOf((short)0);
                            if (value != null) {
                                try {
                                    val = Short.parseShort(value);
                                } catch (Exception var8) {
                                    var8.printStackTrace();
                                }
                            }

                            t = (T) val;
                        }
                    } else {
                        Integer val = 0;
                        if (value != null) {
                            try {
                                val = Integer.parseInt(value);
                            } catch (Exception var9) {
                                var9.printStackTrace();
                            }
                        }

                        t = (T) val;
                    }
                } else {
                    Long val = 0L;
                    if (value != null) {
                        try {
                            val = Long.parseLong(value);
                        } catch (Exception var10) {
                            var10.printStackTrace();
                        }
                    }

                    t = (T) val;
                }
            } else {
                Float val = 0.0F;
                if (value != null) {
                    try {
                        val = Float.parseFloat(value);
                    } catch (Exception var11) {
                        var11.printStackTrace();
                    }
                }

                t = (T) val;
            }
        } else {
            Double val = 0.0D;
            if (value != null) {
                try {
                    val = Double.parseDouble(value);
                } catch (Exception var12) {
                    var12.printStackTrace();
                }
            }

            t = (T) val;
        }

        return t;
    }

    public <T> Set<T> toSet(Set<String> set, Class<T> clazz) {
        if (set == null) {
            return Collections.emptySet();
        } else {
            Set<T> result = new LinkedHashSet(set.size());
            Iterator i$ = set.iterator();

            while(i$.hasNext()) {
                String value = (String)i$.next();
                T t = this.toType(value, clazz);
                result.add(t);
            }

            return result;
        }
    }

    public <T> List<T> toList(List<String> list, Class<T> clazz) {
        if (list == null) {
            return Collections.emptyList();
        } else {
            List<T> result = new ArrayList(list.size());
            Iterator i$ = list.iterator();

            while(i$.hasNext()) {
                String value = (String)i$.next();
                T t = this.toType(value, clazz);
                result.add(t);
            }

            return result;
        }
    }

    public <K, V, L, W> Map<L, W> transformMap(Map<K, V> map, Function<K, L> keyFunction, Function<V, W> valueFunction) {
        Map<L, W> transformedMap = new HashMap();
        Iterator i$ = map.entrySet().iterator();

        while(i$.hasNext()) {
            Map.Entry<K, V> entry = (Map.Entry)i$.next();
            transformedMap.put(keyFunction.apply(entry.getKey()), valueFunction.apply(entry.getValue()));
        }

        return transformedMap;
    }

    public <K, V, L> Map<L, V> transformKeys(Map<K, V> map, Function<K, L> keyFunction) {
        return this.transformMap(map, keyFunction, Functions.identity());
    }
}
