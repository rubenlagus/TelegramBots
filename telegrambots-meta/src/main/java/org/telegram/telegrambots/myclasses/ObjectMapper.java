package org.telegram.telegrambots.myclasses;

import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.api.objects.ResponseParameters;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smaznet on 3/21/17.
 */
public class ObjectMapper {
    public <T> T readValue(String answer, Class<ResponseParameters> typeReference) throws IOException {
        return null;
    }

    public Class<?> getParameterClass(Class clas) {
        ParameterizedType superclass = (ParameterizedType) clas.getGenericSuperclass();
        return (Class<?>) superclass.getActualTypeArguments()[0];
    }

    public Object parseObject(Object obj, String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String objname = className;
        if (className.contains("<")) {
            String arr = className.substring(className.indexOf("<") + 1, className.length() - 1);
            objname = arr;
            if (arr.contains("<")) {
                objname = arr.substring(arr.indexOf("<") + 1, arr.length() - 1);
            }
        }
        Class clazz = Class.forName(objname);
        Object object = clazz.newInstance();
        if (obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;
            for (Field filed :
                    clazz.getDeclaredFields()) {
                if (Modifier.isPrivate(filed.getModifiers()) && !Modifier.isFinal(filed.getModifiers())) {
                    if (jsonObject.has(filed.getName())) {
                        filed.setAccessible(true);
                        Object value = jsonObject.get(filed.getName());
                        if (jsonObject.get(filed.getName()) instanceof JSONObject) {
                            value = parseObject(jsonObject.get(filed.getName()), filed.getType().getName());
                        }
                        if (jsonObject.get(filed.getName()) instanceof JSONArray) {
                            value = toArrayList(jsonObject.getJSONArray(filed.getName()), objname);
                        }
                        if (filed.getType().getName().equals("java.lang.Long") && value.getClass().getName().equals("java.lang.Integer")) {
                            filed.set(object, Long.valueOf((int) value));
                            continue;
                        }
                        //
                        filed.set(object, value);
                    }
                }
            }
            if (object instanceof Update) {

            }
            return object;
        }
        return obj;
    }

    public List<Object> toArrayList(JSONArray jArray, String className) throws ClassNotFoundException {
        List<Object> listdata = new ArrayList<>();
        if (jArray != null) {
            for (int i = 0; i < jArray.length(); i++) {
                if (jArray.get(i) instanceof JSONArray) {

                    List<Object> list = toArrayList(jArray.getJSONArray(i), className);
                    listdata.add(list);
                    continue;
                }
                try {
                    listdata.add(parseObject(jArray.get(i), className));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
        return listdata;
    }

    @SuppressWarnings("unchecked")
    public <T> T readValue(String answer, TypeReference<?> typeReference) throws IOException {
        String className = ((ParameterizedTypeImpl) typeReference.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0].getTypeName();

        T response = (T) new ApiResponse<>();
        Class classs = response.getClass();
        Field[] fileds = classs.getDeclaredFields();
        JSONObject rspjson = new JSONObject(answer);
        for (Field field : fileds) {
            if (Modifier.isPrivate(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                try {
                    if (rspjson.has(field.getName())) {
                        field.setAccessible(true);
                        if (rspjson.get(field.getName()) instanceof JSONArray) {
                            List<Object> listdata = null;
                            try {
                                listdata = toArrayList(rspjson.getJSONArray(field.getName()), className);
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            field.set(response, listdata);
                            continue;
                        }
                        if (rspjson.get(field.getName()) instanceof JSONObject) {
                            try {

                                Object object = parseObject(rspjson.get(field.getName()), className);

                                field.set(response, object);
                                continue;
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            }
                        }
                        field.set(response, rspjson.get(field.getName()));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return (T) response;
    }

    public String writeValueAsString(ReplyKeyboard replyMarkup) {
        JSONObject response = new JSONObject();
        Class clzz = replyMarkup.getClass();
        for (Field f : clzz.getDeclaredFields()) {
            try {
                response.put(f.getName(), f.get(replyMarkup));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return response.toString();
    }

    public <Method extends BotApiMethod<?>> String writeValueAsString(GetUpdates request) {

        JSONObject jsonObject = new JSONObject();
        Class classs = request.getClass();
        Field[] fileds = classs.getDeclaredFields();
        for (Field field : fileds) {
            if (Modifier.isPrivate(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {

                field.setAccessible(true);
                try {
                    jsonObject.put(field.getName(), field.get(request));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject.toString();
    }

    public <Method extends BotApiMethod<?>> String writeValueAsString(Object method) {
        JSONObject output = new JSONObject();
        try {
            String className = method.getClass().getName();
            String objname = className;
            if (className.contains("<")) {
                String arr = className.substring(className.indexOf("<") + 1, className.length() - 1);
                objname = arr;
                if (arr.contains("<")) {
                    objname = arr.substring(arr.indexOf("<") + 1, arr.length() - 1);
                }
            }
            Class clazz = Class.forName(objname);
            for (Field fi : clazz.getDeclaredFields()) {
                if (Modifier.isPrivate(fi.getModifiers()) && !Modifier.isFinal(fi.getModifiers())) {
                    try {
                        fi.setAccessible(true);
                        if (fi.get(method) == null) {
                            continue;
                        }
                        if (Utils.isWrapperType(fi.get(method).getClass())) {
                            output.put(fi.getName(), fi.get(method));
                            continue;
                        }
                        if (fi.get(method) instanceof List) {
                            List<Object> items = (List<Object>) fi.get(method);
                            JSONArray jsonArray = new JSONArray();
                            for (Object object : items) {
                                if (Utils.isWrapperType(object.getClass())) {
                                    jsonArray.put(object);
                                    continue;
                                }
                                if (object instanceof List) {
                                    JSONArray jsonArray1 = new JSONArray();
                                    List<Object> objects = (List<Object>) object;
                                    for (Object objec : objects) {
                                        if (Utils.isWrapperType(objec.getClass())) {
                                            jsonArray1.put(objec);
                                            continue;
                                        }
                                        jsonArray1.put(new JSONObject(writeValueAsString(objec)));
                                    }
                                    jsonArray.put(jsonArray1);
                                    continue;
                                }
                                jsonArray.put(writeValueAsString(object));
                            }
                            output.put(fi.getName(), jsonArray);
                            continue;
                        }
                        output.put(fi.getName(), writeValueAsString(fi.get(method)));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return output.toString();
    }
}
