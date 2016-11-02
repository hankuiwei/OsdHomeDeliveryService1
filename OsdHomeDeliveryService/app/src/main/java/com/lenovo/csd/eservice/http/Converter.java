package com.lenovo.csd.eservice.http;
/**
 * Created by shengtao
 * Date:2016/7/21
 * Time:11:03
 */
public interface Converter<T> {

	public T from(String result, Class<? extends T> clazz);

	public String to(T t);

}
