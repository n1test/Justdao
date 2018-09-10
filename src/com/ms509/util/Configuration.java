package com.ms509.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

public class Configuration {

    private OrderedProperties propertie;
    private FileInputStream fis;

    public Configuration() {
        propertie = new OrderedProperties();
    }

    /**
     * 是否存在配置文件 不存在则新建一个
     */
    private void checkFile() {

        try {
            File file = new File("Config.ini");
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception ignored) {

        }
    }

    /**
     * 设置配置文件ini的key和value
     *
     * @param key值
     * @param value值
     **/
    public void setValue(String key, String value) {
        try {
            this.checkFile();
            fis = new FileInputStream("Config.ini");
            propertie.load(fis);
            fis.close();
            propertie.setProperty(key, value);
            FileOutputStream fos = new FileOutputStream("Config.ini");
            propertie.store(fos, null);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取配置文件具体参数的值
     *
     * @param key值
     * @return 值 错误为空
     */
    public String getValue(String key) {
        String value;
        try {
            fis = new FileInputStream("Config.ini");
            propertie.load(fis);
            fis.close();
            value = propertie.getProperty(key);
        } catch (Exception e) {
            return "";
        }
        if (value == null) {
            value = "";
        }
        return value;
    }
}

class OrderedProperties extends Properties {
    private final LinkedHashSet<Object> keys = new LinkedHashSet<>();

    @Override
    public Enumeration<Object> keys() {
        return Collections.enumeration(keys);
    }

    @Override
    public Object put(Object key, Object value) {
        keys.add(key);
        return super.put(key, value);
    }

    @Override
    public Set<Object> keySet() {
        return keys;
    }

    @Override
    public Set<String> stringPropertyNames() {
        Set<String> set = new LinkedHashSet<>();
        for (Object key : this.keys) {
            set.add((String) key);
        }
        return set;
    }
}
