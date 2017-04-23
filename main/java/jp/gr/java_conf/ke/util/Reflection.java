package jp.gr.java_conf.ke.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by YT on 2017/04/20.
 */
public class Reflection {

    private static final String NL = System.getProperty("line.separator");

    public static <E> E newInstance(String className) {
        return newInstance(className, null);
    }

    public static <E> E newInstance(String className, Class<E> _interface) {
        Class<?> retClass;
        E ret;
        try {
            retClass = Class.forName(className);
            Object callee = retClass.newInstance();
            if (_interface != null && !_interface.isInstance(callee)) {
                throw new ReflectiveException(
                        "インスタンスは" + _interface.getCanonicalName() +"を実装していません。" + NL +
                                "対象クラス: " + className);
            }
            ret = (E) callee;

        } catch (ClassCastException e) {
            throw new ReflectiveException("無効なクラス指定: " + className, e);

        } catch (ClassNotFoundException e) {
            throw new ReflectiveException("無効なクラス指定: " + className, e);

        } catch (InstantiationException e) {
            throw new ReflectiveException(
                    "インスタンスを生成できません。publicデフォルトコンストラクタを有効にしてください。" + NL +
                            "対象クラス: " + className, e);

        } catch (IllegalAccessException e) {
            throw new ReflectiveException(
                    "インスタンスを生成できません。publicデフォルトコンストラクタを有効にしてください。" + NL +
                            "対象クラス: " + className, e);

        }
        return ret;
    }

    public static <E> E callMethod(Object model, String methodName, Object... args) {
        if (model == null) throw new NullPointerException("model is null");
        if (methodName == null) throw new NullPointerException("methodName is null");
        Class clazz = model.getClass();
        E ret;
        try {
            Method method;
            if (args == null) {
                method = clazz.getDeclaredMethod(methodName);
            } else {
                List<Class> classList = new LinkedList<Class>();
                for (Object arg : args) {
                    if (arg == null) throw new IllegalArgumentException("args has null element.");
                    classList.add(arg.getClass());
                }
                Class[] argTypes = new Class[classList.size()];
                argTypes = classList.toArray(argTypes);
                method = clazz.getDeclaredMethod(methodName, argTypes);
            }
            Object r = method.invoke(model, args);
            ret = (E) r;

        } catch (NoSuchMethodException e) {
            throw new ReflectiveException(e);

        } catch (IllegalAccessException e) {
            throw new ReflectiveException(e);

        } catch (InvocationTargetException e) {
            throw new ReflectiveException(e);

        } catch (ClassCastException e) {
            throw new ReflectiveException(e);
        }
        return ret;
    }

    public static <E> E getFieldValue(Object model, String fieldName) {
        if (model == null) throw new NullPointerException("model is null");
        if (fieldName == null) throw new NullPointerException("fieldName is null");
        Class clazz = model.getClass();
        Field field;
        E ret;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object r = field.get(model);
            ret = (E) r;

        } catch (NoSuchFieldException e) {
            throw new ReflectiveException(e);

        } catch (SecurityException e) {
            throw new ReflectiveException(
                    "フィールドインジェクションに失敗しました。" + NL +
                            "セキュリティマネージャーを無効かするか、一部のパーミッションを許可することを検討してください。 "
                    , e);
        } catch (ClassCastException e) {
            throw new ReflectiveException(e);

        } catch (IllegalAccessException e) {
            throw new ReflectiveException(
                    "フィールドインジェクションに失敗しました。" + NL +
                            "フィールド: " + fieldName + NL +
                            "アダプタ　: " + clazz.getCanonicalName()
                    , e);
        }
        return ret;
    }

    public static void setFieldValue(Object model, Field field, Object value) {
        if (model == null) throw new NullPointerException("model is null");
        if (field == null) throw new NullPointerException("field is null");

        Class fieldClass = field.getType();
        field.setAccessible(true);
        try {
            field.set(model, fieldClass.cast(value));
        } catch (IllegalAccessException e) {
            throw new ReflectiveException(e);
        }
    }

    public static void setFieldValue(Object model, String fieldName, Object value) {
        if (model == null) throw new NullPointerException("model is null");
        if (fieldName == null) throw new NullPointerException("fieldName is null");
        Class clazz = model.getClass();
        Field field;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            Class fieldClass = field.getType();
            field.set(model, fieldClass.cast(value));

        } catch (NoSuchFieldException e) {
            throw new ReflectiveException(e);

        } catch (SecurityException e) {
            throw new ReflectiveException(
                    "フィールドインジェクションに失敗しました。" + NL +
                            "セキュリティマネージャーを無効かするか、一部のパーミッションを許可することを検討してください。 "
                    , e);
        } catch (ClassCastException e) {
            throw new ReflectiveException(e);

        } catch (IllegalAccessException e) {
            throw new ReflectiveException(
                    "フィールドインジェクションに失敗しました。" + NL +
                            "フィールド: " + fieldName + NL +
                            "アダプタ　: " + clazz.getCanonicalName()
                    , e);
        }
    }

}
