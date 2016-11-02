package com.lenovo.csd.eservice.http.callback.adapter;

import com.google.gson.Gson;
import com.lenovo.csd.eservice.http.callback.HttpCallBack;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by turing
 * Date:2016/7/21
 * Time:11:03
 */
public abstract class JsonHttpCallBack<T> extends HttpCallBack<T> {

    @Override
    public T parseData(String result) {
        T t = null;
        //try {
            Gson gson = new Gson();
         /*   if (getDataType() == DataType.ARRAY) {
                JSONObject jsonObject = new JSONObject(result);
                t = gson.fromJson(jsonObject.getString(getDataName()), getType());
            } else {*/
        try {
            t = gson.fromJson(result,getGenericClass());
            //t = gson.fromJson(result,new TypeToken<T>() {}.getType());
        }
        catch (Exception e){

        }
            //}
       /* } catch (JSONException e) {
            e.printStackTrace();
        }*/
        return t;
    }
    @SuppressWarnings("unchecked")
    private Class<T> getGenericClass() {
        Type type = getClass().getGenericSuperclass();
        Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
        return (Class<T>) arguments[0];
    }

    /**
     * 在数组中要获取的name（当数据类型为集合时）
     *
     * @return data_name
   /*  *//*
    public String getDataName() {
        return "data";
    }
*/
    /***Z
     * 数据的类型（是集合型的还是只是一个对象）
     *
     * @return data_type
     */
  /*  public DataType getDataType() {
        return DataType.ARRAY;
    }*/

    /***
     * 获取泛型的Type
     *
     * @return 泛型 Type
     */
    //public abstract Type getType();
}
